/**
 * Title: CollisionDetector.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 10, 2022
 * *derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.steeringbehaviors.collision;

import java.util.List;

import gameObjects.GameObject;
import gameObjects.collisions.CollisionDetection;
import math.Vector2;
import utils.ThreeTuple;

/**
 * The Class CollisionDetector.
 */
public class CollisionDetector {
	
	/** The character. */
	GameObject character;
	
	/** CollisionDetection */
	CollisionDetection cd;
	
	/**
	 * Collision detector.
	 *
	 * @param character the character
	 * @param cd - collision Detection object 
	 */
	public CollisionDetector(GameObject character,CollisionDetection cd) {
		this.character = character;
		this.cd = cd;
	}
	
	/**
	 * Gets the variable "Collision".
	 *
	 * @param position the position
	 * @param moveAmount the move amount
	 * @return collision - Collision
	 */
	public Collision getCollision(Vector2 position, Vector2 moveAmount) {

		//get list of blocks 
		List<GameObject> list = cd.getCollision(character, moveAmount.getLength());
		//if returned list is null return no Collision object
		if (list == null) return null;
		
		//cycle through all blocks that cause collisions
		
		for (int i = 0; i < list.size();i++) {
			
			//distance is the Ray
			//float distance = moveAmount.getLength();
			
			//see if block is within moveAmount distance
			//if (list.get(i).getBoundingBox().getCentreOfBox().subtractTwoVectors(
			//		character.getBoundingBox().getCentreOfBox()).getLength() 
			//		< distance) {
				ThreeTuple<Vector2, Vector2, Float> tuple = 
						CollisionDetection.objVsObj(character, list.get(i),
								moveAmount.getLength());
				if (tuple != null) {
					return new Collision(tuple.a, tuple.b);
				}
			//}
		}
		return null;
	}
}
