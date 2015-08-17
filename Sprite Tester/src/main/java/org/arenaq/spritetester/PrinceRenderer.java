package org.arenaq.spritetester;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by arenaq on 6.5.2014.
 */
public class PrinceRenderer implements GLSurfaceView.Renderer {
    private Background background = new Background();
    private SFPrince prince = new SFPrince();
    private Wolf wolf = new Wolf();
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

        drawBackground(gl);
        //drawPrince(gl);
        drawWolf(gl);

        // zde se bude volat ve�ker� dal�� vykreslov�n� hry
        gl.glEnable(GL10.GL_BLEND);
        gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

        loopEnd = System.currentTimeMillis();
        loopRunTime = ((loopEnd - loopStart));
    }

    private void drawWolf(GL10 gl) {
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glTranslatef(wolf.x, 0.4f, 0f);
        gl.glScalef(0.15f, 0.15f, 1f);

        if (wolf.frames > 10) {
            wolf.frames = 0;
            wolf.radek++;
            wolf.x += wolf.speed;
            if (wolf.x > 1.0f) wolf.x = 0;
        } else {
            wolf.frames++;
        }

        if (wolf.radek > 5) {
            wolf.radek = 0;
        }

        gl.glTranslatef(0f, 0f, 0f);
        gl.glMatrixMode(GL10.GL_TEXTURE);
        //gl.glScalef(512f, 512f, 1f);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, wolf.radek * 51 / 512f, 0.0f);
        //gl.glTranslatef(earth.sloupec*128f, earth.radek*128f, 0.0f);

        wolf.draw(gl);
        gl.glPopMatrix();
        gl.glLoadIdentity();
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

        background.loadTexture(gl, SFEngine.PRINCE_BACKGROUND, SFEngine.context);
        prince.loadTexture(gl, SFEngine.PRINCE_SPRITES, SFEngine.context);
        wolf.loadTexture(gl, R.drawable.wolf, SFEngine.context);
    }

    private void drawBackground(GL10 gl) {gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();

        gl.glMatrixMode(GL10.GL_TEXTURE);
        gl.glLoadIdentity();

        background.draw(gl);
        gl.glPopMatrix();
        gl.glLoadIdentity();
    }

    private void drawPrince(GL10 gl) {
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glTranslatef(prince.x, prince.y, 0f);
        gl.glScalef(0.15f, 0.15f, 1f); // 69 x 87

        gl.glMatrixMode(GL10.GL_TEXTURE);
        gl.glLoadIdentity();

        switch (SFEngine.princeAction) {
            case SFEngine.PRINCE_RIGHT:
                gl.glScalef(-1f, 1f, 1f);
                gl.glTranslatef(-prince.sloupec * 69 / 1024f, 0.0f, 0.0f);

                if (prince.action != SFEngine.PRINCE_RIGHT) {
                    prince.action = SFEngine.PRINCE_RIGHT;
                    prince.frames = prince.sloupec = 0;
                }

                if (prince.frames > 5) {
                    prince.frames = 0;
                    prince.sloupec++;
                    if (SFEngine.princeAction != 0) prince.x += prince.speed;
                } else {
                    prince.frames++;
                }

                if (prince.sloupec > 12) {
                    prince.sloupec = 5;
                }
                break;
            case SFEngine.PRINCE_LEFT:
                gl.glTranslatef(prince.sloupec * 69 / 1024f, 0.0f, 0.0f);

                if (prince.action != SFEngine.PRINCE_LEFT) {
                    prince.action = SFEngine.PRINCE_LEFT;
                    prince.frames = prince.sloupec = 0;
                }
                if (prince.frames > 5) {
                    prince.frames = 0;
                    prince.sloupec++;
                    if (SFEngine.princeAction != 0) prince.x -= prince.speed;
                } else {
                    prince.frames++;
                }

                if (prince.sloupec > 12) {
                    prince.sloupec = 5;
                }
                break;
            case SFEngine.PRINCE_UP:
                if (prince.action == SFEngine.PRINCE_RIGHT) {
                    SFEngine.princeAction = SFEngine.PRINCE_LONG_JUMP_RIGHT;
                } else if (prince.action == SFEngine.PRINCE_LEFT) {
                    SFEngine.princeAction = SFEngine.PRINCE_LONG_JUMP_LEFT;
                }
                prince.sloupec = 0;
                prince.radek = 0;
                break;
            case SFEngine.PRINCE_LONG_JUMP_RIGHT:
                gl.glScalef(-1f, 1f, 1f);
                gl.glTranslatef(-prince.sloupec * 102 / 1024f, (87+prince.radek*89) / 1024f, 0.0f);
                if (prince.frames > 5) {
                    prince.frames = 0;
                    prince.sloupec++;
                    prince.x += prince.speed;
                    // TODO prince.y increment
                } else {
                    prince.frames++;
                }

                if (prince.sloupec > 0 && prince.radek > 0) {
                    SFEngine.princeAction = SFEngine.PRINCE_RIGHT;
                    prince.sloupec = 5;
                    prince.radek = 0;
                } else if (prince.sloupec > 9) {
                    prince.sloupec = 0;
                    prince.radek = 1;
                }
                break;
            case SFEngine.PRINCE_LONG_JUMP_LEFT:
                gl.glTranslatef(prince.sloupec * 102 / 1024f, (87+prince.radek*89) / 1024f, 0.0f);
                if (prince.frames > 5) {
                    prince.frames = 0;
                    prince.sloupec++;
                    prince.x -= prince.speed;
                    // TODO prince.y increment
                } else {
                    prince.frames++;
                }

                if (prince.sloupec > 0 && prince.radek > 0) {
                    SFEngine.princeAction = SFEngine.PRINCE_LEFT;
                    prince.sloupec = 5;
                    prince.radek = 0;
                } else if (prince.sloupec > 9) {
                    prince.sloupec = 0;
                    prince.radek = 1;
                }
                break;
        }

        //gl.glTranslatef(earth.sloupec*0.25f, earth.radek*0.25f, 0.0f);

        prince.draw(gl);
        gl.glPopMatrix();
        gl.glLoadIdentity();
    }
}
