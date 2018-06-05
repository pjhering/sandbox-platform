package org.petehering.sandbox.platform;

import org.petehering.sandbox.DesktopApp;
import org.petehering.sandbox.DesktopConfig;
import static org.petehering.sandbox.platform.Global.*;

public class App 
{
    public static void main( String[] args )
    {
        DesktopConfig c = new DesktopConfig ();
        c.input = new PlatformInput ();
        c.game = new PlatformGame (c.input);
        c.title = APP_TITLE;
        c.width = APP_WIDTH;
        c.height = APP_HEIGHT;
        
        DesktopApp app = new DesktopApp (c);
        app.start ();
    }
}
