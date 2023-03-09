/**
 * Title: PlayerHeal.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters.player;


import java.util.ArrayList;

import ai.statemachine.conditions.TimeElapsedCondition;
import gameObjects.GameObject;
import gameObjects.characters.fighters.FightSceneAction;
import gameObjects.characters.fighters.Fighter;
import state.states.FightState;

/**
 * The Class PlayerHeal.
 */
public class PlayerHeal extends FightSceneAction {

	/** timer to display health bottle on screen */
	TimeElapsedCondition time;
	
	/**
	 * Player heal.
	 *
	 * @param obj the obj
	 */
	public PlayerHeal(GameObject obj) {
		super(obj);
		time = new TimeElapsedCondition(1.4f);
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

			//decrease number of health bottles player has in inventory
			((PlayerFighter)selected).setHealthBottles(((PlayerFighter)selected).
					getHealthBottles() - 1);
			
			//reset who attacked list 
			((Fighter)obj).setWhoAttacked(new ArrayList<Fighter>());
			FightState.display.healTimer = 40;
		}
		if (time.test()) {
			//player not selected
			selected.setSelected(false);
		}
	}




}
