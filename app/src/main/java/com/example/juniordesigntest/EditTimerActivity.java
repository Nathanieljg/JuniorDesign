package com.example.juniordesigntest;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class EditTimerActivity<mTimerObjectList> extends AppCompatActivity {

    private List<TimerObject> mTimerObjectList = GlobalTimerList.alarmList;

    private NumberPicker hours;
    private NumberPicker minutes;
    private NumberPicker seconds;
    private EditText editAlarmName;
    private TextView letterS;
    private Button buttonDone;
    private Button earlyNotificationButton;
    private Button exitWithoutSavingButton;
    private int pos;
    private AlarmManagerScheduler alarmManagerScheduler;
    private static final long DAY_AS_MILLI = 24 * 60 * 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        alarmManagerScheduler = new AlarmManagerScheduler(this);

        Intent intent = getIntent();
        pos = intent.getIntExtra("index", 0);

        final TimerObject timer = mTimerObjectList.get(pos);

        hours = findViewById(R.id.editNumberPickerHours);
        String[] hoursValues = new String[100];
        for (int i = 0; i < 100; i++) {
            hoursValues[i] = Integer.toString(i);
        }

        minutes = findViewById(R.id.editNumberPickerMinutes);
        String[] minutesValues = new String[60];
        for (int i = 0; i < 60; i++) {
            minutesValues[i] = Integer.toString(i);
        }

        seconds = findViewById(R.id.editNumberPickerSeconds);
        String[] secondsValues = new String[60];
        for (int i = 0; i < 60; i++) {
            secondsValues[i] = Integer.toString(i);
        }
        letterS = findViewById(R.id.editTextViewS);

        editAlarmName = findViewById(R.id.alarmNameEditText);

        earlyNotificationButton = findViewById(R.id.editEarlyNotificationsButton);
        earlyNotificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadEarlyNotificationFragment(v);
            }
        });

        buttonDone = findViewById(R.id.buttonDone);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                editAlarm(timer);
            }
        });

        exitWithoutSavingButton = findViewById(R.id.buttonExitWithoutSaving);
        exitWithoutSavingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitWithoutSaving(v);
            }
        });

        switch (timer.getTimerType()) {
            case COUNTDOWN:
                int countHours = (int) (TimeUnit.MILLISECONDS.toHours(timer.getTimerLength()));
                int countMinutes = (int) (TimeUnit.MILLISECONDS.toMinutes(timer.getTimerLength())%60);
                int countSeconds = (int) (TimeUnit.MILLISECONDS.toSeconds(timer.getTimerLength())%60);

                hours.setMinValue(0);
                hours.setMaxValue(99);
                hours.setDisplayedValues(hoursValues);
                hours.setWrapSelectorWheel(true);
                hours.setValue(countHours);

                minutes.setMinValue(0);
                minutes.setMaxValue(59);
                minutes.setDisplayedValues(minutesValues);
                minutes.setWrapSelectorWheel(true);
                minutes.setValue(countMinutes);

                seconds.setMinValue(0);
                seconds.setMaxValue(59);
                seconds.setDisplayedValues(secondsValues);
                seconds.setWrapSelectorWheel(true);
                seconds.setValue(countSeconds);

                editAlarmName.setText(timer.getTimerName());

                break;
            case ALARM:
                hours.setMinValue(0);
                hours.setMaxValue(23);
                hours.setDisplayedValues(hoursValues);
                hours.setWrapSelectorWheel(true);
                hours.setValue((int)timer.getHours());

                minutes.setMinValue(0);
                minutes.setMaxValue(59);
                minutes.setDisplayedValues(minutesValues);
                minutes.setWrapSelectorWheel(true);
                minutes.setValue((int)timer.getMinutes());

                editAlarmName.setText(timer.getTimerName());

                seconds.setVisibility(View.GONE);
                letterS.setVisibility(View.GONE);

                break;
        }
    }

    private void editAlarm(final TimerObject timer) {
        String alarmName = editAlarmName.getText().toString();
        if (alarmName.equals("")) {
            Toast toast = Toast.makeText(getApplicationContext(), "Pick a Timer Name", Toast.LENGTH_SHORT);
            toast.show();
        } else {
            long hoursToMilli = hours.getValue() * 60 * 60 * 1000;
            long minToMilli = minutes.getValue() * 60 * 1000;
            long secToMilli = seconds.getValue() * 1000;

            timer.setTimerName(alarmName);

            if (timer.getTimerType() == TimerType.COUNTDOWN) {
                timer.setTimerLength(hoursToMilli + minToMilli + secToMilli);
                timer.setHours(0);
                timer.setMinutes(0);
            } else {
                timer.setHours(hours.getValue());
                timer.setMinutes(minutes.getValue());
                timer.setTimerLength(hoursToMilli + minToMilli + secToMilli);
            }

            // TODO: Switch to use AlarmManager

            // Replace main timer Alarmclock
            Notification notification = alarmManagerScheduler.getNotification(timer.getTimerName(),  "Timer Complete");
            alarmManagerScheduler.removeNotification(timer.getAlarmId(), notification);
            int timerNotificationId = alarmManagerScheduler.scheduleNotification(
                    notification,
                    timer.getExpirationTime());
            timer.setAlarmId(timerNotificationId);

            // Create early notification alarms
            for (EarlyNotificationObject earlyReminder: timer.getEarlyNotifications()) {
                Notification earlyWarningNotification = alarmManagerScheduler.getNotification(timer.getTimerName(),  "Early Warning: " + earlyReminder.getEarlyNotificationTime() + " until expiration");
                alarmManagerScheduler.removeNotification(earlyReminder.notificationId, notification);
                int earlyWarningNotificationId = alarmManagerScheduler.scheduleNotification(
                        earlyWarningNotification,
                        timer.getExpirationTime() - earlyReminder.getEarlyWarningLength());
                earlyReminder.notificationId = earlyWarningNotificationId;
            }

            Toast toast = Toast.makeText(getApplicationContext(), "Timer Added", Toast.LENGTH_SHORT);
            toast.show();
            Intent myIntent = new Intent(EditTimerActivity.this, ViewTimers.class);
            EditTimerActivity.this.startActivity(myIntent);
        }

    }

    public void loadEarlyNotificationFragment(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);
        EarlyNotificationFragment earlyNotificationFragment = new EarlyNotificationFragment();
        earlyNotificationFragment.setArguments(bundle);
        earlyNotificationFragment.show(getSupportFragmentManager(), "TEST");
    }

    private boolean checkIfTimeChanged(TimerObject timer) {
        int countHours = (int) (TimeUnit.MILLISECONDS.toHours(timer.getTimerLength()));
        int countMinutes = (int) (TimeUnit.MILLISECONDS.toMinutes(timer.getTimerLength())%60);
        int countSeconds = (int) (TimeUnit.MILLISECONDS.toSeconds(timer.getTimerLength())%60);
        return (countHours != hours.getValue()
                || countMinutes != minutes.getValue()
                || countSeconds != seconds.getValue());
    }

    public void exitWithoutSaving(View view) {
        Intent intent = new Intent(EditTimerActivity.this, ViewTimers.class);
        startActivity(intent);
    }
}
