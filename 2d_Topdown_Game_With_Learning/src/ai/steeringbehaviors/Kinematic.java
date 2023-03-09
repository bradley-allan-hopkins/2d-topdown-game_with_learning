/**
 * Title: Kinematic.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Jan. 28, 2022
 * *derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.steeringbehaviors;

import math.Vector2;

/**
 * The Class Kinematic.
 */
public class Kinematic {
	
	/** The position of the character*/
	private Vector2 position; 
	
	/** The orientation of the character using Radians */
	private float orientation;
	
	/** The velocity of the character */
	private Vector2 velocity;
	
	/** The rotation. */
	private float rotation;
	
	/**
	 * Constructor 
	 * @param X - float - the x position of the character
	 * @param Y -float - the y position of the character
	 * @param orientation - float - the orientation (which way character is moving/looking)
	 */
	public Kinematic(float X, float Y, float orientation){
		position = new Vector2(X,Y);
		this.orientation = orientation;
		velocity = new Vector2(0,0);
		rotation = 0;
	}
	
	/**
	 * Update method that updates all the variables based on the steering movement and the 
	 * max speed. Time is used to scale the movement based how much time is passed for movement
	 * 
	 * @param steering -  SteeringOutput - gathered from a behavior
	 * @param maxSpeed - float - max speed of the character
	 * @param time - time to scale velocity and orientation
	 */
	public void update(SteeringOutput steering, float maxSpeed, float time) {
		//update position and orientation 
		position.addToVector(velocity.scaler(time));
		orientation += (rotation * time);
		
		orientation = mapToRange(orientation);  // keep rotation within range
		
		//update velocity and rotation
		velocity.addToVector(steering.getLinear().scaler(time));
		rotation += (steering.getAngular() * time);
		
		//check for speeding and clip
		clipSpeed(maxSpeed);
		
	}
	
	/**
	 * Method that clips the velocity to not exceed speed
	 * @param maxSpeed - max speed of GameObject 
	 */
	public void clipSpeed(float maxSpeed) {
		if (velocity.getLength() > maxSpeed) {
			velocity.normalize();
			velocity.scaleVector(maxSpeed);
		}
	}
	
	/**
	 * Map to range.
	 * @param r - rotation
	 * @return float
	 */
	public static float mapToRange(float r) {
		float rotation = r;
		rotation %= (2 * Math.PI);
		if (Math.abs(rotation) > Math.PI) {
			if (rotation < 0.0f)
				rotation += (2 * Math.PI);
			else rotation -= (2 *Math.PI);
		}
		return rotation;
	}
	
	/**
	 * returns a random binomial. Math.random() returns a number between 0 and 1; thus this 
	 * method returns a number between -1 and 1.
	 * @return float
	 */
	public static float randomBinomial() {
		return (float) (Math.random() - Math.random());
	}
	
	/**
	 * This method returns a Vector based on the orienation 
	 * @param orientation the orientation
	 * @return vector - Vector 
	 */
	public static Vector2 asVector(final float orientation) {
		return new Vector2((float)Math.cos(orientation),
			-(float)Math.sin(orientation));
	}
	
	/**
	 * Sets the orientation.
	 * @param orientation the new orientation
	 */
	public void setOrientation(final float orientation) {
		this.orientation = orientation;
	}
	
	/**
	 * Sets the rotation.
	 * @param rotation the new rotation
	 */
	public void setRotation(final float rotation) {
		this.rotation = rotation;
	}
	
	/**
	 * Sets the position.
	 * @param position the new position
	 */
	public void setPosition(final Vector2 position) {
		this.position = position;
	}
	
	
	/**
	 * Sets the velocity.
	 * @param velocity the new velocity
	 * @param maxSpeed of character
	 */
	public void setVelocity(final Vector2 velocity,float maxSpeed) {
		setVelocity(velocity);
		clipSpeed(maxSpeed);
	}
	/**
	 * Sets the velocity.
	 * @param velocity the new velocity
	 */
	public void setVelocity(final Vector2 velocity) {
		this.velocity = velocity;
	}
	
	
	/**
	 * Gets the variable "Position".
	 * @return vector - Position
	 */
	public Vector2 getPosition() {
		return position;
	}
	
	/**
	 * Gets the variable "Orientation".
	 * @return float - Orientation
	 */
	public float getOrientation() {
		return orientation;
	}
	
	/**
	 * Gets the variable "Velocity".
	 * @return vector - Velocity
	 */
	public Vector2 getVelocity() {
		return velocity;
	}
	
	/**
	 * Gets the variable "Rotation".
	 * @return float - Rotation
	 */
	public float getRotation() {
		return rotation;
	}
	
	
	
	
}
