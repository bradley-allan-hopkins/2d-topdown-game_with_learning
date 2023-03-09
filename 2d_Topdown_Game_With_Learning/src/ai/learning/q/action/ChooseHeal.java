/**
 * Title: ChooseHeal.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package ai.learning.q.action;

import gameObjects.GameObject;
import gameObjects.characters.fighters.minotaur.Choose;

/**
 * The Class ChooseHeal. This action selects the heal from Choose and selects the fighter to 
 * heal
 */
public class ChooseHeal extends Choose {

	/**
	 * Choose heal.
	 *
	 * @param obj the obj
	 */
	public ChooseHeal(GameObject obj) {
		super(obj);
	}
	

	@Override
	public void performAction(float elapsedTime) {
		choice = CHOICE.Heal;
		fighter.setSelected(true);
	}

}
