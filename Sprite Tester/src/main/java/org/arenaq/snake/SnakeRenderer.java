package org.arenaq.snake;

import android.opengl.GLSurfaceView;

import org.arenaq.snake.objects.Snake;
import org.arenaq.spritetester.SFEngine;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by arenaq on 16.8.2015.
 */
public class SnakeRenderer implements GLSurfaceView.Renderer {
    private Snake snake = new Snake();

    private long loopStart = 0;
    private long loopEnd = 0;
    private long loopRunTime = 0;

    @Override
    public void onDrawFrame(GL10 gl) {
        loopStart = System.currentTimeMillis();
        try {
            if (loopRunTime < SFEngine.GAME_THREAD_FPS_SLEEP) {
                Thread.sleep(SFEngine.GAME_THREAD_FPS_SLEEP - loopRunTime);
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }

        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        drawSnake(gl);

        // zde se bude volat ve?ker? dal?? vykreslov?n? hry
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);

        loopEnd = System.currentTimeMillis();
        loopRunTime = ((loopEnd - loopStart));
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        gl.glOrthof(0f, 1f, 0f, 1f, -1f, 1f);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        gl.glEnable(GL10.GL_TEXTURE_2D);
        gl.glClearDepthf(1.0f);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);

        snake.loadTexture(gl, Engine.SNAKE_SPRITE_SHEET, Engine.context);
    }

    private void drawSnake(GL10 gl) {
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glTranslatef(snake.x, snake.y, 0f);

        gl.glMatrixMode(GL10.GL_TEXTURE);
        gl.glLoadIdentity();

        switch (Engine.action) {
            case Engine.SNAKE_RIGHT:
                snake.x += snake.speed;
                snake.action = Engine.SNAKE_RIGHT;
                break;
            case Engine.SNAKE_LEFT:
                snake.x -= snake.speed;
                snake.action = Engine.SNAKE_LEFT;
                break;
            case Engine.SNAKE_UP:
                snake.y += snake.speed;
                snake.action = Engine.SNAKE_UP;
                break;
            case Engine.SNAKE_DOWN:
                snake.y -= snake.speed;
                snake.action = Engine.SNAKE_DOWN;
                break;
        }

        gl.glScalef(0.05f, 0.05f, 0.05f);
        snake.draw(gl);
        gl.glPopMatrix();
        gl.glLoadIdentity();
    }
}
