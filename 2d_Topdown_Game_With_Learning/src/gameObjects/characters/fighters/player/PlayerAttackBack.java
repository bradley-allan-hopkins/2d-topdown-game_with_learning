/**
 * Title: PlayerAttackBack.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters.player;

import java.util.List;

import gameObjects.GameObject;
import gameObjects.characters.Entity;
import gameObjects.characters.fighters.Fighter;
import gameObjects.characters.fighters.SwordAttack;
import state.states.FightState;

/**
 * The Class PlayerAttackBack.This action attacks any enemies that has attacked the player and
 * plays the sword animation
 */
public class PlayerAttackBack extends SwordAttack {

	/**
	 * Player attack back.
	 *
	 * @param obj the obj
	 */
	public PlayerAttackBack(GameObject obj) {
		super(obj);
	}


	@Override
	public void performAction(float elapsedTime) {
		int animateSpeed = 15;
		int length = 8;


		if (!playedAnimation) {
			List<Fighter> whoAttacked = ((Fighter)obj).getWhoAttacked();
			if (((Entity)obj).getAnimation().getDelay() == -1) {
				//change animation
				((Entity)obj).setAnimation(5, animateSpeed, length);
				((Entity)obj).getAnimation().setFrame(3);

				//show selection
				whoAttacked.get(0).setSelected(true);
			}

			//update animation as player does not update every frame
			((Entity)obj).getAnimation().update();

			if (((Entity)obj).getAnimation().getFrame() == 7) {
				//set new animation
				((Entity)obj).setAnimation(0, -1, length);
				((Entity)obj).getAnimation().setFrame(1);

				playedAnimation = true;
				//set standing still animation

				randomDamage(whoAttacked.get(0),0.50f);
				whoAttacked.get(0).decreaseHealth(getDamage());


				//sets timer and message to display damage to screen
				FightState.display.timer = 40;
				FightState.display.selected = whoAttacked.get(0);
				if (getDamage() > 50) FightState.display.damage = "Big Hit:" + getDamage();
				else FightState.display.damage = "" + getDamage();



				//go through list again to unselect enemy
				whoAttacked.get(0).setSelected(false);

				//now remove the fighter
				whoAttacked.remove(whoAttacked.get(0));
				FightState.checkIfFighterDead();
				
				//reset damage
				setDamage(-1);
			}

		}
	}

}
