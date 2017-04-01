package com.wxx.map;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 作者：Tangren_ on 2017/3/29 10:57.
 * 邮箱：wu_tangren@163.com
 * TODO:方向传感器
 */

public class MyOrentationListener implements SensorEventListener {

    private SensorManager mSensorManager;
    private Context mContext;
    private Sensor mSensor;

    private float lastX;


    public MyOrentationListener(Context mContext) {
        this.mContext = mContext;
    }

    public void start() {
        mSensorManager = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorManager != null) {
            //获得方向传感器
            mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        }
        if (mSensor != null) {
            mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    public void stop() {
        mSensorManager.unregisterListener(this);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ORIENTATION) {
            float x = event.values[SensorManager.DATA_X];
            if (Math.abs(x - lastX) > 1.0) {
                if (mListener != null) {
                    mListener.onOrientationChanged(x);
                }
            }

            lastX = x;
        }
    }

    //精度发生改变
    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    private OnOrientationListner mListener;

    public MyOrentationListener setmListener(OnOrientationListner mListener) {
        this.mListener = mListener;
        return this;
    }

    public interface OnOrientationListner {
        void onOrientationChanged(float x);
    }
}
