package temporary;

import java.awt.image.BufferedImage;
import static java.lang.Long.MAX_VALUE;
import static java.lang.System.currentTimeMillis;
import java.util.Objects;
import org.petehering.sandbox.Utility;

public class State
{
    private final String configuration;
    private final BufferedImage[] images;
    private final long millisecondsPerFrame;
    private final boolean loop;
    private int index;
    private long startTime;
    private boolean complete;
    private long elapsedTime;
    
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

    void reset ()
    {
        startTime = currentTimeMillis ();
        complete = false;
        elapsedTime = 0L;
        index = 0;
    }
    
    void update (long elapsed)
    {
        if (images.length == 1 || complete)
        {
            return;
        }
        
        elapsedTime += elapsed;
        
        LOOP:
        while (elapsedTime > millisecondsPerFrame)
        {
            elapsedTime -= millisecondsPerFrame;
            index += 1;
            
            if (index >= images.length)
            {
                if (loop)
                {
                    index = 0;
                }
                else
                {
                    index = images.length - 1;
                    complete = true;
                    break LOOP;
                }
            }
        }
    }

    BufferedImage getImage ()
    {
        return images[index];
    }
}
