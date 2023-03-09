/**
 * Title: Tileset.java
 * @author Bradley Hopkins
 * @version 1.0
 * @since Mar. 8, 2022
 */
package tiles;

import graphics.SpriteSheet;
/**
 * The Class Tileset. This class is a holder for all the information a tileSet contains 
 */
public class Tileset {

	/** The name. */
	public String name;
	
	/** The grid start. */
	public int gridStart;
	
	/** The tile width. */
	public int tileWidth;
	
	/** The tile height. */
	public int tileHeight;
	
	/** The grid end. */
	public int gridEnd;
	
	/** The columns. */
	public int columns;
	
	/** The source file name. */
	public String sourceFileName;
	
	/** The source width. */
	public int sourceWidth;
	
	/** The source height. */
	public int sourceHeight;
	
	/** The sprite sheet. */
	public SpriteSheet spriteSheet;
	
	/**
	 * Tileset.
	 *
	 * @param name the name
	 * @param gridStart the grid start
	 * @param tileWidth the tile width
	 * @param tileHeight the tile height
	 * @param tileCount the tile count
	 * @param columns the columns
	 * @param sourceFileName the source file name
	 * @param sourceWidth the source width
	 * @param sourceHeight the source height
	 */
	public Tileset(String name, int gridStart, int tileWidth, int tileHeight, int tileCount,
			int columns, String sourceFileName, int sourceWidth, int sourceHeight) {
		this.name = name;
		this.gridStart = gridStart;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.gridEnd = (tileCount + gridStart) -1;
		this.columns = columns;
		this.sourceFileName = sourceFileName;
		this.sourceWidth = sourceWidth;
		this.sourceHeight = sourceHeight;
		spriteSheet = new SpriteSheet(sourceFileName, tileWidth, tileHeight);
	}
}
