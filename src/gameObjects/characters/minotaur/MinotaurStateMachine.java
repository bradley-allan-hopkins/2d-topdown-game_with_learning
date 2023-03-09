/**
 * Title: AntStateMachine.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 25, 2022
 */
package gameObjects.characters.minotaur;

import ai.statemachine.GameObjectStateMachine;
import ai.statemachine.HierarchicalStateMachine;
import ai.statemachine.State;
import ai.statemachine.SubMachineState;
import ai.statemachine.Transition;
import ai.statemachine.actions.PlaySoundClip;
import ai.statemachine.actions.ResetSoundClip;
import ai.statemachine.actions.SeekObject;
import ai.statemachine.actions.WanderSearch;
import ai.statemachine.conditions.AndCondition;
import ai.statemachine.conditions.CanSeeObjectCondition;
import ai.statemachine.conditions.Condition;
import ai.statemachine.conditions.NotCondition;
import ai.statemachine.conditions.WithinRadiusCondition;
import ai.steeringbehaviors.Behavior;
import gameObjects.GameObject;
import state.states.PlayState;
import tiles.TileManager;

/**
 * The Class AntStateMachine. This class creates the states for the minotaur in the game world
 */
public class MinotaurStateMachine extends GameObjectStateMachine{
	
	

	/**
	 * Minotaur state machine.
	 *
	 * @param obj the obj
	 * @param tm the tm
	 */
	public MinotaurStateMachine(GameObject obj,TileManager tm) {
		super(obj,tm);
	}

	/**
	 * Creates the states.
	 *
	 * @return hierarchical state machine
	 */
	@Override
	public HierarchicalStateMachine createStates() {
		HierarchicalStateMachine topLevel = new HierarchicalStateMachine("Top Level");
		
		Behavior seekAttributes = new Behavior();
		seekAttributes.setMaxAcceleration(1.0f);
		seekAttributes.setMaxPrediction(5);
		seekAttributes.setLookAhead(75);
		seekAttributes.setAvoidDistance(5);
		
		Behavior wanderAttributes = new Behavior();
		wanderAttributes.setMaxAcceleration(0.1f);
		wanderAttributes.setMaxRotation(1.5f);
		wanderAttributes.setTargetRadius(0.01f);
		wanderAttributes.setWanderRadius(32);
		wanderAttributes.setSlowRadius(2);
		wanderAttributes.setWanderRate(10);
		wanderAttributes.setWanderOffset(5);
		wanderAttributes.setMaxAngularAcceleration(0.00005f);
		wanderAttributes.setLookAhead(75);
		wanderAttributes.setAvoidDistance(5);
		wanderAttributes.setCollisionRadius(100);
		
	
		PlaySoundClip getHim = 
				new PlaySoundClip("src/resources/sounds/get-him.wav",90);
		PlaySoundClip neverMind = 
				new PlaySoundClip("src/resources/sounds/never-mind.wav",100);
		
		SubMachineState searchMachine = new SubMachineState("Search Machine",1,topLevel);
		State searching = new State("Searching For Player",0,searchMachine);
		State found = new State("Thirsty", 0 , searchMachine);

		searchMachine.initialState(searching);

		int distanceToTarget = 300;
		Condition wrc = new WithinRadiusCondition(PlayState.player,obj,distanceToTarget);
		Condition csoc = new CanSeeObjectCondition(PlayState.player,obj,tm);
		
		searching.addEntryActions(new ResetSoundClip(neverMind));
		searching.addActions(new WanderSearch(obj,wanderAttributes,1,tm));	
		//if can see player and within distance
		searching.addTransitions(new Transition(new AndCondition(csoc,wrc),
				found,searching.getLevel()));
		
		found.addActions(getHim);
		found.addActions(new SeekObject(obj,PlayState.player,seekAttributes,null,5,tm));
		//if target not within radius and is hidden
		found.addTransitions(new Transition(new AndCondition( new NotCondition(wrc),
				new NotCondition(csoc)),
				searching,found.getLevel()));
		found.addExitActions(new ResetSoundClip(getHim));
		found.addExitActions(neverMind);
		

		topLevel.initialState(searchMachine);
		return topLevel;
	}

}
