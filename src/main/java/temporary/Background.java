package temporary;

import java.awt.image.BufferedImage;
import static java.util.Objects.requireNonNull;

public class Background
{
    private final BufferedImage image;
    private final String configuration;
    
    public Background (String config, BufferedImage image)
    {
        this.configuration = requireNonNull (config);
        this.image = requireNonNull (image);
    }
}
