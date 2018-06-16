package org.petehering.sandbox.platform;

import java.awt.Color;
import static java.awt.Color.*;
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
    public static BufferedImage createTestStateImage (Color color)
    {
        BufferedImage image = new BufferedImage (32, 32, TYPE_INT_RGB);
        Graphics g = image.getGraphics ();
        g.setColor (BLACK);
        g.fillRect (0, 0, 32, 32);
        g.setColor (color);
        g.drawOval (0, 0, 31, 31);
        g.dispose ();
        
        return image;
    }
    
    public static State createTestState (Color color, boolean loop)
    {
        BufferedImage frame = createTestStateImage (color);
        BufferedImage[] images = {frame, frame, frame, frame};
        return new State (images, 250L, loop);
    }
    
    public static Actor createTestActor ()
    {
        State[] states = {
            createTestState (GREEN, true),
            createTestState (YELLOW, false),
            createTestState (ORANGE, true),
            createTestState (RED, false),
            createTestState (MAGENTA, true),
            createTestState (BLUE, false)
        };
        return new Actor (states, 32, 32);
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
        stage.setFocus (createTestActor ());
        stage.add (stage.getFocus ());
        return stage;
    }
}
