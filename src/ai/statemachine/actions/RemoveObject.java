/**
 * Title: RemoveObject.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 29, 2022
 */
package ai.statemachine.actions;

import gameObjects.GameObject;
import state.states.PlayState;

/**
 * The Class RemoveObject. This action removes an object from the entity handler 
 */
public class RemoveObject extends Action {
	
	/** The object. */
	GameObject object;
	
	/**
	 * Removes the object.
	 *
	 * @param object the object
	 */
	public RemoveObject(GameObject object) {
		this.object = object;
	}


	@Override
	public void performAction(float elapsedTime) {
		if (object == null)return;
		PlayState.entityHandler.removeObject(object);
		object = null;
	}

}
