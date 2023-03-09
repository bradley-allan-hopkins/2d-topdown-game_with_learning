/**
 * Title: Rectangle.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 2, 2022
 */
package gameObjects.collisions.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import ai.steeringbehaviors.SteeringOutput;
import gameObjects.GameObject;
import gameObjects.ID;
import gameObjects.Testing;
import math.Vector2;


/**
 * The Class Rectangle.
 */
public class Rectangle extends Shape{

	/**
	 * Rectangle.
	 *
	 * @param holder the holder
	 * @param position the position
	 * @param width the width
	 * @param height the height
	 * @param speed the speed
	 * @param id the id
	 */
	public Rectangle(GameObject holder,Vector2 position,float width, float height,float speed,
			ID id) {
		super(holder,position, width,height,speed,id);
	}
	
	/**
	 * The a.
	 */
	//Four Vector objects with no initialization 
	Vector2 a;
	
	/** The b. */
	Vector2 b;
	
	/**
	 * The c.
	 */
	Vector2 c;
	
	/**
	 * The d.
	 */
	Vector2 d;
	

	@Override
	public void update(float elapsedTime) {/* Not Used*/}
	

	@Override
	public void render(Graphics2D g, ImageObserver observer, float x, float y) {
		if (Testing.viewBoundingBoxes) {
			box.viewBoundingBox(g, Color.BLUE,x,y);
		}
	}
	

	@Override
	public void init() {/* Not used*/}


	@Override
	public void movement(float elapsedTime, SteeringOutput output) {
		// TODO Auto-generated method stub
		
	}
	
	@Override 
	public Shape[] getShapes() {
		Shape[] shapes = new Shape[1];
		shapes[0] = this;
		return shapes;
	}
	
	
}//end of Rectangle subclass 