package org.petehering.sandbox.platform;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static java.lang.Math.round;
import java.util.ArrayList;
import java.util.List;

class Player
{
    static final int IDLE = 0;
    static final int WALK_RIGHT = 1;
    static final int WALK_LEFT = 2;
    static final int RUN_RIGHT = 3;
    static final int RUN_LEFT = 4;
    static final int JUMP_RIGHT = 5;
    static final int JUMP_LEFT = 6;
    static final int FALL_RIGHT = 7;
    static final int FALL_LEFT = 8;
    
    private float x;
    private float y;
    private float width;
    private float height;
    private int currentAnimation;
    private List<Animation> animations;
    
    Player (float x, float y)
    {
        animations = new ArrayList<> ();
        currentAnimation = 0;
    }
    
    void setCurrentAnimation (int i)
    {
        currentAnimation = i;
        Animation a = animations.get (currentAnimation);
        a.reset ();
    }

    void render (Graphics2D g, int xOffset, int yOffset)
    {
//        Animation a = animations.get (currentAnimation);
//        BufferedImage i = a.getBufferedImage ();
//        g.drawImage (i, round (x) - xOffset, round (y) - yOffset, null);
    }
    
    void update (long elapsed)
    {
        Animation a = animations.get (currentAnimation);
        width = a.getBufferedImage ().getWidth ();
        height = a.getBufferedImage ().getHeight ();
    }
    
    float getLeft ()
    {
        return x;
    }
    
    float getRight ()
    {
        return x + width;
    }
    
    float getTop ()
    {
        return y;
    }
    
    float getBottom ()
    {
        return y + height;
    }

    float getCenterX ()
    {
        return x + (width / 2f);
    }

    float getCenterY ()
    {
        return y + (height / 2f);
    }
}
