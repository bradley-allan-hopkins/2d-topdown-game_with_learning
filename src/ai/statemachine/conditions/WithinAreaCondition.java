/**
 * Title: WithinAreaCondition.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 29, 2022
 */
package ai.statemachine.conditions;

import gameObjects.GameObject;
import gameObjects.collisions.CollisionDetection;

/**
 * The Class WithinAreaCondition. Returns true if the character is within the area bounding box
 */
public class WithinAreaCondition extends Condition {

	/** The area. */
	GameObject area;
	
	/** The character. */
	GameObject character;
	
	/**
	 * Within area condition.
	 *
	 * @param area the area
	 * @param character the character
	 */
	public WithinAreaCondition(GameObject area, GameObject character) {
		this.area = area;
		this.character = character;
	}
	
	/**
	 * Test.
	 *
	 * @return true, if successful
	 */
	@Override
	public boolean test() {
		//if they overlap it returns false;
		return CollisionDetection.testOverLap(character, area);
	}

}
