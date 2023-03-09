/**
 * Title: Choose.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package gameObjects.characters.fighters.minotaur;

import ai.statemachine.actions.Action;
import gameObjects.GameObject;
import gameObjects.characters.fighters.Fighter;
import state.states.FightState;
import utils.Random;

/**
 * The Class Choose. This class is where the computer player chooses what action to perform:
 * attack, defend, heal and which fighter to select. This object implements basic logic for 
 * the computer
 */
public class Choose extends Action {
	
	/**
	 * The Enum CHOICE.
	 */
	public enum CHOICE{
		
		/** The Attack. */
		Attack,
		
		/** The Defend. */
		Defend,
		
		/** The Heal. */
		Heal,
		
		/** The None. */
		None
	}
	
	/** The choice. */
	protected static CHOICE choice;
	
	/** The fighter. */
	protected Fighter fighter;
	
	/**
	 * Choose.
	 *
	 * @param obj the obj
	 */
	public Choose(GameObject obj) {
		this.fighter = ((Fighter)obj);
	}


	@Override
	public void performAction(float elapsedTime) {
		//find selected fighter
		for (Fighter fighter : FightState.fighters) {
			if (fighter.getSelected()) {
				fighter.setSelected(false);
			}
		}
		int random = Random.getRndInteger(1, 2);
		//some logic 
		
		//if player health is low attack and kill
		if (FightState.player.getHealth() < 20) random = 1;
		
		//if friends are low in health heal
		else {
			for (int i = 1; i < FightState.fighters.size();i++) {
				if (FightState.fighters.get(i).getHealth() < 30 && fighter.getHealthBottles() > 0) {
					random = 3;
					break;
				}
				else if (fighter.getHealth() < 50 && fighter.getHealthBottles() > 0) {
					random = 2;
				}
			}
		}
		switch(random) {
			//attack
			case 1: 
				FightState.player.setSelected(true);
				choice = CHOICE.Attack;
				break;
			//defend
			case 2: 
				fighter.setSelected(true);
				choice = CHOICE.Defend;
				break;
			//heal
			case 3:
				System.out.println("heal");
				//if self is low in health heal
				if (fighter.getHealth() < 30) fighter.setSelected(true);
				//else heal friend
				else {
					for (int i = 1; i < FightState.fighters.size();i++) {
						if (FightState.fighters.get(i).getHealth() < 30) {
							FightState.fighters.get(i).setSelected(true);
						}
					}
				}
				choice = CHOICE.Heal;
				break;
			default :
				break;
			
		}
	}

}
