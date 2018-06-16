package org.petehering.sandbox.platform;

import org.petehering.sandbox.DesktopApp;
import org.petehering.sandbox.Game;

public class Test02
{
    public static void main (String[] args)
    {
        Game game = new Game02 ();
        DesktopApp app = new DesktopApp (game, 320, 240);
        app.start ();
    }
}
