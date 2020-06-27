package com.example.hp.franchisehelper;

import android.content.Intent;

import android.os.Build;
import android.os.Handler;

import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ProgressBar;


public class Splash extends AppCompatActivity {


    ProgressBar mProgress;
    private static int SPLASH_TIME = 4000; //This is 4 seconds



    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        mProgress=findViewById(R.id.splash_screen_progress_bar);
        mProgress.setScaleY(5F);


        new Thread(new Runnable() {
            public void run() {
                doWork();
                startApp();
                finish();
            }
        }).start();

    }
    private void doWork() {
        for (int progress=0; progress<100; progress+=10) {
            try {
                Thread.sleep(500);
                mProgress.setProgress(progress);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    private void startApp() {
        Intent intent = new Intent(Splash.this, SignUpActivity.class);
        startActivity(intent);
    }


}