package com.example.juniordesigntest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        GlobalTimerList.loadData(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void addTimer(View view) {
        Intent intent = new Intent(HomeScreen.this, AddTimerActivity.class);
        startActivity(intent);
    }

    public void viewTimers(View view) {
        Intent intent = new Intent(HomeScreen.this, ViewTimers.class);
        startActivity(intent);
    }

}
