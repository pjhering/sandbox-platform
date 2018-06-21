package org.petehering.sandbox.platform;

import static java.awt.Color.BLACK;
import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.System.out;
import static java.util.Objects.requireNonNull;
import org.petehering.sandbox.Game;
import org.petehering.sandbox.View;
import static org.petehering.sandbox.platform.TestInput.*;
import static org.petehering.sandbox.platform.TestUtility.createTestStage;

public class Game02 implements Game
{
    private final Stage stage;
    private final Actor actor;
    private final TestInput input;
    private float x, y;
    private int width, height;

    public Game02 (TestInput input)
    {
        this.input = requireNonNull (input);
        this.stage = createTestStage (15, 20, 320, 240);
        this.actor = stage.getFocus ();
        actor.setX (150f);
        actor.setY (150f);
        x = 0f;
        y = 0f;
        width = stage.tileLayer.getWidth ();
        height = stage.tileLayer.getHeight ();
    }

    @Override
    public void update (long elapsed)
    {
        float deltaX = elapsed * 0.25f;
        float deltaY = elapsed * 0.25f;

        if (input.isKeyDown (UP))
        {
            y -= deltaY;
        }

        if (input.isKeyDown (DOWN))
        {
            y += deltaY;
        }

        if (input.isKeyDown (LEFT))
        {
            x -= deltaX;
        }

        if (input.isKeyDown (RIGHT))
        {
            x += deltaX;
        }
        
        x = max (0f, min (width, x));
        y = max (0f, min (height, y));

//        out.print (x);
//        out.print (',');
//        out.println (y);
        
        stage.update (elapsed);
        stage.viewport.center (x, y, width, height);
//        input.update ();
        out.println (stage.viewport);
    }

    @Override
    public void render (View view)
    {
        view.clear (BLACK);
        stage.draw (view.getViewGraphics ());
        view.present ();
    }

    @Override
    public void dispose ()
    {
    }
}
