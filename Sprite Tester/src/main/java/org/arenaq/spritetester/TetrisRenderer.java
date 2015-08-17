package org.arenaq.spritetester;

import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by arenaq on 6.5.2014.
 */
public class TetrisRenderer implements GLSurfaceView.Renderer {
    private long loopStart = 0;
    private long loopEnd = 0;
    private long loopRunTime = 0;

    private float x = 0.5f;
    private float y = 0.5f;

    private Tetris tetris = new Tetris();

    class Tetris {

        FloatBuffer vertexBuffer;
        ByteBuffer indexBuffer;

        float vertices[] = {
                0.0f, 0.0f, 0.0f,
                0.1f, 0.0f, 0.0f,
                0.1f, 0.1f, 0.0f,
                0.0f, 0.1f, 0.0f,
        };

        byte indices[] = {
                0,1,2,
                0,2,3,
        };

        Tetris() {
            ByteBuffer byteBuf = ByteBuffer.allocateDirect(vertices.length * 4);
            byteBuf.order(ByteOrder.nativeOrder());
            vertexBuffer = byteBuf.asFloatBuffer();
            vertexBuffer.put(vertices);
            vertexBuffer.position(0);

            indexBuffer = ByteBuffer.allocateDirect(indices.length);
            indexBuffer.put(indices);
            indexBuffer.position(0);
        }

        public void draw(GL10 gl) {
            gl.glFrontFace(GL10.GL_CCW);
            gl.glEnable(GL10.GL_CULL_FACE);
            gl.glCullFace(GL10.GL_BACK);

            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

            gl.glColor4f(1f, 1f, 1f, 1f);
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
            gl.glDrawElements(GL10.GL_TRIANGLES, indices.length,
                    GL10.GL_UNSIGNED_BYTE, indexBuffer);

            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

            gl.glDisable(GL10.GL_CULL_FACE);
        }
    }

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

        //drawBackground(gl);
        drawTetris(gl);
        calculateTetris();

        // zde se bude volat ve�ker� dal�� vykreslov�n� hry
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

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
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
    }

    private void drawTetris(GL10 gl) {
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glTranslatef(x, y, 0f);

        tetris.draw(gl);

        gl.glPopMatrix();
        gl.glLoadIdentity();
    }

    private void calculateTetris() {
        switch (SFEngine.princeAction) {
            case SFEngine.PRINCE_LEFT:
                x -= 0.005f;
                break;
            case SFEngine.PRINCE_RIGHT:
                x += 0.005f;
                break;
            case SFEngine.PRINCE_UP:
                y += 0.005f;
                break;
            case SFEngine.PRINCE_DOWN:
                y -= 0.005f;
                break;
        }
    }
}
