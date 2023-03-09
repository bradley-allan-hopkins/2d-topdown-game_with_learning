/**
 * Title: PlayerDied.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package gameObjects.characters.fighters.player;


import gameObjects.GameObject;
import gameObjects.characters.Entity;
import gameObjects.characters.fighters.FightSceneAction;
import gameObjects.characters.fighters.Fighter;

/**
 * The Class PlayerDied. This action plays the dead animation for the player and sets the 
 * dead to true
 */
public class PlayerDied extends FightSceneAction {

	/**
	 * Player died.
	 *
	 * @param obj the obj
	 */
	public PlayerDied(GameObject obj) {
		super(obj);
	}

	@Override
	public void performAction(float elapsedTime) {
		if (((Entity)obj).getAnimation().getDelay() == -1) {
			((Entity)obj).setAnimation(4,10, 4);
		}
		((Entity)obj).getAnimation().update();
		if (((Entity)obj).getAnimation().getFrame() == 1) {
			((Entity)obj).getAnimation().setFrame(1);
			((Fighter)obj).setDead(true);
		}
	}

}
