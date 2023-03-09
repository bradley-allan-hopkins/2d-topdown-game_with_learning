/**
 * Title: EnemyAttackedWhileDefendingCondition.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters;

import ai.statemachine.conditions.Condition;
import gameObjects.GameObject;

/**
 * The Class EnemyAttackedWhileDefendingCondition. This condition returns true if 
 * the fighter was attacked and is defending 
 */
public class AttackedWhileDefendingCondition extends Condition {

	/** The fighter. */
	Fighter fighter;
	
	/**
	 * Enemy attacked while defending condition.
	 *
	 * @param fighter the fighter
	 */
	public AttackedWhileDefendingCondition(GameObject fighter) {
		this.fighter = ((Fighter)fighter);
	}
	
	/**
	 * Test.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean test() {
		return fighter.getWhoAttacked() != null && fighter.getWhoAttacked().size() > 0 && 
				fighter.isDefend();
	}

}
