/**
 * Title: Heuristic.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 4, 2022
 * *derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.steeringbehaviors.pathfinding;

/**
 * The Class Heuristic.
 */
public class Heuristic {

	/** stores the goal node that this heuristic is estimating for */
	PathNode goalNode;
	
	
	/**
	 * Heuristic.
	 *
	 * @param goalNode the goal node
	 */
	public Heuristic(PathNode goalNode) {
		this.goalNode = goalNode;
	}
	/** 
	 * Estimated cost to reach the stored goal from the given node 
	 * @param fromNode - the current Node 
	 * @return float - the distance between the goal node and the fromNode
	 *
	 */
	public float estimate(PathNode fromNode) {
		return estimate(fromNode, goalNode);
	}
	
	/**
	 * Estimate the cost to move between any two nodes 
	 * @param fromNode - current Node 
	 * @param toNode - Where the path is going 
	 * @return float - the distance between the nodes 
	 */
	public static float estimate(PathNode fromNode, PathNode toNode) {
		//return the length between the two nodes 
		return toNode.getPosition().subtractTwoVectors(fromNode.getPosition()).getLength();
	}
	
}
