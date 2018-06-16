package org.petehering.sandbox.platform;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static org.petehering.sandbox.Utility.clamp;
import static org.petehering.sandbox.Utility.requireGreaterThan;

public class TileLayer
{
    public final int rows;
    public final int columns;
    public final int tileWidth;
    public final int tileHeight;
    public final int width;
    public final int height;
    private final Tile[][] tiles;
    private int firstVisibleRow;
    private int firstVisibleColumn;
    private int lastVisibleRow;
    private int lastVisibleColumn;
    
    public TileLayer (int rows, int columns, int tileWidth, int tileHeight)
    {
        this.rows = requireGreaterThan (0, rows);
        this.columns = requireGreaterThan (0, columns);
        this.tiles = new Tile[rows][columns];
        this.tileWidth = requireGreaterThan (0, tileWidth);
        this.tileHeight = requireGreaterThan (0, tileHeight);
        this.width = columns * tileWidth;
        this.height = rows * tileHeight;
    }
    
    public TileLayer set (int row, int column, BufferedImage image)
    {
        return set (row, column, image, true);
    }
    
    public TileLayer set (int row, int column, BufferedImage image, boolean blocked)
    {
        tiles[row][column] = new Tile (
            image,
            column * tileWidth,
            row * tileHeight,
            tileWidth,
            tileHeight,
            blocked);
        
        return this;
    }
    
    public Tile getTile (int row, int column)
    {
        return tiles[row][column];
    }
    
    public void draw (Graphics2D g, int xOffset, int yOffset)
    {
        for (int r = firstVisibleRow; r <= lastVisibleRow; r++)
        {
            for (int c = firstVisibleColumn; c < lastVisibleColumn; c++)
            {
                tiles[r][c].draw (g, xOffset, yOffset);
            }
        }
    }

    public void center (Viewport vp)
    {
        firstVisibleColumn = (vp.offset.x > 0)
            ? vp.offset.x / tileWidth
            : 0;
        
        firstVisibleRow = (vp.offset.y > 0)
            ? vp.offset.y / tileHeight
            : 0;
        
        lastVisibleColumn = clamp (
            (int) (1 + firstVisibleColumn + (vp.width / tileWidth)),
            firstVisibleColumn,
            columns);
        
        lastVisibleRow = clamp (
            (int) (1 + firstVisibleRow + (vp.height / tileHeight)),
            firstVisibleRow,
            rows);
    }

    public int getFirstVisibleRow ()
    {
        return firstVisibleRow;
    }

    public int getFirstVisibleColumn ()
    {
        return firstVisibleColumn;
    }

    public int getLastVisibleRow ()
    {
        return lastVisibleRow;
    }

    public int getLastVisibleColumn ()
    {
        return lastVisibleColumn;
    }
}
