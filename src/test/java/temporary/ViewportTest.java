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
        Viewport vp = new Viewport (tl.width - 32, tl.height - 32);
        ViewportGame game = new ViewportGame (bg, tl, a, vp);
        
        DesktopApp app = new DesktopApp (game, tl.width - 32, tl.height - 32);
        app.start ();
    }
}
