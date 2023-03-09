/**
 * Title: Block.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Jan. 30, 2022
 * @revised Jan. 30, 2022
 */
package tiles.blocks;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import gameObjects.GameObject;
import gameObjects.ID;
import gameObjects.Testing;
import math.Vector2;
import tiles.PositionToGrid;


/**
 * The Class Block. Blocks are used for the map and objects used in the map such as rocks, 
 * holes, doors, etc
 */
public abstract class Block extends GameObject{

	/** boolean value to represent if the mouse is over the current position of the block*/
	public boolean mouseHovering = false;
	/** The image for the block */
	protected BufferedImage img;

	/**
	 * Block 
	 *
	 * @param img - Sprite
	 * @param x - x position
	 * @param y - y position
	 * @param width - width of the block
	 * @param height - height of the block
	 * @param id - ID for searching purposes
	 */
	public Block(BufferedImage img, int x, int y, int width, int height, ID id) {
		super(new Vector2(x + width/2, y + height/2), width, height,0,id);
		this.img = img;
	}

	/**
	 * Gets the variable "Image".
	 * @return sprite - Image
	 */
	public BufferedImage getImage() {
		return img;
	}

	@Override
	public void render(Graphics2D g,ImageObserver observer,float x, float y) {
		g.drawImage(img, (int)(getPosX() - unitWidth/2 - x), (int) 
		(getPosY() - unitHeight/2 - y), (int)unitWidth, (int)unitHeight, null);
		if (Testing.viewBoundingBoxes) {
			if (getID() == ID.CollisionBlock || mouseHovering) {
			}
		}

		if (mouseHovering) {
			box.viewBoundingBox(g, null, x, y);
		}
		
		if (Testing.viewTileNumber) {
			int location = PositionToGrid.getGridLocation(getPosition());
			g.drawString("" + location, getPosX(), getPosY());
		}
	}
	
	@Override
	public void update(float e) {/* Not Used */}
		
	@Override
	public void init() {/* Not Used */}
	
	@Override 
	public String toString() {
		return this.getClass().getSimpleName();
	}

}
