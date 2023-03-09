/**
 * Title: PlayerFighter.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters.player;

import gameObjects.ID;
import gameObjects.characters.fighters.Fighter;
import math.Vector2;
import state.GameStateManager;
import utils.ThreeTuple;
import utils.TwoTuple;

/**
 * The Class PlayerFighter.
 */
public class PlayerFighter extends Fighter {
	
	/**
	 * Player fighter.
	 *
	 * @param position the position
	 * @param width the width
	 * @param height the height
	 * @param speed the speed
	 * @param id the id
	 * @param sprite the sprite
	 * @param gsm the gsm
	 */
	public PlayerFighter(Vector2 position, float width, float height,
			float speed, ID id, ThreeTuple<String, Integer, Integer> sprite,
			GameStateManager gsm) {
		super(position, width, height, speed, id, sprite, gsm);
		init();
	}

	@Override
	public void init() {
		box.setWidth(60);
		box.setHeight(80);
		box.setCentreOffset(new Vector2(5,0));
		attackRange = new TwoTuple<Integer,Integer>(35,60);
		defense = 10;
		setAnimation(5, -1, 0);
		animation.setFrame(7);
	}
	
	
}
