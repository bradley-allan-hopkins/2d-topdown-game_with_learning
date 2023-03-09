/**
 * Title: GameObject.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 11, 2022
 */
package gameObjects;

import ai.steeringbehaviors.Kinematic;
import ai.steeringbehaviors.SteeringOutput;
import gameObjects.collisions.BoundingBox;
import gameObjects.collisions.CollisionResolution;
import gameObjects.collisions.shapes.Shape;
import math.Vector2;

/**
 * The Class GameObject. GameObjects are all objects that are used in the game (characters,
 * blocks, etc)
 */
public abstract class GameObject implements GameLogic{
	
	/** true if the gameObject has been the tester for collisions */
	public boolean tested = false;

	/** The count. */
	private static int count = 0;

	/** The game object number. */
	public final int gameObjectNumber;

	/** all game objects must have a kinematic for placement and possible movement.*/
	protected Kinematic kinematic;

	/** The bounding box for the GameObject, used for collision testing and resolution. */
	protected BoundingBox box;

	/** The unit width. */
	protected float unitWidth;

	/** The unit height. */
	protected float unitHeight;

	/** The speed of the gameObject.*/
	protected float speed;

	/** The id. */
	public ID id;

	/**
	 * Game object.
	 * @param position - center position (x,y)
	 * @param unitWidth the unit width
	 * @param unitHeight the unit height
	 * @param speed the speed
	 * @param id the id
	 */
	public GameObject(Vector2 position, float unitWidth, float unitHeight, float speed, ID id) {
		this.kinematic = new Kinematic(position.getX(),position.getY(),0);
		this.unitWidth = unitWidth;
		this.unitHeight = unitHeight;
		this.id = id;
		this.setBox(new BoundingBox(position,unitWidth, unitHeight));
		this.speed = speed;

		//each gameObject will have a new number
		gameObjectNumber = count;
		count++;
	}


	/**
	 * Finalize. When Object is garbage collected subtract from count
	 */
	@Override
	public void finalize() {
		count--;
	}

	/**
	 * Gets the variable "BoundingBox".
	 * @return bounding box - BoundingBox
	 */
	public BoundingBox getBoundingBox() {
		return getBox();
	}

	/**
	 * Gets the variable "CollisionResolution".
	 * @return collision resolution - CollisionResolution
	 */
	@SuppressWarnings("static-method")
	public CollisionResolution getCollisionResolution() {
		return new CollisionResolution();
	}

	/**
	 * Gets the variable "Kinematic".
	 * @return kinematic - Kinematic
	 */
	public synchronized Kinematic getKinematic() {
		return kinematic;
	}

	/**
	 * Gets the variable "Speed".
	 * @return float - Speed
	 */
	public float getSpeed() {
		return speed;
	}

	/**
	 * Gets the variable "UnitHeight".
	 * @return int - UnitHeight
	 */
	public float getUnitHeight() {
		return unitHeight;
	}

	/**
	 * Gets the variable "UnitWidth".
	 * @return int - UnitWidth
	 */
	public float getUnitWidth() {
		return unitWidth;
	}

	/**
	 * Sets the speed.
	 * @param speed the new speed
	 */
	public void setSpeed(float speed) {
		this.speed = speed;
	}


	/**
	 * Sets the unit height.
	 * @param unitHeight the new unit height
	 */
	public void setUnitHeight(int unitHeight) {
		this.unitHeight = unitHeight;
	}


	/**
	 * Sets the unit width.
	 * @param unitWidth the new unit width
	 */
	public void setUnitWidth(int unitWidth) {
		this.unitWidth = unitWidth;
	}


	/**
	 * Public method used to get.
	 *
	 * @return the id
	 */
	public ID getID() {
		return id;
	}


	/**
	 * Public method used to get
	 * @return the box
	 */
	public BoundingBox getBox() {
		return box;
	}


	/**
	 * Public method used to set
	 * @param box the box to set
	 */
	public void setBox(BoundingBox box) {
		this.box = box;
	}
	
	/**
	 * Method that returns the position of the object
	 * @return Vector2 - position
	 */
	public Vector2 getPosition() {
		return kinematic.getPosition();
	}
	
	/**
	 * Method that returns the x coordinate of the object's position
	 * @return float - x coordinate
	 */
	public float getPosX() {
		return kinematic.getPosition().getX();
	}
	
	/**
	 * Method that returns the y coordinate of the object's position
	 * @return float - y coordinate 
	 */
	public float getPosY() {
		return kinematic.getPosition().getY();
	}
	
	/**
	 * Method that returns the x coordinate of the object's position
	 * @return float - x coordinate
	 */
	public float getVelX() {
		return kinematic.getVelocity().getX();
	}
	
	/**
	 * Method that returns the y coordinate of the object's position
	 * @return float - y coordinate 
	 */
	public float getVelY() {
		return kinematic.getVelocity().getY();
	}
	
	/** 
	 * This method will grab the shapes that this object is holding. For example a player 
	 * might have a head, a bag , a body, and a sword. Each shape is used to test for 
	 * collisions 
	 * @return array of shapes 
	 */
	@SuppressWarnings("static-method")
	public Shape[] getShapes() {
		System.out.println("GET SHAPES OBJECT");
		return null;
	}
	
	/**
	 * Method that resets all objects tested to false
	 */
	public void resetTested() {
		this.tested = false;
		Shape[] shapes = getShapes();
		if (shapes == null)return;
		for (int i = 0;i < shapes.length;i++) {
			shapes[i].tested = false;
		}
	}
	/**
	 * Method that updates the outer bounding box to fit all inner boxes 
	 */
	public void updateOuterBox() {
		Vector2 min = new Vector2(0,0);
		Vector2 max = new Vector2(0,0);
		Shape[] shapes = getShapes();
		if (shapes == null) return;
		for (int i = 0 ; i < shapes.length;i++) {
			if (i == 0) {
				min = new Vector2(shapes[i].box.getMin().getX(), shapes[i].box.getMin().getY());
				max = new Vector2(shapes[i].box.getMax().getX(), shapes[i].box.getMax().getY());
			}
			else {
				Vector2 shapeMin = shapes[i].getBoundingBox().getMin();
				Vector2 shapeMax = shapes[i].getBoundingBox().getMax();
				if (shapeMin.getX() < min.getX())min.setX(shapeMin.getX());
				if (shapeMin.getY() < min.getY())min.setY(shapeMin.getY());
				if (shapeMax.getX() > max.getY())max.setX(shapeMax.getX());
				if (shapeMax.getY() > max.getY())max.setY(shapeMax.getY());
			}
		}
		float width = max.getX() - min.getX();
		float height = max.getY() - min.getY();
		Vector2 center = new Vector2((min.getX() + max.getX())/2,(min.getY() + max.getY())/2);
		
		box.updateCompleteBox(center, width + 20, height + 20);
	}
	
	/**
	 * Movement.
	 *
	 * @param elapsedTime the elapsed time
	 * @param output  - SteeringOutput 
	 */
	public void objectMovement(float elapsedTime,SteeringOutput output) {
		movement(elapsedTime, output);
		box.updateBoundingBox(getPosition());
		Shape[] shapes = getShapes();
		if (shapes == null)return;
		for (int i = 0;i < shapes.length;i++) {
			shapes[i].getBoundingBox().updateBoundingBox(box.getCentreOfBox());
		}
	}
	
	/**
	 * Movement.
	 *
	 * @param elapsedTime the elapsed time
	 * @param output - the SteeringOutput 
	 */
	public abstract void movement(float elapsedTime,SteeringOutput output);
}
