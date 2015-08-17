package org.arenaq.spritetester;

import android.app.Activity;
import android.content.Context;
import android.content.Intent; 
import android.os.Bundle;
import android.os.Handler; 
import android.view.WindowManager;

public class StarfighterActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        SFEngine.display = ((WindowManager)   
            getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
    	
        super.onCreate(savedInstanceState);
        
        /* zobraz �vodn� obrazovku z rozvr�en� */ 
        setContentView(R.layout.splashscreen);

        /* spus� �vodn� obrazovku a hlavn� nab�dku v pozdr�en�m vl�knu */  
        new Handler().postDelayed(new Thread() { 
            @Override 
            public void run() { 
                Intent mainMenu = 
                    new Intent(StarfighterActivity.this, SFMainMenu.class); 
                StarfighterActivity.this.startActivity(mainMenu);         
                StarfighterActivity.this.finish();
                overridePendingTransition(R.layout.fadein,R.layout.fadeout); 
            } 
        }, SFEngine.GAME_THREAD_DELAY);
    }
}
