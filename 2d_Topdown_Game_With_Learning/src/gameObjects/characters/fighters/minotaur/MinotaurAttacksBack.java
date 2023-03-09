/**
 * Title: MinotaurAttacksBack.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package gameObjects.characters.fighters.minotaur;

import java.util.ArrayList;
import java.util.List;

import gameObjects.GameObject;
import gameObjects.characters.Entity;
import gameObjects.characters.fighters.Fighter;
import gameObjects.characters.fighters.SwordAttack;
import state.states.FightState;

/**
 * The Class MinotaurAttacksBack. This action allows the minotaur to attack back if it is 
 * defending and it was attacked and it plays a animation 
 */
public class MinotaurAttacksBack extends SwordAttack{


	/**
	 * Minotaur attacks back.
	 *
	 * @param obj the obj
	 */
	public MinotaurAttacksBack(GameObject obj) {
		super(obj);
	}
	

	@Override
	public void performAction(float elapsedTime) {
		int animateSpeed = 15;
		int length = 5;

		
		if (!playedAnimation) {	
			List<Fighter> whoAttacked = ((Fighter)obj).getWhoAttacked();
				if (((Entity)obj).getAnimation().getDelay() == -1) {
					//change animation 
					((Entity)obj).setAnimation(14, animateSpeed, length);
					
					//show selection
					whoAttacked.get(0).setSelected(true);
				}
	
				//update animation as player does not update every frame
				((Entity)obj).getAnimation().update();
				
				if (((Entity)obj).getAnimation().getFrame() == 4) {
					
					((Entity)obj).getAnimation().setFrame(0);
					((Entity)obj).getAnimation().setDelay(-1);

					randomDamage(whoAttacked.get(0),0.50f);
					whoAttacked.get(0).decreaseHealth(getDamage());
					
					
					//sets timer and message to display damage to screen
					FightState.display.timer = 40;
					FightState.display.selected = whoAttacked.get(0);
					if (getDamage() > 50) FightState.display.damage = "Big Hit:" + getDamage();
					else FightState.display.damage = "" + getDamage();
					
					
					
					//go through list again to unselect enemy
					whoAttacked.get(0).setSelected(false);
					
					playedAnimation = true;
					((Fighter)obj).setWhoAttacked(new ArrayList<Fighter>());
					
					//now remove the fighter
					whoAttacked.remove(whoAttacked.get(0));
					FightState.checkIfFighterDead();
					//reset damage
					setDamage(-1);
				}
			
		}
	}
	


}
