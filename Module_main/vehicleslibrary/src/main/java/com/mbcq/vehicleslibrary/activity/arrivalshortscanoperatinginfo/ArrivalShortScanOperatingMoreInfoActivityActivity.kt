package com.mbcq.vehicleslibrary.activity.arrivalshortscanoperatinginfo


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_arrival_scan_operating_more_info.*
import kotlinx.android.synthetic.main.activity_arrival_short_scan_operating_more_info_activity.*
import kotlinx.android.synthetic.main.activity_arrival_short_scan_operating_more_info_activity.arrival_vehicles_scan_operating_more_info_toolbar
import kotlinx.android.synthetic.main.activity_arrival_short_scan_operating_more_info_activity.back_btn
import kotlinx.android.synthetic.main.activity_arrival_short_scan_operating_more_info_activity.billno_ed
import kotlinx.android.synthetic.main.activity_arrival_short_scan_operating_more_info_activity.scan_left_info_tv
import kotlinx.android.synthetic.main.activity_arrival_short_scan_operating_more_info_activity.scan_right_info_tv
import kotlinx.android.synthetic.main.activity_arrival_short_scan_operating_more_info_activity.scan_state_checkbox
import kotlinx.android.synthetic.main.activity_arrival_short_scan_operating_more_info_activity.search_btn
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-03-19 13:43:12 短驳到车扫描详情
 */

@Route(path = ARouterConstants.ArrivalShortScanOperatingMoreInfoActivityActivity)
class ArrivalShortScanOperatingMoreInfoActivityActivity : BaseListMVPActivity<ArrivalShortScanOperatingMoreInfoActivityContract.View, ArrivalShortScanOperatingMoreInfoActivityPresenter, ArrivalShortScanOperatingMoreInfoBean>(), ArrivalShortScanOperatingMoreInfoActivityContract.View {
    @Autowired(name = "ArrivalShortScanOperatingMoreInfo")
    @JvmField
    var mLastDataNo: String = ""
    val mCacheList = mutableListOf<ArrivalShortScanOperatingMoreInfoBean>()
    val mCacheCarList = mutableListOf<ArrivalShortScanOperatingMoreInfoBean>()

