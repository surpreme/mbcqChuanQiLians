package com.mbcq.maplibrary


import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.animation.LinearInterpolator
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.*
import com.amap.api.maps.model.animation.Animation
import com.amap.api.maps.model.animation.RotateAnimation
import com.amap.api.maps.model.animation.TranslateAnimation
import com.mbcq.baselibrary.BaseApplication
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.util.system.ToastUtils
import com.mbcq.maplibrary.util.GaodeLocationUtils
import kotlinx.android.synthetic.main.activity_location.*


/**
 * @author: lzy
 * @time: 2018.08.25
 */

abstract class BaseLocationActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseMVPActivity<V, T>(), BaseView {
    private var mAMap: AMap? = null
    private var mLatLng = LatLng(0.0, 0.0)


    protected fun initGaoDeAmapView(savedInstanceState: Bundle?) {
        location_map.onCreate(savedInstanceState)
        mAMap = location_map.map


        mAMap?.let {
            // 设置默认定位按钮是否显示，非必需设置
//            it.uiSettings.isMyLocationButtonEnabled = true

            startSingleLocation()
            it.setOnMapLoadedListener {
                addMarkerInScreenCenter()
            }
            it.setOnCameraChangeListener(object : AMap.OnCameraChangeListener {
                override fun onCameraChangeFinish(p0: CameraPosition?) {
                    startJumpAnimation()

                }

                override fun onCameraChange(p0: CameraPosition?) {
                }

            })

        }
    }

    /**
     * 屏幕中心marker 跳动
     */
    open fun startJumpAnimation() {
        if (locationMarker != null) {
            //根据屏幕距离计算需要移动的目标点
            val latLng: LatLng = locationMarker?.position!!
            val point: Point = mAMap?.projection?.toScreenLocation(latLng)!!
            point.y -= ScreenSizeUtils.dip2px(mContext, 125f)
            val target: LatLng = mAMap?.projection?.fromScreenLocation(point)!!
            //使用TranslateAnimation,填写一个需要移动的目标点
            val animation: Animation = TranslateAnimation(target)
            animation.setInterpolator { input -> // 模拟重加速度的interpolator
                if (input <= 0.5) {
                    (0.5f - 2 * (0.5 - input) * (0.5 - input)).toFloat()
                } else {
                    (0.5f - Math.sqrt((input - 0.5f) * (1.5f - input).toDouble())).toFloat()
                }
            }
            //整个移动所需要的时间
            animation.setDuration(600)
            //设置动画
            locationMarker?.setAnimation(animation)
            //开始动画
            locationMarker?.startAnimation()
        } else {
            Log.e("ama", "screenMarker is null")
        }
    }

    private var locationMarker: Marker? = null
    private fun addMarkerInScreenCenter() {
        val latLng: LatLng = mAMap?.cameraPosition?.target!!
        val screenPosition: Point = mAMap?.projection?.toScreenLocation(latLng)!!
        locationMarker = mAMap?.addMarker(MarkerOptions()
                .anchor(0.5f, 0.5f)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.purple_pin)))
        //设置Marker在屏幕上,不跟随地图移动
        locationMarker?.setPositionByPixels(screenPosition.x, screenPosition.y)
        locationMarker?.zIndex = 1f
    }

    /**
     * 移动到指定经纬度
     */
    protected fun initAMap(latLng: LatLng?, aMap: AMap) {
        val cameraPosition = CameraPosition(latLng, 15f, 0f, 30f)
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        aMap.moveCamera(cameraUpdate)
        showCircleLocation(aMap)
        //        List<LatLng> latLngs = new ArrayList<LatLng>();
//        latLngs.add(new LatLng(22.1467077800, 113.4887695300));
//        latLngs.add(new LatLng(22.1387577300, 113.4794998200));
//        latLngs.add(new LatLng(22.1015455400, 113.4757232700));
//        latLngs.add(new LatLng(22.1149048900, 113.5011291500));
//        latLngs.add(new LatLng(22.0767319600, 113.5553741500));
//        showLineLocation(aMap, latLngs);
        drawMarkers(aMap, latLng)
    }

    /**
     * 绘制面
     * 地图上的面分为圆形和多边形两种。 如下：官网地址：
     * https://lbs.amap.com/api/android-sdk/guide/draw-on-map/draw-plane
     *
     * @param aMap
     */
    fun showCircleLocation(aMap: AMap) {
        aMap.addCircle(CircleOptions().center(mLatLng).radius(400.0).fillColor(Color.parseColor("#663A7CFF")).strokeColor(Color.parseColor("#663A7CFF")).strokeWidth(2f))
    }

    //画定位标记图
    protected open fun drawMarkers(aMap: AMap, latLng: LatLng?) {
        val markerOptions = MarkerOptions()
                .position(latLng) //                .icon(BitmapDescriptorFactory.fromResource(R.drawable.location))
                .draggable(true)
        val marker = aMap.addMarker(markerOptions)
        marker.showInfoWindow()
//        showAnimation(marker)
    }

    /**
     * 定位标记图动画
     */
    protected open fun showAnimation(marker: Marker) {
        val animation: Animation = RotateAnimation(marker.rotateAngle, marker.rotateAngle + 180, 0f, 0f, 0f)
        val duration = 1000L
        animation.setDuration(duration)
        animation.setInterpolator(LinearInterpolator())
        marker.setAnimation(animation)
        marker.startAnimation()

    }

    /**
     * 单次客户端的定位监听
     */
    private val locationSingleListener = AMapLocationListener { location ->
        val callBackTime = System.currentTimeMillis()
        val sb = StringBuffer()
        sb.append("单次定位完成\n")
        mAMap?.let {
            mLatLng = LatLng(location.latitude, location.longitude)
            initAMap(mLatLng, it)

        }
        sb.append("回调时间: ${GaodeLocationUtils.formatUTC(callBackTime, null)}".trimIndent())
        if (null == location) {
            sb.append("定位失败：location is null!!!!!!!")
        } else {
            sb.append(GaodeLocationUtils.getLocationStr(location))
        }
        LogUtils.d(sb.toString())
        ToastUtils.showToast(BaseApplication.getContext(), sb.toString())
    }
    private var mLocationClientSingle: AMapLocationClient? = null

    /**
     * 启动单次客户端定位
     */
    fun startSingleLocation() {
        if (null == mLocationClientSingle) {
            mLocationClientSingle = AMapLocationClient(mContext)
        }
        val locationClientOption = AMapLocationClientOption()
        //使用单次定位
        locationClientOption.isOnceLocation = true
        // 地址信息
        locationClientOption.isNeedAddress = true
        locationClientOption.isLocationCacheEnable = false
        mLocationClientSingle?.setLocationOption(locationClientOption)
        mLocationClientSingle?.setLocationListener(locationSingleListener)
        mLocationClientSingle?.startLocation()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        location_map.onSaveInstanceState(outState)
    }

    override fun onDestroys() {
        super.onDestroys()
        mLocationClientSingle?.onDestroy()
        location_map.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        location_map.onResume()
    }

    override fun onPause() {
        super.onPause()
        location_map.onPause()
    }

}