/**
 * Title: Testing.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 12, 2022
 */
package gameObjects;

import java.awt.event.KeyEvent;

import game.MyKeyAdapter;
import state.states.PlayState;

/**
 * The Interface Testing. Use boolean to engage testing if statements throughout code
 */
public abstract class Testing {

	/** The view bounding boxes. */
	public static boolean viewBoundingBoxes = false;

	/** View the Path Traveled */
	public static boolean viewPathFinding = false;

	/** View Graph Grid */
	public static boolean viewCompleteGraph = false;

	/**View Graph Nodes */
	public static boolean viewGraphNodes = false;

	/**View Tile Grid Number */
	public static boolean viewTileNumber = false;
	
	/** View mouse Positions */
	public static boolean viewMousePositions = false;
	
	/**Learning */
	public static boolean useLearning = true;
	
	/** Testing */
	public static boolean testing = false;

	/**
	 * Method that checks input and changes the Testing variables based on keys pressed
	 */
	public static void input() {
		if (MyKeyAdapter.isKey(KeyEvent.VK_F12)) {
			if (Testing.testing) {
				Testing.testing = false;
				System.out.println("Testing OFF");
			}
			else {
				Testing.testing = true;
				System.out.println("Testing ON");
			}
		}
		if (MyKeyAdapter.isKey(KeyEvent.VK_F7)) {
			if (Testing.useLearning) {
				Testing.useLearning = false;
				System.out.println("Learning OFF");
				PlayState.display.timer = 50;
			}
			else {
				Testing.useLearning = true;
				System.out.println("Learning ON");
				PlayState.display.timer = 50;
			}
		}
		//only allow testing if testing is enabled
		if (testing) {
			if (MyKeyAdapter.isKey(KeyEvent.VK_F1)) {
				if (Testing.viewBoundingBoxes) {
					Testing.viewBoundingBoxes = false;
					System.out.println("View Bounding Boxes OFF");
				}
				else {
					Testing.viewBoundingBoxes = true;
					System.out.println("View Bounding Boxes ON");
				}
			}
			if (MyKeyAdapter.isKey(KeyEvent.VK_F2)) {
				if (Testing.viewGraphNodes) {
					Testing.viewGraphNodes = false;
					System.out.println("View Graph Nodes OFF");
				}
	
				else {
					Testing.viewGraphNodes = true;
					System.out.println("View Graph Nodes ON");
				}
			}
	
			if (MyKeyAdapter.isKey(KeyEvent.VK_F3)) {
				if (Testing.viewCompleteGraph){
					Testing.viewCompleteGraph = false;
					System.out.println("View Graph Mesh OFF");
				}
				else {
					Testing.viewCompleteGraph = true;
					System.out.println("View Graph Mesh ON");
				}
			}
			if (MyKeyAdapter.isKey(KeyEvent.VK_F4)) {
				if (Testing.viewPathFinding) {
					Testing.viewPathFinding = false;
					System.out.println("View Path Finding OFF");
				}
				else {
					Testing.viewPathFinding = true;
					System.out.println("View Path Finding ON");
				}
			}
			if (MyKeyAdapter.isKey(KeyEvent.VK_F5)) {
				if (Testing.viewTileNumber) {
					Testing.viewTileNumber = false;
					System.out.println("View Tile Number OFF");
				}
				else {
					Testing.viewTileNumber = true;
					System.out.println("View Tile Number ON");
				}
			}
			if (MyKeyAdapter.isKey(KeyEvent.VK_F6)) {
				if (Testing.viewMousePositions) {
					Testing.viewMousePositions = false;
					System.out.println("View Mouse Positions OFF");
				}
				else {
					Testing.viewMousePositions = true;
					System.out.println("View Mouse Positions ON");
				}
			}

		}

	}

}
