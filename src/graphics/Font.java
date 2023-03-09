/**
 * Title: Font.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 21, 2022
 *  Class used from com.zerulus.game
 */
package graphics;

import java.awt.image.BufferedImage;

/**
 * The Class Font.
 */
public class Font {

	/** The fontsheet. */
	private BufferedImage FONTSHEET = null;

	/** The sprite array. */
	private BufferedImage[][] spriteArray;

	/** The tile size. */
	private final int TILE_SIZE = 32;

	/** The w. */
	public int w;

	/** The h. */
	public int h;

	/** The w letter. */
	private int wLetter;

	/** The h letter. */
	private int hLetter;

	/**
	 * Font.
	 *
	 * @param file the file
	 */
	public Font(String file) {
		w = TILE_SIZE;
		h = TILE_SIZE;

		System.out.println("Loading: " + file + "...");
		FONTSHEET = loadFont(file);

		wLetter = FONTSHEET.getWidth() / w;
		hLetter = FONTSHEET.getHeight() / h;
		loadFontArray();
	}

	/**
	 * Font.
	 *
	 * @param file the file
	 * @param w the w
	 * @param h the h
	 */
	public Font(String file, int w, int h) {
		this.w = w;
		this.h = h;

		System.out.println("Loading: " + file + "...");
		FONTSHEET = loadFont(file);

		wLetter = FONTSHEET.getWidth() / w;
		hLetter = FONTSHEET.getHeight() / h;
		loadFontArray();
	}

	/**
	 * Gets the variable "FontSheet".
	 *
	 * @return buffered image - FontSheet
	 */
	public BufferedImage getFontSheet() {
		return FONTSHEET;
	}

	/**
	 * Gets the variable "Height".
	 *
	 * @return int - Height
	 */
	public int getHeight() {
		return h;
	}

	/**
	 * Gets the variable "Letter".
	 *
	 * @param letter the letter
	 * @return buffered image - Letter
	 */
	public BufferedImage getLetter(char letter) {
		int value = letter;

		int x = value % wLetter;
		int y = value / wLetter;
		return getLetter(x, y);
	}

	/**
	 * Gets the variable "Letter".
	 *
	 * @param x the x
	 * @param y the y
	 * @return buffered image - Letter
	 */
	public BufferedImage getLetter(int x, int y) {
		BufferedImage img = FONTSHEET.getSubimage(x * w, y * h, w, h);
		return img;
	}

	/**
	 * Gets the variable "Width".
	 * @return int - Width
	 */
	public int getWidth() {
		return w;
	}

	/**
	 * Load font array.
	 */
	public void loadFontArray() {
		spriteArray = new BufferedImage[wLetter][hLetter];

		for (int x = 0; x < wLetter; x++) {
			for (int y = 0; y < hLetter; y++) {
				spriteArray[x][y] = getLetter(x, y);
			}
		}
	}

	/**
	 * Sets the height.
	 *
	 * @param i the new height
	 */
	public void setHeight(int i) {
		h = i;
		hLetter = FONTSHEET.getHeight() / h;
	}

	/**
	 * Sets the size.
	 *
	 * @param width the width
	 * @param height the height
	 */
	public void setSize(int width, int height) {
		setWidth(width);
		setHeight(height);
	}

	/**
	 * Sets the width.
	 *
	 * @param i the new width
	 */
	public void setWidth(int i) {
		w = i;
		wLetter = FONTSHEET.getWidth() / w;
	}

	/**
	 * Load font.
	 *
	 * @param file the file
	 * @return buffered image
	 */
	@SuppressWarnings("static-method")
	private BufferedImage loadFont(String file) {
		BufferedImage sprite = null;
		try {
			//System.out.println( getClass().getResource(getClass().getSimpleName() + ".class") );
			//sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
			sprite = BufferedImageLoader.loadImage(file);
		} catch (Exception e) {
			System.out.println("ERROR: could not load file: " + file + e);
		}
		return sprite;
	}
}
