/**
 * Title: InvisibleObject.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 2, 2022
 */
package gameObjects.collisions.shapes;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import ai.steeringbehaviors.SteeringOutput;
import gameObjects.GameObject;
import gameObjects.collisions.CollisionLogic;
import math.Vector2;

/**
 * The Class InvisibleObject.
 */
public class InvisibleObject extends GameObject implements CollisionLogic{

	/** The shape. */
	Shape shape;
	
	/**
	 * Invisible object.
	 * @param position - center position (x,y)
	 * @param width - width of object 
	 * @param height - height of object 
	 *
	 * @param shape the shape
	 */
	public InvisibleObject(Vector2 position,float width, float height,Shape shape) {
		super(position, width, height, 0, null);
		this.shape = shape;
	}


	@Override
	public void update(float elapsedTime) {
		if (shape != null)
		shape.update(elapsedTime);
		
	}

	@Override
	public void render(Graphics2D g, ImageObserver observer, float x, float y) {
		if (shape != null)
			shape.render(g, observer, x, y);
	}

	@Override
	public void init() {/* Not used*/}
	

	@Override 
	public Shape[] getShapes() {
		Shape[] shapes = new Shape[1];
		shapes[0] = shape;
		return shapes;
	}

	@Override
	public void movement(float elapsedTime, SteeringOutput output) {
		// TODO Auto-generated method stub
		
	}

}
