package org.petehering.sandbox.platform;

import static org.petehering.sandbox.Utility.requireGreaterThan;

public class Actor
{
    private float x;
    private float y;
    private float width;
    private float height;
    
    public Actor (float width, float height)
    {
        this (0f, 0f, width, height);
    }
    
    public Actor (float x, float y, float width, float height)
    {
        this.x = x;
        this.y = y;
        this.width = requireGreaterThan (0f, width);
        this.height = requireGreaterThan (0f, height);
    }

    public float getX ()
    {
        return x;
    }

    public void setX (float x)
    {
        this.x = x;
    }

    public float getMinX ()
    {
        return x;
    }

    public float getCenterX ()
    {
        return x + (width / 2f);
    }
    
    public float getMaxX ()
    {
        return x + height;
    }

    public float getY ()
    {
        return y;
    }

    public void setY (float y)
    {
        this.y = y;
    }

    public float getMinY ()
    {
        return y;
    }

    public float getCenterY ()
    {
        return y + (height / 2f);
    }
    
    public float getMaxY ()
    {
        return y + height;
    }

    public float getWidth ()
    {
        return width;
    }

    public void setWidth (float width)
    {
        this.width = requireGreaterThan (0f, width);
    }

    public float getHeight ()
    {
        return height;
    }

    public void setHeight (float height)
    {
        this.height = requireGreaterThan (0f, height);
    }
    
    @Override
    public String toString ()
    {
        return new StringBuilder ()
            .append ("Actor{x=")
            .append (x)
            .append (", y=")
            .append (y)
            .append (", width=")
            .append (width)
            .append (", height=")
            .append (height)
            .append ("}")
            .toString ();
    }
}
