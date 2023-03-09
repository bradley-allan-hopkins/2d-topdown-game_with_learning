/**
 * Title: Sprite.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 21, 2022
 * Class used from com.zerulus.game
 * @edit Mar 20 - added resizedImage method
 * @edit Apr 4 - added effects 
 */
package graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import math.Matrix;

/**
 * The Class Sprite.
 */
public class Sprite {

	/**
	 * The Enum effect.
	 */
	public static enum effect {
		/** The normal. */
		NORMAL, 
		/** The sepia. */
		SEPIA, 
		/** The redish. */
		REDISH, 
		/** The grayscale. */
		GRAYSCALE, 
		/** The negative. */
		NEGATIVE, 
		/** The decay. */
		DECAY,
		
		/**
		 *  Black effect.
		 */
		BLACK,
		
		/**
		 *  White Effect.
		 */
		WHITE}

	/** The image. */
	public BufferedImage image;

	/** The pixels. */
	private int[] pixels;

	/** The ogpixels. */
	private int[] ogpixels;

	/** The w. */
	private int w;

	/** The h. */
	private int h;

	/** The id. */
	private float[][] id = {{1.0f, 0.0f, 0.0f},
			{0.0f, 1.0f, 0.0f},
			{0.0f, 0.0f, 1.0f},
			{0.0f, 0.0f, 0.0f}};

	/** The negative. */

	private float[][] negative = {{1.0f, 0.0f, 0.0f},
			{0.0f, 1.0f, 0.0f},
			{0.0f, 0.0f, 1.0f},
			{0.0f, 0.0f, 0.0f}};

	/** The decay. */

	private float[][] decay = {{0.000f, 0.333f, 0.333f},
			{0.333f, 0.000f, 0.333f},
			{0.333f, 0.333f, 0.000f},
			{0.000f, 0.000f, 0.000f}};

	/** The sepia. */

	private float[][] sepia = {{0.393f, 0.349f, 0.272f},
			{0.769f, 0.686f, 0.534f},
			{0.189f, 0.168f, 0.131f},
			{0.000f, 0.000f, 0.000f}};

	/** The redish. */

	private float[][] redish = {{1.0f, 0.0f, 0.0f},
			{0.0f, 0.3f, 0.0f},
			{0.0f, 0.0f, 0.3f},
			{0.0f, 0.0f, 0.0f}};

	/** The grayscale. */

	private float[][] grayscale = {{0.333f, 0.333f, 0.333f},
			{0.333f, 0.333f, 0.333f},
			{0.333f, 0.333f, 0.333f},
			{0.000f, 0.000f, 0.000f}};

	/**
	 *  black scale.
	 */
	private float[][] black = {{0.0f, 0.0f, 0.0f},
			{0.0f, 0.0f, 0.0f},
			{0.0f, 0.0f, 0.0f},
			{0.000f, 0.000f, 0.000f}};
	
	/**
	 *  whitish scale.
	 */
	private float[][] white = {{1.0f, 1.0f, 1.0f},
			{1.0f, 1.0f, 1.0f},
			{1.0f, 1.0f, 1.0f},
			{1.000f, 1.000f, 1.000f}};

	/** The current effect. */

	private float[][] currentEffect = id;

	/**
	 * Sprite.
	 *
	 * @param image the image
	 */
	public Sprite(BufferedImage image) {
		this.image = image;
		this.w = image.getWidth();
		this.h = image.getHeight();
		ogpixels = image.getRGB(0, 0, w, h, ogpixels, 0, w);
		pixels = ogpixels;
	}

	/**
	 * Gets the variable "Height".
	 *
	 * @return int - Height
	 */
	public int getHeight() { return h; }

	/**
	 * Gets the variable "NewSubimage".
	 *
	 * @return sprite - NewSubimage
	 */
	public Sprite getNewSubimage() {
		return getNewSubimage(0, 0, this.w, this.h);
	}

	/**
	 * Gets the variable "NewSubimage".
	 *
	 * @param x the x
	 * @param y the y
	 * @param w the w
	 * @param h the h
	 * @return sprite - NewSubimage
	 */
	public Sprite getNewSubimage(int x, int y, int w, int h) {
		BufferedImage temp = image.getSubimage(x, y, w, h);
		BufferedImage newImage = new BufferedImage(image.getColorModel(), image.getRaster().createCompatibleWritableRaster(w,h), image.isAlphaPremultiplied(), null);
		temp.copyData(newImage.getRaster());
		return new Sprite(newImage);
	}

