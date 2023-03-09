/**
 * Title: FightScreenDisplay.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 17, 2022
 */
package state.states.fightstate;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import javax.swing.JPanel;

import gameObjects.GameObject;
import gameObjects.characters.fighters.Fighter;
import graphics.Sprite;
import state.states.FightState;


/**
 * The Class ScreenDisplay. This class displays health, damages, etc in the FightState screen
 */
public class FightScreenDisplay extends JPanel{
	
	/** The damage. */
	public String damage;
	
	/** The state. */
	FightState state;
	
	/** Selected Player */
	public Fighter selected;
	
	/** The timer. */
	public int timer = 0;//used to display on screen for 'x' frames
	
	/** heal timer */
	public int healTimer = 0;
	
	/**
	 * Fight screen display.
	 *
	 * @param state the state
	 */
	public FightScreenDisplay(FightState state) {
		this.state = state;
	}
	
	
	/**
	 * Render player hit.
	 * @param g - graphics 
	 * @param observer - screen observer
	 */
	public void renderPlayerHit(Graphics2D g, ImageObserver observer) {
		if (timer > 0) {
			g.setColor(Color.RED);
			g.setFont( new Font("Ink Free",Font.BOLD, 60));
			FontMetrics metrics = getFontMetrics(g.getFont());
			//g.drawString(damage,465 - (metrics.stringWidth
			//		(damage)/2), 700);
			g.drawString(damage,selected.getPosX() - (metrics.stringWidth
					(damage)/2), selected.getPosY());
			timer--;
		}
	}
	
	/**
	 * Render player hit.
	 * @param g - graphics 
	 * @param observer - screen observer
	 * @param obj - the obj that is healed 
	 */
	public void renderPlayerHeal(Graphics2D g, ImageObserver observer,GameObject obj) {
		if (healTimer > 0) {
			BufferedImage topLayer = state.icon.getSubimage(134, 292, 31, 32);
			
			int width = 50;
			int height = 50;
			//draw button
			g.drawImage(topLayer, (int)(obj.getBox().getCentreOfBox().getX()), 
				(int)(obj.getPosY()), width,height,observer);
			
			healTimer--;
		}
		
	}
	
	/**
	 * Render the you have died screen
	 *
	 * @param g the g
	 * @param observer the observer
	 */
	public void renderLose(Graphics2D g, ImageObserver observer) {
		g.setColor(Color.WHITE);
		g.setFont( new Font("Ink Free",Font.BOLD, 60));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString(" YOU DIED" ,465 - (metrics.stringWidth
				(" YOU DIED")/2), 500);
		g.drawString("PUSH F8 TO RESTART" ,465 - (metrics.stringWidth
				("PUSH F8 TO RESTART")/2), 600);
	}
	
	/**
	 * Render the you have died screen
	 *
	 * @param g the g
	 * @param observer the observer
	 */
	public void renderWin(Graphics2D g, ImageObserver observer) {
		g.setColor(Color.WHITE);
		g.setFont( new Font("Ink Free",Font.BOLD, 60));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("You Win" ,465 - (metrics.stringWidth
				("YOu Win")/2), 500);
		g.drawString("PUSH F8 TO RESTART" ,465 - (metrics.stringWidth
				("PUSH F8 TO RESTART")/2), 600);
	}
	
	/**
	 * Render to the top of screen 'your turn'
	 *
	 * @param g the g
	 * @param observer the observer
	 */
	public void renderYourTurn(Graphics2D g, ImageObserver observer) {
		g.setColor(Color.WHITE);
		g.setFont( new Font("Ink Free",Font.BOLD, 60));
		FontMetrics metrics = getFontMetrics(g.getFont());
		g.drawString("Your Turn" ,465 - (metrics.stringWidth
				("Your Turn")/2), 60);

	}
	
	/**
	 * Render health bars
	 *
	 * @param g the g
	 * @param observer the observer
	 * @param obj the obj
	 */
	public void renderHealth(Graphics2D g, ImageObserver observer,Fighter obj) {
		
		//grab images
		Sprite leftBar = state.ui.getSpriteExact(257,36, 23, 24);//left
		Sprite middleBar = state.ui.getSpriteExact(283,36,24,24);//middle
		Sprite rightBar = state.ui.getSpriteExact(308, 36, 25,24);//right
		Sprite health = state.ui.getSpriteExact(342, 40, 8, 14);
		
		//complete bar is 132 pixels and 66 halfway
		//g.drawImage(leftBar.image, 309, 850, 50, 30, observer);
		int healthOffset = 7;
		int barOffset = -2;
		int width = 50;
		int height = 30;
		g.drawImage(leftBar.image, (int)(obj.getBox().getCentreOfBox().getX() - 66), 
				(int)(obj.getPosY() + obj.getBox().getHeight()/2 + barOffset), width,height,observer);
		g.drawImage(middleBar.image,(int)(obj.getBox().getCentreOfBox().getX() - 26),
				(int)(obj.getPosY() + obj.getBox().getHeight()/2 + barOffset),width,height,observer);
		g.drawImage(rightBar.image,(int)(obj.getBox().getCentreOfBox().getX() + 15 ),
				(int)(obj.getPosY() + obj.getBox().getHeight()/2 + barOffset),width,height,observer);
		//health for player 
		g.drawImage(health.image, (int)(obj.getBox().getCentreOfBox().getX() - 41), 
				(int)(obj.getPosY() + obj.getBox().getHeight()/2 + healthOffset), obj.getHealth() , 16, observer);
	}
	
	/**
	 * Render health bars
	 *
	 * @param g the g
	 * @param observer the observer
	 * @param obj the obj
	 */
	public void renderHealthBottles(Graphics2D g, ImageObserver observer,Fighter obj) {
		
		BufferedImage topLayer = state.icon.getSubimage(134, 292, 31, 32);
		
		
		//int healthOffset = 7;
		int barOffset = 35;
		int sideOffset = 40;
		int width = 35;
		int height = 30;
		for (int i = 0; i < obj.getHealthBottles();i++) {
			//draw button
			g.drawImage(topLayer, (int)(obj.getBox().getCentreOfBox().getX() - 52 + (i * sideOffset)), 
				(int)(obj.getPosY() + obj.getBox().getHeight()/2 + barOffset), width,height,observer);
		}
	
	}
	
}
