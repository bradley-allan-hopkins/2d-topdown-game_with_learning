/**
 * Title: PlayerChoose.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters.player;

import ai.statemachine.actions.Action;
import game.MouseHandler;
import gameObjects.characters.fighters.Fighter;
import gameObjects.collisions.CollisionDetection;
import math.Vector2;
import state.states.FightState;

/**
 * The Class PlayerChoose. This action allows the player to select the buttons 
 */
public class PlayerChoose extends Action {

	@Override
	public void performAction(float elapsedTime) {
		//go through each fighter and check mouse position and if player clicks 
		for(Fighter f: FightState.fighters) {
			if (CollisionDetection.isIntersectionInObj(new Vector2(MouseHandler.getX(),
					MouseHandler.getY()), f)){
				if (MouseHandler.getButton() == 1) {
					//select current fighter
					f.setSelected(true);
					//deselect other fighters 
					for (Fighter fighter: FightState.fighters) {
						if (fighter != f) {
							fighter.setSelected(false);
						}
					}
				}
				break;
			}
		}
	}

}
