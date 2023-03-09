/**
 * Title: TravelToTarget.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 16, 2022
 */
package ai.statemachine.actions;

import java.util.List;

import ai.steeringbehaviors.Behavior;
import ai.steeringbehaviors.pathfinding.Connection;
import ai.steeringbehaviors.pathfinding.Heuristic;
import ai.steeringbehaviors.pathfinding.PathFinding;
import ai.steeringbehaviors.pathfinding.SmoothPathFinding;
import ai.steeringbehaviors.pathfinding.Graph.GraphHolder;
import gameObjects.GameObject;
import gameObjects.collisions.shapes.InvisibleObject;
import gameObjects.collisions.shapes.Rectangle;
import math.Vector2;
import state.states.PlayState;
import tiles.PositionToGrid;
import tiles.TileManager;

/**
 * The Class TravelToTarget. This action creates a path from a start object to another object
 */
public class TravelToTarget extends SeekObject {


	/** Destination Reached*/
	boolean reached = false;
	
	/** The path traveled. */
	List<Vector2> pathTraveled;
	
	/** The Path traveled */
	public Vector2[] travelPath;
	
	/** Use smooth path finding*/
	boolean smoothpath = false;
	
	/** The path num. */
	int pathNum = 0;
	
	/** If the character get to end of path */
	boolean finished = false;
	
	/** radius to next Vector2 position in path*/
	float radius;
	
	/**
	 * Travel to target.
	 *
	 * @param character the character
	 * @param target the target
	 * @param seek -Attributes need to includes: maxAcceleration, maxPrediction,threshold
	 * decayCoeffienct, collisionRadius, lookAhead, avoidDistance, maxRotation
	 * @param radius - radius to target
	 * @param smoothpath - if path should be smoothed 
	 * @param targets - list of targets to avoid 
	 * @param speed - this sets the characters speed
	 * @param tm - the current Tilemanager
	 */
	public TravelToTarget(GameObject character, GameObject target, Behavior seek,
			float radius, boolean smoothpath,List<GameObject> targets, float speed,
			TileManager tm) {
		super(character, target, seek, targets,speed,tm);
		this.smoothpath = smoothpath;
		this.radius = radius;
	}
	
	@Override
	public void performAction(float elapsedTime) {

		if (travelPath == null){//convert ant position into array position
			//create path using characters position and the target position
			pathNum = 0;
			reached = false;
			createPath(PositionToGrid.getGridLocation(character), 
					PositionToGrid.getGridLocation(target));
			//if travel path is still null exit method
			if (travelPath == null)return;
			setTarget(travelPath[pathNum]);
		}
		//check if character is at next path point
		if (character.getPosition().subtractTwoVectors(travelPath[pathNum]).getLength() <= radius) {
			
			//if character has reached the end
			if (pathNum == (travelPath.length - 1)) {
				reached = true;
				PlayState.tm.graph.pathTraveled.remove(travelPath);
				travelPath = null;
				PlayState.tm.graph.resetNodes();
				
			}
			else {
			setTarget(travelPath[++pathNum]);
			}
		}
		//delgate to seek behavior
		super.performAction(elapsedTime);
	}
	

	
	/**
	 * Creates the path.
	 *
	 * @param begin the begin
	 * @param end the end
	 */
	public void createPath(int begin, int end) {
		PathFinding path = new PathFinding();
		if (PlayState.tm.graph.getGraphHolder() != null) {
			//get the current graph for the level
			GraphHolder[] holder = PlayState.tm.graph.getGraphHolder();
			
			//if the location is out of bounds exit 
			if (begin < 0 || begin > holder.length -1 || end < 0 || end > holder.length -1) return;
			
			//if the location points to a null location in the graph grid exit
			if (holder[begin] == null || holder[end] == null) return;
				
			Connection[] connections = path.pathFindAStar(PlayState.tm.graph,
					holder[begin].a,holder[end].a, new Heuristic(holder[end].a));
			
			//if no connections are returned exit 
			if (connections == null || connections.length == 0) {
				//pathTraveled = null;
				return;
			}
			
			Vector2[] travel = new Vector2[0];
			travel = Vector2.addToVectorArray(travel,connections[0].getFromNode().getPosition());
			for (int i = 0;i < connections.length;i++) {
				travel = Vector2.addToVectorArray(travel, connections[i].getToNode().getPosition());
			}
			if (smoothpath) {
				SmoothPathFinding smooth = new SmoothPathFinding(PlayState.tm.graph,
						PlayState.tm,new Rectangle(null,new Vector2(0,0),40f,40f,0,null));
				travelPath = smooth.smoothPath(travel);
			}
			else 
				travelPath = travel;
			
			//for testing add travel path to graph list 
			PlayState.tm.graph.pathTraveled.add(travelPath);
		}
	}
	
	/**
	 * Sets the target.
	 *
	 * @param target the new target
	 */
	public void setTarget(Vector2 target) {
		GameObject gameObj = new InvisibleObject(target,16,16,null);
		this.target = gameObj;
	}
	
	/**
	 * return true if destination is reached 
	 * @return true if reached
	 */
	public boolean isReached() {
		return reached;
	}
	
	/**
	 * reset reached
	 */
	public void resetReached() {
		reached = false;
	}
	
}
