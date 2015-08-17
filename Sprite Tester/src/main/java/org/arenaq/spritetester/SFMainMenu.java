package org.arenaq.spritetester;

import android.app.Activity; 
import android.content.Intent; 
import android.os.Bundle; 
import android.view.View; 
import android.view.View.OnClickListener; 
import android.widget.ImageButton; 

public class SFMainMenu extends Activity { 
    /** Called when the activity is first created. */ 
    @Override 
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        setContentView(R.layout.main); 

        /** spus� podbarvuj�c� hudbu */ 
        SFEngine.musicThread = new Thread() { 
            public void run() { 
                Intent bgmusic = 
                    new Intent(getApplicationContext(), SFMusic.class); 
                startService(bgmusic); 
                SFEngine.context = getApplicationContext(); 
            } 
        }; 
        SFEngine.musicThread.start(); 

        final SFEngine engine = new SFEngine();

        /** nastav volby pro tla��tka nab�dky */ 
        ImageButton start = (ImageButton)findViewById(R.id.btnStart); 
        ImageButton exit = (ImageButton)findViewById(R.id.btnExit); 

        start.getBackground().setAlpha(SFEngine.MENU_BUTTON_ALPHA); 
        start.setHapticFeedbackEnabled(SFEngine.HAPTIC_BUTTON_FEEDBACK); 

        exit.getBackground().setAlpha(SFEngine.MENU_BUTTON_ALPHA); 
        exit.setHapticFeedbackEnabled(SFEngine.HAPTIC_BUTTON_FEEDBACK); 

        start.setOnClickListener(new OnClickListener() { 
            @Override 
            public void onClick(View v) { 
                /** spus� hru!!!! */
                Intent game = new Intent(getApplicationContext(), SFGame.class); 
                SFMainMenu.this.startActivity(game);             	
            } 
        }); 

        exit.setOnClickListener(new OnClickListener() { 
            @Override 
            public void onClick(View v) { 
                boolean clean = false; 
                clean = engine.onExit(v); 
                if (clean) { 
                    int pid = android.os.Process.myPid(); 
                    android.os.Process.killProcess(pid); 
                } 
            } 
        }); 
    } 
}
