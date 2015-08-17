package org.arenaq.snake;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by arenaq on 17.8.2015.
 */
public class GameView extends GLSurfaceView {
    private SnakeRenderer renderer;

    public GameView(Context context) {
        super(context);
        renderer = new SnakeRenderer();
        this.setRenderer(renderer);
    }
}
