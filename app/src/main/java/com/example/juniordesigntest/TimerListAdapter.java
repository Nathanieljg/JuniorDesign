package com.example.juniordesigntest;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.CountDownTimer;
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



public class TimerListAdapter extends RecyclerView.Adapter<TimerListAdapter.TimerViewHolder> {

    private List<TimerObject> mTimerObjectList;
    private Context context;
    private static final long DAY_AS_MILLI = 24 * 60 * 60 * 1000;

    public TimerListAdapter(List<TimerObject> alarms, Context context) {

        mTimerObjectList = GlobalTimerList.alarmList;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TimerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_alarm_list, parent, false);
        return new TimerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TimerViewHolder holder, int position) {
//        holder.bind(mAlarmObjectList.get(position));
        final TimerObject timer = mTimerObjectList.get(position);

        TextView alarmName = holder.timerName;
        alarmName.setText(timer.getTimerName());

        if (holder.countDown != null) {
            holder.countDown.cancel();
        }
        final long totalTime = timer.getTimerLength();
        final String typeTime = timer.getTimerTime();

        long remainingTimerTime;
        if (timer.getExpirationTime() < System.currentTimeMillis()) {
            remainingTimerTime = DAY_AS_MILLI - (System.currentTimeMillis() - timer.getExpirationTime());
        } else {
            remainingTimerTime = timer.getExpirationTime() - System.currentTimeMillis();
        }

        final int tempPos = position;

        holder.editButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(context, EditTimerActivity.class);
                intent.putExtra("index", tempPos);
                context.startActivity(intent);
            }
        });

        holder.countDown = new CountDownTimer(remainingTimerTime, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                int hours = (int) (TimeUnit.MILLISECONDS.toHours(millisUntilFinished));
                int minutes = (int) (TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)%60);
                int seconds = (int) (TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)%60);

                String time_left_formatted = String.format(Locale.getDefault(),"%02d:%02d:%02d", hours, minutes, seconds);
                switch(timer.getTimerType()) {
                    case COUNTDOWN:
                        holder.remainingTime.setText(time_left_formatted);
                        break;
                    case ALARM:
                        holder.remainingTime.setText(typeTime);
                        break;
                }

                int progressLeft = (int)(( (float)(millisUntilFinished) / totalTime) * 100);
                System.out.println(progressLeft);
                holder.progressBar.setProgress(progressLeft);
                holder.progressBar.setProgressTintList(getProgressColor(progressLeft));

                // Can be used for smoother progressbar animations
//                ObjectAnimator progressAni = ObjectAnimator.ofInt(holder.progressBar, "progress", progressLeft);
//                progressAni.setDuration(1000); // 0.5 second
//                progressAni.setInterpolator(new DecelerateInterpolator());
//                progressAni.start();
            }

            @Override
            public void onFinish() {
                // TODO: Change to do something when a timer finishes
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
        return mTimerObjectList.size();
    }

    public class TimerViewHolder extends RecyclerView.ViewHolder {

        private TextView timerName;
        private ProgressBar progressBar;
        private TextView remainingTime;
        private Button editButton;

        private CountDownTimer countDown;
        private boolean timerRunning;

        public TimerViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.progressBar);
            remainingTime = itemView.findViewById(R.id.timeLeft);
            timerName = itemView.findViewById(R.id.timerName);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }
}
