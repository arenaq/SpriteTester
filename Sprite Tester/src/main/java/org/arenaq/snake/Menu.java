package org.arenaq.snake;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import org.arenaq.spritetester.R;

public class Menu extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Engine.context = getApplicationContext();

        /** setting up menu options */
        ImageButton start = (ImageButton)findViewById(R.id.btnStart);
        ImageButton exit = (ImageButton)findViewById(R.id.btnExit);

        start.getBackground().setAlpha(Engine.MENU_BUTTON_ALPHA);
        start.setHapticFeedbackEnabled(Engine.HAPTIC_BUTTON_FEEDBACK);

        exit.getBackground().setAlpha(Engine.MENU_BUTTON_ALPHA);
        exit.setHapticFeedbackEnabled(Engine.HAPTIC_BUTTON_FEEDBACK);

        start.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                /** launch the game */
                Intent game = new Intent(getApplicationContext(), Game.class);
                Menu.this.startActivity(game);
            }
        });

        exit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
            }
        });
    }
}
