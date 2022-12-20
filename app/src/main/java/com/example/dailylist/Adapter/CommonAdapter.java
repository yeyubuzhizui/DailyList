package com.example.dailylist.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import com.example.dailylist.Dialog.ViewHolder;

import java.util.List;

/**
 * @Author: 夜雨
 * @ClassName: CommonAdapter
 * @Description: TODO
 * @Date: 2022/12/12 0:32
 * @Version: 1.0
 */

public abstract class CommonAdapter<T> extends BaseAdapter {
    private Context mContext;
    private List<T> list;
    private LayoutInflater mInflater;
    private int layoutId;

    public CommonAdapter(Context context, List<T> list, int layoutId) {
        this.mContext = context;
        this.list = list;
        this.layoutId = layoutId;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public T getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);

        convert(holder, getItem(position));

        return holder.getConvertView();
    }

    /**
     * @Author: 夜雨
     * @Description: TODO
     * @Date: 2022/12/12 1:22
     * @Param: [holder, t]
     * @return: void
     **/
    public abstract void convert(ViewHolder holder, T t);

}
