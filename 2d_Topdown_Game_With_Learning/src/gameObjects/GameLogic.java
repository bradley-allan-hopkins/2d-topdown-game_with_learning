/**
 * Title: GameLogic.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 11, 2022
 */
package gameObjects;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

/**
 * The Interface GameLogic. All GameObjects should update, render, and initialize themselves
 */
public interface GameLogic {
	
	/**
	 * Update that is performed every frame
	 * @param elapsedTime  - the time to update - should be close to 1
	 */
	public void update(float elapsedTime);
	
	
	/**
	 * Render the GameObject onto the screen
	 * @param g the g
	 * @param observer the observer
	 * @param x - used to translate the object for camera viewing
	 * @param y - used to translate the object for camera viewing
	 */
	public void render(Graphics2D g,ImageObserver observer,float x, float y);
	
	
	/**
	 * Initializes the object
	 */
	public void init();
}
