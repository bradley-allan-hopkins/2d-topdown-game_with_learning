/**
 * Title: Separation.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Jan. 31, 2022
 * *derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 * @Revised 
 * Mar 29,2022 - changed the List<Kinematic> to List<GameObject>
 */
package ai.steeringbehaviors;

import java.util.List;

import gameObjects.GameObject;
import math.Vector2;

/**
 * The Class Separation. This object applies a velocity away from other agents. Each other 
 * target is cycled through and if the agent is within a threshold then a vector is in the 
 * opposite direction is applied. 
 */
public class Separation implements Behaviors{

	/** The character. */
	Kinematic character;
	
	/** The max acceleration. */
	float maxAcceleration;
	
	/** List of all potential targets. */
	List<GameObject> targets;
	
	/** The threshold to take action */
	float threshold; 
	
	/** constant coefficent of decay for the inverse square law */
	float decayCoefficient; 
	
	/**
	 * Constructor
	 *
	 * @param character -Kinematic
	 * @param targets - List of all targets
	 * @param threshold - distance at which to apply a separation force
	 * @param maxAcceleration - float - 
	 * @param decayCoefficient the decay coefficient
	 */
	public Separation(Kinematic character, List<GameObject> targets, float threshold, 
			float maxAcceleration,float decayCoefficient) {
		this.targets = targets;
		this.character = character;
		this.threshold = threshold;
		this.maxAcceleration = maxAcceleration;
		this.decayCoefficient = decayCoefficient;
	}

	
	@Override
	public SteeringOutput getSteering() {
		SteeringOutput result = new SteeringOutput();
		
		//loop through each target
		if (targets != null) {
			for (int i = 0; i < targets.size(); i++) {
				if (targets.get(i) != null) {
					//Vector2 direction = targets.get(i).getPosition().subtractTwoVectors(
					//		character.getPosition());
					Vector2 direction = targets.get(i).getPosition().subtractTwoVectors(
							character.getPosition());
					float distance = direction.getLength();
					if (distance == 0)continue;
					if (distance < threshold) {
						//calculate strength of repulsion
						float strength = Math.min(decayCoefficient/(distance * distance),
								maxAcceleration);
			
						//add acceleration
						direction.normalize();
						result.linear.addToVector(direction.scaler(strength));
					}
				}
			}
		}
		return result;
	}
	
}
