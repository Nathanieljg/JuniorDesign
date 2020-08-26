package com.example.juniordesigntest;

import android.os.CountDownTimer;

public class AlarmObject {

    private String alarmName;
//    private long timeLeft;
    private long startTime;
    private long expirationTime;
    private long timerLength;

    AlarmObject(String alarmName, long timerLength) {
        this.alarmName = alarmName;
        this.startTime = System.currentTimeMillis();
        this.expirationTime = System.currentTimeMillis() + timerLength;
        this.timerLength = timerLength;
//        this.timeLeft = timeLeft;
//        this.startTime = startTime;
    }

    String getAlarmName() {
        return alarmName;
    }

    long getStartTime() {
        return startTime;
    }

    long getExpirationTime() {
        return expirationTime;
    }

    long getTimerLength() {
        return timerLength;
    }
}
