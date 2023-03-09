/**
 * Title: WanderSearchForObjects.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 26, 2022
 */
package ai.statemachine.actions;

import java.util.List;

import ai.statemachine.conditions.Condition;
import ai.statemachine.conditions.WithinRadiusCondition;
import ai.steeringbehaviors.Behavior;
import ai.steeringbehaviors.SteeringOutput;
import ai.steeringbehaviors.grouping.BlendedSteering;
import ai.steeringbehaviors.grouping.PrioritySteering;
import ai.steeringbehaviors.seekandflee.Wander;
import gameObjects.GameObject;
import tiles.TileManager;
/**
 * The Class WanderSearchForObjects. This action uses the wander behavior to steer the 
 * character around until it is close to an object at which point it delegates to a seek 
 * behavior to find the object
 */
public class WanderSearchForObjects extends SeekObject {

	/** The wander. */
	Wander wander;
	
	/** The wander priority. */
	PrioritySteering wanderPriority;
	
	/** The objects. */
	List<GameObject> objects;
	
	/** The found object. */
	GameObject foundObject;

	/** Distance to stop wandering and move towards object */
	float radius;

	/** The w att. */
	Behavior wAtt;


	/**
	 * Search for object.
	 *
	 * @param character the character
	 * @param objects - an array of 
	 * @param radius - radius to seek 
	 * @param wanderAttributes - Attributes need to includes: maxAcceleration, maxRotation,
	 * targetRadius, wanderRadius, slowRadius, wanderRate, wanderOffset, maxAngularAcceleration
	 * @param seekAttributes -Attributes need to includes: maxAcceleration, maxPrediction,threshold
	 * decayCoeffienct, collisionRadius, lookAhead, avoidDistance, maxRotation
	 * @param targets - other interactable gameObjects used in separation
	 * @param speed - this sets the characters speed
	 * @param tm - the current Tilemanager

	 */
	public WanderSearchForObjects(GameObject character, List<GameObject> objects,float radius,
			Behavior wanderAttributes,Behavior seekAttributes, List<GameObject> targets,
			float speed,TileManager tm){
		super(character, character, seekAttributes, targets,speed,tm);
		this.wAtt = wanderAttributes;
		this.objects = objects;
		this.radius = radius;
		createWanderPriority();
	}
	
	/**
	 * Creates the wander priority.
	 */
	public void createWanderPriority() {
		wander = new Wander(character.getKinematic(),character.getKinematic(),
				wAtt.getTargetRadius(), wAtt.getSlowRadius(), wAtt.getWanderRate(),
				wAtt.getWanderOffset(),wAtt.getWanderRadius() ,wAtt.getMaxAcceleration(),
				wAtt.getMaxAngularAcceleration(),wAtt.getMaxRotation());
		
		BlendedSteering wanderBlended = new BlendedSteering(wAtt.getMaxAcceleration(),
				wAtt.getMaxRotation());
		wanderBlended.addBehavior(wander, 1.0f);
		wanderBlended.addBehavior(separation,0.5f );
		
		BlendedSteering[] wanderGroup = new BlendedSteering[2];
		wanderGroup[0] = avoidanceBlended;
		wanderGroup[1] = wanderBlended;
		
		wanderPriority = new PrioritySteering(wanderGroup);
		
		

	}
	


	@Override
	public void performAction(float elapsedTime) {

		for (int i = 0; i < objects.size(); i++) {
			Condition checkDistance = new WithinRadiusCondition(character,objects.get(i),radius);
			if (checkDistance.test()) {
				super.target = objects.get(i);
				super.performAction(elapsedTime);
				this.foundObject = objects.get(i);
				return;
			}
		}
		wander(elapsedTime);
	}

	/**
	 * Wander.
	 *
	 * @param elapsedTime the elapsed time
	 */
	public void wander(float elapsedTime) {

		SteeringOutput steeringOutput = wanderPriority.getSteering();
		if (character.getKinematic().getVelocity().getLength() > 0) {
			character.getKinematic().setOrientation(
					(float) -Math.atan2(character.getKinematic().getVelocity().getY(),
							character.getKinematic().getVelocity().getX()));
		}
		//otherwise set the targets orientation based on the velocity
		
		
		character.objectMovement(elapsedTime, steeringOutput);
	}
}


