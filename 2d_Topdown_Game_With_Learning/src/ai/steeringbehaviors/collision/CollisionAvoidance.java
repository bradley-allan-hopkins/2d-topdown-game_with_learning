/**
 * Title: CollisionAvoidance.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 1, 2022
 * *derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */

package ai.steeringbehaviors.collision;

import java.util.List;

import ai.steeringbehaviors.Behaviors;
import ai.steeringbehaviors.Kinematic;
import ai.steeringbehaviors.SteeringOutput;
import gameObjects.GameObject;
import gameObjects.collisions.CollisionDetection;
import math.Vector2;

/**
 * The Class CollisionAvoidance. This class allows the agent to "see" a object that is in 
 * the agents view. First the closest target is found and then the object checks if the 
 * target is within the cone. If the character is going to collide the object calculates 
 * a velocity that will try to avoid the target
 */
public class CollisionAvoidance implements Behaviors {
	
	
	/** The characters Kinematic */
	Kinematic character;
	
	/** Max acceleration of the character */
	float maxAcceleration;
	
	/** Collision radius */
	float collisionRadius;
	
	/** The object that is testing for a collision*/
	GameObject obj;
	
	/** List of GameObjects to avoid */
	List<GameObject> list;
	
	/**
	 * Collision avoidance.
	 * @param obj - the GameObject doing the checks 
	 * @param character - the GameObjects kinematic
	 * @param collisionRadius - radius to measure at what distance to fix
	 * @param maxAcceleration - max acceleration needed to fix
	 */
	public CollisionAvoidance(GameObject obj, Kinematic character, 
			float collisionRadius, float maxAcceleration) {
		this.character = character;
		this.maxAcceleration = maxAcceleration;
		this.collisionRadius = collisionRadius;
		this.obj = obj;
	}
	
	@Override
	public SteeringOutput getSteering() {
		//Step 1: find target closest to collision
		
		float shortestTime = (float) Double.POSITIVE_INFINITY;
		Kinematic firstTarget = null;
		float firstMinSeparation = 0;
		float firstDistance = 0;
		Vector2 firstRelativePos = new Vector2(0,0);
		Vector2 firstRelativeVel = new Vector2(0,0);
			
		//get a list of all characters currently in the game
		if (list == null) {
			System.out.println("List is null");
			 list = CollisionDetection.getPossibleCharacterCollisions(obj, collisionRadius);
		}
		
		if (list != null) {		
			//loop through all targets
			for (int i = 0; i < list.size(); i++) {
				
				//double check as target could have been removed from game 
				if (list.get(i) != null) {
					Vector2 relativePos = list.get(i).getKinematic().getPosition().subtractTwoVectors(
							character.getPosition());
					Vector2 relativeVel = list.get(i).getKinematic().getVelocity().subtractTwoVectors(
							character.getVelocity());
					float relativeSpeed = relativeVel.getLength();
					float timeToCollision = relativePos.dot(relativeVel)/
							(relativeSpeed * relativeSpeed);
					//System.out.println("TIME TO COLLISION:" + timeToCollision);
							
					//check to see if actually a collision
					float distance = relativePos.getLength();
					float minSeparation = distance - relativeSpeed * timeToCollision;
					//System.out.println("MinSeparation: " + minSeparation);
					if (minSeparation > 2 * collisionRadius) 
						continue;
					
					//check if it is the shortest
					if (timeToCollision > 0 && timeToCollision < shortestTime) {
						//System.out.println("ENTERED NEXT IF");
						//store the data
						shortestTime = timeToCollision;
						firstTarget = list.get(i).getKinematic();
						firstMinSeparation = minSeparation;
						firstDistance = distance;
						firstRelativePos = relativePos;
						firstRelativeVel = relativeVel;
					}
				}
			}
			//Step 2: Calculate the steering for closest target
			//no target
			if (firstTarget == null) return null;
			//System.out.println("Past hurdle");
			//if character is going to hit exactly or if character is already colliding then 
			//use current position
			Vector2 relativePos;
			if (firstMinSeparation <= 0 || firstDistance < 2 * collisionRadius)
			{
				//System.out.println("first Target: " + firstTarget.getPosition());
				//System.out.println("character Position: " + character.getPosition());
				
				relativePos = firstTarget.getPosition().subtractTwoVectors(character.getPosition());
				//System.out.println("RELATIVE POS: " + relativePos);
			}
			else 
				relativePos = firstRelativePos.addTwoVectors(firstRelativeVel).scaler(shortestTime);
			
			//avoid target
			relativePos.normalize();
			SteeringOutput result = new SteeringOutput();
			result.setLinear(relativePos.scaler(maxAcceleration));
			result.setAngular(0);
			return result;
		}
		return null;
	}
	
	/**
	 * sets list of targets 
	 * @param list - list of targets
	 */
	public void setTargets(List<GameObject> list) {
		this.list = list;
	}
}
