package com.example.realtmsapp_camel;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Chronometer chronometer;
    private long PauseOffSet = 0;
    private boolean isPlaying = false;
    private ToggleButton toggleButton;
    private Button reset_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Let's select our element
        chronometer = findViewById(R.id.chronometer);
        toggleButton = findViewById(R.id.Toggle);
        reset_btn = findViewById(R.id.reset_btn);
        // now we need to customize our toggle button
        toggleButton.setText(null);
        toggleButton.setTextOn(null);
        toggleButton.setTextOff(null);
        //the event listener will automaticly generated by android studio
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                //now let's code our stopwatch
                //b is the state of our button, it will be true if we pressed the button otherwise it will be false
                if(b){
                    chronometer.setBase(SystemClock.elapsedRealtime()- PauseOffSet);
                    chronometer.start();
                    isPlaying = true;
                }else{
                    chronometer.stop();
                    PauseOffSet = SystemClock.elapsedRealtime()- chronometer.getBase();
                    isPlaying = false;
                }
            }
        });

        //now we need to add the reset function of our button
        // we should check if the chronometre is actually workin to be able to reset otherwise we can't
        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPlaying){
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    PauseOffSet = 0;
                    chronometer.start();
                    isPlaying = true;
                    //now everything should work let's run our app and see it
                }
            }
        });
    }
}