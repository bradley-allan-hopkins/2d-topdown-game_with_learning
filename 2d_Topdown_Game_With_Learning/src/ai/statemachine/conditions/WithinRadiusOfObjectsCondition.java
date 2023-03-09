/**
 * Title: WithinRadiusOfObjectsCondition.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 14, 2022
 */
package ai.statemachine.conditions;

import java.util.List;

import gameObjects.GameObject;

// TODO: Auto-generated Javadoc
/**
 * The Class WithinRadiusOfObjectsCondition. This class tests if a object is within range of 
 * any object in a array of gameObjects. Array could be a single type of different objects or 
 * different types of a gameObject 
 */
public class WithinRadiusOfObjectsCondition extends Condition{

	/** The obj 1. */
	protected GameObject obj1;
	
	/** The objects. */
	protected List<GameObject> objects;
	
	/**
	 *  Object Within Radius.
	 */
	protected GameObject close;
	
	/** The radius. */
	protected float radius;
	
	/**

	/**
	 * Within radius of objects condition.
	 *
	 * @param obj1 the obj 1
	 * @param objects the objects
	 * @param radius the radius
	 */
	public WithinRadiusOfObjectsCondition(GameObject obj1, List<GameObject> objects,
			float radius) {
		this.obj1 = obj1;
		this.objects = objects;
		this.radius = radius;
	}

	/**
	 * Test.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean test() {
		for (int i = 0; i < objects.size();i++) {
			GameObject obj2 = objects.get(i);
			if (obj1.getPosition().subtractTwoVectors(obj2.getPosition()).getLength() < radius){
				close = objects.get(i);
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the variable "CloseObject".
	 *
	 * @return game object - CloseObject
	 */
	public GameObject getCloseObject() {
		return close;
	}



}
