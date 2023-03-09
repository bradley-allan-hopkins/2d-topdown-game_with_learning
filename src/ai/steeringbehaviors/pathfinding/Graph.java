/**
 * Title: Graph.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 4, 2022
 * *derived from psuedo code in "AI for Games third Addition" By Ian MillingTon
 * @revised 
 * Mar 21,2022 - added buildGraph method
 * Mar 21,2022, - added if (Testing.viewGraphNodes) and if (Testing.ViewCompleteGraph)
 * Mar 21,2022 - added pathTraveled - called by TravelTo actions??????
 */
package ai.steeringbehaviors.pathfinding;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

import game.Game;
import gameObjects.ID;
import gameObjects.Testing;
import math.Vector2;
import tiles.TileManager;
import tiles.blocks.Block;
import tiles.blocks.TerrainBlock;
import utils.TwoTuple;

/**
 * The Class Graph. This class stores the connections for each node 
 */
public class Graph {
	
	/** The all nodes. */
	GraphHolder[] allNodes;
	
	/** The filled. */
	int filled = 0;

	/** The path traveled by many objects */
	public List<Vector2[]> pathTraveled;
	
	/**
	 * Instantiates a new graph().
	 */
	public Graph() {
		pathTraveled = new ArrayList<Vector2[]>();
	}
	/**
	 * Graph.
	 * @param size the size
	 */
	public Graph(int size) {
		this();
		allNodes = new GraphHolder[size];
	}
	
	/**
	 * Gets the variable "GraphHolder".
	 *
	 * @return graph holder[] - GraphHolder
	 */
	public GraphHolder[] getGraphHolder() {
		return allNodes;
	}
	
	/**
	 * Method that adds a Node and its connections to the array  .
	 *
	 * @param node the node
	 * @param connections the connections
	 */
	public void addNode(PathNode node, Connection[] connections) {
		if (filled < allNodes.length)
			allNodes[filled++] = new GraphHolder(node,connections);
	}
	
	/**
	 * class that extends a generic TwoTuple allowing a array of GraphHolders.
	 */
	public class GraphHolder extends TwoTuple<PathNode,Connection[]>{

		/**
		 * Graph holder.
		 *
		 * @param a the a
		 * @param b the b
		 */
		public GraphHolder(PathNode a, Connection[] b) {
			super(a, b);
		}	
	}
	
	/**
	 * Method that returns an of array of connections outgoing from the current Node .
	 *
	 * @param fromNode - check which connections are connected to fromNode
	 * @return Connection - return the array of connections
	 */
	public Connection[] getConnections(PathNode fromNode) {
		int number = fromNode.getNum();

		if (number < allNodes.length) {
			return allNodes[number].b;
		}
		return null;
	}
	
