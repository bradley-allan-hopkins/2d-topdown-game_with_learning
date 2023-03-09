/**
 * Title: Face.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Jan. 28, 2022
 * *derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.steeringbehaviors.orientation;

import ai.steeringbehaviors.Kinematic;
import ai.steeringbehaviors.SteeringOutput;
import math.Vector2;

/**
 * This object will turn the targets orientation to match the current velocity. The new 
 * Orientation is passed to Align which will allow the agent to turn 
 */
public class Face extends Align {
	
	/**
	 * Face
	 *
	 * @param character - Kinematic
	 * @param target - Kinematic
	 * @param targetRadius - Radius for arriving at target
	 * @param slowRadius - float - Radius to begin slowing down
	 * @param maxAngularAcceleration - float 
	 * @param maxRotation the max rotation
	 */
	public Face(Kinematic character, Kinematic target, float targetRadius, float slowRadius,
			float maxAngularAcceleration,float maxRotation) {
		super(character, target, targetRadius, slowRadius,
				maxAngularAcceleration,maxRotation);
	}
	
	@Override
	public SteeringOutput getSteering() {
		//direction
		
		Vector2 direction = target.getPosition().subtractTwoVectors(character.getPosition());

		if (direction.getLength() > 0) {
			Kinematic explicitTarget = new Kinematic(target.getPosition().getX(),
					target.getPosition().getY(),0);
			explicitTarget.setOrientation((float) Math.atan2(direction.getY(), direction.getX()));
			super.target = explicitTarget;
		}
		return super.getSteering();
	}
}
