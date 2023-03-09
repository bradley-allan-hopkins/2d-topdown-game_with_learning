/**
 * Title: HSMState.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 17, 2022
 * derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.statemachine;

import ai.statemachine.actions.Action;

/**
 * The Interface HSMState. This interface just allows all classes that implement this interface
 * to be the same base
 */
public interface HSMState extends HSMBase
{
	
	/**
	 * Gets the variable "EntryActions".
	 * @return action[] - EntryActions
	 */
	public Action[] getEntryActions();

	/**
	 * Gets the variable "ExitActions".
	 * @return action[] - ExitActions
	 */
	public Action[] getExitActions();
	
	/**
	 * Gets the variable "Transitions".
	 * @return transition[] - Transitions
	 */
	public Transition[] getTransitions();
	
	/** 
	 * get level of state
	 * @return int - the level
	 */
	public int getLevel();
	

	/**
	 * public add actions to actions array
	 * @param action - normal repeatable action
	 */
	public void addActions(Action action);

	/**
	 * public add actions to exit actions array
	 * @param action - entry action that is added
	 */
	public void addEntryActions(Action action);

	/**
	 * public add actions to exit actions array
	 * @param action that is added
	 */
	public void addExitActions(Action action);
	
	/**
	 * public add transitions to the transition array
	 * @param transition - the transition being added 
	 */
	public void addTransitions(Transition transition);
	


}
