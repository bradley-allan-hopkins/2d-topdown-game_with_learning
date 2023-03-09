/**
 * Title: WanderSearch.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 16, 2022
 */
package ai.statemachine.actions;

import java.util.List;


import ai.steeringbehaviors.Behavior;
import ai.steeringbehaviors.SteeringOutput;
import ai.steeringbehaviors.collision.CollisionDetector;
import ai.steeringbehaviors.collision.ObstacleAvoidance;
import ai.steeringbehaviors.grouping.BlendedSteering;
import ai.steeringbehaviors.grouping.PrioritySteering;
import ai.steeringbehaviors.seekandflee.Wander;
import gameObjects.GameObject;
import gameObjects.collisions.CollisionDetection;
import tiles.TileManager;

/**
 * The Class WanderSearch. This action lets the agent wander around while avoid obstacles 
 */
public class WanderSearch extends Action {


	/** The wander. */
	Wander wander;
	
	/** The wander priority. */
	PrioritySteering wanderPriority;
	
	/** The objects. */
	List<GameObject> objects;
	
	/** The found object. */
	GameObject foundObject;

	/** Distance to stop wandering and move towards object */
	float radius;

	/** The w att. */
	Behavior wAtt;
	
	/** GameObject character*/
	GameObject character;
	
	/** Change character speed to match action */
	float speed;
	
	/** TileManager */
	TileManager tm;

	/**
	 * Search for object.
	 *
	 * @param character the character
	 * @param wanderAttributes - Attributes need to includes: maxAcceleration, maxRotation,
	 * targetRadius, wanderRadius, slowRadius, wanderRate, wanderOffset, maxAngularAcceleration,
 	 * collisionRadius, lookAhead, avoidDistance,
	 * @param speed - this sets the characters speed
	 * @param tm - the current Tilemanager
	 */
	public WanderSearch(GameObject character, Behavior wanderAttributes, float speed,
			TileManager tm){
		this.wAtt = wanderAttributes;
		this.character = character;
		this.speed = speed;
		this.tm = tm;
		createWanderPriority();
	}
	
	/**
	 * Creates the wander priority.
	 */
	public void createWanderPriority() {
		wander = new Wander(character.getKinematic(),character.getKinematic(),
				wAtt.getTargetRadius(), wAtt.getSlowRadius(), wAtt.getWanderRate(),
				wAtt.getWanderOffset(),wAtt.getWanderRadius() ,wAtt.getMaxAcceleration(),
				wAtt.getMaxAngularAcceleration(),wAtt.getMaxRotation());
		
		ObstacleAvoidance avoid = new ObstacleAvoidance(character.getKinematic(),
				wAtt.getAvoidDistance(),wAtt.getLookAhead(),wAtt.getMaxAcceleration() * 5,
				new CollisionDetector(character,new CollisionDetection(tm)));
		
		BlendedSteering wanderBlended = new BlendedSteering(wAtt.getMaxAcceleration(),
				wAtt.getMaxRotation());
		wanderBlended.addBehavior(wander, 1.0f);
		
		BlendedSteering avoidanceBlended = new BlendedSteering(wAtt.getMaxAcceleration(),
				wAtt.getMaxRotation());
		avoidanceBlended.addBehavior(avoid,1.0f);
		
		BlendedSteering[] wanderGroup = new BlendedSteering[2];
		wanderGroup[0] = avoidanceBlended;
		wanderGroup[1] = wanderBlended;
		
		wanderPriority = new PrioritySteering(wanderGroup);
		
		

	}
	
	@Override
	public void performAction(float elapsedTime) {
		character.setSpeed(speed);

		SteeringOutput steeringOutput = wanderPriority.getSteering();
		if (character.getKinematic().getVelocity().getLength() > 0) {
			character.getKinematic().setOrientation(
					(float) -Math.atan2(character.getKinematic().getVelocity().getY(),
							character.getKinematic().getVelocity().getX()));
		}
		//otherwise set the targets orientation based on the velocity
		character.objectMovement(elapsedTime, steeringOutput);
	}
}
