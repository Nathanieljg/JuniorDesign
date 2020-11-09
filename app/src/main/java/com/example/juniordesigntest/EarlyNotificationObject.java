package com.example.juniordesigntest;

public class EarlyNotificationObject {

    public long[] time;
    public int notificationId;

    EarlyNotificationObject(long[] earlyNotificationLength) {
        this.time = earlyNotificationLength;
    }

    public long getEarlyWarningLength() {
        return time[0] + time[1] + time[2];
    }
}
