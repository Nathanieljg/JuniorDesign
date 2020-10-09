package com.example.juniordesigntest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
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
//        EarlyNotificationFragment earlyNotificationFragment = new EarlyNotificationFragment();
//        earlyNotificationFragment.setArguments(bundle);
//        earlyNotificationFragment.show(getSupportFragmentManager(), "TEST");

        // Create new fragment and transaction
        AddEarlyNotificationFragment newFragment = new AddEarlyNotificationFragment();
        newFragment.setArguments(bundle);

        newFragment.show(getFragmentManager(), "add_early_notification");
        dismiss();

//        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();

//        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//
//        // Replace whatever is in the fragment_container view with this fragment,
//        // and add the transaction to the back stack
//        transaction.replace(R.id.fragment_container , newFragment);
//        transaction.addToBackStack(null);
//
//        // Commit the transaction
//        transaction.commit();
    }
}
