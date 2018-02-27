package com.example.mette.lockscreenapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
    Button unlock_screen;
    EditText enter_code;
    TextView display_success;

   /* @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*super.onAttachedToWindow();
        this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);*/
        super.onCreate(savedInstanceState);

        //Set up our Lockscreen
        makeFullScreen();
        startService(new Intent(this, com.example.mette.lockscreenapplication.LockScreenService.class));

        setContentView(R.layout.activity_main);

        unlock_screen = (Button)findViewById(R.id.button_unlock);
        enter_code = (EditText)findViewById(R.id.enter_code);
        display_success = (TextView)findViewById(R.id.display_success);

        unlock_screen.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String entered_code = enter_code.getText().toString();
                String code = com.example.mette.lockscreenapplication.data.Credentials.password();
                if (entered_code.equals(code)) {
                    Toast.makeText(MainActivity.this, "Runs correct version", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }


    /**
     * A simple method that sets the screen to fullscreen.  It removes the Notifications bar,
     *   the Actionbar and the virtual keys (if they are on the phone)
     */
    public void makeFullScreen() {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(Build.VERSION.SDK_INT < 19) { //View.SYSTEM_UI_FLAG_IMMERSIVE is only on API 19+
            this.getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        } else {
            this.getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE);
        }
    }

    @Override
    public void onBackPressed() {
        return; //Do nothing!
    }


    public void unlockScreen(View view) {
        //Instead of using finish(), this totally destroys the process
        android.os.Process.killProcess(android.os.Process.myPid());
    }

   /* @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_HOME)
        {
            Log.i("Home Button","Clicked");
        }
        if(keyCode==KeyEvent.KEYCODE_BACK)
        {
            finish();
        }
        return false;
    }*/
}