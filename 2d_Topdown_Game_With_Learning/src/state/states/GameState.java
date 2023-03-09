/**
 * Title: GameState.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 8, 2022
 */
package state.states;


import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

import state.GameStateManager;

/**
 * The Class GameState.
 */
public abstract class GameState {

	/** Each GameState is controlled by hte GameStateManager */
	protected GameStateManager gsm;



	/**
	 * Game state.
	 *
	 * @param gsm the gsm
	 */
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
	}

	/**
	 * Input used for mouse input
	 */
	public abstract void input();

	/**
	 * Method that
	 * @param e - key user pushed 
	 */
	public abstract void keyPressed(KeyEvent e);

	/**
	 * Method that
	 * @param e - key user released 
	 */
	public abstract void keyReleased(KeyEvent e);

	/**
	 * Render.
	 *
	 * @param g the g
	 * @param observer the observer
	 * @param x the amount to skew the x axis
	 * @param y the amount to skew the y axis
	 */
	public abstract void render(Graphics2D g,ImageObserver observer,float x, float y);

	/**
	 * Update.
	 * @param elapsedTime - game loop time 
	 */
	public abstract void update(float elapsedTime);
	
	/**
	 * Public method used to get
	 * @return the gsm
	 */
	public GameStateManager getGsm() {
		return gsm;
	}

	/**
	 * Public method used to set
	 * @param gsm the gsm to set
	 */
	public void setGsm(GameStateManager gsm) {
		this.gsm = gsm;
	}
}
