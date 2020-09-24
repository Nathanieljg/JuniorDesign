package com.example.juniordesigntest;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class EditTimerActivity<mTimerObjectList> extends AppCompatActivity {

    private List<TimerObject> mTimerObjectList = GlobalTimerList.alarmList;

    private TextView mTextView;
    private NumberPicker hours;
    private NumberPicker minutes;
    private NumberPicker seconds;
    private EditText editAlarmName;
    //private Button buttonDone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        int pos = intent.getIntExtra("index", 0);

        final TimerObject timer = mTimerObjectList.get(pos);

        String[] hoursValues = new String[100];
        for (int i = 0; i < 100; i++) {
            hoursValues[i] = Integer.toString(i);
        }

        String[] minutesValues = new String[60];
        for (int i = 0; i < 60; i++) {
            minutesValues[i] = Integer.toString(i);
        }

        String[] secondsValues = new String[60];
        for (int i = 0; i < 60; i++) {
            secondsValues[i] = Integer.toString(i);
        }

        switch (timer.getTimerType()) {
            case COUNTDOWN:
                int countHours = (int) (TimeUnit.MILLISECONDS.toHours(timer.getExpirationTime()));
                int countMinutes = (int) (TimeUnit.MILLISECONDS.toMinutes(timer.getExpirationTime())%60);
                int countSeconds = (int) (TimeUnit.MILLISECONDS.toSeconds(timer.getExpirationTime())%60);

                hours = findViewById(R.id.editNumberPickerHours);
                hours.setMinValue(0);
                hours.setMaxValue(99);
                hours.setDisplayedValues(hoursValues);
                hours.setWrapSelectorWheel(true);
                hours.setValue(countHours);

                minutes = findViewById(R.id.editNumberPickerMinutes);
                minutes.setMinValue(0);
                minutes.setMaxValue(59);
                minutes.setDisplayedValues(minutesValues);
                minutes.setWrapSelectorWheel(true);
                minutes.setValue(countMinutes);

                seconds = findViewById(R.id.editNumberPickerSeconds);

                seconds.setMinValue(0);
                seconds.setMaxValue(59);
                seconds.setDisplayedValues(secondsValues);
                seconds.setWrapSelectorWheel(true);
                seconds.setValue(countSeconds);

                editAlarmName = findViewById(R.id.alarmNameEditText);
                editAlarmName.setText(timer.getTimerName());

                break;
            case ALARM:
                hours = findViewById(R.id.editNumberPickerHours);
                hours.setMinValue(0);
                hours.setMaxValue(23);
                hours.setDisplayedValues(hoursValues);
                hours.setWrapSelectorWheel(true);
                hours.setValue((int)timer.getHours());

                minutes = findViewById(R.id.editNumberPickerMinutes);
                minutes.setMinValue(0);
                minutes.setMaxValue(59);
                minutes.setDisplayedValues(minutesValues);
                minutes.setWrapSelectorWheel(true);
                minutes.setValue((int)timer.getMinutes());

                editAlarmName = findViewById(R.id.alarmNameEditText);
                editAlarmName.setText(timer.getTimerName());

//                seconds.setVisibility(View.GONE);
//                letterS.setVisibility(View.GONE);
                break;
        }
    }


        /*buttonDone = findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addAlarm();
            }
        });*/
}