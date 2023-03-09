/**
 * Title: EnemyFighter.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters;

import gameObjects.ID;
import math.Vector2;
import state.GameStateManager;
import utils.ThreeTuple;

/**
 * The Class EnemyFighter. This class is used to separate all enemies from non-enemies. Can 
 * be used in 'instanceof' 
 */
public abstract class EnemyFighter extends Fighter {

	/**
	 * Enemy fighter.
	 *
	 * @param position the position
	 * @param width the width
	 * @param height the height
	 * @param speed the speed
	 * @param id the id
	 * @param sprite the sprite
	 * @param gsm the gsm
	 */
	public EnemyFighter(Vector2 position, float width, float height,
			float speed, ID id, ThreeTuple<String, Integer, Integer> sprite,
			GameStateManager gsm) {
		super(position, width, height, speed, id, sprite, gsm);
	}



}
