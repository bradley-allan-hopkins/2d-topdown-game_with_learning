/**
 * Title: CollisionLogic.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 1, 2022
 */
package gameObjects.collisions;

import gameObjects.collisions.shapes.Shape;

// TODO: Auto-generated Javadoc
/**
 * The Interface CollisionLogic. Every object that has collision will be holding a shape that 
 * collides (head(circle), body(rectangle), etc)
 */
public interface CollisionLogic {
	
	/**
	 * Gets the variable "Collisions". 
	 *@return shape[] - Collisions
	 */
	public Shape[] getShapes();

}
