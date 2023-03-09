/**
 * Title: Align.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Jan. 27, 2022
 * *derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */

package ai.steeringbehaviors.orientation;

import ai.steeringbehaviors.Behaviors;
import ai.steeringbehaviors.Kinematic;
import ai.steeringbehaviors.SteeringOutput;

/**
 * The Class Align that is used to align a agents orientation with its current direction of 
 * velocity
 */
public class Align implements Behaviors{
	
	/** The character. */
	protected Kinematic character;
	
	/** The target. */
	protected Kinematic target;
	
	/** The max angular acceleration. */
	float maxAngularAcceleration;
	
	/** The max rotation. */
	float maxRotation;
	
	/** Radius for arriving at target */
	float targetRadius;
	
	/** Radius to begin slowing down */
	float slowRadius;
	
	/** The time to achieve target speed. */
	float timeToTarget = 0.1f;
	
	
	/**
	 * Align. 
	 *
	 * @param character - Kinematic
	 * @param target - Kinematic
	 * @param targetRadius - float - Radius for arriving at target
	 * @param slowRadius - float - Radius to begin slowing down
	 * @param maxAngularAcceleration - float
	 * @param maxRotation - float
	 */
	public Align(Kinematic character, Kinematic target,float targetRadius, 
			float slowRadius,float maxAngularAcceleration,float maxRotation){
		this.character = character;
		this.target = target;
		this.targetRadius = targetRadius;
		this.slowRadius = slowRadius;
		this.maxAngularAcceleration = maxAngularAcceleration;
		this.maxRotation = maxRotation;
	}
	
	@Override
	public SteeringOutput getSteering() {
		SteeringOutput result = new SteeringOutput();
		
		//get direction to the target
		float rotation = target.getOrientation() - character.getOrientation();
		
		//map the result to the (-pi,pi) interval
		rotation = Kinematic.mapToRange(rotation);
		
		float rotationSize = Math.abs(rotation);
		
		
		//check if character is at target 
		if (rotationSize < targetRadius) {
			return result;
		}

		float targetRotation;
		//if outside of slow radius 
		if (rotationSize > slowRadius)
			targetRotation = maxRotation;
		//if inside scale rotation
		else 
			targetRotation = maxRotation * (rotationSize/slowRadius);


		//the final target rotation combines speed (already in the variable) and direction
		targetRotation *= rotation/rotationSize;

		//acceleration trues to get to the target rotation
		result.setAngular(targetRotation - character.getRotation());

		result.setAngular(result.getAngular() / timeToTarget);

		//check if acceleration is too great
		float angularAcceleration = Math.abs(result.getAngular());
		
		if (angularAcceleration > maxAngularAcceleration) {
			result.setAngular(result.getAngular() / angularAcceleration);
			result.setAngular(result.getAngular() * maxAngularAcceleration);
		}
		
		return result;
	}
}
