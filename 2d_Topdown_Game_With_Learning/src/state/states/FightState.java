/**
 * Title: FightState.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package state.states;


import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import ai.statemachine.HierarchicalStateMachine;
import ai.statemachine.SubMachineState;

import ai.statemachine.HSMBase.UpdateResult;
import ai.statemachine.actions.Action;
import game.Game;

import gameObjects.Camera;
import gameObjects.GameObject;
import gameObjects.ID;

import gameObjects.characters.fighters.EnemyFighter;
import gameObjects.characters.fighters.Fighter;
import gameObjects.characters.fighters.player.PlayerFighter;
import gameObjects.handlers.Handler;
import gameObjects.handlers.ObjectHandler;
import graphics.Sprite;
import graphics.SpriteSheet;
import math.Vector2;
import sounds.SoundPlayer;
import state.GameStateManager;
import state.states.fightstate.FightScreenDisplay;
import tiles.TileManager;
import tiles.TileMap;
import tiles.TileMapNormal;
import tiles.blocks.Block;
import tiles.blocks.ButtonBlock;
import utils.Random;

/**
 * The Class FightState.
 */
public class FightState extends GameState {
	
	/** The turn. */
	public static int turn;
	
	/** the last turn */
	public static int lastTurn;
	
	/** The fighters. */
	public static List<Fighter> fighters;
	
	/** The enemy. */
	GameObject enemy;
	
	/** Game Over */
	boolean gameOver;
	
	/** The tm. */
	public TileManager tm;
	
	/** The obj handler. */
	ObjectHandler objHandler;
	
	/** The cam. */
	Camera cam;
	
	/** The entity handler. */
	Handler entityHandler;
	
	/** The player. */
	public static PlayerFighter player;
	
	/** The display. */
	public static FightScreenDisplay display;
	
	/** The ui. */
	public SpriteSheet ui;
	/** The buttons. */
	public static Block[] buttons;
	
	/** Sound Player */
	SoundPlayer soundPlayer;
	
	/** The icon. */
	public SpriteSheet icon;
	
	/** The top level. */
	static HierarchicalStateMachine topLevel;
	
	/** List of stateMachines */
	List<SubMachineState> machines;
	
	

