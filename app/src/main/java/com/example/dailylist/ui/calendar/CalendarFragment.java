package com.example.dailylist.ui.calendar;
//TODO 日历的交互

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.dailylist.Adapter.TaskAdapter;
import com.example.dailylist.Bean.TaskBean;
import com.example.dailylist.R;
import com.example.dailylist.databinding.FragmentCalendarBinding;
import com.example.dailylist.utils.Util;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @ProjectName: DailyList
 * @ClassName: CalendarActivity
 * @Description: 日历
 * @Author: 夜雨不知醉
 * @Date: 2022/10/15
 * @Version: 1.0
 */
public class CalendarFragment extends Fragment {
    private FragmentCalendarBinding binding;
    private View root;
    private CalendarView calendarView;
    private ListView listView;
    private View view;
    private Context context;
    private List<TaskBean> taskList = new ArrayList<>();
    private Util myUtil;
    private ExecutorService threadPool;

//    public View onCreateView(@NonNull LayoutInflater inflater,
//                             ViewGroup container,
//                             Bundle savedInstanceState) {
//        CalendarViewModel calendarViewModel =
//                new ViewModelProvider(this).get(CalendarViewModel.class);
//
//        binding = FragmentCalendarBinding.inflate(inflater, container, false);
//        root = binding.getRoot();
//
//        lv = binding.lv;
//        return root;
//    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_calendar, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initEvents();
    }

    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initViews() {
        view = getView();
        calendarView = view.findViewById(R.id.calendarView);
        listView = view.findViewById(R.id.lv);
        context = getContext();
        threadPool = new ThreadPoolExecutor(2, 20,
                1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        myUtil = new Util(threadPool);

        threadPool.execute(() -> {
            myUtil.getTask();
            LocalDate showDay = LocalDate.now();
            List<TaskBean> showTaskList = myUtil.getShowList(showDay);
            getActivity().runOnUiThread(() -> {
                TaskAdapter taskAdapter = new TaskAdapter(context, showTaskList);
                listView.setAdapter(taskAdapter);
            });
        });

    }

    /**
     * @Author: 夜雨
     * @Description: 获取选中日期的任务清单
     * @Date: 2022/12/8 17:12
     * @Param: [day]
     * @return: java.util.List<com.example.dailylist.bean.taskBean>
     **/
    @NotNull
    private void initEvents() {
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            LocalDate pickDay = LocalDate.of(year, month + 1, dayOfMonth);
            Log.i("checkDay", pickDay.toString());
            List<TaskBean> showTaskList = myUtil.getShowList(pickDay);
            TaskAdapter taskAdapter = new TaskAdapter(context, showTaskList);
            listView.setAdapter(taskAdapter);
        });
    }
}