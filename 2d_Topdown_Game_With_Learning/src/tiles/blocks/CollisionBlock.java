/**
 * Title: CollisionBlock.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 8, 2022
 */
package tiles.blocks;

import java.awt.image.BufferedImage;

import ai.steeringbehaviors.SteeringOutput;
import gameObjects.ID;
import gameObjects.collisions.CollisionLogic;
import gameObjects.collisions.shapes.Rectangle;
import gameObjects.collisions.shapes.Shape;

/**
 * The Class CollisionBlock.
 */
public class CollisionBlock extends Block implements CollisionLogic{
	
	/** The block. */
	Rectangle block;

	/**
	 * Collision block.
	 *
	 * @param img the img
	 * @param x the x
	 * @param y the y
	 * @param width the width
	 * @param height the height
	 * @param id the id
	 */
	public CollisionBlock(BufferedImage img, int x, int y, int width,
			int height, ID id) {
		super(img, x, y, width, height, id);
	}

	/**
	 * Gets the variable "Shapes".
	 *
	 * @return shape[] - Shapes
	 */
	@Override
	public Shape[] getShapes() {
	Shape[] shapes = new Shape[1];
	shapes[0] = block;
		return shapes;
	}

	@Override
	public void movement(float elapsedTime, SteeringOutput output) {
		// TODO Auto-generated method stub
		
	}

}
