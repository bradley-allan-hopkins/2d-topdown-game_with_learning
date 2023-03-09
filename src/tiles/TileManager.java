/**
 * Title: TileManager.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 11, 2022
 *  Class skeleton used from com.zerulus.game
 * EDIT:
 * Mar 8 = added getObjects() and moved contents from addTileMap() to getObjects
 */
package tiles;

import java.awt.Graphics2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import ai.steeringbehaviors.pathfinding.Connection;
import ai.steeringbehaviors.pathfinding.Graph;
import ai.steeringbehaviors.pathfinding.PathNode;
import ai.steeringbehaviors.pathfinding.Graph.GraphHolder;
import game.Game;
import gameObjects.GameObject;
import gameObjects.ID;
import gameObjects.collisions.CollisionResolution;
import gameObjects.collisions.shapes.Area;
import gameObjects.collisions.shapes.InvisibleObject;
import gameObjects.collisions.shapes.Polygon;
import gameObjects.collisions.shapes.Rectangle;
import gameObjects.events.Event;
import math.Vector2;
import state.states.PlayState;
import tiles.blocks.Block;
import tiles.blocks.LineOfSightBlock;
import tiles.blocks.TerrainBlock;
import tiles.locations.LocationPoint;
import utils.ThreeTuple;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * The Class TileManager.
 */
public class TileManager {

	/** List of TileMaps */
	public ArrayList<TileMap> tm;
	/** List of graph tiles */
	public TileMap graphTiles;
	/** List of tile map locations that random GameObjects are able to generate on */
	public TileMap randomTiles;
	/** List of GameObjects that are obstacles*/
	public ArrayList<GameObject> obstacles;
	/** array of the TileSets used in game*/
	public Tileset[] allTilesets;
	/** array of all locations in game */
	public ArrayList<LocationPoint> locations;
	/** list of all Line of sight objects - these block line of sight*/
	public ArrayList<LineOfSightBlock> lineOfSightBlocks;
	
	/** list of areas in level*/
	public ArrayList<GameObject> areas;
	
	/** the graph for the level currently loaded*/
	public Graph graph;

	/** The block width. */
	private int blockWidth;

	/** The block height. */
	private int blockHeight;
	
	/** The map Width */
	private int mapWidth;
	
	/** The Map Height */
	private int mapHeight;


	/**
	 * Tile manager.
	 * @param path the path
	 * @param scaler - the GameScaler used to upsize all components
	 */
	public TileManager(String path, int scaler) {
		tm = new ArrayList<TileMap>();
		graph = new Graph();
		obstacles = new ArrayList<GameObject>();
		areas = new ArrayList<GameObject>();
		locations = new ArrayList<LocationPoint>();
		lineOfSightBlocks = new ArrayList<LineOfSightBlock>();
		addTileMap(path,scaler);
	}

