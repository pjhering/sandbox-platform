package temporary;

import java.util.List;
import org.petehering.sandbox.DesktopApp;

public class ViewportTest
{
    public static void main (String[] args)
    {
        IniFileParser ini = new IniFileParser ("/test.ini");
        Background bg = ini.getBackground ();
        TileLayer tl = ini.getTileLayer ();
        List<Actor> a = ini.getActors ();
        Viewport vp = new Viewport (640f, 480f);
        ViewportGame game = new ViewportGame (bg, tl, a, vp);
        
        DesktopApp app = new DesktopApp (game, 640, 480);
        app.start ();
    }
}
