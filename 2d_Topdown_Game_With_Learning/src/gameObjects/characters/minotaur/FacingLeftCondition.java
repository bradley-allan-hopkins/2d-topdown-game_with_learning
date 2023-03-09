/**
 * Title: FacingLeftCondition.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 16, 2022
 */
package gameObjects.characters.minotaur;

import ai.statemachine.conditions.Condition;
import gameObjects.GameObject;

/**
 * The Class FacingLeft. This condition will return true if the character is facing left
 */
public class FacingLeftCondition extends Condition {

	/** The obj. */
	GameObject obj;
	
	/**
	 * Facing left.
	 *
	 * @param obj the obj
	 */
	public FacingLeftCondition(GameObject obj) {
		this.obj = obj;
	}

	@Override
	public boolean test() {
		if (obj.getKinematic().getOrientation() <= ((5 * Math.PI)/4) && 
				obj.getKinematic().getOrientation() >= (3 * Math.PI/4) 
				||
				obj.getKinematic().getOrientation() <= (-3 * Math.PI)/4 &&
				obj.getKinematic().getOrientation() >= (- 5 * Math.PI/4))
		{
			return true;
		}
		return false;
	}

}
