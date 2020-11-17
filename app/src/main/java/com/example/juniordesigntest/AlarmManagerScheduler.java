package com.example.juniordesigntest;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.FragmentActivity;

public class AlarmManagerScheduler {

    private AppCompatActivity activity;
    private int idOffset = 0; //Used to change timestamp so they do not override each other

    public AlarmManagerScheduler(AppCompatActivity activity) {
        this.activity = activity;
    }

    /*
Schedules a new notification and returns the ID of the notification
 */
    public int scheduleNotification(Notification notification, long expirationTime) {
        int notificationId = GlobalTimerList.getNewAlarmId() + idOffset;
        idOffset += 1;
        Log.e("Scheduling Notification", "Alarm ID: " + notificationId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ATTAK_timer";
            String description = "Channel for timer notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("test", name, importance);
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = activity.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            Intent notificationIntent = new Intent(activity, NotificationPublisher.class);
            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notificationId);
            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, notificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(
                    expirationTime,
                    pendingIntent);
            alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);

//            alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, expirationTime, pendingIntent);
        }
        return notificationId;
    }

    public Notification getNotification(String contentTitle, String contentText) {
        Log.e("Creating Notification", contentTitle + " : " + contentText);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(activity.getApplicationContext(), "test" )
                .setSmallIcon(R.drawable.ic_baseline_notification_important_24)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        return builder.build();
    }

    public void removeNotification(int notificationId, Notification notification) {

        Log.e("Removing Notification", "Alarm ID: " + notificationId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "ATTAK_timer";
            String description = "Channel for timer notifications";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("test", name, importance);
            channel.setDescription(description);

            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = activity.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

            Intent notificationIntent = new Intent(activity, NotificationPublisher.class);
            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notificationId);
            notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(activity, notificationId, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) activity.getSystemService(Context.ALARM_SERVICE);
//            AlarmManager.AlarmClockInfo alarmClockInfo = new AlarmManager.AlarmClockInfo(
//                    expirationTime,
//                    pendingIntent);
            alarmManager.cancel(pendingIntent);
//            alarmManager.setAlarmClock(alarmClockInfo, pendingIntent);

//            alarmManager.setExactAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, expirationTime, pendingIntent);
        }
//        return notificationId;
    }
}
