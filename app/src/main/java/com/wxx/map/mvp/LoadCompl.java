package com.wxx.map.mvp;

import android.util.Log;

import com.google.gson.Gson;
import com.wxx.map.bean.ListData;
import com.wxx.map.http.CallServer;
import com.wxx.map.http.HttpListener;
import com.wxx.map.utils.API;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.lang.ref.WeakReference;

/**
 * 作者：Tangren_ on 2017/4/1 11:30.
 * 邮箱：wu_tangren@163.com
 * TODO:一句话描述
 */

public class LoadCompl implements LoadPresetner {

    private OnLoadResult view;

    private WeakReference<OnLoadResult> mainActivityWeakReference;

    private static final String TAG = "LoadCompl";


    public LoadCompl(OnLoadResult view) {
        this.view = view;
        mainActivityWeakReference = new WeakReference<OnLoadResult>(view);
    }

    @Override
    public void doLoad(int page, int radius, String location) {
        final OnLoadResult view = mainActivityWeakReference.get();
        if (view != null) {
            StringBuffer buffer = new StringBuffer();
            buffer.append("ak=FEHzLBQiiGNeR8r0ad8cINBrRbaW4hHM&")
                    .append("geotable_id=165733&")
                    .append("location=" + location + "&")
                    .append("mcode=DF:39:DD:62:A6:59:0A:76:26:F1:77:AC:C8:63:9E:65:6B:DC:BA:FB;com.wxx.map&")
                    .append("radius=" + radius + "&")
                    .append("page_index=" + page);
            final Request<String> request = NoHttp.createStringRequest(API.URL + buffer.toString(), RequestMethod.GET);
            CallServer.getHttpclient().add(0, request, new HttpListener<String>() {
                @Override
                public void success(int what, Response<String> response) {
                    if (view != null) {
                        ListData listData = new Gson().fromJson(response.get(), ListData.class);
                        Log.d(TAG, listData.toString());
                        view.onResult(1, 20, 0, listData.getContents());

                    }
                }

                @Override
                public void fail(int what, Response<String> response) {
                    Log.d(TAG, "失败");
                }
            });
        } else {
            try {
                Log.d(TAG, "哇哦--资源已被销毁----");
            } catch (Exception e) {
                Log.d(TAG, e.getMessage());
            }
        }
    }
}