	/**
	 * Adds the tile map.
	 *
	 * @param path the path
	 * @param blockWidth the block width
	 * @param blockHeight the block height
	 */
	private void addTileMap(String path,int scaler) {

		//up to 10 layers 
		String[] data = new String[10];

		try {
			//create items to read XML
			String fileName = "src/resources/" + path;
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document doc = builder.parse(new File(fileName));
			doc.getDocumentElement().normalize();

			//grab tile width and height for map
			NodeList list = doc.getElementsByTagName("map");
			Node node = list.item(0);
			Element eElement = (Element) node;
			
			blockWidth = Integer.parseInt(eElement.getAttribute("tilewidth"));
			blockHeight = Integer.parseInt(eElement.getAttribute("tileheight"));
			
			//look at elements in tileset and source
			list = doc.getElementsByTagName("tileset");
			node = list.item(0);
			eElement = (Element) node;
			
			NodeList imagelist = doc.getElementsByTagName("image");
			Node imagenode = imagelist.item(0);
			Element iElement = (Element) imagenode;
			
			int tilesets = list.getLength();
			
			//create array of Tileset that hold information for each tileset
			allTilesets = new Tileset[tilesets];
			for (int i = 0; i < tilesets; i++) {
				node = list.item(i);
				eElement = (Element) node;
				
				//grab information about each tile set
				String name = eElement.getAttribute("name");
				int tileWidth = Integer.parseInt(eElement.getAttribute("tilewidth"));
				int tileHeight = Integer.parseInt(eElement.getAttribute("tileheight"));
				int columns = Integer.parseInt(eElement.getAttribute("columns"));
				int gridStart = Integer.parseInt(eElement.getAttribute("firstgid"));
				int tileCount = Integer.parseInt(eElement.getAttribute("tilecount"));
				
				imagenode = imagelist.item(i);
				iElement = (Element) imagenode;
				
				//grab source information about each tile set
				String sourceFileName = iElement.getAttribute("source").substring(2);
				int sourceWidth = Integer.parseInt(iElement.getAttribute("width"));
				int sourceHeight = Integer.parseInt(iElement.getAttribute("height"));
				allTilesets[i] = new Tileset(name, gridStart, tileWidth, tileHeight, tileCount,
						columns, sourceFileName, sourceWidth,sourceHeight);	
			}
			
			//next look at elements in "layer"
			list = doc.getElementsByTagName("layer");
			int layers = list.getLength();
			
			//each layer will be stored separately in tm
			for(int i = 0; i < layers; i++) {
				node = list.item(i);
				eElement = (Element) node;
				
				//map total width and total height 
				mapWidth = Integer.parseInt(eElement.getAttribute("width"));
				mapHeight = Integer.parseInt(eElement.getAttribute("height"));
				//layer name used to change ID
				String layerName = eElement.getAttribute("name");

				data[i] = eElement.getElementsByTagName("data").item(0).getTextContent();

				//if layer is named obstacles create collision blocks 
				if(layerName.equalsIgnoreCase("obstacles")) {
					tm.add(new TileMapNormal(data[i],allTilesets, mapWidth, mapHeight,ID.CollisionBlock,scaler));
				} 
				//different named layers will have different ID used 
				else if(layerName.equalsIgnoreCase("Hide")) {
					tm.add(new TileMapNormal(data[i], allTilesets, mapWidth, mapHeight, ID.HIDE,scaler));
				} 
				else if(layerName.equalsIgnoreCase("Ground")) {
					tm.add(new TileMapNormal(data[i], allTilesets, mapWidth, mapHeight, ID.GROUND,scaler));
				} 
				else if(layerName.equalsIgnoreCase("Graph")) {
					graphTiles = new GraphTileMap(data[i], allTilesets, mapWidth, mapHeight, ID.NormalBlock,scaler);
				} 
				else if (layerName.equalsIgnoreCase("Random")) {
					randomTiles = new GraphTileMap(data[i], allTilesets, mapWidth, mapHeight, ID.NormalBlock,scaler);
				}
				else if (layerName.equalsIgnoreCase("FirstLayer")) {
					tm.add(new TileMapFirst(data[i], allTilesets, mapWidth, mapHeight,ID.NormalBlock,scaler));
				}
				//else create normal blocks that are not tangible 
				else {
					tm.add(new TileMapNormal(data[i], allTilesets, mapWidth, mapHeight,ID.NormalBlock,scaler));
				}
			}
			//search XML for object layer
			getObjects(doc,scaler);
			buildGraph();
			
			
		} catch(Exception e) {
			System.out.println("ERROR - TILEMANAGER: can not read tilemap:");
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	/**
	 * Method that searches XML document for objects 
	 * @param doc - document 
	 */
	private void getObjects(Document doc, int scaler) {
		//now find objects. Separate into object groups and then grab each object 
		NodeList list = doc.getElementsByTagName("objectgroup");
		int objects = list.getLength();

		//each object group will create different objects based on name
		for(int i = 0; i < objects; i++) {
			Node node = list.item(i);
			Element eElement = (Element) node;
			String name = eElement.getAttribute("name");
			
			NodeList objectList = eElement.getElementsByTagName("object");
			System.out.println("Length: " + objectList.getLength());
			for (int j = 0 ; j < objectList.getLength();j++) {
				Node objectNode = objectList.item(j);
				Element oElement = (Element) objectNode;
				
				if (name.equalsIgnoreCase("Rectangles")){
					float objectWidth = Float.parseFloat(oElement.getAttribute("width"));
					float objectHeight = Float.parseFloat(oElement.getAttribute("height"));
					float recX = Float.parseFloat(oElement.getAttribute("x"));
					float recY = Float.parseFloat(oElement.getAttribute("y"));
			
					Rectangle rectangle = new Rectangle(null,new Vector2(
							(recX + objectWidth/2)* scaler , //x
					(recY + objectHeight/2)* scaler ),//y
					objectWidth * scaler , objectHeight * scaler,0, ID.CollisionBlock);
					
					System.out.println("Rectangle:" + j + " created");
					obstacles.add(new InvisibleObject(rectangle.getPosition(),
							rectangle.getUnitWidth(),rectangle.getUnitHeight(),rectangle));
				}
				else if (name.equalsIgnoreCase("Event")){
					float objectWidth = Float.parseFloat(oElement.getAttribute("width"));
					float objectHeight = Float.parseFloat(oElement.getAttribute("height"));
					float recX = Float.parseFloat(oElement.getAttribute("x"));
					float recY = Float.parseFloat(oElement.getAttribute("y"));
			
					PlayState.events.add(new Event(new Vector2(
							(recX + objectWidth/2)* scaler , //x
					(recY + objectHeight/2)* scaler ),//y
					objectWidth * scaler , objectHeight * scaler, ID.CollisionBlock,"Start"));
				}
				else if (name.equalsIgnoreCase("Locations")){
					float locX = Float.parseFloat(oElement.getAttribute("x"));
					float locY = Float.parseFloat(oElement.getAttribute("y"));
					String locName = oElement.getAttribute("name");
			
					locations.add(new LocationPoint(locName,new Vector2(locX * scaler,
							locY * scaler)));
					
				}
				else if (name.equalsIgnoreCase("LineOfSight")){
					float objectWidth = Float.parseFloat(oElement.getAttribute("width"));
					float objectHeight = Float.parseFloat(oElement.getAttribute("height"));
					float recX = Float.parseFloat(oElement.getAttribute("x"));
					float recY = Float.parseFloat(oElement.getAttribute("y"));
			
					//casting to int might lose some accuracy 
					LineOfSightBlock block = new LineOfSightBlock(
							(int)((recX) * scaler)  , //x
							(int)((recY) * scaler),//y
							(int)objectWidth * scaler ,
							(int) objectHeight * scaler);
					lineOfSightBlocks.add(block);
				}
				else if (name.equalsIgnoreCase("Areas")){
					float objectWidth = Float.parseFloat(oElement.getAttribute("width"));
					float objectHeight = Float.parseFloat(oElement.getAttribute("height"));
					float recX = Float.parseFloat(oElement.getAttribute("x"));
					float recY = Float.parseFloat(oElement.getAttribute("y"));
					String recName = oElement.getAttribute("name");
			
					//create rectangle object with no collision 
					Area area = new Area(null,new Vector2(
							(recX + objectWidth/2)* scaler , //x
					(recY + objectHeight/2)* scaler ),//y
					objectWidth * scaler , objectHeight * scaler,0, null,recName) {
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
					};
					areas.add(area);
				}
				else if (name.equalsIgnoreCase("Polygons")) {
					float polyX = Float.parseFloat(oElement.getAttribute("x"));
					float polyY = Float.parseFloat(oElement.getAttribute("y"));
					
					NodeList polyList = eElement.getElementsByTagName("polygon");
					Node polyNode = polyList.item(j);
					Element pElement = (Element) polyNode;
					String pData = pElement.getAttribute("points");
					String[] dataSplit = pData.split(" ");
					List<Vector2> points = new ArrayList<Vector2>();
					for (String s:dataSplit) {
						//not separate each point into x and y
						String[] coordinates = s.split(",");
						float x = Float.parseFloat(coordinates[0]) + polyX;
						float y = Float.parseFloat(coordinates[1]) + polyY;
						points.add(new Vector2(x,y).scaler(scaler) );
					}
					ThreeTuple<Float, Float, Vector2> information = 
							Polygon.findSize(points);
					//System.out.println(information.a * scaler + "," +  information.b * scaler + ",Position:" + information.c.scaler(scaler));
					System.out.println(information.a+ "," +  information.b  + ",Position:" + information.c);
					Polygon poly = new Polygon(null,information.c,
							information.a, information.b, 0,ID.POLYGON,points);
					obstacles.add(new InvisibleObject(poly.getPosition(),
							poly.getUnitWidth(),poly.getUnitHeight(),poly));
				}
			}
		}
	}
	
	/**
	 * Gets the variable "ObstacleBlocks".
	 *
	 * @return tile map - ObstacleBlocks
	 */
	public TileMap getObstacleBlocks() {
		for (TileMap tilemap : tm)
			if (tilemap.getID() == ID.CollisionBlock) {
				return tilemap;
			}
		return null;
	}
	
	/**
	 * method that returns a tileMap if tilemap has proper ID
	 * @param id - ID of wanted TileMap
	 * @return the TileMap with matching ID
	 */
	public TileMap getTileMap(ID id) {
		for (TileMap tilemap : tm) {
			if (tilemap.getID() == id) {
				return tilemap;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * Method that gets all blocks from a tileMap  
	 * @param i - the TileMap location
	 * @return an array of blocks in that TileMap
	 */
	public Block[] getBlocks(int i) {
		return tm.get(i).getBlocks();
	}
	
	/**
	 * Method that tests if TM exists with obstacles 
	 * @return true is obstacles exist 
	 */
	public boolean isObstaclesBlocksNotNull() {
		for (TileMap tilemap : tm)
			if (tilemap.getID() == ID.CollisionBlock) {
				return true;
			}
		return false;
	}
	
	/**
	 * Gets the variable "BlockHeight".
	 * @return int - BlockHeight
	 */
	public int getTileHeight() { return blockHeight; }

	/**
	 * Gets the variable "BlockWidth".
	 * @return int - BlockWidth
	 */
	public int getTileWidth() { return blockWidth; }

	


	/**
	 * Render.
	 *
	 * @param g the graphics object for the window 
	 * @param observer - the window observer
	 * @param x - the amount to skew the x 
	 * @param y - the amount to skey the y
	 */
	public void render(Graphics2D g,ImageObserver observer,float x, float y) {
		for(int i = 0; i < tm.size(); i++) {
			tm.get(i).render(g,observer, x, y);
		}
		graph.render(g, observer, x, y);
	}

	/**
	 * Public method used to get the total width
	 * @return the mapWidth
	 */
	public int getMapWidth() {
		return mapWidth * Game.GRID_TILE_SIZE;
	}
	
	/**
	 * public method used to get mapWidth
	 * @return the mapWidth
	 */
	
	public int getWidth() {
		return mapWidth;
	}
	
	/**
	 * public method used to get mapHeight
	 * @return the map height
	 */
	public int getHeight() {
		return mapHeight;
	}
	
	/**
	 * Method that is used to calculate the total height of the map after 
	 * @return the Height of the map
	 */
	public int getMapHeight() {
		return mapHeight * Game.GRID_TILE_SIZE;
	}

	/**
	 * Public method used to set
	 * @param mapWidth the mapWidth to set
	 */
	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}
	
	/**
	 * This method rebuilds the graph with new nodes and connections. This method is called
	 * when the terrain is changed as different terrain has different costs or no costs at all
	 */
	public void buildGraph() {
		if (graphTiles == null)return;
		Block[] terrainBlocks = graphTiles.getBlocks();
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
		//System.out.println("Array Size:" + (mapWidth * mapHeight) + " BLOCK SIZE:" + terrainBlocks.length);
		GraphHolder[] holder = new GraphHolder[mapWidth * mapHeight];
		
		int counter = 0;
		int offset = 0;
		
		//go through each terrainBlock to create the connections
		// * * *
		// * x *
		// * * *
		for (int i = 0; i < terrainBlocks.length;i++) {
			if (terrainBlocks[i] != null) {
				Connection[] tempCon = new Connection[maxCon];
				Block block = terrainBlocks[i];
				//left of block
				if ((block.getPosX() - gridSize) > 0) {
					offset = -1;
					if (terrainBlocks[i + offset] != null) {
						if (!(((TerrainBlock)terrainBlocks[i + offset]).getCost() == 0))
							tempCon[counter++] = new Connection((
									(TerrainBlock)terrainBlocks[i + offset]).getCost(),
									tempNodes[i],tempNodes[i + offset]);
					}
					//left - up
					if (block.getPosY() - gridSize > 0) {
						offset = -(mapWidth + 1);
						//offset = -17;
						if (terrainBlocks[i + offset] != null) {
							if (!(((TerrainBlock)terrainBlocks[i + offset]).getCost() == 0))
								tempCon[counter++] = new Connection(
									((TerrainBlock)terrainBlocks[i + offset]).getCost(),
									tempNodes[i],tempNodes[i + offset]);
						}
					}
					//left - down
					if (block.getPosY() + gridSize < getMapHeight() * Game.SCALER) {
						offset = mapWidth -1;
						//offset = 15;
						if (terrainBlocks[i + offset] != null) {
							if (!(((TerrainBlock)terrainBlocks[i + offset]).getCost() == 0))
								tempCon[counter++] = new Connection((
									(TerrainBlock)terrainBlocks[i + offset]).getCost(),
									tempNodes[i],tempNodes[i + offset]);
						}
					}
						
				}
				//right of block
				if (block.getPosX() + gridSize < getMapWidth() * Game.SCALER) {
					offset = 1;
					if (terrainBlocks[i + offset] != null)
					tempCon[counter++] = new Connection((
							(TerrainBlock)terrainBlocks[i + offset]).getCost(),
							tempNodes[i],tempNodes[i + offset]);
					//right -up
					if (block.getPosY() - gridSize > 0) {
						offset= -(mapWidth - 1);
						//offset = -15;
						if (terrainBlocks[i + offset] != null)
						tempCon[counter++] = new Connection(
								((TerrainBlock)terrainBlocks[i + offset]).getCost(),
								tempNodes[i],tempNodes[i + offset]);
					}
					//right - down 
					if (block.getPosY() + gridSize < getMapHeight() * Game.SCALER) {
						offset = mapWidth + 1;
						//offset = 17;
						if (terrainBlocks[i + offset] != null)
						tempCon[counter++] = new Connection(
								((TerrainBlock)terrainBlocks[i + offset]).getCost(),
								tempNodes[i],tempNodes[i + offset]);
					}
				}
				//up 
				if (block.getPosY() - gridSize > 0) {
					offset = -mapWidth;
					//offset = -16;
					if (terrainBlocks[i + offset] != null)
					tempCon[counter++] = new Connection(
							((TerrainBlock)terrainBlocks[i + offset]).getCost(),
							tempNodes[i],tempNodes[i + offset]);
				}
				// down
				if (block.getPosY() + gridSize < getMapHeight() * Game.SCALER) {
					offset = mapWidth;
					//offset = 16;
					if (terrainBlocks[i + offset] != null)
					tempCon[counter++] = new Connection(
							((TerrainBlock)terrainBlocks[i + offset]).getCost(),
							tempNodes[i],tempNodes[i + offset]);
				}
				
				//shrink connections down to actual size to avoid random nulls 
				int conCount = 0;
				Connection[] connections = new Connection[counter];
				for (int j = 0; j < tempCon.length; j++) {
					if (tempCon[j] != null) {
						connections[conCount++] = tempCon[j];
					}
				}
				holder[i] = graph.new GraphHolder(tempNodes[i],connections);
				//holderCount++;
			}
			graph.setGraphHolder(holder);
			counter = 0;
		}//end for loop
	}

	
}
