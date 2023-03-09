/**
 * Title: Seek.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Jan. 31, 2022
 */

package ai.steeringbehaviors.seekandflee;

import ai.steeringbehaviors.Behaviors;
import ai.steeringbehaviors.Kinematic;
import ai.steeringbehaviors.SteeringOutput;

/**
 * The Class Seek. The object causes the agent to travel towards the target at max speed
 */
public class Seek implements Behaviors{
	
	/** The character. */
	protected Kinematic character;
	
	/** The target. */
	protected Kinematic target;
	
	/** The max acceleration. */
	float maxAcceleration;
	
	/**
	 * Constructor
	 *
	 * @param character - Kinematic 
	 * @param target - Kinematic - the target Kinematic
	 * @param maxAcceleration - max acceleration of the agent
	 */
	public Seek(Kinematic character, Kinematic target, float maxAcceleration){
		this.character = character;
		this.target = target;
		this.maxAcceleration = maxAcceleration;
	}
	

	@Override
	public SteeringOutput getSteering() {
		SteeringOutput result = new SteeringOutput();
		
		//find direction of target from seeker
		result.setLinear(target.getPosition().subtractTwoVectors(character.getPosition()));
		
		//velocity full speed to target
		result.getLinear().normalize();
		result.getLinear().scaleVector(maxAcceleration);
		
		result.setAngular(0);

		return result;
	}

}
