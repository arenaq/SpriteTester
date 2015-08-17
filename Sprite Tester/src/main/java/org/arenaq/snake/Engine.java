package org.arenaq.snake;

import android.content.Context;
import android.view.Display;

import org.arenaq.spritetester.R;

/**
 * Created by arenaq on 16.8.2015.
 */
public class Engine {

    public static final int GAME_THREAD_DELAY = 1000;
    public static final int MENU_BUTTON_ALPHA = 0;
    public static final boolean HAPTIC_BUTTON_FEEDBACK = true;
    public static final int SNAKE_SPRITE_SHEET = R.drawable.snake_sprite_sheet;

    public static Context context;
    public static Display display;

    public static final int SNAKE_UP = 1;
    public static final int SNAKE_DOWN = 2;
    public static final int SNAKE_LEFT = 3;
    public static final int SNAKE_RIGHT = 4;

    public static int action = 0;
    public static float x, y;
}
