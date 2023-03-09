/**
 * Title: AnimateRight.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 16, 2022
 */
package gameObjects.characters.minotaur;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import gameObjects.GameObject;
import gameObjects.characters.Entity;
import graphics.AnimateAction;

/**
 * The Class AnimateRight. This action renders the object facing right
 */
public class AnimateRight extends AnimateAction {

	/** The obj. */
	GameObject obj;
	
	/**
	 * Animate right.
	 *
	 * @param obj the obj
	 */
	public AnimateRight(GameObject obj) {
		this.obj = obj;
	}
	
	@Override
	public void render(Graphics2D g, ImageObserver observer, float x, float y) {
		Entity e = ((Entity)obj);
		
		g.drawImage(e.getAnimation().getImage().image, 
				(int)(obj.getPosX() - obj.getUnitWidth()/2 - x ),
				(int)(obj.getPosY() - obj.getUnitHeight()/2 - y) ,
				(int)obj.getUnitWidth(),
				(int)obj.getUnitHeight(),
				observer);
	}

}
