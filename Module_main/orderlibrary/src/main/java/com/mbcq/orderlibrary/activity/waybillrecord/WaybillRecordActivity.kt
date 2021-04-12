package com.mbcq.orderlibrary.activity.waybillrecord


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseSmartMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.*
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.commonlibrary.greendao.DaoSession
import com.mbcq.commonlibrary.greendao.WebAreaDbInfoDao
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_waybill_record.*
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: lzy
 * @time: 2020-09-10 10:07:09
 * 运单记录
 */

@Suppress("IMPLICIT_CAST_TO_ANY")
@Route(path = ARouterConstants.WaybillRecordActivity)
class WaybillRecordActivity : BaseSmartMVPActivity<WaybillRecordContract.View, WaybillRecordPresenter, WaybillRecordBean>(), WaybillRecordContract.View {
    @Autowired(name = "WaybillRecord")
    @JvmField
    var mWaybillRecord: String = "{}"
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""//发货网点
    private var isCanRefreshTotalTitle = true

    companion object {
        const val AGAIN_FIXED_DATA_CODE = 7389

    }

    override fun getLayoutId(): Int = R.layout.activity_waybill_record

    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    @SuppressLint("SimpleDateFormat")
    override fun initDatas() {
        mStartDateTag = FilterTimeUtils.getStartTime(0)
        mEndDateTag = FilterTimeUtils.getEndTime()
        mShippingOutletsTag = UserInformationUtil.getWebIdCode(mContext) + ","
        super.initDatas()

    }

    override fun refresh() {
        isCanRefreshTotalTitle = true
        super.refresh()

    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getPageData(mCurrentPage, mShippingOutletsTag, mStartDateTag, mEndDateTag)
    }


    override fun onClick() {
        super.onClick()
        waybill_record_toolbar.setBackButtonOnClickListener {
            onBackPressed()
        }
        waybill_record_search_tv.apply {
            onSingleClicks {
                ARouter.getInstance().build(ARouterConstants.HouseSearchActivity).navigation()
            }
        }
        waybill_record_toolbar.setRightButtonOnClickListener {
            WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                override fun isNull() {

                }

                override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                    FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "运单筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
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
                            isCanRefreshTotalTitle = true
                            refresh()
                        }

                    }).show(supportFragmentManager, "WaybillRecordActivityFilterWithTimeDialog")
                }

            })
        }


    }

    override fun addItemDecoration(): RecyclerView.ItemDecoration = object : BaseItemDecoration(mContext) {
        override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
            rect.top = ScreenSizeUtils.dp2px(mContext, 10f)
        }

        override fun doRule(position: Int, rect: Rect) {
            rect.bottom = rect.top
        }
    }

    override fun getSmartLayoutId(): Int = R.id.waybill_record_smart
    override fun getSmartEmptyId(): Int = R.id.waybill_record_smart_frame
    override fun getRecyclerViewId(): Int = R.id.waybill_record_recycler

    override fun setAdapter(): BaseRecyclerAdapter<WaybillRecordBean> = WaybillRecordAdapter(mContext, if (!mWaybillRecord.isNullOrBlank()) (JSONObject(mWaybillRecord).optBoolean("isShowFixed")) else false).also {
        it.mOnRecyclerDeleteClickInterface = object : OnClickInterface.OnRecyclerDeleteClickInterface {
            override fun onDelete(v: View, position: Int, mResult: String) {
                //billState @1 已入库
                if (it.getAllData()[position].billState != "1") {
                    showToast("该单已经${it.getAllData()[position].billStateStr}，无法删除记录！")
                    return
                }
                TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要删除运单号为${it.getAllData()[position].billno}的运单吗？ 删除后不可恢复！") {
                    mPresenter?.deleteWayBill(mResult, position)
                }.show()
            }

        }
        it.mOnRecyclerFixedClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                setResult(AGAIN_FIXED_DATA_CODE, Intent().putExtra("fixedData",mResult) )
                onBackPressed()
            }

        }
        it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                ARouter.getInstance().build(ARouterConstants.WaybillDetailsActivity).withString("WaybillDetails", mResult).navigation()
            }


        }
    }

    var mTotalS = 0

    @SuppressLint("SetTextI18n")
    override fun getPageDataS(list: List<WaybillRecordBean>, totalS: String, mWaybillRecordTotalBean: WaybillRecordTotalBean) {
        if (isCanRefreshTotalTitle) {
            waybill_record_toolbar.setCenterTitleText("运单记录($totalS)")
            all_info_bottom_tv.text = "合计：$totalS 票，${if (mWaybillRecordTotalBean.qty.isNullOrEmpty()) 0 else mWaybillRecordTotalBean.qty.toInt()}件，运费¥${if (mWaybillRecordTotalBean.accSum.isNullOrEmpty()) 0.00 else mWaybillRecordTotalBean.accSum}"
            if (isInteger(totalS))
                mTotalS = totalS.toInt()
            isCanRefreshTotalTitle = false
        }
        appendDatas(list)
    }

    override fun deleteWayBillS(mIndex: Int) {
        adapter.removeItem(mIndex)
        mTotalS -= 1
        waybill_record_toolbar.setCenterTitleText("运单记录($mTotalS)")

    }


}