/**
 * Title: Move.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 6, 2022
 */
package ai.boardgame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import game.Game;
import gameObjects.Testing;

import math.Vector2;
/**
 * The Class Move. This class holds the position of where the piece should move too
 */
public class Move {

	/** The position. */
	private Vector2 position;
	
	/** The player */
	GamePiece color;
	


	/**
	 * Move.
	 *
	 * @param position the position
	 */
	public Move(Vector2 position) {
		this.position = position;
		//make default computer player color
		color = GamePiece.Black;
	}
	
	/**
	 * Move.
	 *
	 * @param position the position
	 * @param color the color
	 */
	public Move(Vector2 position,GamePiece color) {
		this(position);
		this.color = color;
		
	}
	
	/**
	 * Public method used to get
	 * @return the position
	 */
	public Vector2 getPosition() {
		return position;
	}

	/**
	 * Public method used to set
	 * @param position the position to set
	 */
	public void setPosition(Vector2 position) {
		this.position = position;
	}
	
	/**
	 * 
	 * Method for testing 
	 * @param g - graphics 
	 * @param observer - screen observer
	 * @param x - camera offset
	 * @param y - camera offset 
	 */
	public void render(Graphics2D g, ImageObserver observer, float x, float y) {
		
		if (Testing.viewBoundingBoxes) {
			g.setColor(Color.RED);
			
			int size = 10;
			g.fillOval((int)(position.getX() + Game.GRID_TILE_SIZE - size/2), (int) 
					(position.getY() + Game.GRID_TILE_SIZE - size/2), size, size);
		}
	}
	
	@Override 
	public boolean equals(Object o) {
		Move move = (Move)o;
		return Float.compare(position.getX(),move.position.getX()) == 0 
				&& Float.compare(position.getY(), move.position.getY()) == 0;
	}
	
	/**
	 * Public method used to get
	 * @return the color
	 */
	public GamePiece getColor() {
		return color;
	}

	/**
	 * Public method used to set
	 * @param color the color to set
	 */
	public void setColor(GamePiece color) {
		this.color = color;
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}


}
