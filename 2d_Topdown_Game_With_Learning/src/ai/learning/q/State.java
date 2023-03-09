/**
 * Title: State.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 6, 2022
 */
package ai.learning.q;

/**
 * The Class State. The current state of the agent. Could be health, position, proximity to 
 * enemies; something that
 * is important for the agent for learning 
 */
public abstract class State {
	

	/**
	 * 
	 * Method that returns the number for the state based on some calculation 
	 * @return int - the state number
	 */
	public abstract int getStateNUM(); 
	
}
