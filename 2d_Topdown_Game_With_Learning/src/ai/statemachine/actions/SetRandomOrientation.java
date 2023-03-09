/**
 * Title: SetRandomOrientation.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 26, 2022
 */
package ai.statemachine.actions;

import gameObjects.GameObject;
import math.Vector2;
import utils.Random;

/**
 * The Class SetRandomOrientation. This class sets the orientation randomly between a range.
 * The Range is counterclockwise 
 */
public class SetRandomOrientation extends Action {
	
	/** The start range. */
	float startRange;
	
	/** The end range. */
	float endRange;
	
	
	/** The GameObject */
	GameObject obj;
	
	/**
	 * Sets the random orientation.
	 * @param obj - the object that will change its orientation 
	 * @param startRange the start range
	 * @param endRange the end range
	 */
	public SetRandomOrientation(GameObject obj, float startRange, float endRange) {
		this.obj = obj;
		this.startRange = startRange;
		this.endRange = endRange;
	}

	@Override
	public void performAction(float elapsedTime) {
		float range = endRange - startRange;
		
		//stop movement for turn
		obj.getKinematic().setVelocity(new Vector2(0,0));
		obj.getKinematic().setOrientation(random(range));
	}
	
	private float random(float range) {
		float random = Random.randomBinomial();
		random = Math.abs(random);
		float rand = range * random;
		return startRange + rand;//turn by rand 
	}

}
