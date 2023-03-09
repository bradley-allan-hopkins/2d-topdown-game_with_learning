/**
 * Title: Handler.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 11, 2022
 */
package gameObjects.handlers;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

import gameObjects.GameLogic;
import gameObjects.GameObject;

/**
 * This object holds a list of GameObjects and calls all their updates and then renders them
 * to screen
 *
 * @author bradl
 */
public class Handler implements GameLogic{
	
	
	/** List of Game Objects */
	protected List<GameObject> gameObjects;
	
	/** Instantiates a new handler(). */
	public Handler() {
		init();
	}
	
	/**
	 * Initializes the object
	 */
	@Override
	public void init() {
		gameObjects = new ArrayList<GameObject>();
	}
	
	@Override
	public void update(float elapsedTime) {
		for (int i = 0; i < gameObjects.size();i++) {
			gameObjects.get(i).update(elapsedTime);
		}
	}
	
	@Override
	public void render(Graphics2D g, ImageObserver observer,float x, float y) {
		for (int i = 0; i < gameObjects.size();i++) {
			gameObjects.get(i).render(g,observer,x,y);
		}
	}

	/**
	 * Method that add objects to the handler.
	 *
	 * @param gameObject the game object
	 */
	public void addObject(GameObject gameObject) {
		this.gameObjects.add(gameObject);
	}
	
	/**
	 * Method that removes game object from handler.
	 * @param gameObject the game object
	 */
	public void removeObject(GameObject gameObject) {
		this.gameObjects.remove(gameObject);
	}
	
	/**
	 * Method that returns the list of all game objects in the game.
	 * @return list - List
	 */
	public List<GameObject> getList(){
		return gameObjects;
	}

}
