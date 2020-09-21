package com.example.juniordesigntest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class AddTimerActivity extends AppCompatActivity {

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

        hours = findViewById(R.id.numberPickerHours);
        String[] hoursValues = new String[100];
        for (int i = 0; i < 100; i++) {
            hoursValues[i] = Integer.toString(i);
        }
        hours.setMinValue(0);
        hours.setMaxValue(99);
        hours.setDisplayedValues(hoursValues);
        hours.setWrapSelectorWheel(true);

        minutes = findViewById(R.id.numberPickerMinutes);
        String[] minutesValues = new String[60];
        for (int i = 0; i < 60; i++) {
            minutesValues[i] = Integer.toString(i);
        }
        minutes.setMinValue(0);
        minutes.setMaxValue(59);
        minutes.setDisplayedValues(minutesValues);
        minutes.setWrapSelectorWheel(true);

        seconds = findViewById(R.id.numberPickerSeconds);
        String[] secondsValues = new String[60];
        for (int i = 0; i < 60; i++) {
            secondsValues[i] = Integer.toString(i);
        }
        seconds.setMinValue(0);
        seconds.setMaxValue(59);
        seconds.setDisplayedValues(secondsValues);
        seconds.setWrapSelectorWheel(true);

        editAlarmName = findViewById(R.id.alarmNameEditText);

        buttonAddTimer = findViewById(R.id.buttonAddTimer);
        buttonAddTimer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addAlarm();
            }
        });

        timerSelection = findViewById(R.id.radioGroup);
        letterS = findViewById(R.id.textViewS); // used to hide and un-hide for alarm/countdown

        // check if the radio button is set to countdown or alarm and change layout if needed
        timerSelection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radioButtonCountdown:
                        String[] hoursValuesCountdown = new String[100];
                        for (int i = 0; i < 100; i++) {
                            hoursValuesCountdown[i] = Integer.toString(i);
                        }
                        hours.setDisplayedValues(null); // prevents index out of bounds
                        hours.setMinValue(0);
                        hours.setMaxValue(99);
                        hours.setDisplayedValues(hoursValuesCountdown);
                        seconds.setVisibility(View.VISIBLE);
                        letterS.setVisibility(View.VISIBLE);
                        break;
                    case R.id.radioButtonAlarm:
                        String[] hoursValuesAlarm = new String[24];
                        for (int i = 0; i < 24; i++) {
                            hoursValuesAlarm[i] = Integer.toString(i);
                        }
                        hours.setDisplayedValues(null); // prevents index out of bounds
                        hours.setMinValue(0);
                        hours.setMaxValue(23);
                        hours.setDisplayedValues(hoursValuesAlarm);
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
            RadioButton radioValue = this.findViewById(checkedRadioButtonId);

            TimerType timerType;
            if (radioValue.getText().toString().equals("Countdown")) {
                timerType = TimerType.COUNTDOWN;
                GlobalTimerList.alarmList.add(new TimerObject(alarmName, hoursToMilli + minToMilli + secToMilli, timerType, 0, 0));
            } else {
                timerType = TimerType.ALARM;
                GlobalTimerList.alarmList.add(new TimerObject(alarmName, hoursToMilli + minToMilli + secToMilli, timerType, hours.getValue(), minutes.getValue()));
            }
            Toast toast = Toast.makeText(getApplicationContext(), "Timer Added", Toast.LENGTH_SHORT);
            toast.show();
            Intent myIntent = new Intent(AddTimerActivity.this, HomeScreen.class);
            AddTimerActivity.this.startActivity(myIntent);
        }
    }
}
