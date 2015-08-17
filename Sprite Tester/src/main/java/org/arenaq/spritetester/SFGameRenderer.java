package org.arenaq.spritetester;

import javax.microedition.khronos.egl.EGLConfig; 
import javax.microedition.khronos.opengles.GL10; 
import android.opengl.GLSurfaceView.Renderer; 

public class SFGameRenderer implements Renderer { 
    private SFBackground background = new SFBackground(); 
    private SFBackground background2 = new SFBackground();
    private SFGoodGuy player1 = new SFGoodGuy();
    private SFEarth earth = new SFEarth();
    private SFPrince prince = new SFPrince();

    private int goodGuyBankFrames = 0; 
    private long loopStart = 0; 
    private long loopEnd = 0; 
    private long loopRunTime = 0; 

    private float bgScroll1; 
    private float bgScroll2; 

    private void scrollBackground1(GL10 gl) { 
        if (bgScroll1 == Float.MAX_VALUE) { 
            bgScroll1 = 0f; 
        }
        
        gl.glMatrixMode(GL10.GL_MODELVIEW); 
        gl.glLoadIdentity(); 
        gl.glPushMatrix(); 
        gl.glScalef(1f, 1f, 1f); 
        gl.glTranslatef(0f, 0f, 0f);
        
        gl.glMatrixMode(GL10.GL_TEXTURE); 
        gl.glLoadIdentity(); 
        gl.glTranslatef(0.0f, bgScroll1, 0.0f); // posouv�n� textury
        
        background.draw(gl); 
        gl.glPopMatrix(); 
        bgScroll1 += SFEngine.SCROLL_BACKGROUND_1; 
        gl.glLoadIdentity();         
    } 

    private void scrollBackground2(GL10 gl) { 
        if (bgScroll2 == Float.MAX_VALUE) { 
            bgScroll2 = 0f; 
        }
        
        gl.glMatrixMode(GL10.GL_MODELVIEW); 
        gl.glLoadIdentity(); 
        gl.glPushMatrix(); 
        gl.glScalef(.5f, 1f, 1f); 
        gl.glTranslatef(1.5f, 0f, 0f);         

        gl.glMatrixMode(GL10.GL_TEXTURE); 
        gl.glLoadIdentity(); 
        gl.glTranslatef(0.0f, bgScroll2, 0.0f); 
        background2.draw(gl); 
        gl.glPopMatrix(); 
        bgScroll2 += SFEngine.SCROLL_BACKGROUND_2; 
        gl.glLoadIdentity(); 
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
        
        scrollBackground1(gl); 
        scrollBackground2(gl); 

        movePlayer1(gl);
        drawEarth(gl);
        drawPrince(gl);
        
        // zde se bude volat ve�ker� dal�� vykreslov�n� hry 
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

        background.loadTexture(gl, SFEngine.BACKGROUND_LAYER_ONE, 
                               SFEngine.context);
        background2.loadTexture(gl,SFEngine.BACKGROUND_LAYER_TWO, 
                                SFEngine.context);
        player1.loadTexture(gl, SFEngine.PLAYER_SHIP, SFEngine.context);
        earth.loadTexture(gl, SFEngine.EARTH_SPRITES, SFEngine.context);
        prince.loadTexture(gl, SFEngine.PRINCE_SPRITES, SFEngine.context);
    }

    private void drawPrince(GL10 gl) {
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glScalef(.25f, .25f, 1f);

        if (prince.frames > 10) {
            prince.frames = 0;
            prince.sloupec++;
        } else {
            prince.frames++;
        }

        if (prince.sloupec > 8) {
            prince.sloupec = 0;
        }

        gl.glTranslatef(1.0f, 0f, 0f);
        gl.glMatrixMode(GL10.GL_TEXTURE);
        gl.glLoadIdentity();
        //gl.glTranslatef(earth.sloupec*0.25f, earth.radek*0.25f, 0.0f);
        gl.glTranslatef(prince.sloupec/16f, 0.0f, 0.0f);

        prince.draw(gl);
        gl.glPopMatrix();
        gl.glLoadIdentity();
    }

    private void drawEarth(GL10 gl) {
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        gl.glPushMatrix();
        gl.glScalef(.25f, .25f, 1f);

        if (earth.frames > 10) {
            earth.frames = 0;
            earth.sloupec++;
        } else {
            earth.frames++;
        }

        if (earth.sloupec > 3) {
            earth.sloupec = 0;
            earth.radek++;
        }
        if (earth.radek > 2) earth.radek = 0;

        gl.glTranslatef(0f, 0f, 0f);
        gl.glMatrixMode(GL10.GL_TEXTURE);
        //gl.glScalef(512f, 512f, 1f);
        gl.glLoadIdentity();
        gl.glTranslatef(earth.sloupec/4f, earth.radek/4f, 0.0f);
        //gl.glTranslatef(earth.sloupec*128f, earth.radek*128f, 0.0f);

        earth.draw(gl);
        gl.glPopMatrix();
        gl.glLoadIdentity();
    }
    