	/**
	 * Render.
	 *
	 * @param g the g
	 * @param observer the observer
	 * @param x the x
	 * @param y the y
	 */
	public void render(Graphics2D g,ImageObserver observer,float x, float y) {
		if (allNodes != null) {
			for (int i = 0; i < allNodes.length;i++) {
				if (allNodes[i] != null) {
					if (Testing.viewGraphNodes) {
						if (allNodes[i].a.open) {
							g.setColor(Color.YELLOW);
							g.drawRect((int)allNodes[i].a.getPosition().getX() - 10,
									(int)allNodes[i].a.getPosition().getY() - 10, 20, 20);
						}
						
						if (allNodes[i].a.closed) {
							int x1 = (int)allNodes[i].a.getPosition().getX();
							int y1 = (int)allNodes[i].a.getPosition().getY();
							g.setColor(Color.YELLOW);
							int lineSize = 10;
							g.drawLine(x1, y1, x1 - lineSize, y1 - lineSize);
							g.drawLine(x1, y1, x1 - lineSize, y1 + lineSize);
							g.drawLine(x1, y1, x1 + lineSize, y1 - lineSize);
							g.drawLine(x1, y1, x1 + lineSize, y1 + lineSize);
							
							g.drawRect((int)allNodes[i].a.getPosition().getX() - 10,
									(int)allNodes[i].a.getPosition().getY() - 10, 20, 20);
						}
					}
					
					if (Testing.viewCompleteGraph) {
						for (int j = 0; j < allNodes[i].b.length;j++) {
							int x1 = (int)allNodes[i].a.getPosition().getX();
							int y1 = (int)allNodes[i].a.getPosition().getY();
							int x2 = (int)allNodes[i].b[j].getToNode().getPosition().getX();
							int y2 = (int)allNodes[i].b[j].getToNode().getPosition().getY();
							
							g.setColor(Color.WHITE);
							g.drawLine(x1, y1, x2, y2);
						}
					}
				}
			}
		}
		if (pathTraveled != null) {
			if (Testing.viewPathFinding) {
				for (Vector2[] travelPath : pathTraveled) {
					for (int i = 0; i < travelPath.length -1;i++) {
						g.setColor(Color.GREEN);
						g.drawOval((int) travelPath[i].getX()-5, 
								(int)travelPath[i].getY()-5,10,10);
						g.drawLine((int) travelPath[i].getX(), 
								(int)travelPath[i].getY(),
								(int) travelPath[i + 1].getX(),
								(int)travelPath[i + 1].getY());
					}
					g.drawOval((int) travelPath[travelPath.length -1].getX()-5, 
							(int)travelPath[travelPath.length -1].getY()-5,10,10);
				}
			}
		}
	}
	
	/**
	 * method to reset all nodes to false.
	 */
	public void resetNodes() {
		if (allNodes != null) {
			for (int i = 0; i < allNodes.length;i++) {
				if (allNodes[i] != null) {
					allNodes[i].a.checked = false;
					allNodes[i].a.closed = false;
					allNodes[i].a.open = false;
				}
			}
		}
	}
	
	/**
	 * Set the graphHolder.
	 *
	 * @param holder the new graph holder
	 */
	public void setGraphHolder(GraphHolder[] holder) {
		this.allNodes = holder;
	}
	
