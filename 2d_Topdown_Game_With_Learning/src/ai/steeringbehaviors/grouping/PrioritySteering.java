/**
 * Title: PrioritySteering.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 10, 2022
 * *derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.steeringbehaviors.grouping;

import ai.steeringbehaviors.Behaviors;
import ai.steeringbehaviors.SteeringOutput;

// TODO: Auto-generated Javadoc
/**
 * The Class PrioritySteering. This class checks each group of BlendedSteering behaviors. 
 * Starting with the first group the object will test if any steering behavior exists. If 
 * the first group returns a behavior than the agent will execute that blended group only. 
 * The next group in succession is only checked if the prior group fails to return a steering 
 * behavior.
 */
public class PrioritySteering implements Behaviors{

	/** The Constant EPSILON. */
	static final float EPSILON = 0.0001f;
	
	/** The groups. */
	BlendedSteering[] groups;
	
	/**
	 * Priority steering.
	 *
	 * @param groups the groups
	 */
	public PrioritySteering(BlendedSteering[] groups) {
		this.groups = groups;
	}

	@Override
	public SteeringOutput getSteering() {
		SteeringOutput result = new SteeringOutput();
		for (BlendedSteering group : groups) {
			result = group.getSteering();
			
			//check if we are above the threshold
			if (result.getLinear().getLength() > EPSILON || Math.abs(result.getAngular()) > EPSILON) {
				//System.out.println(group);
				return result;
			}
		}
		//if no steering output returned a steering behavior that modifies the velocity or 
		//angle significantly than return the last blended steering behavior
		return result;
	}
	

}
