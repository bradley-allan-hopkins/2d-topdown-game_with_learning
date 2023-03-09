/**
 * Title: NotCondition.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package ai.statemachine.conditions;

/**
 * The Class NotCondition. This condition returns true when the condition is false (not true)
 */
public class NotCondition extends Condition {

	/** The condition. */
	Condition condition;
	
	/**
	 * Not condition.
	 *
	 * @param condition the condition
	 */
	public NotCondition(Condition condition) {
		this.condition = condition;
	}
	
	@Override
	public boolean test() {
		return !condition.test();
	}

}
