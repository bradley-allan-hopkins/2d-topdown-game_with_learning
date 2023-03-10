/**
 * Title: Game.java
 * This class runs the game from main and starts a continuous loop until player quits.
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 4, 2022
 */
package game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;

import gameObjects.Testing;
import state.GameStateManager;


/**
 * The Class Game.
 */
public class Game extends Canvas implements Runnable{
	
	
	/** The Constant GRID_TILE_SIZE. */
	public static final int GRID_TILE_SIZE = 16;
	
	/** The Constant BLOCKS_WIDE. */
	static final int BLOCKS_WIDE = 20;
	
	/** The Constant BLOCKS_HIGH. */
	static final int BLOCKS_HIGH = 20;
	
	/** The Constant SCALER. */
	public static final int SCALER = 3;
	
	/** Actual Tile Size */
	public static final int TILE_SIZE = GRID_TILE_SIZE * SCALER;
	
	/** The Constant SCREEN_WIDTH. */
	public static final int SCREEN_WIDTH = BLOCKS_WIDE * GRID_TILE_SIZE * SCALER;
	
	/** The Constant SCREEN_HEIGHT. */
	public static final int SCREEN_HEIGHT = BLOCKS_HIGH * GRID_TILE_SIZE * SCALER;
	
	/** The Constant TITLE. */
	static final String TITLE = "Assignment 3 - Game 2";
	
	/** The game over. */
	boolean gameOver = false;
	
	/** The window. */
	public static Window window;

	/** The mouse handler. */
	MouseHandler mouseHandler;
	
	/** The game state manager. */
	GameStateManager gameStateManager;

	
	/** The running. */
	private boolean running = false;
	
	/** The thread. */
	private Thread thread;
	
	/**
	 * Instantiates a new game().
	 */
	public Game() {
		window = new Window(SCREEN_WIDTH,SCREEN_HEIGHT,TITLE,this);
		gameStateManager = new GameStateManager();
		mouseHandler = new MouseHandler(gameStateManager);
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
		
		this.addKeyListener(new MyKeyAdapter(gameStateManager));
		this.requestFocus();
	}
	
	/**
	 * Start.
	 */
	private synchronized void start() {
		if (running) return;
		
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * Stop.
	 */
	private synchronized void stop() {
		if(!running) return;
		
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(1);
	}
	
	/**
	 * Run.This is the main game loop
	 */
	@Override
	public void run() {
		
		long lastTime = System.nanoTime();
		final double amountOfTicks = 60.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		int updates = 0;
		int frames = 0;
		long timer = System.currentTimeMillis();
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			if (delta >= 1) {
				delta--;
				//update((float)delta);
				update((float)delta);
				render();
				updates++;
				
				
			}
			if (running)
				//update((float)delta);
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				System.out.println(updates + "Updates, FPS " + frames);
				updates = 0;
				frames = 0;
			}
			
		}
		stop();
	}
	
	/**
	 * Update all game objects 
	 */
	private void update(float elapsedTime) {
		float time = elapsedTime;
		time = 1;
		//update elapedTime to not fluctuate
		gameStateManager.update(time);
		gameStateManager.input();
		Testing.input();
	}
	
	/**
	 * Render all game objects 
	 */
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		
		if (bs == null) {
			createBufferStrategy(3);//3 buffers if available
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

		// -------------DRAW SECTION---------------

		gameStateManager.render(g2d,this,0,0);

		//------------END OF DRAW SECTION--------------------
		
		bs.show();
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		Game game= new Game();
		
		game.start();
	}
}





