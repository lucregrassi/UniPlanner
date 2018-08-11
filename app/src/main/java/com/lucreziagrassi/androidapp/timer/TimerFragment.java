package com.lucreziagrassi.androidapp.timer;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lucreziagrassi.androidapp.R;

import java.util.Locale;


public class TimerFragment extends Fragment {

    private static final long START_TIME_IN_MILLIS = 1500000;

    private TextView timerTextView;

    private ImageButton playButton;
    private ImageButton pauseButton;
    private ImageButton resetButton;

    private CountDownTimer timer;

    private boolean timerRunning;

    private long remainingTimeInMillis = START_TIME_IN_MILLIS;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        // inflate the layout for this fragment
        View view = inflater.inflate(R.layout.timer_fragment, container,false);

        // get references to widgets
        timerTextView = view.findViewById(R.id.textViewTimer);

        playButton = view.findViewById(R.id.timer_playButton);
        pauseButton = view.findViewById(R.id.timer_pauseButton);
        resetButton = view.findViewById(R.id.timer_resetButton);

        // set listeners
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!timerRunning){
                    start();
                }
            }
        });

        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timerRunning) {
                    pause();
                }
            }
        });

        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });

        updateCountDownText();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getActivity().setTitle(R.string.timer_fragment_name);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void start(){
        timer = new CountDownTimer(remainingTimeInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                remainingTimeInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
            }
        }.start();

        timerRunning = true;
    }

    private void pause(){
        timer.cancel();
        timerRunning = false;
    }

    private void reset(){
        remainingTimeInMillis = START_TIME_IN_MILLIS;
        updateCountDownText();
    }

    private void updateCountDownText(){
        int minutes = (int) (remainingTimeInMillis/1000)/60;
        int seconds = (int) (remainingTimeInMillis/1000)%60;
        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);
        timerTextView.setText(timeLeftFormatted);
    }

}
