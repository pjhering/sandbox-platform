package org.petehering.sandbox.platform;

import static java.awt.event.KeyEvent.*;
import org.petehering.sandbox.DesktopInput;

public class TestInput extends DesktopInput
{
    public static final int UNUSED = -1;
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int ESCAPE = 4;
    public static final int ENTER = 5;

    public TestInput ()
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
                
            case VK_DOWN:
            case VK_D:
            case VK_K:
                return DOWN;
                
            case VK_LEFT:
            case VK_S:
            case VK_J:
                return LEFT;
                
            case VK_RIGHT:
            case VK_F:
            case VK_L:
                return RIGHT;
                
            case VK_ESCAPE:
                return ESCAPE;
                
            case VK_ENTER:
                return ENTER;
                
            default:
                return UNUSED;
        }
    }
}
