/**
 * Title: LookWhereYourGoing.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Jan. 31, 2022
 * *derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */

package ai.steeringbehaviors.orientation;

import ai.steeringbehaviors.Kinematic;
import ai.steeringbehaviors.SteeringOutput;
import math.Vector2;

/**
 * The Class LookWhereYourGoing. This object directly turns the agent to face the current
 * velocity that the agent is traveling. 
 */
public class LookWhereYourGoing extends Align {

	
	
	/**
	 * Constructor
	 *
	 * @param character the character
	 * @param target the target
	 * @param targetRadius the target radius
	 * @param slowRadius the slow radius
	 * @param maxAngularAcceleration the max angular acceleration
	 * @param maxRotation the max rotation
	 */
	public LookWhereYourGoing(Kinematic character, Kinematic target, float targetRadius,
			float slowRadius,float maxAngularAcceleration,float maxRotation) {
		super(character, target, targetRadius, slowRadius, maxAngularAcceleration, 
				maxRotation);
	}
	

	@Override
	public SteeringOutput getSteering() {
		//check for zero direction
		Vector2 velocity = character.getVelocity();
		if (velocity.getLength() == 0) {
			return null;
		}
		//otherwise set the targets orientation based on the velocity
		character.setOrientation((float) -Math.atan2(velocity.getY(), velocity.getX()));
		
		//delgate to align
		return super.getSteering();
	}

}
