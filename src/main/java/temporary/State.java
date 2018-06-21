package temporary;

import java.awt.image.BufferedImage;
import static java.lang.Long.MAX_VALUE;
import java.util.Objects;
import org.petehering.sandbox.Utility;

public class State
{
    private final String configuration;
    private final BufferedImage[] images;
    private final long millisecondsPerFrame;
    private final boolean loop;
    
    public State (String config, BufferedImage[] images, long millisecondsPerFrame, boolean loop)
    {
        this.configuration = Objects.requireNonNull (config);
        this.images = Utility.requireNonNull (images);
        this.millisecondsPerFrame = Utility.requireGreaterThan (0, millisecondsPerFrame);
        this.loop = loop;
    }
    
    public State (String config, BufferedImage image)
    {
        this (config, new BufferedImage[]{image}, MAX_VALUE, false);
    }
}
