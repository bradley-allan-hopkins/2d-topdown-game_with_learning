/**
 * Title: TileMap.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 19, 2022
 */
package tiles;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import gameObjects.ID;
import tiles.blocks.Block;
/**
 * The Class TileMap.
 */
public abstract class TileMap {
	
	/** The blocks. */
	protected Block[] blocks;
	
	/** The id. */
	protected ID id;
	
	/**
	 * 
	 * Method that adds blocks to the blocks array
	 * @param block - block added
	 * @param i - array location 
	 */
	public void addBlockToArray(Block block, int i) {
		blocks[i] = block;
	}
	
	/** 
	 * Method that allows Actions array to grow
	 * @param block - the block that will be added to the array 
	 */
	public void addToBlockArray(Block block) {
		if (blocks == null) blocks = new Block[0];
		Block[] temp = new Block[blocks.length + 1];
		for (int i = 0; i < blocks.length;i++) {
			temp[i] = blocks[i];
		}
		temp[temp.length - 1] = block;
		blocks = temp;
	}

	
	/**
	 * Method that 
	 * @param block - the array that replaces the current array 
	 */
	public void replaceBlocks(Block[] block) {
		this.blocks = block;
	}

	/**
	 * Gets the variable "ID".
	 *
	 * @return id - ID
	 */
	public ID getID() {
		return id;
	}
	
	/**
	 * Gets the variable "Blocks".
	 *
	 * @return block[] - Blocks
	 */
	public Block[] getBlocks() {
		return blocks;
	}
	
	/**
	 * Method that renders block onto screen 
	 * @param g the g
	 * @param observer the observer
	 * @param x - used to translate the object for camera viewing
	 * @param y - used to translate the object for camera viewing
	 */
	public void render(Graphics2D g,ImageObserver observer,float x, float y){
		for (Object block : blocks)
			if (block != null) {
				((Block) block).render(g, observer,0,0);
			}
		for (Object block : blocks)
			if (block != null && ((Block)block).mouseHovering) {
				((Block) block).getBox().viewBoundingBox(g, Color.RED,x,y);
			}
	}

	
}
