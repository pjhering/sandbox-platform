package org.petehering.sandbox.platform;

import static java.awt.Color.BLACK;
import org.petehering.sandbox.DesktopInput;
import org.petehering.sandbox.Game;
import org.petehering.sandbox.View;

class PlatformGame implements Game
{
    private final Stage stage;
    private final DesktopInput input;

    public PlatformGame (DesktopInput input)
    {
        this.input = input;
        this.stage = new Stage (640, 480);
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
        stage.render (view.getViewGraphics ());
        view.present ();
    }

    @Override
    public void dispose ()
    {
    }
}