    private void movePlayer1(GL10 gl) { 
        switch (SFEngine.playerFlightAction) { 
        case SFEngine.PLAYER_BANK_LEFT_1: 
            gl.glMatrixMode(GL10.GL_MODELVIEW); 
            gl.glLoadIdentity(); 
            gl.glPushMatrix(); 
            gl.glScalef(.25f, .25f, 1f); 

            if (goodGuyBankFrames < SFEngine.PLAYER_FRAMES_BETWEEN_ANI 
                && SFEngine.playerBankPosX > 0) { 
                SFEngine.playerBankPosX -= SFEngine.PLAYER_BANK_SPEED; 
                gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f); 
                gl.glMatrixMode(GL10.GL_TEXTURE); 
                gl.glLoadIdentity(); 
                gl.glTranslatef(0.75f, 0.0f, 0.0f); 
                goodGuyBankFrames += 1;
            } 
            else if (goodGuyBankFrames >= SFEngine.PLAYER_FRAMES_BETWEEN_ANI 
                     && SFEngine.playerBankPosX > 0) { 
                SFEngine.playerBankPosX -= SFEngine.PLAYER_BANK_SPEED; 
                gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f); 
                gl.glMatrixMode(GL10.GL_TEXTURE); 
                gl.glLoadIdentity(); 
                gl.glTranslatef(0.0f,0.25f, 0.0f); 
            }
            else  { 
                gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f); 
                gl.glMatrixMode(GL10.GL_TEXTURE); 
                gl.glLoadIdentity(); 
                gl.glTranslatef(0.0f, 0.0f, 0.0f); 
            } 

            player1.draw(gl); 
            gl.glPopMatrix(); 
            gl.glLoadIdentity(); 
            break;                          
        case SFEngine.PLAYER_BANK_RIGHT_1: 
            gl.glMatrixMode(GL10.GL_MODELVIEW); 
            gl.glLoadIdentity(); 
            gl.glPushMatrix(); 
            gl.glScalef(.25f, .25f, 1f); 

            if (goodGuyBankFrames < SFEngine.PLAYER_FRAMES_BETWEEN_ANI 
                && SFEngine.playerBankPosX < 3) {
                SFEngine.playerBankPosX += SFEngine.PLAYER_BANK_SPEED; 
                gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f); 
                gl.glMatrixMode(GL10.GL_TEXTURE); 
                gl.glLoadIdentity(); 
                gl.glTranslatef(0.25f,0.0f, 0.0f); 
                goodGuyBankFrames += 1;             	
            }
            else if (goodGuyBankFrames >= SFEngine.PLAYER_FRAMES_BETWEEN_ANI 
                    && SFEngine.playerBankPosX < 3) {
                SFEngine.playerBankPosX += SFEngine.PLAYER_BANK_SPEED; 
                gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f); 
                gl.glMatrixMode(GL10.GL_TEXTURE); 
                gl.glLoadIdentity(); 
                gl.glTranslatef(0.50f,0.0f, 0.0f); 
            } 
            else { 
                gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f); 
                gl.glMatrixMode(GL10.GL_TEXTURE); 
                gl.glLoadIdentity(); 
                gl.glTranslatef(0.0f,0.0f, 0.0f); 
            } 

            player1.draw(gl); 
            gl.glPopMatrix(); 
            gl.glLoadIdentity(); 
            break; 
        case SFEngine.PLAYER_RELEASE: 
            gl.glMatrixMode(GL10.GL_MODELVIEW); 
            gl.glLoadIdentity(); 
            gl.glPushMatrix(); 
            gl.glScalef(.25f, .25f, 1f); 
            gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f); 
            gl.glMatrixMode(GL10.GL_TEXTURE); 
            gl.glLoadIdentity(); 
            gl.glTranslatef(0.0f,0.0f, 0.0f); 
            player1.draw(gl); 
            gl.glPopMatrix(); 
            gl.glLoadIdentity(); 
            goodGuyBankFrames += 1; 
            break; 
        default: 
            gl.glMatrixMode(GL10.GL_MODELVIEW); 
            gl.glLoadIdentity(); 
            gl.glPushMatrix(); 
            gl.glScalef(.25f, .25f, 1f); 
            gl.glTranslatef(SFEngine.playerBankPosX, 0f, 0f); 
            gl.glMatrixMode(GL10.GL_TEXTURE); 
            gl.glLoadIdentity(); 
            gl.glTranslatef(0.0f,0.0f, 0.0f); 
            player1.draw(gl); 
            gl.glPopMatrix(); 
            gl.glLoadIdentity(); 
            break; 
        } 
    }     
}
