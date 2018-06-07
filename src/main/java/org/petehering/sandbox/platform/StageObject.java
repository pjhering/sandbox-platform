package org.petehering.sandbox.platform;

import java.awt.Graphics2D;


abstract class StageObject
{
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    protected float dx;
    protected float dy;

    StageObject(float x, float y)
    {
        this.x = x;
        this.y = y;
    }

    abstract void render(Graphics2D g, int xOffset, int yOffset);

    abstract void update(long elapsed);

    void setDelta(float dx, float dy)
    {
        this.dx = dx;
        this.dy = dy;
    }

    float getLeft()
    {
        return x;
    }

    float getRight()
    {
        return x + width;
    }

    float getTop()
    {
        return y;
    }

    float getBottom()
    {
        return y + height;
    }

    float getCenterX()
    {
        return x + (width / 2f);
    }

    float getCenterY()
    {
        return y + (height / 2f);
    }
}
