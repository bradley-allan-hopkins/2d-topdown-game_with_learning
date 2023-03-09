/**
 * Title: NormalBlock.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 8, 2022
 */
package tiles.blocks;

import java.awt.image.BufferedImage;

import ai.steeringbehaviors.SteeringOutput;
import gameObjects.ID;

/**
 * The Class NormalBlock.
 */
public class NormalBlock extends Block {

	/**
	 * Normal block.
	 *
	 * @param img the img
	 * @param x the x
	 * @param y the y
	 * @param width the width
	 * @param height the height
	 * @param id the id
	 */
	public NormalBlock(BufferedImage img, int x, int y, int width, int height,
			ID id) {
		super(img, x, y, width, height, id);
	}

	@Override
	public void movement(float elapsedTime, SteeringOutput output) {
		// TODO Auto-generated method stub
		
	}

}
