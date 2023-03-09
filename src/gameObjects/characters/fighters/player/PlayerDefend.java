/**
 * Title: PlayerDefend.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters.player;

import java.util.ArrayList;

import gameObjects.GameObject;
import gameObjects.characters.Entity;
import gameObjects.characters.fighters.FightSceneAction;
import gameObjects.characters.fighters.Fighter;
import state.states.FightState;
import tiles.blocks.ButtonBlock;

/**
 * The Class PlayerDefend. This action sets the fighter's defend to true and sets animation 
 * to show shield
 */
public class PlayerDefend extends FightSceneAction{


	/**
	 * Player defend.
	 *
	 * @param obj the obj
	 */
	public PlayerDefend(GameObject obj) {
		super(obj);
	}

	@Override
	public void performAction(float elapsedTime) {
		int animateSpeed = 15;
		int length = 8;

		if (!playedAnimation) {	
			if (((Entity)obj).getAnimation().getDelay() == -1) {
				((Entity)obj).setAnimation(0, animateSpeed, length);
			}
			((Entity)obj).getAnimation().update();
			if (((Entity)obj).getAnimation().getFrame() == 2) {
				((Entity)obj).setAnimation(0,-1, length);
				((Entity)obj).getAnimation().setFrame(1);
				
				//find selected fighter
				Fighter selected = null;
				System.out.println(FightState.fighters.size());
				for (Fighter fighter : FightState.fighters) {
					if (fighter.getSelected()) {
						selected = fighter;
						break;
					}
				}
				if (selected == null) {
					System.out.println("Selected is null");//error message
					return;
				}
				
				//animation has played 
				playedAnimation = true;
				((Fighter)obj).setWhoAttacked(new ArrayList<Fighter>());
				
				
				((ButtonBlock)FightState.buttons[1]).setPressed(false);
				
				selected.setDefend(true);
				selected.setSelected(false);
			}
		}
	}
}
