package temporary;

import java.awt.Point;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.petehering.sandbox.Utility.requireGreaterThanOrEqualTo;

public class Viewport
{
    private float x;
    private float y;
    private final float width;
    private final float height;
    private final Point offset;
    
    public Viewport (float x, float y, float width, float height)
    {
        this.x = requireGreaterThanOrEqualTo (0f, x);
        this.y = requireGreaterThanOrEqualTo (0f, y);
        this.width = requireGreaterThanOrEqualTo (0f, width);
        this.height = requireGreaterThanOrEqualTo (0f, height);
        this.offset = new Point ();
    }
    
    public void update (float targetX, float targetY, TileLayer tileLayer)
    {
        x = max (0f, min (tileLayer.width - width, targetX));
        y = max (0f, min (tileLayer.height - height, targetY));
        
        offset.setLocation (x, y);
    }
    
    public boolean isViewable (Tile tile)
    {
        return isViewable (tile.x, tile.y, tile.width, tile.height);
    }
    
    public boolean isViewable (Actor actor)
    {
        return isViewable (
            actor.getX (),
            actor.getY (),
            actor.getWidth (),
            actor.getHeight ());
    }
    
    private boolean isViewable (float thatX, float thatY, float thatWidth, float thatHeight)
    {
        return x < thatX + thatWidth
            && thatX < x + width
            && y < thatY + thatHeight
            && thatY < y + height;
    }
    
    public Point getOffset ()
    {
        return offset;
    }
}
