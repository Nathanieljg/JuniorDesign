package com.example.juniordesigntest;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.List;


public class AddEarlyNotificationFragment extends DialogFragment implements DialogInterface.OnDismissListener {

    private RecyclerView recyclerView;
    private int timerObjectPos;
    private NumberPicker hours;
    private NumberPicker minutes;
    private NumberPicker seconds;
    private List<EarlyNotificationObject> mEarlyNotifications;
    private Button addEarlyWarningDialogButton;

    public AddEarlyNotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        timerObjectPos = getArguments().getInt("pos");
        this.mEarlyNotifications = GlobalTimerList.alarmList.get(timerObjectPos).getEarlyNotifications();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_early_notification, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hours = view.findViewById(R.id.editEarlyNotificationsNumberPickerHours);
        String[] hoursValues = new String[100];
        for (int i = 0; i < 100; i++) {
            hoursValues[i] = Integer.toString(i);
        }

        minutes = view.findViewById(R.id.editEarlyNotificationsNumberPickerMinutes);
        String[] minutesValues = new String[60];
        for (int i = 0; i < 60; i++) {
            minutesValues[i] = Integer.toString(i);
        }

        seconds = view.findViewById(R.id.editEarlyNotificationsNumberPickerSeconds);
        String[] secondsValues = new String[60];
        for (int i = 0; i < 60; i++) {
            secondsValues[i] = Integer.toString(i);
        }

        hours.setMinValue(0);
        hours.setMaxValue(99);
        hours.setDisplayedValues(hoursValues);
        hours.setWrapSelectorWheel(true);

        minutes.setMinValue(0);
        minutes.setMaxValue(59);
        minutes.setDisplayedValues(minutesValues);
        minutes.setWrapSelectorWheel(true);

        seconds.setMinValue(0);
        seconds.setMaxValue(59);
        seconds.setDisplayedValues(secondsValues);
        seconds.setWrapSelectorWheel(true);

        addEarlyWarningDialogButton = view.findViewById(R.id.addEarlyWarningDialogButton);
        addEarlyWarningDialogButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addEarlyNotification();
            }
        });

    }

    @Override
    public void onDismiss(final DialogInterface dialog) {
        super.onDismiss(dialog);
        Bundle bundle = new Bundle();
        bundle.putInt("pos", timerObjectPos);
        EarlyNotificationFragment earlyNotificationFragment = new EarlyNotificationFragment();
        earlyNotificationFragment.setArguments(bundle);
        earlyNotificationFragment.show(getFragmentManager(), "TEST");

    }

    private void addEarlyNotification() {
        long hoursToMilli = hours.getValue() * 60 * 60 * 1000;
        long minToMilli = minutes.getValue() * 60 * 1000;
        long secToMilli = seconds.getValue() * 1000;
        if (hoursToMilli != 0 || minToMilli != 0 || secToMilli != 0) {
            long[] earlyNotificationLength = {hoursToMilli, minToMilli, secToMilli};
            mEarlyNotifications.add(new EarlyNotificationObject(earlyNotificationLength));
        }
        this.dismiss();
    }
}
