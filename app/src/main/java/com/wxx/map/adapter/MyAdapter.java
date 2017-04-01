package com.wxx.map.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wxx.map.R;

/**
 * 作者：Tangren_ on 2017/3/31 17:17.
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */

public class MyAdapter extends RecyclerView.Adapter {


    public MyAdapter() {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new MyHolder(inflater.inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MyHolder myHolder = (MyHolder) holder;
        ((MyHolder) holder).textView.setText("item" + position);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class MyHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
