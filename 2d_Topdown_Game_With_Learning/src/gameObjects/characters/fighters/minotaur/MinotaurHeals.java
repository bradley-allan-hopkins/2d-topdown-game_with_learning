/**
 * Title: MinotaurHeals.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package gameObjects.characters.fighters.minotaur;

import java.util.ArrayList;

import ai.statemachine.conditions.TimeElapsedCondition;
import gameObjects.GameObject;
import gameObjects.characters.fighters.FightSceneAction;
import gameObjects.characters.fighters.Fighter;
import state.states.FightState;

/**
 * The Class MinotaurHeals. This action performs the heal and it sets a timer in the fightstate
 * display to show a health bottle above the player
 */
public class MinotaurHeals extends FightSceneAction {

	/** The time. */
	TimeElapsedCondition time;
	
	
	/**
	 * Minotaur heals.
	 *
	 * @param obj the obj
	 */
	public MinotaurHeals(GameObject obj) {
		super(obj);
		time = new TimeElapsedCondition(1.3f);
	}


	@Override
	public void performAction(float elapsedTime) {

		//not playing animation but using to not do more than once
		if (!playedAnimation) {
			
			//find selected fighter
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
			//increase health
			selected.increaseHealth(40);
			System.out.println(selected.getHealth() + " > " + ((Fighter)obj).getHealth());

			//decrease number of health bottles player has in inventory
			((Fighter)obj).setHealthBottles(((Fighter)obj).getHealthBottles() - 1);
			
			//reset who attacked list 
			((Fighter)obj).setWhoAttacked(new ArrayList<Fighter>());

			//player not selected
			
			FightState.display.healTimer = 25;
			//time.getTimer().resetTimer();
		}
		if (time.test()) {
			//player not selected
			selected.setSelected(false);
		}
	}

}
