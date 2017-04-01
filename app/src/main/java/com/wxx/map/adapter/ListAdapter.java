package com.wxx.map.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wxx.map.R;
import com.wxx.map.bean.ListData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：Tangren_ on 2017/4/1 10:25.
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */

public class ListAdapter extends RecyclerView.Adapter {

    private List<ListData.ContentsBean> list;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ListHolder(inflater.inflate(R.layout.list_item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ListData.ContentsBean bean = list.get(position);
        if (bean == null)
            return;
        ListHolder listHolder = (ListHolder) holder;
        listHolder.address.setText(bean.getAddress());
        listHolder.title.setText(bean.getTitle());
        listHolder.distance.setText("距离：" + bean.getDistance() + "米");
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public void loadMore(List<ListData.ContentsBean> list) {
        if (list == null) {
            add(list);
        } else {
            this.list.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void add(List<ListData.ContentsBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }


    class ListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.address)
        TextView address;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.distance)
        TextView distance;

        public ListHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
