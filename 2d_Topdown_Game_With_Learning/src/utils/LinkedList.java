/**
 * Title: LinkedList.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 8, 2022
 */
package utils;

import java.util.Comparator;

/**
 * The Class LinkedList.
 *
 * @param <T> the generic type
 */
public class LinkedList<T> {
	
	/** The head pointer */
	protected Node head;
	
	/** The Tail pointer */
	protected Node tail;
	
	/** Middle Pointer */
	protected Node middle;//will implement feature later

	/** the size of the structure */
	protected int size;
	
	/**
	 * LinkedList - constructor 
	 */
	public LinkedList(){
		this.head = null;
		this.tail = null;
		this.middle = null;
		this.size = 0;
	}
	/** 
	 * Inner Class - node
	 */
	public class Node{
		/** Node Data */
		T data;
		/** Next Node in sequence */
		public Node next;
		/** previous Node in sequence */
		public Node previous;
		/** 
		 * Node 
		 * @param T - data 
		 */
		Node(T data){
			this.data = data;
			this.next = null;
			this.previous = null;
		}
		
		/**
		 * Gets the variable "Data".
		 *
		 * @return t - Data
		 */
		public T getData() {
			return data;
		}
		
	}
	
	/**
	 * 
	 * Method that adds a new node in order from lowest value to highest. No duplicate objects 
	 * allowed 
	 * @param data - what is added to node 
	 * @return boolean - true if data is added to list 
	 */
	public boolean add(T data) {
		Node newNode = new Node(data);
		//System.out.print("Adding:" + data + " to ");
		
		
		
		//first node - head and tail now pointing at first node
		if (head == null){
			head = newNode;
			tail = newNode;
		}
		else {
			Node currentNode = head;//start at first node 
			for (int k = 0; k < size;k++){
				if (currentNode != null){
					if (currentNode.data == data)return false;
					DefaultComparator c = new DefaultComparator();
					int value = c.compare(data,currentNode.data);
					//if data is valued less than or equal
					if (value < 1) {
						//point newNode next to the current node
						newNode.next = currentNode;
						if (currentNode.previous != null) {
							//if the current node previous is not null point the new node to it
							newNode.previous = currentNode.previous;
							currentNode.previous.next = newNode;
							currentNode.previous = newNode;
						}
						if (currentNode == head) { 
							head = newNode;
							currentNode.previous = newNode;
						}
						
						break;
					}
					//if the new Node is valued greater
					if (currentNode.next != null) {
						currentNode = currentNode.next;
					}
					else {
						currentNode.next = newNode;
						newNode.previous = currentNode;
						tail = newNode;
					}
					
				}
			}
		}
		size++;
		return true; // if no match is found,
	}
	
	/**
	 * Method that removes the Node with current data.
	 *
	 * @param data the data
	 * @return t
	 */
	public T remove(T data) {
		Node currentNode = findNode(data);
		//remove node from list
		if (currentNode == null) {
			System.out.println("DOUBLY - remove - currentNode null");
			return null;
		}
		removeNode(currentNode);
		return currentNode.data;
	}
	
	/**
	 *  private method findData will search through the list and return a node 
	 * 	that matches the data being searched. Method calling findNode is responsible
	 * 	for Node
	 *
	 * @param data the data
	 * @return Node - returns a Node that matches the data being searched
	 */
	private Node findNode(T data){
		//first check if structure is empty 
		if (size == 0)
			return null;
		//if only one node exists 
		else if (size == 1){
			if (data == head.data){//if the data matches data in the node
				return head;//return head
			}
			return null;//if first data does not match 
		}
		//if more than one node exists 
		else {
			Node currentNode = head;//start at first node 
			for (int k = 0; k < size ;k++){
				if (currentNode != null){
					//if data matches 
					if (data == currentNode.data)
						return currentNode;//return node that contains data
					else if (currentNode.next != null)
						currentNode = currentNode.next; //move to next node
				}
			}
			return null; // if no match is found,
		}
	}
	/**
	 * Method that changes the currentNode next and previous to null and sets the next 
	 * and previous nodes to point to eachother.
	 *
	 * @param currentNode the current node
	 */
	private void removeNode(Node currentNode) {
		if (head == currentNode) 
			head = currentNode.next;
		if (tail == currentNode)
			tail = currentNode.previous;
		if (currentNode.previous != null) {
			currentNode.previous.next = currentNode.next;
		}
		if(currentNode.next != null) {
			currentNode.next.previous = currentNode.previous;
		}
		currentNode.next = null;
		currentNode.previous = null;
		size--;
	}
	
