/**
 * Title: Pursue.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Jan. 31, 2022
 */
package ai.steeringbehaviors.seekandflee;

import ai.steeringbehaviors.Kinematic;
import ai.steeringbehaviors.SteeringOutput;
import math.Vector2;

/**
 * The Class Pursue. The object causes the agent to travel towards the target at full speed. 
 * The target is first the actual target but changes based on a predicted new position. The 
 * new position is calculated using a prediction scaler that calculates a future position. 
 * The future position is where the target would be moving if the velocity does not change. 
 */
public class Pursue extends Seek {

	/** The max prediction.*/
	float maxPrediction; 

	/** The pursue target. */
	Kinematic pursueTarget;

	/**
	 * Constructor
	 *
	 * @param character - Kinematic
	 * @param target - Kinematic
	 * @param maxAcceleration - float
	 * @param maxPrediction - float
	 */
 	public Pursue(Kinematic character, Kinematic target, float maxAcceleration, 
 			float maxPrediction) {
		super(character, new Kinematic(0,0,0), maxAcceleration);
		this.pursueTarget = target;
		this.maxPrediction = maxPrediction;
	}

 	@Override
	public SteeringOutput getSteering() {
		//find distance
		Vector2 direction = pursueTarget.getPosition().subtractTwoVectors(character.getPosition());
		float distance = direction.getLength();

		//find current speed
		float speed = character.getVelocity().getLength();

		//check if speed gives a reasonable prediction time
		float prediction;
		if (speed <= distance/maxPrediction)
			prediction = maxPrediction;
		else
			prediction = distance/speed;

		//put the target together
		Kinematic explicitTarget = new Kinematic(pursueTarget.getPosition().getX(),
				pursueTarget.getPosition().getY(),0);
		explicitTarget.getPosition().addToVector(pursueTarget.getVelocity().scaler(prediction));
		super.target = explicitTarget;


		//delegate to Seek
		return super.getSteering();
	}

	/**
	 * Sets the target.
	 * @param newTarget the new target
	 */
	public void setTarget(Kinematic newTarget) {
			this.pursueTarget = newTarget;
	}
}
