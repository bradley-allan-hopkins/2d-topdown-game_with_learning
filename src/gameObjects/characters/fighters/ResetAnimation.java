/**
 * Title: ResetAnimation.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters;

import ai.statemachine.actions.Action;
/**
 * The Class ResetAnimation. This action resets animation for attack,defend,and heal so that 
 * they can play again
 */
public class ResetAnimation extends Action {
	
	/** The action. */
	FightSceneAction action;
	
	/**
	 * Reset animation.
	 *
	 * @param action the action
	 */
	public ResetAnimation(FightSceneAction action) {
		this.action = action;
	}

	@Override
	public void performAction(float elapsedTime) {
		action.playedAnimation = false;
	}

}
