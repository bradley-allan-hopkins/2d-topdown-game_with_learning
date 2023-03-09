/**
 * Title: Condition.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 9, 2022
 * derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.statemachine.conditions;

/**
 * The Class Condition. This returns true if the condition is met
 */
public abstract class Condition {

	/**
	 * Test.
	 * @return true, if successful
	 */
	public abstract boolean test();
	
	@Override 
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
