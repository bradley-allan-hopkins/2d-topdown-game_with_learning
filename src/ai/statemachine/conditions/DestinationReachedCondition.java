/**
 * Title: DestinationReachedCondition.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 23, 2022
 */
package ai.statemachine.conditions;

import ai.statemachine.actions.TravelToTarget;

/**
 * The Class DestinationReachedCondition. Returns true if the target is reached. 
 */
public class DestinationReachedCondition extends Condition {

	/** The travel. */
	TravelToTarget travel;
	
	/**
	 * Destination reached condition.
	 *
	 * @param travel the travel
	 */
	public DestinationReachedCondition(TravelToTarget travel) {
		this.travel = travel;
	}
	
	@Override
	public boolean test() {
		if (travel.isReached()) {
			travel.resetReached();
			return true;
		}
		return false;
	}

}
