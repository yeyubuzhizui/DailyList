package com.example.dailylist.utils.Api;

import android.util.Log;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.dailylist.Bean.TaskBean;
import com.example.dailylist.UserConfig;
import com.example.dailylist.utils.OkHttpUtils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * @Author: 夜雨
 * @ClassName: Task
 * @Description: TODO
 * @Date: 2022/12/4 18:36
 * @Version: 1.0
 */

public class TaskApi {
    public static JSONObject get() {
        String uid = UserConfig.uid;
        String response = OkHttpUtils.builder().url(ApiConfig.BaseUrl + "/api/task/")
                .addHeader("Authorization", UserConfig.token)
                .addParam("uid", uid)
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .get()
                .sync();
        Log.i("heiye", response);
        return JSONArray.parseObject(response);
    }

    public static JSONObject create(TaskBean task) {
        Integer categoryId = task.getTaskId();
        Integer quadrant = task.getQuadrant();
        String taskName = task.getTaskName();
        String represent = task.getRepresent();
        LocalDateTime startTime = task.getStartTime();
        LocalDateTime endTime = task.getEndTime();
        OkHttpUtils okHttpUtils = OkHttpUtils.builder().url(ApiConfig.BaseUrl + "/api/task/create")
                .addHeader("Authorization", UserConfig.token)
                .addParam("uid", UserConfig.uid)
                .addParam("task_name", taskName)
                .addParam("start_time", String.valueOf(new Timestamp(startTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli())))
                .addParam("end_time", String.valueOf(new Timestamp(endTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli())))
                .addHeader("Content-Type", "application/json; charset=utf-8");
        if (represent != null) {
            okHttpUtils.addParam("represent", represent);
        }
        if (categoryId != null) {
            okHttpUtils.addParam("category_id", String.valueOf(categoryId));
        }
        if (quadrant != null) {
            Log.i("创建时", String.valueOf(quadrant));
            okHttpUtils.addParam("quadrant", String.valueOf(quadrant));
        }
        String response = okHttpUtils
                .post(false).sync();
        Log.i("insert", response);
        return JSONArray.parseObject(response);
    }

    public static JSONObject update(TaskBean task) {
        Integer taskId = task.getTaskId();
        Integer categoryId = task.getTaskId();
        Integer quadrant = task.getQuadrant();
        String taskName = task.getTaskName();
        Boolean execution = task.getExecution();
        String represent = task.getRepresent();
        LocalDateTime startTime = task.getStartTime();
        LocalDateTime endTime = task.getEndTime();
        String response = OkHttpUtils.builder().url(ApiConfig.BaseUrl + "/api/task/update")
                .addHeader("Authorization", UserConfig.token)
                .addParam("id", String.valueOf(taskId))
                .addParam("uid", UserConfig.uid)
                .addParam("category_id", String.valueOf(categoryId))
                .addParam("quadrant", String.valueOf(quadrant))
                .addParam("task_name", taskName)
                .addParam("represent", represent)
                .addParam("execution", String.valueOf(execution))
                .addParam("start_time", String.valueOf(new Timestamp(startTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli())))
                .addParam("end_time", String.valueOf(new Timestamp(endTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli())))
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .post(false)
                .sync();
        Log.i("taskUpdate", response);
        if ("Internal Server Error".equals(response)) {
            return null;
        }
        return JSONArray.parseObject(response);
    }
}
