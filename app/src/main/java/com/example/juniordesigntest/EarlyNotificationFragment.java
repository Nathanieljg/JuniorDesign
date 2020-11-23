package com.example.juniordesigntest;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class EarlyNotificationFragment extends DialogFragment {

    private RecyclerView recyclerView;
    private int pos;
    private Button createNewButton;
    private List<EarlyNotificationObject> mEarlyNotifications;
    private AlarmManagerScheduler alarmManagerScheduler;

    public EarlyNotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        alarmManagerScheduler = new AlarmManagerScheduler((AppCompatActivity) getActivity());
        pos = getArguments().getInt("pos");
        this.mEarlyNotifications = GlobalTimerList.alarmList.get(pos).getEarlyNotifications();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_early_notification, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.earlyNotificationRecyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(llm);
        EarlyNotificationListAdapter arrayAdapter = new EarlyNotificationListAdapter(pos);
        arrayAdapter.setOnItemClickListener(new EarlyNotificationListAdapter.ClickListener() {
            @Override
            public void onDeleteClick(int position, View v) {
                alarmManagerScheduler.removeNotification(mEarlyNotifications.get(position).notificationId, alarmManagerScheduler.getNotification("Remove", "Remove"));
            }
        });
        recyclerView.setAdapter(arrayAdapter);

        createNewButton = view.findViewById(R.id.createNewButton);
        createNewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAddEarlyNotificationFragment(v);
            }
        });
    }

    public void loadAddEarlyNotificationFragment(View view) {
        Bundle bundle = new Bundle();
        bundle.putInt("pos", pos);

        // Create new fragment and transaction
        AddEarlyNotificationFragment newFragment = new AddEarlyNotificationFragment();
        newFragment.setArguments(bundle);

        newFragment.show(getFragmentManager(), "add_early_notification");
        dismiss();
    }
}
