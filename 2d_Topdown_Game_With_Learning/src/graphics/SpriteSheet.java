/**
 * Title: SpriteSheet.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Jan. 30, 2022
 * @revised
 *  Partial Class used from com.zerulus.game
 */
package graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import math.Vector2;

/**
 * The Class SpriteSheet.
 */
public class SpriteSheet {

	/**
	 * The current font.
	 */
	public static Font currentFont;

	/**
	 *  Image of the SpriteSheet.
	 */
	private BufferedImage image;

	/**
	 * The spritesheet.
	 */
	private Sprite SPRITESHEET = null;

	/**
	 * The sprite array.
	 */
	private Sprite[][] spriteArray;

	/**
	 * The tile size.
	 */
	private final int TILE_SIZE = 32;

	/**
	 * The w.
	 */
	public int w;

	/** The h.*/
	public int h;

	/**
	 * The w sprite.
	 */
	private int wSprite;

	/**
	 * The h sprite.
	 */
	private int hSprite;

	/**
	 * The file.
	 */
	private String file;

	/**
	 * Sprite sheet.
	 *
	 * @param image - BufferedImage
	 */
	public SpriteSheet(BufferedImage image) {
		this.image = image;
	}

	/**
	 * Sprite sheet.
	 *
	 * @param sprite the sprite
	 * @param name the name
	 * @param w the w
	 * @param h the h
	 */
	public SpriteSheet(Sprite sprite, String name, int w, int h) {
		this.w = w;
		this.h = h;

		System.out.println("Loading: " + name + "...");
		SPRITESHEET = sprite;

		wSprite = SPRITESHEET.image.getWidth() / w;
		hSprite = SPRITESHEET.image.getHeight() / h;
		loadSpriteArray();

	}

	/**
	 * Sprite sheet.
	 *
	 * @param file the file
	 */
	public SpriteSheet(String file) {
		this.file = file;
		w = TILE_SIZE;
		h = TILE_SIZE;

		System.out.println("Loading: " + file + "...");
		SPRITESHEET = new Sprite(loadSprite(file));

		wSprite = SPRITESHEET.image.getWidth() / w;
		hSprite = SPRITESHEET.image.getHeight() / h;
		loadSpriteArray();
	}

	/**
	 * Sprite sheet.
	 *
	 * @param file the file
	 * @param w the w
	 * @param h the h
	 */
	public SpriteSheet(String file, int w, int h) {
		this.w = w;
		this.h = h;
		this.file = file;

		System.out.println("Loading: " + file + "...");
		SPRITESHEET = new Sprite(loadSprite(file));
		image = loadSprite(file);

		wSprite = SPRITESHEET.image.getWidth() / w;
		hSprite = SPRITESHEET.image.getHeight() / h;
		loadSpriteArray();
	}

	/**
	 * Draw array.
	 *
	 * @param g the g
	 * @param img the img
	 * @param pos the pos
	 * @param width the width
	 * @param height the height
	 * @param xOffset the x offset
	 * @param yOffset the y offset
	 */
	public static void drawArray(Graphics2D g, ArrayList<Sprite> img, Vector2 pos, int width,
			int height, int xOffset, int yOffset) {
		float x = pos.getX();
		float y = pos.getY();

		for (int i = 0; i < img.size(); i++) {
			if (img.get(i) != null) {
				g.drawImage(img.get(i).image, (int) x, (int) y, width, height, null);
			}

			x += xOffset;
			y += yOffset;
		}
	}

	/**
	 * Draw array.
	 *
	 * @param g the g
	 * @param f the f
	 * @param word the word
	 * @param pos the pos
	 * @param size the size
	 * @param xOffset the x offset
	 */
	public static void drawArray(Graphics2D g, Font f, String word, Vector2 pos, 
			int size, int xOffset) {
		drawArray(g, f, word, pos, size, size, xOffset, 0);
	}

	/**
	 * Draw array.
	 *
	 * @param g the g
	 * @param f the f
	 * @param word the word
	 * @param pos the pos
	 * @param width the width
	 * @param height the height
	 * @param xOffset the x offset
	 * @param yOffset the y offset
	 */
	public static void drawArray(Graphics2D g, Font f, String word, Vector2 pos, 
			int width, int height, int xOffset, int yOffset) {
		float x = pos.getX();
		float y = pos.getY();

		currentFont = f;

		for (int i = 0; i < word.length(); i++) {
			if (word.charAt(i) != 32)
				g.drawImage(f.getLetter(word.charAt(i)), (int) x, (int) y, width, height, null);

			x += xOffset;
			y += yOffset;
		}

	}

	/**
	 * Draw array.
	 *
	 * @param g the g
	 * @param word the word
	 * @param pos the pos
	 * @param size the size
	 */
	public static void drawArray(Graphics2D g, String word, Vector2 pos, int size) {
		drawArray(g, currentFont, word, pos, size, size, size, 0);
	}

	/**
	 * Draw array.
	 *
	 * @param g the g
	 * @param word the word
	 * @param pos the pos
	 * @param size the size
	 * @param xOffset the x offset
	 */
	public static void drawArray(Graphics2D g, String word, Vector2 pos, int size, int xOffset) {
		drawArray(g, currentFont, word, pos, size, size, xOffset, 0);
	}

