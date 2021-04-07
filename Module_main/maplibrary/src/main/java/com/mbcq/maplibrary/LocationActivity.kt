package com.mbcq.maplibrary


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.maps.AMapUtils
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.services.core.AMapException
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.help.Inputtips
import com.amap.api.services.help.Inputtips.InputtipsListener
import com.amap.api.services.help.InputtipsQuery
import com.amap.api.services.help.Tip
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.util.system.SoftKeyboardUtil
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.LocationResultEvent
import com.mbcq.maplibrary.view.WebCodeLocationBean
import com.mbcq.maplibrary.view.WebCodeLocationRecyclerAdapter
import kotlinx.android.synthetic.main.activity_location.*
import kotlinx.android.synthetic.main.item_location_father.*
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject


/**
 * @author: lzy
 * @time: 2018.08.25
 */

@Route(path = ARouterConstants.LocationActivity)
class LocationActivity : BaseLocationActivity<LocationContract.View, LocationPresenter, WebCodeLocationBean>(), LocationContract.View {
    private var autoTips: List<Tip>? = null
    private var isfirstinput = true

    var inputtipsListener = InputtipsListener { list, rCode ->
        if (rCode == AMapException.CODE_AMAP_SUCCESS) { // 正确返回
            autoTips = list
            val listString: MutableList<String> = ArrayList()
            for (i in list.indices) {
                listString.add(list[i].name)
            }
            val aAdapter = ArrayAdapter(
                    applicationContext,
                    R.layout.route_inputs, listString)
            search_ed.setAdapter(aAdapter)
            aAdapter.notifyDataSetChanged()
            if (isfirstinput) {
                isfirstinput = false
                search_ed.showDropDown()
            }
        } else {
            Toast.makeText(this@LocationActivity, "erroCode $rCode", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getLayoutId(): Int = R.layout.activity_location
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(0)
        initGaoDeAmapView(savedInstanceState)
        initSearch()
    }


    protected fun initSearch() {
        search_ed.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                val newText = s.toString().trim { it <= ' ' }
                if (newText.length > 0) {
//                    val inputquery = InputtipsQuery(newText, "北京") 限制搜索地区
                    val inputquery = InputtipsQuery(newText, " ")
                    val inputTips = Inputtips(this@LocationActivity, inputquery)
                    inputquery.cityLimit = true
                    inputTips.setInputtipsListener(inputtipsListener)
                    inputTips.requestInputtipsAsyn()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        search_ed.onItemClickListener = OnItemClickListener { parent, view, position, id ->
            Log.i("MY", "setOnItemClickListener")
            if (autoTips != null && autoTips!!.size > position) {
                val tip = autoTips!![position]
                searchPoi(tip)
            }
        }
    }

    override fun onClick() {
        super.onClick()
        /*open_close_iv.setOnClickListener(object :SingleClick(){
            override fun onSingleClick(v: View?) {
                val behavior = BottomSheetBehavior.from(bottom_sheet)
                if(behavior.state ==BottomSheetBehavior.STATE_HIDDEN){
                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                }else{
                    behavior.state = BottomSheetBehavior.STATE_HIDDEN

                }
            }

        })*/
        sure_location_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                var mSelectData = ""
                for (item in adapter.getAllData()) {
                    if (item.isChecked) {
                        mSelectData = Gson().toJson(item)
                        break
                    }
                }
                if (mSelectData.isNotBlank()) {
                    EventBus.getDefault().postSticky(LocationResultEvent(mSelectData,1,"",""))
                    onBackPressed()
                } else {
                    showToast("请选择您的到货网点")
                }
            }

        })
        iv_back.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    private var isInputKeySearch = false
    private var inputSearchKey: String? = null
    private var searchLatlonPoint: LatLonPoint? = null
    private var firstItem: PoiItem? = null

    private fun searchPoi(result: Tip) {
        isInputKeySearch = true
        inputSearchKey = result.name //getAddress(); // + result.getRegeocodeAddress().getCity() + result.getRegeocodeAddress().getDistrict() + result.getRegeocodeAddress().getTownship();
        searchLatlonPoint = result.point
        firstItem = PoiItem("tip", searchLatlonPoint, inputSearchKey, result.address)
        firstItem?.cityName = result.district
        firstItem?.adName = ""
//        resultData.clear()
//        searchResultAdapter.setSelectedPosition(0)
        searchLatlonPoint?.let {
            mAMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(it.latitude, it.longitude), 26f))

        }
        hideKeyboard(search_ed)
        SoftKeyboardUtil.closeKeyboard(this)
        search_ed.setText("")
//        doSearchQuery()
    }

    override fun getRecyclerViewId(): Int = R.id.webcode_location_recycler

    override fun addItemDecoration(): RecyclerView.ItemDecoration = BaseItemDecoration(mContext)

    override fun setAdapter(): BaseRecyclerAdapter<WebCodeLocationBean> = WebCodeLocationRecyclerAdapter(mContext).also {
        it.mOnLocationInterface=object :WebCodeLocationRecyclerAdapter.OnLocationInterface{
            override fun onSelected(v: View, position: Int, result: String) {
                EventBus.getDefault().postSticky(LocationResultEvent(result,1,"",""))
                onBackPressed()
            }

            override fun onCall(v: View, position: Int, result: String) {
                val obj = JSONObject(result)
                TalkSureCancelDialog(mContext, getScreenWidth(), "您确认要拨打${obj.optString("webid")}的客服电话${if (obj.optString("webTel").isNullOrBlank()) obj.optString("webMb") else obj.optString("webTel")}吗？") {
                    val intent = Intent(Intent.ACTION_DIAL)
                    val data: Uri = Uri.parse("tel:${if (obj.optString("webTel").isNullOrBlank()) obj.optString("webMb") else obj.optString("webTel")}")
                    intent.data = data
                    startActivity(intent)
                }.show()
            }

        }

    }

    override fun getAllWebCodeInfoS(list: List<WebCodeLocationBean>, mLatLng: LatLng) {
        if (adapter.getAllData().isNotEmpty())
            adapter.clearData()
        val showList = mutableListOf<WebCodeLocationBean>()
        for (item in list) {
            try {
                val distance: Float = AMapUtils.calculateLineDistance(LatLng(mLatLng.latitude, mLatLng.longitude), LatLng(item.latitude.toDouble(), item.longitude.toDouble()))
                item.distance = haveTwoDouble((distance / 1000).toDouble())
                if (distance <= 200 * 1000.00)
                    showList.add(item)
                LogUtils.e("mmmm", distance)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        val behavior = BottomSheetBehavior.from(bottom_sheet)
        if(showList.isNotEmpty()){
            behavior.setPeekHeight(ScreenSizeUtils.dip2px(mContext,160f))
        }else{
            behavior.peekHeight = ScreenSizeUtils.dip2px(mContext,25f)

        }
        adapter.appendData(showList)


    }

    override fun locationSuccessResult(mLatLng: LatLng) {
        mPresenter?.getAllWebCodeInfo(mLatLng)

    }
}