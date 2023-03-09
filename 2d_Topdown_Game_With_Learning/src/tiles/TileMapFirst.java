/**
 * Title: TileMapFirst.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Apr. 11, 2022
 */
package tiles;

import gameObjects.ID;
import tiles.blocks.Block;
import tiles.blocks.NormalBlock;

/**
 * The Class TileMapFirst. The first layer will contain null areas if areas are holes in game
 */
public class TileMapFirst extends TileMap {

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
		public TileMapFirst(String data, Tileset[] allTilesets, int width, int height, ID id, int scaler) {

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
						blocks[i] = new NormalBlock((tileset.spriteSheet.grabImageTile((temp - 1) % 
									tileset.columns, (temp - 1) / tileset.columns, tileset.tileWidth, 
											tileset.tileHeight)) ,//image
									i % width * tileset.tileWidth * scaler, //x
									i / height * tileset.tileHeight * scaler,//y
									tileset.tileWidth * scaler, //width
									tileset.tileHeight * scaler, //height
									id);
						}
					}
			}

			System.out.println("FINISHED FIRST LAYER:" + blocks.length);
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
