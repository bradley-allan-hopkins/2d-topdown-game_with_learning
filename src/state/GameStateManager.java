/**
 * Title: GameStateManager.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 8, 2022
 *  Class skeleton used from com.zerulus.game
 */
package state;


import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.Arrays;

import gameObjects.Camera;
import graphics.Font;
import graphics.Fontf;
import graphics.SpriteSheet;
import state.states.FightState;
import state.states.GameOverState;
import state.states.GameState;
import state.states.IntroState;
import state.states.MenuState;
import state.states.PauseState;
import state.states.PlayState;

/**
 * The Class GameStateManager.
 */
public class GameStateManager {

	/** The Constant MENU. */
	public static final int MENU = 0;


	/** The Constant PLAY. */
	public static final int PLAY = 1;

	/** The Constant PAUSE. */
	public static final int PAUSE = 2;

	/** The Constant GAMEOVER. */
	public static final int GAMEOVER = 3;

	/** The Constant EDIT. */
	public static final int EDIT = 4;

	/** The Constant INTRO. */
	public static final int INTRO = 5;
	
	/** The Fight Scene */
	public static final int FIGHT = 6;

	/** The font. */
	public static Font font;

	/** The fontf. */
	public static Fontf fontf;

	/** The ui. */
	public static SpriteSheet ui;

	/** The button. */
	public static SpriteSheet button;

	/** The cam. */
	public static Camera cam;

	/** The states. */
	private GameState states[];
	
	/** If the states are currently paused */
	private boolean paused[];

	/**
	 * Instantiates a new game state manager().
	 */
	public GameStateManager() {

		states = new GameState[7];
		paused = new boolean[7];
		Arrays.fill(paused, false);
		button = new SpriteSheet("images/ui/buttons.png", 122, 57);
		//states[MENU] = new MenuState(this);
		states[PLAY] = new PlayState(this);
		//states[FIGHT] = new FightState(this);
	}

	/**
	 * Adds the.
	 *
	 * @param state the state
	 */
	public void add(int state) {
		if (states[state] != null)
			return;

		if (state == PLAY) {
			//cam = new Camera(new AABB(new Vector2f(0, 0), GamePanel.width + 64, GamePanel.height + 64));
			states[PLAY] = new PlayState(this);
		}
		else if (state == MENU) {
			states[MENU] = new MenuState(this);
		}
		else if (state == PAUSE) {
			states[PAUSE] = new PauseState(this);
		}
		else if (state == GAMEOVER) {
			states[GAMEOVER] = new GameOverState(this);
		}
		else if (state == INTRO) {
			states[INTRO] = new IntroState(this);
		}
		else if (state == EDIT) {
			if(states[PLAY] != null) {
				//states[EDIT] = new EditState(this);
			}
		}
		else if (state == FIGHT) {
			states[FIGHT] = new FightState(this);
		}
	}
	
	/**
	 * Pause the state so that it is not updated or rendered
	 *
	 * @param state the state
	 */
	public void pauseState(int state) {
		paused[state] = true;
	}
	
	/**
	 * Resume state.
	 *
	 * @param state the state
	 */
	public void resumeState(int state) {
		paused[state] = false;
	}
	

	/**
	 * Adds the andpop.
	 *
	 * @param state the state
	 */
	public void addAndpop(int state) {
		addAndpop(state, 0);
	}

	/**
	 * Adds the andpop.
	 *
	 * @param state the state
	 * @param remove the remove
	 */
	public void addAndpop(int state, int remove) {
		pop(state);
		add(state);
	}

	/**
	 * Gets the variable "State".
	 *
	 * @param state the state
	 * @return game state - State
	 */
	public GameState getState(int state) {
		return states[state];
	}

	/**
	 * Input.
	 */
	public void input() {

		for (int i = 0; i < states.length; i++) {
			if (states[i] != null) {
				if (!paused[i])
					states[i].input();
			}
		}
	}

	/**
	 * Checks if is state active.
	 *
	 * @param state the state
	 * @return true, if is state active
	 */
	public boolean isStateActive(int state) {
		return states[state] != null;
	}

	/**
	 * Method that
	 * @param e - the key the user pushed 
	 */
	public void keyPressed(KeyEvent e) {
		for (int i = 0; i < states.length; i++) {

			if (states[i] != null) {
				if (!paused[i])
					states[i].keyPressed(e);
			}
		}

	}

	/**
	 * Method that
	 * @param e - key user pushed 
	 */
	public void keyReleased(KeyEvent e) {
		for (int i = 0; i < states.length; i++) {

			if (states[i] != null) {
				states[i].keyReleased(e);
			}
		}
	}

	/**
	 * Pop and set paused status to false
	 *
	 * @param state the state
	 */
	public void pop(int state) {
		states[state] = null;
		paused[state] = false;
	}

	/**
	 * Render.
	 *
	 * @param g the g
	 * @param observer the observer
	 * @param x the x
	 * @param y the y
	 */
	public void render(Graphics2D g,ImageObserver observer,float x, float y) {
		for (int i = 0; i < states.length; i++) {
			if (states[i] != null) {
				if (!paused[i])
					states[i].render(g,observer,x,y);
			}
		}
	}

	/**
	 * Update.
	 * @param elapsedTime - game loop time 
	 */
	public void update(float elapsedTime) {
		for (int i = 0; i < states.length; i++) {
			if (states[i] != null) {
				if (!paused[i])
					states[i].update(elapsedTime);
			}
		}
	}
	
	/**
	 * Swap positions in the array
	 * @param i - swap i for k
	 * @param k - swap k for i 
	 */
	public void swap(int i, int k) {
		GameState temp = states[i];
		states[i] = states[k];
		states[k] = temp;
		
	}

}
