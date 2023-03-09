/**
 * Title: Flee.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Jan. 28, 2022
 */
package ai.steeringbehaviors.seekandflee;

import ai.steeringbehaviors.Behaviors;
import ai.steeringbehaviors.Kinematic;
import ai.steeringbehaviors.SteeringOutput;

/**
 * Flee sets the velocity of the agent to the opposite direction of the current target.
 */
public class Flee implements Behaviors{

	/** The character. */
	Kinematic character;
	
	/** The target. */
	Kinematic target;
	
	/** The max acceleration. */
	float maxAcceleration;
	
	/**
	 * Flee
	 *
	 * @param character - Kinematic
	 * @param target - Kinematic
	 * @param maxAcceleration - float
	 */
	public Flee(Kinematic character, Kinematic target, float maxAcceleration){
		this.character = character;
		this.target = target;
		this.maxAcceleration = maxAcceleration;
	}
	
	@Override
	public SteeringOutput getSteering() {
		
		SteeringOutput result = new SteeringOutput();
		
		//find direction of seeker from target
		result.setLinear(character.getPosition().subtractTwoVectors(target.getPosition()));
		
		//velocity full speed away from target
		result.getLinear().normalize();
		result.getLinear().scaleVector(maxAcceleration);

		result.setAngular(0); //no angular movement through this behavior
		
		return result;
	}
	
}

