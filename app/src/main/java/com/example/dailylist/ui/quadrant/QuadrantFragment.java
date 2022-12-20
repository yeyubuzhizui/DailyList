package com.example.dailylist.ui.quadrant;
//TODO 四象限界面交互

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.dailylist.Adapter.TaskAdapter;
import com.example.dailylist.Bean.TaskBean;
import com.example.dailylist.Dialog.DialogAddTask;
import com.example.dailylist.Dialog.DialogManager;
import com.example.dailylist.R;
import com.example.dailylist.utils.Util;

import java.util.List;
import java.util.concurrent.*;

/**
 * @ProjectName: DailyList
 * @ClassName: QuadrantActivity
 * @Description: 四象限任务
 * @Author: 夜雨不知醉
 * @Date: 2022/10/15
 * @Version: 1.0
 */
public class QuadrantFragment extends Fragment {
    private View view;
    private ListView lv_quadrant1, lv_quadrant2, lv_quadrant3, lv_quadrant4;
    private TextView tvQuadrant;
    private ImageView imgAddTask;
    private List<TaskBean> taskList;
    private ExecutorService threadPool;
    private Util util;
    private Context context;
    private DialogAddTask dialogAddTask;
    private TaskAdapter taskAdapter1, taskAdapter2, taskAdapter3, taskAdapter4;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_quadrant, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initData();
        initEvent();
    }

    private void initData() {
        util = new Util(threadPool);
        threadPool = new ThreadPoolExecutor(2, 20,
                1L, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        threadPool.execute(() -> {
            util.getTask();
            getActivity().runOnUiThread(() -> {
                List<TaskBean> quadrantList1 = util.getQuadrantList(1);
                List<TaskBean> quadrantList2 = util.getQuadrantList(2);
                List<TaskBean> quadrantList3 = util.getQuadrantList(3);
                List<TaskBean> quadrantList4 = util.getQuadrantList(4);
                taskAdapter1 = new TaskAdapter(context, quadrantList1);
                taskAdapter2 = new TaskAdapter(context, quadrantList2);
                taskAdapter3 = new TaskAdapter(context, quadrantList3);
                taskAdapter4 = new TaskAdapter(context, quadrantList4);
                lv_quadrant1.setAdapter(taskAdapter1);
                lv_quadrant2.setAdapter(taskAdapter2);
                lv_quadrant3.setAdapter(taskAdapter3);
                lv_quadrant4.setAdapter(taskAdapter4);
            });
        });

        taskList = util.getTaskList();
        dialogAddTask = new DialogAddTask(context, Gravity.BOTTOM, getActivity(), true, taskBean -> {
            switch (taskBean.getQuadrant()) {
                case 1: {
                    taskAdapter1.addTask(taskBean);
                    break;
                }
                case 2: {
                    taskAdapter2.addTask(taskBean);
                    break;
                }
                case 3: {
                    taskAdapter3.addTask(taskBean);
                    break;
                }
                case 4: {
                    taskAdapter4.addTask(taskBean);
                    break;
                }

                default: {
                    break;
                }
            }
            taskBean.setQuadrant(0);
        });
    }

    private void initView() {
        context = getContext();
        view = getView();
        lv_quadrant1 = view.findViewById(R.id.lv_quadrant1);
        lv_quadrant2 = view.findViewById(R.id.lv_quadrant2);
        lv_quadrant3 = view.findViewById(R.id.lv_quadrant3);
        lv_quadrant4 = view.findViewById(R.id.lv_quadrant4);

        imgAddTask = view.findViewById(R.id.img_addTask);
    }

    private void initEvent() {
        imgAddTask.setOnClickListener(v -> {
            DialogManager.getInstance().show(dialogAddTask);
        });
    }
}
