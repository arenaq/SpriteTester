package org.arenaq.snake;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import org.arenaq.spritetester.R;

/**
 * Created by arenaq on 17.8.2015.
 */
public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Engine.display = ((WindowManager)
                getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashscreen);

        /* launch splash screen and main menu screen in delayed thread */
        new Handler().postDelayed(new Thread() {
            @Override
            public void run() {
                Intent mainMenu =
                        new Intent(Main.this, Menu.class);
                Main.this.startActivity(mainMenu);
                Main.this.finish();
                overridePendingTransition(R.layout.fadein,R.layout.fadeout);
            }
        }, Engine.GAME_THREAD_DELAY);
    }
}
