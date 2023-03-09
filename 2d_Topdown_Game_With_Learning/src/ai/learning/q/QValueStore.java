/**
 * Title: QValueStore.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package ai.learning.q;

/**
 * The Class QValueStore.
 */
public abstract class QValueStore {
	
	/**
	 * Update value. This method will update the value for the action for the state. 
	 * If the state does not exist, create a new state. If the action does not exist create
	 * a new action with value
	 *
	 * @param state the state
	 * @param action the action
	 * @param value the value
	 * @return true, if successful
	 * */
	public abstract boolean storeQValue(State state, String action, float value);
	
	/**
	 * Gets the current value of the action in the state
	 *
	 * @param state the state
	 * @param action the action
	 * @return float - QValue
	 */
	public abstract float getQValue(State state, String action);
	
	/**
	 * Gets the best action for the state based on value
	 *
	 * @param state the state
	 * @return string - BestAction
	 */
	public abstract String getBestAction(State state);
	
	/**
	 * Save file.
	 *
	 * @return true, if successful
	 */
	public abstract boolean saveFile();
	
	/**
	 * Read file and load contents into array
	 *
	 * @return true, if successful
	 */
	public abstract boolean readFile();
	
	/**
	 * Size.
	 *
	 * @return int
	 */
	public abstract int size();

}
