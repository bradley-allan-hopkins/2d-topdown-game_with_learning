/**
 * Title: PathFinding.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 4, 2022
 *derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.steeringbehaviors.pathfinding;

import gameObjects.Testing;


/**
 * The Class PathFinding.
 */
public class PathFinding {
	
	
	/**
	 * Path finding.
	 *
	 */
	public PathFinding() {
		
	}
	
	//this structure is used to keep track of the information we need for each node 
	class NodeRecord implements Comparable<NodeRecord>{
		
		PathNode node;
		Connection connection;
		float costSoFar;
		float estimatedTotalCost;
		
		@Override
		public int compareTo(NodeRecord record) {
			if (this.estimatedTotalCost > record.estimatedTotalCost)return 1;
			else if (this.estimatedTotalCost < record.estimatedTotalCost)return -1;
			return 0;
		}
				
		public PathNode getNode() {
			return node;
		}
		
		@Override
		public String toString() {
			return "Node #:" + node.getNum() + " , Cost so Far:"
					+ costSoFar + " ,EST:" + estimatedTotalCost;
		}
	}
			

	/**
	 * 
	 * Method that finds the path of least cost 
	 * @param graph - the graph to use 
	 * @param start - the start node 
	 * @param goal - the goal node 
	 * @param heuristic - the heuristic being used 
	 * @return the connections to travel the path
	 */
	public Connection[] pathFindAStar(Graph graph, PathNode start, PathNode goal, Heuristic heuristic) {
		
		//Initialize the record for the start node
		NodeRecord startRecord = new NodeRecord();
		startRecord.node = start;
		startRecord.connection = null;
		startRecord.costSoFar = 0;
		startRecord.estimatedTotalCost = heuristic.estimate(start);
		
		//Initialize the open and closed lists 
		NodeRecordList open = new NodeRecordList();
;
		open.add(startRecord);
		NodeRecordList closed = new NodeRecordList();

		
		NodeRecord endNodeRecord;
		float endNodeHeuristic;
		NodeRecord current = null;
		
		//Iterate through processing each node 
		while (open.size() > 0) {
			//find the smallest element in the open list using the estimatedTotalCost
			current = open.min();
			
			//if it is the goal node, then terminate 
			if (current.node == goal) {
				break;
			}
			
			//otherwise get its outgoing connections 
			Connection[] connections = graph.getConnections(current.node);
			
			
			//loop through each connection in turn 
			for (Connection connection:connections) {
				//System.out.println("CHECKING CONNECTION: " + connection);
				PathNode endNode = connection.getToNode();
				float endNodeCost = current.costSoFar + connection.getCost();
				
				//if the node is closed we may have to skip, or remove it from the closed list
				if (closed.contains(endNode)) {
					//here we find the record in the closed list 
					endNodeRecord = closed.findNode(endNode);
					//if we didn't find a shorter route
					if (endNodeRecord.costSoFar <= endNodeCost) continue;
					
					//otherwise remove it from the closed list 
					closed.removeNode(endNode);
					
					//we can use the nodes old cost values to calculate its heuristic 
					//without calling the possibly expensive heuristic function
					endNodeHeuristic = endNodeRecord.estimatedTotalCost - 
							endNodeRecord.costSoFar;
				}
				//skip if the node is open and we've not found a better route
				else if (open.contains(endNode)) {
					//here we find the record in the open list corrosponding to the endNode
					endNodeRecord = open.findNode(endNode);
					if (endNodeRecord.costSoFar <= endNodeCost)continue;
					
					//again, we can calculate the heuristic 
					endNodeHeuristic = endNodeRecord.estimatedTotalCost - 
							endNodeRecord.costSoFar;
				}
				else {
					endNodeRecord = new NodeRecord();
					endNodeRecord.node = endNode;
					
					//we then calculate the heuristic value using the method since we 
					//don't have an existing record to use 
					endNodeHeuristic = heuristic.estimate(endNode);
				}
				
				//we're here if we need to update the node. Update the cost,estimate and
				//connection
				endNodeRecord.costSoFar = endNodeCost;
				endNodeRecord.connection = connection;
				endNodeRecord.estimatedTotalCost = endNodeCost + endNodeHeuristic;
				
				//and add it to the open list 
				if (!open.contains(endNode)) {
					//System.out.println("ADDED ENDNODE:" + endNodeRecord);
					//open.push(endNodeRecord);
					open.add(endNodeRecord);
				}
				

			}//end of for loop 
			
			//we've finished looking at the connections for the current node, so add it to the 
			//closed list and remove from the open list
			
			open.remove(current);

			closed.add(current);
		}
		//we're here if we've either found the goal, or if we've no more nodes to search
		if (current == null || current.node != goal) {
			//we have run out of nodes without finding the goal: no solution
			return null;
		}
		Connection[] path = new Connection[0];
		//while the current node is not equal to (pointing to the same object)
		while (current.node != start) {
			path = addToArray(path,current.connection);
			//traverse through closed list to find all NodeRecords
			current = closed.findNode(current.connection.getFromNode());
			
		//traverse through both lists for testing 
		if (Testing.viewGraphNodes) {
			for (int i = 0;i < open.size();i++) {
				open.get(i).node.open = true;
			}
			for (int i = 0;i < closed.size();i++) {
				closed.get(i).node.closed = true;
			}
		}
		}
		return reverse(path);
	}
	
	
	private static Connection[] reverse(Connection[] connections) {
		Connection temp;
		for (int i = 0;i < connections.length / 2 ;i++) {
			temp = connections[i];
			connections[i] = connections[connections.length - i -1];
			connections[connections.length - i - 1] = temp;
		}
		return connections;
	}
	
	/** 
	 * Method that allows Connection array to grow
	 */
	private Connection[] addToArray(Connection[] array,Connection connection) {
		Connection[] temp = new Connection[array.length + 1];
		for (int i = 0; i < array.length;i++) {
			temp[i] = array[i];
		}
		temp[temp.length - 1] = connection;
		return temp;
	}
	

	
	
	/**
	 * Method that tests the class  
	 * @param args - possible arguments 
	 */
	public static void main(String[] args){
		
		//PathFinding path = new PathFinding(); 
		
		//PathNode one = new PathNode(new Vector2(0,0));
		
		//Connection[] connections = new Connection[5];
		//connections[0] = new Connection(1,1,2);
		//connections[1] = new Connection(1,2,3);
		//connections[2] = new Connection(1,3,4);
		//connections[3] =  new Connection(1,4,5);
		//connections[4] = new Connection(1,5,6);
		
		//Graph graph = new Graph(connections);
		
	//	Heuristic heuristic = new Heuristic(6);
		
		//path.pathFindAStar(graph, 1, 6, heuristic);
//
				
				
	}
	
}
