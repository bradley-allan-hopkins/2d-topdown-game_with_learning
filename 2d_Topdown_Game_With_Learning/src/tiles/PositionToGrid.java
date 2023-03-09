/**
 * Title: PositionToGrid.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 21, 2022
 */
package tiles;

import game.Game;
import gameObjects.GameObject;
import math.Vector2;
import state.states.PlayState;
import tiles.blocks.Block;

/**
 * The Class PositionToGrid. This class is used to get an array location based on coordinates
 */
public class PositionToGrid {

	
	/**
	 * Get an array location for the map based on x and y coordinates 
	 *
	 * @param x the x
	 * @param y the y
	 * @return int - GridLocation
	 */
	public static int getGridLocation(float x, float y) {
		int tileSize = Game.GRID_TILE_SIZE * Game.SCALER;
		int i = (int)x/tileSize;
		int j = (int)y/tileSize;
		int offset = j * PlayState.tm.getWidth();
		int location = i + offset;
		return location;
	}
	
	/**
	 *Get an array location for the map based on the GameObject
	 *
	 * @param obj the obj
	 * @return int - Location
	 */
	public static int getGridLocation(GameObject obj) {
		return getGridLocation(obj.getPosition());
	}
	
	/**
	 * Get an array location for the map based on x and y coordinates 
	 * @param coordinate - center position (x,y)
	 * @return int - GridLocation
	 */
	public static int getGridLocation(Vector2 coordinate) {
		return getGridLocation(coordinate.getX(),coordinate.getY());
	}
	
	/**
	 * Gets the variable "Position".
	 *
	 * @param tm the tileManager
	 * @param gridLocation the grid location
	 * @return vector 2 - Position
	 */
	public static Vector2 getPosition(TileManager tm, int gridLocation) {
		Block[] blocks = tm.tm.get(0).getBlocks();
		if (gridLocation < blocks.length) {
			if (blocks[gridLocation] != null) return blocks[gridLocation].getPosition();
		}
		return null;
	}
}
