/**
 * Title: AndCondition.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package ai.statemachine.conditions;

/**
 * The Class AndCondition. This condition returns true when both conditionA and conditionB 
 * return true
 */
public class AndCondition extends Condition {

	/** The condition A. */
	Condition conditionA;
	
	/** The condition B. */
	Condition conditionB;
	
	/**
	 * And condition.
	 *
	 * @param conditionA the condition A
	 * @param conditionB the condition B
	 */
	public AndCondition(Condition conditionA, Condition conditionB) {
		this.conditionA = conditionA;
		this.conditionB = conditionB;
	}
	
	@Override
	public boolean test() {
		return conditionA.test() && conditionB.test();
	}

}
