/**
 * Title: PlayState.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package state.states;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

import ai.statemachine.HierarchicalStateMachine;
import ai.learning.q.QLearning;
import game.Game;
import game.MouseHandler;
import gameObjects.Camera;
import gameObjects.ID;
import gameObjects.Testing;
import gameObjects.characters.Player;
import gameObjects.characters.minotaur.Minotaur;
import gameObjects.characters.minotaur.MinotaurStateMachine;
import gameObjects.collisions.CollisionDetection;
import gameObjects.collisions.shapes.Rectangle;
import gameObjects.events.Event;
import gameObjects.handlers.Handler;
import gameObjects.handlers.ObjectHandler;
import math.Vector2;
import sounds.SoundPlayer;
import state.GameStateManager;
import state.states.menu.ScreenDisplay;
import tiles.TileManager;
import tiles.blocks.Block;
import tiles.locations.LocationPoint;
import utils.Random;
import utils.ThreeTuple;

/**
 * The Class PlayState.
 */
public class PlayState extends GameState {

	/** The entity handler. */
	public static Handler entityHandler;

	/** The tile manager for this level. */
	public static TileManager tm;

	/** The events. */
	public static List<Event> events;

	/** Level Areas */
	public static List<Rectangle> areas;

	/** The collision detection. */
	private CollisionDetection collisionDetection;
	
	/** The Player */
	public static Player player;

	/** The obj handler. */
	private ObjectHandler objHandler;
	/** The cam. */
	private Camera cam;

	/** The top level. */
	HierarchicalStateMachine topLevel;
	
	/** The music player */
	SoundPlayer music;

	/** The buttons. */
	Block button;
	
	/** Finish Line */
	Vector2 finish;
	
	/** Game Over */
	boolean gameOver;
	
	/** Who Won */
	int won;

	/** learning */
	QLearning learning;
	
	
	/** The iterations. */
	int iterations;
	
	/** The location. */
	int location = 0;
	
	/** The rho. */
	float rho = 0.3f;
	
	/** SoundPlayer */
	public SoundPlayer soundPlayer;
	
	/** ScreenDisplay*/
	public static ScreenDisplay display;
	
	/**
	 * Play state.
	 *
	 * @param gsm the gsm
	 */
	public PlayState(GameStateManager gsm) {
		super(gsm);
		init();
	}


	/**
	 * Inits the.
	 */
	public void init() {
		
		PlayState.display = new ScreenDisplay();
		tm = new TileManager("levels/world.xml",Game.SCALER);
		areas = new ArrayList<Rectangle>();
		PlayState.events = new ArrayList<Event>();
		PlayState.entityHandler = new Handler();
		collisionDetection = new CollisionDetection(tm);
		for (int i = 0; i < tm.locations.size();i++) {
			if (tm.locations.get(i).getName().equalsIgnoreCase("Finish")){
				finish = tm.locations.get(i).getPosition();
				break;
			}
		}
		soundPlayer = new SoundPlayer();
		soundPlayer.load("src/resources/sounds/epic-travel.wav");
		soundPlayer.playContinuos();
		soundPlayer.setVolume(80);
		startGame();
	}


	@Override
	public void input() {/* not used - could have player use mouse for controls*/}


	@Override
	public void keyPressed(KeyEvent e) {
		cam.keyPressed(e);
		if (!gameOver)
			player.keyPressed(e);	
		else {
			//restart game
			if (e.getKeyCode() == KeyEvent.VK_F8) {
				gsm.pop(GameStateManager.FIGHT);
				gsm.pop(GameStateManager.PLAY);
				gsm.add(GameStateManager.PLAY);
			}
		}
	}


	@Override
	public void keyReleased(KeyEvent e) {
		cam.keyReleased(e);
		player.keyReleased(e);
	}

	/**
	 * Load tile map.
	 *
	 * @param name the name
	 */
	@SuppressWarnings("static-method")
	public void loadTileMap(String name) {
		PlayState.tm = new TileManager(name,Game.SCALER);
	}


	/**
	 * Load tile map.
	 *
	 * @param tilemanager the tilemanager
	 */
	@SuppressWarnings("static-method")
	public void loadTileMap(TileManager tilemanager) {
		PlayState.tm = tilemanager;
	}


	@Override
	public void render(Graphics2D g,ImageObserver observer,float x, float y){
		objHandler.render(g,observer, x, y);
		display.renderLearning(g, observer);
		
		if (gameOver) {
			g.setColor(Color.WHITE);
			g.setFont( new Font("Ink Free",Font.BOLD, 60));
			g.drawString("You Win" ,325 , 500);
			g.drawString("PUSH F8 TO RESTART" ,150, 560);
		}
	}

	/**
	 * Start game.
	 */
	public void startGame() {


		player = new Player(new Vector2(100,100),64,64,2,ID.Player,
				new ThreeTuple<String, Integer, Integer>("/images/linkFormatted.png",32,32),gsm);
		
		entityHandler.addObject(player);
		//create camera that follows the player
		this.cam = new Camera(new Vector2(0,0),
				Game.SCREEN_WIDTH,Game.SCREEN_HEIGHT,player, tm);
		//shift box
		cam.getBox().setCentreOffset(new Vector2(cam.getBox().getWidth()/2,
				cam.getBox().getHeight()/2));

		//create the handler that will hold all entity

		this.objHandler = new ObjectHandler(cam,tm);
		objHandler.addHandler(entityHandler);
		
		createEnemies();
	}
	
	/**
	 * Creates the enemies.

	 */
	public void createEnemies() {
		
		List<LocationPoint> list = tm.locations;
		for (int i = 0; i < list.size();i++) {
			if (list.get(i).getName().equals("")){
				Minotaur minotaur = new Minotaur(list.get(i).getPosition(),124,124,2,ID.Minotaur,
						new ThreeTuple<String,
						Integer,Integer>("/images/Minotaur - Sprite Sheet.png",
						96,96),gsm);
				MinotaurStateMachine msm = new MinotaurStateMachine(minotaur,tm);
				minotaur.getKinematic().setOrientation(Random.getRndInteger(0, 6));
				minotaur.loadStateMachine(msm.createStates());
				entityHandler.addObject(minotaur);
			}
			else if (list.get(i).getName().equalsIgnoreCase("Bridge") || 
					list.get(i).getName().equalsIgnoreCase("End")) {
				Minotaur minotaur = new Minotaur(list.get(i).getPosition(),124,124,2,ID.Minotaur,
						new ThreeTuple<String,
						Integer,Integer>("/images/Minotaur - Sprite Sheet.png",
						96,96),gsm);
				minotaur.setAnimation(10, 20, 5);
				entityHandler.addObject(minotaur);
			}
		}
	}
	

	@Override
	public void update(float elapsedTime) {
		if (!gameOver) {
			if (!gsm.isStateActive(GameStateManager.PAUSE)) {
					
	
					if (Testing.viewMousePositions) {
						System.out.println((MouseHandler.getX() + cam.getPosX()) + "," + 
					(MouseHandler.getY() + cam.getPosY()));
					}
					objHandler.update(elapsedTime);
					collisionDetection.checkForCollision(elapsedTime,cam);
					if (player.getPosition().subtractTwoVectors(finish).getLength() < 20) {
						gameOver = true;
					}
				}
		}
	}
	
}
