/**
 * Title: EnemyChoosesDefendCondition.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters.minotaur;

import gameObjects.characters.fighters.minotaur.Choose.CHOICE;
// TODO: Auto-generated Javadoc
/**
 * The Class EnemyChoosesDefendCondition. This condition returns true if the computer player 
 * chooses defend
 */
public class EnemyChoosesDefendCondition extends EnemyChoosesCondition {

	

	/**
	 * Enemy chooses defend condition.
	 *
	 * @param choose the choose
	 */
	public EnemyChoosesDefendCondition(Choose choose) {
		super(choose);
	}

	@Override
	public boolean test() {
		if (Choose.choice == CHOICE.Defend) {
			Choose.choice = CHOICE.None;
			return true;
		}
		return false;
	}

}
