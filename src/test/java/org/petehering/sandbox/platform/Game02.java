package org.petehering.sandbox.platform;

import static java.awt.Color.BLACK;
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
    
    public Game02 (TestInput input)
    {
        this.input = requireNonNull (input);
        this.stage = createTestStage (15, 20, 320, 240);
        this.actor = stage.getFocus ();
    }

    @Override
    public void update (long elapsed)
    {
        if (input.isKeyDown (UP))
        {
            actor.setY (actor.getY () - (elapsed * .25f));
        }
        
        if (input.isKeyDown (DOWN))
        {
            actor.setY (actor.getY () + (elapsed * .25f));
        }
        
        if (input.isKeyDown (LEFT))
        {
            actor.setX (actor.getX () - (elapsed * .25f));
        }
        
        if (input.isKeyDown (RIGHT))
        {
            actor.setX (actor.getX () + (elapsed * .25f));
        }
        
        stage.update (elapsed);
        input.update ();
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
