package com.example.dailylist.ui.list;

import android.content.Context;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ListView;
import androidx.lifecycle.ViewModel;
import com.example.dailylist.Bean.TaskBean;
import com.example.dailylist.utils.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @Author: 夜雨
 * @ClassName: CalendarViewModel
 * @Description: TODO
 * @Date: 2022/12/12 12:54
 * @Version: 1.0
 */

public abstract class ListViewModel extends ViewModel {
    private CalendarView calendarView;
    private ListView listView;
    private View view;
    private Context context;
    private List<TaskBean> taskList = new ArrayList<>();
    private Util myUtil;
    private ExecutorService threadPool;

    public ListViewModel() {
    }
}
