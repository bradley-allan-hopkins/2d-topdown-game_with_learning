/**
 * Title: HTransition.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 16, 2022
 * derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.statemachine;

import ai.statemachine.actions.Action;
import ai.statemachine.conditions.Condition;

/**
 * The Class HTransition.
 */
public class Transition {

	/** The actions. */
	Action[] actions;
	
	/** The condition. */
	Condition condition;
	
	/** The target state. */
	HSMState targetState;
	
	/** previous state*/
	int previousLevel;
	
	/** the level of transition*/
	int level;
	
	/**
	 * Instantiates a new h transition().
	 */
	public Transition() {}
	
	
	/**
	 * H transition.
	 * @param condition the condition
	 * @param targetState the target state
	 * @param previousLevel - the previous states level
	 */
	public Transition(Condition condition,HSMState targetState,int previousLevel) {
		this.condition = condition;
		this.targetState = targetState;
		this.previousLevel = previousLevel;
	}
	
	/**
	 * return the difference in levels of the hierechy from the source to the target of the 
	 * transition
	 * @return int - Level
	 */
	public int getLevel() {
		//ends in a positive number 
		return targetState.getLevel() - previousLevel;
	}
	

	/**
	 * Checks if is condition is triggered.
	 * @return true, if is triggered
	 */
	public boolean isTriggered() {
		return condition.test();
	}
	
	/**
	 * Gets the variable "TargetState".
	 *
	 * @return state - TargetState
	 */
	public HSMState getTargetState() {
		return targetState;
	}
	
	/**
	 * Gets the variable "Actions".
	 *
	 * @return action[] - Actions
	 */
	public Action[] getActions() {
		return actions;
	}
	
	/**
	 * public add actions to actions array
	 * @param action - action added to array 
	 */
	public void addActions(Action action) {
		actions = Action.addToActionArray(actions,action);
	}
	
	/** 
	 * 
	 * Method that sets the target state
	 * @param target - the target state for this transition
	 */
	public void setTargetState(HSMState target) {
		this.targetState = target;
	}
	
	/**
	 * public method that sets the condition
	 * @param condition - the condition to activate this transition
	 */
	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	
	/**
	 * Method that allows Transitions array to grow
	 * @param array - the original array that the transition is added to
	 * @param transition that is added to current array 
	 * @return a new Transition array
	 */
	public static Transition[] addToTransitionArray(Transition[] array,Transition transition) {
		Transition[] temp = new Transition[array.length + 1];
		for (int i = 0; i < array.length;i++) {
			temp[i] = array[i];
		}
		temp[array.length] = transition;
		return temp;
	}
	
	@Override
	public String toString() {
		return "Condition Passed:" + condition + " => " + targetState;
	}
}
