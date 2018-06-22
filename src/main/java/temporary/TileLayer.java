package temporary;

import java.awt.Graphics2D;
import java.awt.Point;
import static java.util.Objects.requireNonNull;
import static org.petehering.sandbox.Utility.requireGreaterThan;

public class TileLayer
{
    private final String configuration;
    private final Tileset tileset;
    private final Tile[][] tiles;
    public final int rows;
    public final int columns;
    public final int tileWidth;
    public final int tileHeight;
    public final int width;
    public final int height;
    
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
        int y = row * tileHeight;
        tiles[row][col] = new Tile (tileset, id, x, y, tileWidth, tileHeight);
    }

    void draw (Graphics2D g, Viewport vp)
    {
        Point p = vp.getOffset ();
        
        for (int r = 0; r < rows; r++)
        {
            for (int c = 0; c < columns; c++)
            {
                if (tiles[r][c] != null && vp.isViewable (tiles[r][c]))
                {
                    tiles[r][c].draw (g, p);
                }
            }
        }
    }
}
