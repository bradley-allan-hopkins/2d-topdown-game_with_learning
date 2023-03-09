/**
 * Title: Collision.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 10, 2022
 * *derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.steeringbehaviors.collision;

import math.Vector2;

/**
 * The Class Collision. The object holds the position of intersection for a collision and 
 * the normal vector of the intersected side of the object intersected. 
 */
public class Collision {
	
	/** The position. */
	private Vector2 position;
	
	/** The normal. */
	private Vector2 normal;
	
	/**
	 * Collision.
	 *
	 * @param position the position
	 * @param normal the normal
	 */
	public Collision(Vector2 position, Vector2 normal) {
		this.position = position;
		this.normal = normal;
	}
	
	/**
	 * Gets the variable "Position".
	 * @return vector 2 - Position
	 */
	public Vector2 getPosition() {
		return position;
	}
	
	/**
	 * Gets the variable "Normal".
	 * @return vector 2 - Normal
	 */
	public Vector2 getNormal() {
		return normal;
	}
}
