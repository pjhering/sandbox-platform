package temporary;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.System.out;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.imageio.ImageIO;
import static org.petehering.sandbox.Utility.SPACES;
import org.petehering.sandbox.sprites.SpriteSheet;

public class IniFileParser
{
    private static final Class[] ACTOR_PARAMS = {
        String.class,
        State[].class,
        float.class,
        float.class,
        float.class,
        float.class};
    
    private Map<String, BufferedImage> images;
    private String line;
    private String[] tokens;
    protected Background background;
    private Tileset tileset;
    protected TileLayer tileLayer;
    private Map<String, State[]> states;
    protected List<Actor> actors;

    public IniFileParser (String path)
    {
        images = new HashMap<> ();
        states = new HashMap<> ();
        actors = new ArrayList<> ();

        try (InputStream stream = getClass ().getResourceAsStream (path))
        {
            InputStreamReader reader = new InputStreamReader (stream);
            BufferedReader buffer = new BufferedReader (reader);

            while (next (buffer))
            {
                line = line.trim ().toLowerCase ();

                switch (line)
                {
                    case "[background]":
                        parseBackground (buffer);
                        break;
                    case "[tileset]":
                        parseTileset (buffer);
                        break;
                    case "[tilelayer]":
                        parseTileLayer (buffer);
                        break;
                    case "[states]":
                        parseStates (buffer);
                        break;
                    case "[actors]":
                        parseActors (buffer);
                        break;
                    default:
                        throw new RuntimeException ("unrecognized line: " + line);
                }
            }
        }
        catch (Exception ex)
        {
            throw new RuntimeException (ex);
        }
    }
    
    private void parseBackground (BufferedReader buffer) throws IOException
    {
        if (!next (buffer))
        {
            throw new RuntimeException ("expected background configuration");
        }
        
        BufferedImage image = getImage (line);
        background = new Background (line, image);
    }

    private void parseTileset (BufferedReader buffer) throws IOException
    {
        if (!next (buffer))
        {
            throw new RuntimeException ("expected tileset configuration");
        }

        String config = line;

        // first line must be:
        //   the path to the image
        //   number of rows
        //   number of columns
        //   number of definition lines
        String key = tokens[0];

        BufferedImage image = getImage (tokens[0]);
        int rows = parseInt (tokens[1]);
        int cols = parseInt (tokens[2]);
        int length = parseInt (tokens[3]);

        BufferedImage[] subimages = new SpriteSheet (image).imageArray (cols, rows);
        BufferedImage[] sprites = new BufferedImage[length];
        System.arraycopy (subimages, 0, sprites, 0, length);

        String[] names = new String[length];
        boolean[] blockeds = new boolean[length];

        // each subsequent line must be:
        //   a tile name
        //   blocked boolean
        for (int i = 0; i < length; i++)
        {
            if (next (buffer))
            {
                names[i] = tokens[0];
                blockeds[i] = parseBoolean (tokens[1]);
            }
        }

        tileset = new Tileset (config, sprites, names, blockeds);
    }

    private void parseTileLayer (BufferedReader buffer) throws IOException
    {
        if (!next (buffer))
        {
            throw new RuntimeException ("expected tilelayer configuration");
        }

        // first line must be:
        //   number of rows
        //   number of columns
        //   width of tiles
        //   height of tiles;
        int rows = parseInt (tokens[0]);
        int cols = parseInt (tokens[1]);
        int width = parseInt (tokens[2]);
        int height = parseInt (tokens[3]);
        tileLayer = new TileLayer (line, tileset, rows, cols, width, height);

        for (int r = 0; r < rows; r++)
        {
            if (!next (buffer))
            {
                throw new RuntimeException ("expecting tilelayer row");
            }

            for (int c = 0; c < cols; c++)
            {
                if (c >= tokens.length)
                {
                    throw new RuntimeException ("expecting tilelayer column");
                }

                try
                {
                    int id = parseInt (tokens[c]);

                    // skip negative ids
                    // skip ids not in tileset
                    if (0 <= id && id < tileset.size ())
                    {
                        tileLayer.set (id, r, c);
                    }
                }
                catch (NumberFormatException ex)
                {
                    // skip non-numeric tokens
                }
            }
        }
    }
    
