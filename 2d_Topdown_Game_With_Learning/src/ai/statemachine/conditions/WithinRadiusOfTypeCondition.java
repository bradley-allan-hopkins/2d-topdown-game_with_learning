/**
 * Title: WithinRadiusOfCorpseCondition.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 25, 2022
 */
package ai.statemachine.conditions;

import java.util.List;

import gameObjects.GameObject;

/**
 * The Class WithinRadiusOfCorpseCondition. This condition checks if the object is within 
 * radius of a Type 
 */
public class WithinRadiusOfTypeCondition extends WithinRadiusOfObjectsCondition {

	String classType;
	/**
	 * Within radius of corpse condition.
	 *
	 * @param obj1 the obj 1
	 * @param objects the objects
	 * @param radius the radius
	 * @param c - the class 
	 */
	public WithinRadiusOfTypeCondition(GameObject obj1,
			List<GameObject> objects, float radius,Class<?> c) {
		super(obj1, objects, radius);
		//convert Class to String for easier comparison 
		this.classType = c.getSimpleName();
	}
	

	@Override
	public boolean test() {
		for (int i = 0; i < objects.size();i++) {
			GameObject obj2 = objects.get(i);
			//check against type
			if (obj2.getClass().getSimpleName().equalsIgnoreCase(classType)){
				if (obj1.getPosition().subtractTwoVectors(obj2.getPosition()).getLength() < radius){
					System.out.println("Close To Corpse");
					close = objects.get(i);
					return true;
				}
			//}
			}
		}
		return false;
	}
}
