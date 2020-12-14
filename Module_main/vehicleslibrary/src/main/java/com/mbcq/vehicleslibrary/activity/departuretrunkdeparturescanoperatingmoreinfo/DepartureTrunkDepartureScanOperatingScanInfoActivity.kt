package com.mbcq.vehicleslibrary.activity.departuretrunkdeparturescanoperatingmoreinfo


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_departure_trunk_departure_scan_operating_scan_info.*

/**
 * @author: lzy
 * @time: 2020-12-14 13:31:14 干线扫描详情
 *
 */

@Route(path = ARouterConstants.DepartureTrunkDepartureScanOperatingScanInfoActivity)
class DepartureTrunkDepartureScanOperatingScanInfoActivity : BaseListMVPActivity<DepartureTrunkDepartureScanOperatingScanInfoContract.View, DepartureTrunkDepartureScanOperatingScanInfoPresenter, DepartureTrunkDepartureScanOperatingScanInfoBean>(), DepartureTrunkDepartureScanOperatingScanInfoContract.View {
    @Autowired(name = "departureScanInfo")
    @JvmField
    var mDepartureScanInfoBean: DepartureTrunkDepartureScanOperatingScanMoreInfoBean? = null

    override fun getLayoutId(): Int = R.layout.activity_departure_trunk_departure_scan_operating_scan_info

    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        scan_state_checkbox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (!isChecked) {
                adapter.clearData()
                adapter.appendData(if (mCacheList.isNotEmpty())mCacheList else mCacheCarList)

            } else if (isChecked) {
                adapter.clearData()
                val mFilterList = mutableListOf<DepartureTrunkDepartureScanOperatingScanInfoBean>()
                for (item in if (mCacheList.isNotEmpty())mCacheList else mCacheCarList) {
                    if (!item.isScaned)
                        mFilterList.add(item)
                }
                adapter.appendData(mFilterList)

            }
        }
    }

    override fun initDatas() {
        super.initDatas()
        mDepartureScanInfoBean?.let {
            if (it.getmType() == 0)
                mPresenter?.getPageData(it.billno, it.inOneVehicleFlag, 1)
            else
                mPresenter?.getCarScanData(it.inOneVehicleFlag,1)

        }
    }

    override fun onClick() {
        super.onClick()
        departure_vehicles_scan_operating_more_info_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
        back_btn.apply {
            onSingleClicks {
                onBackPressed()
            }
        }
    }

    override fun getRecyclerViewId(): Int = R.id.departure_vehicles_scan_operating_more_info_recycler
    val mCacheList = mutableListOf<DepartureTrunkDepartureScanOperatingScanInfoBean>()
    val mCacheCarList = mutableListOf<DepartureTrunkDepartureScanOperatingScanInfoBean>()

    override fun setAdapter(): BaseRecyclerAdapter<DepartureTrunkDepartureScanOperatingScanInfoBean> = DepartureTrunkDepartureScanOperatingScanInfoAdapter(mContext)
    override fun getPageDataS(list: List<DepartureTrunkDepartureScanOperatingScanInfoBean>) {
        if (list.isNotEmpty()) {
            mDepartureScanInfoBean?.let {
                val mShowLi = mutableListOf<DepartureTrunkDepartureScanOperatingScanInfoBean>()
                for (index in 1..it.goodsTotalNum) {
                    val mDepartureTrunkDepartureScanOperatingScanInfoBean = DepartureTrunkDepartureScanOperatingScanInfoBean()
                    val endBillno = if (index.toString().length == 1) "000$index" else if (index.toString().length == 2) "00$index" else if (index.toString().length == 3) "0$index" else if (index.toString().length == 4) "$index" else ""
                    mDepartureTrunkDepartureScanOperatingScanInfoBean.lableNo = "${it.billno}$endBillno"
                    var isHas = false
                    for (xxx in list) {
                        if (mDepartureTrunkDepartureScanOperatingScanInfoBean.lableNo == xxx.lableNo) {
                            isHas = true
                            mDepartureTrunkDepartureScanOperatingScanInfoBean.mResultTag = Gson().toJson(xxx)
                            mDepartureTrunkDepartureScanOperatingScanInfoBean.isScaned = true
                        }
                        if (!isHas) {
                            mDepartureTrunkDepartureScanOperatingScanInfoBean.mResultTag = "{}"
                            mDepartureTrunkDepartureScanOperatingScanInfoBean.isScaned = false

                        }

                    }
                    mShowLi.add(mDepartureTrunkDepartureScanOperatingScanInfoBean)
                }
                if (mCacheList.isNotEmpty())
                    mCacheList.clear()
                mCacheList.addAll(mShowLi)
                adapter.appendData(mShowLi)
            }

        }
    }

    override fun getCarScanDataS(list: List<DepartureTrunkDepartureScanOperatingScanInfoBean>) {
        mDepartureScanInfoBean?.let {
            mPresenter?.getCarInfo(it.inOneVehicleFlag, list)

        }
    }

    override fun getCarInfoS(list: List<DepartureTrunkDepartureScanOperatingMoreCarInfoBean>, mScanList: List<DepartureTrunkDepartureScanOperatingScanInfoBean>) {
        val mShowLi = mutableListOf<DepartureTrunkDepartureScanOperatingScanInfoBean>()
        for (itemF in list) {
            for (index in 1..itemF.totalQty) {
                val mBeanSon = DepartureTrunkDepartureScanOperatingScanInfoBean()
                val endBillno = if (index.toString().length == 1) "000$index" else if (index.toString().length == 2) "00$index" else if (index.toString().length == 3) "0$index" else if (index.toString().length == 4) "$index" else ""
                mBeanSon.lableNo = "${itemF.billno}$endBillno"
                mBeanSon.mResultTag = "{}"
                for (mCCCCB in mScanList) {
                    if (mBeanSon.lableNo == mCCCCB.lableNo) {
                        mBeanSon.isScaned = true
                    }
                }
                mShowLi.add(mBeanSon)
                mCacheCarList.add(mBeanSon)

            }
        }
        adapter.appendData(mShowLi)
    }
}