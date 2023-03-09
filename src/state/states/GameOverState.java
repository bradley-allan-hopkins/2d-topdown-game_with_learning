/**
 * Title: GameOverState.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 8, 2022
 *  Class skeleton used from com.zerulus.game
 */
package state.states;

import state.GameStateManager;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

/**
 * The Class GameOverState.
 */
public class GameOverState extends GameState {

    /** The gameover. */
   // private String gameover = "GAME OVER";

    /** The img button. */
   // private BufferedImage imgButton;
    
    /** The img hover. */
   // private BufferedImage imgHover;
    //private Button btnReset;
    /** The font. */
    //private Button btnQuit;
   // private Font font;


    /**
     * Game over state.
     *
     * @param gsm the gsm
     */
    public GameOverState(GameStateManager gsm) {
        super(gsm);
        /*
        imgButton = GameStateManager.button.getSubimage(0, 0, 121, 26);
        imgHover = GameStateManager.button.getSubimage(0, 29, 122, 28);

        font = new Font("MeatMadness", Font.PLAIN, 48);
        btnReset = new Button("RESTART", imgButton, font, new Vector2f(GamePanel.width / 2, GamePanel.height / 2 - 48), 32, 16);
        btnQuit = new Button("QUIT", imgButton, font, new Vector2f(GamePanel.width / 2, GamePanel.height / 2 + 48), 32, 16);

        btnReset.addHoverImage(btnReset.createButton("RESTART", imgHover, font, btnReset.getWidth(), btnReset.getHeight(), 32, 20));
        btnQuit.addHoverImage(btnQuit.createButton("QUIT", imgHover, font, btnQuit.getWidth(), btnQuit.getHeight(), 32, 20));
        
        btnReset.addEvent(e -> {
            gsm.add(GameStateManager.PLAY);
            gsm.pop(GameStateManager.GAMEOVER);
        });

        btnQuit.addEvent(e -> {
            System.exit(0);
        });*/
    }



   /* @Override
    public void input(KeyHandler key) {
       key.escape.tick();

       // btnReset.input(mouse, key);
        //btnQuit.input(mouse, key);

        if (key.escape.clicked) {
			System.exit(0);
		}
    }*/


   @Override
    public void render(Graphics2D g,ImageObserver observer,float x, float y) {
        //SpriteSheet.drawArray(g, gameover, new Vector2f(GamePanel.width / 2 - gameover.length() * (32 / 2), GamePanel.height / 2 - 32 / 2), 32, 32, 32);
        //btnReset.render(g);
       // btnQuit.render(g);
    }


	@Override
	public void keyPressed(KeyEvent e) {/* Not Used*/}

	@Override
	public void keyReleased(KeyEvent e) {/* Not used*/}


	@Override
	public void update(float elapsedTime) {/* Not used*/}

	@Override
	public void input() {/* Not used*/}


}
