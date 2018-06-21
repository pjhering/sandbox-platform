package temporary;

import static java.util.Objects.requireNonNull;
import static org.petehering.sandbox.Utility.requireGreaterThan;

public class TileLayer
{
    private final String configuration;
    private final Tileset tileset;
    private Tile[][] tiles;
    private final int rows;
    private final int columns;
    private final int tileWidth;
    private final int tileHeight;
    private final int width;
    private final int height;
    
    public TileLayer (String config, Tileset tileset, int rows, int columns, int tileWidth, int tileHeight)
    {
        this.configuration = requireNonNull (config);
        this.tileset = requireNonNull (tileset);
        this.rows = requireGreaterThan (0, rows);
        this.columns = requireGreaterThan (0, columns);
        this.tiles = new Tile[rows][columns];
        this.tileWidth = requireGreaterThan (0, tileWidth);
        this.tileHeight = requireGreaterThan (0, tileHeight);
        this.width = columns * tileWidth;
        this.height = rows * tileHeight;
    }
    
    public void set (int id, int row, int col)
    {
        int x = col * tileWidth;
        int y = col * tileHeight;
        tiles[row][col] = new Tile (tileset, id, x, y, tileWidth, tileHeight);
    }
}
