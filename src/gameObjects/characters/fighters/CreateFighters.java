/**
 * Title: CreateFighters.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package gameObjects.characters.fighters;

import java.util.ArrayList;
import java.util.List;

import gameObjects.ID;
import gameObjects.characters.Player;
import gameObjects.characters.fighters.minotaur.MinotaurFightStateMachine;
import gameObjects.characters.fighters.minotaur.MinotaurFighter;
import gameObjects.characters.fighters.player.PlayerFightStateMachine;
import gameObjects.characters.fighters.player.PlayerFighter;
import gameObjects.characters.minotaur.Minotaur;
import math.Vector2;
import state.GameStateManager;
import tiles.TileManager;
import utils.Random;
import utils.ThreeTuple;

/**
 * The Class CreateFighters. This object creates the fighters for the battle. It will always 
 * create the player and randomly choose 1-3 minotaur fighters to battle
 */
public class CreateFighters {

	
	/**
	 * Creates the fighters.
	 *
	 * @param player the player
	 * @param mino the mino
	 * @param gsm the gsm
	 * @param tm the tm
	 * @return list
	 */
	public static List<Fighter> createFighters(Player player, Minotaur mino,GameStateManager
			gsm,TileManager tm) {
		//pick starting location - location will actually be determined by FightState
		Vector2 start = new Vector2(0,0);
		
		//create list of all fighters 
		List<Fighter> list = new ArrayList<Fighter>();
		
		//create player using current health and number of health bottles 
		PlayerFighter playerfighter = new PlayerFighter(start,110,110,2,ID.Player,
				new ThreeTuple<String, Integer, Integer>("/images/linkFormatted.png",32,32),gsm);
		//addFighter(player);
		list.add(playerfighter);
		
		//add the state machine for the fighter 
		playerfighter.setHStateM(new PlayerFightStateMachine(playerfighter,tm).createStates());
		
		//update health and number of health bottles in inventory
		System.out.println("HEALTH:" + player.getWorldHealth());
		playerfighter.setHealth(player.getWorldHealth());
		playerfighter.setHealthBottles(player.getWorldHealthBottles());
		
		
		//choose randomly how many minotaur fighters will be in the fight: 1 - 3
		int random = Random.getRndInteger(1, 3);
		for (int i = 0; i < random;i++) {
			MinotaurFighter minotaur = new MinotaurFighter(start,
					300,300,2,ID.Minotaur,
					new ThreeTuple<String,Integer,Integer>("/images/Minotaur - Sprite Sheet.png",
					96,96),gsm);
			//set the state machine for the fighter 
			list.add(minotaur);
			minotaur.setHStateM(new MinotaurFightStateMachine(minotaur,tm).createStates());
			
		}
		
		//return the list of all the fighters 
		return list;
	}
	
}