    override fun getLayoutId(): Int = R.layout.activity_arrival_short_scan_operating_more_info_activity
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        scan_state_checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                adapter.replaceData(if (mCacheList.isNotEmpty()) mCacheList else mCacheCarList)
            } else {
                val mFilterList = mutableListOf<ArrivalShortScanOperatingMoreInfoBean>()
                for (item in if (mCacheList.isNotEmpty()) mCacheList else mCacheCarList) {
                    if (!item.isScan)
                        mFilterList.add(item)
                }
                adapter.replaceData(mFilterList)
            }
        }
    }

    override fun initDatas() {
        super.initDatas()
        val obj = JSONObject(mLastDataNo)
        if (obj.optInt("lookType") == 1)
            mPresenter?.getScanInfo(obj.optString("billno"), obj.optString("inoneVehicleFlag"))
        else if (obj.optInt("lookType") == 2)
            mPresenter?.getScanCarInfo(obj.optString("inoneVehicleFlag"))

    }

    override fun onClick() {
        super.onClick()
        search_btn.apply {
            onSingleClicks {
                if (billno_ed.text.toString().isBlank()) {
                    adapter.replaceData(if (mCacheList.isNotEmpty()) mCacheList else mCacheCarList)

                } else {
                    val searchStr = billno_ed.text.toString()
                    val mFilterList = mutableListOf<ArrivalShortScanOperatingMoreInfoBean>()
                    for (item in if (mCacheList.isNotEmpty()) mCacheList else mCacheCarList) {
                        if (item.lableNo == searchStr)
                            mFilterList.add(item)
                    }
                    if (mFilterList.isNotEmpty())
                        adapter.replaceData(mFilterList)
                }

            }
        }
        back_btn.apply {
            onSingleClicks {
                onBackPressed()
            }
        }
        arrival_vehicles_scan_operating_more_info_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun getRecyclerViewId(): Int = R.id.arrival_vehicles_scan_operating_more_info_recycler

    override fun setAdapter(): BaseRecyclerAdapter<ArrivalShortScanOperatingMoreInfoBean> = ArrivalShortScanOperatingMoreInfoAdapter(mContext)

    @SuppressLint("SetTextI18n")
    fun showTopTotal(list: List<ArrivalShortScanOperatingMoreInfoBean>) {
        var mUnScanTotalQty = 0//总未扫件数
        var mScanTotalQty = 0//已扫本车
        var mHandScanQty = 0
        var mPDAScanQty = 0
        var mMoreCarOrder = 0
        val obj = JSONObject(mLastDataNo)
        val mXinOneVehicleFlag = obj.optString("inOneVehicleFlag")
        for (xItem in list) {
            if (!xItem.isScan) {
                mUnScanTotalQty++
            } else {
                if (xItem.inOneVehicleFlag.replace(" ", "") == mXinOneVehicleFlag.replace(" ", ""))
                    mScanTotalQty++
                if (xItem.scanTypeStr.contains("手动")) {
                    mHandScanQty++
                } else if (xItem.scanTypeStr.contains("PDA")) {
                    mPDAScanQty++
                }
            }
            if (xItem.getmDismantleInfo().contains("拆")) {
                mMoreCarOrder++
            }

        }
        scan_left_info_tv.text = "总件数:${list.size}\n手动扫描件数：${mHandScanQty}\n拆票件数：${mMoreCarOrder}"
        scan_right_info_tv.text = "未扫件数：${if (obj.optInt("qty") == 0) mUnScanTotalQty else (obj.optInt("qty") - mScanTotalQty)}\npda扫描件数：${mPDAScanQty}"
    }

    override fun getScanInfoS(list: List<ArrivalShortScanOperatingMoreInfoBean>, sendScanJay: String) {
        val obj = JSONObject(mLastDataNo)
        val billno = obj.optString("billno")
        val totalQty = obj.optInt("totalQty", 1)
        val mArrivalScanOperatingMoreInfoList = mutableListOf<ArrivalShortScanOperatingMoreInfoBean>()
        val mXinOneVehicleFlag = obj.optString("inoneVehicleFlag")
        val mSendScanJay = JSONArray(sendScanJay)
        for (index in 1..totalQty) {
            val endBillno = if (index.toString().length == 1) "000$index" else if (index.toString().length == 2) "00$index" else if (index.toString().length == 3) "0$index" else if (index.toString().length == 4) "$index" else ""
            val mArrivalShortScanOperatingMoreInfoBean = ArrivalShortScanOperatingMoreInfoBean()
            mArrivalShortScanOperatingMoreInfoBean.lableNo = "${billno}$endBillno"
            var isChaivarNumber = ""
            for (xIndex in 0 until mSendScanJay.length()) {
                if (mSendScanJay.getJSONObject(xIndex).optString("lableNo") == "${billno}$endBillno") {
                    isChaivarNumber = mSendScanJay.getJSONObject(xIndex).optString("inOneVehicleFlag")
                    continue
                }
            }
            if (isChaivarNumber.isNotBlank())
                mArrivalShortScanOperatingMoreInfoBean.setmDismantleInfo(if (isChaivarNumber.contentEquals(mXinOneVehicleFlag)) "" else "拆")
            mArrivalShortScanOperatingMoreInfoBean.inOneVehicleFlag = isChaivarNumber
            for (xxx in list) {
                if (xxx.lableNo == mArrivalShortScanOperatingMoreInfoBean.lableNo) {
                    mArrivalShortScanOperatingMoreInfoBean.isScan = true
                    mArrivalShortScanOperatingMoreInfoBean.billno = xxx.billno
                    mArrivalShortScanOperatingMoreInfoBean.inOneVehicleFlag = xxx.inOneVehicleFlag
                    mArrivalShortScanOperatingMoreInfoBean.opeMan = xxx.opeMan
                    mArrivalShortScanOperatingMoreInfoBean.recordDate = xxx.recordDate
                    mArrivalShortScanOperatingMoreInfoBean.scanTypeStr = xxx.scanTypeStr
                    mArrivalShortScanOperatingMoreInfoBean.scanType = xxx.scanType
                    continue
                }
            }
            mArrivalScanOperatingMoreInfoList.add(mArrivalShortScanOperatingMoreInfoBean)
        }
        adapter.appendData(mArrivalScanOperatingMoreInfoList)
        mCacheList.addAll(mArrivalScanOperatingMoreInfoList)
        showTopTotal(mArrivalScanOperatingMoreInfoList)

    }

    override fun getScanCarInfoS(list: List<ArrivalShortScanOperatingMoreInfoBean>) {
        adapter.appendData(list)
        mCacheList.addAll(list)
        showTopTotal(list)
    }

}