	/** Private inner class - found on https://www2.cs.duke.edu/csed/poop/huff/spring05/code/PriorityQueue.java
	used to compare different objects
	*/
	private static class DefaultComparator implements Comparator<Object>{
		
		/**
		 *  method used to compare two objects by converting to an integer. The  
		 * 		method will return 0,-1 , or 1 based on compared values. 
		 *
		 * @param o1 the o 1
		 * @param o2 the o 2
		 * @return int - (0 = equal), (-1 = less than), (1 = more than)
		 */
		@SuppressWarnings({"rawtypes", "unchecked"})
		@Override
		public int compare(Object o1, Object o2){
			return ((Comparable) o1).compareTo(o2);
		}
	}
	
	/**
	 * Prints the contents.
	 */
	public void printContents() {
		printContents("");
	}
	/** 
	 * Method that is used to print all data in order of storage
	 * @param s - string for testing purposes
	 */
	public void printContents(String s) {
			System.out.print(s + " LIST CONTENTS: ");
			//first check if structure is empty 
			if (size == 0) return;
			//if only one node exists 
			else if (size == 1){
				System.out.print(head.data);
			}
			//if more than one node exists 
			else {
				Node currentNode = head;//start at first node 
				for (int k = 0; k < size ;k++){
					if (currentNode != null){
						System.out.print(currentNode.data + " ");
						if (currentNode.next != null)
							currentNode = currentNode.next; //move to next node
					}
				}
			}
			System.out.println();
		
	}
	
	/**
	 * Pop.
	 * @return t
	 */
	public T pop() {
		T temp = head.data;
		head = head.next;
		return temp;
	}
	/**
	 * public method the gets size of list
	 * @return int - the current size of the structure
	 */
	public int size() {
		return size;
	}
	
	/**
	 * return the lowest value which is the head
	 * @return the data with the lowest value
	 */
	public T min() {
		return getHead();
	}
	
	/**
	 * Get head data
	 * @return head data
	 */
	public T getHead() {
		return head.data;
	}
	
	/**
	 * get Tail data
	 * @return tail data
	 */
	public T getTail() {
		return tail.data;
	}
	
	
	/**
	 *  Public method to return data at i position.
	 *
	 * @param i - position in list
	 * @return x - data in ith position
	 */
	public T get(int i){
		return getNode(i).data;
	}
	
	/**
	 * Method that returns the node at location i.
	 *
	 * @param i - location in doublyLinked list
	 * @return node - Node
	 */
	private Node getNode(int i) {
			int halfway = size/2; // halfway mark 
			Node currentNode = null;
			if (size == 0)
				return null;
			else if (i == 0)
				currentNode = head;
			//if i is before the halfway mark start at the beginning
			else if (i <= halfway){
				currentNode = head;
				for (int k = 0; k < i;k++){
					currentNode = currentNode.next;
				}
			}
			//if i is past halfway mark start at the end 
			else {
				currentNode = tail;
				for (int k = size -1 ; k > i;k--){
				currentNode = currentNode.previous;
				
				}
			}
		
		return currentNode;
	}
	/**
	 * 
	 * Method that is used for testing 
	 * @param args - possible arguments 
	 */
	
	public static void main(String[] args) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		LinkedList<Integer> list1 = new LinkedList<Integer>();
		list.add(5);
		list.add(10);
		list.add(12);
		list.add(9);
		list.add(19);
		list.add(7);
		list.add(17);
		list.add(100);
		
		list.remove(5);
		list.add(13);
		list.add(21);
		list.remove(7);
		list1.add(list.remove(10));
		
		
		System.out.println("Head:" + list.head.data);
		System.out.println("Tail:" + list.tail.data);

		list.printContents();
		list1.printContents();
		

		//System.out.println("Size:" + list.size());
		DefaultComparator c = new DefaultComparator();
		System.out.println("COMPARE:" + c.compare(9, 10));
		
	}
}
