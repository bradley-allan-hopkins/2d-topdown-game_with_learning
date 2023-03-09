/**
 * Title: Wander.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Jan. 31, 2022
 * *derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 * @Revised Mar 12 : added the kinematic target parameter 
 */

package ai.steeringbehaviors.seekandflee;

import ai.steeringbehaviors.Kinematic;
import ai.steeringbehaviors.SteeringOutput;
import ai.steeringbehaviors.orientation.Face;

/**
 * The Class Wander. This object lets the agent pick a random location to use as a target. 
 * The random location is first chosen using a point in front of the agent. The location is 
 * then moved at random to a another point within a radius from the first point; this new point
 * becomes the target for the agent. 
 */
public class Wander extends Face{

	/** radius and forward offset to the wander circle */
	float wanderOffset; //use 1.5f
	/** The wander radius. */
	float wanderRadius; //use 4f
	
	/** maximum rate at which the wander orientation can change */
	float wanderRate; //use 0.4f
	
	/** current orientation of the wander target */
	float wanderOrientation;
	
	/** The max acceleration. */
	float maxAcceleration;
	
	
	/**
	 * Constructor
	 *
	 * @param character - kinematic 
	 * @param target - the target to travel to 
	 * @param targetRadius - used for Align
	 * @param slowRadius - used for Align
	 * @param wanderRate - float - used to determine how much orientation change occurs
	 * @param wanderOffset float - used to determine first point
	 * @param wanderRadius - float - used to determine final point
	 * @param maxAcceleration -float - max acceleration to wander
	 * @param maxAngularAcceleration -float - max speed to turn 
	 * @param maxRotation - max amount that is allowed to rotate
	 */
	public Wander(Kinematic character, Kinematic target, float targetRadius, float slowRadius,
			float wanderRate, float wanderOffset, float wanderRadius,float maxAcceleration,
			float maxAngularAcceleration,float maxRotation) {
		super(character, new Kinematic(target.getPosition().getX(),target.getPosition().getY(),
				target.getOrientation())
				,targetRadius, slowRadius,maxAngularAcceleration,maxRotation);
		this.wanderOrientation = character.getOrientation();
		this.wanderRate = wanderRate;
		this.wanderOffset = wanderOffset;
		this.wanderRadius = wanderRadius;
		this.maxAcceleration = maxAcceleration;
	}

	@Override
	public SteeringOutput getSteering() {
		
		//calculate the target to delegate to face - update orientation
		wanderOrientation += Kinematic.randomBinomial() * wanderRate;
		
		//calculate the combined target orientation
		float targetOrientation = wanderOrientation + character.getOrientation();

		// calculate the centre of the wander circle
		target.setPosition(character.getPosition().addTwoVectors(Kinematic.asVector(
				character.getOrientation()).scaler(wanderOffset)));
		
		//calculate the target location
		target.setPosition(target.getPosition().addTwoVectors(Kinematic.asVector(
				targetOrientation).scaler(wanderRadius)));

		//delegate to face
		SteeringOutput result = super.getSteering();
		
		//accelerate at full
		result.setLinear(Kinematic.asVector(character.getOrientation()).scaler(maxAcceleration));
		
		return result;
	}
	

}
