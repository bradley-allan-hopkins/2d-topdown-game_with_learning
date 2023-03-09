/**
 * Title: MinotaurDefend.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package gameObjects.characters.fighters.minotaur;

import java.util.ArrayList;

import gameObjects.GameObject;
import gameObjects.characters.Entity;
import gameObjects.characters.fighters.FightSceneAction;
import gameObjects.characters.fighters.Fighter;

/**
 * The Class MinotaurDefend. This action allows the minotaur to defend and plays its animation
 */
public class MinotaurDefend extends FightSceneAction {



	/**
	 * Minotaur defend.
	 *
	 * @param obj the obj
	 */
	public MinotaurDefend(GameObject obj) {
		super(obj);
	}

	@Override
	public void performAction(float elapsedTime) {
		//System.out.println("defend");
		int animateSpeed = 15;
		int length = 6;
		int animation = 14;
		int previousAnimation = ((Entity)obj).getCurrentAnimation();

		if (!playedAnimation) {	
			if (previousAnimation != animation || ((Entity)obj).getAnimation().getDelay() == -1) {
				((Entity)obj).setAnimation(14, animateSpeed, length);
			}
			//((Entity)obj).getAnimation().update();
			if (((Entity)obj).getAnimation().getFrame() == 1) {
				((Entity)obj).setAnimation(14,-1, length);
				((Entity)obj).getAnimation().setFrame(0);
				
				//find selected fighter
				Fighter selected = ((Fighter)obj);

				//animation has played 
				playedAnimation = true;
				
				//reset who attacked list 
				((Fighter)obj).setWhoAttacked(new ArrayList<Fighter>());
				
				
				selected.setDefend(true);
				selected.setSelected(false);
			}
		}
	}

}
