package org.petehering.sandbox.platform;

import java.awt.image.BufferedImage;
import static java.lang.System.currentTimeMillis;
import java.util.ArrayList;
import java.util.List;

class Animation
{
    private final List<BufferedImage> images;
    private final long FPS;
    private final long MPF;
    private long startTime;
    private boolean complete;
    private int frame;
    
    Animation (long framesPerSecond)
    {
        this.images = new ArrayList<> ();
        this.FPS = framesPerSecond;
        this.MPF = 1000 / FPS;
    }
    
    void reset ()
    {
        startTime = currentTimeMillis ();
        complete = false;
    }
    
    void update ()
    {
        long elapsed = currentTimeMillis () - startTime;
        frame = (int) (elapsed / MPF);
        
        if (frame >= images.size ())
        {
            complete = true;
            frame = 0;
        }
    }
    
    Animation add (BufferedImage frame)
    {
        images.add (frame);
        return this;
    }
    
    Animation add (BufferedImage image, int x, int y, int width, int height)
    {
        images.add (image.getSubimage (x, y, width, height));
        return this;
    }
    
    BufferedImage getBufferedImage ()
    {
        
        return images.get (frame);
    }
}
