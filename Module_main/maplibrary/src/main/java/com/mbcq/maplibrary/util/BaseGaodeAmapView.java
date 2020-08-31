package com.mbcq.maplibrary.util;

import android.content.Context;
import android.location.Location;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.mbcq.baselibrary.util.log.LogUtils;

/**
 * @Auther: liziyang
 * @datetime: 2019-12-15
 * @desc:
 */
public class BaseGaodeAmapView {
    /**
     * latitude = amapLocation.getLatitude();
     * longitude = amapLocation.getLongitude();
     */
    public static void setupMapView(AMap aMap, Context context) {
        // 设置默认定位按钮是否显示，非必需设置
        aMap.getUiSettings().setMyLocationButtonEnabled(true);
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        AMapLocationClient mlocationClient = new AMapLocationClient(context);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        mLocationOption.setGpsFirst(true);
//        aMap.animateCamera(CameraUpdateFactory.zoomTo(16));
        // 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false
        aMap.setMyLocationEnabled(true);
        aMap.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                LogUtils.d(location.getTime());
//                CameraUpdate mUpdata = CameraUpdateFactory.newCameraPosition(
//                        //15是缩放比例，0是倾斜度，30显示比例
//                        new CameraPosition(new LatLng(location.getLatitude(), location.getLongitude()), 17, 0, 30));
//                aMap.moveCamera(mUpdata);
            }
        });
    }

    public static void setupLocationStyle(AMap aMap,Boolean isShowSupportZoomControlsBtn) {
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 默认模式，连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动，1秒1次定位
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        // 设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒
        myLocationStyle.interval(1000);
        // 设置定位蓝点的Style
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.getUiSettings().setZoomControlsEnabled(isShowSupportZoomControlsBtn);

    }

}
