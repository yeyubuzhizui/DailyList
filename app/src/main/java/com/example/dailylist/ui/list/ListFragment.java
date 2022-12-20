package com.example.dailylist.ui.list;
//TODO l的交互

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.dailylist.Adapter.TaskAdapter;
import com.example.dailylist.Bean.TaskBean;
import com.example.dailylist.Dialog.DialogAddTask;
import com.example.dailylist.Dialog.DialogListFunTab;
import com.example.dailylist.Dialog.DialogManager;
import com.example.dailylist.R;
import com.example.dailylist.utils.Util;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * @ProjectName: DailyList
 * @ClassName: listActivity
 * @Description: 清单
 * @Author: 夜雨不知醉
 * @Date: 2022/10/15
 * @Version: 1.0
 */
public class ListFragment extends Fragment {
    private ImageView imgAddTask;
    private ListView listView;
    private View view;
    private Context context;
    private List<TaskBean> taskList = new ArrayList<>();
    private Util mUtil;
    private ExecutorService threadPool;
    private DialogAddTask dialogAddTask;
    private DialogListFunTab dialogFunTab;
    private TaskAdapter taskAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_list, container, false);
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
        mUtil = new Util();

        threadPool = new ThreadPoolExecutor(2, 20,
                1L,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        threadPool.execute(() -> {
            taskList = mUtil.getTask();
            getActivity().runOnUiThread(() -> {
                TaskAdapter taskAdapter = new TaskAdapter(context, taskList);
                listView.setAdapter(taskAdapter);
            });
        });
    }

    private void initView() {
        Log.i("listFragment", "初始化Views");
        context = getContext();
        listView = view.findViewById(R.id.lv);
        imgAddTask = view.findViewById(R.id.img_addTask);
        dialogFunTab = new DialogListFunTab(context, Gravity.LEFT, getActivity());
        dialogAddTask = new DialogAddTask(context, Gravity.BOTTOM, getActivity(), false, taskBean -> {
            Log.i("addtask", taskBean.getStartTime().toString());
            taskAdapter.addTask(taskBean);
            taskBean.setStartTime(LocalDateTime.now());
            taskBean.setEndTime(LocalDateTime.now());
        });
    }


    private void initEvent() {
        imgAddTask.setOnClickListener(v -> {
            DialogManager.getInstance().show(dialogAddTask);
            taskAdapter = new TaskAdapter(context, taskList);
            listView.setAdapter(taskAdapter);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
//                openDrawer(GravityCompat.START);
                break;
            }
            default: {
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
