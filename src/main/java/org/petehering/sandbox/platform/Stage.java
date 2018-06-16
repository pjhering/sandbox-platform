package org.petehering.sandbox.platform;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.Collections.sort;
import java.util.List;
import static java.util.Objects.requireNonNull;

public class Stage
{
    public final Viewport viewport;
    public final TileLayer tileLayer;
    public final List<Actor> actors;
    private Actor focus;
    
    public Stage (Viewport viewport, TileLayer tileLayer)
    {
        this.viewport = requireNonNull (viewport);
        this.tileLayer = requireNonNull (tileLayer);
        this.actors = new ArrayList<> ();
    }
    
    public boolean add (Actor actor)
    {
        return actors.add (actor);
    }
    
    public boolean remove (Actor actor)
    {
        return actors.remove (actor);
    }
    
    public void update (long elapsed)
    {
        viewport.center (this);
        tileLayer.center (viewport);
        sort (actors);
    }
    
    public void draw (Graphics2D g)
    {
        Point o = viewport.offset;
        tileLayer.draw (g, o.x, o.y);
        actors.forEach ((Actor a) ->
        {
            if (viewport.contains (a))
            {
                a.draw (g, o.x, o.y);
            }
        });
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
