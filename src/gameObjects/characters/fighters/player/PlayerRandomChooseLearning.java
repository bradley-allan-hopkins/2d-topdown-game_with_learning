/**
 * Title: PlayerRandomChooseLearning.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package gameObjects.characters.fighters.player;

import ai.statemachine.actions.Action;
import game.MouseHandler;
import state.states.FightState;
import utils.Random;

/**
 * The Class PlayerRandomChooseLearning. This Action allows the play to randomly attack or 
 * defend. This will allow the computer to learn different states while not playing the game
 * directly
 */
public class PlayerRandomChooseLearning extends Action {


	@Override
	public void performAction(float elapsedTime) {
		int random = Random.getRndInteger(0, FightState.fighters.size()-1);

		//attack
		if (random > 0) {
			FightState.fighters.get(random).setSelected(true);
			MouseHandler.setMouse(61, 743);
			MouseHandler.setMouseButton(1);

		}
		//defend
		else {
			FightState.fighters.get(0).setSelected(true);
			MouseHandler.setMouse(74, 810);
			MouseHandler.setMouseButton(1);
		}
	}

}
