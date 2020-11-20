package com.example.juniordesigntest;

import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

// TODO: Make this not a public list
public class GlobalTimerList {

    public static String listName = "alarmList";

    public static ArrayList<TimerObject> alarmList = new ArrayList<>();

    public static int getNewAlarmId() { return (int) (System.currentTimeMillis() / 1000L); }

}

