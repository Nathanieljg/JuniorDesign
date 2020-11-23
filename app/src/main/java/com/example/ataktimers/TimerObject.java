package com.example.ataktimers;

import android.os.CountDownTimer;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class TimerObject {

    private String timerName;
    private long startTime;
    private long expirationTime;
    private long timerLength;
    private TimerType timerType;
    private long hours;
    private long minutes;
    private CountDownTimer countDown;
    private List<EarlyNotificationObject> earlyNotifications;
    private int alarmId;
    private Date expirationDate;

    private final long DAY_AS_MILLI = 24 * 60 * 60 * 1000;

    TimerObject(String timerName, long timerLength, TimerType timerType, long hours, long minutes, List<EarlyNotificationObject> earlyNotifications) {
        this.timerName = timerName;
        this.startTime = System.currentTimeMillis();
        this.timerType = timerType;
        this.earlyNotifications = earlyNotifications;

        Calendar calendar = Calendar.getInstance();

        switch (timerType) {
            case COUNTDOWN:
                this.expirationTime = System.currentTimeMillis() + timerLength;
                this.timerLength = timerLength;
                calendar.add(Calendar.MILLISECOND, (int) timerLength);
                this.expirationDate = calendar.getTime();
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
                calendar.add(Calendar.MILLISECOND, (int) timerLength);
                this.expirationDate = calendar.getTime();
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

    String getTimerTime() {
        if (timerType == TimerType.COUNTDOWN) {
            int tempHours = (int) (TimeUnit.MILLISECONDS.toHours(timerLength));
            int tempMinutes = (int) (TimeUnit.MILLISECONDS.toMinutes(timerLength) % 60);
            int tempSeconds = (int) (TimeUnit.MILLISECONDS.toSeconds(timerLength) % 60);
            return String.format(Locale.getDefault(), "%02d:%02d:%02d", tempHours, tempMinutes, tempSeconds);
        } else {
            return String.format(Locale.getDefault(), "%02d:%02d", this.hours, this.minutes);
        }
    }



    public long getHours() {
        return hours;
    }

    public long getMinutes() {
        return minutes;
    }

    public List<EarlyNotificationObject> getEarlyNotifications() { return earlyNotifications; }

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
        TimeZone tz = TimeZone.getTimeZone("GMT");
        cal.setTimeZone(tz);
        cal.getTime();
        Log.e("System current time", String.valueOf(cal.getTime()));
        Log.e("System current time ms", String.valueOf(cal.getTimeInMillis()));
//        if (cal.get(Calendar.HOUR_OF_DAY) < this.hours && cal.get(cal.get(Calendar.MINUTE)) < this.minutes) {
//            cal.add(Calendar.DATE, 1);
//        }
        cal.set(Calendar.HOUR_OF_DAY, (int) this.hours);
        cal.set(Calendar.MINUTE, (int) this.minutes);
        cal.set(Calendar.SECOND, 0);
        // move to next day if timer has passed
        if (cal.before(Calendar.getInstance())) {
            cal.add(Calendar.DATE, 1);
        }
        Log.e("Alarm end time", String.valueOf(cal.getTime()));
        Log.e("Alarm end time in milli", String.valueOf(cal.getTimeInMillis()));
        setExpirationTime(cal.getTimeInMillis());
    }

    public CountDownTimer getCountDown() {
        return countDown;
    }

    public void setCountDown(CountDownTimer countDown) {
        this.countDown = countDown;
    }

    public void setEarlyNotifications(List<EarlyNotificationObject> earlyNotifications) {
        this.earlyNotifications = earlyNotifications;
    }

    public void setAlarmId(int id) {
        alarmId = id;
    }

    public int getAlarmId() {
        return alarmId;
    }

    public Date getExpirationTimeStamp() {
        return expirationDate;
    }

    public void setExpirationTimeStamp(String expirationTimeStamp) {
        expirationTimeStamp = expirationTimeStamp;
    }

    public boolean isTimerExpired() { return System.currentTimeMillis() > expirationTime; }
}
