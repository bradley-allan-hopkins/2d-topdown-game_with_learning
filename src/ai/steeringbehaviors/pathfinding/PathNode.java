/**
 * Title: Node.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 5, 2022
 */
package ai.steeringbehaviors.pathfinding;

import math.Vector2;

/**
 * The Class Node.
 */
public class PathNode {

	/** The position. */
	private Vector2 position;
	
	/** if node checked */
	boolean checked;
	
	boolean open;
	boolean closed;
	
	/** The number that can not be changed and which is unique*/
	private final int number;
	
	/**
	 * Node.
	 *
	 * @param position the position
	 * @param number - number of the node 
	 */
	public PathNode(Vector2 position,int number) {
		this.position = position;
		this.number = number;
	}
	
	/**
	 * 
	 * Method that returns the position of the node the game world
	 * @return Vector2 position
	 */
	public Vector2 getPosition() {
		return position;
	}
	
	/**
	 * Method that returns the unique number of the node
	 * @return int
	 */
	public int getNum() {
		return number;
	}
	
	@Override 
	public String toString() {
		return "#:" + number + " @ position:" + position;
	}
	
	
}
