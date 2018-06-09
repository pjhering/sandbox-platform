package org.petehering.sandbox.platform;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Math.round;
import static java.lang.System.out;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;
import javax.imageio.ImageIO;
import static org.petehering.sandbox.platform.Global.*;
import org.petehering.sandbox.sprites.SpriteSheet;

class Stage
{
    private Viewport viewport;
    
    private final Brick[][] bricks;
    private final int brickWidth;
    private final int brickHeight;
    private final int width;
    private final int height;
    private final int numberOfVisibleRows;
    private final int numberOfVisibleColumns;
    
    private int numberOfRows;
    private final int numberOfColumns;
    private int firstVisibleRow;
    private int firstVisibleColumn;
    private int lastVisibleRow;
    private int lastVisibleColumn;
    private final TreeMap<Integer, List<StageObject>> groups;
    private Player player;

    Stage (int viewWidth, int viewHeight)
    {
        this.bricks = loadBricks (BRICKS_FILE);
        this.numberOfRows = bricks.length;
        this.numberOfColumns = bricks[0].length;
        brickWidth = bricks[0][0].getWidth ();
        brickHeight = bricks[0][0].getHeight ();
        this.height = bricks.length * brickWidth;
        this.width = bricks[0].length * brickHeight;
        
        this.viewport = new Viewport (viewWidth, viewHeight, width, height);

        this.numberOfVisibleColumns = viewWidth / brickWidth;
        this.numberOfVisibleRows = viewHeight / brickHeight;

        this.groups = new TreeMap<> ();
    }

    private Brick[][] loadBricks (String file)
    {
        Brick[][] array = null;
        
        try (InputStream stream1 = getClass ().getResourceAsStream (file))
        {
            InputStreamReader reader = new InputStreamReader (stream1);
            BufferedReader buffer = new BufferedReader (reader);
            String[] tokens = buffer.readLine ().split (WHITESPACE);
            BufferedImage image = null;
            
            try (InputStream stream2 = getClass ().getResourceAsStream (tokens[0]))
            {
                image = ImageIO.read (stream2);
            }
            catch (IOException ex2)
            {
                throw new RuntimeException (ex2);
            }
            
            SpriteSheet sheet = new SpriteSheet (image);
            
            int tileRows = Integer.parseInt (tokens[1]);
            int tileCols = Integer.parseInt (tokens[2]);
            int tileHeight = image.getHeight () / tileRows;
            int tileWidth = image.getWidth () / tileCols;
            
            BufferedImage[] subimages = sheet.imageArray (tileCols, tileRows);
            
            LinkedList<String> lines = new LinkedList<> ();
            String line;
            while ((line = buffer.readLine ()) != null)
            {
                lines.add (line);
            }
            
            int rows = lines.size ();
            array = new Brick[rows][];
            
            for (int i = 0; i < rows; i++)
            {
                int y = i * tileHeight;
                line = lines.remove ();
                tokens = line.trim ().split (WHITESPACE);
                array[i] = new Brick[tokens.length];

                for (int j = 0; j < tokens.length; j++)
                {
                    int x = j * tileWidth;
                    int tileIndex = Integer.parseInt (tokens[j]);
                    array[i][j] = new Brick (subimages[tileIndex], tileIndex != 0, x, y, tileWidth, tileHeight);
                }
            }
            
            numberOfRows = rows;
        }
        catch (IOException ex1)
        {
            throw new RuntimeException (ex1);
        }
        
        return array;
    }
    
    private int getColumn (float x)
    {
        return round (x) / brickWidth;
    }
    
    private int getRow (float y)
    {
        return round (y) / brickHeight;
    }

    void update (long elapsed)
    {
        player.update(elapsed);
        viewport.center(player);
        updateFirstAndLastRowsAndColumns ();
    }

    private void updateFirstAndLastRowsAndColumns ()
    {
        firstVisibleColumn = viewport.getXOffset() / brickWidth;
        lastVisibleColumn = firstVisibleColumn + numberOfVisibleColumns;// - 1;

        firstVisibleRow = viewport.getYOffset() / brickHeight;
        lastVisibleRow = firstVisibleRow + numberOfVisibleRows;// - 1;
    }

    void render (Graphics2D g)
    {
        this.renderBricks (g);
        this.renderPlayer (g);
    }

    void renderPlayer (Graphics2D g)
    {
        if (viewport.contains (player))
        {
            player.render(g, viewport.getXOffset(), viewport.getYOffset());
        }
    }

    void renderBricks (Graphics2D g)
    {
        for (int row = firstVisibleRow; row <= lastVisibleRow; row++)
        {
            if (row >= numberOfRows)
            {
                break;
            }
            
            for (int col = firstVisibleColumn; col <= lastVisibleColumn; col++)
            {
                if (col > numberOfColumns)
                {
                    break;
                }
                
                bricks[row][col].draw(g, viewport.getXOffset(), viewport.getYOffset());
            }
        }
    }

    Player getPlayer ()
    {
        return player;
    }

    void setPlayer (Player player)
    {
        this.player = player;
    }
}
