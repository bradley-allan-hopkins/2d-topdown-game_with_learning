/**
 * Title: ReinforcementProblem.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package ai.learning.q;


import ai.statemachine.actions.Action;
import gameObjects.GameObject;
import utils.TwoTuple;

/**
 * The Class ReinforcementProblem. 
 */
public abstract class ReinforcementProblem {
	

	/** list of all actions in game??? */
	Action[] actions;

	/** GameObject doing the tests*/
	GameObject obj;

	
	/**
	 * Reinforcement problem.
	 *
	 * @param obj the obj
	 */
	public ReinforcementProblem(GameObject obj) {
		this.obj = obj;
	}
	

	/**
	 * Gets the variable "RandomState".
	 *
	 * @return state - RandomState
	 */
	public abstract State getRandomState() ;

	
	/**
		 * Gets the variable "CurrentState".
		 *
		 * @return state - CurrentState
		 */
		public abstract State getCurrentState();
	

	/**
	 * Gets the variable "AvailableActions".
	 *
	 * @param state the state
	 * @return string[] - AvailableActions
	 */
	public  abstract String[] getAvailableActions(State state); 

	
	/**
	 * Perform the action and return the new state and the reinforcement value for said action 
	 * on the given state
	 *
	 * @param state the state
	 * @param action the action
	 * @return two tuple
	 */ 
	public abstract TwoTuple<Float, State> takeAction(State state,String action);

}
