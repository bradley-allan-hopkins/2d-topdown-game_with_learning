/**
 * Title: LocationPoint.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 16, 2022
 */
package tiles.locations;

import math.Vector2;

/**
 * The Class LocationPoint. This class is used to hold a position in the game set by the 
 * tile manager. Examples: start, finish, player positions
 */
public class LocationPoint {


	/** The name. */
	String name;
	
	/** The position. */
	Vector2 position;
	
	/**
	 * Location point.
	 *
	 * @param name the name
	 * @param position the position
	 */
	public LocationPoint(String name, Vector2 position) {
		this.name = name;
		this.position = position;
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

	/**
	 * Public method used to get
	 * @return the position
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Public method used to set
	 * @param position the position to set
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}
}
