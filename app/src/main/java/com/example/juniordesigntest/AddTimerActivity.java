package com.example.juniordesigntest;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddTimerActivity extends AppCompatActivity {

    private Button buttonAddEarlyNotifications;
    private Button buttonAddTimer;
    private NumberPicker hours;
    private NumberPicker earlyHours;
    private NumberPicker minutes;
    private NumberPicker earlyMinutes;
    private NumberPicker seconds;
    private NumberPicker earlySeconds;
    private EditText editAlarmName;
    private RadioGroup timerSelection;
    private TextView letterS;
    private static final long DAY_AS_MILLI = 24 * 60 * 60 * 1000;
    private List<long[]> earlyNotifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_add_timer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        earlyNotifications = new ArrayList<>();
        hours = findViewById(R.id.numberPickerHours);
        earlyHours = findViewById(R.id.earlyNumberPickerHours);

        String[] hoursValues = new String[100];
        for (int i = 0; i < 100; i++) {
            hoursValues[i] = Integer.toString(i);
        }
        hours.setMinValue(0);
        hours.setMaxValue(99);
        hours.setDisplayedValues(hoursValues);
        hours.setWrapSelectorWheel(true);

        earlyHours.setMinValue(0);
        earlyHours.setMaxValue(99);
        earlyHours.setDisplayedValues(hoursValues);
        earlyHours.setWrapSelectorWheel(true);

        minutes = findViewById(R.id.numberPickerMinutes);
        earlyMinutes = findViewById(R.id.earlyNumberPickerMinutes);

        String[] minutesValues = new String[60];
        for (int i = 0; i < 60; i++) {
            minutesValues[i] = Integer.toString(i);
        }
        minutes.setMinValue(0);
        minutes.setMaxValue(59);
        minutes.setDisplayedValues(minutesValues);
        minutes.setWrapSelectorWheel(true);

        earlyMinutes.setMinValue(0);
        earlyMinutes.setMaxValue(59);
        earlyMinutes.setDisplayedValues(minutesValues);
        earlyMinutes.setWrapSelectorWheel(true);

        earlySeconds = findViewById(R.id.earlyNumberPickerSeconds);
        seconds = findViewById(R.id.numberPickerSeconds);

        String[] secondsValues = new String[60];
        for (int i = 0; i < 60; i++) {
            secondsValues[i] = Integer.toString(i);
        }
        seconds.setMinValue(0);
        seconds.setMaxValue(59);
        seconds.setDisplayedValues(secondsValues);
        seconds.setWrapSelectorWheel(true);

        earlySeconds.setMinValue(0);
        earlySeconds.setMaxValue(59);
        earlySeconds.setDisplayedValues(secondsValues);
        earlySeconds.setWrapSelectorWheel(true);

        editAlarmName = findViewById(R.id.alarmNameEditText);

        buttonAddTimer = findViewById(R.id.buttonAddTimer);
        buttonAddTimer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addAlarm();
            }
        });

        buttonAddEarlyNotifications = findViewById(R.id.buttonAddEarly);
        buttonAddEarlyNotifications.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addEarlyNotifications();
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
    private void addEarlyNotifications() { //this is called whenver the add notification button is pushed
        Toast toast = Toast.makeText(getApplicationContext(), "Early Notification added", Toast.LENGTH_SHORT);

        long hoursToMilli = earlyHours.getValue() * 60 * 60 * 1000;
        long minToMilli = earlyMinutes.getValue() * 60 * 1000;
        long secToMilli = earlySeconds.getValue() * 1000;
        if (hoursToMilli != 0 || minToMilli != 0 || secToMilli != 0) {
            long[] earlyNotificationLength = {hoursToMilli, minToMilli, secToMilli};
            earlyNotifications.add(earlyNotificationLength);
            toast.show();
            earlyHours.setValue(0);
            earlyMinutes.setValue(0);
            earlySeconds.setValue(0);
        }

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

            for (long[] earlyNotification: earlyNotifications) {
                if (hoursToMilli + minToMilli + secToMilli < earlyNotification[0] + earlyNotification[1] + earlyNotification[2]) {
                    earlyNotifications.remove(earlyNotification);
                }
            }

            int checkedRadioButtonId = timerSelection.getCheckedRadioButtonId();
            RadioButton radioValue = this.findViewById(checkedRadioButtonId);

            TimerType timerType;
            final TimerObject timer;
            if (radioValue.getText().toString().equals("Countdown")) {
                timerType = TimerType.COUNTDOWN;
                timer = new TimerObject(alarmName, hoursToMilli + minToMilli + secToMilli, timerType, 0, 0, earlyNotifications);
            } else {
                timerType = TimerType.ALARM;
                timer = new TimerObject(alarmName, hoursToMilli + minToMilli + secToMilli, timerType, hours.getValue(), minutes.getValue(), earlyNotifications);
            }
            GlobalTimerList.alarmList.add(timer);

            long remainingTimerTime;
            if (timer.getExpirationTime() < System.currentTimeMillis()) {
                remainingTimerTime = DAY_AS_MILLI - (System.currentTimeMillis() - timer.getExpirationTime());
            } else {
                remainingTimerTime = timer.getExpirationTime() - System.currentTimeMillis();
            }

            CountDownTimer countDown = new CountDownTimer(remainingTimerTime, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    // TODO: Early warning messages can be sent from here
                    long[] toRemove = null;
                    earlyNotifications = timer.getEarlyNotifications();

                    for (long[] earlyReminder: earlyNotifications) {
                        if (millisUntilFinished <= earlyReminder[0] + earlyReminder[1] + earlyReminder[2]){
                            sendNotification(earlyReminder[0] / 3600000 + " Hours, " + earlyReminder[1] / 60000 +
                                    " Minutes, " + earlyReminder[2] / 1000 + " Seconds Remaining!");
                            toRemove = earlyReminder;
                        }
                    }
                    if (toRemove != null) { //removes the already triggered notification
                        earlyNotifications.remove(toRemove);
                    }
                }

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onFinish() {
                    sendNotification("Timer complete!");
                    GlobalTimerList.alarmList.remove(timer);
                }
                public void sendNotification(String contentText) {
                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
                    r.play();

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        CharSequence name = "ATTAK_timer";
                        String description = "Channel for timer notifications";
                        int importance = NotificationManager.IMPORTANCE_HIGH;
                        NotificationChannel channel = new NotificationChannel("test", name, importance);
                        channel.setDescription(description);
                        // Register the channel with the system; you can't change the importance
                        // or other notification behaviors after this
                        NotificationManager notificationManager = getSystemService(NotificationManager.class);
                        notificationManager.createNotificationChannel(channel);
                    }

//                     TODO: add intent functionality so that clicking on the notification will stop alarm and take you to alarm details
//                    Intent intent = new Intent(this, AlertDetails.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "test" )
                            .setSmallIcon(R.drawable.ic_baseline_notification_important_24)
                            .setContentTitle(timer.getTimerName())
                            .setContentText(contentText)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//                            .setContentIntent(pendingIntent)
//                            .setAutoCancel(true);
                    int notificationId = 0;
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
                    // notificationId is a unique int for each notification that you must define
                    notificationManager.notify(notificationId, builder.build());
                    notificationId++;
                }

            };
            timer.setCountDown(countDown);
            countDown.start();

            Toast toast = Toast.makeText(getApplicationContext(), "Timer Added", Toast.LENGTH_SHORT);
            toast.show();
            Intent myIntent = new Intent(AddTimerActivity.this, HomeScreen.class);
            AddTimerActivity.this.startActivity(myIntent);
        }
    }

}
