package org.petehering.sandbox.platform;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import static java.lang.Math.round;
//import static java.lang.System.out;
import java.util.LinkedList;
import javax.imageio.ImageIO;
import static org.petehering.sandbox.Utility.clamp;
import static org.petehering.sandbox.platform.Global.*;
import org.petehering.sandbox.sprites.SpriteSheet;

class Stage
{
    private final int viewWidth;
    private final int viewHeight;
    
    private final Brick[][] bricks;
    private final int brickWidth;
    private final int brickHeight;
    private final int width;
    private final int height;
    private final int numberOfVisibleRows;
    private final int numberOfVisibleColumns;
    
    private final Player player;
    
    private int xOffset;
    private int yOffset;
    private int numberOfRows;
    private int numberOfColumns;
    private int firstVisibleRow;
    private int firstVisibleColumn;
    private int lastVisibleRow;
    private int lastVisibleColumn;

    Stage (int viewWidth, int viewHeight)
    {
        this.viewWidth = viewWidth;
        this.viewHeight = viewHeight;

        this.bricks = loadBricks (BRICKS_FILE);
        brickWidth = bricks[0][0].getWidth ();
        brickHeight = bricks[0][0].getHeight ();
        this.width = bricks.length * bricks[0][0].getWidth ();
        this.height = bricks[0].length * bricks[0][0].getHeight ();

        this.numberOfVisibleColumns = viewWidth / brickWidth + 2;
        this.numberOfVisibleRows = viewHeight / brickHeight + 2;

        this.player = new Player (PLAYER_START_X, PLAYER_START_Y);
        this.player.setCurrentAnimation (IDLE);
        this.player.setDelta (0.0f, 0.1f);
        
        updateOffsets ();
        updateFirstAndLastRowsAndColumns();
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
            String line = null;
            while ((line = buffer.readLine ()) != null)
            {
                lines.add (line);
            }
            
            int rows = lines.size ();
            array = new Brick[rows][];
            
            for (int i = 0; i < rows; i++)
            {
                numberOfRows += 1;
                int y = i * tileHeight;
                line = lines.remove ();
                tokens = line.trim ().split (WHITESPACE);
                array[i] = new Brick[tokens.length];

                for (int j = 0; j < tokens.length; j++)
                {
                    if (j > numberOfColumns) numberOfColumns += 1;
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
        updateOffsets ();
        updateFirstAndLastRowsAndColumns ();
    }

    private void updateOffsets ()
    {
        float x = player.getCenterX () - (width / 2f);
        float y = player.getCenterY () - (height / 2f);

        xOffset = round (clamp (x, 0f, width - viewWidth));
        yOffset = round (clamp (y, 0f, height - viewHeight));
    }

    private void updateFirstAndLastRowsAndColumns ()
    {
        firstVisibleColumn = xOffset / brickWidth;
        lastVisibleColumn = firstVisibleColumn + numberOfVisibleColumns;

        firstVisibleRow = yOffset / brickHeight;
        lastVisibleRow = firstVisibleRow + numberOfVisibleRows;
    }

    void render (Graphics2D g)
    {
        this.renderBricks (g);
        this.renderPlayer (g);
    }

    void renderPlayer (Graphics2D g)
    {
        player.render (g, xOffset, yOffset);
    }

    void renderBricks (Graphics2D g)
    {
        //int count = 0;
        for (int row = firstVisibleRow; row <= lastVisibleRow; row++)
        {
            if (row >= numberOfRows) break;
            
            for (int col = firstVisibleColumn; col <= lastVisibleColumn; col++)
            {
                if (col >= numberOfColumns) break;
                
                bricks[row][col].draw (g, xOffset, yOffset);
                //count += 1;
            }
        }
        //out.println ("rendered " + count + " bricks");
    }
}
