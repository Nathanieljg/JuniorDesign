package com.example.juniordesigntest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.invoke.ConstantCallSite;

public class AddTimerActivity extends AppCompatActivity {

    private static final long START_TIME_MILLI = 10000;

    private Button buttonReset;
    private Button buttonAddTimer;
    private NumberPicker hours;
    private NumberPicker minutes;
    private NumberPicker seconds;
    private EditText editAlarmName;
    private RadioGroup timerSelection;
    private TextView letterS;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_timer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        text_view_timer = findViewById(R.id.text_view_timer);
        buttonAddTimer = findViewById(R.id.button_add_timer);
//        buttonReset = findViewById(R.id.button_reset);

        hours = findViewById(R.id.number_picker_hours);
        String [] hoursValues = new String[100];
        for (int i=0; i < 100; i++) {
            hoursValues[i] = Integer.toString(i);
        }
        hours.setMinValue(0);
        hours.setMaxValue(99);
        hours.setDisplayedValues(hoursValues);
        hours.setWrapSelectorWheel(false);


        minutes = findViewById(R.id.number_picker_minutes);
        String [] minutesValues = new String[60];
        for (int i=0; i < 60; i++) {
            minutesValues[i] = Integer.toString(i);
        }
        minutes.setMinValue(0);
        minutes.setMaxValue(59);
        minutes.setDisplayedValues(minutesValues);
        minutes.setWrapSelectorWheel(false);

        seconds = findViewById(R.id.number_picker_seconds);
        String [] secondsValues = new String[60];
        for (int i=0; i < 60; i++) {
            secondsValues[i] = Integer.toString(i);
        }
        seconds.setMinValue(0);
        seconds.setMaxValue(59);
        seconds.setDisplayedValues(secondsValues);
        seconds.setWrapSelectorWheel(false);

        editAlarmName = findViewById(R.id.alarmNameEditText);

        buttonAddTimer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addAlarm();
            }
        });

        timerSelection = findViewById(R.id.radioGroup);
        letterS = findViewById(R.id.textViewms);
        timerSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                                      @Override
                                                      public void onCheckedChanged(RadioGroup group, int checkedId) {
                                                          switch(checkedId)
                                                          {
                                                              case R.id.radioButton:
                                                                  String [] hoursValues = new String[100];
                                                                  for (int i=0; i < 100; i++) {
                                                                      hoursValues[i] = Integer.toString(i);
                                                                  }
                                                                  hours.setMinValue(0);
                                                                  hours.setMaxValue(99);
                                                                  hours.setDisplayedValues(hoursValues);
                                                                  hours.setWrapSelectorWheel(false);
                                                                  seconds.setVisibility(View.VISIBLE);
                                                                  letterS.setVisibility(View.VISIBLE);
                                                                  break;
                                                              case R.id.radioButton2:
                                                                  String [] hoursValues2 = new String[24];
                                                                  for (int i=0; i < 24; i++) {
                                                                      hoursValues2[i] = Integer.toString(i);
                                                                  }
                                                                  hours.setMinValue(0);
                                                                  hours.setMaxValue(23);
                                                                  hours.setDisplayedValues(hoursValues2);
                                                                  hours.setWrapSelectorWheel(false);
                                                                  seconds.setVisibility(View.GONE);
                                                                  letterS.setVisibility(View.GONE);
                                                                  break;
                                                          }
                                                      }
        });

    }

    private void addAlarm() {
        String alarmName = editAlarmName.getText().toString();
        if (alarmName.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Pick a Timer Name", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            long hoursToMilli = hours.getValue() * 60 * 60 * 1000;
            long minToMilli = minutes.getValue() * 60 * 1000;
            long secToMilli = seconds.getValue() * 1000;
            int checkedRadioButtonId = timerSelection.getCheckedRadioButtonId();
            String name = "";
            RadioButton radiovalue = this.findViewById(checkedRadioButtonId);
            if (radiovalue.getText().toString().equals("Countdown")) {
                name = "countdown";
                GlobalAlarmList.alarmList.add(new AlarmObject(alarmName, hoursToMilli + minToMilli + secToMilli, name, 0,0));
            } else {
                name = "alarm";
                GlobalAlarmList.alarmList.add(new AlarmObject(alarmName, hoursToMilli + minToMilli + secToMilli, name, hours.getValue(),minutes.getValue()));
            }
            Toast toast = Toast.makeText(getApplicationContext(), "Timer Added", Toast.LENGTH_SHORT);
            toast.show();
            Intent myIntent = new Intent(AddTimerActivity.this, HomeScreen.class);
            AddTimerActivity.this.startActivity(myIntent);
        }
    }
