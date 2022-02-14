package com.knowtest.lealtest.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.knowtest.lealtest.R;

import java.util.Timer;
import java.util.TimerTask;

public class WaitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wait);
        this.getSupportActionBar().hide();
        Timer timer = new Timer();
        MyTimerTask myTask = new MyTimerTask();
        timer.schedule(myTask, 3000, 3000);
    }


    public class MyTimerTask extends TimerTask {
        public void run() {
            finish();

        }

    }
}