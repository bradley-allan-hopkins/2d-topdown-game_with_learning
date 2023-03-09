/**
 * Title: PlayerFightStateMachine.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters.player;

import ai.statemachine.GameObjectStateMachine;
import ai.statemachine.HierarchicalStateMachine;
import ai.statemachine.State;
import ai.statemachine.SubMachineState;
import ai.statemachine.Transition;
import ai.statemachine.actions.PlaySoundClip;
import ai.statemachine.actions.ResetSoundClip;
import ai.statemachine.conditions.TimeElapsedCondition;
import gameObjects.GameObject;
import gameObjects.characters.fighters.AttackedWhileDefendingCondition;
import gameObjects.characters.fighters.HealthZeroCondition;
import gameObjects.characters.fighters.NextTurn;
import gameObjects.characters.fighters.ResetAnimation;
import tiles.TileManager;

/**
 * The Class PlayStateStates. This class creates the state machine for the PlayState
 */
public class PlayerFightStateMachine extends GameObjectStateMachine{
	
	
	
	/**
	 * Player fight state machine.
	 *
	 * @param obj the obj
	 * @param tm the tm
	 */
	public PlayerFightStateMachine(GameObject obj,TileManager tm) {
		super(obj,tm);
	}

	/**
	 * Creates the states.
	 * @return hierarchical state machine
	 */
	@Override
	public HierarchicalStateMachine createStates() {
		HierarchicalStateMachine topLevel = new HierarchicalStateMachine("Top Level");
		
		SubMachineState playerMachine = new SubMachineState("Player Machine",1,topLevel);
		
		State player = new State("Player Turn",0,playerMachine);
		State playerAttack = new State("Player is attacking",0,playerMachine);
		State playerDefend = new State("Player is defending",0,playerMachine);
		State playerHeal = new State("Player is healing",0,playerMachine);
		State playerAttackBack = new State("Player is Attacking back", 0,playerMachine);
		State dead = new State("Dead",0,playerMachine);
		
		playerMachine.initialState(player);
		
		//sounds 
		PlaySoundClip attackSwordSound = 
				new PlaySoundClip("src/resources/sounds/sword-clash.wav",80);
		PlaySoundClip attackSwordShortSound = 
				new PlaySoundClip("src/resources/sounds/sword-clash-short.wav",80);
		PlaySoundClip drinkingSound = 
				new PlaySoundClip("src/resources/sounds/drinkPotion.wav",95);
		
		//player state
		//------actions 
		PlayerAttack pAttack = new PlayerAttack(obj);//player state
		PlayerDefend pDefend = new PlayerDefend(obj);
		PlayerHeal pHeal = new PlayerHeal(obj);
		PlayerAttackBack pBack = new PlayerAttackBack(obj);
		
		//----transitions out of player state
		Transition playerAttacks = new Transition(new PlayerClicksOnAttack(),
				playerAttack,player.getLevel());
		Transition playerDefends = new Transition(new PlayerClicksOnDefend(),
				playerDefend,player.getLevel());
		Transition playerHeals = new Transition(new PlayerClicksOnHeal(),
				playerHeal,player.getLevel());
		Transition attackBack = new Transition(new AttackedWhileDefendingCondition(obj),
				playerAttackBack, player.getLevel());
		Transition healthZero = new Transition(new HealthZeroCondition(obj),
				dead,player.getLevel());
				

		player.addTransitions(playerAttacks);
		player.addTransitions(playerDefends);
		player.addTransitions(playerHeals);
		player.addTransitions(attackBack);
		player.addTransitions(healthZero);
		player.addActions(new PlayerChoose());
		
		//allow the player to play by itself for learning 
		//player.addActions((new PlayerRandomChooseLearning()));
		
		//player attacks state
		playerAttack.addActions(pAttack);
		Transition animationDone = new Transition(new TimeElapsedCondition(2.3f),player,
				playerAttack.getLevel());
		playerAttack.addTransitions(animationDone);
		playerAttack.addExitActions(new NextTurn());
		playerAttack.addExitActions(new ResetAnimation(pAttack));
		playerAttack.addActions(attackSwordSound);
		playerAttack.addExitActions(new ResetSoundClip(attackSwordSound));
		
		//player defend state
		playerDefend.addActions(pDefend);
		playerDefend.addTransitions(new Transition(new TimeElapsedCondition(2.3f),player,
				playerAttack.getLevel()));
		playerDefend.addExitActions(new NextTurn());
		playerDefend.addExitActions(new ResetAnimation(pDefend));
		
		//player heal state 
		playerHeal.addActions(pHeal);
		playerHeal.addTransitions(new Transition(new TimeElapsedCondition(2.3f),player,
				playerAttack.getLevel()));
		playerHeal.addExitActions(new NextTurn());
		playerHeal.addExitActions(new ResetAnimation(pHeal));
		playerHeal.addActions(drinkingSound);
		playerHeal.addExitActions(new ResetSoundClip(drinkingSound));
		
		//player attacks back state
		playerAttackBack.addActions(pBack);
		playerAttackBack.addExitActions(new ResetAnimation(pBack));
		playerAttackBack.addActions(attackSwordShortSound);
		playerAttackBack.addExitActions(new ResetSoundClip(attackSwordShortSound));
		playerAttackBack.addTransitions(new Transition(new TimeElapsedCondition(1.3f),player,
				playerAttack.getLevel()));
		
		//dead state
		dead.addActions(new PlayerDied(obj));
		
		topLevel.initialState(playerMachine);
		return topLevel;
	}


}
