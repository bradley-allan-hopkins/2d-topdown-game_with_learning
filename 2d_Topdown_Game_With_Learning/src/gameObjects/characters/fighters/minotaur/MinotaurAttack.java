/**
 * Title: MinotaurAttack.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters.minotaur;


import gameObjects.GameObject;
import gameObjects.characters.Entity;
import gameObjects.characters.fighters.Fighter;
import gameObjects.characters.fighters.SwordAttack;
import state.states.FightState;



/**
 * The Class MinotaurAttack. This action attacks the player and plays the minotaur animation
 */
public class MinotaurAttack extends SwordAttack{


	/**
	 * Minotaur attack.
	 *
	 * @param obj the obj
	 */
	public MinotaurAttack(GameObject obj) {
		super(obj);
	}

	@Override
	public void performAction(float elapsedTime) {
		int animateSpeed = 15;
		int length = 9;
		int animation = 16;
		int previousAnimation = ((Entity)obj).getCurrentAnimation();

		if (!playedAnimation) {
			if (previousAnimation != animation) {
				((Entity)obj).setAnimation(animation, animateSpeed, length);
				//((Entity)obj).setAnimation(animation,((Entity)obj).getSpriteSheet().
				//		getSpriteArray(animation), animateSpeed);
				//((Entity)obj).getAnimation().setLength(length);
				
				for (Fighter fighter : FightState.fighters) {
					if (fighter.getSelected()) {
						selected = fighter;
						break;
					}
				}
			}
			else ((Entity)obj).setDelay(animateSpeed);

			if (((Entity)obj).getAnimation().getFrame() == length - 1) {
				((Entity)obj).setAnimation(15, animateSpeed, 6);


				randomDamage(selected,1);
				if (selected == null) {
					System.out.println("Selected is null");//error message
					return;
				}
				//selected fighter is hit for 'x' amount of damage
				
				selected.decreaseHealth(getDamage());
				//add this enemy to the who attacked list
				selected.getWhoAttacked().add(((Fighter)obj));

				//animation is played
				playedAnimation = true;

				//sets timer and message to display damage to screen
				FightState.display.timer = 40;
				FightState.display.selected = selected;
				if (getDamage() > 50) FightState.display.damage = "Big Hit:" + getDamage();
				else FightState.display.damage = "" + getDamage();

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