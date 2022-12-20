package com.example.dailylist.Activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.alibaba.fastjson.JSONObject;
import com.example.dailylist.Bean.TaskBean;
import com.example.dailylist.MainActivity;
import com.example.dailylist.R;
import com.example.dailylist.utils.Api.TaskApi;
import com.example.dailylist.utils.Util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @Author: 夜雨
 * @ClassName: CustomAddTask
 * @Description: 显示任务详情
 * @Date: 2022/12/5 21:14
 * @Version: 1.0
 */

public class ShowTaskActivity extends Activity {
    private Integer taskId, categoryId, quadrant;
    private String taskName, represent;
    private LocalDateTime startTime, endTime;
    private TextView tvDate;
    private EditText etTitle, etRepresent;
    private Button btnOk;
    private Calendar calendar;
    private ThreadPoolExecutor threadPool;
    private Util util;
    private LocalDateTime localDateTime;
    private TaskBean taskBean;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_show_task);
        initViews();
        initEvents();
    }

    private void initEvents() {
        tvDate.setOnClickListener(v -> {
            localDateTime = LocalDateTime.now();
            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    (view, year, month, dayOfMonth) -> {
                        localDateTime = LocalDateTime.of(year, month, dayOfMonth, 0, 0, 0);
                        final String data = (month + 1) + "月-" + dayOfMonth + "日 ";
                    },
                    localDateTime.getYear(), localDateTime.getMonthValue(), localDateTime.getDayOfMonth());
            datePickerDialog.show();


            LocalDateTime endTime = localDateTime;
            taskBean.setStartTime(localDateTime);
            taskBean.setEndTime(endTime);
        });

        btnOk.setOnClickListener(v -> {

            taskName = etTitle.getText().toString();
            taskBean.setExecution(false);
            represent = etRepresent.getText().toString();
            threadPool.execute(() -> {
                JSONObject reponse = TaskApi.update(taskBean);
            });
            Intent intent = new Intent(ShowTaskActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    private void initViews() {
        calendar = Calendar.getInstance(Locale.CHINA);
        tvDate = findViewById(R.id.tv_date);
        etTitle = findViewById(R.id.et_title);
        etRepresent = findViewById(R.id.et_represent);
        btnOk = findViewById(R.id.btn_ok);

        threadPool = new ThreadPoolExecutor(2, 5,
                1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        util = new Util();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        taskBean = (TaskBean) bundle.getSerializable("task");

//        taskId = (Integer) bundle.getSerializable("taskId");
//        categoryId = (Integer) bundle.getSerializable("categoryId");
//        quadrant = (Integer) bundle.getSerializable("quadrant");
//        title = intent.getStringExtra("title");
//        represent = intent.getStringExtra("represent");
//        startTime = (LocalDateTime) intent.getSerializableExtra("startTime");
//        endTime = (LocalDateTime) intent.getSerializableExtra("endTime");

        taskId = taskBean.getTaskId();
        categoryId = taskBean.getCategoryId();
        quadrant = taskBean.getQuadrant();
        taskName = taskBean.getTaskName();
        represent = taskBean.getRepresent();
        startTime = taskBean.getStartTime();
        endTime = taskBean.getEndTime();

        tvDate.setText(getShowDate(startTime));
        etTitle.setText(taskName);
        etRepresent.setText(represent);
    }

    private String getShowDate(LocalDateTime startTime) {
        String showDate;
        DateTimeFormatter dfDateTime1 = DateTimeFormatter.ofPattern("MM月dd日");
        DateTimeFormatter dfDateTime2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        if (startTime.getYear() == LocalDate.now().getYear()) {
            showDate = dfDateTime1.format(startTime);
        } else {
            showDate = dfDateTime2.format(startTime);
        }
        return showDate;
    }
}
