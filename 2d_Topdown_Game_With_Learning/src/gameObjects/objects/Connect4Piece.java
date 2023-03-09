/**
 * Title: Connect4Piece.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 4, 2022
 */
package gameObjects.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import ai.boardgame.GamePiece;
import ai.steeringbehaviors.SteeringOutput;
import gameObjects.ID;
import gameObjects.Testing;
import gameObjects.collisions.shapes.Rectangle;
import math.Vector2;

/**
 * The Class Connect4Piece. This class is the Connect 4 piece that will be in the game 
 */
public class Connect4Piece extends Rectangle{
	

	/** The color. */
	private GamePiece color;

	/**
	 * Connect 4 piece.
	 *
	 * @param position the position
	 * @param width the width
	 * @param height the height
	 * @param speed the speed
	 * @param id the id
	 * @param color the color
	 */
	public Connect4Piece(Vector2 position, float width,
			float height, float speed, ID id,GamePiece color) {
		super(null, position, width, height, speed, id);
		this.color = color;
		//allow pieces to be placed next to eachOther
		box.setWidth(unitWidth - 2);
	}
	

	@Override
	public void update(float elapsedTime) {
		movement(elapsedTime);
	}
	
	/**
	 * Movement.
	 *
	 * @param elapsedTime the elapsed time
	 */
	public void movement(float elapsedTime) {
		SteeringOutput output = new SteeringOutput();
		output.setLinear(new Vector2(0,2));
		kinematic.update(output, speed, elapsedTime);
		//System.out.println(kinematic.getVelocity());
		//System.out.println(kinematic.getPosition());
		//System.out.println(getPosX() + "," + getPosY());
		
		box.updateBoundingBox(kinematic.getPosition());
	}
	

	@Override
	public void render(Graphics2D g, ImageObserver observer, float x, float y) {
		//set color based on color variable 
		if (color == GamePiece.White) {
			g.setColor(Color.WHITE);
		}
		else g.setColor(Color.BLACK);
		
		g.fillOval((int)(getPosX() - unitWidth/2 - x), (int) 
				(getPosY() - unitHeight/2 - y), (int)unitWidth, (int)unitHeight);
		
		//for testing show bounding box
		if (Testing.viewBoundingBoxes) {
			box.viewBoundingBox(g, Color.GREEN,x,y);
		}
	}

	
}
