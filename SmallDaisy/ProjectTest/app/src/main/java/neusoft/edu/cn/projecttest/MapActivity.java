package neusoft.edu.cn.projecttest;


import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;



/**

 * 此demo用来展示如何结合定位SDK实现定位，并使用MyLocationOverlay绘制定位位置 同时展示如何使用自定义图标绘制并点击时弹出泡泡

 *

 */

public class MapActivity extends Activity {



    // 定位相关

    LocationClient mLocClient;

    public MyLocationListenner myListener = new MyLocationListenner();

    private LocationMode mCurrentMode;

    BitmapDescriptor mCurrentMarker;



    MapView mMapView;

    BaiduMap mBaiduMap;



    // UI相关

    OnCheckedChangeListener radioButtonListener;

    Button requestLocButton;

    ToggleButton togglebtn = null;

    boolean isFirstLoc = true;// 是否首次定位



    @Override

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_map);

        judgePermission();

        requestLocButton = (Button) findViewById(R.id.button1);

        mCurrentMode = LocationMode.NORMAL;

        requestLocButton.setText("普通");

        OnClickListener btnClickListener = new OnClickListener() {

            public void onClick(View v) {

                switch (mCurrentMode) {

                    case NORMAL:

                        requestLocButton.setText("跟随");

                        mCurrentMode = LocationMode.FOLLOWING;

                        mBaiduMap

                                .setMyLocationConfigeration(new MyLocationConfiguration(

                                        mCurrentMode, true, mCurrentMarker));

                        break;

                    case COMPASS:

                        requestLocButton.setText("普通");

                        mCurrentMode = LocationMode.NORMAL;

                        mBaiduMap

                                .setMyLocationConfigeration(new MyLocationConfiguration(

                                        mCurrentMode, true, mCurrentMarker));

                        break;

                    case FOLLOWING:

                        requestLocButton.setText("罗盘");

                        mCurrentMode = LocationMode.COMPASS;

                        mBaiduMap

                                .setMyLocationConfigeration(new MyLocationConfiguration(

                                        mCurrentMode, true, mCurrentMarker));

                        break;

                }

            }

        };

        requestLocButton.setOnClickListener(btnClickListener);



        togglebtn = (ToggleButton) findViewById(R.id.togglebutton);

        togglebtn

                .setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {



                    @Override

                    public void onCheckedChanged(CompoundButton buttonView,

                                                 boolean isChecked) {

                        if (isChecked) {

                            // 普通地图

                            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

                        } else {

                            // 卫星地图

                            mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

                        }



                    }

                });



        // 地图初始化

        mMapView = (MapView) findViewById(R.id.bmapView);

        mBaiduMap = mMapView.getMap();

        // 开启定位图层

        mBaiduMap.setMyLocationEnabled(true);

        // 定位初始化

        mLocClient = new LocationClient(this);

        mLocClient.registerLocationListener(myListener);

        LocationClientOption option = new LocationClientOption();

        option.setOpenGps(true);// 打开gps

        option.setCoorType("bd09ll"); // 设置坐标类型

        option.setScanSpan(1000);// 设置发起定位请求的间隔时间为1000ms

        option.setIsNeedAddress(true);

        mLocClient.setLocOption(option);

        mLocClient.start();



    }



    /**

     * 定位SDK监听函数

     */

    public class MyLocationListenner implements BDLocationListener {



        @Override

        public void onReceiveLocation(BDLocation location) {

            // map view 销毁后不在处理新接收的位置

            if (location == null || mMapView == null)

                return;

            MyLocationData locData = new MyLocationData.Builder()

                    .accuracy(location.getRadius())

                    // 此处设置开发者获取到的方向信息，顺时针0-360

                    .direction(100).latitude(location.getLatitude())

                    .longitude(location.getLongitude()).build();

            mBaiduMap.setMyLocationData(locData);

            if (isFirstLoc) {

                isFirstLoc = false;

                LatLng ll = new LatLng(location.getLatitude(),

                        location.getLongitude());

                // MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);

                // 设置缩放比例,更新地图状态

                float f = mBaiduMap.getMaxZoomLevel();// 19.0

                MapStatusUpdate u = MapStatusUpdateFactory.newLatLngZoom(ll,

                        f - 2);

                mBaiduMap.animateMapStatus(u);

                //地图位置显示

                Toast.makeText(MapActivity.this, location.getAddrStr(),

                        Toast.LENGTH_SHORT).show();

            }



        }



        public void onReceivePoi(BDLocation poiLocation) {

        }

    }



    @Override

    protected void onPause() {

        mMapView.onPause();

        super.onPause();

    }



    @Override

    protected void onResume() {

        mMapView.onResume();

        super.onResume();

    }



    @Override

    protected void onDestroy() {

        // 退出时销毁定位

        mLocClient.stop();

        // 关闭定位图层

        mBaiduMap.setMyLocationEnabled(false);

        mMapView.onDestroy();

        mMapView = null;

        super.onDestroy();

    }


    //6.0之后要动态获取权限，重要！！！

    protected void judgePermission() {



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // 检查该权限是否已经获取

            // 权限是否已经 授权 GRANTED---授权  DINIED---拒绝



            // sd卡权限

            String[] SdCardPermission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

            if (ContextCompat.checkSelfPermission(this, SdCardPermission[0]) != PackageManager.PERMISSION_GRANTED) {

                // 如果没有授予该权限，就去提示用户请求

                ActivityCompat.requestPermissions(this, SdCardPermission, 100);

            }



            //手机状态权限

            String[] readPhoneStatePermission = {Manifest.permission.READ_PHONE_STATE};

            if (ContextCompat.checkSelfPermission(this, readPhoneStatePermission[0]) != PackageManager.PERMISSION_GRANTED) {

                // 如果没有授予该权限，就去提示用户请求

                ActivityCompat.requestPermissions(this, readPhoneStatePermission, 200);

            }



            //定位权限

            String[] locationPermission = {Manifest.permission.ACCESS_FINE_LOCATION};

            if (ContextCompat.checkSelfPermission(this, locationPermission[0]) != PackageManager.PERMISSION_GRANTED) {

                // 如果没有授予该权限，就去提示用户请求

                ActivityCompat.requestPermissions(this, locationPermission, 300);

            }



            String[] ACCESS_COARSE_LOCATION = {Manifest.permission.ACCESS_COARSE_LOCATION};

            if (ContextCompat.checkSelfPermission(this, ACCESS_COARSE_LOCATION[0]) != PackageManager.PERMISSION_GRANTED) {

                // 如果没有授予该权限，就去提示用户请求

                ActivityCompat.requestPermissions(this, ACCESS_COARSE_LOCATION, 400);

            }





            String[] READ_EXTERNAL_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE};

            if (ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE[0]) != PackageManager.PERMISSION_GRANTED) {

                // 如果没有授予该权限，就去提示用户请求

                ActivityCompat.requestPermissions(this, READ_EXTERNAL_STORAGE, 500);

            }



            String[] WRITE_EXTERNAL_STORAGE = {Manifest.permission.WRITE_EXTERNAL_STORAGE};

            if (ContextCompat.checkSelfPermission(this, WRITE_EXTERNAL_STORAGE[0]) != PackageManager.PERMISSION_GRANTED) {

                // 如果没有授予该权限，就去提示用户请求

                ActivityCompat.requestPermissions(this, WRITE_EXTERNAL_STORAGE, 600);

            }



        }else{

            //doSdCardResult();

        }

        //LocationClient.reStart();

    }

}
