/**
 * Title: GrowObject.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 29, 2022
 */
package ai.statemachine.actions;

import gameObjects.GameObject;

/**
 * The Class GrowObject. This action grows an object by changing the GameObject's width and 
 * height 
 */
public class GrowObject extends Action {
	
	/** The timer. */
	Timer timer;
	
	/** The object. */
	GameObject object;
	
	/** The grow time. */
	float growTime;
	
	/** The growth. */
	float growth;
	
	/**
	 * Grow object.
	 *
	 * @param object the object
	 * @param growTime the grow time
	 * @param growth the growth
	 */
	public GrowObject(GameObject object,float growTime,float growth) {
		this.growth = growth;
		this.growTime = growTime;
		this.timer = new Timer();
		this.object = object;
	}


	@Override
	public void performAction(float elapsedTime) {
		timer.performAction(1);
		if (timer.timeElapsed() >= growTime) {
			object.setUnitWidth((int) (object.getUnitWidth() * growth));
			object.setUnitHeight((int) (object.getUnitHeight() * growth));
			timer.resetTimer();
		}
	}

}
