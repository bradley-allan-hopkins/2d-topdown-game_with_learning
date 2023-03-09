/**
 * Title: NodeRecordList.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 5, 2022
 * *derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.steeringbehaviors.pathfinding;

import ai.steeringbehaviors.pathfinding.PathFinding.NodeRecord;
import utils.LinkedList;

/**
 * The Class NodeRecordList.
 */
public class NodeRecordList extends LinkedList<NodeRecord>{

	/**
	 * 
	 * Method that checks if the list contains the node 
	 * @param node - the node this method is searching for 
	 * @return true if list contains node 
	 */
	public boolean contains(PathNode node) {
		NodeRecord record = findNode(node);
		if (record == null)return false;
		return true;
	}
	
	/**
	 * Method that finds the NodeRecord containing the node 
	 * @param node - what node to search for 
	 * @return a NodeRecord containing the node if NodeRecord exists in list
	 */
	public NodeRecord findNode(PathNode node) {
		//first check if structure is empty 
		if (size == 0) return null;
		//if only one node exists 
		else if (size == 1){
			if (node.getNum() == head.getData().getNode().getNum()){//if the data matches data in the node
				return head.getData();//return head
			}
			return null;//if first data does not match 
		}
		//if more than one node exists 
		else {
			Node currentNode = head;//start at first node 
			for (int k = 0; k < size;k++){
				if (currentNode != null){
					//if data matches 
					if (node.getNum() == currentNode.getData().getNode().getNum())
						return currentNode.getData();//return A nodeRecord holding the PathNode
						
					if (currentNode.next != null)
						currentNode = currentNode.next;//if not found move to next node
				}
			}
			return null; // if no match is found,
		}
	}
	
	/**
	 * Method that removes the NodeRecord from the list containing the node 
	 * @param node - the node that is to be removed
	 * @return NodeRecord - returns the removed Record
	 */
	public NodeRecord removeNode(PathNode node) {
		NodeRecord record = findNode(node);
		if (record == null) return null;
		//call super's method to remove data
		return remove(record);
	}
	
}
