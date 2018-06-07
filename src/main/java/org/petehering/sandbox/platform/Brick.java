package org.petehering.sandbox.platform;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static java.util.Objects.requireNonNull;
import static org.petehering.sandbox.platform.Global.*;

class Brick
{
    private final boolean blocked;
    private final BufferedImage image;
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    
    Brick (BufferedImage image, boolean blocked, int x, int y, int width, int height)
    {
        this.image = requireNonNull (image);
        this.blocked = blocked;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }
    
    void draw (Graphics2D g, int xOffset, int yOffset)
    {
        g.drawImage (image, x - xOffset, y - yOffset, width, height, null);
        
        if (DEBUG)
        {
            g.setColor (blocked ? BRICK_BLOCKED_COLOR : BRICK_NORMAL_COLOR);
            g.drawRect (x - xOffset, y - yOffset, width - 1, height - 1);
        }
    }
    
    int getX ()
    {
        return x;
    }

    int getY ()
    {
        return y;
    }

    int getWidth ()
    {
        return width;
    }

    int getHeight ()
    {
        return height;
    }

    boolean isBlocked ()
    {
        return blocked;
    }

    BufferedImage getImage ()
    {
        return image;
    }
}
