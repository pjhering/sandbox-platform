package org.petehering.sandbox.platform;

import static java.awt.Color.BLACK;
import org.petehering.sandbox.Game;
import org.petehering.sandbox.View;
import static org.petehering.sandbox.platform.TestUtility.createTestStage;

public class Game02 implements Game
{
    private final Stage stage;
    
    public Game02 ()
    {
        this.stage = createTestStage (15, 20, 320, 240);
    }

    @Override
    public void update (long elapsed)
    {
        stage.update (elapsed);
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
