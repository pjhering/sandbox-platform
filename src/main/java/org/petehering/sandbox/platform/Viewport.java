package org.petehering.sandbox.platform;

import java.awt.Point;
import static org.petehering.sandbox.Utility.clamp;
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
    
    public void center (Stage stage)
    {
        Actor a = stage.getFocus ();
        
        if (a != null)
        {
            x = clamp (a.getCenterX () - (width / 2f), 0f, stage.tileLayer.width - width);
            y = clamp (a.getCenterY () - (height / 2f), 0f, stage.tileLayer.height - height);
        }
        else
        {
            x = (stage.tileLayer.width - width) / 2f;
            y = (stage.tileLayer.height - height) / 2f;
        }
        
        offset.setLocation (x, y);
    }

    public boolean contains (Actor a)
    {
        return this.x < a.getMaxX ()
            && this.x + this.width > a.getMinX ()
            && this.y < a.getMaxY ()
            && this.y + this.height > a.getMinY ();
    }
}
