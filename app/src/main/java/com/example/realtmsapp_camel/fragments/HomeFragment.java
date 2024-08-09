package com.example.realtmsapp_camel.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import com.example.realtmsapp_camel.DatabaseHelper;
import com.example.realtmsapp_camel.R;
import com.google.android.material.button.MaterialButton;


import java.text.MessageFormat;
import java.util.Locale;

public class HomeFragment extends Fragment {

    public HomeFragment(){

    }
    public TextView TimerView;
    MaterialButton reset, start, stop, save, another;
    EditText Editprocess, Editperson, Editmodel;
    int seconds, minutes, milliSeconds;
    long milliseconds, startTime, timeBuff, updateTime =0L;
    private Handler handler;
    private DatabaseHelper databaseHelper;
    private int trialCount;
    private int trialSetCount;

    private final Runnable runnable= new Runnable() {
        @Override
        public void run() { //for timer di ako gumawa neto bossing
            milliseconds = SystemClock.uptimeMillis() - startTime;
            updateTime = timeBuff + milliseconds;
            seconds = (int)(updateTime/1000);
            minutes = seconds/60;
            seconds = seconds%60;
            milliSeconds= (int) (updateTime %1000);

            TimerView.setText(MessageFormat.format("{0}:{1}:{2}", minutes, String.format(Locale.getDefault(), "%02d", seconds), String.format(Locale.getDefault(), "%02d", milliSeconds)));
            handler.postDelayed(this, 0);
        }
    };
    @SuppressLint("SetTextI18n")

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        TimerView= view.findViewById(R.id.TimerView);
        Editprocess = view.findViewById(R.id.Process);
        Editperson = view.findViewById(R.id.Person);
        Editmodel = view.findViewById(R.id.Model);

        reset= view.findViewById(R.id.Reset);
        start= view.findViewById(R.id.Start);
        stop= view.findViewById(R.id.Stop);
        save= view.findViewById(R.id.Save);
        another = view.findViewById(R.id.Another);


        databaseHelper = new DatabaseHelper(getActivity());
        handler = new Handler(Looper.getMainLooper());
        trialCount = 1;
        trialSetCount = 1;

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                reset.setEnabled(false);
                stop.setEnabled(true);
                start.setEnabled(false);


            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeBuff+= milliseconds;
                handler.removeCallbacks(runnable);
                reset.setEnabled(true);
                stop.setEnabled(false);
                start.setEnabled(true);
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                //reset all values
                milliseconds = 0L;
                startTime=0L;
                timeBuff=0L;
                updateTime=0L;
                seconds=0;
                minutes=0;
                milliSeconds=0;
                TimerView.setText("00:00:00");
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //para kunin mga values sa text box
                String timerValue = TimerView.getText().toString();
                String process = Editprocess.getText().toString();
                String person = Editperson.getText().toString();
                String model = Editmodel.getText().toString();

                //para mag require ng input sa fields
                if (process.isEmpty() || person.isEmpty() || model.isEmpty()) {
                    Toast.makeText(getActivity(), "Process, Person, and Model fields must be filled", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (trialCount <= 10) {
                    if (databaseHelper.insertTrial(process, person, model, timerValue, trialCount, trialSetCount)) {
                        trialCount++;
                        Toast.makeText(getActivity(), "Time saved!", Toast.LENGTH_SHORT).show();
                        if (trialCount > 10) { //counter for trials para titigil pag lagpas
                            disableEditTexts();
                        }
                    } else { //error identifier if hindi nagsasave (ayusin if ever)
                        Toast.makeText(getActivity(), "Failed to save time", Toast.LENGTH_SHORT).show();
                    }
                } else { //warning sa user if tapos na yung 10 trials
                    Toast.makeText(getActivity(), "Trial limit reached. Reset to save new trials.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        another.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    milliseconds = 0L;
                    startTime=0L;
                    timeBuff=0L;
                    updateTime=0L;
                    seconds=0;
                    minutes=0;
                    milliSeconds=0;
                    trialCount = 1; //eto para reset to first trial next set
                    trialSetCount++; //eto para mag skip if ever need mo na bago process
                    TimerView.setText("00:00:00"); //reset all values
                    Editprocess.setText("");
                    Editperson.setText("");
                    Editmodel.setText("");
                    enableEditTexts(); // para pwede na palitan text
            }
        });
        TimerView.setText("00:00:00"); //para may view agad kahit kakastart lang
        return view;
    }

    // disable text para di mabago habang nagkuha time
    private void disableEditTexts() {
        Editprocess.setEnabled(false);
        Editperson.setEnabled(false);
        Editmodel.setEnabled(false);
    }

    // enable para pwede na ulit
    private void enableEditTexts() {
        Editprocess.setEnabled(true);
        Editperson.setEnabled(true);
        Editmodel.setEnabled(true);
    }

}