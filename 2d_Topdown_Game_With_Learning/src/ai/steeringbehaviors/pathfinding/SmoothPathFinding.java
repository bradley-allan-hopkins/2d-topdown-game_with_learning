/**
 * Title: SmoothPathFinding.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 21, 2022
 */
package ai.steeringbehaviors.pathfinding;


import game.Game;
import gameObjects.GameObject;
import gameObjects.collisions.shapes.Rectangle;
import math.Vector2;
import tiles.PositionToGrid;
import tiles.TileManager;
/**
 * The Class SmoothPathFinding. This class smoothes out the travel of character from the original
 * travel points given from the pathfinding object
 */
public class SmoothPathFinding {
	
	/** The graph being checked against*/
	Graph graph;
	
	/** The tilemanager holding tile size*/
	TileManager tm;
	
	/** used for the character sizing */
	GameObject character;
	
	/**
	 * Smooth path finding.
	 * @param graph the graph
	 * @param tm - the current tileManager in use 
	 * @param character - the character that is traveling 
	 */
	public SmoothPathFinding(Graph graph,TileManager tm,GameObject character) {
		this.graph = graph;
		this.character = character;
		this.tm = tm;
	}

	/**
	 * Smooth path.
	 *
	 * @param inputPath the input path
	 * @return vector 2 []
	 */
	public Vector2[] smoothPath(Vector2[] inputPath) {

		//if the path is only two nodes, we can not smooth
		if (inputPath.length == 2) return inputPath;
		
		//compile an output path
		Vector2[] outputPath = new Vector2[1];
		outputPath[0] = inputPath[0] ;
				
		//keep track of where we are in the input path. We start at 2, because we assume
		// two adjacent nodes will pass the ray cast
		int inputIndex = 2;
		
		//loop until we find the last item in the input 
		while (inputIndex < inputPath.length - 1) {
			//do the ray cast 
			Vector2 fromPt = outputPath[outputPath.length - 1];
			Vector2 toPt = inputPath[inputIndex];
			if (!rayClear(fromPt, toPt)){
				
				//the ray cast failed, add the last node that passed to the output list 
				outputPath = Vector2.addToVectorArray(outputPath, inputPath[inputIndex -1]);
			}
			//consider next node 
			inputIndex++;
		}
		
		//we have reached the end of the input path, add the end node to the output 
		outputPath = Vector2.addToVectorArray(outputPath, inputPath[inputPath.length - 1]);
		
		return outputPath;
	}
	
	/**
	 *this method checks if the ray from fromPt to toPt intersects any null locations
	 *
	 * @param fromPt The point that the ray starts at
	 * @param toPt The point that the ray is traveling to
	 * @return true, if successful
	 */
	public boolean rayClear(Vector2 fromPt, Vector2 toPt) {
		//center ray
		Vector2 ray = toPt.subtractTwoVectors(fromPt);

		int tileSize = tm.getTileWidth() * Game.SCALER;
		//half the tile size
		tileSize /= 2;
		
		int increment = (int) Math.max(Math.abs(ray.getX()/tileSize), Math.abs(ray.getY()/tileSize));
		
		//start the increment at 1/increment - 1/1 will equal the toPt
		int i = 1;
		Vector2 newPoint = null;
		do {
			//move along the ray at increments to check is tile location intersected is null
			float scaler =(float)i/increment;
			newPoint = fromPt.addTwoVectors(ray.scaler(scaler));
			
			//first check center position
			if (graph.allNodes[PositionToGrid.getGridLocation(newPoint)] == null) return false;
			
			//create 4 offshoots to check if point is close to null graph location 
			//this will take the character width as a reference 
			Rectangle shape = new Rectangle(null,newPoint,character.getUnitWidth(),
					character.getUnitHeight(),0,null);
			//fill corners array with the 4 corners of the characters bounding box 
			for (int j = 0;j<4;j++) {
				if (graph.allNodes[PositionToGrid.getGridLocation(
						shape.getBoundingBox().getPoint(j))] == null)return false;
			}
			i++;
			
		}while ((newPoint.getX() != toPt.getX()) && (newPoint.getY() != toPt.getY()));

		//if no grid locations were null ray was successful
		return true;
	}
	
}