	/**
	 * Draw array.
	 *
	 * @param g the g
	 * @param word the word
	 * @param pos the pos
	 * @param width the width
	 * @param height the height
	 * @param xOffset the x offset
	 */
	public static void drawArray(Graphics2D g, String word, Vector2 pos, int width, int height, int xOffset) {
		drawArray(g, currentFont, word, pos, width, height, xOffset, 0);
	}

	
	/**
	 * Gets the variable "Cols".
	 *
	 * @return int - Cols
	 */
	public int getCols() { return wSprite; }

	/**
	 * Gets the variable "Filename".
	 *
	 * @return string - Filename
	 */
	public String getFilename() { return file; }

	/**
	 * Gets the variable "Height".
	 *
	 * @return int - Height
	 */
	public int getHeight() { return h; }

	/**
	 * Gets the variable "NewSprite".
	 *
	 * @param x the x
	 * @param y the y
	 * @return sprite - NewSprite
	 */
	public Sprite getNewSprite(int x, int y) {
		return SPRITESHEET.getNewSubimage(x * w, y * h, w, h);
	}

	/**
	 * Gets the variable "Rows".
	 *
	 * @return int - Rows
	 */
	public int getRows() { return hSprite; }

	/**
	 * Gets the variable "Sprite".
	 *
	 * @param x the x
	 * @param y the y
	 * @return sprite - Sprite
	 */
	public Sprite getSprite(int x, int y) {
		return SPRITESHEET.getSubimage(x * w, y * h, w, h);
	}

	/**
	 * Gets the variable "Sprite".
	 *
	 * @param x the x
	 * @param y the y
	 * @param w the w
	 * @param h the h
	 * @return sprite - Sprite
	 */
	public Sprite getSprite(int x, int y, int w, int h) {
		return SPRITESHEET.getSubimage(x * w, y * h, w, h);
	}
	
	/**
	 * Gets the variable "Sprite".
	 *
	 * @param x the x
	 * @param y the y
	 * @param w the w
	 * @param h the h
	 * @return sprite - Sprite
	 */
	public Sprite getSpriteExact(int x, int y, int w, int h) {
		return SPRITESHEET.getSubimage(x, y , w, h);
	}

	/**
	 * Gets the variable "SpriteArray".
	 *
	 * @param i the i
	 * @return sprite[] - SpriteArray
	 */
	public Sprite[] getSpriteArray(int i) {
		return spriteArray[i];
	}

	/**
	 * Gets the variable "SpriteArray2".
	 *
	 * @return sprite[][] - SpriteArray2
	 */
	public Sprite[][] getSpriteArray2() {
		return spriteArray;
	}

	/**
	 * Gets the variable "SpriteSheet".
	 *
	 * @return sprite - SpriteSheet
	 */
	public Sprite getSpriteSheet() {
		return SPRITESHEET;
	}

	/**
	 * Gets the variable "Subimage".
	 *
	 * @param x the x
	 * @param y the y
	 * @param w the w
	 * @param h the h
	 * @return buffered image - Subimage
	 */
	public BufferedImage getSubimage(int x, int y, int w, int h) {
		return SPRITESHEET.image.getSubimage(x, y, w, h);
	}

	/**
	 * Gets the variable "TotalTiles".
	 *
	 * @return int - TotalTiles
	 */
	public int getTotalTiles() { return wSprite * hSprite; }

	/**
	 * Gets the variable "Width".
	 *
	 * @return int - Width
	 */
	public int getWidth() { return w; }

	/**
	 * Grab image.
	 *
	 * @param column - int - location of sprite
	 * @param row - int - location of sprite
	 * @param width - int - width of sprite
	 * @param height - int - height of sprite
	 * @return BufferedImage - returns image of Sprite
	 */
	public BufferedImage grabImage(int column, int row, int width, int height) {
		BufferedImage img = image.getSubimage((column * width) - width,
				(row * height) - height, width, height);
		return img;
	}

	/**
	 * Grab image - For tiles.
	 *
	 * @param column - int - location of sprite
	 * @param row - int - location of sprite
	 * @param width - int - width of sprite
	 * @param height - int - height of sprite
	 * @return BufferedImage - returns image of Sprite
	 */
	public BufferedImage grabImageTile(int column, int row, int width, int height) {
		BufferedImage img = image.getSubimage(column * width, row * height , width , height);
		return img;
	}

	/**
	 * Load sprite array.
	 */
	public void loadSpriteArray() {
		spriteArray = new Sprite[hSprite][wSprite];

		for (int y = 0; y < hSprite; y++) {
			for (int x = 0; x < wSprite; x++) {
				spriteArray[y][x] = getSprite(x, y);
			}
		}
	}

	/**
	 * Sets the effect.
	 *
	 * @param e the new effect
	 */
	@SuppressWarnings("static-method")
	public void setEffect(Sprite.effect e) {
		System.out.println("SPRITESHEET - setEffect() = DOES NOTHING");
		//SPRITESHEET.setEffect(e);
	}

	/**
	 * Sets the height.
	 *
	 * @param i the new height
	 */
	public void setHeight(int i) {
		h = i;
		hSprite = SPRITESHEET.image.getHeight() / h;
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
		wSprite = SPRITESHEET.image.getWidth() / w;
	}


	/**
	 * Load sprite.
	 *
	 * @param file the file
	 * @return buffered image
	 */
	@SuppressWarnings("static-method")
	private BufferedImage loadSprite(String file) {
		BufferedImage sprite = null;
		try {
			//sprite = ImageIO.read(getClass().getClassLoader().getResourceAsStream(file));
			sprite = BufferedImageLoader.loadImage(file);
		} catch (Exception e) {
			System.out.println("ERROR: could not load file: " + file + e);
		}
		return sprite;
	}
}
