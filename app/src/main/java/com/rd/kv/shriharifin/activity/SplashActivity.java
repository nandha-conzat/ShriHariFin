package com.rd.kv.shriharifin.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.rd.kv.shriharifin.R;

/**
 * Created by COBURG DESIGN on 26-03-2016.
 */
public class SplashActivity extends Activity {
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        try {
            new Handler().postDelayed(new Runnable() {
                /*
                 * Showing splash screen with a timer. This will be useful when
                 * you want to show case your app logo / company
                 */
                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    try {
                        synchronized (this) {
                            // Wait given period of time or exit on touch
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    // TODO Auto-generated method stub
                                    try {
                                        Thread.sleep(0);
                                    } catch (InterruptedException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }
                                }
                            }).start();
                            wait(1000);
                        }
                    } catch (InterruptedException ex) {
                    }

                    finish();

                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(i);
                }

            }, SPLASH_TIME_OUT);
        } catch (OutOfMemoryError e) {
            Toast.makeText(
                    getApplicationContext(),
                    "Your phone memory is too low, free your cache memory and start the application",
                    Toast.LENGTH_LONG).show();
        }

    }
}
