package com.example.dailylist.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;

/**
 * @Author: 夜雨
 * @ClassName: CommonDialog
 * @Description: TODO
 * @Date: 2022/12/11 12:08
 * @Version: 1.0
 */


public class DialogView extends Dialog {

    public DialogView(Context context, int layout, int style, int gravity) {
        super(context, style);
        setContentView(layout);
        setCanceledOnTouchOutside(true);
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = gravity;
        window.setAttributes(layoutParams);
    }
}