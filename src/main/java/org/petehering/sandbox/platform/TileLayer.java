package org.petehering.sandbox.platform;

import java.awt.Graphics2D;


public interface TileLayer
{
    public int getRows ();
    
    public int getColumns ();
    
    public int getTileWidth ();
    
    public int getTileHeight ();
    
    public int getWidth ();
    
    public int getHeight ();

    void center(Viewport vp);

    void detectCollisons(Actor a);

    void draw(Graphics2D g, int xOffset, int yOffset);

    int getFirstVisibleColumn();

    int getFirstVisibleRow();

    int getLastVisibleColumn();

    int getLastVisibleRow();

    Tile getTile(int row, int column);

    Tile set(int row, int column, int type);
}
