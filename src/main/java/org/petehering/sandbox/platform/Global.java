package org.petehering.sandbox.platform;

import java.awt.Color;
import static java.awt.Color.*;

public interface Global
{
    String APP_TITLE = "Sandbox Platform 1.0";
    int APP_WIDTH = 640;
    int APP_HEIGHT = 480;
    String APP_ICON = "/big-smile.png";
    
    boolean DEBUG = true;
    Color BRICK_BLOCKED_COLOR = RED;
    Color BRICK_NORMAL_COLOR = GREEN;
    Color PLAYER_DEBUG_COLOR = CYAN;
    
    Color CLEAR_COLOR = BLACK;
    
    // keyboard input
    int UNKNOWN = -1;
    int UP = 0;
    int RIGHT = 1;
    int DOWN = 2;
    int LEFT = 3;
    int JUMP = 4;
    int SHOOT = 5;
    
    // level data
    String WHITESPACE = "\\s+";
    String BRICKS_FILE = "/bricks.txt";
    
    // player sprites
    String PLAYER_IMAGE = "/player.png";
    
    // player data
    float PLAYER_START_X = 48;
    float PLAYER_START_Y = 16;
    
    // player animations
    int IDLE = 0;
    int WALK_RIGHT = 1;
    int WALK_LEFT = 2;
    int RUN_RIGHT = 3;
    int RUN_LEFT = 4;
    int JUMP_RIGHT = 5;
    int JUMP_LEFT = 6;
    int FALL_RIGHT = 7;
    int FALL_LEFT = 8;
    int PLAYER_ANIMATION_COUNT = 9;
}
