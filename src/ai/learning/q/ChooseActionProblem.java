/**
 * Title: ChooseActionProblem.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package ai.learning.q;

import ai.learning.q.action.ChooseAttack;
import ai.learning.q.action.ChooseDefend;
import ai.learning.q.action.ChooseHeal;
import ai.statemachine.actions.Action;
import gameObjects.GameObject;
import gameObjects.characters.fighters.Fighter;
import gameObjects.characters.fighters.SwordAttack;
import gameObjects.characters.fighters.minotaur.MinotaurAttack;
import state.states.FightState;
import state.states.PlayState;
import utils.TwoTuple;

/**
 * The Class ChooseActionProblem. This problem is which action should the enemy choose in 
 * battle dependent on its own health, its current supply of health bottles and the players 
 * health
 */
public class ChooseActionProblem extends ReinforcementProblem{


	/** The last minotaur health. */
	int lastMinotaurHealth;
	
	/** The last player health. */
	int lastPlayerHealth;

	/**
	 * Choose action problem.
	 *
	 * @param obj the obj
	 */
	public ChooseActionProblem(GameObject obj) {
		super(obj);
		
		//reset last health from gameworld 
		lastMinotaurHealth = ((Fighter)obj).getHealth();
		lastPlayerHealth = PlayState.player.getWorldHealth();
		init();
	}

	/**
	 * Gets the variable "AvailableActions".
	 *
	 * @param state the state
	 * @return string[] - AvailableActions
	 */
	@Override
	public String[] getAvailableActions(State state) {
		String[] availActions = new String[actions.length];


		//heal not available if no health bottles
		if (((Fighter)obj).getHealthBottles() == 0) {
			availActions = new String[2];
			availActions[0] = actions[0].getClass().getSimpleName();
			availActions[1] = actions [1].getClass().getSimpleName();
			return availActions;
		}

		//else turn actions into strings for saving into txt file
		for (int i = 0; i < actions.length; i++) {
			availActions[i] = actions[i].getClass().getSimpleName();
		}

		return availActions;
	}

	/**
	 * Gets the variable "FirstState".
	 *
	 * @return state - FirstState
	 */
	public static State getFirstState() {
		int bottles = 3; //starting bottles
		int playerHealth = PlayState.player.getWorldHealth();//world health of player 
		int minotaurHealth = 100; //starting minotaur health
		return new ChoosingBattleActionState(bottles,playerHealth,minotaurHealth);
	}
	
	/**
	 * return current state 
	 */
	@Override
	public State getCurrentState() {
		Fighter player = FightState.fighters.get(0);
		Fighter minotaur = ((Fighter)obj);
		return new ChoosingBattleActionState(minotaur.getHealthBottles(),
				player.getHealth(),minotaur.getHealth());
	}


	@Override
	public State getRandomState() {
		return null;
	}


	/**
	 * 
	 * Method that inits variables 
	 */
	public void init() {
		actions = new Action[3];
		actions[0] = new ChooseAttack(obj);
		actions[1] = new ChooseDefend(obj);
		actions[2] = new ChooseHeal(obj);
	}



	@Override
	public TwoTuple<Float, State> takeAction(State state, String action) {
		float reward = 0;
		//perform action must halt and wait for loop to continue
		Fighter minotaur = ((Fighter)obj);
		Fighter player = FightState.fighters.get(0);
		
		if (minotaur.getHealthBottles() == 0 && action.equalsIgnoreCase("ChooseHeal")) {
			System.out.println("--------------------------0 bottles yes healed");
		}
		
		//System.out.println("health:" + minotaur.getHealth());
		if (minotaur.getHealth() > 0){
			//loop through all available actions and check for matching action
			for (Action a : actions) {
				if (action.equalsIgnoreCase(a.getClass().getSimpleName())){
					a.performAction(1);
				}
			}
		}




		//find results before they appear on screen
		int bottles = minotaur.getHealthBottles();
		int minotaurHealth = minotaur.getHealth();
		int playerHealth = player.getHealth();
		
		if (action.equalsIgnoreCase("ChooseHeal")){
			bottles = minotaur.getHealthBottles() -1;
			//negative reward for healing at top
			if (minotaurHealth > 70)reward -= 1;
			minotaurHealth = minotaur.getHealth() + 40;
			if (minotaurHealth > 100) minotaurHealth = 100;
		}
		else if (action.equalsIgnoreCase("ChooseAttack")){
			MinotaurAttack attack = new MinotaurAttack(obj);
			attack.randomDamage(player,1);
			playerHealth -= SwordAttack.getDamage();
			if (playerHealth < 0)playerHealth = 0;
		}
		//defend will not change until next round thus:
		
		//if minotaur attacked back because defended
		//System.out.println(lastPlayerHealth + " > " + player.getHealth());
		if (lastPlayerHealth > player.getHealth() && minotaur.isDefend())reward += 1;
		
		
		State newState = new ChoosingBattleActionState(bottles, playerHealth,minotaurHealth);
		
		//future testing
		//if player is dead
		if (playerHealth <= 0)reward += 100;
		//if minotaur is dead
		if (minotaurHealth == 0)reward -= 10;
		//if minotaur health is low 
		if (minotaurHealth < 25)reward -= 1;
		//if minotaur health is good
		if (minotaurHealth >50)reward += 3;
		//if player health is low
		if (playerHealth < 30)reward += 2;
		
		//System.out.println(bottles + " , " + playerHealth + " , " + minotaurHealth);
		//System.out.println("After wait reward:" + reward + ", state:" + state.getStateNUM() + 
		//		" , newState:" + newState.getStateNUM());
		
		lastPlayerHealth = player.getHealth();
		lastMinotaurHealth = minotaur.getHealth();

		return new TwoTuple<Float,State>(reward,newState);
	}

}
