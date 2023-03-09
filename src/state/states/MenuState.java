/**
 * Title: MenuState.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 18, 2022
 */
package state.states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import state.GameStateManager;

/**
 * The Class MenuState. This class is for a menu 
 */
public class MenuState extends GameState {


	/**
	 * Menu state.
	 *
	 * @param gsm the gsm
	 */
	public MenuState(GameStateManager gsm) {
		super(gsm);

	}


	@Override
	public void input() {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {/*Not used*/}


	@Override
	public void keyReleased(KeyEvent e) {/* not used*/}


	@Override
	public void render(Graphics2D g,ImageObserver observer,float x, float y) {
	}

	@Override
	public void update(float elapsedTime) {
	
	}
}
