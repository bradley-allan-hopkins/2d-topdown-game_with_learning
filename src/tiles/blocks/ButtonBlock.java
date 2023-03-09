/**
 * Title: ButtonBlock.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 25, 2022
 */
package tiles.blocks;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import ai.steeringbehaviors.SteeringOutput;
import gameObjects.ID;
import math.Vector2;
/**
 * The Class ButtonBlock.
 */
public class ButtonBlock extends Block {
	

	/** if button is pressed*/
	private boolean pressed;
	/** Id */
	private int buttonNum;
	


	/** Overlay Image */
	private BufferedImage topLayer;
	
	/** Overlay Offset */
	private Vector2 offset;
	
	/** Overlay Image Width*/
	private int topLayerWidth;
	
	/** Overlay Image Height */
	private int topLayerHeight;
	
	/** Button Pressed Image */
	private BufferedImage pressedImage;
	
	/** Button Pressed Width */
	private int pressedWidth;
	
	/** Button pressed Height */
	private int pressedHeight;
	



	/**
	 * Button block.
	 *
	 * @param img the img
	 * @param x the x
	 * @param y the y
	 * @param width the width
	 * @param height the height
	 * @param id the id
	 * @param buttonNum - number of the button, used to know what button it is 
	 * @param topLayer - the image used on the button 
	 * @param topLayerWidth  - the top layer image width 
	 * @param topLayerHeight - the top layer image height 
	 * @param topLayerOffset - use to centre image over button
	 * @param pressedImage - when the button is pushed image 
	 * @param pressedWidth - pressed button width 
	 * @param pressedHeight - pressed button height 
	 */
	public ButtonBlock(BufferedImage img, int x, int y, int width, int height,
			ID id, int buttonNum,BufferedImage topLayer, int topLayerWidth, int topLayerHeight,
			Vector2 topLayerOffset,
			BufferedImage pressedImage, int pressedWidth, int pressedHeight) {
		super(img, x, y, width, height, id);
		this.buttonNum = buttonNum;
		this.topLayer = topLayer;
		this.topLayerWidth = topLayerWidth;
		this.topLayerHeight = topLayerHeight;
		this.offset = topLayerOffset;
		this.pressedImage = pressedImage;
		this.pressedWidth = pressedWidth;
		this.pressedHeight = pressedHeight;
		this.pressed = false;
		
	}
	
	@Override
	public void render(Graphics2D g,ImageObserver observer,float x, float y) {
		if (pressed) {
			g.drawImage(pressedImage, (int)(getPosX() - pressedWidth/2 - x), (int) 
					(getPosY() - pressedHeight/2 - y), pressedWidth, pressedHeight, null);
		}
		else {
			g.drawImage(img, (int)(getPosX() - unitWidth/2 - x), (int) 
		(getPosY() - unitHeight/2 - y), (int)unitWidth, (int)unitHeight, null);
		}
		if (topLayer != null) {
			g.drawImage(topLayer, (int)(getPosX() + offset.getX() - x), (int) 
					(getPosY() + offset.getY() - y), topLayerWidth, topLayerHeight, null);
		}
		
	}

	@Override
	public void movement(float elapsedTime, SteeringOutput output) {/* Not used */}

	
	/**
	 * Public method used to get
	 * @return the buttonNum
	 */
	public int getButtonNum() {
		return buttonNum;
	}

	/**
	 * Public method used to set
	 * @param buttonNum the buttonNum to set
	 */
	public void setButtonNum(int buttonNum) {
		this.buttonNum = buttonNum;
	}
	
	/**
	 * Public method used to get
	 * @return the pressed
	 */
	public boolean isPressed() {
		return pressed;
	}

	/**
	 * Public method used to set
	 * @param pressed the pressed to set
	 */
	public void setPressed(boolean pressed) {
		this.pressed = pressed;
	}
	
	/**
	 * Public method that removes the top layer
	 */
	public void removeTopLayer() {
		topLayer = null;
	}
	
	/** 
	 * Public method that changes the topLayer 
	 * @param topLayer - set the top image
	 */
	public void setTopLayer(BufferedImage topLayer) {
		this.topLayer = topLayer;
	}
}
