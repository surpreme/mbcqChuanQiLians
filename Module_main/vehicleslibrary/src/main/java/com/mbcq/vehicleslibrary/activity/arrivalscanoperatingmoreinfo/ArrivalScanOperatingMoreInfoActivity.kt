package com.mbcq.vehicleslibrary.activity.arrivalscanoperatingmoreinfo


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.CompoundButton
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.departuretrunkdeparturescanoperatingmoreinfo.DepartureTrunkDepartureScanOperatingScanInfoBean
import kotlinx.android.synthetic.main.activity_arrival_scan_operating_more_info.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-03-16 14:22:43 到车扫描详情
 */

@Route(path = ARouterConstants.ArrivalScanOperatingMoreInfoActivity)
class ArrivalScanOperatingMoreInfoActivity : BaseListMVPActivity<ArrivalScanOperatingMoreInfoContract.View, ArrivalScanOperatingMoreInfoPresenter, ArrivalScanOperatingMoreInfoBean>(), ArrivalScanOperatingMoreInfoContract.View {
    @Autowired(name = "ArrivalScanOperatingMoreInfo")
    @JvmField
    var mLastDataNo: String = ""
    val mCacheList = mutableListOf<ArrivalScanOperatingMoreInfoBean>()
    val mCacheCarList = mutableListOf<ArrivalScanOperatingMoreInfoBean>()

    override fun getLayoutId(): Int = R.layout.activity_arrival_scan_operating_more_info
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        scan_state_checkbox.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if (!isChecked) {
                    adapter.replaceData(if (mCacheList.isNotEmpty()) mCacheList else mCacheCarList)
                } else {
                    val mFilterList = mutableListOf<ArrivalScanOperatingMoreInfoBean>()
                    for (item in if (mCacheList.isNotEmpty()) mCacheList else mCacheCarList) {
                        if (!item.isScan)
                            mFilterList.add(item)
                    }
                    adapter.replaceData(mFilterList)
                }

            }

        })

    }

    override fun initDatas() {
        super.initDatas()
        val obj = JSONObject(mLastDataNo)
        mPresenter?.getScanInfo(obj.optString("billno"), obj.optString("inoneVehicleFlag"))
    }

    override fun onClick() {
        super.onClick()
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

    @SuppressLint("SetTextI18n")
    fun showTopTotal(list: List<ArrivalScanOperatingMoreInfoBean>) {
        var mUnScanTotalQty = 0
        var mHandScanQty = 0
        var mPDAScanQty = 0
        var mMoreCarOrder = 0
        for (xItem in list) {
            if (!xItem.isScan) {
                mUnScanTotalQty++
            } else {
                if (xItem.scanTypeStr.contains("手动")) {
                    mHandScanQty++
                } else if (xItem.scanTypeStr.contains("PDA")) {
                    mPDAScanQty++
                }
                if (xItem.getmDismantleInfo().contains("拆")) {
                    mMoreCarOrder++
                }

            }
        }
        scan_left_info_tv.text = "总件数:${list.size}\n手动扫描件数：${mHandScanQty}\n拆票件数：${mMoreCarOrder}"
        scan_right_info_tv.text = "未扫件数：${mUnScanTotalQty}\npda扫描件数：${mPDAScanQty}"
    }

    override fun getRecyclerViewId(): Int = R.id.arrival_vehicles_scan_operating_more_info_recycler

    override fun setAdapter(): BaseRecyclerAdapter<ArrivalScanOperatingMoreInfoBean> = ArrivalScanOperatingMoreInfoAdapter(mContext)

    override fun getScanInfoS(list: List<ArrivalScanOperatingMoreInfoBean>) {
        val obj = JSONObject(mLastDataNo)
        val billno = obj.optString("billno")
        val totalQty = obj.optInt("totalQty", 1)
        val mArrivalScanOperatingMoreInfoList = mutableListOf<ArrivalScanOperatingMoreInfoBean>()
        for (index in 1..totalQty) {
            val endBillno = if (index.toString().length == 1) "000$index" else if (index.toString().length == 2) "00$index" else if (index.toString().length == 3) "0$index" else if (index.toString().length == 4) "$index" else ""
            val mArrivalScanOperatingMoreInfoBean = ArrivalScanOperatingMoreInfoBean()
            mArrivalScanOperatingMoreInfoBean.lableNo = "${billno}$endBillno"
            for (xxx in list) {
                if (xxx.lableNo == mArrivalScanOperatingMoreInfoBean.lableNo) {
                    mArrivalScanOperatingMoreInfoBean.isScan = true
                    mArrivalScanOperatingMoreInfoBean.billno = xxx.billno
                    mArrivalScanOperatingMoreInfoBean.inOneVehicleFlag = xxx.inOneVehicleFlag
                    mArrivalScanOperatingMoreInfoBean.opeMan = xxx.opeMan
                    mArrivalScanOperatingMoreInfoBean.recordDate = xxx.recordDate
                    mArrivalScanOperatingMoreInfoBean.scanTypeStr = xxx.scanTypeStr
                    mArrivalScanOperatingMoreInfoBean.scanType = xxx.scanType
                    continue
                }
            }
            mArrivalScanOperatingMoreInfoList.add(mArrivalScanOperatingMoreInfoBean)
        }
        adapter.appendData(mArrivalScanOperatingMoreInfoList)
        mCacheList.addAll(mArrivalScanOperatingMoreInfoList)
        showTopTotal(mArrivalScanOperatingMoreInfoList)

    }

}