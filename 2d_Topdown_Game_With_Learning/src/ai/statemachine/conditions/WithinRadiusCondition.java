/**
 * Title: WithinRadiusCondition.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 29, 2022
 */
package ai.statemachine.conditions;

import gameObjects.GameObject;
/**
 * The Class WithinRadiusCondition. Returns true if the object is within a radius of another 
 * object 
 */
public class WithinRadiusCondition extends Condition {

	/** The obj 1. */
	GameObject obj1;
	
	/** The obj 2. */
	GameObject obj2;
	
	/** The radius. */
	float radius;
	
	/**
	 * Within radius condition.
	 *
	 * @param obj1 the obj 1
	 * @param obj2 the obj 2
	 * @param radius the radius
	 */
	public WithinRadiusCondition(GameObject obj1, GameObject obj2,float radius) {
		this.obj1 = obj1;
		this.obj2 = obj2;
		this.radius = radius;
	}
	
	/**
	 * Test.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean test() {
		return obj1.getPosition().subtractTwoVectors(obj2.getPosition()).getLength() <= radius;
	}

}
