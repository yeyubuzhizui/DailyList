package com.example.dailylist.utils.Api;

import android.util.Log;
import com.alibaba.fastjson.JSONObject;
import com.example.dailylist.UserConfig;
import com.example.dailylist.utils.OkHttpUtils;

import java.time.LocalDateTime;

/**
 * @Author: 夜雨
 * @ClassName: taskComplete
 * @Description: TODO
 * @Date: 2022/12/4 20:05
 * @Version: 1.0
 */

public class TaskCompleteApi {
    public static JSONObject get(int taskId) {
        String response = OkHttpUtils.builder().url(ApiConfig.BaseUrl + "/api/taskComplete/")
                .addHeader("Authorization", UserConfig.token)
                .addParam("task_id", String.valueOf(taskId))
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .get()
                .sync();
        Log.i("TaskCompleteApi_get", response);
        return JSONObject.parseObject(response);
    }

    public static JSONObject create(int taskId, LocalDateTime executionTime) {
        String response = OkHttpUtils.builder().url(ApiConfig.BaseUrl + "/api/taskComplete/create")
                .addHeader("Authorization", UserConfig.token)
                .addParam("task_id", String.valueOf(taskId))
                .addParam("execution_time", String.valueOf(executionTime))
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .get()
                .sync();
        Log.i("TaskCompleteApi_get", response);
        return JSONObject.parseObject(response);
    }
}
