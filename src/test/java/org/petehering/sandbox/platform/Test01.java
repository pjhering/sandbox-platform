package org.petehering.sandbox.platform;

import static java.lang.System.out;
import static org.petehering.sandbox.platform.TestUtility.createTestStage;

public class Test01
{
    public static void main (String[] args)
    {
        Stage stage = createTestStage (30, 40, 320, 240);
        
        update (stage, -3000, -3000);
        update (stage, -64, -64);
        update (stage, 0, 0);
        update (stage, 15 * 32, 20 * 32);
        update (stage, 30 * 32, 40 * 32);
        update (stage, 3000, 3000);
        
        for (int r = 0; r < stage.tileLayer.getRows(); r++)
        {
            for (int c = 0; c < stage.tileLayer.getColumns(); c++)
            {
                Tile tile = stage.tileLayer.getTile (r, c);
                out.print ("row ");
                out.print (r);
                out.print (", column ");
                out.print (c);
                out.print (" - ");
                out.println (tile);
            }
        }
    }
    
    private static void update (Stage stage, int x, int y)
    {
        Actor a = stage.getFocus ();
        a.setX (x);
        a.setY (y);
        out.println (a);
        
        stage.update (0L);
        out.println (stage.viewport.offset);
        out.print ("rows ");
        out.print (stage.tileLayer.getFirstVisibleRow ());
        out.print (" - ");
        out.println (stage.tileLayer.getLastVisibleRow ());
        out.print ("columns ");
        out.print (stage.tileLayer.getFirstVisibleColumn ());
        out.print (" - ");
        out.println (stage.tileLayer.getLastVisibleColumn ());
        out.println ();
    }
}
