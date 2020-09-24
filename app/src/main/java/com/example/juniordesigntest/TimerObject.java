package com.example.juniordesigntest;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class TimerObject {

    private String timerName;
    private long startTime;
    private long expirationTime;
    private long timerLength;
    private TimerType timerType;
    private long hours;
    private long minutes;

    private final long DAY_AS_MILLI = 24 * 60 * 60 * 1000;

    TimerObject(String timerName, long timerLength, TimerType timerType, long hours, long minutes) {
        this.timerName = timerName;
        this.startTime = System.currentTimeMillis();
        this.timerType = timerType;

        switch (timerType) {
            case COUNTDOWN:
                this.expirationTime = System.currentTimeMillis() + timerLength;
                this.timerLength = timerLength;
                break;
            case ALARM:
                this.hours = hours;
                this.minutes = minutes;

                setExpirationInMillis();

                if (getExpirationTime() < System.currentTimeMillis()) {
                    this.timerLength = DAY_AS_MILLI - (System.currentTimeMillis() - getExpirationTime());
                } else {
                    this.timerLength = getExpirationTime() - System.currentTimeMillis();
                }
                break;
        }
    }

    String getTimerName() {
        return timerName;
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

    TimerType getTimerType() { return timerType; }

    String getTimerTime() { return String.format(Locale.getDefault(), "%02d:%02d", hours, minutes); }

    public long getHours() {
        return hours;
    }

    public long getMinutes() {
        return minutes;
    }

    public void setTimerName(String timerName) {
        this.timerName = timerName;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setExpirationTime(long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public void setTimerLength(long timerLength) {
        switch (timerType) {
            case COUNTDOWN:
                setExpirationTime(System.currentTimeMillis() + timerLength);
                this.timerLength = timerLength;
                break;
            case ALARM:
                setExpirationInMillis();

                if (getExpirationTime() < System.currentTimeMillis()) {
                    this.timerLength = DAY_AS_MILLI - (System.currentTimeMillis() - getExpirationTime());
                } else {
                    this.timerLength = getExpirationTime() - System.currentTimeMillis();
                }
                break;
        }
    }

    public void setTimerType(TimerType timerType) {
        this.timerType = timerType;
    }

    public void setHours(long hours) {
        this.hours = hours;
    }

    public void setMinutes(long minutes) {
        this.minutes = minutes;
    }

    public void setExpirationInMillis() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, (int) this.hours);
        cal.set(Calendar.MINUTE, (int) this.minutes);
        TimeZone tz = TimeZone.getTimeZone("GMT");
        cal.setTimeZone(tz);
        setExpirationTime(cal.getTimeInMillis());
    }

}
