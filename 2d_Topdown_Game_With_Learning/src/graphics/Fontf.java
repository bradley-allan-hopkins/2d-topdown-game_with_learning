/**
 * Title: Fontf.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 21, 2022
 *  Class used from com.zerulus.game
 */
package graphics;

import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.io.InputStream;
import java.util.HashMap;


/**
 * The Class Fontf.
 */
public class Fontf {

	/** The fonts. */
	private HashMap<String, Font> fonts;

	/**
	 * Instantiates a new fontf().
	 */
	public Fontf() {
		fonts = new HashMap<String, Font>();
	}

	/**
	 * Gets the variable "Font".
	 *
	 * @param name the name
	 * @return font - Font
	 */
	public Font getFont(String name) {
		return fonts.get(name);
	}

	/**
	 * Load font.
	 *
	 * @param path the path
	 * @param name the name
	 */
	public void loadFont(String path, String name) {
		try {
			System.out.println("Loading: " + path + "...");
			//Font customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getClassLoader().getResourceAsStream(path));
			InputStream input = getClass().getResourceAsStream(path);
			Font customFont = Font.createFont(Font.TRUETYPE_FONT, input);
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
			ge.registerFont(customFont);

			Font font = new Font(name, Font.PLAIN, 32);

			fonts.put(name, font);
			input.close();
		} catch (Exception e) {
			System.out.println("ERROR: ttfFont - can't load font " + path + "...");
			e.printStackTrace();
		}
	}

}