package com.mbcq.vehicleslibrary.activity.allarrivalrecord.arrivalinventory


import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseSmartMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.FilterTimeUtils
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_arrival_inventory.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: lzy
 * @time: 2020-09-25 18:00:00
 * 到货库存 Arrival inventory
 */
@Route(path = ARouterConstants.ArrivalInventoryActivity)
class ArrivalInventoryActivity : BaseSmartMVPActivity<ArrivalInventoryContract.View, ArrivalInventoryPresenter, ArrivalInventoryBean>(), ArrivalInventoryContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""
    override fun getLayoutId(): Int = R.layout.activity_arrival_inventory
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    @SuppressLint("SimpleDateFormat")
    override fun initExtra() {
        super.initExtra()
        mStartDateTag = FilterTimeUtils.getStartTime(7)
        mEndDateTag = FilterTimeUtils.getEndTime()
        mShippingOutletsTag = UserInformationUtil.getWebIdCode(mContext)
    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getPage(mCurrentPage, mShippingOutletsTag, mStartDateTag, mEndDateTag)

    }

    override fun onClick() {
        super.onClick()
        arrival_inventory_toolbar.setRightButtonOnClickListener {
            WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                override fun isNull() {

                }

                override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                    FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "到货库存记录筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
                        /**
                         * s1 网点
                         * s2  start@end
                         */
                        override fun onResult(s1: String, s2: String) {
                            mShippingOutletsTag = s1
                            val timeList = s2.split("@")
                            if (timeList.isNotEmpty() && timeList.size == 2) {
                                mStartDateTag = timeList[0]
                                mEndDateTag = timeList[1]
                            }
                            refresh()

                        }

                    }).show(supportFragmentManager, "mArrivalInventoryActivityFilterWithTimeDialog")
                }

            })
        }
        arrival_inventory_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun addItemDecoration(): RecyclerView.ItemDecoration = object : BaseItemDecoration(mContext) {
        override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
            rect.top = ScreenSizeUtils.dp2px(mContext, 10f)
        }

        override fun doRule(position: Int, rect: Rect) {
            rect.bottom = rect.top
        }
    }
    override fun getSmartLayoutId() = R.id.arrival_inventory_smart
    override fun getSmartEmptyId() = R.id.arrival_inventory_smart_frame
    override fun getRecyclerViewId(): Int = R.id.arrival_inventory_recycler
    override fun setAdapter(): BaseRecyclerAdapter<ArrivalInventoryBean> = ArrivalInventoryAdapter(mContext).also {
        it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                ARouter.getInstance().build(ARouterConstants.WaybillDetailsActivity).withString("WaybillDetails", mResult).navigation()

            }

        }
    }

    @SuppressLint("SetTextI18n")
    override fun getPageS(list: List<ArrivalInventoryBean>, totalBean: ArrivalInventoryTotalBean, page: Int, count: String) {
        appendDatas(list)
        if (page == 1) {
            arrival_inventory_toolbar.setCenterTitleText("到货库存(${count})")
            all_info_bottom_tv.text = "合计：${totalBean.rowCou}票，${totalBean.weight}kg，${totalBean.volumn}m³，运费${totalBean.accSum}"
        }
    }
}