package org.petehering.sandbox.platform;

import java.awt.image.BufferedImage;
import static org.petehering.sandbox.Utility.requireGreaterThan;
import static org.petehering.sandbox.Utility.requireNonNull;

public class State
{
    private final BufferedImage[] frames;
    private final long millisecondsPerFrame;
    private final boolean loop;
    private int index;
    private long elapsedTime;
    private boolean complete;
    
    public State (BufferedImage[] frames, long millisecondsPerFrame, boolean loop)
    {
        this.frames = requireNonNull (frames);
        this.millisecondsPerFrame = requireGreaterThan (0L, millisecondsPerFrame);
        this.loop = loop;
        reset ();
    }
    
    public BufferedImage getCurrentImage ()
    {
        return frames[index];
    }
    
    public void update (long elapsedMilliseconds)
    {
        if (complete)
        {
            return;
        }
        
        this.elapsedTime += elapsedMilliseconds;
        
        while (elapsedTime > millisecondsPerFrame)
        {
            index += 1;
            
            if (index >= frames.length)
            {
                if (loop)
                {
                    index = 0;
                }
                else
                {
                    index = frames.length - 1;
                    complete = true;
                }
            }
        }
    }
    
    public final void reset ()
    {
        this.index = 0;
        this.elapsedTime = 0L;
        this.complete = false;
    }
}