	/**
	 * Gets the variable "Subimage".
	 *
	 * @param x the x
	 * @param y the y
	 * @param w the w
	 * @param h the h
	 * @return sprite - Subimage
	 */
	public Sprite getSubimage(int x, int y, int w, int h) {
		return new Sprite(image.getSubimage(x, y, w, h));
	}

	/**
	 * Gets the variable "Width".
	 *
	 * @return int - Width
	 */
	public int getWidth() { return w; }


	/**
	 * Method that returns new Color in hex form.
	 *
	 * @param color the color
	 * @return Color
	 */
	public static Color hexToColor(String color) {
		return new Color(
				Integer.valueOf(color.substring(1, 3), 16),
				Integer.valueOf(color.substring(3, 5), 16),
				Integer.valueOf(color.substring(5, 7), 16));
	}

	/**
	 * Method that returns a resized Buffered Image.
	 *
	 * @param width - width of the image
	 * @param height - height of the image
	 * @return the new Buffered Image
	 */
	public  BufferedImage resizedImage(int width, int height) {
		int type=0;
		type = image.getType() == 0? BufferedImage.TYPE_INT_ARGB : image.getType();
		BufferedImage resizedImage = new BufferedImage(width, height,type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}

	/**
	 * Restore colors.
	 */
	public void restoreColors() {
		image.setRGB(0, 0, w, h, pixels, 0, w);
	}

	/**
	 * Restore default.
	 */
	public void restoreDefault() {
		image.setRGB(0, 0, w, h, ogpixels, 0, w);
	}

	/**
	 * Save colors.
	 */
	public void saveColors() {
		pixels = image.getRGB(0, 0, w, h, pixels, 0, w);
		//currentEffect = id;
	}




	/**
	 * Sets the brightness.
	 *
	 * @param value the new brightness
	 */
	public void setBrightness(float value) {
		float[][] effect = id;
		for(int i = 0; i < 3; i++)
			effect[3][i] = value;

		addEffect(effect);
	}

	/**
	 * Sets the contrast.
	 *
	 * @param value the new contrast
	 */
	public void setContrast(float value) {
		float[][] effect = id;
		float contrast = (259 * (value + 255)) / (255 * (259 - value));
		for(int i = 0; i < 3; i++) {
			if(i < 3)
				effect[i][i] = contrast;
			effect[3][i] = 128 * (1 - contrast);
		}

		addEffect(effect);
	}

	/**
	 * Sets the effect.
	 *
	 * @param e the new effect
	 */
	public void setEffect(effect e) {
		float[][] effect;
		switch (e) {
			case SEPIA: effect = sepia;
			break;
			case REDISH: effect = redish;
			break;
			case GRAYSCALE: effect = grayscale;
			break;
			case NEGATIVE: effect = negative;
			break;
			case DECAY: effect = decay;
			break;
			case BLACK: effect = black;
			break;
			case WHITE: effect = white;
			break;
			default: effect = id;
		}

		if(effect != currentEffect) {
			addEffect(effect);
		}
	}

	/**
	 * Adds the effect.
	 *
	 * @param effect the effect
	 */
	private void addEffect(float[][] effect) {
		float[][] rgb = new float[1][4];
		float[][] xrgb;
		for(int x = 0; x < w; x++) {
			for(int y = 0; y < h; y++) {
				int p = pixels[x + y * w];

				int a = (p >> 24) & 0xff;

				rgb[0][0] = (p >> 16) & 0xff;
				rgb[0][1] = (p >> 8) & 0xff;
				rgb[0][2] = (p) & 0xff;
				rgb[0][3] = 1f;

				xrgb = Matrix.multiply(rgb, effect);

				for(int i = 0; i < 3; i++) {
					if(xrgb[0][i] > 255) rgb[0][i] = 255;
					else if(xrgb[0][i] < 0) rgb[0][i] = 0;
					else rgb[0][i] = xrgb[0][i];
				}

				p = (a<<24) | ((int) rgb[0][0]<<16) | ((int) rgb[0][1]<<8) | (int) rgb[0][2];
				image.setRGB(x, y, p);
			}
		}
		currentEffect = effect;
	}
}

