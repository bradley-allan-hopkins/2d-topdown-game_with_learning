/**
 * Title: TileMap.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Feb. 11, 2022
 *  Class skeleton used from com.zerulus.game
 */
package tiles;

import gameObjects.ID;
import tiles.blocks.Block;
import tiles.blocks.CollisionBlock;
import tiles.blocks.NormalBlock;

/**
 * The Class TileMap.
 */
public class TileMapNormal extends TileMap{

	/**
	 * Tile map.
	 *
	 * @param data - the numbers representing each tile in each tileset 
	 * @param allTilesets - contains all information for each tile set in XML
	 * @param width the width of the map
	 * @param height the height of the map
	 * @param id the id
	 * @param scaler - used to scale up all pixel art
	 */
	public TileMapNormal(String data, Tileset[] allTilesets, int width, int height, ID id, int scaler) {

		this.id = id;
		
		//create temp array 
		Block tempBlocks[] = new Block[width* height];
		
		//fill block array with each element from xml 
		String[] block = data.split(",");
		
		//variable to size the final array. It could be smaller than temp array as not all 
		//layer locations need to be filled
		int size = 0;

		//go though each block item 
		for(int i = 0; i < block.length; i++) {
			//Compensate for 0
			int temp = Integer.parseInt(block[i].replaceAll("\\s+","")) ;
			//0 represents nothing so no block is created
			Tileset tileset = null;
			
			if(temp != 0) {
				
				for (int k = 0;k < allTilesets.length; k++) {
					if (temp >= allTilesets[k].gridStart && temp <= allTilesets[k].gridEnd) {
						tileset = allTilesets[k];
						break;
					}
				}
				if (tileset != null) {
					temp -= (tileset.gridStart - 1);
					//fill tempblocks array
					if (id == ID.CollisionBlock) {
						tempBlocks[i] = new CollisionBlock((tileset.spriteSheet.grabImageTile((temp - 1) % 
								tileset.columns, (temp - 1) / tileset.columns, tileset.tileWidth, 
										tileset.tileHeight)) ,//image
								i % width * tileset.tileWidth * scaler, //x
								i / height * tileset.tileHeight * scaler,//y
								tileset.tileWidth * scaler, //width
								tileset.tileHeight * scaler, //height
								id);
					}
					else {
					tempBlocks[i] = new NormalBlock((tileset.spriteSheet.grabImageTile((temp - 1) % 
								tileset.columns, (temp - 1) / tileset.columns, tileset.tileWidth, 
										tileset.tileHeight)) ,//image
								i % width * tileset.tileWidth * scaler, //x
								i / height * tileset.tileHeight * scaler,//y
								tileset.tileWidth * scaler, //width
								tileset.tileHeight * scaler, //height
								id);
						size++;
					}
				}
			}
		}
		//create final array based on how many blocks were actually created
		blocks = new Block[size];
		int counter = 0;
		//go through each item in tempblocks, if item is null skip 
		for (int k = 0 ; k < tempBlocks.length; k++){
			if (tempBlocks[k] != null) {
				blocks[counter++] = tempBlocks[k];
			}
		}
		System.out.println("FINISHED NORMAL LAYER");
	}
	
	/**
	 * constructor creating empty block holder
	 * @param size - size to create the array 
	 * @param id - id of the tileMap
	 */
	public TileMapNormal(int size,ID id) {
		blocks = new Block[size];
		this.id = id;
	}
	
	
	
	
	/** 
	 * Method that update blocks
	 * @param elapsedTime - time for frame update
	 */
	public void update(float elapsedTime) {
		for (Object block : blocks)
			if (block != null) 
				((Block) block).update(elapsedTime);
	}



}
