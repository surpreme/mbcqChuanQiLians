package com.mbcq.orderlibrary.fragment.waybillroad

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.PolylineOptions
import com.mbcq.baselibrary.ui.BaseFragment
import com.mbcq.baselibrary.ui.mvp.BaseEmptyMVPFragment
import com.mbcq.baselibrary.ui.mvp.BaseMVPFragment
import com.mbcq.baselibrary.view.CustomizeToastUtil
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.fragment_waybill_road_bottom.*
import kotlinx.android.synthetic.main.item_waybill_road_bottom.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject


/**
 * @information 订单运输轨迹
 * @author lzy  121.463749,31.162066
 * 由于高德地图无法适应封装的生命周期
 */
class WaybillRoadBottomFragment : BaseEmptyMVPFragment<WaybillRoadBottomsContract.View, WaybillRoadBottomsPresenter>(), WaybillRoadBottomsContract.View {
    private var mAMap: AMap? = null
    private var map_location_view: MapView? = null
    var WaybillRoadBottom = ""
    lateinit var waybill_road_bottom_recycler: RecyclerView
    lateinit var waybill_number_tv: TextView
    lateinit var consignee_info_tv: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initDatas()
    }


    @SuppressLint("SetTextI18n")
    private fun initDatas() {
        arguments?.let {
            WaybillRoadBottom = it.getString("WaybillDetails", "{}")
        }

        val obj = JSONObject(WaybillRoadBottom)
        obj.optString("billno").also {
            waybill_number_tv.text = it
            mPresenter?.getTrackRoad(it)
        }
        consignee_info_tv.text = "收货人：${obj.optString("consignee")}   ${obj.optString("consigneeMb")}"
        consignee_address_tv.text = "【收货地址】${obj.optString("consigneeAddr")}"
    }

    lateinit var mWaybillRoadBottomAdapter: WaybillRoadBottomAdapter
    private fun initView(view: View) {

        waybill_number_copy_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {

                val clipboard = activity?.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip: ClipData = ClipData.newPlainText("lzySupreme", waybill_number_tv.text.toString())
                clipboard.setPrimaryClip(clip)
                //*************************
                val toastUtil = CustomizeToastUtil()
                toastUtil.Short(context, "复制成功").setGravity(Gravity.CENTER).setToastBackground(Color.WHITE, R.drawable.toast_radius).show()
            }

        })
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val mView = inflater.inflate(R.layout.fragment_waybill_road_bottom, container, false)
        map_location_view = mView.findViewById(R.id.map_location_view)
        map_location_view?.onCreate(savedInstanceState)
        mAMap = map_location_view?.map
        mAMap?.let {
            val mLatLng = LatLng(121.463749, 31.162066)
//            initAMap(mLatLng, it)


        }
        consignee_info_tv = mView.findViewById(R.id.consignee_info_tv)
        waybill_number_tv = mView.findViewById(R.id.waybill_number_tv)
        waybill_road_bottom_recycler = mView.findViewById(R.id.waybill_road_bottom_recycler)
        waybill_road_bottom_recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, true)
        mWaybillRoadBottomAdapter = WaybillRoadBottomAdapter(mContext).also {
            waybill_road_bottom_recycler.adapter = it
        }
        return mView
    }

    override fun onStart() {
        super.onStart()
       /* GlobalScope.launch { // 在后台启动一个新的协程并继续
            delay(500L) // 非阻塞的等待 0.5 秒钟（默认时间单位是毫秒）
        }*/
        mAMap?.let {
            val mLatLng = LatLng(22.1467077800, 113.4887695300)
            initAMap(mLatLng, it)
            drawLine()


        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        map_location_view?.onSaveInstanceState(outState)

    }

    /**
     * 移动到指定经纬度
    // CameraPosition 第一个参数： 目标位置的屏幕中心点经纬度坐标。
    // CameraPosition 第二个参数： 目标可视区域的缩放级别
    // CameraPosition 第三个参数： 目标可视区域的倾斜度，以角度为单位。
    // CameraPosition 第四个参数： 可视区域指向的方向，以角度为单位，从正北向顺时针方向计算，从0度到360度
     */
    protected fun initAMap(latLng: LatLng?, aMap: AMap) {
        val cameraPosition = CameraPosition(latLng, 10f, 0f, 0f)
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        aMap.moveCamera(cameraUpdate)
        //        List<LatLng> latLngs = new ArrayList<LatLng>();
//        latLngs.add(new LatLng(22.1467077800, 113.4887695300));
//        latLngs.add(new LatLng(22.1387577300, 113.4794998200));
//        latLngs.add(new LatLng(22.1015455400, 113.4757232700));
//        latLngs.add(new LatLng(22.1149048900, 113.5011291500));
//        latLngs.add(new LatLng(22.0767319600, 113.5553741500));
//        showLineLocation(aMap, latLngs);
    }

    //上海 121.540824,31.079938
//广州113.345023,23.108731
    protected fun drawLine() {
        val latLngs = mutableListOf<LatLng>()
//        latLngs.add(LatLng(114.07568, 22.539019))
//        latLngs.add(LatLng(108.802242, 34.253653))
        latLngs.add( LatLng(22.1467077800, 113.4887695300))
        latLngs.add( LatLng(22.1015455400, 113.4757232700))
        latLngs.add( LatLng(22.1149048900, 113.5011291500))
        latLngs.add( LatLng(22.0767319600, 113.5553741500))
        mAMap?.let {
            showLineLocation(it, latLngs)

        }
    }

    /**
     * 绘制轨迹线
     * 官网
     * https://lbs.amap.com/api/android-sdk/guide/draw-on-map/draw-polyline
     * 测试url
     * http://www.gpsspg.com/maps.htm
     * List<LatLng> latLngs = new ArrayList<LatLng>();
     * latLngs.add(new LatLng(39.999391,116.135972));
     *
     * @param aMap
    </LatLng></LatLng> */
    protected fun showLineLocation(aMap: AMap, latLngs: List<LatLng>) {
        aMap.addPolyline(PolylineOptions().addAll(latLngs).width(10f).color(Color.argb(255, 1, 1, 1)))
    }

    override fun onDestroy() {
        super.onDestroy()
        map_location_view?.onDestroy()

    }

    override fun onResume() {
        super.onResume()
        map_location_view?.onResume()
    }

    override fun onPause() {
        super.onPause()
        map_location_view?.onPause()
    }

    override fun getTrackRoadS(list: List<WaybillRoadBottomBean>) {
        mWaybillRoadBottomAdapter.appendData(list)
    }
}