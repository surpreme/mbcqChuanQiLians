package com.mbcq.orderlibrary.fragment.waybillroad

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.mbcq.baselibrary.ui.BaseFragment
import com.mbcq.baselibrary.ui.mvp.BaseEmptyMVPFragment
import com.mbcq.baselibrary.ui.mvp.BaseMVPFragment
import com.mbcq.baselibrary.view.CustomizeToastUtil
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.fragment_waybill_road_bottom.*
import kotlinx.android.synthetic.main.item_waybill_road_bottom.*
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initDatas()
    }


    private fun initDatas() {
        arguments?.let {
            WaybillRoadBottom = it.getString("WaybillDetails", "{}")
        }
        val obj = JSONObject(WaybillRoadBottom)
        mPresenter?.getTrackRoad(obj.optString("billno"))
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
        waybill_road_bottom_recycler = mView.findViewById(R.id.waybill_road_bottom_recycler)
        waybill_road_bottom_recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        mWaybillRoadBottomAdapter = WaybillRoadBottomAdapter(mContext).also {
            waybill_road_bottom_recycler.adapter = it
        }
        return mView
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        map_location_view?.onSaveInstanceState(outState)

    }

    /**
     * 移动到指定经纬度
     */
    protected fun initAMap(latLng: LatLng?, aMap: AMap) {
        val cameraPosition = CameraPosition(latLng, 15f, 0f, 30f)
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