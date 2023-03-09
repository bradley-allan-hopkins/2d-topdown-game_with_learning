/**
 * Title: BoundingBox.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 4, 2022
 */
package gameObjects.collisions;

import java.awt.Color;
import java.awt.Graphics;

import math.Vector2;
/**
 * The Class BoundingBox.This object is the bounding box of the GameObject
 */
public class BoundingBox {
	
	/** The center of the bounding box, which is the same as the obj position*/ 
	private Vector2 center;
	
	/** The center offset. If an offset exists Used for changing the bounding box location*/
	private Vector2 centerOffset = new Vector2(0,0);
	
	/** The width of the bounding box */
	private float width;
	
	/** The height of the bounding box*/
	private float height;
	

	/**
	 * Bounding box.
	 *
	 * @param center the center
	 * @param width the width
	 * @param height the height
	 */
	public BoundingBox(Vector2 center,float width, float height) {
		this.width = width;
		this.height = height;
		this.center = center;
	}
	
	/**
	 * Sets the centre offset.
	 * @param centerOffset the new centre offset
	 */
	public void setCentreOffset(Vector2 centerOffset) {
		this.centerOffset = centerOffset;
		this.center = center.addTwoVectors(this.centerOffset);
	}
	
	/**
	 * Gets the variable "CentreOfBox".
	 * @return vector 2 - CentreOfBox
	 */
	public Vector2 getCentreOfBox() {
		return center;
	}
	
	/** 
	 * Set the center of box
	 * @param center - center position
	 */
	public void setCenterOfBox(Vector2 center) {
		this.center = center;
	}
	
	/**
	 * Gets the variable "Min". Min is the top left of the bounding box
	 * @return vector 2 - Min
	 */
	public Vector2 getMin() {
		return new Vector2(center.getX() - getWidth()/2.0f, center.getY() 
				- getHeight()/2.0f);
	}
	
	/**
	 * Gets the variable "Max". Max is the bottom right of the bounding box
	 * @return vector 2 - Max
	 */
	public Vector2 getMax() {
		return new Vector2(center.getX() + getWidth()/2.0f,center.getY() 
				+ getHeight()/2.0f);
	}
	
	/**
	 * Update bounding box. This updates the bounding box to be the same location as the 
	 * player
	 * @param center the center
	 */
	public void updateBoundingBox(Vector2 center) {
		this.center = center.addTwoVectors(centerOffset);
	}
	
	/**
	 * This method returns the points of the box
	 * @param i - corner number: 0 represents top left
	 * @return Vector2 - returns the corner
	 */
	public Vector2 getPoint(int i) {
		if (i == 0)return new Vector2 (getMin().getX(), getMin().getY());
		if (i == 1)return new Vector2(getMin().getX(), getMax().getY());
		if (i == 2)return new Vector2(getMax().getX(),getMax().getY());
		if (i == 3)return new Vector2(getMax().getX(), getMin().getY());
		return null;
	}
	
	/**
	 * View bounding box. Draws the bounding box on screen for testing purposes
	 * @param g the g
	 * @param color the color
	 * @param x - offset used to compensate for camera view
	 * @param y - offset for camera
	 */
	public void viewBoundingBox(Graphics g, Color color,float x, float y) {
		g.setColor(color);
		g.drawRect((int)(center.getX() - width/2 - x),
				(int)( center.getY() - height/2 - y), (int)width, (int)height);
	}

	/**
	 * Public method used to get.
	 * @return the width
	 */
	public float getWidth() {
		return width;
	}

	/**
	 * Public method used to set.
	 * @param width the width to set
	 */
	public void setWidth(float width) {
		this.width = width;
	}

	/**
	 * Public method used to get.
	 * @return the height
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * Public method used to set.
	 * @param height the height to set
	 */
	public void setHeight(float height) {
		this.height = height;
	}
	
	/** 
	 * public method that updates the bounding box size
	 * @param center - center position (x,y)
	 * @param width - width of bounding box 
	 * @param height - height of bounding box 
	 */
	public void updateCompleteBox(Vector2 center, float width, float height) {
		this.center = center;
		this.width = width;
		this.height = height;
	}

}
