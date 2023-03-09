/**
 * Title: PlayerAttack.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters.player;

import java.util.ArrayList;

import gameObjects.GameObject;
import gameObjects.characters.Entity;
import gameObjects.characters.fighters.Fighter;
import gameObjects.characters.fighters.SwordAttack;
import state.states.FightState;
import tiles.blocks.ButtonBlock;

/**
 * The Class PlayerAttack. This actions allows the player to perform an attack on a selected
 * enemy fighter. It also plays the player animation to attack 
 */
public class PlayerAttack extends SwordAttack{

	/**
	 * Player attack.
	 *
	 * @param obj the obj
	 */
	public PlayerAttack(GameObject obj) {
		super(obj);
	}

	@Override
	public void performAction(float elapsedTime) {
		int animateSpeed = 15;
		int length = 8;

		if (!playedAnimation) {	
			if (((Entity)obj).getAnimation().getDelay() == -1) {
				//change animation 
				((Entity)obj).setAnimation(5, animateSpeed, length);
			}

			//update animation as player does not update every frame
			((Entity)obj).getAnimation().update();
			
			
			if (((Entity)obj).getAnimation().getFrame() == 7) {
				//set standing still animation
				((Entity)obj).setAnimation(5, -1, length);
				((Entity)obj).getAnimation().setFrame(7);
				
				Fighter selected = null;
				for (Fighter fighter : FightState.fighters) {
					if (fighter.getSelected()) {
						selected = fighter;
						break;
					}
				}
				randomDamage(selected,1);
				if (selected == null) {
					System.out.println("Selected is null");//error message
					return;
				}
				selected.decreaseHealth(getDamage());
				playedAnimation = true;
				((ButtonBlock)FightState.buttons[0]).setPressed(false);
				
				//sets timer and message to display damage to screen
				FightState.display.timer = 40;
				FightState.display.selected = selected;
				if (getDamage() > 50) FightState.display.damage = "Big Hit:" + getDamage();
				else FightState.display.damage = "" + getDamage();
				
				//reset who attacked list 
				((Fighter)obj).setWhoAttacked(new ArrayList<Fighter>());
				
				//add this enemy to the who attacked list
				selected.getWhoAttacked().add(((Fighter)obj));
				
				//enemy is not longer selected and player is not defending 
				selected.setSelected(false);
				((Fighter)obj).setDefend(false);
				FightState.checkIfFighterDead();
				
				//reset damage
				setDamage(-1);
			}
		}
	}
	
}
