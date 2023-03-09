/**
 * Title: NextTurn.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 16, 2022
 */
package gameObjects.characters.fighters;

import ai.statemachine.actions.Action;
import state.states.FightState;

/**
 * The Class NextTurn. This action calls the method in FightState to switch to the next fighters
 * state machine 
 */
public class NextTurn extends Action {

	@Override
	public void performAction(float elapsedTime) {
		//if last turn is not -1 then enemy died during attack back 
		if (FightState.lastTurn != -1)FightState.resetToLastTurn();
		else FightState.nextTurn();
		

	}

}
