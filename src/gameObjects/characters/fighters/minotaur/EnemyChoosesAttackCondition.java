/**
 * Title: EnemyChoosesAttackCondition.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package gameObjects.characters.fighters.minotaur;

import gameObjects.characters.fighters.minotaur.Choose.CHOICE;

/**
 * The Class EnemyChoosesAttackCondition. This condition returns true if the computer player
 * picks the attack
 */
public class EnemyChoosesAttackCondition extends EnemyChoosesCondition {




	/**
	 * Enemy chooses attack condition.
	 *
	 * @param choose the choose
	 */
	public EnemyChoosesAttackCondition(Choose choose) {
		super(choose);

	}


	@Override
	public boolean test() {
		if (Choose.choice == CHOICE.Attack) {
			Choose.choice = CHOICE.None;
			return true;
		}
		return false;
	}

}
