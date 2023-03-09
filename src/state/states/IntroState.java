/**
 * Title: IntroState.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 8, 2022
 *  Class skeleton used from com.zerulus.game
 */

package state.states;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.InputStream;

import javax.imageio.ImageIO;

import game.Game;

import state.GameStateManager;


/**
 * The Class IntroState.
 */
public class IntroState extends GameState {
	
	/** The logo. */
	private BufferedImage logo;
	
	/** The alpha. */
	private int alpha;
	
	/** The ticks. */
	private int ticks;
	
	/** The fade in. */
	private final int FADE_IN = 60;
	
	/** The length. */
	private final int LENGTH = 60;
	
	/** The fade out. */
	private final int FADE_OUT = 60;
	
	/**
	 * Intro state.
	 *
	 * @param gsm the gsm
	 */
	public IntroState(GameStateManager gsm) {
		super(gsm);
		init();
	}
	
	/**
	 * Inits the.
	 */
	public void init() {
		ticks = 0;
		try {
			InputStream input = getClass().getResourceAsStream("/images/title.png");
			logo = ImageIO.read(input);
			input.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	

	@Override
	public void update(float elapsedTime) {
		//handleInput();
		ticks++;
		if(ticks < FADE_IN) {
			alpha = (int) (255 - 255 * (1.0 * ticks / FADE_IN));
			if(alpha < 0) alpha = 0;
		}
		if(ticks > FADE_IN + LENGTH) {
			alpha = (int) (255 * (1.0 * ticks - FADE_IN - LENGTH) / FADE_OUT);
			if(alpha > 255) alpha = 255;
		}
		if(ticks > FADE_IN + LENGTH + FADE_OUT) {
			gsm.add(GameStateManager.PLAY);
			gsm.pop(GameStateManager.INTRO);
		}
	}

	@Override
	public void render(Graphics2D g,ImageObserver observer,float x, float y) {
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
		g.drawImage(logo, 0, 0, Game.SCREEN_WIDTH,Game.SCREEN_HEIGHT, null);
		g.setColor(new Color(0, 0, 0, alpha));
		g.fillRect(0, 0, Game.SCREEN_WIDTH, Game.SCREEN_HEIGHT);
	}
	


	@Override
	public void input() {/* not used*/}


	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			gsm.add(GameStateManager.PLAY);
			gsm.pop(GameStateManager.INTRO);
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {/* not used*/}
	
}