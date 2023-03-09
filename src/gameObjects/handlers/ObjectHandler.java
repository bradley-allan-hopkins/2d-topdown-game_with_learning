/**
 * Title: ObjectHandler.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 21, 2022
 */
package gameObjects.handlers;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

import game.MouseHandler;
import gameObjects.Camera;
import gameObjects.GameLogic;
import gameObjects.GameObject;
import gameObjects.ID;
import gameObjects.Testing;
import gameObjects.collisions.CollisionDetection;
import math.Vector2;
import tiles.TileManager;
import tiles.TileMap;
import tiles.blocks.Block;
import tiles.blocks.LineOfSightBlock;

/**
 * The Class ObjectHandler.
 */
public class ObjectHandler implements GameLogic {

	/**
	 *  A list of all the tileMaps.
	 */
	List<TileMap> tilemaps;
	
	/**
	 *  A list of all the current Handlers.
	 */
	List<Handler> handlers;
	
	/**
	 *  A list of all the obstacles in the game.
	 */
	List<GameObject> obstacles;
	
	/** List of all line of sight object */
	List<LineOfSightBlock> lineOfSightBlocks;
	
	/**
	 *  Camera for game.
	 */
	Camera cam;
	
	/** TileManager */
	TileManager tm;
	
	/**
	 * Instantiates a new object handler().
	 *
	 * @param cam the cam
	 * @param tm - the current TileManager
	 */
	public ObjectHandler(Camera cam,TileManager tm) {
		this.tm = tm;
		this.cam = cam;
		init();
	}
	
	@Override
	public void init() {
		obstacles = tm.obstacles;
		tilemaps = tm.tm;
		lineOfSightBlocks = tm.lineOfSightBlocks;
		handlers = new ArrayList<Handler>();
	}
	
	/**
	 * Method that adds Handler objects to a list .
	 *
	 * @param handler the handler
	 */
	public void addHandler(Handler handler) {
		handlers.add(handler);
	}
	

	@Override
	public void update(float elapsedTime) {

		//update each object in the game only if object is in camera
		//might want to update so that updates are continuous ????
		for (int i = 0; i < handlers.size();i++) {
			List<GameObject> list = handlers.get(i).getList();
			for (int j = 0; j < list.size();j++) {
				if (list.get(j).id == ID.Player)
					{list.get(j).update(elapsedTime);
					//cam.getBox().updateBoundingBox(list.get(j).getKinematic().getPosition());
					
					}
				else if (CollisionDetection.testOverLap(cam,list.get(j)))
					list.get(j).update(elapsedTime);
			}
		}
		//if no tile map exist exit 
		if (tilemaps == null)return;
		for (int i = 0; i < tilemaps.size();i++) {
			Block[] blocks = tilemaps.get(i).getBlocks();
			for (int j = 0;j < blocks.length;j++) {
				if (blocks[j] != null) {
				if (CollisionDetection.testOverLap(cam, blocks[j]))
					blocks[j].update(elapsedTime);
				}
			}
		}
		cam.update(elapsedTime);
	}


	@Override
	public void render(Graphics2D g, ImageObserver observer,float x, float y) {
		//create bigger camera so that extra data is rendered to screen so no black areas 
		//around play area
		Camera bigCam = new Camera(new Vector2(cam.getBox().getMin().getX()  - 10,
				cam.getBox().getMin().getY() - 10),cam.getUnitWidth() +20,
				cam.getUnitHeight() + 20,ID.CAMERA);
		bigCam.getBox().setCentreOffset(new Vector2(bigCam.getBox().getWidth()/2,bigCam.getBox().getHeight()/2));
		
		//Render first tiles to screen so characters are in front of the tiles
		for (int i = 0; i < tilemaps.size();i++) {
			if (tilemaps.get(i).getID() != ID.HIDE) {
				Block[] blocks = tilemaps.get(i).getBlocks();
				for (int j = 0;j < blocks.length;j++) {
					if (blocks[j] != null) {
					if (CollisionDetection.testOverLap(bigCam, blocks[j]))
						blocks[j].render(g, observer,cam.getPosX(),cam.getPosY());
					}
				}
			}
		}
		

		//render the characters second
		List<GameObject> players = new ArrayList<GameObject>();
		for (int i = 0; i < handlers.size();i++) {
			List<GameObject> list = handlers.get(i).getList();
			for (int j = 0; j < list.size();j++) {
				if (CollisionDetection.testOverLap(cam,list.get(j))) {
					if (list.get(j).getID() == ID.Player) {
						players.add(list.get(j));
						continue;
					}
					list.get(j).render(g, observer,cam.getPosX(),cam.getPosY());
				}
			}
		}
		//render player last so he hides the objects throughout level
		for (GameObject o : players)
			o.render(g, observer, cam.getPosX(), cam.getPosY());
		
		//render any tiles that will hide the characters 
		for (int i = 0; i < tilemaps.size();i++) {
			if (tilemaps.get(i).getID() == ID.HIDE) {
			Block[] blocks = tilemaps.get(i).getBlocks();
			for (int j = 0;j < blocks.length;j++) {
				if (blocks[j] != null)
				if (CollisionDetection.testOverLap(bigCam, blocks[j]))
					blocks[j].render(g, observer,cam.getPosX(),cam.getPosY());
			}
			}
		}
		//render any obstacles that will be in front of all other tiles 
		for (int i = 0; i < obstacles.size();i++) {
			obstacles.get(i).render(g, observer, cam.getPosX(),cam.getPosY());
		}
		for (int i = 0; i < lineOfSightBlocks.size();i++) {
			lineOfSightBlocks.get(i).render(g, observer, cam.getPosX(), cam.getPosY());
		}
		
		//render cam last for testing purposes 
		
		if (Testing.viewMousePositions) {
			System.out.println(MouseHandler.getX() + "," + MouseHandler.getY());
		}
		cam.render(g, observer, 0, 0);
		
	}


	
}
