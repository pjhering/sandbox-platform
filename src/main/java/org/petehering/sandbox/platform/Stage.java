package org.petehering.sandbox.platform;

import java.awt.Graphics2D;
import java.awt.Point;
import static java.util.Objects.requireNonNull;

public class Stage
{
    public final Viewport viewport;
    public final TileLayer tileLayer;
    private Actor focus;
    
    public Stage (Viewport viewport, TileLayer tileLayer)
    {
        this.viewport = requireNonNull (viewport);
        this.tileLayer = requireNonNull (tileLayer);
    }
    
    public void update (long elapsed)
    {
        viewport.center (this);
        tileLayer.center (viewport);
    }
    
    public void draw (Graphics2D g)
    {
        Point o = viewport.offset;
        tileLayer.draw (g, o.x, o.y);
    }

    public Actor getFocus ()
    {
        return focus;
    }

    public void setFocus (Actor focus)
    {
        this.focus = focus;
    }

    public Viewport getViewport ()
    {
        return viewport;
    }

    public TileLayer getTileLayer ()
    {
        return tileLayer;
    }
}
