package org.petehering.sandbox.platform;

import java.awt.image.BufferedImage;
import static java.lang.System.currentTimeMillis;
//import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;

class Animation
{

    private final List<BufferedImage> images;
    private final long FPS;
    private final long MPF;
    private final boolean loop;
    private long startTime;
    private boolean complete;
    private int frame;

    Animation(BufferedImage[] frames, long framesPerSecond, boolean loop)
    {
        this.FPS = framesPerSecond;
        this.MPF = 1000 / FPS;
        this.loop = loop;
        this.images = new ArrayList<>();
        this.startTime = currentTimeMillis();
        complete = false;

        for (BufferedImage image : frames)
        {
            images.add(image);
        }
//        out.println("images.size=" + images.size());
//        out.println("FPS=" + FPS);
//        out.println("MPF=" + MPF);
//        out.println("loop=" + loop);
//        out.println("startTime=" + startTime);
//        out.println("complete=" + complete);
    }

    void reset()
    {
        startTime = currentTimeMillis();
        complete = false;
    }

    void update()
    {
        if (!complete)
        {
            long elapsed = currentTimeMillis() - startTime;

            if (elapsed > 0)
            {
                long next = elapsed / MPF;

                if (next >= images.size())
                {
                    if (loop)
                    {
                        frame = (int) (next % images.size());
                    }
                    else
                    {
                        frame = images.size() - 1;
                        complete = true;
                    }
                }
                else
                {
                    frame = (int) next;
                }
            }
        }
    }

    BufferedImage getBufferedImage()
    {

        return images.get(frame);
    }
}
