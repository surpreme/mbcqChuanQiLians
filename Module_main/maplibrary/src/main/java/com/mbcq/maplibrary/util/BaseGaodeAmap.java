package com.mbcq.maplibrary.util;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.mbcq.baselibrary.BaseApplication;
import com.mbcq.baselibrary.util.log.LogUtils;
import com.mbcq.baselibrary.util.system.ToastUtils;

/**
 * @Auther: liziyang
 * @datetime: 2019-12-15
 * @desc:
 */
public class BaseGaodeAmap {
    private int continueCount = 0;

    /**
     * 启动单次客户端定位
     */
    public static void startSingleLocation(AMapLocationClient locationClientSingle, Context context) {
        if (null == locationClientSingle) {
            locationClientSingle = new AMapLocationClient(context);
        }

        AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
        //使用单次定位
        locationClientOption.setOnceLocation(true);
        // 地址信息
        locationClientOption.setNeedAddress(true);
        locationClientOption.setLocationCacheEnable(false);
        locationClientSingle.setLocationOption(locationClientOption);
        locationClientSingle.setLocationListener(locationSingleListener);
        locationClientSingle.startLocation();
    }

    /**
     * 启动单次客户端定位
     */
    public static void startSingleLocation(AMapLocationClient locationClientSingle, AMapLocationListener locationSingleListener, Context context) {
        if (null == locationClientSingle) {
            locationClientSingle = new AMapLocationClient(context);
        }

        AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
        //使用单次定位
        locationClientOption.setOnceLocation(true);
        // 地址信息
        locationClientOption.setNeedAddress(true);
        locationClientOption.setLocationCacheEnable(false);
        locationClientSingle.setLocationOption(locationClientOption);
        if (locationSingleListener != null)
            locationClientSingle.setLocationListener(locationSingleListener);
        locationClientSingle.startLocation();
    }

    /**
     * 停止单次客户端
     */
    void stopSingleLocation(AMapLocationClient locationClientSingle) {
        if (null != locationClientSingle) {
            locationClientSingle.stopLocation();
        }
    }

    /**
     * 启动连续客户端定位
     */
    void startContinueLocation(AMapLocationClient locationClientContinue, Context context) {
        if (null == locationClientContinue) {
            locationClientContinue = new AMapLocationClient(context);
        }

        //使用连续的定位方式  默认连续
        AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
        // 地址信息
        locationClientOption.setNeedAddress(true);
        locationClientContinue.setLocationOption(locationClientOption);
        locationClientContinue.setLocationListener(locationContinueListener);
        locationClientContinue.startLocation();
    }

    /**
     * 启动连续客户端定位
     */
    public static void startContinueLocation(AMapLocationClient locationClientContinue, AMapLocationListener locationContinueListener, Context context) {
        if (null == locationClientContinue) {
            locationClientContinue = new AMapLocationClient(context);
        }

        //使用连续的定位方式  默认连续
        AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
        // 地址信息
        locationClientOption.setNeedAddress(true);
        locationClientContinue.setLocationOption(locationClientOption);
        if (locationContinueListener != null)
            locationClientContinue.setLocationListener(locationContinueListener);
        locationClientContinue.startLocation();
    }

    /**
     * 停止连续客户端
     */
    void stopContinueLocation(AMapLocationClient locationClientContinue) {
        if (null != locationClientContinue) {
            locationClientContinue.stopLocation();
        }
    }

    /**
     * 单次客户端的定位监听
     */
    private static AMapLocationListener locationSingleListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            long callBackTime = System.currentTimeMillis();
            StringBuffer sb = new StringBuffer();
            sb.append("单次定位完成\n");
            sb.append("回调时间: " + GaodeLocationUtils.formatUTC(callBackTime, null) + "\n");
            if (null == location) {
                sb.append("定位失败：location is null!!!!!!!");
            } else {
                sb.append(GaodeLocationUtils.getLocationStr(location));
            }
            LogUtils.d(sb.toString());
            ToastUtils.showToast(BaseApplication.getContext(), sb.toString());
        }
    };

    /**
     * 连续客户端的定位监听
     */
    AMapLocationListener locationContinueListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation location) {
            continueCount++;
            long callBackTime = System.currentTimeMillis();
            StringBuffer sb = new StringBuffer();
            sb.append("持续定位完成 " + continueCount + "\n");
            sb.append("回调时间: " + GaodeLocationUtils.formatUTC(callBackTime, null) + "\n");
            if (null == location) {
                sb.append("定位失败：location is null!!!!!!!");
            } else {
                sb.append(GaodeLocationUtils.getLocationStr(location));
            }

            LogUtils.d(sb.toString());
        }
    };

    public static void destoryOnce(AMapLocationClient locationClientSingle) {
        if (null != locationClientSingle) {
            locationClientSingle.onDestroy();
            locationClientSingle = null;
        }

    }

    public static void destoryAlway(AMapLocationClient locationClientContinue) {

        if (null != locationClientContinue) {
            locationClientContinue.onDestroy();
            locationClientContinue = null;
        }
    }

}
