/**
 * Title: SteeringOutput.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Jan. 31, 2022
 * *derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.steeringbehaviors;

import math.Vector2;

/**
 * The Class SteeringOutput.
 */
public class SteeringOutput {
	
	/** The linear vector. Also known as velocity. */
	Vector2 linear;
	
	/** The angular. Also known as rotation*/
	float angular;
	
	/**
	 * Instantiates a new steering output().
	 */
	public SteeringOutput(){
		setLinear(new Vector2(0,0));
		setAngular(0);
	}

	/**
	 * Gets the variable "Linear".
	 * @return vector - Linear
	 */
	public Vector2 getLinear() {
		return linear;
	}

	/**
	 * Sets the linear.
	 * @param linear the new linear
	 */
	public void setLinear(Vector2 linear) {
		this.linear = linear;
	}

	/**
	 * Gets the variable "Angular".
	 * @return float - Angular
	 */
	public float getAngular() {
		return angular;
	}

	/**
	 * Sets the angular.
	 * @param angular the new angular
	 */
	public void setAngular(float angular) {
		this.angular = angular;
	}
	
	
}
