/**
 * Title: TerrainBlock.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 8, 2022
 */
package tiles.blocks;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import ai.steeringbehaviors.SteeringOutput;
import gameObjects.ID;

/**
 * The Class TerrainBlock.
 */
public class TerrainBlock extends Block {
	
	/** The terrain cost. */
	int terrainCost;
	
	/** The tile number. */
	int tileNumber;
	
	/** The path. */
	boolean path = false;

	/**
	 * Terrain block.
	 *
	 * @param x the x
	 * @param y the y
	 * @param width the width
	 * @param height the height
	 * @param id the id
	 * @param tileNumber the tile number
	 * @param terrainCost the terrain cost
	 */
	public TerrainBlock(int x, int y, int width, int height,
			ID id,int tileNumber, int terrainCost) {
		super(null, x, y, width, height, id);
		this.tileNumber = tileNumber;
		this.terrainCost = terrainCost;
	}


	@Override
	public void render(Graphics2D g,ImageObserver observer,float x, float y) {
		//super.render(g, observer, x, y);
		if (mouseHovering) {
			box.viewBoundingBox(g, Color.RED,x,y);
		}
		if (path) {
			g.setColor(Color.MAGENTA);
			g.drawOval((int)getPosX(), (int)getPosY(), 10, 10);
		}
	}
	
	/**
	 * Get tile number
	 * @return int - tileMap number
	 */
	public int getTileNumber() {
		return tileNumber;
	}
	
	/**
	 * get Terrain cost 
	 * @return int - terrainCost for block
	 */
	public int getCost() {
		return terrainCost;
	}


	@Override
	public void movement(float elapsedTime, SteeringOutput output) {/* not used*/}
}
