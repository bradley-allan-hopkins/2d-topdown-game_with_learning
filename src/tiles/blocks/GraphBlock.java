/**
 * Title: GraphBlock.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 18, 2022
 */
package tiles.blocks;

import ai.steeringbehaviors.SteeringOutput;
import gameObjects.ID;

/**
 * The Class GraphBlock.
 */
public class GraphBlock extends Block{

	/**
	 * Graph block.
	 *
	 * @param x the x
	 * @param y the y
	 * @param width the width
	 * @param height the height
	 * @param id the id
	 */
	public GraphBlock(int x, int y, int width, int height,ID id) {
		super(null, x, y, width, height, null);
	}

	@Override
	public void movement(float elapsedTime, SteeringOutput output) {/* Not used*/}

}
