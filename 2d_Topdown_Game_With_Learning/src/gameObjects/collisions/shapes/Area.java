/**
 * Title: Area.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 4, 2022
 */
package gameObjects.collisions.shapes;

import gameObjects.GameObject;
import gameObjects.ID;
import math.Vector2;
/**
 * The Class Area. This class is an area in the game (ei. house, gameboard, field)
 */
public class Area extends Rectangle {

	/** The name of the area in game  */
	private String name;
	
	/**
	 * Area.
	 *
	 * @param holder the holder
	 * @param position the position
	 * @param width the width
	 * @param height the height
	 * @param speed the speed
	 * @param id the id
	 * @param name the name
	 */
	public Area(GameObject holder, Vector2 position, float width, float height,
			float speed, ID id,String name) {
		super(holder, position, width, height, speed, id);
		this.name = name;
	}

	/**
	 * Public method used to get
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Public method used to set
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
}
