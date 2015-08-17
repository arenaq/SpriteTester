package org.arenaq.snake;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by arenaq on 16.8.2015.
 */
public class Game extends Activity {
    private GameView gameView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.onPause();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        Engine.x = x;
        Engine.y = y;

        // int height = Engine.display.getHeight() / 4;
        // int playableArea = Engine.display.getHeight() - height;

        // if (y > playableArea) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (x < Engine.display.getWidth() / 2) {
                    if (Engine.action != Engine.SNAKE_RIGHT) Engine.action = Engine.SNAKE_LEFT;
                } else {
                    if (Engine.action != Engine.SNAKE_LEFT) Engine.action = Engine.SNAKE_RIGHT;
                }
                if (y < Engine.display.getHeight() / 2) {
                    if (Engine.action != Engine.SNAKE_DOWN) Engine.action = Engine.SNAKE_UP;
                } else {
                    if (Engine.action != Engine.SNAKE_UP) Engine.action = Engine.SNAKE_DOWN;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        // }

        return false;
    }
}
