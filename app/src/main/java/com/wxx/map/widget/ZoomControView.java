package com.wxx.map.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.wxx.map.R;

/**
 * 作者：Tangren_ on 2017/3/30 20:06.
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */

public class ZoomControView extends LinearLayout implements View.OnClickListener, BaiduMap.OnMapStatusChangeListener {

    //放大按钮
    private Button enlargeBtn;

    //缩小按钮
    private Button narrowBtn;

    //路况
    private Button roadlingt;

    //定位
    private Button location;

    private BaiduMap mBaiduMap;

    //地图状态
    private MapStatus mStatus;

    //地图最小缩小
    private float minZoomLevel;

    //地图最大放大
    private float maxZoomLevel;

    private LinearLayout itemView;

    private double mLatitude;
    private double mLongitude;

    public ZoomControView(Context context) {
        this(context, null);
    }

    public ZoomControView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomControView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        itemView = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.item_zoom, null);
        narrowBtn = (Button) itemView.findViewById(R.id.narrowBtn);
        enlargeBtn = (Button) itemView.findViewById(R.id.enlargeBtn);
        roadlingt = (Button) itemView.findViewById(R.id.roadlight);
        location = (Button) itemView.findViewById(R.id.location);
        narrowBtn.setOnClickListener(this);
        enlargeBtn.setOnClickListener(this);
        roadlingt.setOnClickListener(this);
        location.setOnClickListener(this);

        addView(itemView);
    }

    @Override
    public void onClick(View v) {
        mStatus = mBaiduMap.getMapStatus();
        switch (v.getId()) {
            case R.id.enlargeBtn:
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(mStatus.zoom + 1));
                controlZoomStatus();
                break;
            case R.id.narrowBtn:
                mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(mStatus.zoom - 1));
                controlZoomStatus();
                break;
            case R.id.roadlight:
                setTraffic();
                break;
            case R.id.location:
                setLocation();
                break;
            default:
                break;
        }

        //重新获取地图缩放状态
        mStatus = mBaiduMap.getMapStatus();
    }

    private void setTraffic() {
        if (mBaiduMap.isTrafficEnabled()) {
            mBaiduMap.setTrafficEnabled(false);
//            item.setTitle("实时交通ON");
            roadlingt.setBackgroundResource(R.mipmap.ic_homepage_roadlight);
        } else {
            mBaiduMap.setTrafficEnabled(true);
            roadlingt.setBackgroundResource(R.mipmap.ic_homepage_roadlight_pressed);
//            item.setTitle("实时交通OFF");
        }
    }

    //控制缩放状态
    private void controlZoomStatus() {
        float zoom = mBaiduMap.getMapStatus().zoom;
        if (zoom >= maxZoomLevel) {
            enlargeBtn.setEnabled(false);
        } else {
            enlargeBtn.setEnabled(true);
        }

        if (zoom <= minZoomLevel) {
            narrowBtn.setEnabled(false);
        } else {
            narrowBtn.setEnabled(true);
        }
    }


    public void setMapView(MapView mapView) {
        mBaiduMap = mapView.getMap();
        mBaiduMap.setOnMapStatusChangeListener(this);
        maxZoomLevel = mBaiduMap.getMaxZoomLevel();
        minZoomLevel = mBaiduMap.getMinZoomLevel();
        controlZoomStatus();
    }


    private void setLocation() {
        LatLng lng = new LatLng(mLatitude, mLongitude);
        MapStatusUpdate status = MapStatusUpdateFactory.newLatLng(lng);
        mBaiduMap.animateMapStatus(status);
    }


    public void setLocation(double mLatitude, double mLongitude) {
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
    }

    /**
     * @param mapStatus
     */
    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {
    }

    /**
     * @param mapStatus
     */
    @Override
    public void onMapStatusChange(MapStatus mapStatus) {
        controlZoomStatus();
    }

    /**
     * @param mapStatus
     */
    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {
    }

}
