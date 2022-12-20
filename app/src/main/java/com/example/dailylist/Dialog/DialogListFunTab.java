package com.example.dailylist.Dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.example.dailylist.R;

/**
 * @Author: 夜雨
 * @ClassName: DiallogListFunTab
 * @Description: TODO
 * @Date: 2022/12/12 9:26
 * @Version: 1.0
 */

public class DialogListFunTab extends DialogView {
    private Activity activity;

    public DialogListFunTab(Context context, int gravity, Activity activity) {
        super(context, R.layout.dialog_list_fun_tab_layout, R.style.Theme_Dialog_White, gravity);
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
    }

    private void initData() {
    }

    private void refershView() {
    }

    private void initView() {
    }
}
