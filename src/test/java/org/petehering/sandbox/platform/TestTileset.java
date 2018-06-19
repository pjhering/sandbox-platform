package org.petehering.sandbox.platform;

import java.awt.image.BufferedImage;
import static java.util.Objects.requireNonNull;


public class TestTileset implements Tileset
{
    private BufferedImage image;
    
    public TestTileset (BufferedImage image)
    {
        this.image = requireNonNull (image);
    }

    @Override
    public boolean getBlocked(int id)
    {
        return false;
    }

    @Override
    public BufferedImage getImage(int id)
    {
        return image;
    }

    @Override
    public String getName(int id)
    {
        return "test";
    }

    @Override
    public String getConfiguration ()
    {
        return "test";
    }
}
