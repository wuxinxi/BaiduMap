# BaiduMap
## 1.jar包+so库的引用
 初始化（在setConView()之前调用，建议在application） </br>
 SDKInitializer.initialize(getApplicationContext()); </br>

   MapStatusUpdate status = MapStatusUpdateFactory.zoomTo(15.0F); </br>
   mBaiduMap.setMapStatus(status); </br>

## 2.加入定位，集成方向传感器
  LocationClient </br>
  LocationClientOption定位的一些设置 </br>
  BDLocationListener </br>
      BDLocation </br>
      MyLocationData </br>
  自定义图标 </br>
  BitmapDescriptor </br>
  引入方向传感器 </br>
  SensorManager-Sensor </br>
  BDLocationListener对方向进行设置 </br>

## 3.模式切换 </br>
  mLocationMode:三种模式，NORMAL、FOLLOWING、COMPASS(圆盘,实时定位) </br>
  MyLocationConfiguration config = new MyLocationConfiguration(mLocationMode, true, mLoacationBitmap); </br>
  mBaiduMap.setMyLocationConfigeration(config); </br>

## 4.添加覆盖物，覆盖物的点击事件以及InfoWindow </br>
  Marker:覆盖物标示 </br>
  OverlayOptions 指定位置坐标 </br>
  Baidumap.setOnMarerClickListener </br>
  InfoWindow:BaiduMap.showInfowindow,BaiduMap.hideInfoWindow </br>


## 5.事件
  //覆盖物的点击事件 </br>
    mBaiduMap.setOnMarkerClickListener(this); </br>
  //地图的点击事件 </br>
    mBaiduMap.setOnMapClickListener(this); </br>
  //地图状态事件 </br> 
    mBaiduMap.setOnMapStatusChangeListener(this); </br>
## 6.添加导航
  注意： </br>
      1).删除armeabi-v7a so包才会验证成功，无fuck说 </br>
      2).TTS白名单注册（否认无导播声音） </br>
      3).权限问题 </br>
  初始化导航 </br>
     BaiduNaviManager.getInstance().init(this, mSDCardPath, APP_FOLDER_NAME, new BaiduNaviManager.NaviInitListener…… </br>
  设置导播声音 </br>
     Bundle bundle = new Bundle(); </br>
  // 必须设置APPID，否则会静音 </br>
     bundle.putString(BNCommonSettingParam.TTS_APP_ID, "0F5B74755E163F0570459A939D1DF18E"); </br>
     BNaviSettingManager.setNaviSdkParam(bundle); </br>

## 预览
<img src="https://raw.githubusercontent.com/wuxinxi/BaiduMap/master/img/ic_001.png" width="460" height="768"/> <br/>

<img src="https://raw.githubusercontent.com/wuxinxi/BaiduMap/master/img/ic_002.png" width="460" height="768"/> <br/>

<img src="https://raw.githubusercontent.com/wuxinxi/BaiduMap/master/img/ic_003.png" width="460" height="768"/> <br/>

<img src="https://raw.githubusercontent.com/wuxinxi/BaiduMap/master/img/ic_004.png" width="460" height="768"/> <br/>

<img src="https://raw.githubusercontent.com/wuxinxi/BaiduMap/master/img/ic_005.png" width="460" height="768"/> <br/>

