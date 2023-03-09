/**
 * Title: MinotaurFightStateMachine.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package gameObjects.characters.fighters.minotaur;



import ai.statemachine.GameObjectStateMachine;
import ai.statemachine.HierarchicalStateMachine;
import ai.statemachine.State;
import ai.statemachine.SubMachineState;
import ai.statemachine.Transition;
import ai.statemachine.actions.PlaySoundClip;
import ai.statemachine.actions.ResetSoundClip;
import ai.statemachine.conditions.TimeElapsedCondition;
import gameObjects.GameObject;
import gameObjects.Testing;
import gameObjects.characters.fighters.AttackedWhileDefendingCondition;
import gameObjects.characters.fighters.HealthZeroCondition;
import gameObjects.characters.fighters.NextTurn;
import gameObjects.characters.fighters.ResetAnimation;
import tiles.TileManager;

/**
 * The Class MinotaurFightStateMachine. This is the state machine for the minotaur fighter
 */
public class MinotaurFightStateMachine extends GameObjectStateMachine {

	/**
	 * Minotaur fight state machine.
	 *
	 * @param obj the obj
	 * @param tm the tm
	 */
	public MinotaurFightStateMachine(GameObject obj,TileManager tm) {
		super(obj,tm);
	}


	@Override
	public HierarchicalStateMachine createStates() {
		HierarchicalStateMachine topLevel = new HierarchicalStateMachine("Top Level");
		
		SubMachineState minotaurMachine = new SubMachineState("Minotaur Machine",1,topLevel);
		State dying = new State("Minotaur is dying",1,topLevel);
		State dead = new State("Minotaur is dead",1,topLevel);
		
		State choose = new State("Minotaur Turn",0,minotaurMachine);
		State minoAttack = new State("Minotaur is attacking",0,minotaurMachine);
		State minoDefend = new State("Minotaur is defending",0,minotaurMachine);
		State minoHeal = new State("Minotaur is healing",0,minotaurMachine);
		State minoAttackBack = new State("Minotaur is attacking back",0,minotaurMachine);
		
		
		minotaurMachine.initialState(choose);
		
		//sounds 
		PlaySoundClip attackSwordSound = 
				new PlaySoundClip("src/resources/sounds/sword-clash.wav",80);
		PlaySoundClip attackSwordShortSound = 
				new PlaySoundClip("src/resources/sounds/sword-clash-short.wav",80);
		PlaySoundClip drinkingSound = 
				new PlaySoundClip("src/resources/sounds/drinkPotion.wav",95);
		PlaySoundClip deadSound = 
				new PlaySoundClip("src/resources/sounds/died-short"
						+ ".wav",85);
		
		//minotaur machine 
		Transition healthZero = new Transition(new HealthZeroCondition(obj),dying,
				minotaurMachine.getLevel());
		
		
		//player state is where minotaur will use learning 
		Choose choice = new Choose(obj);
		
		if (Testing.useLearning) {
			ChooseLearning choiceLearning = new ChooseLearning(obj);
			choose.addActions(choiceLearning);
			healthZero.addActions(new SaveLearning(choiceLearning));
		}
		else 
			choose.addActions(choice);
		
		minotaurMachine.addTransitions(healthZero);
		MinotaurAttack mAttack = new MinotaurAttack(obj);
		MinotaurDefend mDefend = new MinotaurDefend(obj);
		MinotaurAttacksBack mAttackBack = new MinotaurAttacksBack(obj);
		MinotaurHeals mHeal = new MinotaurHeals(obj);
		

		
		Transition minoAttacks = new Transition(new EnemyChoosesAttackCondition(choice),
				minoAttack,choose.getLevel());
		Transition minoDefends = new Transition(new EnemyChoosesDefendCondition(choice),
				minoDefend,choose.getLevel());
		Transition minoHeals = new Transition(new EnemyChoosesHealCondition(choice),
				minoHeal,choose.getLevel());
		Transition minoAttacksBack = new Transition(new AttackedWhileDefendingCondition(obj),
				minoAttackBack, choose.getLevel());
		
		choose.addTransitions(minoAttacks);
		choose.addTransitions(minoHeals);
		choose.addTransitions(minoDefends);
		choose.addTransitions(minoAttacksBack);
		
		//player attacks state
		minoAttack.addActions(mAttack);
		minoAttack.addActions(attackSwordSound);
		minoAttack.addTransitions(new Transition(new TimeElapsedCondition(2.3f),choose,
				minoAttack.getLevel()));
		minoAttack.addExitActions(new NextTurn());
		minoAttack.addExitActions(new ResetAnimation(mAttack));
		minoAttack.addExitActions(new ResetSoundClip(attackSwordSound));
		
		//minotaur defends state 
		minoDefend.addActions(mDefend);
		minoDefend.addTransitions(new Transition(new TimeElapsedCondition(1.2f),choose,
				minoAttack.getLevel()));
		minoDefend.addExitActions(new NextTurn());
		minoDefend.addExitActions(new ResetAnimation(mDefend));
		
		
		//minotaur heal state
		minoHeal.addActions(mHeal);
		minoHeal.addActions(drinkingSound);
		minoHeal.addTransitions(new Transition(new TimeElapsedCondition(2.3f),choose,
				minoHeal.getLevel()));
		minoHeal.addExitActions(new NextTurn());
		minoHeal.addExitActions(new ResetAnimation(mHeal));
		minoHeal.addExitActions(new ResetSoundClip(drinkingSound));
		
		//minotaur attacks back state
		minoAttackBack.addActions(mAttackBack);
		minoAttackBack.addActions(attackSwordShortSound);
		minoAttackBack.addTransitions(new Transition(new TimeElapsedCondition(2.3f),choose,
				minoAttack.getLevel()));
		minoAttackBack.addExitActions(new ResetAnimation(mAttackBack));
		minoAttackBack.addExitActions(new ResetSoundClip(attackSwordShortSound));
		
		//dead state 
		dying.addActions(deadSound);
		dying.addActions(new MinotaurDead(obj));
		dying.addTransitions(new Transition(new TimeElapsedCondition(2.3f),dead,
				dying.getLevel()));
		
		
		topLevel.initialState(minotaurMachine);
		return topLevel;
	}


}
