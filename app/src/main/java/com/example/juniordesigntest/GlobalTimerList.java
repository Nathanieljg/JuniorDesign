package com.example.juniordesigntest;

import java.util.ArrayList;

// TODO: Make this not a public list
public class GlobalTimerList {

    public static ArrayList<TimerObject> alarmList = new ArrayList<>();

    public static int getNewAlarmId() { return (int) (System.currentTimeMillis() / 1000L); }
}

