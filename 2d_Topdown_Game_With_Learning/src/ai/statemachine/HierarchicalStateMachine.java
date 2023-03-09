/**
 * Title: HierarchicalStateMachine.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 16, 2022
 * derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.statemachine;

import ai.statemachine.actions.Action;

/**
 * The Class HierarchicalStateMachine.
 */
public class HierarchicalStateMachine implements HSMBase {
	
	HSMBase parent;
	
	String name;

	/**list of states at this level of the heirachy*/
	HSMState[] states;
	
	/** Initial state for when the machine has no current State */
	HSMState initialState;
	
	/** the current state of the machine*/
	HSMState currentState;
	
	/**
	 * Hierarchical state machine.
	 *
	 * @param initialState the initial state
	 * @param name - the name of the machine 
	 */
	public HierarchicalStateMachine(HSMState initialState,String name) {
		this.name = name;
		this.initialState = initialState;
		states = new HSMState[0];
	}
	
	/**
	 * Constructor without the initial state, must use init() to set initialstate
	 * @param name - name of the machine 
	 */
	public HierarchicalStateMachine(String name) {
		this.name = name;
		states = new HSMState[0];
	}
	/**
	 * initializes the first state
	 * @param initialState - the initial state to start in
	 */
	public void initialState(HSMState initialState) {
		this.initialState = initialState;
		
	}
	

	@Override
	public HSMState[] getStates() {
		if (currentState != null)
			return currentState.getStates();
		
		return new HSMState[0]; // the book says return [] 
	}
	
	/**
	 * method that recursively updates the machine 
	 * @return Array of actions
	 */
	@Override
	public UpdateResult update() {
		//if we are in no state, use the initial state
		if (currentState == null) {
			currentState = initialState;
			UpdateResult result = new UpdateResult();
			result.actions = currentState.getEntryActions();
			return result;
		}
		
		//try to find a transition in the current state 
		Transition triggeredTransition = null;
		for (Transition transition : currentState.getTransitions()) {
			if (transition.isTriggered()) {
				triggeredTransition = transition;
				break;
			}
		}
		
		UpdateResult result = null;
		//if we have found one, make a result structure for it
		if (triggeredTransition != null) {
			result = new UpdateResult();
			result.actions = new Action[0]; // blank slate
			result.transition = triggeredTransition;
			result.level = triggeredTransition.getLevel();
		}
		//other wise recurse down for a result
		else 
			//currentState.update();
			result = currentState.update(); // LOOK INTO THIS AS IT WAS THE SAME NAME 
		
		//check if the result contains a transition
		if (result.transition != null) {
			//act based on level
			HSMState targetState;
			if (result.level == 0) {
				//its on our level so honor it
				targetState = result.transition.getTargetState();
				result.actions = Action.combineArrays(result.actions, currentState.getExitActions());
				result.actions = Action.combineArrays(result.actions,result.transition.getActions());
				result.actions = Action.combineArrays(result.actions,targetState.getEntryActions());
				
				//set current state
				currentState = targetState;
				
				//add our normal action (we may be a state)
				result.actions = Action.combineArrays(result.actions, getActions());
				//---------------System.out.println("TRANSITION FIRED:" + result.transition);
				//clear the transition, so nobody else does it 
				result.transition = null;
			}
			else if (result.level > 0) {
				//it is destined for a higher level so exit our current state 
				result.actions = Action.combineArrays(result.actions, currentState.getExitActions());
				currentState = null;
				
				//decrease number of level to go
				result.level -= 1;
			}
			else {
				//it needs to be passed down
				targetState = result.transition.getTargetState();
				HierarchicalStateMachine targetMachine = (HierarchicalStateMachine) targetState.getParent();
				result.actions = Action.combineArrays(result.actions, result.transition.getActions());
				result.actions = Action.combineArrays(result.actions, targetMachine.updateDown(targetState,-result.level));
				
				//clear the transition, so nobody else does it 
				result.transition = null;
			}
		}
		else {
			//we can simply do our normal action
			//System.out.println(this);
			result.actions = Action.combineArrays(result.actions, getActions());
			
		}
		
		return result; //not sure why this is here
	}
	
	/**
	 * recurses up the parent heirarchy, transitioning into each state in turn for the given
	 * number of levels 
	 * @param state the target state
	 * @param level -level ??????????????????????????
	 * @return a array of Actions
	 */
	public Action[] updateDown(HSMBase state, int level) {
		//if we are not at top level, continue recursing
		Action[]  actions = new Action[0];
		if (level > 0) {
			//pass ourself as the transition state to out parent 
		actions = Action.combineArrays(actions, ((HierarchicalStateMachine) getParent()).updateDown(this,level-1));
		}
		//otherwise we have no actions to add to 
		else
			actions = new Action[0];
		//if we have a current state, exit it
		if (currentState != null) {
			actions = Action.combineArrays(actions, currentState.getExitActions());
		}
		
		//move to the new state and return all the actions 
		currentState = (HSMState) state;
		actions = Action.combineArrays(actions, ((HSMState) state).getEntryActions());
		return null;
	}
	
	/**
	 * add to state array 
	 * @param state - State that is added to array
	 */
	public void addStates(HSMState state) {
		states = addToStateArray(states, state);
	}
	
	@Override
	public String toString() {
		return "StateMachine: " + name;
	}
	
	/**
	 * Method that 
	 * @param array - array that the state will be added to 
	 * @param state - the state that will be added 
	 * @return new array of States
	 */
	public static HSMState[] addToStateArray(HSMState[] array,HSMState state) {
		HSMState[] temp = new HSMState[array.length + 1];
		for (int i = 0; i < array.length;i++) {
			temp[i] = array[i];
		}
		temp[array.length] = state;
		return temp;
	}
	
	/**
	 * Method that adds array to another array
	 * @param array1  - original array 
	 * @param array2  - array that will combine with original 
	 * @return new array 
	 */
	public static HSMState[] combineArrays(HSMState[] array1, HSMState[] array2) {
		int totalSize = array1.length + array2.length;
		HSMState[] newArray = new HSMState[totalSize];
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
}
