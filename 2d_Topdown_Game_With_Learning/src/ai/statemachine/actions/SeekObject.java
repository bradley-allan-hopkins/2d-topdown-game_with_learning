/**
 * Title: SeekObject.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 26, 2022
 */
package ai.statemachine.actions;

import java.util.List;

import ai.steeringbehaviors.Behavior;
import ai.steeringbehaviors.Behaviors;
import ai.steeringbehaviors.Separation;
import ai.steeringbehaviors.SteeringOutput;
import ai.steeringbehaviors.collision.CollisionDetector;
import ai.steeringbehaviors.collision.ObstacleAvoidance;
import ai.steeringbehaviors.grouping.BlendedSteering;
import ai.steeringbehaviors.grouping.PrioritySteering;
import ai.steeringbehaviors.orientation.LookWhereYourGoing;
import ai.steeringbehaviors.seekandflee.Pursue;
import gameObjects.GameObject;
import gameObjects.collisions.CollisionDetection;
import tiles.TileManager;

/**
 * The Class SearchForObject. 
 * This object uses the seek behavior to move towards the object
 */
public class SeekObject extends Action {
	
	

	/** The object. */
	protected GameObject target;
	
	/** The character. */
	protected GameObject character;
	
	/** priority behavior */
	PrioritySteering seekPriority;
	
	/** attributes */
	Behavior att;
	
	/** list of targets to avoid */
	List<GameObject> targets;
	
	/** Blended groups*/
	protected BlendedSteering avoidanceBlended,separationBlended;
	
	/** behaviors */
	Behaviors separation, pursue,collisionAvoidance,avoid;
	
	/** speed to set character to*/
	float speed;
	
	/** tileManager */
	TileManager tm;
	
	/**
	 * Search for object. 
	 *
	 * @param character the character
	 * @param target - the target object 
	 * @param attributes - Attributes need to includes: maxAcceleration, maxPrediction,threshold
	 * decayCoeffienct, collisionRadius, lookAhead, avoidDistance, maxRotation
	 * @param targets - other interactable gameObjects used in separation
	 * @param speed - this sets the characters speed
	 * @param tm - the current Tilemanager
	 */
	public SeekObject(GameObject character, GameObject target, Behavior attributes,
			List<GameObject> targets,float speed,TileManager tm) {
		this.character = character;
		this.target = target;
		this.att = attributes;
		this.targets = targets;
		this.speed = speed;
		this.tm = tm;
		createSeekPriority();
	}
	
	/**
	 * Creates the seek priority.
	 */
	public void createSeekPriority() {
		pursue = new Pursue(character.getKinematic() ,target.getKinematic() ,
				att.getMaxAcceleration(),att.getMaxPrediction());
		
		separation = new Separation(character.getKinematic(),targets,att.getThreshold(),
				att.getMaxAcceleration(),att.getDecayCoefficient());

		avoid = new ObstacleAvoidance(character.getKinematic(),att.getAvoidDistance(),
				att.getLookAhead(),att.getMaxAcceleration() * 5,
				new CollisionDetector(character,new CollisionDetection(tm)));
		
		
		//create nest searching blended steering
		BlendedSteering seekBlended = new BlendedSteering(att.getMaxAcceleration(),
				att.getMaxRotation());
		seekBlended.addBehavior(pursue,1.0f);
		seekBlended.addBehavior(separation, 0.8f);
		
		avoidanceBlended = new BlendedSteering(att.getMaxAcceleration(),att.getMaxRotation());
		avoidanceBlended.addBehavior(avoid,1.0f);

		
		BlendedSteering[] seekGroup = new BlendedSteering[2];
		seekGroup[0] = avoidanceBlended;
		seekGroup[1] = seekBlended;
		
		seekPriority = new PrioritySteering(seekGroup);
	}
	


	@Override
	public void performAction(float elapsedTime) {
		getSeek(elapsedTime);
	}
	
	/**
	 * Gets the variable "Seek".
	 * @param elapsedTime the elapsed time
	 */
	public void getSeek(float elapsedTime) {
		character.setSpeed(speed);
		//i used pursue as character could be seeking a moveable object 
		((Pursue)pursue).setTarget(target.getKinematic());
		SteeringOutput steeringOutput = seekPriority.getSteering();
		
		//turn character towards direction traveling 
		LookWhereYourGoing look = new LookWhereYourGoing(character.getKinematic(),
				target.getKinematic(),att.getSlowRadius(),att.getTargetRadius(),
				att.getMaxAngularAcceleration(),
				att.getMaxRotation());
		look.getSteering();
		
		//delegate movement to character
		character.objectMovement(elapsedTime, steeringOutput);
	}

	
}
