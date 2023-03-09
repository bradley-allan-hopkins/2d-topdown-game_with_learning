/**
 * Title: Test.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package ai.statemachine.actions;

/**
 * The Class Test. This action allows the programmer to test if an action is activated
 */
public class Test extends Action {
	
	/** The test. */
	String test = "THIS TEST ACTION FIRED";
	
	/**
	 * Instantiates a new test().
	 */
	public Test() {}
	
	/**
	 * Test.
	 *
	 * @param test the test
	 */
	public Test(String test) {
		this.test = test;
	}

	@Override
	public void performAction(float elapsedTime) {
		System.out.println(test);
	}

}
