/**
 * Title: MouseHandler.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 4, 2022
 */
package game;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import state.GameStateManager;

/**
 * The Class MouseHandler.
 */
public class MouseHandler implements MouseListener, MouseMotionListener {

	/** The mouse X. */
	private static int mouseX = -1;
	
	/** The mouse Y. */
	private static int mouseY = -1;
	
	/** The mouse B. */
	private static int mouseB = -1;
	
	/** The gm. */
	GameStateManager gm;

	/**
	 * Mouse handler.
	 *
	 * @param gm the gm
	 */
	public MouseHandler(GameStateManager gm) {
		this.gm = gm;
	}

	/**
	 * Gets the variable "Button".
	 * @return int - Button
	 */
	public static int getButton() {
		return mouseB;
	}
	/**
	 * reset button
	 */
	public static void resetButton() {
		mouseB = -1;
	}

	/**
	 * Gets the variable "X".
	 * @return int - X
	 */
	public static int getX() {
		return mouseX;
	}

	/**
	 * Gets the variable "Y".
	 * @return int - Y
	 */
	public static int getY() {
		return mouseY;
	}


	/**
	 * Mouse clicked.
	 * @param e the e
	 */
	@Override
	public void mouseClicked(MouseEvent e) {/* Not Used*/}

	/**
	 * Mouse dragged.
	 *
	 * @param e the e
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	/**
	 * Mouse entered.
	 * @param e the e
	 */
	@Override
	public void mouseEntered(MouseEvent e) {/* Not used */}

	/**
	 * Mouse exited.
	 * @param e the e
	 */
	@Override
	public void mouseExited(MouseEvent e) {/* Not used*/}

	/**
	 * Mouse moved.
	 *
	 * @param e the e
	 */
	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	/**
	 * Mouse pressed.
	 *
	 * @param e the e
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		mouseB = e.getButton();
	}

	/**
	 * Mouse released.
	 *
	 * @param e the e
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		mouseB = -1;
	}
	
	/**
	 * 
	 * Method that sets the mouse position 
	 * @param x - x coordinate
	 * @param y - y coordinate 
	 */
	public static void setMouse(int x, int y) {
		mouseX = x;
		mouseY = y;
	}
	
	/**
	 * 
	 * Method that sets the mouse button 
	 * @param i - mouse button number 1-right 
	 */
	public static void setMouseButton(int i) {
		mouseB = i;
	}
}
