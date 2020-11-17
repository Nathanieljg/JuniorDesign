package com.example.juniordesigntest;

import android.content.Intent;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class EarlyNotificationListAdapter extends RecyclerView.Adapter<EarlyNotificationListAdapter.EarlyNotificationViewHolder> {

    private List<EarlyNotificationObject> mEarlyNotifications;

    public EarlyNotificationListAdapter(int timerObjectPos) {
        this.mEarlyNotifications = GlobalTimerList.alarmList.get(timerObjectPos).getEarlyNotifications();
        Log.println(Log.DEBUG, "TEST", String.valueOf(mEarlyNotifications.size()));
    }

    @NonNull
    @Override
    public EarlyNotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_early_notification_item, parent, false);
        return new EarlyNotificationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EarlyNotificationViewHolder holder, final int position) {

        final EarlyNotificationObject earlyNotification = mEarlyNotifications.get(position);
        holder.earlyNotificationTime.setText(earlyNotification.getEarlyNotificationTime());

//        holder.deleteButton.setOnClickListener(new Button.OnClickListener() {
//            public void onClick(View view) {
//                mEarlyNotifications.remove(position);
//                notifyDataSetChanged();
//            }
//        });

    }

//    public String formatEarlyNotificationString(long[] notification) {
//        long hours = TimeUnit.MILLISECONDS.toHours(notification[0]);
//        long min = TimeUnit.MILLISECONDS.toMinutes(notification[1]);
//        long sec = TimeUnit.MILLISECONDS.toSeconds(notification[2]);
//        return String.format(Locale.getDefault(), "%02d:%02d:%02d",
//                hours,
//                min,
//                sec);
//    }

    @Override
    public int getItemCount() {
        return mEarlyNotifications.size();
    }

    public class EarlyNotificationViewHolder extends RecyclerView.ViewHolder {

        private TextView earlyNotificationTime;
        private Button deleteButton;

        public EarlyNotificationViewHolder(@NonNull View itemView) {
            super(itemView);
            earlyNotificationTime = itemView.findViewById(R.id.earlyNotificationTime);

            deleteButton = itemView.findViewById(R.id.deleteButton);

            // remove early notification from AlarmObject and AlarmManager
            deleteButton.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View view) {
                    mEarlyNotifications.remove(getAdapterPosition());
                    notifyDataSetChanged();
                }
            });
        }

    }
}
