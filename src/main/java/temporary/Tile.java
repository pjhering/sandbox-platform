package temporary;

import java.awt.image.BufferedImage;
import static java.util.Objects.requireNonNull;
import static org.petehering.sandbox.Utility.requireGreaterThan;
import static org.petehering.sandbox.Utility.requireGreaterThanOrEqualTo;

public class Tile
{
    private final Tileset tileset;
    public final int id;
    public final int x;
    public final int y;
    public final int width;
    public final int height;
    
    public Tile (Tileset tileset, int id, int x, int y, int width, int height)
    {
        this.tileset = requireNonNull (tileset);
        this.id = requireGreaterThanOrEqualTo (0, id);
        this.x = requireGreaterThanOrEqualTo (0, x);
        this.y = requireGreaterThanOrEqualTo (0, y);
        this.width = requireGreaterThan (0, width);
        this.height = requireGreaterThan (0, height);
    }
    
    public BufferedImage getImage ()
    {
        return tileset.getImage (id);
    }
    
    public String getName ()
    {
        return tileset.getName (id);
    }
    
    public boolean isBlocked ()
    {
        return tileset.isBlocked (id);
    }
}
