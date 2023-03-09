/**
 * Title: ObstacleAvoidance.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 10, 2022
 * *derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.steeringbehaviors.collision;

import ai.steeringbehaviors.Kinematic;
import ai.steeringbehaviors.SteeringOutput;
import ai.steeringbehaviors.seekandflee.Seek;
import math.Vector2;

/**
 * The Class ObstacleAvoidance. This class calls the CollisionDetector class and asks for 
 * a collision that will occur in the future. If said collision exists than create a target 
 * that is handed to the seek class. 
 */
public class ObstacleAvoidance extends Seek {
	
	/** The detector. */
	CollisionDetector detector;
	
	/** The avoid distance. */
	float avoidDistance;
	
	/** The look ahead. */
	float lookAhead;

	/**
	 * Obstacle avoidance.
	 *
	 * @param character the character
	 * @param avoidDistance the avoid distance
	 * @param lookAhead the look ahead
	 * @param maxAcceleration the max acceleration
	 * @param detector the detector
	 */
	public ObstacleAvoidance(Kinematic character, float avoidDistance,
			float lookAhead, float maxAcceleration, CollisionDetector detector) {
		super(character, new Kinematic(0,0,0), maxAcceleration);
		this.lookAhead = lookAhead;
		this.avoidDistance = avoidDistance;
		this.detector = detector;

	}
	
	@Override
	public SteeringOutput getSteering() {
		
		//Step 1: calculate the target to delegate to seek
		
		//calculate the collision ray vector
		Vector2 ray = new Vector2(character.getVelocity().getX(), character.getVelocity().getY());
		ray.normalize();
		ray.scaleVector(lookAhead);
		
		//find the collision
		Collision collision = detector.getCollision(character.getPosition(), ray);
		
		//if no collision
		if (collision == null) return null;
		
		//Step 2: create a target and delegate to seek
		Vector2 reflect = character.getVelocity().reflect(collision.getNormal());
		Vector2 newV = character.getVelocity().addTwoVectors(reflect);
		//obj.getKinematic().setVelocity(newV,obj.getSpeed());
		Vector2 targetPosition = character.getPosition().addTwoVectors(newV.scaler(avoidDistance));
		super.target.setPosition(targetPosition);
		
		//delegate to seek
		return super.getSteering();
	}
	
}
