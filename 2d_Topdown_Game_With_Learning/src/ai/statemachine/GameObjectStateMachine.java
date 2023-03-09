/**
 * Title: GameObjectStateMachine.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 16, 2022
 */
package ai.statemachine;


import gameObjects.GameObject;
import tiles.TileManager;

/**
 * The Class GameObjectStateMachine. State Machine designs should be made into their own 
 * classes using this blueprint.
 */
public abstract class GameObjectStateMachine {
	
	/** The obj. */
	protected GameObject obj;
	
	/** The tm. */
	protected TileManager tm;
	
	/**
	 * Game object state machine.
	 *
	 * @param obj the obj
	 * @param tm the tm
	 */
	public GameObjectStateMachine(GameObject obj,TileManager tm) {
		this.obj = obj;
		this.tm = tm;
	}
	
	/**
	 * Creates the states.
	 * @return hierarchical state machine
	 */
	public abstract HierarchicalStateMachine createStates();

}
