package com.example.dailylist.ui.user;
//TODO 用户界面交互

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.dailylist.R;

/**
 * @ProjectName: DailyList
 * @ClassName: listActivity
 * @Description: 清单
 * @Author: 夜雨不知醉
 * @Date: 2022/10/15
 * @Version: 1.0
 */

public class UserFragment extends Fragment {
    private View view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initEvents();
    }

    private void initViews() {
        view = getView();
    }

    private void initEvents() {

    }
}
