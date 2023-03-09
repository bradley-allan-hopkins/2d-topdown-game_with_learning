/**
 * Title: Evade.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Jan. 28, 2022
 */
package ai.steeringbehaviors.seekandflee;

import ai.steeringbehaviors.Kinematic;
import ai.steeringbehaviors.SteeringOutput;
import math.Vector2;

/**
 * The Class Evade that allows the agent to evade its target using a prediction calculation. 
 * The object calculates what position the target will be in after "X" time and sets the 
 * target to this new position. This new target is passed to Flee that determines the agents
 * new velocity.
 */
public class Evade extends Flee {
	
	/** The max prediction. */
	float maxPrediction;
	
	/** The evade target. */
	Kinematic evadeTarget;

	/**
	 * Evade. 
	 *
	 * @param seeker - Kinematic
	 * @param target - Kinematic
	 * @param maxAcceleration - float
	 * @param maxPrediction - float
	 */
	public Evade(Kinematic seeker, Kinematic target, float maxAcceleration, float 
			maxPrediction) {
		super(seeker, new Kinematic(0,0,0), maxAcceleration);
		this.evadeTarget = target;
		this.maxPrediction = maxPrediction;
	}
	
	@Override
	public SteeringOutput getSteering() {
		
		//find distance
		Vector2 direction = character.getPosition().subtractTwoVectors(evadeTarget.getPosition());
		float distance = direction.getLength();
		
		//find current speed
		float speed = character.getVelocity().getLength();
		
		//check if speed gives a reasonable prediction time
		float prediction;
		if (speed <= distance/maxPrediction)
			prediction = maxPrediction;
		else 
			prediction = distance/speed;
		
		//change super target to new guessed target position
		Kinematic explicitTarget = new Kinematic(evadeTarget.getPosition().getX(),
				evadeTarget.getPosition().getY(),0);
		
		explicitTarget.getPosition().addToVector(evadeTarget.getVelocity().scaler(prediction));
		super.target = explicitTarget;
		
		//delegate to Seek
		return super.getSteering();
	}
	
	/**
	 * Sets the target.
	 * @param newTarget the new target
	 */
	public void setTarget(Kinematic newTarget) {
		this.evadeTarget = newTarget;
	}

}
