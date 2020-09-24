package com.example.juniordesigntest;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

public class ViewTimers extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView alarmList = findViewById(R.id.alarmList);
        LinearLayoutManager llm = new LinearLayoutManager(ViewTimers.this, LinearLayoutManager.VERTICAL, false);
        alarmList.setLayoutManager(llm);

        // uncomment to populate the TimerList with pre-made alarms when the app is launched
//        for (int i=1; i < 6; i++) {
//            String tempName = "Alarm " + i;
//            long tempStartTime = 10000*i;
//            GlobalAlarmList.alarmList.add(new AlarmObject(tempName, tempStartTime));
//            System.out.println("TESTING" + i);
//        }

        TimerListAdapter alarmAdapter = new TimerListAdapter(GlobalTimerList.alarmList, this);
        alarmList.setAdapter(alarmAdapter);
//        alarmAdapter.updateAlarmObjects(alarmObjects);
        alarmAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void addTimer(View view) {
        Intent intent = new Intent(ViewTimers.this, AddTimerActivity.class);
        startActivity(intent);
    }
}

