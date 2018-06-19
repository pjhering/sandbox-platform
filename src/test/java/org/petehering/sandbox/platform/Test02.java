package org.petehering.sandbox.platform;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.petehering.sandbox.DesktopApp;
import org.petehering.sandbox.Game;

public class Test02 extends TestCase
{
    public Test02 (String name)
    {
        super (name);
    }
    
    public static Test suite()
    {
        return new TestSuite( Test02.class );
    }
    
    public void test02 ()
//    public static void main (String[] args)
    {
        TestInput input = new TestInput ();
        Game game = new Game02 (input);
        DesktopApp app = new DesktopApp (game, input, 320, 240);
        app.start ();
    }
}
