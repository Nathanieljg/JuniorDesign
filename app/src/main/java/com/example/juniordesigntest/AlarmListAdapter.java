package com.example.juniordesigntest;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AlarmListAdapter extends RecyclerView.Adapter<AlarmListAdapter.AlarmViewHolder> {

    private List<AlarmObject> mAlarmObjectList;
    private Context context;

    public AlarmListAdapter(List<AlarmObject> alarms) {

        mAlarmObjectList = GlobalAlarmList.alarmList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AlarmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_alarm_list, parent, false);
        return new AlarmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AlarmViewHolder holder, int position) {
//        holder.bind(mAlarmObjectList.get(position));
        AlarmObject alarm = mAlarmObjectList.get(position);
        long remainingAlarmTime = alarm.getExpirationTime() - System.currentTimeMillis();

        TextView alarmName = holder.alarmName;
        alarmName.setText(alarm.getAlarmName());


        if (holder.countDown != null) {
            holder.countDown.cancel();
        }
        final long totalTime = alarm.getTimerLength();
        holder.countDown = new CountDownTimer(remainingAlarmTime, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
//                int hours = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);
//                int minutes = (int) millisUntilFinished / 1000 / 60;
//                int seconds = (int) millisUntilFinished / 1000 % 60;
                int hours = (int) (TimeUnit.MILLISECONDS.toHours(millisUntilFinished)%60);
                int minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)%60);
                int seconds = (int) (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);


                String time_left_formatted = String.format(Locale.getDefault(),"%02d:%02d:%02d", hours, minutes, seconds);
                holder.remainingTime.setText(time_left_formatted);

                int progressLeft = (int)(( (float)(millisUntilFinished) / totalTime) * 100);
                System.out.println(progressLeft);
                holder.progressBar.setProgress(progressLeft);
                holder.progressBar.setProgressTintList(getProgressColor(progressLeft));
//                ObjectAnimator progressAni = ObjectAnimator.ofInt(holder.progressBar, "progress", progressLeft);
//                progressAni.setDuration(1000); // 0.5 second
//                progressAni.setInterpolator(new DecelerateInterpolator());
//                progressAni.start();
            }

            @Override
            public void onFinish() {
                holder.timerRunning = false;
                holder.progressBar.setProgress(0);
            }
        }.start();
    }

    private ColorStateList getProgressColor(int progressLeft) {
        if (progressLeft >= 50) {
            return ColorStateList.valueOf(Color.GREEN);
        } else if (progressLeft >= 25) {
            return ColorStateList.valueOf(Color.YELLOW);
        } else {
            return ColorStateList.valueOf(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return mAlarmObjectList.size();
    }

    public class AlarmViewHolder extends RecyclerView.ViewHolder {

        private TextView alarmName;
        private ProgressBar progressBar;
        private TextView remainingTime;
        private long timeLeft;
        private long startTime;
        private String alarmNameString;
        private CountDownTimer countDown;
        private boolean timerRunning;

        public AlarmViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
            remainingTime = itemView.findViewById(R.id.timeLeft);
            alarmName = itemView.findViewById(R.id.alarmName);

        }

        public void bind(final AlarmObject alarmObject) {
            alarmNameString = alarmObject.getAlarmName();
            alarmName.setText(alarmObject.getAlarmName());

            progressBar.setProgress((int)((float)timeLeft / startTime));
            start_timer();
        }

        private void start_timer() {
            countDown = new CountDownTimer(timeLeft, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeft = millisUntilFinished;
                    update_countdown_text();
                    int progress = (int) (((float) timeLeft / startTime) * 100);
                    progressBar.setProgress(progress);
                }

                @Override
                public void onFinish() {
                    timerRunning = false;
                }
            }.start();
            timerRunning = true;
        }

        private void update_countdown_text() {

            int hours = (int) ((timeLeft / (1000 * 60 * 60)) % 24);
            int minutes = (int) timeLeft / 1000 / 60;
            int seconds = (int) timeLeft / 1000 % 60;

            String time_left_formatted = String.format(Locale.getDefault(),"%02d:%02d:%02d", hours,minutes, seconds);

            remainingTime.setText(time_left_formatted);
        }
    }
}


