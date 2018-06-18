package org.petehering.sandbox.platform;

import org.petehering.sandbox.DesktopApp;
import org.petehering.sandbox.Game;

public class Test02
{
    public static void main (String[] args)
    {
        TestInput input = new TestInput ();
        Game game = new Game02 (input);
        DesktopApp app = new DesktopApp (game, input, 320, 240);
        app.start ();
    }
}
