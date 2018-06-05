package org.petehering.sandbox.platform;

import org.petehering.sandbox.DesktopApp;
import org.petehering.sandbox.DesktopConfig;

public class App 
{
    public static void main( String[] args )
    {
        DesktopConfig c = new DesktopConfig ();
        c.input = new PlatformInput ();
        c.game = new PlatformGame (c.input);
        c.title = "Sandbox Platform 1.0";
        c.width = 640;
        c.height = 480;
        
        DesktopApp app = new DesktopApp (c);
        app.start ();
    }
}
