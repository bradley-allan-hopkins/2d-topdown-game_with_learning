/**
 * Title: Board.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 6, 2022 new
 */
package ai.boardgame;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import game.Game;
import gameObjects.Testing;
import math.Vector2;
// TODO: Auto-generated Javadoc
/**
 * The Class Board. This is a connect 4 board 
 */
public class Board {
	
	
	/** rows */
	int rows;
	
	/** columns */
	int columns;
	
	
	/** Top left Position*/
	Vector2 topLeft;
	
	/** the board grid */
	int[][] board;
	
	/** possible moves*/
	ArrayList<Move> moves;
	
	/** current Color */
	GamePiece color;
	
	/** winning pieces */
	Vector2[] line = new Vector2[2];
	
	/**
	 * Instantiates a new board().
	 * @param topLeft - the position of the topleft corner of the board
	 * @param rows - how many rows the board has 
	 * @param columns - how many columns the board has 
	 */
	public Board(Vector2 topLeft, int rows, int columns) {
		this.topLeft = topLeft;
		this.rows = rows;
		this.columns = columns;
		board = new int[columns][rows];
		
		//fill the board 2d array with -1 values 
		for (int column = 0; column < board.length ;column++) {
			for (int row = 0 ; row < board[0].length ; row++) {
				board[column][row] = -1; 	
			}
		}
		
		//set first move to be players 
		color = GamePiece.White;
		setFirstMoves();
	}
	
	/**
	 * Sets the first moves. The first moves is all the moves on the bottom of the board
	 */
	public void setFirstMoves() {
		
		moves = new ArrayList<Move>();
		//set y to the last row
		int y = (int) (topLeft.getY() + (Game.TILE_SIZE * (rows - 1)));
		//start x at the 1st column
		int x = (int) topLeft.getX();
		
		//create the moves for the bottom of the board 
		for (int i = 0; i < columns; i++) {
			moves.add(new Move(new Vector2(x + (Game.TILE_SIZE * i),y)));
		}
	}
	

	/**
	 * get Current Player
	 * @return GamePiece - which is the color of the player white:player black:computer
	 */
	public GamePiece currentPlayer() {
		return color;
	}
	
	/**
	 * Gets the variable "Moves".
	 *
	 * @return move[] - Moves
	 */
	public ArrayList<Move> getMoves() {
		return moves;
	}
	
	/**
	 * Make move.
	 *
	 * @param move the move
	 * @return board
	 */
	public Board makeMove(Move move) {
		//System.out.println("MOVE:" + move.getPosition());
		
		//first check if move matches an available move and then remove it from the 
		for (int i = 0; i < moves.size();i++) {
			if (moves.get(i).equals(move)) {
				moves.remove(i);
				addPieceToGrid(move);
				//switch who's turn is next 
				if (color == GamePiece.White)
					color = GamePiece.Black;
				else color = GamePiece.White;
				
				break;
			}
		}
		//return a clone of the current board 
		return makeClone();
	}

	
	/**
	 * Checks if is game over.
	 *
	 * @return true, if is game over
	 */
	public boolean isGameOver() {
		int winning = won();
		//-1 represents no winner
		if (winning != -1)return true;
		return false;
	}
	

	/**
	 * 
	 * Method that returns a score based on the current position of all pieces on the board
	 * if player is winning return 10, if opposite player is winning return -10. If tied return
	 * 1 and return 0 if nothing 
	 * @param player - player checking the current evaluation
	 * @return float - the score 
	 */
	public float evaluate(GamePiece player) {

		if (isTied())return 1;
		int won = won();
		if (won != -1) {
			if (won == player.ordinal())return 10;
			return -10;
		}
		return 0;
	}
	
	/**
	 * 
	 * Method that renders the moves, and the bounding boxes. This is used for visual testing
	 * @param g - graphics 
	 * @param observer - window observer 
	 * @param x - x offset from camera
	 * @param y - y offset from camera
	 */
	public void render(Graphics2D g, ImageObserver observer, float x, float y) {
		for (int i = 0; i < moves.size(); i++) {
			moves.get(i).render(g, observer, x, y);
			//System.out.println(moves.get(i).getPosition());
		}
		if (Testing.viewBoundingBoxes) {
			g.setColor(Color.RED);
			for (int column = 0; column < board.length ;column++) {
				for (int row = 0 ; row < board[0].length ; row++) {
					g.drawString("" + board[column][row], column * Game.TILE_SIZE + 128 + 32,
								row * Game.TILE_SIZE + 128 + 32);
				}
			}
		}
	}
	
	/**
	 * Render win pieces. This method renders a line over the winning pieces 
	 *
	 * @param g the g
	 * @param observer the observer
	 */
	public void renderWinPieces(Graphics2D g, ImageObserver observer) {
		if (line[0] != null) {
			g.setColor(Color.ORANGE);
			g.drawLine((int)line[0].getX(), (int)line[0].getY(), 
					(int)line[1].getX(), (int)line[1].getY());
		}
		
	}
	
	/**
	 * Column full. This method checks if the current column is full so that no new 
	 * pieces can continue falling on full columns 
	 *
	 * @param position the position
	 * @return true, if successful
	 */
	public boolean columnFull(Vector2 position) {
		int xMinus = (int) (topLeft.getX() / (Game.TILE_SIZE));
		int column = (int) (position.getX()/(Game.TILE_SIZE) - xMinus); 
		if (board[column][0] != -1)
			return true;
		return false;
	}

