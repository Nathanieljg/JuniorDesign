package com.example.juniordesigntest;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

// TODO: Make this not a public list
public class GlobalTimerList {

    public static String listName = "alarmList";

    public static ArrayList<TimerObject> alarmList = new ArrayList<>();

    public static int getNewAlarmId() { return (int) (System.currentTimeMillis() / 1000L); }

    /**
     * A method to save the updated global timer list whenever a new timer is
     * added to it
     * */
    public static void saveData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(GlobalTimerList.alarmList);
        editor.putString(GlobalTimerList.listName, json);
        editor.apply();
    }

    /***
     * Load in the data via shared preferences, and update the globalTimerAlarmList
     */
    public static void loadData(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(GlobalTimerList.listName, null);
        Type type = new TypeToken<ArrayList<TimerObject>>() {}.getType();
        GlobalTimerList.alarmList = gson.fromJson(json, type);
        if (GlobalTimerList.alarmList == null) {
            GlobalTimerList.alarmList = new ArrayList<>();
        }
    }

}

