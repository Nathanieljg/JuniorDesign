package com.example.juniordesigntest;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class EarlyNotificationObject {

    public long[] time;
    public int notificationId;

    EarlyNotificationObject(long[] earlyNotificationLength) {
        this.time = earlyNotificationLength;
    }

    public long getEarlyWarningLength() {
        return time[0] + time[1] + time[2];
    }

    public String getEarlyNotificationTime() {
        long hours = TimeUnit.MILLISECONDS.toHours(time[0]);
        long min = TimeUnit.MILLISECONDS.toMinutes(time[1]);
        long sec = TimeUnit.MILLISECONDS.toSeconds(time[2]);
        return String.format(Locale.getDefault(), "%02d:%02d:%02d",
                hours,
                min,
                sec);
    }
}