	/**
	 * Adds the piece to grid. This method adds the fallen pieces to the grid and add a new 
	 * move above the new piece. 
	 *
	 * @param move the move
	 */
	public void addPieceToGrid(Move move) {
		//add piece to grid 
		//minus left and top of board game as board game does not start at (0,0)
		int xMinus = (int) (topLeft.getX() / (Game.TILE_SIZE));
		int yMinus = (int) (topLeft.getY() / (Game.TILE_SIZE));
		int column = (int) (move.getPosition().getX()/(Game.TILE_SIZE) - xMinus);
		int row = (int) (move.getPosition().getY()/(Game.TILE_SIZE) - yMinus);
		board[column][row] = color.ordinal();
		
		//create a new move above piece 
		if (row -1 >= 0 && board[column][row -1] == -1) {
			//add move above piece 
			moves.add(new Move(new Vector2(column * Game.TILE_SIZE + topLeft.getX(),
					row * Game.TILE_SIZE + topLeft.getY() - Game.TILE_SIZE)));
		}
		
	}
	
	
	/**
	 * Make clone. This method creates a new board clone of the current board.
	 *
	 * @return board
	 */
	public Board makeClone() {
		
		//create new board
		Board newBoard = new Board(topLeft,rows,columns);
		//copy board grid
		for (int column = 0; column < board.length ;column++) {
			for (int row = 0 ; row < board[0].length ; row++) {
				newBoard.board[column][row] = board[column][row];
			}
		}
		//copy available moves 
		newBoard.moves = new ArrayList<Move>();//reset moves
		for (int i = 0; i < moves.size(); i++) {
			newBoard.moves.add(new Move(new Vector2(moves.get(i).getPosition().getX(),
					moves.get(i).getPosition().getY())));
		}
		newBoard.color = color;
					
		return newBoard;
		
	}
	
	/**
	 * Checks if is tied. If any grid locations still contain -1 the game is not a tie
	 *
	 * @return true, if is tied
	 */
	public boolean isTied() {
		for (int column = 0; column < board.length ;column++) {
			for (int row = 0 ; row < board[0].length ; row++) {
				if (board[column][row] == -1) return false;
			}
		}
		return true;
	}
	
	/**
	 * Checks if is game over.
	 *
	 * @return true, if is game over
	 */
	public int won() {
		int win = 4;
		for (int column = 0; column < board.length ;column++) {
			for (int row = 0 ; row < board[0].length ; row++) {
				//check for horizontal wins 
				if (board[column][row] != -1) {
					//the current piece 
					int piece = board[column][row];
					//System.out.println(piece);
					int inARow = 1;
					for (int k = 1 ; k < win ; k++) {
						if (column + k <= columns -1) {
							//if the next piece is not the same break from horizontal checks
							if (board[column + k][row] == -1 || board[column + k][row] != piece)
								break;
							//else 
							inARow++;
							if (inARow == win) {
								line[0] = getPosition(column,row);
								line[1] = getPosition(column + k,row);
								return piece;
							}
						}
					}
					//check vertical
					inARow = 1;
					for (int k = 1 ; k < win ; k++) {
						if (row - k >= 0) {
							//if the next piece is not the same break from vertical checks
							if (board[column][row -k] == -1 || board[column][row - k] != piece)
								break;
							//else 
							inARow++;
							if (inARow == win) {
								line[0] = getPosition(column,row);
								line[1] = getPosition(column,row -k);
								return piece;
							}
						}
					}
					//check right diagonal
					inARow = 1;
					for (int k = 1 ; k < win ; k++) {
						if (column + k <= columns -1 && row - k >= 0) {
							//if the next piece is not the same break from vertical checks
							if (board[column + k][row -k] == -1 || 
									board[column + k][row - k] != piece)
								break;
							//else 
							inARow++;
							if (inARow == win) {
								line[0] = getPosition(column,row);
								line[1] = getPosition(column + k,row -k);
								return piece;
							}
						}
					}
					//check left diagonal
					inARow = 1;
					for (int k = 1 ; k < win ; k++) {
						if (column - k >= 0 && row - k >= 0) {
							//if the next piece is not the same break from vertical checks
							if (board[column - k][row -k] == -1 || 
									board[column - k][row - k] != piece)
								break;
							//else 
							inARow++;
							if (inARow == win) {
								line[0] = getPosition(column,row);
								line[1] = getPosition(column - k,row -k);
								return piece;
							}
						}
					}
				}
			}
		}
		return -1;
	}
	
	/**
	 * Checks if is game over.
	 *
	 * @return true, if is game over
	 */
	public int getPoints() {
		int points = 0;
		int win = 4;
		for (int column = 0; column < board.length ;column++) {
			for (int row = 0 ; row < board[0].length ; row++) {
				//check for horizontal 
				if (board[column][row] != -1) {
					//the current piece 
					int piece = board[column][row];
					//only point for the current color
					if (piece != color.ordinal())continue;
					//System.out.println(color);
					//System.out.println(piece);
					int inARow = 1;
					for (int k = 1 ; k < win ; k++) {
						if (column + k <= columns -1) {
							//if the next piece is not the same break from horizontal checks
							if (board[column + k][row] == piece)
								inARow++;
							//if (inARow == win) return piece;
						}
					}
					//if two in a row
					if (inARow == 2) points += 2;
					if (inARow == 3) points += 4;
					if (inARow == 4) points += 10;
				}
			}
		}
		return points;
	}
	
	/**
	 * Gets the variable "Position".
	 *
	 * @param column the column
	 * @param row the row
	 * @return vector 2 - Position
	 */
	public Vector2 getPosition(int column, int row) {
		return new Vector2(column * Game.TILE_SIZE + topLeft.getX() + Game.TILE_SIZE/2,
				row * Game.TILE_SIZE + topLeft.getY() + Game.TILE_SIZE/2);
	}
	
}
