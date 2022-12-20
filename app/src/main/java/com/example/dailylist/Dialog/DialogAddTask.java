package com.example.dailylist.Dialog;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.alibaba.fastjson.JSONObject;
import com.example.dailylist.Bean.TaskBean;
import com.example.dailylist.R;
import com.example.dailylist.utils.Api.TaskApi;
import com.example.dailylist.utils.Util;

import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * @Author: 夜雨
 * @ClassName: DialogAddTask
 * @Description: TODO
 * @Date: 2022/12/11 20:27
 * @Version: 1.0
 */

public class DialogAddTask extends DialogView {
    private LinearLayout llayoutTask, llayoutCalendar, llayoutFunQuadrant;
    private TextView tv_date, tvQuadrant;
    private EditText et_todo;
    private ImageView img_send;

    private Util util;
    private ExecutorService threadPool;
    private Activity activity;
    private boolean isQuadrant;
    private Context context;
    private TaskBean taskBean;
    private OnTaskListening onTaskListening;
    private LocalDateTime localDateTime;

    public DialogAddTask(Context context, int gravity, Activity activity, boolean isQuadrant, OnTaskListening onTaskListening) {
        super(context, R.layout.dialog_add_task_layout, R.style.Theme_Dialog_White, gravity);
        this.onTaskListening = onTaskListening;
        this.context = context;
        this.isQuadrant = isQuadrant;
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        refershView();
        initData();
        initEvent();
    }

    private void initEvent() {
        llayoutCalendar.setOnClickListener(v -> {
            localDateTime = LocalDateTime.now();
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                    (view, year, month, dayOfMonth) -> {
                        Log.i("年", String.valueOf(year));
                        Log.i("年", String.valueOf(month));
                        Log.i("年", String.valueOf(dayOfMonth));
                        localDateTime = LocalDateTime.of(year, month + 1, dayOfMonth, 0, 0, 0);
                        final String data = (month + 1) + "月-" + dayOfMonth + "日 ";
                        Log.i("设置时间", localDateTime.toString());
                        taskBean.setStartTime(localDateTime);
                        taskBean.setEndTime(localDateTime);
                    },
                    localDateTime.getYear(), localDateTime.getMonthValue() - 1, localDateTime.getDayOfMonth());
            datePickerDialog.show();


        });

        tvQuadrant.setOnClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(context, tvQuadrant);
            popupMenu.getMenuInflater().inflate(R.menu.quadrant_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(item -> {
                String quadrantTitle = item.getTitle().toString();
                tvQuadrant.setText(quadrantTitle);
                switch (quadrantTitle) {
                    case "重要且紧急": {
                        taskBean.setQuadrant(1);
                        break;
                    }
                    case "重要不紧急": {
                        taskBean.setQuadrant(2);
                        break;
                    }
                    case "不重要但紧急": {
                        taskBean.setQuadrant(3);
                        break;
                    }
                    case "不重要不紧急": {
                        taskBean.setQuadrant(4);
                        break;
                    }
                    default: {
                        break;
                    }
                }
                return false;
            });
            popupMenu.show();
        });

        img_send.setOnClickListener(v -> {
            taskBean.setTaskName(String.valueOf(et_todo.getText()));
            threadPool.execute(() -> {
                Log.i("startTime", String.valueOf(taskBean.getStartTime()));
                Log.i("endTime", String.valueOf(taskBean.getEndTime()));
                JSONObject response = TaskApi.create(taskBean);
                if (response.getIntValue("code") == 0) {
                    JSONObject t = response.getJSONObject("result");
                    taskBean.setTaskId(t.getInteger("id"));
                    activity.runOnUiThread(() -> {
                        onTaskListening.getTask(taskBean);
                    });
                }
            });
            refershView();
            DialogManager.getInstance().hide(this);
        });
    }

    private void initData() {
        taskBean = new TaskBean();

        if (isQuadrant) {
            taskBean.setQuadrant(1);
        } else {
            taskBean.setQuadrant(0);
        }

        taskBean.setCategoryId(0);
        taskBean.setRepresent("");
        taskBean.setExecution(false);
        taskBean.setStartTime(LocalDateTime.now());
        taskBean.setEndTime(LocalDateTime.now());

        util = new Util();
        threadPool = new ThreadPoolExecutor(2, 20,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    private void refershView() {
        if (isQuadrant) {
            llayoutTask.setVisibility(View.GONE);
            llayoutFunQuadrant.setVisibility(View.VISIBLE);
        } else {
            llayoutTask.setVisibility(View.VISIBLE);
            llayoutFunQuadrant.setVisibility(View.GONE);
        }
        tv_date.setText("今天");
        et_todo.setText("");
    }

    private void initView() {
        tv_date = findViewById(R.id.tv_date);
        et_todo = findViewById(R.id.et_todo);

        llayoutTask = findViewById(R.id.llayout_fun_task);
        llayoutCalendar = findViewById(R.id.llayout_calendar);

        llayoutFunQuadrant = findViewById(R.id.llayout_fun_quadrant);
        tvQuadrant = findViewById(R.id.tv_quadrant);
        img_send = findViewById(R.id.img_send);
    }

    public interface OnTaskListening {
        void getTask(TaskBean taskBean);
    }

}
