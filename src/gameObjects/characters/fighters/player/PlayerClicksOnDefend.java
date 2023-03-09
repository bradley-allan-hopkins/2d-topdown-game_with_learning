/**
 * Title: PlayerClicksOnDefend.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 16, 2022
 */
package gameObjects.characters.fighters.player;

import gameObjects.characters.fighters.Fighter;
import state.states.FightState;
import tiles.blocks.ButtonBlock;

/**
 * The Class PlayerClicksOnDefend. This condition returns true if player clicks on the 
 * defend button
 */
public class PlayerClicksOnDefend extends PlayerClicksOnButton {

	/**
	 * Test.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean test() {
		if (isButton()) {
			int buttonNum = ((ButtonBlock)b).getButtonNum();
			//if defend button is pushed
			if (buttonNum == 1) {
				//if a enemy is selected
				for (Fighter fighter :FightState.fighters) {
					if (fighter instanceof PlayerFighter) {
						if (fighter.getSelected()) {
							((ButtonBlock)b).setPressed(true);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
