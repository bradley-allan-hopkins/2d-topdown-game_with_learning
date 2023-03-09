/**
 * Title: ChooseAttack.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package ai.learning.q.action;

import gameObjects.GameObject;
import gameObjects.characters.fighters.minotaur.Choose;
import state.states.FightState;
/**
 * The Class ChooseAttack. This action selects the attack in Choose and selects the player 
 * to attack 
 */
public class ChooseAttack extends Choose{

	
	/**
	 * Choose attack.
	 *
	 * @param obj the obj
	 */
	public ChooseAttack(GameObject obj) {
		super(obj);

	}

	@Override
	public void performAction(float elapsedTime) {
		choice = CHOICE.Attack;
		FightState.fighters.get(0).setSelected(true);
	}

}
