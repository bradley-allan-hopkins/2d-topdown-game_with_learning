/**
 * Title: SubMachineState.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 16, 2022
 * derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.statemachine;

import ai.statemachine.actions.Action;


/**
 * The Class SubMachineState. This class is a duplicate of State with the added extention
 * of HierarchicalStateMachine. It was needed as multiple inheritance is not allowed in java.
 * This class needed to have the same attributes as states as it is also a state that can 
 * be used by a hierachicalStateMachine.
 */
public class SubMachineState extends HierarchicalStateMachine implements HSMState {

	//duplicate as State
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
	 * Sub machine state.
	 * @param name - name of the Machine 
	 *
	 * @param level - the level of the state
	 * @param parent - the parent is of type stateMachine
	 */
	public SubMachineState(String name, int level,HierarchicalStateMachine parent) {
		super(name);
		this.parent = parent;
		this.actions = new Action[0];
		this.entryActions = new Action[0];
		this.exitActions = new Action[0];
		this.transitions = new Transition[0];
		this.level = level;
	}

	@Override
	public Action[] getActions() {
		return actions; //route to the state
	}
	
	/**
	 * Update.
	 * @return action[]
	 */
	@Override
	public UpdateResult update() {
		return super.update();
	}
	
	/**
	 * Gets the variable "States".
	 *we get states by adding ourself to our active children 
	 * @return h state[] - States
	 */
	@Override
	public HSMState[] getStates() {
		HSMState[] states = new HSMState[1];
		states[0] = this;
		if (super.currentState != null) {
			return combineArrays(states,super.currentState.getStates());
		}
		return states;
	}
	
	//duplicate code of states to "allow" for multiple inheritance 
	

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
	public Action[] getEntryActions() {
		return entryActions;
	}

	@Override
	public Action[] getExitActions() {
		return exitActions;
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
