package org.arenaq.spritetester;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;

public class SFGame extends Activity { 
    private SFGameView gameView; 

    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        gameView = new SFGameView(this);
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

        SFEngine.x = x;
        SFEngine.y = y;

    	// int height = SFEngine.display.getHeight() / 4;
    	// int playableArea = SFEngine.display.getHeight() - height;

    	// if (y > playableArea) {
            switch (event.getAction()) { 
            case MotionEvent.ACTION_DOWN: 
                if (x < SFEngine.display.getWidth() / 2) { 
                    SFEngine.princeAction = SFEngine.PRINCE_LEFT;
                } else { 
                    SFEngine.princeAction = SFEngine.PRINCE_RIGHT;
                }
                if (y < SFEngine.display.getHeight() / 3) {
                    SFEngine.princeAction = SFEngine.PRINCE_UP;
                } else if (y > SFEngine.display.getHeight() * 2 / 3) {
                    SFEngine.princeAction = 0;
                }
                break; 
            case MotionEvent.ACTION_UP: 
            	//SFEngine.princeAction = 0;
                Log.e("ACTION_UP", "princeAction = 0;");
                break; 
            } 
    	// }
    	
        return false; 
    }     
}
