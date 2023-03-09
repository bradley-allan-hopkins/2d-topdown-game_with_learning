/**
 * Title: CollisionResolution.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 11, 2022
 */
package gameObjects.collisions;

import gameObjects.GameObject;
import math.Vector2;
import utils.ThreeTuple;

/**
 * The Class CollisionResolution.
 */
public class CollisionResolution {
	
	/** The resolve. */
	public boolean resolve = false;
	

	/**
	 * Resolve collision.
	 *
	 * @param tuple the tuple
	 * @param obj the obj
	 */
	public void resolveCollision(ThreeTuple<Vector2, Vector2, Float> tuple, GameObject obj) {
		stopCollision(tuple, obj);
	}
	
	/**
	 * Method that stop the Objects from causing a collision in the first place
	 * @param tuple Contact Point, Norm, 'time'
	 * @param obj - the GameObject that is causing the collision
	 */
	@SuppressWarnings("static-method")
	public void stopCollision(ThreeTuple<Vector2, Vector2, Float> tuple, GameObject obj) {
		//dont allow penetration 
		if (tuple.b.getX() == 0 && tuple.b.getY() == 0)
			obj.getKinematic().setVelocity(tuple.b);
		else {
			tuple.b.normalize();
			Vector2 reflect = obj.getKinematic().getVelocity().reflect(tuple.b);
			Vector2 newV = obj.getKinematic().getVelocity().addTwoVectors(reflect);
			obj.getKinematic().setVelocity(newV,obj.getSpeed());
		}
	}
}
