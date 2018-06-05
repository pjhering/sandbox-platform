package org.petehering.sandbox.platform;

import static java.awt.event.KeyEvent.*;
import org.petehering.sandbox.DesktopInput;
import static org.petehering.sandbox.platform.Global.*;

class PlatformInput extends DesktopInput
{

    public PlatformInput ()
    {
        super (6);
    }

    @Override
    public int keyToIndex (int keyCode)
    {
        switch (keyCode)
        {
            case VK_UP:
            case VK_E:
            case VK_I:
                return UP;
                
            case VK_RIGHT:
            case VK_F:
            case VK_L:
                return RIGHT;
                
            case VK_DOWN:
            case VK_D:
            case VK_K:
                return DOWN;
                
            case VK_LEFT:
            case VK_S:
            case VK_J:
                return LEFT;
                
            case VK_SPACE:
                return JUMP;
                
            case VK_ENTER:
                return SHOOT;
                
            default:
                return UNKNOWN;
        }
    }
}
