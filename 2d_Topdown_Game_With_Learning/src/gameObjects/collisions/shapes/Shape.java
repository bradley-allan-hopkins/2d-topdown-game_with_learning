/**
 * Title: Shape.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 1, 2022
 */
package gameObjects.collisions.shapes;

import java.io.Serializable;

import gameObjects.GameObject;
import gameObjects.ID;

import gameObjects.collisions.CollisionResolution;
import math.Vector2;

/**
 * The Class Shape.
 */
public abstract class Shape extends GameObject implements Serializable{
	
	/**
	 *  Reference to GameObject that is holding shape.
	 */
	GameObject gameObject;
	
	
	/**
	 * Shape.
	 *
	 * @param holder the holder
	 * @param position the position
	 * @param unitWidth the unit width
	 * @param unitHeight the unit height
	 * @param speed the speed
	 * @param id the id
	 */
	public Shape(GameObject holder,Vector2 position, float unitWidth, float unitHeight,float speed, ID id) {
		super(position, unitWidth, unitHeight,speed, id);
		this.gameObject = holder;
	}
	

	@Override
	public CollisionResolution getCollisionResolution() {
		return new CollisionResolution();
	}
}
