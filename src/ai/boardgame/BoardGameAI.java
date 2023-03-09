/**
 * Title: BoardGameAI.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 6, 2022
 * derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 */
package ai.boardgame;

import utils.TwoTuple;
/**
 * The Class BoardGameAI. This class lets the computer player look at a 'x' number of moves 
 * again to guess which current move would be best 
 */
public class BoardGameAI {
	
	/** The recurse. */
	int recurse = 0;
	
	/**
	 * Minimax.
	 *
	 * @param board the board
	 * @param player the player
	 * @param maxDepth the max depth
	 * @param currentDepth the current depth
	 * @return two tuple
	 */
	public TwoTuple<Float,Move> minimax(Board board,GamePiece player, int maxDepth, 
			int currentDepth){
		//check if we're done recursing
		if (board.isGameOver() || currentDepth == maxDepth) {
			return new TwoTuple<Float,Move>(board.evaluate(player),null);
		}
		
		//otherwise bubble up values from below
		Move bestMove = null;
		float bestScore;
		if (board.currentPlayer() == player) {
			bestScore = Float.NEGATIVE_INFINITY;
		}
		else 
			bestScore = Float.POSITIVE_INFINITY;

		for (int i = 0; i < board.getMoves().size();i++){
			//create new board
			Move move = new Move(board.getMoves().get(i).getPosition());
			Board newBoard = board.makeClone();
			newBoard.makeMove(move);
			
			//recurse
			TwoTuple<Float,Move> recursed = minimax(newBoard,player, maxDepth, currentDepth + 1);
			float currentScore = recursed.a;
			
			//update the best score 
			if (board.currentPlayer() == player) {
				if (currentScore > bestScore) {
					bestScore = currentScore;
					bestMove = move;
				}
			}
			else {
				if (currentScore < bestScore) {
					bestScore = currentScore;
					bestMove = move;
				}
			}
		}
		return new TwoTuple<Float,Move>(bestScore, bestMove);
	}
	
	/**
	 * Ab nega max.
	 *
	 * @param board the board
	 * @param maxDepth the max depth
	 * @param currentDepth the current depth
	 * @param alpha the alpha
	 * @param beta the beta
	 * @return two tuple
	 */
	public TwoTuple<Float,Move> abNegaMax(Board board, int maxDepth, int currentDepth, float alpha, 
			float beta){
		//check if we're done recursing
		if (board.isGameOver() || currentDepth == maxDepth) {
			return new TwoTuple<Float,Move>(board.evaluate(GamePiece.Black),null);
		}
		
		//otherwise bubble up values from below
		Move bestMove = null;
		float bestScore = Float.NEGATIVE_INFINITY;
		
		//go througn each move

		for (int i = 0; i < board.getMoves().size();i++){
			//create new board
			Move move = new Move(board.getMoves().get(i).getPosition());
			Board newBoard = board.makeClone();
			newBoard.makeMove(move);
			
			//recurse
			TwoTuple<Float,Move> recursed = abNegaMax(newBoard, maxDepth, currentDepth + 1,
					-beta, -Math.max(alpha, bestScore));
			float currentScore = -recursed.a;
			
			//update the best score 
			if (currentScore > bestScore) {
				bestScore = currentScore;
				bestMove = move;
			}
			
			//if we're outside the bounds, prune by exiting immediately 
			if (bestScore >= beta) {
				break;
			}
		}

		return new TwoTuple<Float,Move>(bestScore, bestMove);
	}
}