    private void parseStates (BufferedReader buffer) throws IOException
    {
        if (!next (buffer))
        {
            throw new RuntimeException ("expecting states configuration");
        }
        
        // the first line must be;
        //    path to an image
        //    number of states
        //    unique name
        BufferedImage image = getImage (tokens[0]);
        SpriteSheet sheet = new SpriteSheet (image);
        String key = tokens[1];
        int length = parseInt (tokens[2]);
        
        states.put (key, new State[length]);
        
        for (int i = 0; i < length; i++)
        {
            if (!next (buffer))
            {
                throw new RuntimeException ("expecting state definition");
            }
            
            switch (tokens.length)
            {
                // single image - x y width height
                case 4:
                {
                    int x = parseInt (tokens[0]);
                    int y = parseInt (tokens[1]);
                    int w = parseInt (tokens[2]);
                    int h = parseInt (tokens[3]);
                    BufferedImage frame = sheet.subimage (x, y, w, h);
                    states.get (key)[i] = new State (line, frame);
                }
                break;
                    
                // multiple subimages
                // x y width height rows columns framesPerSecond loop
                case 8:
                {
                    int x = parseInt (tokens[0]);
                    int y = parseInt (tokens[1]);
                    int w = parseInt (tokens[2]);
                    int h = parseInt (tokens[3]);
                    int r = parseInt (tokens[4]);
                    int c = parseInt (tokens[5]);
                    long fps = parseLong (tokens[6]);
                    boolean loop = parseBoolean (tokens[7]);
                    BufferedImage[] frames = sheet.imageArray (x, y, w, h, c, r);
                    states.get (key)[i] = new State (line, frames, 1000L/fps, loop);
                }
                break;
                    
                default:
                    throw new RuntimeException ("illegal state definition");
            }
        }
    }
    
    private void parseActors (BufferedReader buffer) throws Exception
    {
        if (!next (buffer))
        {
            throw new RuntimeException ("expecting actors configuration");
        }
        
        // first line must be:
        //   number of actors
        int length = parseInt (tokens[0]);
        
        for (int i = 0; i < length; i++)
        {
            if (!next (buffer))
            {
                throw new RuntimeException ("expecting actor definition");
            }
            
            // actor definition must be:
            //   fully qualified Actor class
            //   states key
            //   x
            //   y
            //   width
            //   height
            Class clazz = Class.forName (tokens[0]);
            String key = tokens[1];
            float x = parseFloat (tokens[2]);
            float y = parseFloat (tokens[3]);
            float w = parseFloat (tokens[4]);
            float h = parseFloat (tokens[5]);
            State[] s = states.get (key);
            Object[] args = {line, s, x, y, w, h};
            actors.add ((Actor) clazz
                .getConstructor (ACTOR_PARAMS)
                .newInstance (args));
        }
    }

    private BufferedImage getImage (String string) throws IOException
    {
        String path = string.startsWith ("/") ? string : "/" + string;

        if (!images.containsKey (path))
        {
            try (InputStream stream = getClass ().getResourceAsStream (path))
            {
                BufferedImage image = ImageIO.read (stream);
                images.put (string, image);
            }
        }

        return images.get (string);
    }

    private boolean next (BufferedReader buffer) throws IOException
    {
        while ((line = buffer.readLine ()) != null)
        {
//            out.println (line);
            
            line = line.trim ();

            if (!(line.startsWith ("#") || line.isEmpty ()))
            {
                tokens = line.trim ().split (SPACES);
                return true;
            }
        }

        return false;
    }

    public Background getBackground ()
    {
        return background;
    }

    public TileLayer getTileLayer ()
    {
        return tileLayer;
    }

    public List<Actor> getActors ()
    {
        return actors;
    }
}
