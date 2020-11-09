package com.example.juniordesigntest;

import java.util.ArrayList;

// TODO: Make this not a public list
public class GlobalTimerList {

    public static ArrayList<TimerObject> alarmList = new ArrayList<>();

    private static int alarmId = 0;

    public static int getNewAlarmId() {
        int newId = alarmId;
        alarmId++;
        return newId;
    }
}

