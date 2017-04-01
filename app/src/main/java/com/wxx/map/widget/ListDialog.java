package com.wxx.map.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wxx.map.R;
import com.wxx.map.adapter.ListAdapter;
import com.wxx.map.bean.ListData;

import java.util.List;

/**
 * 作者：Tangren_ on 2017/3/31 16:02.
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */

public class ListDialog extends PopupWindow {
    private TextView dis;
    private RecyclerView recyclerView;
    private AppCompatActivity mContext;
    private View view;
    private ListAdapter mAdapter;

    public ListDialog(AppCompatActivity context) {
        super(context);
        this.mContext = context;
        mAdapter = new ListAdapter();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.popu_view, null);
        dis = (TextView) view.findViewById(R.id.dis);
        recyclerView = (RecyclerView) view.findViewById(R.id.lRecyclerView);
        dis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);

        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(getPopHeight());
        this.setFocusable(false);
        this.setAnimationStyle(R.style.animBottom);
        this.setOutsideTouchable(false);
        ColorDrawable drawable = new ColorDrawable(Color.parseColor("#FFFFFF"));
        this.setBackgroundDrawable(drawable);
    }

    public void setAdd(List<ListData.ContentsBean> list) {
        mAdapter.add(list);
        recyclerView.setAdapter(mAdapter);
    }

    public void setMore(List<ListData.ContentsBean> list) {
        mAdapter.loadMore(list);
    }

    private int getPopHeight() {
        DisplayMetrics metrics = mContext.getResources().getDisplayMetrics();
        return (int) (metrics.heightPixels * 0.6);
    }

}