	/**
	 * This method rebuilds the graph with new nodes and connections. This method is called
	 * when the terrain is changed as different terrain has different costs or no costs at all
	 *
	 * @param tilemanager the tilemanager
	 * @return Graph
	 */
	public static Graph buildGraph(TileManager tilemanager) {
		Graph graph = new Graph();

		Block[] terrainBlocks = tilemanager.getTileMap(ID.EDIT_LAYER).getBlocks();
		//max connection size for tile grid
		int maxCon = 8;
		int gridSize = Game.GRID_TILE_SIZE * Game.SCALER;
		
		PathNode[] tempNodes = new PathNode[terrainBlocks.length];
		
		//first create all the PathNodes in Map
		for (int i = 0; i < terrainBlocks.length;i++) {
			if (terrainBlocks[i] != null) {
				tempNodes[i] = new PathNode(terrainBlocks[i].getPosition(),i);
			}
		}
		GraphHolder[] holder = new GraphHolder[tilemanager.getWidth() * tilemanager.getHeight()];
		
		int offset = 0;
		
		//go through each terrainBlock to create the connections
		// 1 6 4
		// 0 x 3
		// 2 7 5
		for (int i = 0; i < terrainBlocks.length;i++) {
			if (terrainBlocks[i] != null) {
				Connection[] tempCon = new Connection[maxCon];
				Block block = terrainBlocks[i];
				
				//left of block
				//check if left is out of bounds
				if ((block.getPosX() - gridSize) > 0) {
					//the position in array added to current 'i' position
					offset = -1;
					//if graph location is null don't add connection to it
					buildConnection(tempCon,0,i,terrainBlocks,offset,tempNodes);

					//left - up 
					//check to make sure in bounds 
					if (block.getPosY() - gridSize > 0) {
						//the position in array added to current 'i' position
						offset = -(tilemanager.getTileWidth() + 1);
						buildConnection(tempCon,1,i,terrainBlocks,offset,tempNodes);
					}
					//left - down
					//check to make sure in bounds 
					if (block.getPosY() + gridSize < tilemanager.getMapHeight() * Game.SCALER) {
						//the position in array added to current 'i' position
						offset = tilemanager.getTileWidth() -1;
						buildConnection(tempCon,2,i,terrainBlocks,offset,tempNodes);
					}		
				}
				
				//right of block
				//check to make sure in bounds 
				if (block.getPosX() + gridSize < tilemanager.getMapWidth() * Game.SCALER) {
					//the position in array added to current 'i' position
					offset = 1;
					buildConnection(tempCon,3,i,terrainBlocks,offset,tempNodes);
					//right -up
					//check to make sure in bounds 
					if (block.getPosY() - gridSize > 0) {
						//the position in array added to current 'i' position
						offset = -(tilemanager.getTileWidth() -1);
						buildConnection(tempCon,4,i,terrainBlocks,offset,tempNodes);
					}
					//right - down 
					//check to make sure in bounds 
					if (block.getPosY() + gridSize < tilemanager.getMapHeight() * Game.SCALER) {
						//the position in array added to current 'i' position
						offset = tilemanager.getTileWidth() + 1;
						buildConnection(tempCon,5,i,terrainBlocks,offset,tempNodes);
					}
				}
				//up 
				//check to make sure in bounds 
				if (block.getPosY() - gridSize > 0) {
					//the position in array added to current 'i' position
					offset = -tilemanager.getTileWidth();
					buildConnection(tempCon,6,i,terrainBlocks,offset,tempNodes);
				}
				// down
				//check to make sure in bounds 
				if (block.getPosY() + gridSize < tilemanager.getMapHeight() * Game.SCALER) {
					//the position in array added to current 'i' position
					offset = tilemanager.getTileWidth();
					buildConnection(tempCon,7,i,terrainBlocks,offset,tempNodes);
				}
				/*do not use diagonal connections if orthogonal connections are null
				* this will make the character look more realistic not trying
				* to go through corners
				*/
				if (tempCon[0] == null || tempCon[6] == null) {
					tempCon[1] = null;
				}
				if (tempCon[0] == null || tempCon[7] == null) {
					tempCon[2] = null;
				}
				if (tempCon[6] == null || tempCon[3] == null) {
					tempCon[4] = null;
				}
				if (tempCon[3] == null || tempCon[7] == null) {
					tempCon[5] = null;
				}
				// count how many connections exist
				int connectionCounter = 0;
				for (Connection c : tempCon) {
					if (c != null)connectionCounter++;
				}
				//create new array based on exact size
				int conCount = 0;
				Connection[] connections = new Connection[connectionCounter];
				for (int j = 0; j < tempCon.length; j++) {
					if (tempCon[j] != null) {
						connections[conCount++] = tempCon[j];
					}
				}
				holder[i] = graph.new GraphHolder(tempNodes[i],connections);
			}
		}//end for loop
		graph.setGraphHolder(holder);
		return graph;
	}
	
	/**
	 * Builds the connection.
	 *
	 * @param tempCon the temp con
	 * @param conNum the con num
	 * @param i the i
	 * @param terrainBlocks the terrain blocks
	 * @param offset the offset
	 * @param tempNodes the temp nodes
	 */
	private static void buildConnection(Connection[] tempCon,int conNum, int i, Block[] terrainBlocks,
			int offset,PathNode[] tempNodes) {
		if (terrainBlocks[i + offset] != null)
		tempCon[conNum] = new Connection(
				((TerrainBlock)terrainBlocks[i + offset]).getCost(),
				tempNodes[i],tempNodes[i + offset]);
	}
}
