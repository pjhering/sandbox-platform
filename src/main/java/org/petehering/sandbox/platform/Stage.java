package org.petehering.sandbox.platform;

import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import static java.util.Collections.sort;
import java.util.List;
import static java.util.Objects.requireNonNull;

public class Stage
{
    public final Viewport viewport;
    public final TileLayer tileLayer;
    public final List<Actor> actors;
    public final List<Actor> added;
    public final List<Actor> removed;
    private Actor focus;

    public Stage (Viewport viewport, TileLayer tileLayer)
    {
        this.viewport = requireNonNull (viewport);
        this.tileLayer = requireNonNull (tileLayer);
        this.actors = new ArrayList<> ();
        this.added = new ArrayList<> ();
        this.removed = new ArrayList<> ();
    }

    public boolean add (Actor actor)
    {
        return added.add (actor);
    }

    public boolean remove (Actor actor)
    {
        return removed.remove (actor);
    }

    public void update (long elapsed)
    {
        int size = actors.size ();
        for (int a = 0; a < size; a++)
        {
            actors.get (a).update (elapsed);
        }

        for (int a = 0; a < size; a++)
        {
            Actor a1 = actors.get (a);

            for (int b = a + 1; b < size; b++)
            {
                Actor a2 = actors.get (b);

                if (a1.getGroup () != a2.getGroup ())
                {
                    if (a1.intersects (a2))
                    {
                        a1.hitActor (a2);
                    }
                }
            }

            if (a1.getAdded ().size () > 0)
            {
                added.addAll (a1.getAdded ());
                a1.getAdded ().clear ();
            }

            if (a1.getRemoved ().size () > 0)
            {
                removed.addAll (a1.getRemoved ());
                a1.getRemoved ().clear ();
            }
        }

        if (removed.size () > 0)
        {
            actors.removeAll (removed);
            removed.clear ();
        }

        if (added.size () > 0)
        {
            actors.addAll (added);
            added.clear ();
            sort (actors);
        }

        viewport.center (this);
        tileLayer.center (viewport);
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
