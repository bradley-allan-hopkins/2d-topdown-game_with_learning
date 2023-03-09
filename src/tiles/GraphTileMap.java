/**
 * Title: GraphTileMap.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 18, 2022
 */
package tiles;

import gameObjects.ID;
import tiles.blocks.Block;
import tiles.blocks.TerrainBlock;

/**
 * The Class GraphTileMap.
 */
public class GraphTileMap extends TileMap {

	/**
	 * Graph tile map.
	 *
	 * @param data the data
	 * @param allTilesets the all tilesets
	 * @param width the width
	 * @param height the height
	 * @param id the id
	 * @param scaler the scaler
	 */
	public GraphTileMap(String data, Tileset[] allTilesets, int width,
			int height, ID id, int scaler) {
		this.id = id;
		
		//create temp array 
		blocks = new Block[width* height];
		//fill block array with each element from xml 
		String[] block = data.split(",");

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
					blocks[i] = new TerrainBlock(
								i % width * tileset.tileWidth * scaler, //x
								i / height * tileset.tileHeight * scaler,//y
								tileset.tileWidth * scaler, //width
								tileset.tileHeight * scaler, //height
								id,temp -1, 1);
					
				}
			}
		}
		System.out.println("FINISHED LAYER Graph, Size:" + blocks.length);
	}


}
