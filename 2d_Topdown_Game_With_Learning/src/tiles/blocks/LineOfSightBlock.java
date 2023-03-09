/**
 * Title: LineOfSightBlock.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 16, 2022
 */
package tiles.blocks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import ai.steeringbehaviors.SteeringOutput;

import gameObjects.Testing;

/**
 * The Class LineOfSightBlock. This block is used to stop the line of sight of agents. This 
 * is used for ray casting 
 */
public class LineOfSightBlock extends Block {

	/**
	 * Line of sight block.
	 *
	 * @param x the x
	 * @param y the y
	 * @param width the width
	 * @param height the height
	 */
	public LineOfSightBlock(int x, int y, int width,int height) {
		super(null, x, y, width, height, null);
	}


	@Override
	public void movement(float elapsedTime, SteeringOutput output) {/* not used */}
	

	@Override
	public void render(Graphics2D g,ImageObserver observer,float x, float y) {
		//this block is not rendered 
		if (Testing.viewBoundingBoxes) {
			if (Testing.viewBoundingBoxes) {
				box.viewBoundingBox(g, Color.BLUE,x,y);
			}
		}
	}

}
