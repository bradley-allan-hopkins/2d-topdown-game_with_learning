/**
 * Title: HState.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 16, 2022
 * derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.statemachine;

import ai.statemachine.actions.Action;

/**
 * The Class HState.
 */
public class State implements HSMState{
	
	HSMBase parent;
	
	/** Name of state - for testing purposes*/
	String name;

	/** The actions. */
	Action[] actions;
	
	/** The entry actions. */
	Action[] entryActions;

	/** The exit actions. */
	Action[] exitActions;

	/** The transitions. */
	Transition[] transitions;
	
	/** level of State*/
	int level;
	
	/**
	 * Instantiates a new h state().
	 * @param name - name of the state
	 * @param level - level of the state
	 * @param parent - parent of the state
	 */
	public State(String name, int level,HierarchicalStateMachine parent) {
		this.parent = parent;
		this.name = name;
		this.actions = new Action[0];
		this.entryActions = new Action[0];
		this.exitActions = new Action[0];
		this.transitions = new Transition[0];
		this.level = level;
	}

	
	/**
	 * Method that adds array to another array
	 * @param array1 - original array 
	 * @param array2 - 2nd array 
	 * @return an array of states
	 */
	public static State[] combineArrays(State[] array1, State[] array2) {
		int totalSize = array1.length + array2.length;
		State[] newArray = new State[totalSize];
		//add data from 1st array
		for (int i = 0; i < array1.length;i++) {
			newArray[i] = array1[i];
		}
		//add data from 2nd array
		for (int i = 0; i < array2.length;i++) {
			newArray[array1.length + i] = array2[i];
		}
		return newArray;
	}
	
	@Override
	public HSMBase getParent() {
		return parent;
	}

	@Override
	public void addActions(Action action) {
		actions = Action.addToActionArray(actions,action);
	}

	@Override
	public void addEntryActions(Action action) {
		entryActions = Action.addToActionArray(entryActions,action);
	}

	@Override
	public void addExitActions(Action action) {
		exitActions = Action.addToActionArray(exitActions,action);
	}

	@Override
	public void addTransitions(Transition transition) {
		transitions = Transition.addToTransitionArray(transitions,transition);
	}

	@Override
	public Action[] getActions() {
		return actions;
	}

	@Override
	public Action[] getEntryActions() {
		return entryActions;
	}

	@Override
	public Action[] getExitActions() {
		return exitActions;
	}


	@Override
	public State[] getStates() {
		//if were just a state, the the stack is just us
		State[] states = new State[1];
		states[0] = this;
		return states;
	}

	@Override
	public Transition[] getTransitions() {
		return transitions;
	}
	
	@Override
	public int getLevel() {
		return level;
	}
	
	@Override
	public String toString() {
		return "State: " + name;
	}
	



}
