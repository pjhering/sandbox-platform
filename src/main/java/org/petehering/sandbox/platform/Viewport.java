package org.petehering.sandbox.platform;

import java.awt.Point;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.petehering.sandbox.Utility.requireGreaterThan;

public class Viewport
{
    private float x;
    private float y;
    public final float width;
    public final float height;
    public final Point offset;
    
    public Viewport (float width, float height)
    {
        this (0f, 0f, width, height);
    }
    
    public Viewport (float x, float y, float width, float height)
    {
        this.x = x;
        this.y = y;
        this.width = requireGreaterThan (0f, width);
        this.height = requireGreaterThan (0f, height);
        this.offset = new Point ();
    }
    
    public void center (float targetX, float targetY, int stageWidth, int stageHeight)
    {
        x = max(0f, min(stageWidth - width, targetX - (width / 2f)));
        y = max(0f, min(stageHeight - height, targetY - (height / 2f)));
        
        offset.setLocation (x, y);
    }
    
    public void center (int stageWidth, int stageHeight)
    {
        x = (stageWidth - width) / 2;
        y = (stageHeight - height) / 2;
        
        offset.setLocation (x, y);
    }
    
    public void center (Stage stage)
    {
        Actor a = stage.getFocus ();
        
        if (a != null)
        {
            center (
                a.getCenterX (),
                a.getCenterY (),
                stage.tileLayer.getWidth (),
                stage.tileLayer.getHeight ());
        }
        else
        {
            center (
                stage.tileLayer.getWidth (),
                stage.tileLayer.getHeight ());
        }
    }

    public boolean contains (Actor a)
    {
        return this.x < a.getMaxX ()
            && this.x + this.width > a.getMinX ()
            && this.y < a.getMaxY ()
            && this.y + this.height > a.getMinY ();
    }
    
    @Override
    public String toString ()
    {
        return new StringBuilder ()
            .append ("Viewport{x=")
            .append (x)
            .append (", y=")
            .append (y)
            .append (", width=")
            .append (width)
            .append (", height=")
            .append (height)
            .append ("}")
            .toString ();
    }
}
