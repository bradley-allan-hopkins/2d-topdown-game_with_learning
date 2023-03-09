/**
 * 
 */
package game;

import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * Class 
 * @author bradl
 *
 */
public class Window extends Canvas{
	
	/** the Jrame for the game*/
	public JFrame frame;
	/** window height is off from canvas to jframe*/
	public static final int HEIGHT_OFFSET = 39;
	/** window width is off from canvas to jframe*/
	public static final int WIDTH_OFFSET = 16;

	
	/**
	 * Window.
	 *
	 * @param width the width
	 * @param height the height
	 * @param title the title
	 * @param game the game
	 */
	public Window(int width, int height, String title, Game game) {

		
		frame = new JFrame(title);
		
		frame.setPreferredSize(new Dimension(width + WIDTH_OFFSET, height + HEIGHT_OFFSET));
		frame.add(game);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		
		frame.setFocusable(true);
		frame.requestFocus();
		
		
		frame.setVisible(true);
		
		
	}
}
