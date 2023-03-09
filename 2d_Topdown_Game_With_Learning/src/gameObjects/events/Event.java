/**
 * Title: Event.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 8, 2022
 */
package gameObjects.events;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import ai.steeringbehaviors.SteeringOutput;
import gameObjects.GameObject;
import gameObjects.ID;
import math.Vector2;

/**
 * The Class Event.
 */
public class Event extends GameObject{
	
	/** The name. */
	String name;

	/**
	 * Event.
	 *
	 * @param position the position
	 * @param unitWidth the unit width
	 * @param unitHeight the unit height
	 * @param id the id
	 * @param name the name
	 */
	public Event(Vector2 position, float unitWidth, float unitHeight,ID id, String name) {
		super(position, unitWidth,unitHeight,0, id);
		this.name = name;
	}
	
	/**
	 * Gets the variable "Name".
	 * @return string - Name
	 */
	public String getName() {
		return name;
	}


	@Override
	public void update(float elapsedTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics2D g, ImageObserver observer, float x, float y) {
		//box.viewBoundingBox(g, null, x, y);
	}


	@Override
	public void init() {/* Not used*/}

	@Override
	public void movement(float elapsedTime, SteeringOutput output) {
		// TODO Auto-generated method stub
		
	}

}
