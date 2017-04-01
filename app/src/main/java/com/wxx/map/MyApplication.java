package com.wxx.map;

import android.app.Application;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.OkHttpNetworkExecutor;

/**
 * 作者：Tangren_ on 2017/3/31 16:00.
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */

public class MyApplication extends Application {
    
    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        NoHttp.initialize(this, new NoHttp.Config()
                .setNetworkExecutor(new OkHttpNetworkExecutor()));
        Logger.setDebug(true);
        Logger.setTag("----Debug日志：----");

    }

    public static MyApplication getInstance() {
        if (instance == null) {
            synchronized (MyApplication.class) {
                if (instance == null)
                    instance = new MyApplication();
            }
        }
        return instance;
    }

}