	/**
	 * Fight state.
	 *
	 * @param gsm the gsm
	 */
	public FightState(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	/**
	 * Sets the game world enemy.
	 *
	 * @param enemy the new game world enemy
	 */
	public void setGameWorldEnemy(GameObject enemy) {
		this.enemy = enemy;
	}
	


	/**
	 * Inits the.
	 *
	 */
	public void init() {
		FightState.lastTurn = -1;
		FightState.fighters = new ArrayList<Fighter>();
		FightState.display = new FightScreenDisplay(this);
		
		ui =  new SpriteSheet("images/ui/ui_big_pieces.png");
		icon = new SpriteSheet("images/ui/icons2.png");
		
		entityHandler = new Handler();
		tm = new TileManager("levels/FightScene.xml",Game.SCALER);
		
		//create camera that follows the player
		this.cam = new Camera(new Vector2(0,0),
				Game.SCREEN_WIDTH,Game.SCREEN_HEIGHT,null, tm);
		//shift box
		cam.getBox().setCentreOffset(new Vector2(cam.getBox().getWidth()/2,
				cam.getBox().getHeight()/2));

		//create the handler that will hold all entity

		this.objHandler = new ObjectHandler(cam,tm);
		objHandler.addHandler(entityHandler);

		
		topLevel = new HierarchicalStateMachine("Top Level");
		

		//start the music
		soundPlayer = new SoundPlayer();
		soundPlayer.load("src/resources/sounds/war-is-coming.wav");
		soundPlayer.playContinuos();
		soundPlayer.setVolume(80);
	}
	
	/**
	 * Next turn. This switches to the next fighter in the list 
	 */
	public static void nextTurn() {
		//topLevel = fighters.get(turn).getStateM().createStates();
		turn++;
		if (turn == fighters.size())turn = 0;
		topLevel = fighters.get(turn).getHStateM();
		
	}
	
	/**
	 * Method that adds the buttons to the screen
	 */
	public void addButton() {
		TileMap buttonMap = new TileMapNormal(0,ID.NormalBlock);

		//attack button 
		Sprite buttonNormal = ui.getSpriteExact(15,288, 55, 40);
		Sprite buttonPressed = ui.getSpriteExact(15,370, 55, 40);
		BufferedImage topLayer = icon.getSubimage(33, 162, 32, 32);
		int buttonWidth = 80;
		int buttonHeight = 64;
		buttonMap.addToBlockArray(new ButtonBlock(
				buttonNormal.image,10,700, buttonWidth, buttonHeight, ID.Block,//bottom layer
				0,//button number
				topLayer,48,48,new Vector2(-11,-21),//top layer
				buttonPressed.image,buttonWidth,buttonHeight));//button pressed 
		
		
		//defend button 
		topLayer = icon.getSubimage(33, 194, 32, 32);
		buttonWidth = 80;
		buttonHeight = 64;
		buttonMap.addToBlockArray(new ButtonBlock(
				buttonNormal.image,10,765, buttonWidth, buttonHeight, ID.Block,//bottom layer
				1,//button number
				topLayer,48,48,new Vector2(-12,-21),//top layer
				buttonPressed.image,buttonWidth,buttonHeight));//button pressed 
		
		//health buttons 3X 
		buttonNormal = ui.getSpriteExact(769,24, 25, 25);
		buttonPressed = ui.getSpriteExact(769,49, 24, 24);
		topLayer = icon.getSubimage(134, 292, 32, 32);
		buttonWidth = 64;
		buttonHeight = 64;
		//slot 1
		int buttonNum = 2;
		Vector2[] slotLocations = new Vector2[] {
				new Vector2(10,875),new Vector2(70,875),new Vector2(130,875)};
		for (int i = 0; i < player.getHealthBottles();i++) {
			buttonMap.addToBlockArray(new ButtonBlock(
					buttonNormal.image,
					(int)slotLocations[i].getX(),
					(int)slotLocations[i].getY(), 
					buttonWidth, buttonHeight, ID.Block,//bottom layer
					buttonNum++,//button number
					topLayer,48,48,new Vector2(-14,-18),//top layer
					buttonPressed.image,buttonWidth,buttonHeight));//button pressed 
		}
		
		tm.tm.add(buttonMap);
		buttons = buttonMap.getBlocks();
	}
	
	/**
	 * Adds the fighter.
	 *
	 * @param fighter the fighter
	 */
	public void addFighter(Fighter fighter) {
		fighter.getKinematic().setPosition(null);
		entityHandler.addObject(fighter);
		fighters.add(fighter);
	}
	
	/**
	 * Adds the fighters to the fightScene
	 *
	 * @param fighter the fighter
	 * @param playerAttacked - which player caused the collision in world 
	 */
	public void addFighters(List<Fighter> fighter, boolean playerAttacked) {
		
		//do not include player 
		int randomStart = Random.getRndInteger(1, fighter.size() -1);
		
		for (int i = 0;i < fighter.size();i++) {
			Fighter f = fighter.get(i);
			//if player attacked first let him go first else 
			if (playerAttacked && f instanceof PlayerFighter) {
				topLevel = f.getHStateM();
			}
			else if (!playerAttacked) {
				topLevel = fighter.get(randomStart).getHStateM();
				turn = randomStart;
			}
			//set the positions of the fighters based on the tilemanager locations 
			f.getKinematic().setPosition(tm.locations.get(i).getPosition());
			f.getBox().updateBoundingBox(tm.locations.get(i).getPosition());
			entityHandler.addObject(f);
			fighters.add(f);
			if (f instanceof PlayerFighter)player = (PlayerFighter) f;
		}
		
		addButton();
	}
	
	/**
	 * Check if fighter dead.
	 *
	 */
	public static void checkIfFighterDead() {
		for (int i = 0 ; i < fighters.size();i++) {
			if (fighters.get(i).getHealth() <= 0 && !fighters.get(i).isDead()) {
				lastTurn = turn;
				topLevel = fighters.get(i).getHStateM();

			}
		}
	}
	
	/**
	 * Reset to last turn. This is used when a enemy is killed during the players turn and needs
	 * to reset back to original turn. Allows the enemy to die when killed vs waiting 
	 *
	 */
	public static void resetToLastTurn() {
		topLevel = fighters.get(lastTurn).getHStateM();
		lastTurn = -1;
	}

	@Override
	public void input() {/* no mouse input*/}


	@Override
	public void keyPressed(KeyEvent e) {
		if (gameOver) {
			//restart game
			if (e.getKeyCode() == KeyEvent.VK_F8) {
				gsm.pop(GameStateManager.FIGHT);
				gsm.pop(GameStateManager.PLAY);
				gsm.add(GameStateManager.PLAY);
			}
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {/* not used */}


	@Override
	public void render(Graphics2D g, ImageObserver observer, float x, float y) {
		//render all the objects 
		objHandler.render(g,observer, x, y);
		
		//render health bars, health bottles
		for (int i = 0 ; i < fighters.size();i++) {
			display.renderHealth(g, observer, fighters.get(i));
			if (fighters.get(i).getSelected())
				display.renderPlayerHeal(g, observer, fighters.get(i));
			if (fighters.get(i) instanceof EnemyFighter) {
				display.renderHealthBottles(g, observer, fighters.get(i));
			}
		}
		//if not game over show hit point damages on screen
		if (!gameOver) {
			display.renderPlayerHit(g,observer);
			if (turn == 0)display.renderYourTurn(g, observer);
		}
		else 
			display.renderLose(g, observer);
		
	}
	

	@Override
	public void update(float elapsedTime) {
		if (!gameOver) {
			objHandler.update(elapsedTime);
			
			//hierarchical state machine 
			UpdateResult result = topLevel.update();
			Action[] actions = result.getActions();
			for (Action a : actions) {
				a.performAction(elapsedTime);
			}
			
			//if player is dead game over 
			if (player.isDead())gameOver = true;
			
			//check if all enemies are dead 
			if (enemiesDead()) {
				gsm.pop(GameStateManager.FIGHT);
				gsm.resumeState(GameStateManager.PLAY);
				PlayState.player.setWorldHealth(FightState.player.getHealth()); 
				PlayState.player.setWorldHealthBottles(FightState.player.getHealthBottles()); 
				PlayState.entityHandler.removeObject(enemy);
				try {
					((PlayState)gsm.getState(GameStateManager.PLAY)).soundPlayer.resumeAudio();
				} catch (UnsupportedAudioFileException | IOException
						| LineUnavailableException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				try {
					soundPlayer.stop();
				} catch (UnsupportedAudioFileException | IOException
						| LineUnavailableException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * Enemy dead.
	 */
	public static void enemyDead() {
		for (Fighter fighter : fighters) {
			if (fighter instanceof EnemyFighter)
				if (fighter.getHealth() == 0) {
					fighters.remove(fighter);
				}
		}
	}
	
	/**
	 * Enemies dead.
	 *
	 * @return true, if successful
	 */
	@SuppressWarnings("static-method")
	public boolean enemiesDead() {
		if (fighters.size() == 1)return true;
		return false;
	}

}
