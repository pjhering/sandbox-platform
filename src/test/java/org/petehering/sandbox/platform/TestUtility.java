package org.petehering.sandbox.platform;

import static java.awt.Color.BLACK;
import static java.awt.Color.RED;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class TestUtility
{
    public static BufferedImage createTestTileImage ()
    {
        BufferedImage image = new BufferedImage (32, 32, TYPE_INT_RGB);
        Graphics g = image.getGraphics ();
        g.setColor (BLACK);
        g.fillRect (0, 0, 32, 32);
        g.setColor (RED);
        g.drawRect (0, 0, 31, 31);
        g.drawLine (0, 0, 31, 31);
        g.drawLine (31, 0, 0, 31);
        g.dispose ();
        
        return image;
    }
    
    public static TileLayer createTestTileLayer (int rows, int columns)
    {
        BufferedImage image = createTestTileImage ();
        TileLayer tileLayer = new TileLayer (rows, columns, 32, 32);
        for (int r = 0; r < tileLayer.rows; r++)
        {
            for (int c = 0; c < tileLayer.columns; c++)
            {
                tileLayer.set (r, c, image);
            }
        }
        
        return tileLayer;
    }
    
    public static Stage createTestStage (int rows, int columns, float vWidth, float vHeight)
    {
        TileLayer layer = createTestTileLayer (rows, columns);
        Viewport viewport = new Viewport (vWidth, vHeight);
        Stage stage = new Stage (viewport, layer);
        stage.setFocus (new Actor (0, 0, 32, 32));
        
        return stage;
    }
}
