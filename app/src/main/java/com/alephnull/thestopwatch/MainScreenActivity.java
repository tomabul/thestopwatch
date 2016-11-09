package com.alephnull.thestopwatch;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainScreenActivity extends AppCompatActivity {

    private Button btnRestart;
    private Button btnReset;
    private Button btnStartStop;

    private TextView txtMinutes;
    private TextView txtSeconds;
    private TextView txtMiliseconds;

    private Handler customHandler = new Handler();

    private long startTime = 0L;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        btnRestart = (Button) findViewById(R.id.btnRestart);
        btnReset = (Button) findViewById(R.id.btnReset);
        btnStartStop = (Button) findViewById(R.id.btnStartStop);

        txtMinutes = (TextView) findViewById(R.id.txtMinutes);
        txtSeconds = (TextView) findViewById(R.id.txtSeconds);
        txtMiliseconds = (TextView) findViewById(R.id.txtMiliseconds);

        btnRestart.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                customHandler.postDelayed(updateTimerThread, 0);
            }
        });

        btnStartStop.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);
            }
        });

    }

    private Runnable updateTimerThread = new Runnable() {
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updatedTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            txtMinutes.setText(mins);
            txtSeconds.setText(String.format("%02d", secs));
            txtMiliseconds.setText(String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
        }
    };
}
