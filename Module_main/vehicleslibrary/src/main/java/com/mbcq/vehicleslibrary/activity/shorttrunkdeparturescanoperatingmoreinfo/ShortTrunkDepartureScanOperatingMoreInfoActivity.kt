package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperatingmoreinfo


import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_short_trunk_departure_scan_operating_more_info.*

/**
 * @author: lzy
 * @time: 2020-12-14 08:38:10  短驳扫描详情
 */

@Route(path = ARouterConstants.ShortTrunkDepartureScanOperatingMoreInfoActivity)
class ShortTrunkDepartureScanOperatingMoreInfoActivity : BaseListMVPActivity<ShortTrunkDepartureScanOperatingMoreInfoContract.View, ShortTrunkDepartureScanOperatingMoreInfoPresenter, ShortTrunkDepartureScanOperatingMoreInfoBean>(), ShortTrunkDepartureScanOperatingMoreInfoContract.View {
    @Autowired(name = "ShortScanInfo")
    @JvmField
    var mShortScanInfoBean: ShortTrunkDepartureScanOperatingMoreInfoIntentBean? = null
    override fun getLayoutId(): Int = R.layout.activity_short_trunk_departure_scan_operating_more_info
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }


    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        scan_state_checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (!isChecked) {
                adapter.clearData()
                adapter.appendData(if (mCacheList.isNotEmpty()) mCacheList else mCacheCarList)

            } else if (isChecked) {
                adapter.clearData()
                val mFilterList = mutableListOf<ShortTrunkDepartureScanOperatingMoreInfoBean>()
                for (item in if (mCacheList.isNotEmpty()) mCacheList else mCacheCarList) {
                    if (!item.isScaned)
                        mFilterList.add(item)
                }
                adapter.appendData(mFilterList)

            }
        }
    }

    override fun initDatas() {
        super.initDatas()
        mShortScanInfoBean?.let {
            if (it.getmType() == 0)
                mPresenter?.getPageData(it.billno, it.inOneVehicleFlag, -1)
            else
                mPresenter?.getCarInfo(it.inOneVehicleFlag)


        }
    }

    override fun onClick() {
        super.onClick()
        short_vehicles_scan_operating_more_info_toolbar.setBackButtonOnClickListener(object : SingleClick() {
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

    override fun addItemDecoration(): RecyclerView.ItemDecoration = object : BaseItemDecoration(mContext) {
        override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
            rect.top = ScreenSizeUtils.dp2px(mContext, 5f)
        }

        override fun doRule(position: Int, rect: Rect) {
            rect.bottom = rect.top
        }
    }

    override fun getRecyclerViewId(): Int = R.id.short_vehicles_scan_operating_more_info_recycler

    override fun setAdapter(): BaseRecyclerAdapter<ShortTrunkDepartureScanOperatingMoreInfoBean> = ShortTrunkDepartureScanOperatingMoreInfoAdapter(mContext)

    val mCacheList = mutableListOf<ShortTrunkDepartureScanOperatingMoreInfoBean>()
    val mCacheCarList = mutableListOf<ShortTrunkDepartureScanOperatingMoreInfoBean>()
    @SuppressLint("SetTextI18n")
    fun showTopTotal(list: List<ShortTrunkDepartureScanOperatingMoreInfoBean>) {
        var mUnScanTotalQty = 0
        var mHandScanQty = 0
        var mPDAScanQty = 0
        var mMoreCarOrder = 0
        for (xItem in list) {
            if (!xItem.isScaned) {
                mUnScanTotalQty++
            } else {
                if (xItem.scanTypeStr.contains("手动")) {
                    mHandScanQty++
                } else if (xItem.scanTypeStr.contains("PDA")) {
                    mPDAScanQty++
                }
                if (xItem.mDismantleInfo.contains("拆")) {
                    mMoreCarOrder++
                }

            }
        }
        scan_left_info_tv.text = "总件数:${list.size}\n手动扫描件数：${mHandScanQty}\n拆票件数：${mMoreCarOrder}"
        scan_right_info_tv.text = "未扫件数：${mUnScanTotalQty}\npda扫描件数：${mPDAScanQty}"
    }

    @SuppressLint("SetTextI18n")
    override fun getPageDataS(list: List<ShortTrunkDepartureScanOperatingMoreInfoBean>) {
        if (list.isNotEmpty()) {
            mShortScanInfoBean?.let {
                if (it.getmType() == 0) {
                    val mShowLi = mutableListOf<ShortTrunkDepartureScanOperatingMoreInfoBean>()
                    for (index in 1..it.goodsTotalNum) {
                        val mShortTrunkDepartureScanOperatingMoreInfoBean = ShortTrunkDepartureScanOperatingMoreInfoBean()
                        val endBillno = if (index.toString().length == 1) "000$index" else if (index.toString().length == 2) "00$index" else if (index.toString().length == 3) "0$index" else if (index.toString().length == 4) "$index" else ""
                        mShortTrunkDepartureScanOperatingMoreInfoBean.lableNo = "${it.billno}$endBillno"
                        var isHas = false
                        for (xxx in list) {
                            if (mShortTrunkDepartureScanOperatingMoreInfoBean.lableNo == xxx.lableNo) {
                                isHas = true
                                mShortTrunkDepartureScanOperatingMoreInfoBean.mResultTag = Gson().toJson(xxx)
                                mShortTrunkDepartureScanOperatingMoreInfoBean.mDismantleInfo = xxx.mDismantleInfo
                                mShortTrunkDepartureScanOperatingMoreInfoBean.isScaned = true
                                mShortTrunkDepartureScanOperatingMoreInfoBean.scanName = xxx.scanName
                                mShortTrunkDepartureScanOperatingMoreInfoBean.scanTypeStr = xxx.scanTypeStr
                                mShortTrunkDepartureScanOperatingMoreInfoBean.time = xxx.time
                                mShortTrunkDepartureScanOperatingMoreInfoBean.mScanInOneVehicleFlag = xxx.mScanInOneVehicleFlag
                                continue
                            }
                        }
                        if (!isHas) {
                            mShortTrunkDepartureScanOperatingMoreInfoBean.mResultTag = "{}"
                            mShortTrunkDepartureScanOperatingMoreInfoBean.isScaned = false
                            mShortTrunkDepartureScanOperatingMoreInfoBean.mDismantleInfo = ""


                        }
                        mShowLi.add(mShortTrunkDepartureScanOperatingMoreInfoBean)
                    }
                    if (mCacheList.isNotEmpty())
                        mCacheList.clear()
                    mCacheList.addAll(mShowLi)
                    adapter.appendData(mShowLi)
                    showTopTotal(mShowLi)
                } else {
                    if (mCacheCarList.isNotEmpty())
                        mCacheCarList.clear()
                    adapter.appendData(list)
                    mCacheCarList.addAll(list)
                    showTopTotal(list)

                }

            }

        }

    }

}