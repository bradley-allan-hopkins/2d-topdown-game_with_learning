/**
 * Title: HealthZeroCondition.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters;

import ai.statemachine.conditions.Condition;
import gameObjects.GameObject;

/**
 * The Class HealthZeroCondition. This condition returns true if the fighter health is zero
 */
public class HealthZeroCondition extends Condition {

	/** The obj. */
	GameObject obj;
	
	/**
	 * Health zero condition.
	 *
	 * @param obj the obj
	 */
	public HealthZeroCondition(GameObject obj) {
		this.obj = obj;
	}
	

	@Override
	public boolean test() {
		if (((Fighter)obj).getHealth() <= 0)return true;
		return false;
	}

}
