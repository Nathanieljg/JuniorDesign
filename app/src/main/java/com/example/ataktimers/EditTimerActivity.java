package com.example.ataktimers;

import android.app.Notification;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Toast;

import com.example.ataktimers.R;

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

//            CountDownTimer countDown = new CountDownTimer(remainingTimerTime, 1000) {
//                @Override
//                public void onTick(long millisUntilFinished) {
//                    long[] toRemove = null;
//                    List<EarlyNotificationObject> earlyNotifications = timer.getEarlyNotifications();
//
//                    for (EarlyNotificationObject earlyReminder: earlyNotifications) {
//                        if (millisUntilFinished <= earlyReminder[0] + earlyReminder[1] + earlyReminder[2]){
//                            sendNotification(earlyReminder[0] / 3600000 + " Hours, " + earlyReminder[1] / 60000 +
//                                    " Minutes, " + earlyReminder[2] / 1000 + " Seconds Remaining!");
//                            toRemove = earlyReminder;
//                        }
//                    }
//                    if (toRemove != null) { //removes the already triggered notification
//                        earlyNotifications.remove(toRemove);
//                    }
//                }
//
//                @RequiresApi(api = Build.VERSION_CODES.O)
//                @Override
//                public void onFinish() {
//                    sendNotification("Timer complete!");
//                    GlobalTimerList.alarmList.remove(timer);
//                }
//                public void sendNotification(String contentText) {
//                    Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//                    Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
//                    r.play();
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        CharSequence name = "ATTAK_timer";
//                        String description = "Channel for timer notifications";
//                        int importance = NotificationManager.IMPORTANCE_HIGH;
//                        NotificationChannel channel = new NotificationChannel("test", name, importance);
//                        channel.setDescription(description);
//                        // Register the channel with the system; you can't change the importance
//                        // or other notification behaviors after this
//                        NotificationManager notificationManager = getSystemService(NotificationManager.class);
//                        notificationManager.createNotificationChannel(channel);
//                    }
//
////                     TODO: add intent functionality so that clicking on the notification will stop alarm and take you to alarm details
////                    Intent intent = new Intent(this, AlertDetails.class);
////                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
////                    PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
//
//                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), "test" )
//                            .setSmallIcon(R.drawable.ic_baseline_notification_important_24)
//                            .setContentTitle(timer.getTimerName())
//                            .setContentText(contentText)
//                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
////                            .setContentIntent(pendingIntent)
////                            .setAutoCancel(true);
//                    int notificationId = 0;
//                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
//                    // notificationId is a unique int for each notification that you must define
//                    notificationManager.notify(notificationId, builder.build());
//                    notificationId++;
//                }
//
//            };
//            timer.setCountDown(countDown);
//            countDown.start();

//            // Create main alarms
//            int timerNotificationId = scheduleNotification(
//                    getNotification(timer.getTimerName(), "Timer Complete"),
//                    timer.getExpirationTime());
//            timer.setAlarmId(timerNotificationId);
//
//            // Create early notification alarms
//            for (EarlyNotificationObject earlyReminder: timer.getEarlyNotifications()) {
//                int earlyWarningNotificationId = scheduleNotification(
//                        getNotification(timer.getTimerName(), "Early Warning"),
//                        timer.getExpirationTime() - earlyReminder.getEarlyWarningLength());
//                earlyReminder.notificationId = earlyWarningNotificationId;
//            }

            Toast toast = Toast.makeText(getApplicationContext(), "Timer Added", Toast.LENGTH_SHORT);
            toast.show();
            Intent myIntent = new Intent(EditTimerActivity.this, HomeScreen.class);
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
}
