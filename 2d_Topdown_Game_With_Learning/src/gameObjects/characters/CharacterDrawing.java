/**
 * Title: CharacterDrawing.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 11, 2022
 */

package gameObjects.characters;
import java.awt.Graphics;
import java.awt.image.ImageObserver;

/** 
 * This interface lays out the blueprint for how characters should render themselves
 */
public interface CharacterDrawing {
	
	/**Draw image based on facing toward camera and right 
	 * @param g -Graphics
	 * @param observer -ImageObserver
	 * @param x - used to translate the object for camera viewing
	 * @param y - used to translate the object for camera viewing
	 */
	public void drawRightFacingImage(Graphics g, ImageObserver observer,float x, float y);
	
	/**Draw image based on facing toward camera and left 
	 * @param g -Graphics
	 * @param observer -ImageObserver
	 * @param x - used to translate the object for camera viewing
	 * @param y - used to translate the object for camera viewing
	 */
	public void drawLeftFacingImage(Graphics g, ImageObserver observer,float x, float y);
	
	/**Draw image based on facing away from camera and right 
	 * @param g -Graphics
	 * @param observer -ImageObserver
	 * @param x - used to translate the object for camera viewing
	 * @param y - used to translate the object for camera viewing
	 */
	public void drawRightBackImage(Graphics g, ImageObserver observer,float x, float y);
	
	/**Draw image based on facing away from camera and left 
	 * @param g -Graphics
	 * @param observer -ImageObserver
	 * @param x - used to translate the object for camera viewing
	 * @param y - used to translate the object for camera viewing
	 */
	public void drawLeftBackImage(Graphics g, ImageObserver observer, float x, float y);
	
	
	/**
	 * public method that is used to return images from file
	 */
	public void getImages();
}
