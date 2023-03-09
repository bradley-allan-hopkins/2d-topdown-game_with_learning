/**
 * Title: MinotaurFighter.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package gameObjects.characters.fighters.minotaur;

import gameObjects.ID;
import gameObjects.characters.fighters.EnemyFighter;
import math.Vector2;
import state.GameStateManager;
import utils.ThreeTuple;
import utils.TwoTuple;

/**
 * The Class MinotaurFighter. This is the minotaur that fights in the battles. 
 */
public class MinotaurFighter extends EnemyFighter {
	

	
	/**
	 * Minotaur fighter.
	 *
	 * @param position the position
	 * @param width the width
	 * @param height the height
	 * @param speed the speed
	 * @param id the id
	 * @param sprite the sprite
	 * @param gsm the gsm
	 */
	public MinotaurFighter(Vector2 position, float width, float height,
			float speed, ID id, ThreeTuple<String, Integer, Integer> sprite,
			GameStateManager gsm) {
		super(position, width, height, speed, id, sprite, gsm);
		init();
	}


	@Override
	public void init() {
		box.setWidth(100);
		box.setHeight(100);
		box.setCentreOffset(new Vector2(-10,0));
		setAnimation(15, 20, 6);
		
		//attack and defence 
		attackRange = new TwoTuple<Integer,Integer>(5,12);
		defense = 5;
	}
	
	
	@Override
	public void update(float elapsedTime) {
		//if the player is dead do not perform any more animations 
		if (!isDead()) animation.update();
	}


}
