package com.example.dailylist.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.example.dailylist.Activity.ShowTaskActivity;
import com.example.dailylist.Bean.TaskBean;
import com.example.dailylist.Dialog.ViewHolder;
import com.example.dailylist.R;
import com.example.dailylist.utils.Api.TaskApi;
import com.example.dailylist.utils.JsonUtil;
import com.example.dailylist.utils.Util;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @Author: 夜雨
 * @ClassName: TaskAdapter
 * @Description: 任务列表适配器
 * @Date: 2022/12/6 9:51
 * @Version: 1.0
 */
public class TaskAdapter extends CommonAdapter<TaskBean> {
    private ThreadPoolExecutor threadPool;
    private Context context;
    private Util mUtil;
    private List<TaskBean> taskList;

    public TaskAdapter(Context context, List<TaskBean> taskList) {
        super(context, taskList, R.layout.custom_task);
        this.context = context;
        this.taskList = taskList;
        mUtil = new Util();
        threadPool = new ThreadPoolExecutor(2, 5,
                1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
    }

    public void addTask(TaskBean taskBean) {
//        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Log.i("JSON", String.valueOf(taskBean.getStartTime()));
        Log.i("JSON.toJSONString(task)", JsonUtil.toJson(taskBean));
        taskList.add(JsonUtil.jsonToObj(JsonUtil.toJson(taskBean), TaskBean.class));
        notifyDataSetChanged();
    }

    private void initEvent(ViewHolder holder, TaskBean taskBean) {

        holder.setOnClickListener(R.id.cd_view, v -> {
            Intent intent = new Intent(context, ShowTaskActivity.class);

            Bundle bundle = new Bundle();
            bundle.putSerializable("task", taskBean);

            intent.putExtras(bundle);
            context.startActivity(intent);
        });

        holder.setOnCheckedChangeListener(R.id.cb_done, (buttonView, isChecked) -> {
            taskBean.setExecution(isChecked);
            threadPool.execute(() -> {
                JSONObject response = TaskApi.update(taskBean);
            });
        });
    }


    @Override
    public void convert(ViewHolder holder, TaskBean taskBean) {
        refershView(holder, taskBean);
        initEvent(holder, taskBean);
    }

    private void refershView(ViewHolder holder, TaskBean taskBean) {
        String showDate = mUtil.getShowDate(taskBean.getStartTime());
        holder.setText(R.id.tv_task_name, taskBean.getTaskName());
        holder.setText(R.id.tv_date, showDate);
        Log.i("taskBean.getExecution()", String.valueOf(taskBean.getExecution()));
        holder.setChecked(R.id.cb_done, taskBean.getExecution());
    }

}
