/**
 * Title: ScreenDisplay.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 11, 2022
 */
package state.states.menu;
import java.awt.*;
import java.awt.image.ImageObserver;

import javax.swing.JPanel;

import gameObjects.Testing;

/**
 * The Class ScreenDisplay.
 */
public class ScreenDisplay extends JPanel{
	
	/** The timer. */
	public int timer = 0;//used to display on screen for 'x' frames

	/**
	 * Render player hit.
	 * @param g - graphics 
	 * @param observer - screen observer
	 */
	public void renderLearning(Graphics2D g, ImageObserver observer) {
		if (timer > 0) {
			g.setColor(Color.WHITE);
			g.setFont( new Font("Ink Free",Font.BOLD, 60));
			FontMetrics metrics = getFontMetrics(g.getFont());
			//g.drawString(damage,465 - (metrics.stringWidth
			//		(damage)/2), 700);
			if (Testing.useLearning) {
				g.drawString("LEARNING ON",500 - (metrics.stringWidth
						("LEARNING ON")/2), 500);
			}
			else {
			g.drawString("LEARNING OFF",500 - (metrics.stringWidth
					("LEARNING OFF")/2), 500);
			}
			timer--;
		}
	}
}
