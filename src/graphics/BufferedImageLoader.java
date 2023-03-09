/**
 * Title: BufferedImageLoader.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Jan. 31, 2022
 */
package graphics;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * The Class BufferedImageLoader. This object takes the path of the image and returns the image
 */
public class BufferedImageLoader {


	/**
	 * Load image.
	 *
	 * @param path - String - the path of the image file
	 * @return buffered image
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static BufferedImage loadImage(String path) throws IOException {
		return ImageIO.read(new File("src/resources/" + path));
	}
}
