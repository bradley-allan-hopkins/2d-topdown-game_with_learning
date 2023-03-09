/**
 * Title: PlayerClicksOnAttack.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 16, 2022
 */
package gameObjects.characters.fighters.player;

import gameObjects.characters.fighters.EnemyFighter;
import gameObjects.characters.fighters.Fighter;
import state.states.FightState;
import tiles.blocks.ButtonBlock;

/**
 * The Class PlayerClicksOnAttack. This condition returns true if player clicks on attack 
 * button
 */
public class PlayerClicksOnAttack extends PlayerClicksOnButton{


	@Override
	public boolean test() {
		if (isButton()) {
			int buttonNum = ((ButtonBlock) b).getButtonNum();
			//if attack button is pushed
			if (buttonNum == 0) {
				//if a enemy is selected
				for (Fighter fighter :FightState.fighters) {
					if (fighter instanceof EnemyFighter) {
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
