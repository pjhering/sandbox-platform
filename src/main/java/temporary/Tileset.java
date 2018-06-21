package temporary;

import java.awt.image.BufferedImage;
import java.util.Objects;
import org.petehering.sandbox.Utility;

public class Tileset
{
    private final String configuration;
    private final BufferedImage[] images;
    private final String[] names;
    private final boolean[] blockeds;

    public Tileset (String config, BufferedImage[] images, String[] names, boolean[] blockeds)
    {
        this.configuration = Objects.requireNonNull (config);
        this.images = Utility.requireNonNull (images);
        this.names = Utility.requireNonNull (names);
        this.blockeds = Objects.requireNonNull (blockeds);
        
        if (!(images.length == names.length && names.length == blockeds.length))
        {
            throw new RuntimeException ("unequal array lengths");
        }
    }
    
    public int size ()
    {
        return images.length;
    }
    
    public BufferedImage getImage (int i)
    {
        return images[i];
    }
    
    public String getName (int i)
    {
        return names[i];
    }
    
    public boolean isBlocked (int i)
    {
        return blockeds[i];
    }

    public String getConfiguration ()
    {
        return configuration;
    }
}
