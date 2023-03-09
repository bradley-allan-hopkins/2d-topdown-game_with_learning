/**
 * Title: HSMBase.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 16, 2022
 * derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.statemachine;

import ai.statemachine.actions.Action;

/**
 * The Class HSMBase.
 */
public interface HSMBase {
	
	/**
	 * The Class UpdateResult.
	 */
	public class UpdateResult{
		
		/** The actions. */
		Action[] actions;
		
		/** The transition. */
		Transition transition;
		
		/** The level. */
		int level;
		
		/** 
		 * public get actions
		 * @return Array of Actions
		 */
		public Action[] getActions() {
			return actions;
		}
	}
		
	/**
	 * all classes that implement HSMBase should override with their own actions 
	 * @return action[] - Actions
	 */
	default Action[] getActions() {
		return new Action[0]; 
	}
		
	/**
	 * Update.
	 * @return update result
	 */
	default UpdateResult update() {
		UpdateResult result = new UpdateResult();
		result.actions = getActions();
		result.transition = null;
		result.level = 0;
		return result;
	}
	
	/**
	 * Gets the variable "States".
	 * @return h state[] - States
	 */
	public abstract HSMState[] getStates();

	/**
	 * public method that get the parent of this state
	 * @return the parent
	 */
	public HSMBase getParent();


}
