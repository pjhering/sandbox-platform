package org.petehering.sandbox.platform;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import static java.util.Objects.requireNonNull;
import javax.imageio.ImageIO;
import static org.petehering.sandbox.Utility.SPACES;
import static org.petehering.sandbox.Utility.requireGreaterThan;

public class TextTileset implements Tileset
{

    public final String path;
    public final int rows;
    public final int columns;
    private final BufferedImage[] image;
    private final String[] name;
    private final boolean[] blocked;

    public TextTileset(String tilesetFilePath)
    {
        try (InputStream stream = getClass().getResourceAsStream(tilesetFilePath))
        {
            InputStreamReader reader = new InputStreamReader (stream);
            BufferedReader buffer = new BufferedReader (reader);
            String line = buffer.readLine();
            String[] tokens = line.trim().split(SPACES);
            
            this.path = requireNonNull(tokens[0]);
            this.rows = requireGreaterThan(0, parseInt (tokens[1]));
            this.columns = requireGreaterThan(0, parseInt (tokens[2]));

            int length = rows * columns;

            this.image = new BufferedImage[length];
            this.name = new String[length];
            this.blocked = new boolean[length];
            
            while ((line = buffer.readLine()) != null)
            {
                tokens = line.trim().split(SPACES);
                int id = parseInt (tokens[0]);
                name[id] = tokens[1];
                blocked[id] = parseBoolean (tokens[2]);
            }

            BufferedImage source = ImageIO.read(stream);
            int width = source.getWidth() / columns;
            int height = source.getHeight() / rows;

            int id = 0;
            for (int r = 0; r < rows; r++)
            {
                int y = r * height;

                for (int c = 0; c < columns; c++)
                {
                    int x = c * width;

                    image[id] = source.getSubimage(x, y, width, height);
                    id += 1;
                }
            }
        }
        catch (Exception ex)
        {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public BufferedImage getImage(int id)
    {
        return image[id];
    }

    @Override
    public String getName(int id)
    {
        return name[id];
    }

    @Override
    public boolean getBlocked(int id)
    {
        return blocked[id];
    }

    @Override
    public String getConfiguration ()
    {
        return new StringBuilder ()
            .append (path)
            .append (' ')
            .append (rows)
            .append (' ')
            .append (columns)
            .toString ();
            
    }
}
