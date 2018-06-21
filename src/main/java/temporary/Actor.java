package temporary;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Objects;
import org.petehering.sandbox.Utility;
import static org.petehering.sandbox.Utility.requireGreaterThan;

public class Actor
{
    private final String configuration;
    private final State[] states;
    private int state;
    protected float x;
    protected float y;
    protected float width;
    protected float height;
    
    public Actor (String config, float x, float y, float width, float height)
    {
        this (config, new State[0], x, y, width, height);
    }
    
    public Actor (String config, State state, float x, float y, float width, float height)
    {
        this (config, new State[]{state}, x, y, width, height);
    }
    
    public Actor (String config, State[] states, float x, float y, float width, float height)
    {
        this.configuration = Objects.requireNonNull (config);
        this.states = Utility.requireNonNull (states);
        this.x = x;
        this.y = y;
        this.width = requireGreaterThan (0f, width);
        this.height = requireGreaterThan (0f, height);
    }
    
    public void update (long elapsed)
    {
    }
    
    public void draw (Graphics2D g, int xOffset, int yOffset)
    {
        BufferedImage image = this.states[state].getImage ();
        int iWidth = image.getWidth () / 2;
        int iHeight = image.getHeight () / 2;
        int _x = (int) (x + (width / 2f)) - iWidth - xOffset;
        int _y = (int) (y + (height / 2f)) - iHeight - yOffset;
        g.drawImage (image, _x, _y, null);
    }
    
    public void setState (int state)
    {
        if (0 <= state && state < states.length)
        {
            this.state = state;
            this.states[state].reset ();
        }
    }
    
    State getState ()
    {
        return states[state];
    }

    public float getX ()
    {
        return x;
    }

    public void setX (float x)
    {
        this.x = x;
    }

    public float getY ()
    {
        return y;
    }

    public void setY (float y)
    {
        this.y = y;
    }

    public float getWidth ()
    {
        return width;
    }

    public void setWidth (float width)
    {
        this.width = width;
    }

    public float getHeight ()
    {
        return height;
    }

    public void setHeight (float height)
    {
        this.height = height;
    }

    public String getConfiguration ()
    {
        return configuration;
    }
}
