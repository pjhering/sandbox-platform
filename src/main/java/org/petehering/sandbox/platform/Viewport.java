package org.petehering.sandbox.platform;

import static java.lang.Math.round;
import static org.petehering.sandbox.Utility.clamp;


class Viewport
{
    private float x;
    private float y;
    private final float width;
    private final float height;
    private final float stageWidth;
    private final float stageHeight;
    
    Viewport (float width, float height, float stageWidth, float stageHeight)
    {
        this.width = width;
        this.height = height;
        this.stageWidth = stageWidth;
        this.stageHeight = stageHeight;
    }
    
    public boolean isViewable (StageObject obj)
    {
        return this.x < obj.getRight() &&
                this.y < obj.getTop() &&
                obj.getLeft() < this.x + this.width &&
                obj.getBottom() < this.y + this.height;
    }
    
    void center (StageObject obj)
    {
        this.x = clamp (obj.getCenterX () - (width / 2f), 0f, stageWidth - width);
        this.y = clamp (obj.getCenterY () - (height / 2f), 0f, stageHeight - height);
    }
    
    int getXOffset ()
    {
        return round (x);
    }
    
    int getYOffset ()
    {
        return round (y);
    }

    float getX()
    {
        return x;
    }

    float getY()
    {
        return y;
    }

    float getWidth()
    {
        return width;
    }

    float getHeight()
    {
        return height;
    }

    float getStageWidth()
    {
        return stageWidth;
    }

    float getStageHeight()
    {
        return stageHeight;
    }
}
