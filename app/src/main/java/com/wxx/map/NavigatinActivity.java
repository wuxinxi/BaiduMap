package com.wxx.map;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mapapi.map.MapView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 作者：Tangren_ on 2017/3/29 18:20.
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */

public class NavigatinActivity extends AppCompatActivity {
    @BindView(R.id.mMapView)
    MapView mMapView;
    @BindView(R.id.fab)
    FloatingActionButton fab;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }


    @OnClick(R.id.fab)
    public void onClick() {
    }
}
