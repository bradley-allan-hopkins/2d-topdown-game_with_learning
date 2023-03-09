/**
 * Title: EnemyChoosesCondition.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters.minotaur;

import ai.statemachine.conditions.Condition;

/**
 * The Class EnemyChoosesCondition. Abstract class for all of the enemy chooses conditions
 */
public abstract class EnemyChoosesCondition extends Condition{

	
	/** The choose. */
	Choose choose;
	
	/**
	 * Enemy chooses attack condition.
	 *
	 * @param choose the choose
	 */
	public EnemyChoosesCondition(Choose choose) {
		this.choose = choose;
	}
	
}
