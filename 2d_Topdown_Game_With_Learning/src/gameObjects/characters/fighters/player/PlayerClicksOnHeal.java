/**
 * Title: PlayerClicksOnHeal.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 16, 2022
 */
package gameObjects.characters.fighters.player;

import gameObjects.characters.fighters.Fighter;
import state.states.FightState;
import tiles.blocks.ButtonBlock;

/**
 * The Class PlayerClicksOnHeal. This condition returns true if player clicks on one of the
 * health buttons
 */
public class PlayerClicksOnHeal extends PlayerClicksOnButton {

	@Override
	public boolean test() {
		if (isButton()) {
			int buttonNum = ((ButtonBlock) b).getButtonNum();
			//if attack button is pushed
			if (buttonNum == 2 || buttonNum == 3 || buttonNum == 4) {
				//if a enemy is selected
				for (Fighter fighter :FightState.fighters) {
					if (fighter instanceof PlayerFighter) {
						if (fighter.getSelected()) {
							((ButtonBlock)b).setTopLayer(null);
							return true;
						}
					}
				}
			}
		}
		return false;
	}

}
