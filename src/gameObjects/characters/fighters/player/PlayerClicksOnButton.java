/**
 * Title: PlayerClicksOnButton.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters.player;

import ai.statemachine.conditions.Condition;
import game.MouseHandler;
import gameObjects.collisions.CollisionDetection;
import math.Vector2;
import state.states.FightState;
import tiles.blocks.Block;

/**
 * The Class PlayerClicksOnButton. This condition returns true if the player clicks on a game
 * button in the fightState
 */
public abstract class PlayerClicksOnButton extends Condition {

	
	/** The b. */
	protected Block b;
	

	/**
	 * Checks if a button is pushed 
	 *
	 * @return true, if a button is pushed
	 */
	public boolean isButton() {
		Block[] buttons = FightState.buttons;
		for (int i = 0; i < buttons.length;i++) {
			b = buttons[i];
			if (buttons[i] != null) {
				//check if mouse position is intersecting a button bounding box
				if (CollisionDetection.isIntersectionInObj(new Vector2(MouseHandler.getX(),
						MouseHandler.getY()), b)){
					//if mouse 1 is pushed
					if (MouseHandler.getButton() == 1) 
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * Public method used to get
	 * @return the b
	 */
	public Block getB() {
		return b;
	}

	/**
	 * Public method used to set
	 * @param b the b to set
	 */
	public void setB(Block b) {
		this.b = b;
	}
}
