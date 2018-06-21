package org.petehering.sandbox.platform;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import java.util.HashMap;
import java.util.Map;
import static java.util.Objects.requireNonNull;
import javax.imageio.ImageIO;
import static org.petehering.sandbox.Utility.SPACES;
import org.petehering.sandbox.sprites.SpriteSheet;

public class StateIO
{
    private final Class loader;

    public StateIO ()
    {
        this (StateIO.class);
    }

    public StateIO (Class loader)
    {
        this.loader = requireNonNull (loader);
    }
    
    public static State[] select (Map<String, State> map, String ... keys)
    {
        State[] states = new State[keys.length];
        
        for (int i = 0; i < states.length; i++)
        {
            states[i] = map.get (keys[i]);
        }
        
        return states;
    }

    public Map<String, State> read (String path)
    {
        Map<String, BufferedImage> images = new HashMap<> ();
        Map<String, State> states = new HashMap<> ();

        try (InputStream stream = loader.getResourceAsStream (path))
        {
            InputStreamReader reader = new InputStreamReader (stream);
            BufferedReader buffer = new BufferedReader (reader);
            String line;
            String[] tokens;

            while ((line = buffer.readLine ()) != null)
            {
                tokens = line.trim ().split (SPACES);

                if (!images.containsKey (tokens[1]))
                {
                    try (InputStream imageStream = loader.getResourceAsStream (tokens[1]))
                    {
                        BufferedImage image = ImageIO.read (imageStream);
                        images.put (tokens[1], image);
                    }
                }

                BufferedImage image = images.get (tokens[1]);

                switch (tokens.length)
                {
                    case 2://single frame, entire image
                    {
                        State state = new State (image);
                        state.setConfiguration (line.trim ());
                        states.put (tokens[0], state);
                    }
                    break;

                    case 6:
                    {
                        boolean multiple = tokens[5].equalsIgnoreCase ("true")
                            || tokens[5].equalsIgnoreCase ("false");
                        
                        if (multiple)//multiple frames, entire image
                        {
                            int r = parseInt (tokens[2]);
                            int c = parseInt (tokens[3]);
                            long mpf = parseInt (tokens[4]);
                            boolean loop = parseBoolean (tokens[5]);
                            SpriteSheet sheet = new SpriteSheet (image);
                            State state = new State (sheet.imageArray (c, r), mpf, loop);
                            state.setConfiguration (line.trim ());
                            states.put (tokens[0], state);
                        }
                        else//single frame, subimage
                        {
                            int x = parseInt (tokens[2]);
                            int y = parseInt (tokens[3]);
                            int w = parseInt (tokens[4]);
                            int h = parseInt (tokens[5]);
                            State state = new State (image.getSubimage (x, y, w, h));
                            state.setConfiguration (line.trim ());
                            states.put (tokens[0], state);
                        }
                    }
                    break;

                    case 10://multiple frames, subimages
                    {
                        int x = parseInt (tokens[2]);
                        int y = parseInt (tokens[3]);
                        int w = parseInt (tokens[4]);
                        int h = parseInt (tokens[5]);
                        int r = parseInt (tokens[6]);
                        int c = parseInt (tokens[7]);
                        long mpf = parseInt (tokens[8]);
                        boolean loop = parseBoolean (tokens[9]);
                        SpriteSheet sheet = new SpriteSheet (image);
                        State state = new State (sheet.imageArray (x, y, w, h, c, r), mpf, loop);
                        state.setConfiguration (line.trim ());
                        states.put (tokens[0], state);
                    }
                    break;
                }
            }
        }
        catch (Exception ex)
        {
            throw new RuntimeException (ex);
        }

        return states;
    }
}
