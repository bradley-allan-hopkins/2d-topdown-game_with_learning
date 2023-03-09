/**
 * Title: EnemyChoosesHealCondition.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters.minotaur;

import gameObjects.characters.fighters.minotaur.Choose.CHOICE;
/**
 * The Class EnemyChoosesHealCondition. This condition returns true if the computer player 
 * chooses to heal
 */
public class EnemyChoosesHealCondition extends EnemyChoosesCondition {

	/**
	 * Enemy chooses heal condition.
	 *
	 * @param choose the choose
	 */
	public EnemyChoosesHealCondition(Choose choose) {
		super(choose);
	}


	@Override
	public boolean test() {
		if (Choose.choice == CHOICE.Heal) {
			Choose.choice = CHOICE.None;
			return true;
		}
		return false;
	}

}
