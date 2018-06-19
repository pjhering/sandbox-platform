package org.petehering.sandbox.platform;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Integer.parseInt;
import java.util.LinkedList;
import static java.util.Objects.requireNonNull;
import static org.petehering.sandbox.Utility.SPACES;

public final class TileLayerIO
{
    private final Class loader;

    public TileLayerIO(Class loader)
    {
        this.loader = requireNonNull (loader);
    }
    
    public TileLayerIO ()
    {
        this (TileLayerIO.class);
    }
    
    public TextTileLayer read (String path) throws IOException
    {
        TextTileLayer layer = null;
        
        try (InputStream stream = loader.getResourceAsStream(path))
        {
            InputStreamReader reader = new InputStreamReader (stream);
            BufferedReader buffer = new BufferedReader (reader);
            String line = buffer.readLine();
            String[] tokens = line.trim().split(SPACES);
            
            Tileset tileset = new TextTileset (tokens[0]);
            int rows = parseInt (tokens[1]);
            int cols = parseInt (tokens[2]);
            int width = parseInt (tokens[3]);
            int height = parseInt (tokens[4]);
            layer = new TextTileLayer (tileset, rows, cols, width, height);
            
            LinkedList<int[]> map = new LinkedList<>();
            while ((line = buffer.readLine()) != null)
            {
                tokens = line.trim().split(SPACES);
                int[] array = new int[tokens.length];
                for (int i = 0; i < array.length; i++)
                {
                    array[i] = parseInt (tokens[i]);
                    map.add(array);
                }
            }
            
            int row = 0;
            while (!map.isEmpty())
            {
                int[] array = map.remove();
                for (int col = 0; col < array.length; col++)
                {
                    if (array[col] >= 0)
                    {
                        layer.set(row, col, array[col]);
                    }
                }
                row += 1;
            }
        }
        catch (Exception ex)
        {
            throw new RuntimeException (ex);
        }
        
        return layer;
    }
    
    public void write (TextTileLayer layer, String path)
    {
        String tileset = layer.getTileset ().getConfiguration ();

        try (FileWriter file = new FileWriter (path, false))
        {
            file.write (tileset);
            file.write ("\n");
            
            for (int r = 0; r < layer.getRows (); r++)
            {
                for (int c = 0; c < layer.getColumns (); c++)
                {
                    Tile t = layer.getTile (r, c);
                    if (t != null)
                    {
                        file.write (Integer.toString (t.getType ()));
                    }
                    else
                    {
                        file.write ("-1");
                    }
                    
                    file.write (' ');
                }
                file.write ('\n');
            }
            file.flush ();
        }
        catch (Exception ex)
        {
            throw new RuntimeException (ex);
        }
    }
}
