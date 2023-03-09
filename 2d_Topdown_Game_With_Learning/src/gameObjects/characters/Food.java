/**
 * Title: Food.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 19, 2022
 */
package gameObjects.characters;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

import gameObjects.GameObject;
import gameObjects.collisions.CollisionResolution;
import gameObjects.collisions.shapes.Rectangle;
import gameObjects.collisions.shapes.Shape;
import graphics.BufferedImageLoader;
import graphics.SpriteSheet;
import math.Vector2;
import utils.ThreeTuple;

/**
 * The Class Food.
 */
public class Food extends Rectangle {
	
	/**
	 * Food.
	 *
	 * @param position the position
	 * @param width the width
	 * @param height the height
	 */
	public Food(Vector2 position, float width, float height) {
		super(null, position, width, height, 0, null);
		init();
	}

	/** The food. */
	BufferedImage food;

	@Override
	public void update(float elapsedTime) {/* Not used*/}

	@Override
	public void render(Graphics2D g, ImageObserver observer,float x,float y) {
		g.drawImage(food,(int) (kinematic.getPosition().getX() - 24 - x), 
				(int) (kinematic.getPosition().getY() - 24 - y),48,48,observer);
	}


	@Override
	public void init() {
		getImages();
	}
	
	/**
	 * attempt to grab images for sprite.
	 */
	public void getImages() {
		try {
			SpriteSheet spriteSheet = new SpriteSheet(BufferedImageLoader.loadImage(
					"/images/food.png"));
			food = spriteSheet.grabImage(1, 1, (int)getUnitWidth(), (int)getUnitHeight());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Override
	public Shape[] getShapes() {
		Shape[] shapes = new Shape[1];
		shapes[0] = this;
		return shapes;
	}
	
	@Override
	public CollisionResolution getCollisionResolution() {
		return new CollisionResolution() {
			@Override
			public void resolveCollision(ThreeTuple<Vector2, Vector2, Float> tuple, GameObject obj) {
				//no collision
				return;
			}
		};
	}

}
