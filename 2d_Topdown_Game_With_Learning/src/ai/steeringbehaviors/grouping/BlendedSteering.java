/**
 * Title: BlendedSteering.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Jan. 28, 2022
 * *derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.steeringbehaviors.grouping;

import java.util.ArrayList;
import java.util.List;

import ai.steeringbehaviors.Behaviors;
import ai.steeringbehaviors.SteeringOutput;

/**
 * The Class BlendedSteering that creates a new velocity based on multiple steering behaviors
 */
public class BlendedSteering implements Behaviors {
	
	/** The behaviors. */
	List<BehaviorAndWeight> behaviors;
	
	/** The max acceleration. */
	float maxAcceleration;
	
	/** The max rotation. */
	float maxRotation;

	/**
	 * Blended steering
	 *
	 * @param maxAcceleration - float
	 * @param maxRotation - float
	 */
	public BlendedSteering(float maxAcceleration, float maxRotation) {
		this.maxAcceleration = maxAcceleration;
		this.maxRotation = maxRotation;
		behaviors = new ArrayList<BehaviorAndWeight>();
	}
	

	@Override
	public SteeringOutput getSteering() {
		SteeringOutput result = new SteeringOutput();
		
		//accumulate all accelerations from all behaviors 
		for (int i = 0; i < behaviors.size() ; i++) {
			 SteeringOutput steering = behaviors.get(i).behavior.getSteering();
			if (steering != null) {
			result.getLinear().addToVector(steering.getLinear().scaler(behaviors.get(i).weight));
			result.setAngular(result.getAngular() + steering.getAngular() * behaviors.get(i).weight);
			}
		}
		
		//crop the result and return
		
		//if magnitude is greater than maxAcceleration
		if (result.getLinear().getLength() > maxAcceleration) {
			result.getLinear().normalize();
			result.getLinear().scaleVector(maxAcceleration);
			
		}
		result.setAngular(Math.min(result.getAngular(), maxRotation));
		
		return result;
	}
	
	/**
	 * The Class BehaviorAndWeight.
	 */
	class BehaviorAndWeight{
		
		/** The behavior. */
		Behaviors behavior;
		
		/** The weight. */
		float weight;
		
		/**
		 * Behavior and weight
		 *
		 * @param behavior - Behaviors
		 * @param weight - float
		 */
		BehaviorAndWeight(Behaviors behavior, float weight){
			this.behavior = behavior;
			this.weight = weight;
		}
	}
	
	/**
	 * Adds the behavior to the list of behaviors
	 *
	 * @param behavior - Behaviors
	 * @param weight - float
	 */
	public void addBehavior(Behaviors behavior, float weight) {
		behaviors.add(new BehaviorAndWeight(behavior,weight));
	}
	
	/**
	 * Update behavior.
	 * @param behavior - find the behavior that matches and update with new information
	 */
	public void updateBehavior(Behaviors behavior) {
		 @SuppressWarnings("unlikely-arg-type")
		int i =	behaviors.indexOf(behavior);
		if (i >= 0)
			behaviors.set(i, new BehaviorAndWeight(behavior,behaviors.get(i).weight));
		
	}

	/**
	 * Gets the variable "List".
	 * @return list - List
	 */
	public List<BehaviorAndWeight> getList(){
		return behaviors;
	}
	
	@Override 
	public String toString() {
		String concantanate = "";
		for (int i = 0; i < behaviors.size();i++) {
			concantanate += behaviors.get(i).behavior;
		}
		return concantanate;
	}
	
}
