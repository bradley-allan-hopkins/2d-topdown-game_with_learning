/**
 * Title: Connection.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 4, 2022
 * *derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.steeringbehaviors.pathfinding;

/**
 * The Class Connection.
 */
public class Connection {

	/** cost of connection*/
	float cost;
	
	/** the node the connection is coming from */
	PathNode fromNode;
	
	/** the node the connection is going to */
	PathNode toNode;
	
	/**
	 * Connection.
	 *
	 * @param cost the cost
	 * @param fromNode the from node
	 * @param toNode the to node
	 */
	public Connection(float cost, PathNode fromNode, PathNode toNode) {
		this.cost = cost;
		this.fromNode = fromNode;
		this.toNode = toNode;
	}
	
	/** 
	 * Method that returns the cost 
	 * @return float - the cost of the connection
	 */
	public float getCost() {
		return cost;
	}
	
	/**
	 * Method that returns the from Node 
	 * @return PathNode - the node the connection is coming from 
	 */
	public PathNode getFromNode() {
		return fromNode;
	}
	
	/**
	 * Method that returns the to Node .
	 *
	 * @return PathNode - the node the connection is going to
	 */
	public PathNode getToNode() {
		return toNode;
	}
	
	@Override 
	public String toString() {
		return "Cost:" + cost + " ,FromNode:" + fromNode + " ,ToNode:" + toNode;
	}
}
