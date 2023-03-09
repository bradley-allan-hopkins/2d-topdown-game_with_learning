/**
 * Title: Camera.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 20, 2022
 */
package gameObjects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

import ai.steeringbehaviors.SteeringOutput;
import game.Game;
import gameObjects.characters.Entity;
import math.Vector2;
import tiles.TileManager;

/**
 * The Class Camera.
 */
public class Camera extends GameObject{
	
	/**
	 * The Enum Movement.
	 */
	enum Movement{
		
		/** The go. */
		GO,
		/** The stop. */
		STOP;
	}
	
	/** The horizontal. */
	Movement vertical,horizontal;
	
	/** The left. */
	boolean left = false;
	
	/** The right. */
	boolean right = false;
	
	/** The up. */
	boolean up = false;
	
	/** The down. */
	boolean down = false;
	
	/** The x. */
	float x = 0;
	
	/** The y. */
	float y = 0;
	
	/** Entity that the camera will follow if one exists*/
	Entity entity;
	
	TileManager tm;

	/**
	 * Camera.
	 * @param position - center position (x,y)
	 * @param unitWidth the unit width
	 * @param unitHeight the unit height
	 * @param id the id
	 */
	public Camera(Vector2 position, float unitWidth, float unitHeight,ID id) {
		super(position, unitWidth, unitHeight,2, id);
	}
	
	/**
	 * Camera.
	 * @param position - center position (x,y)
	 * @param unitWidth the unit width
	 * @param unitHeight the unit height
	 * @param entity - entity that the camera will follow
	 * @param tm - the tileManager that the camera is viewing
	 */
	public Camera(Vector2 position, float unitWidth, float unitHeight, Entity entity, 
			TileManager tm) {
		this(position,unitWidth,unitHeight,ID.CAMERA);
		this.entity = entity;
		this.tm = tm;
	}

	
	@Override
	public void init() {/*no initialize*/}
	
	
	/**
	 * Key pressed.
	 * @param e KeyEvent
	 */
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
			//p1 inputs
			case KeyEvent.VK_UP:
				vertical = Movement.GO;
				up = true;
				break;
			case KeyEvent.VK_DOWN:
				vertical = Movement.GO;
				down = true;
				break;
			case KeyEvent.VK_RIGHT:
				horizontal = Movement.GO;
			right = true;
				break;
			case KeyEvent.VK_LEFT:
				horizontal = Movement.GO;
				left = true;
				break;
			default :
				break;
		}
	}

	
	 /**
 	 * Key released.
 	 *
 	 * @param e the e
 	 */
 	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
			//p1 inputs
			case KeyEvent.VK_UP:
				vertical = Movement.STOP;
				up = false;
				break;
			case KeyEvent.VK_DOWN:
				vertical = Movement.STOP;
				down = false;
				break;
				//p2 inputs
			case KeyEvent.VK_LEFT:
				horizontal = Movement.STOP;
				left = false;
				break;
			case KeyEvent.VK_RIGHT:
				horizontal = Movement.STOP;
				right = false;
				break;
			default :
				break;
		}
	}
	
	@Override
	public void update(float elapsedTime) {
		

		moveCamera();
		kinematic.setVelocity(new Vector2(x,y));
		checkMapBounds();
		kinematic.setPosition(new Vector2(getPosX() + kinematic.getVelocity().getX(),
				getPosY() + kinematic.getVelocity().getY()));
		
		box.updateBoundingBox(getPosition());
		
	}
	
	/**
	 * Check current velocity and see if velocity + position puts camera out of bounds of map
	 */
	private void checkMapBounds() {
		//camera must not go beyond left bound x = 0
		if (getPosX() + getVelX() < 0) {
			kinematic.setVelocity(new Vector2( 0 ,getVelY()));
		}
		//camera must not go beyond up bound y = 0
		if (getPosY() + getVelY() < 0) {
			kinematic.setVelocity(new Vector2(getVelX() , 0));
		}
		//camera must not go beyond right bound x + box width = tileMap width
		if (getPosX() + box.getWidth() + getVelX() > tm.getMapWidth() * Game.SCALER) {
			kinematic.setVelocity(new Vector2(0, getVelY()));
		}
		//camera must not go beyond down bound y + box height = tileMap height
		if (getPosY() + box.getHeight() + getVelY() >tm.getMapHeight() * Game.SCALER) {
			kinematic.setVelocity(new Vector2(getVelX(), 0));
		}
	}
	
	/**
	 * Move camera.
	 *
	 */
	private void moveCamera() {
		if (entity == null) {
			//redundant coding but due to lag in keyboard keyReleased it is necessary
			if(vertical == Movement.STOP && up == false && down == false) {
				kinematic.getVelocity().setY(0);
				y = 0;
			}
			if (horizontal == Movement.STOP && left == false && right == false) {
				kinematic.getVelocity().setX(0);
				x = 0;
			}
			
			
			if (horizontal == Movement.GO) {
				if (right) {
					//only move camera when player is past half way marks 
					//if (CharacterFactory.player.getKinematic().getPosition().getX() > 
					//	kinematic.getPosition().getX() + box.getWidth()/2)
						x = speed;
				}
				else if (left) {
					//only move camera when player is past half way marks 
				//	if (CharacterFactory.player.getKinematic().getPosition().getX() < 
					//	kinematic.getPosition().getX() + box.getWidth()/2)
					x = -speed;
				}
			}
			if (vertical == Movement.GO) {
				if (down) {
					//only move camera when player is past half way marks 
					//if (CharacterFactory.player.getKinematic().getPosition().getY() > 
					//	kinematic.getPosition().getY() + box.getHeight()/2)
						y = speed;
				}
				else if (up) {
					//only move camera when player is past half way marks 
					//if (CharacterFactory.player.getKinematic().getPosition().getY() < 
					//	kinematic.getPosition().getY() + box.getHeight()/2)
						y = -speed;
				}
			}
		}
		else {
			
			//check against entity's velocity X
			if (entity.getVelX() == 0) {
				resetX();
			}
			//if velocity is positive and entity is right of halfway mark, move screen right
			else if (entity.getVelX() > 0){	
				if (entity.getPosX() > getPosX() + box.getWidth()/2)
					x = entity.getVelX();
				else resetX();
			}
			//if velocity is negative and entity is left of halfway, mark move screen left
			else if (entity.getVelX() < 0) {
				if (entity.getPosX() < getPosX() + box.getWidth()/2)
					x = entity.getVelX();
				else resetX();
			}
			
			//check against entity's velocity Y
			if (entity.getVelY() == 0) 
				resetY();
			//if velocity is positive and entity is below halfway mark, move screen down
			else if (entity.getVelY() > 0){	
				if (entity.getPosY() > getPosY() + box.getHeight()/2) 
					y = entity.getVelY();
				else resetY();
			}
			//if velocity is negative and entity is above halfway, mark move screen up
			else if (entity.getVelY() < 0) {
				if (entity.getPosY() < getPosY() + box.getHeight()/2)
					y = entity.getVelY();
				else resetY();
			}
		}
	}
	
	private void resetX() {
		x = 0;
		kinematic.setVelocity(new Vector2(0,getVelY()));
	}
	
	private void resetY() {
		y = 0;
		kinematic.setVelocity(new Vector2(getVelX(),0));
	}


	@Override
	public void render(Graphics2D g, ImageObserver observer,float x, float y) {
		if (Testing.viewBoundingBoxes) {
			box.viewBoundingBox(g, Color.GREEN,x,y);
		}
	}

	@Override
	public void movement(float elapsedTime, SteeringOutput output) {
		// TODO Auto-generated method stub
		
	}
}
