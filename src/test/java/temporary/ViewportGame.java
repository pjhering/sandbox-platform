package temporary;

import static java.awt.Color.BLACK;
import java.util.List;
import org.petehering.sandbox.Game;
import org.petehering.sandbox.View;

public class ViewportGame implements Game
{
    private final Background bg;
    private final TileLayer tiles;
    private final List<Actor> actors;
    private final Viewport viewport;
    private float x, y;
    private float deltaX;
    private float deltaY;
    
    public ViewportGame (Background bg, TileLayer tl, List<Actor> a, Viewport vp)
    {
        this.bg = bg;
        this.tiles = tl;
        this.actors = a;
        this.viewport = vp;
        this.x = 0f;
        this.y = 0f;
        this.deltaX = 0.125f;
        this.deltaY = 0.125f;
    }

    @Override
    public void update (long elapsed)
    {
        x += elapsed * deltaX;
        y += elapsed * deltaY;
        
        if (0f > x || x > tiles.width)
        {
            deltaX *= -1;
        }
        
        if (0f > y || y > tiles.height)
        {
            deltaY *= -1;
        }
        
        viewport.update (x, y, tiles);
    }

    @Override
    public void render (View view)
    {
        view.clear (BLACK);
        tiles.draw (view.getViewGraphics (), viewport);
        view.present ();
    }

    @Override
    public void dispose ()
    {
    }
}
