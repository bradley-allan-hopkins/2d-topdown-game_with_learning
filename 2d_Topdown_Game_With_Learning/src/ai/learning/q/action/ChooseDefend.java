/**
 * Title: ChooseDefend.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package ai.learning.q.action;

import gameObjects.GameObject;
import gameObjects.characters.fighters.minotaur.Choose;


/**
 * The Class ChooseDefend. This action selects the defend in Choose and selects the fighter
 * that is defending
 */
public class ChooseDefend extends Choose {

	/**
	 * Choose defend.
	 *
	 * @param obj the obj
	 */
	public ChooseDefend(GameObject obj) {
		super(obj);
	}

	@Override
	public void performAction(float elapsedTime) {
		choice = CHOICE.Defend;
		fighter.setSelected(true);
	}
}
