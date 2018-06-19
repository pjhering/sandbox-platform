package org.petehering.sandbox.platform;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static java.util.Objects.requireNonNull;
import static org.petehering.sandbox.Utility.requireGreaterThan;
import static org.petehering.sandbox.Utility.requireGreaterThanOrEqualTo;

public class Tile
{

    private final Tileset tileset;
    private final int type;
    private final int x;
    private final int y;
    private final int width;
    private final int height;

    public Tile(Tileset tileset, int type, int x, int y, int width, int height)
    {
        this.tileset = requireNonNull(tileset);
        this.type = type;
        this.x = requireGreaterThanOrEqualTo(0, x);
        this.y = requireGreaterThanOrEqualTo(0, y);
        this.width = requireGreaterThan(0, width);
        this.height = requireGreaterThan(0, height);
    }

    public void draw(Graphics2D g, int xOffset, int yOffset)
    {
        g.drawImage(tileset.getImage(type), x - xOffset, y - yOffset, width, height, null);
    }

    @Override
    public String toString()
    {
        return new StringBuilder()
                .append("Tile{type=")
                .append(type)
                .append(", name='")
                .append(tileset.getName(type))
                .append("', x=")
                .append(x)
                .append(", y=")
                .append(y)
                .append(", width=")
                .append(width)
                .append(", height=")
                .append(height)
                .append(", blocked=")
                .append(tileset.getBlocked(type))
                .append("}")
                .toString();

    }
    
    public String getName ()
    {
        return tileset.getName(type);
    }
    
    public boolean isBlocked ()
    {
        return tileset.getBlocked(type);
    }

    public int getType()
    {
        return type;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }
}
