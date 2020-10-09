package com.example.juniordesigntest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;

public class EarlyNotificationFragment extends DialogFragment {

    private RecyclerView recyclerView;
    private int pos;

    public EarlyNotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pos = getArguments().getInt("pos");
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

        recyclerView.setAdapter(arrayAdapter);
    }
}
