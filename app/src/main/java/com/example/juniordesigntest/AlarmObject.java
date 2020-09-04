package com.example.juniordesigntest;

import android.os.CountDownTimer;

import java.util.Calendar;
import java.util.TimeZone;

public class AlarmObject {

    private String alarmName;
    private long startTime;
    private long expirationTime;
    private long timerLength;
    private String timerType;
    private long hours;
    private long minutes;
    private long DAY_AS_MILLI = 24 * 60 * 60 * 1000;


    AlarmObject(String alarmName, long timerLength, String timerType, long hours, long minutes) {
        this.alarmName = alarmName;
        this.startTime = System.currentTimeMillis();
        if (timerType.equals("countdown")) {
            this.expirationTime = System.currentTimeMillis() + timerLength;
        } else {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY, (int) hours);
            cal.set(Calendar.MINUTE, (int) minutes);
            TimeZone tz = TimeZone.getTimeZone("GMT");
            cal.setTimeZone(tz);
            this.expirationTime = cal.getTimeInMillis();
            this.hours = hours;
            this.minutes = minutes;
        }
        if (timerType.equals("countdown")) {
            this.timerLength = timerLength;
        } else {
            long remainingAlarmTime = 0;
            if (getExpirationTime() < System.currentTimeMillis()) {
                remainingAlarmTime = DAY_AS_MILLI - (System.currentTimeMillis() - getExpirationTime());
            } else {
                remainingAlarmTime = getExpirationTime() - System.currentTimeMillis();
            }
            this.timerLength = remainingAlarmTime;
        }
        this.timerType = timerType;
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

    String getTimerType() { return timerType; }

    String getAlarmTime() { return String.format("%02d:%02d", hours, minutes); }


}
