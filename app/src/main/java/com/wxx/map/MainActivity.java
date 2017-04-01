package com.wxx.map;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomControls;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.navisdk.adapter.BNCommonSettingParam;
import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviSettingManager;
import com.baidu.navisdk.adapter.BaiduNaviManager;
import com.wxx.map.adapter.MyAdapter;
import com.wxx.map.bean.ListData;
import com.wxx.map.mvp.LoadCompl;
import com.wxx.map.mvp.LoadPresetner;
import com.wxx.map.mvp.OnLoadResult;
import com.wxx.map.widget.ListDialog;
import com.wxx.map.widget.ZoomControView;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements BaiduMap.OnMarkerClickListener
        , BaiduMap.OnMapClickListener, BaiduMap.OnMapStatusChangeListener, OnLoadResult {

    @BindView(R.id.mMapView)
    MapView mMapView;

    private static final String TAG = "MainActivity";
    @BindView(R.id.dh)
    FloatingActionButton dh;
    @BindView(R.id.mn)
    FloatingActionButton mn;
    @BindView(R.id.zoom)
    ZoomControView zoom;
    @BindView(R.id.list)
    Button list;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.linerLayout)
    LinearLayout linerLayout;

    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private MyLoacationListener myLocationListener;
    private boolean isFirstIn = true;

    private double mLatitude;
    private double mLongitude;
    private BitmapDescriptor mLoacationBitmap;

    private MyOrentationListener myOrentationListener;
    private float mCurrentX;//当前位置

    private MyLocationConfiguration.LocationMode mLocationMode;

    //覆盖物
    private BitmapDescriptor mMarker;

    //infowindow
    private BitmapDescriptor mBitmapWindow;
    private InfoWindow infoWindow;

    //显示的动画
    private TranslateAnimation shwoAction;
    //隐藏时的动画
    private TranslateAnimation hideAction;

    //当前选择的经纬度
    private LatLng currentLatLng = null;

    //导航相关权限
    private static final String[] authBaseArr = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION};
    private static final String[] authComArr = {Manifest.permission.READ_PHONE_STATE};
    private static final int authBaseRequestCode = 1;
    private static final int authComRequestCode = 2;
    private String APP_FOLDER_NAME = "TMap";
    private String mSDCardPath = null;
    public static final String ROUTE_PLAN_NODE = "routePlanNode";
    public static List<Activity> activityList = new LinkedList<Activity>();
    private boolean hasInitSuccess = false;//百度导航是否初始化成功
    private boolean hasRequestComAuth = false;

    //popuwidow
    private ListDialog mDialog;
    private LoadPresetner mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_main_custom);
        ButterKnife.bind(this);
        initView();
        initMarker();
        initLocation();


        if (initDirs()) {
            initNavigatin();
        }

    }

    String authinfo = null;

    /***************************************
     * 导航部分
     ***************************************************/
    private void initNavigatin() {
        //申请权限
        if (Build.VERSION.SDK_INT >= 23) {
            if (!hasBasePhoneAuth()) {
                this.requestPermissions(authBaseArr, authBaseRequestCode);
                return;
            }
        }
        //初始化导航
        BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener() {
            @Override
            public void onAuthResult(int status, String msg) {
                if (0 == status) {
                    authinfo = "key校验成功!";
                } else {
                    authinfo = "key校验失败, " + msg;
                }
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, authinfo, Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void initStart() {
                Toast.makeText(MainActivity.this, "百度导航初始化开始", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void initSuccess() {
                Toast.makeText(MainActivity.this, "百度导航初始化成功", Toast.LENGTH_SHORT).show();
                hasInitSuccess = true;
                initSetting();
            }

            @Override
            public void initFailed() {
                Toast.makeText(MainActivity.this, "百度导航引擎初始化失败", Toast.LENGTH_SHORT).show();
            }
        }, null, ttsHandler, ttsPlayStateListener);
    }


    /**
     * 内部TTS播报状态回传handler
     */
    private Handler ttsHandler = new Handler() {
        public void handleMessage(Message msg) {
            int type = msg.what;
            switch (type) {
                case BaiduNaviManager.TTSPlayMsgType.PLAY_START_MSG: {
                    Log.d(TAG, "Handler : TTS play start");
                    break;
                }
                case BaiduNaviManager.TTSPlayMsgType.PLAY_END_MSG: {
                    Log.d(TAG, "Handler : TTS play end");
                    break;
                }
                default:
                    showToastMsg("TTS验证失败");
                    Log.d(TAG, "TTS验证失败");
                    break;
            }
        }
    };

    private void initSetting() {
        // BNaviSettingManager.setDayNightMode(BNaviSettingManager.DayNightMode.DAY_NIGHT_MODE_DAY);
        BNaviSettingManager
                .setShowTotalRoadConditionBar(BNaviSettingManager.PreViewRoadCondition.ROAD_CONDITION_BAR_SHOW_ON);
        BNaviSettingManager.setVoiceMode(BNaviSettingManager.VoiceMode.Veteran);
        // BNaviSettingManager.setPowerSaveMode(BNaviSettingManager.PowerSaveMode.DISABLE_MODE);
        BNaviSettingManager.setRealRoadCondition(BNaviSettingManager.RealRoadCondition.NAVI_ITS_ON);
        Bundle bundle = new Bundle();
        // 必须设置APPID，否则会静音
        bundle.putString(BNCommonSettingParam.TTS_APP_ID, "0F5B74755E163F0570459A939D1DF18E");
        Log.d(TAG, "设置APPID");
        BNaviSettingManager.setNaviSdkParam(bundle);
    }

    /**
     * 内部TTS播报状态回调接口
     */
    private BaiduNaviManager.TTSPlayStateListener ttsPlayStateListener = new BaiduNaviManager.TTSPlayStateListener() {

        @Override
        public void playEnd() {
            Log.d(TAG, "TTSPlayStateListener : TTS play end");
        }

        @Override
        public void playStart() {
            Log.d(TAG, "TTSPlayStateListener : TTS play start");
        }
    };

    public void showToastMsg(final String msg) {
        MainActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //初始化文件路径
    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    //判断是否有Sd卡
    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }


    private boolean hasBasePhoneAuth() {
        if (Build.VERSION.SDK_INT >= 23) {
            PackageManager pm = this.getPackageManager();
            for (String auth : authComArr) {
                if (pm.checkPermission(auth, this.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    private void routeplanToNavi(LatLng currentLatLng, boolean isSimulation) {
        BNRoutePlanNode.CoordinateType mCoordinateType = BNRoutePlanNode.CoordinateType.BD09LL;
        if (!hasInitSuccess) {
            Toast.makeText(MainActivity.this, "还未初始化!", Toast.LENGTH_SHORT).show();
        }
        // 权限申请
        if (Build.VERSION.SDK_INT >= 23) {
            // 保证导航功能完备
            if (!hasCompletePhoneAuth()) {
                if (!hasRequestComAuth) {
                    hasRequestComAuth = true;
                    this.requestPermissions(authComArr, authComRequestCode);
                    return;
                } else {
                    Toast.makeText(MainActivity.this, "没有完备的权限!", Toast.LENGTH_SHORT).show();
                }
            }

        }

        BNRoutePlanNode sNode = null;
        BNRoutePlanNode eNode = null;


//        sNode = new BNRoutePlanNode(113.9512500000, 22.5489710000, "出发地", null, mCoordinateType);
//        eNode = new BNRoutePlanNode(113.9410840000, 22.5460020000, "终点站", null, mCoordinateType);

        sNode = new BNRoutePlanNode(mLongitude, mLatitude, "起始地", null, mCoordinateType);
        eNode = new BNRoutePlanNode(currentLatLng.longitude, currentLatLng.latitude, "目的地", null, mCoordinateType);

        if (sNode != null && eNode != null) {
            List<BNRoutePlanNode> list = new ArrayList<BNRoutePlanNode>();
            list.add(sNode);
            list.add(eNode);
            BaiduNaviManager.getInstance().launchNavigator(this, list, 1, isSimulation, new DemoRoutePlanListener(sNode));
        }
    }


    public class DemoRoutePlanListener implements BaiduNaviManager.RoutePlanListener {

        private BNRoutePlanNode mBNRoutePlanNode = null;

        public DemoRoutePlanListener(BNRoutePlanNode node) {
            mBNRoutePlanNode = node;
        }

        @Override
        public void onJumpToNavigator() {

            //设置途径点以及resetEndNode会回调该接口

            for (Activity ac : activityList) {
                if (ac.getClass().getName().endsWith("BNGuideActivity")) {
                    return;
                }
            }
            Intent intent = new Intent(MainActivity.this, BNGuideActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable(ROUTE_PLAN_NODE, (BNRoutePlanNode) mBNRoutePlanNode);
            intent.putExtras(bundle);
            startActivity(intent);

        }

        @Override
        public void onRoutePlanFailed() {
            // TODO Auto-generated method stub
            Toast.makeText(MainActivity.this, "算路失败", Toast.LENGTH_SHORT).show();
        }
    }


    private boolean hasCompletePhoneAuth() {
        // TODO Auto-generated method stub

        PackageManager pm = this.getPackageManager();
        for (String auth : authComArr) {
            if (pm.checkPermission(auth, this.getPackageName()) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    /*****************************************************************************************/
    private void initMarker() {
        mMarker = BitmapDescriptorFactory.fromResource(R.mipmap.ic_mb);
        addOverLays(MapCar.infos);
    }

    private void initView() {

        shwoAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -1.0f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        shwoAction.setDuration(300);

        hideAction = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f);
        hideAction.setDuration(300);


        mBaiduMap = mMapView.getMap();
        //隐藏缩放控件
        mMapView.showZoomControls(false);
        //是否显示比例尺，默认true
        mMapView.showScaleControl(true);
        zoom.setMapView(mMapView);
        //隐藏logo
        View child = mMapView.getChildAt(1);
        if (child != null && (child instanceof ImageView || child instanceof ZoomControls))
            child.setVisibility(View.GONE);

        //覆盖物的点击事件
        mBaiduMap.setOnMarkerClickListener(this);
        //地图的点击事件
        mBaiduMap.setOnMapClickListener(this);
        //地图状态事件
        mBaiduMap.setOnMapStatusChangeListener(this);
        mBaiduMap.setMyLocationEnabled(true);//开启定位图层
        MapStatusUpdate status = MapStatusUpdateFactory.zoomTo(15.0F);
        mBaiduMap.setMapStatus(status);


        //Bottomsheet

        initBottomSheet();


        mDialog = new ListDialog(this);
        mPresenter = new LoadCompl(this);
    }

    private void initBottomSheet() {
        final BottomSheetBehavior sheetBehavior = BottomSheetBehavior.from(linerLayout);

        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(manager);
        MyAdapter adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

//        sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);//半打开
        sheetBehavior.setHideable(true);
//
        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
    }


    //定位
    private void initLocation() {

        mLocationClient = new LocationClient(getApplicationContext());
        myLocationListener = new MyLoacationListener();
        mLocationClient.registerLocationListener(myLocationListener);

        LocationClientOption options = new LocationClientOption();
        options.setCoorType("bd09ll");//坐标类型
        options.setIsNeedAddress(true);
        options.setOpenGps(true);
        options.setScanSpan(1000);

        mLocationClient.setLocOption(options);

        //模式
        mLocationMode = MyLocationConfiguration.LocationMode.NORMAL;

        mLoacationBitmap = BitmapDescriptorFactory.fromResource(R.mipmap.navi_map_gps_locked);

        myOrentationListener = new MyOrentationListener(getApplicationContext());
        myOrentationListener.setmListener(new MyOrentationListener.OnOrientationListner() {
            @Override
            public void onOrientationChanged(float x) {
                mCurrentX = x;
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_1: //普通地图
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.item_2://卫星地图
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;
            case R.id.item_3:
                if (mBaiduMap.isTrafficEnabled()) {
                    mBaiduMap.setTrafficEnabled(false);
                    item.setTitle("实时交通ON");
                } else {
                    mBaiduMap.setTrafficEnabled(true);
                    item.setTitle("实时交通OFF");
                }
                break;
            case R.id.item_4:
                mLocationMode = MyLocationConfiguration.LocationMode.NORMAL;
                break;
            case R.id.item_5://跟随模式
                mLocationMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                break;
            case R.id.item_6://罗盘模式
                mLocationMode = MyLocationConfiguration.LocationMode.COMPASS;
                break;
            case R.id.item_7:
                addOverLays(MapCar.infos);
                break;
            case R.id.item_8:
                Toast.makeText(this, currentLatLng.latitude + "\n" + currentLatLng.longitude, Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addOverLays(List<MapCar> infos) {
        //首先清楚定位的浮层
        mBaiduMap.clear();
        LatLng latLng = null;
        Marker marker = null;
        OverlayOptions options;
        for (MapCar car : infos) {
            //经纬度
            latLng = new LatLng(car.getLatitude(), car.getLongitude());
            //图标
            options = new MarkerOptions().position(latLng).icon(mMarker).zIndex(5);
            marker = (Marker) mBaiduMap.addOverlay(options);
            Bundle bundle = new Bundle();
            bundle.putParcelable("car", car);
            marker.setExtraInfo(bundle);
        }

        //定位到覆盖物的位置，应该是最后一个覆盖物的位置，如果不想定位到覆盖物的位置而是定位到人的位置，注释掉
//        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLng);
//        mBaiduMap.setMapStatus(msu);
    }

    @OnClick({R.id.dh, R.id.mn, R.id.list})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.dh:
                routeplanToNavi(currentLatLng, true);
                break;
            case R.id.mn:
                routeplanToNavi(currentLatLng, false);
                break;
            case R.id.list:
                mPresenter.doLoad(0, 1000, mLongitude + "," + mLatitude);
                break;
            default:
                break;
        }

    }

    //覆盖物的点击事件
    @Override
    public boolean onMarkerClick(Marker marker) {
        Bundle bundle = marker.getExtraInfo();
        MapCar car = (MapCar) bundle.getParcelable("car");

        TextView infoText = new TextView(getApplicationContext());
        infoText.setBackgroundResource(R.mipmap.location_tips);
        infoText.setPadding(30, 20, 30, 30);
        infoText.setText(car.getName());
        infoText.setTextColor(Color.parseColor("#FFFFFF"));

        mBitmapWindow = BitmapDescriptorFactory.fromView(infoText);
        final LatLng lng = marker.getPosition();
        Point p = mBaiduMap.getProjection().toScreenLocation(lng);
        p.y -= 47;
        currentLatLng = mBaiduMap.getProjection().fromScreenLocation(p);

        infoWindow = new InfoWindow(mBitmapWindow, currentLatLng, 0, new InfoWindow.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick() {
                mBaiduMap.hideInfoWindow();
                stopAnimation();
            }
        });
        mBaiduMap.showInfoWindow(infoWindow);
        startAnimation();
        return true;
    }

    private void startAnimation() {
        if (dh.getVisibility() == View.VISIBLE || mn.getVisibility() == View.VISIBLE)
            return;
        dh.startAnimation(shwoAction);
        mn.startAnimation(shwoAction);
        dh.setVisibility(View.VISIBLE);
        mn.setVisibility(View.VISIBLE);
    }

    private void stopAnimation() {
        if (dh.getVisibility() == View.GONE || mn.getVisibility() == View.GONE)
            return;
        dh.startAnimation(hideAction);
        mn.startAnimation(hideAction);
        dh.setVisibility(View.GONE);
        mn.setVisibility(View.GONE);
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mBaiduMap.hideInfoWindow();
        stopAnimation();
    }

    @Override
    public boolean onMapPoiClick(MapPoi mapPoi) {
        return false;
    }


    /**
     * 手势操作地图
     *
     * @param mapStatus
     */
    @Override
    public void onMapStatusChangeStart(MapStatus mapStatus) {

    }

    /**
     * 地图变化中
     *
     * @param mapStatus
     */
    @Override
    public void onMapStatusChange(MapStatus mapStatus) {
        mBaiduMap.hideInfoWindow();
        stopAnimation();
    }

    /**
     * 地图状态改变
     *
     * @param mapStatus
     */
    @Override
    public void onMapStatusChangeFinish(MapStatus mapStatus) {

    }


    private class MyLoacationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            MyLocationData data = new MyLocationData.Builder()
                    .direction(mCurrentX)//方向
                    .accuracy(bdLocation.getRadius())
                    .latitude(bdLocation.getLatitude())
                    .longitude(bdLocation.getLongitude())
                    .build();

            mBaiduMap.setMyLocationData(data);

            //得到经纬度
            mLatitude = bdLocation.getLatitude();
            mLongitude = bdLocation.getLongitude();

            zoom.setLocation(mLatitude, mLongitude);

            /**
             * NORMAL:不会实时更新位置
             * COMPASS:实时更新位置,罗盘
             * FOLLOWING:跟随模式
             */
            MyLocationConfiguration config = new MyLocationConfiguration(mLocationMode, true, mLoacationBitmap);
            mBaiduMap.setMyLocationConfigeration(config);
//            mBaiduMap.setMyLocationEnabled(true);//当不需要定位图层时关闭定位图层

            if (isFirstIn) {
                LatLng latLbg = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());//经纬度
                MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(latLbg);
                mBaiduMap.animateMapStatus(msu);
                isFirstIn = false;

                Toast.makeText(MainActivity.this, bdLocation.getAddrStr(), Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }
    }

    /**
     * mvp返回结果
     *
     * @param code
     * @param total
     * @param page
     * @param list
     */
    @Override
    public void onResult(int code, int total, int page, List<ListData.ContentsBean> list) {
        mDialog.setAdd(list);
        View parent = ((ViewGroup) this.findViewById(android.R.id.content)).getChildAt(0);
        mDialog.showAtLocation(parent, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT > 19) {
            getWindow().getDecorView()
                    .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //开启定位
        mBaiduMap.setMyLocationEnabled(true);
        if (!mLocationClient.isStarted())
            mLocationClient.start();
        //开启方向传感器
        myOrentationListener.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onStop();
        mMapView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //结束定位
        mBaiduMap.setMyLocationEnabled(false);
        mLocationClient.stop();
        //关闭方向传感器
        myOrentationListener.stop();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == authBaseRequestCode) {
            for (int ret : grantResults) {
                if (ret == 0) {
                    continue;
                } else {
                    Toast.makeText(MainActivity.this, "缺少导航基本的权限!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            initNavigatin();
        } else if (requestCode == authComRequestCode) {
            for (int ret : grantResults) {
                if (ret == 0) {
                    continue;
                }
            }
            routeplanToNavi(currentLatLng, true);
        }

    }
}
