package com.example.dailylist.ui.tomatoClock;
//TODO 番茄闹钟界面交互

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.dailylist.R;
import com.example.dailylist.UserConfig;

/**
 * @ProjectName: DailyList
 * @ClassName: TomatoClockActivity
 * @Description: 番茄闹钟
 * @Author: 夜雨不知醉
 * @Date: 2022/10/15
 * @Version: 1.0
 */
public class TomatoClockFragment extends Fragment {
    private static final int UPDATE_TEXTVIEW = 0;
    private static int count = 0;
    private View view;
    private TextView tvTime;
    private Button btnStart, btnPause, btnResume, btnStop;
    private CountDownTimer countDownTimer;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_tomato_clock, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initEvents();
    }

    private void initViews() {
        btnStart = view.findViewById(R.id.btn_start);
        btnPause = view.findViewById(R.id.btn_stop);
        btnResume = view.findViewById(R.id.btn_continue);
        btnStop = view.findViewById(R.id.btn_close);
        tvTime = view.findViewById(R.id.tv_time);
    }

    private void initEvents() {
        btnStart.setOnClickListener(v -> {
            btnStart.setVisibility(View.GONE);
//            btnPause.setVisibility(View.VISIBLE);
            btnStop.setVisibility(View.VISIBLE);
            countDownTimer = new CountDownTimer(UserConfig.tomatoTime * 60 * 1000, 1000) {
                public void onTick(long millisUntilFinished) {
                    long s = millisUntilFinished / 1000;
                    String showTime = String.format("%02d:%02d", s / 60, s % 60);
                    tvTime.setText(showTime);
                }

                public void onFinish() {
                    tvTime.setText("25:00");
                }
            }.start();

        });
        btnPause.setOnClickListener(v -> {
            btnPause.setVisibility(View.GONE);
            btnResume.setVisibility(View.VISIBLE);
            btnStop.setVisibility(View.VISIBLE);
        });
        btnResume.setOnClickListener(v -> {
            btnPause.setVisibility(View.VISIBLE);
            btnResume.setVisibility(View.GONE);
            btnStop.setVisibility(View.GONE);
        });
        btnStop.setOnClickListener(v -> {
            btnStart.setVisibility(View.VISIBLE);
            btnResume.setVisibility(View.GONE);
            btnStop.setVisibility(View.GONE);

            countDownTimer.cancel();
            tvTime.setText("25:00");
        });
    }
}
