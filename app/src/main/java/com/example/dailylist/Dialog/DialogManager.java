package com.example.dailylist.Dialog;

import android.content.Context;
import android.view.Gravity;

/**
 * @Author: 夜雨
 * @ClassName: DialogManager
 * @Description: 提示框管理类
 * @Date: 2022/12/11 12:40
 * @Version: 1.0
 */

public class DialogManager {

    //单例模式
    private static volatile DialogManager mInstance = null;

    private DialogManager() {
    }

    public static DialogManager getInstance() {
        if (mInstance == null) {
            synchronized (DialogManager.class) {
                if (mInstance == null) {
                    mInstance = new DialogManager();
                }
            }
        }
        return mInstance;
    }

    public DialogView initView(Context mContext, int layout, int style) {
        return new DialogView(mContext, layout, style, Gravity.CENTER);
    }

    public DialogView initView(Context mContext, int layout, int style, int gravity) {
        return new DialogView(mContext, layout, style, gravity);
    }

    public void show(DialogView view) {
        if (view != null) {
            if (!view.isShowing()) {
                view.show();
            }
        }
    }

    public void hide(DialogView view) {
        if (view != null) {
            if (view.isShowing()) {
                view.dismiss();
            }
        }
    }

}
