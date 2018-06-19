package org.petehering.sandbox.platform;

import java.awt.image.BufferedImage;


public interface Tileset
{
    
    String getConfiguration ();

    boolean getBlocked(int id);

    BufferedImage getImage(int id);

    String getName(int id);
}
