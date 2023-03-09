/**
 * Title: FightSceneAction.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters;

import ai.statemachine.actions.Action;
import gameObjects.GameObject;
/**
 * The Class FightSceneAction. Abstract class for all the fightActions. They all deal with 
 * a GameObject and play an animation. When the playedAnimation is true, the animation will not
 * play again; this allows for the animation to only play once. PlayedAnimation will need 
 * to be reset so that if the fighter chooses the action again, the animation can play again. 
 */
public abstract class FightSceneAction extends Action {

	/** The obj. */
	protected GameObject obj;
	
	/** The played animation. */
	protected boolean playedAnimation;
	
	/** Selected fighter*/
	protected Fighter selected;
	
	/**
	 * Does fight scene action. 
	 * First set playedAnimation to false
	 *
	 * @param obj the obj
	 */
	public FightSceneAction(GameObject obj) {
		this.obj = obj;
		playedAnimation = false;
	}
	
	/**
	 * Public method used to get
	 * @return the obj
	 */
	public GameObject getObj() {
		return obj;
	}

	/**
	 * Public method used to set
	 * @param obj the obj to set
	 */
	public void setObj(GameObject obj) {
		this.obj = obj;
	}

	/**
	 * Public method used to get
	 * @return the playedAnimation
	 */
	public boolean isPlayedAnimation() {
		return playedAnimation;
	}

	/**
	 * Public method used to set
	 * @param playedAnimation the playedAnimation to set
	 */
	public void setPlayedAnimation(boolean playedAnimation) {
		this.playedAnimation = playedAnimation;
	}


}
