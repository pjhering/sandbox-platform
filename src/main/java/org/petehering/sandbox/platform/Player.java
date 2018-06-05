package org.petehering.sandbox.platform;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.Math.round;
import java.util.ArrayList;
import java.util.List;
import static org.petehering.sandbox.platform.Global.*;
import org.petehering.sandbox.sprites.SpriteSheet;

class Player
{
    
    private float x;
    private float y;
    private float width;
    private float height;
    private int currentAnimation;
    private final Animation[] animations;
    
    Player (float x, float y)
    {
        animations = new Animation[PLAYER_ANIMATION_COUNT];
        currentAnimation = 0;
        
        try
        {
            SpriteSheet sheet = new SpriteSheet (PLAYER_IMAGE);
            animations[IDLE] = new Animation (sheet.imageArray(0,   0, 128, 32, 4, 1), 1, true);
            animations[WALK_RIGHT] = new Animation (sheet.imageArray(0,  32, 128, 32, 4, 1), 4, true);
            animations[WALK_LEFT] = new Animation (sheet.imageArray(0,  64, 128, 32, 4, 1), 4, true);
            animations[RUN_RIGHT] = new Animation (sheet.imageArray(0,  96, 128, 32, 4, 1), 8, true);
            animations[RUN_LEFT] = new Animation (sheet.imageArray(0, 128, 128, 32, 4, 1), 8, true);
            animations[JUMP_RIGHT] = new Animation (sheet.imageArray(0, 160, 128, 32, 4, 1), 1, true);
            animations[JUMP_LEFT] = new Animation (sheet.imageArray(0, 192, 128, 32, 4, 1), 1, true);
            animations[FALL_RIGHT] = new Animation (sheet.imageArray(0, 224, 128, 32, 4, 1), 1, true);
            animations[FALL_LEFT] = new Animation (sheet.imageArray(0, 256, 128, 32, 4, 1), 1, true);
        }
        catch (IOException ex)
        {
            throw new RuntimeException (ex);
        }
    }
    
    void setCurrentAnimation (int i)
    {
        if (0 >= i && i < animations.length)
        {
            currentAnimation = i;
            Animation a = animations[currentAnimation];
            a.reset ();
        }
    }

    void render (Graphics2D g, int xOffset, int yOffset)
    {
        Animation a = animations[currentAnimation];
        BufferedImage i = a.getBufferedImage ();
        g.drawImage (i, round (x) - xOffset, round (y) - yOffset, null);
    }
    
    void update (long elapsed)
    {
        Animation a = animations[currentAnimation];
        a.update();
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
