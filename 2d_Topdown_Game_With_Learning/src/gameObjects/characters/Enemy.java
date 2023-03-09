/**
 * Title: Enemy.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 16, 2022
 */
package gameObjects.characters;

import ai.statemachine.HierarchicalStateMachine;
import ai.steeringbehaviors.SteeringOutput;
import gameObjects.ID;
import math.Vector2;
import state.GameStateManager;
import utils.ThreeTuple;

/**
 * The Class Enemy. This abstract class is for all enemies in the game 
 */
public abstract class Enemy extends Entity {
	
	/** The top level. */
	protected HierarchicalStateMachine topLevel;

	/**
	 * Enemy.
	 *
	 * @param position the position
	 * @param width the width
	 * @param height the height
	 * @param speed the speed
	 * @param id the id
	 * @param sprite the sprite
	 * @param gsm the gsm
	 */
	public Enemy(Vector2 position, float width, float height, float speed,
			ID id, ThreeTuple<String, Integer, Integer> sprite,GameStateManager gsm) {
		super(position, width, height, speed, id, sprite,gsm);
	}


	@Override
	public void update(float elapsedTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}


	@Override
	public void movement(float elapsedTime, SteeringOutput output) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * Load state machine into topLevel
	 *
	 * @param hsm the hsm
	 */
	public void loadStateMachine(HierarchicalStateMachine hsm) {
		this.topLevel = hsm;
	}

}
