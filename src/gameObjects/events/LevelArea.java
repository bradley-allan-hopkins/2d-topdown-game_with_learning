/**
 * Title: LevelArea.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 29, 2022
 */
package gameObjects.events;

import gameObjects.collisions.shapes.Rectangle;
import math.Vector2;

/**
 * The Class LevelArea. This object represents a area in a game (ie. field, home, or etc)
 */
public class LevelArea extends Rectangle {

	/**
	 * Level area.
	 *
	 * @param position the position
	 * @param width the width
	 * @param height the height
	 */
	public LevelArea(Vector2 position, float width, float height) {
		super(null, position, width, height, 0, null);
	}

}
