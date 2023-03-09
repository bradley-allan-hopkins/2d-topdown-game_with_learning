/**
 * Title: PauseState.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 8, 2022
 * Class skeleton used from com.zerulus.game
 */
package state.states;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

import state.GameStateManager;


/**
 * The Class PauseState.
 */
public class PauseState extends GameState {

	/** The btn resume. */
	//private Button btnResume;

	/** The btn exit. */
	//private Button btnExit;

	/** The font. */
	//private Font font;

	/**
	 * Pause state.
	 *
	 * @param gsm the gsm
	 */
	public PauseState(GameStateManager gsm) {
		super(gsm);

		//BufferedImage imgButton = GameStateManager.button.getSubimage(0, 0, 121, 26);
		//
		//font = new Font("MeatMadness", Font.PLAIN, 48);
		//btnResume = new Button("PAUSED");
		// btnExit = new Button("EXIT", imgButton, font, new Vector2f(GamePanel.width / 2, GamePanel.height / 2 + 48), 32, 16);

		//btnResume.addHoverImage(btnResume.createButton("RESUME", imgHover, font, btnResume.getWidth(), btnResume.getHeight(), 32, 20));
		//btnExit.addHoverImage(btnExit.createButton("EXIT", imgHover, font, btnExit.getWidth(), btnExit.getHeight(), 32, 20));

		//btnResume.addEvent(e -> {
		//   gsm.pop(GameStateManager.PAUSE);
		// });

		// btnExit.addEvent(e -> {
		//     System.exit(0);
		// });
	}


	@Override
	public void input() {
		//  btnResume.input(mouse, key);
		//  btnExit.input(mouse, key);
	}


	@Override
	public void keyPressed(KeyEvent e) {/* not used*/}

	@Override
	public void keyReleased(KeyEvent e) {/** not used*/}


	@Override
	public void render(Graphics2D g,ImageObserver observer,float x, float y) {
		// btnResume.render(g);
		//btnExit.render(g);
	}

	@Override
	public void update(float elapsedTime) {/* not used*/}
}
