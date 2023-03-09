/**
 * Title: OutsideRadiusCondition.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 29, 2022
 */
package ai.statemachine.conditions;

import gameObjects.GameObject;

/**
 * The Class OutsideRadiusCondition. Returns true if the object is far enough away from another 
 * object based on radius
 */
public class OutsideRadiusCondition extends Condition {

	/** The obj 1. */
	GameObject obj1;
	
	/** The condition. */
	WithinRadiusOfObjectsCondition condition;
	
	/** The radius. */
	float radius;
	
	/**
	 * Outside radius condition.
	 *
	 * @param obj1 the obj 1
	 * @param condition the condition
	 * @param radius the radius
	 */
	public OutsideRadiusCondition(GameObject obj1, 	WithinRadiusOfObjectsCondition condition
			,float radius) {
		this.obj1 = obj1;
		this.condition = condition;
		this.radius = radius;
	}
	
	/**
	 * Test.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean test() {
		if (obj1.getPosition().subtractTwoVectors(
				condition.getCloseObject().getPosition()).getLength() > radius){
			return true;
		}
		return false;
	}

}
