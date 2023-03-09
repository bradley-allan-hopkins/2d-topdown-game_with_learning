/**
 * Title: MinotaurDead.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters.minotaur;

import ai.statemachine.actions.Action;
import gameObjects.GameObject;
import gameObjects.characters.Entity;
import gameObjects.characters.fighters.Fighter;
import gameObjects.characters.fighters.NextTurn;
import state.states.FightState;

/**
 * The Class MinotaurDead. This action plays the animation when the minotaur dies 
 */
public class MinotaurDead extends Action {


	/** The obj. */
	GameObject obj;
	
	/** The played animation. */
	boolean playedAnimation = false;
	
	/**
	 * Minotaur dead.
	 *
	 * @param obj the obj
	 */
	public MinotaurDead(GameObject obj) {
		this.obj = obj;
	}
	

	@Override
	public void performAction(float elapsedTime) {
		int animateSpeed = 15;
		int length = 6;
		int animation = 19;

		if (!playedAnimation) {	
			if (((Entity)obj).getCurrentAnimation() != animation ) {
				((Entity)obj).setAnimation(animation,((Entity)obj).getSpriteSheet().
						getSpriteArray(animation), animateSpeed);
				((Entity)obj).getAnimation().setLength(length);
			}
			else ((Entity)obj).setDelay(animateSpeed);
	
			//check if animation is done 
			if (((Entity)obj).getAnimation().getFrame() == length - 1) {
				((Entity)obj).setAnimation(animation,((Entity)obj).getSpriteSheet().
						getSpriteArray(animation), -1);
				((Entity)obj).getAnimation().setFrame(5);
				playedAnimation = true;
				((Fighter)obj).setDead(true);
				FightState.fighters.remove(obj);
			}
		}else {
			NextTurn nextturn = new NextTurn();
			nextturn.performAction(elapsedTime);
		}
	}
}
