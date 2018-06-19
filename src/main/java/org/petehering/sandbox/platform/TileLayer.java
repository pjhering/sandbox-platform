package org.petehering.sandbox.platform;

import java.awt.Graphics2D;
import static java.util.Objects.requireNonNull;
import static org.petehering.sandbox.Utility.clamp;
import static org.petehering.sandbox.Utility.requireGreaterThan;

public class TileLayer
{

    private final Tileset tileset;
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

    public TileLayer(Tileset tileset, int rows, int columns, int tileWidth, int tileHeight)
    {
        this.tileset = requireNonNull (tileset);
        this.rows = requireGreaterThan(0, rows);
        this.columns = requireGreaterThan(0, columns);
        this.tiles = new Tile[rows][columns];
        this.tileWidth = requireGreaterThan(0, tileWidth);
        this.tileHeight = requireGreaterThan(0, tileHeight);
        this.width = columns * tileWidth;
        this.height = rows * tileHeight;
    }

    public Tile set(int row, int column, int type)
    {
        tiles[row][column] = new Tile(
                tileset,
                type,
                column * tileWidth,
                row * tileHeight,
                tileWidth,
                tileHeight);

        return tiles[row][column];
    }

    public Tile getTile(int row, int column)
    {
        if (0 <= row && row < tiles.length)
        {
            if (0 <= column && column < tiles[row].length)
            {
                return tiles[row][column];
            }
        }

        return null;
    }

    public void draw(Graphics2D g, int xOffset, int yOffset)
    {
        for (int r = firstVisibleRow; r <= lastVisibleRow; r++)
        {
            for (int c = firstVisibleColumn; c < lastVisibleColumn; c++)
            {
                tiles[r][c].draw(g, xOffset, yOffset);
            }
        }
    }

    public void center(Viewport vp)
    {
        firstVisibleColumn = (vp.offset.x > 0)
                ? vp.offset.x / tileWidth
                : 0;

        firstVisibleRow = (vp.offset.y > 0)
                ? vp.offset.y / tileHeight
                : 0;

        lastVisibleColumn = clamp(
                (int) (1 + firstVisibleColumn + (vp.width / tileWidth)),
                firstVisibleColumn,
                columns - 1);

        lastVisibleRow = clamp(
                (int) (1 + firstVisibleRow + (vp.height / tileHeight)),
                firstVisibleRow,
                rows - 1);
    }

    public int getFirstVisibleRow()
    {
        return firstVisibleRow;
    }

    public int getFirstVisibleColumn()
    {
        return firstVisibleColumn;
    }

    public int getLastVisibleRow()
    {
        return lastVisibleRow;
    }

    public int getLastVisibleColumn()
    {
        return lastVisibleColumn;
    }

    public void detectCollisons(Actor a)
    {
        float top = a.getMinY();
        float bottom = a.getMaxY();
        float left = a.getMinX();
        float right = a.getMaxX();

        Tile tl = getTile(top, left);
        Tile tr = getTile(top, right);
        Tile bl = getTile(bottom, left);
        Tile br = getTile(bottom, right);

        if ((tl != null && tl.isBlocked())
                || (tr != null && tr.isBlocked())
                || (bl != null && bl.isBlocked())
                || (br != null && br.isBlocked()))
        {
            a.hitTile(tl, tr, bl, br);
        }
    }

    private Tile getTile(float y, float x)
    {
        int row = (int) (y / tileHeight);
        int col = (int) (x / tileWidth);

        return getTile(row, col);
    }
}
