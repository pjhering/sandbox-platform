package org.petehering.sandbox.platform;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import static java.lang.Math.round;
import static org.petehering.sandbox.Utility.requireGreaterThan;
import static org.petehering.sandbox.Utility.requireNonNull;

public class Actor implements Comparable<Actor>
{
    private State[] states;
    private int state;
    private int group;
    private float x;
    private float y;
    private float width;
    private float height;
    
    public Actor (State[] states, float width, float height)
    {
        this (states, 0f, 0f, width, height);
    }
    
    public Actor (State[] states, float x, float y, float width, float height)
    {
        this.states = requireNonNull (states);
        this.x = x;
        this.y = y;
        this.width = requireGreaterThan (0f, width);
        this.height = requireGreaterThan (0f, height);
        this.state = 0;
    }
    
    public void draw (Graphics2D g, int xOffset, int yOffset)
    {
        BufferedImage image = states[state].getCurrentImage ();
        int _x = round (x + ((width - image.getWidth ()) / 2F));
        int _y = round (y + ((height - image.getHeight ()) / 2F));
        g.drawImage (image, _x, _y, null);
    }
    
    public void setState (int i)
    {
        if (0 <= i && i < states.length)
        {
            this.state = i;
            states[i].reset ();
        }
    }
    
    public void setGroup (int group)
    {
        this.group = group;
    }
    
    public int getGroup ()
    {
        return this.group;
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

    @Override
    public int compareTo (Actor that)
    {
        return this.group < that.group
            ? -1
            : this.group == that.group
            ? 0
            : 1;
    }
}
