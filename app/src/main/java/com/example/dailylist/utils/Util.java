package com.example.dailylist.utils;

import android.util.Log;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.dailylist.Bean.TaskBean;
import com.example.dailylist.utils.Api.TaskApi;
import com.example.dailylist.utils.Api.TaskCompleteApi;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @Author: 夜雨
 * @ClassName: util
 * @Description: TODO
 * @Date: 2022/12/7 2:24
 * @Version: 1.0
 */

public class Util {

    private List<TaskBean> taskList = new ArrayList<>();
    private ExecutorService threadPool;
    private LocalDateTime checkDateTime;

    public Util(ExecutorService threadPool) {
        this.threadPool = threadPool;
    }

    public Util() {
    }

    public List<TaskBean> getTaskList() {
        return taskList;
    }

    public List<TaskBean> getTask() {
        JSONObject response = TaskApi.get();
        if (response == null) {
            return taskList;
        }
        JSONObject result = response.getJSONObject("result");
        JSONArray taskListJson = result.getJSONArray("list");
        for (int i = 0; i < taskListJson.size(); i++) {
            JSONObject taskJson = taskListJson.getJSONObject(i);
            TaskBean task = new TaskBean();

            int id = taskJson.getIntValue("id");

            task.setTaskId(id);
            task.setCategoryId(taskJson.getIntValue("category_id"));
            task.setQuadrant(taskJson.getIntValue("quadrant"));
            task.setTaskName(taskJson.getString("task_name"));
            task.setRepresent(taskJson.getString("represent"));
            task.setExecution(taskJson.getBooleanValue("execution"));
            task.setStartTime(Time.utcToLocalDateTime(taskJson.getString("start_time")));
            task.setEndTime(Time.utcToLocalDateTime(taskJson.getString("end_time")));

            List<LocalDateTime> executionTime = new ArrayList<>();
            response = TaskCompleteApi.get(id);
            if (response.getInteger("code") == 0) {
                JSONArray t = response.getJSONObject("result").getJSONArray("list");
                for (int j = 0; j < t.size(); j++) {
                    executionTime.add(Time.utcToLocalDateTime(t.getString(i)));
                }
            }
            task.setExecutionTime(executionTime);
            taskList.add(task);
            Log.i("util.taskList", String.valueOf(taskList.size()));
        }
        return taskList;
    }

    public List<TaskBean> getShowList(LocalDate day) {
        List<TaskBean> showTaskList = new ArrayList<>();
        LocalDateTime dayMax = LocalDateTime.of(day, LocalTime.MAX);
        LocalDateTime dayMin = dayMax.plusDays(-1);
        for (int i = 0; i < taskList.size(); i++) {
            TaskBean task = taskList.get(i);
            LocalDateTime startTime = task.getStartTime();
            LocalDateTime endTime = task.getEndTime();
            if (startTime.isBefore(dayMax) && endTime.isAfter(dayMin)) {
                showTaskList.add(task);
            }
        }
        return showTaskList;
    }

    public List<TaskBean> getQuadrantList(Integer idx) {
        List<TaskBean> quadrantTaskList = new ArrayList<>();
        for (int i = 0; i < taskList.size(); i++) {
            TaskBean task = taskList.get(i);
            if (task.getQuadrant().equals(idx)) {
                quadrantTaskList.add(task);
            }
        }
        return quadrantTaskList;
    }

    public String getShowDate(LocalDateTime startTime) {
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
