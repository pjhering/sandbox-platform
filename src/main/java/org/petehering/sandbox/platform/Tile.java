package org.petehering.sandbox.platform;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static java.util.Objects.requireNonNull;
import static org.petehering.sandbox.Utility.requireGreaterThan;
import static org.petehering.sandbox.Utility.requireGreaterThanOrEqualTo;

public class Tile
{
    public final BufferedImage image;
    public final int x;
    public final int y;
    public final int width;
    public final int height;
    public final boolean blocked;
    
    public Tile (BufferedImage image, int x, int y, int width, int height, boolean blocked)
    {
        this.image = requireNonNull (image);
        this.x = requireGreaterThanOrEqualTo (0, x);
        this.y = requireGreaterThanOrEqualTo (0, y);
        this.width = requireGreaterThan (0, width);
        this.height = requireGreaterThan (0, height);
        this.blocked = blocked;
    }
    
    public void draw (Graphics2D g, int xOffset, int yOffset)
    {
        g.drawImage (image, x - xOffset, y - yOffset, width, height, null);
    }
    
    @Override
    public String toString ()
    {
        return new StringBuilder ()
            .append ("Tile{x=")
            .append (x)
            .append (", y=")
            .append (y)
            .append (", width=")
            .append (width)
            .append (", height=")
            .append (height)
            .append (", blocked=")
            .append (blocked)
            .append ("}")
            .toString ();
            
    }
}
