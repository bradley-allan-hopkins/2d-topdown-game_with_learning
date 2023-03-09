/**
 * Title: Arrive.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Jan. 27, 2022
 * *derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.steeringbehaviors.velocitymatch;

import ai.steeringbehaviors.Behaviors;
import ai.steeringbehaviors.Kinematic;
import ai.steeringbehaviors.SteeringOutput;
import math.Vector2;

/**
 * The Class Arrive that creates a object that is used for AI agents. The agent will
 * travel to a target at full speed until the agent is within a certain distance, at which
 * time the agent will slow down.  
 */
public class Arrive implements Behaviors{

	/** The character. */
	Kinematic arriveCharacter;
	
	/** The target. */
	Kinematic arriveTarget;
	
	/** The max speed. */
	float maxSpeed;
	
	/** The max acceleration. */
	float maxAcceleration;
	
	/** The target radius. */
	//radius for target
	float targetRadius; // book uses 1.5f
	
	/** The slow radius. */
	float slowRadius; //book uses 5f
	
	/** The time to target. */
	float timeToTarget = 0.1f;
	
	
	/**
	 * Arrive
	 *
	 * @param character - Kinematic
	 * @param target - Kinematic
	 * @param targetRadius - float
	 * @param slowRadius - float
	 * @param maxSpeed - float
	 * @param maxAcceleration - float
	 */
	public Arrive(Kinematic character, Kinematic target,float targetRadius, 
			float slowRadius, float maxSpeed,float maxAcceleration){
		this.arriveCharacter = character;
		this.arriveTarget = target;
		this.targetRadius = targetRadius;
		this.slowRadius = slowRadius;
		this.maxSpeed = maxSpeed;
		this.maxAcceleration = maxAcceleration;
	}
	

	@Override
	public SteeringOutput getSteering() {
		
		SteeringOutput result = new SteeringOutput();
		float targetSpeed;
		
		//find direction of target from seeker
		Vector2 direction = arriveTarget.getPosition().subtractTwoVectors(arriveCharacter.getPosition());
		float distance = direction.getLength();
		
		//if character reached target
		if (distance < targetRadius) 
			return null;
			//return result;
		
		//if seeker is outside of slow radius speed up
		if (distance > slowRadius)
			targetSpeed = maxSpeed;
		else //slow the character down
			targetSpeed = maxSpeed * (distance / slowRadius);
		
		Vector2 targetVelocity = direction;
		targetVelocity.normalize();
		targetVelocity.scaleVector(targetSpeed);
				
		
		//acceleration 
		result.setLinear(targetVelocity.subtractTwoVectors(arriveCharacter.getVelocity()));
		result.getLinear().divideFromVector(timeToTarget);
		
		//check acceleration
		if (result.getLinear().getLength() > maxAcceleration) {
			result.getLinear().normalize();
			result.getLinear().scaleVector(maxAcceleration);
		}
		
		result.setAngular(0);
		return result;
	}

}
