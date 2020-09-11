package com.mbcq.orderlibrary.activity.waybillrecord

/*
import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.CommonApplication
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.commonlibrary.greendao.DaoSession
import com.mbcq.commonlibrary.greendao.WebAreaDbInfoDao
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_waybill_record.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

var mStartDateTag = ""
var mEndDateTag = ""
var mShippingOutletsTag = ""//发货网点

override fun getLayoutId(): Int = R.layout.activity_waybill_record
private var mIsSelectAllTag = false
private var isCanRefreshTotalTitle = true
override fun initViews(savedInstanceState: Bundle?) {
    super.initViews(savedInstanceState)
    setStatusBar(R.color.base_blue)
}

@SuppressLint("SimpleDateFormat")
override fun initDatas() {
    val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
    val mDate = Date(System.currentTimeMillis())
    val format = mDateFormat.format(mDate)
    mStartDateTag = "$format 00:00:00"
    mEndDateTag = "$format 23:59:59"
    mShippingOutletsTag = UserInformationUtil.getWebIdCode(mContext) + ","
    super.initDatas()

}

override fun getPageDatas(mCurrentPage: Int) {
    super.getPageDatas(mCurrentPage)
    mPresenter?.getPageData(mCurrentPage, mShippingOutletsTag, mStartDateTag, mEndDateTag)
}

interface WebDbInterface {
    fun isNull()
    fun isSuccess(list: MutableList<WebAreaDbInfo>)

}

*/
/**
 * 得到greenDao数据库中的网点
 * 可视化 stetho 度娘
 *//*

protected fun getDbWebId(mWebDbInterface: WebDbInterface) {
    val daoSession: DaoSession = (application as CommonApplication).daoSession
    val userInfoDao: WebAreaDbInfoDao = daoSession.webAreaDbInfoDao
    val dbDatas = userInfoDao.queryBuilder().list()
    if (dbDatas.isNullOrEmpty()) {
        mWebDbInterface.isNull()
    } else {
        mWebDbInterface.isSuccess(dbDatas)
    }
}

override fun onClick() {
    super.onClick()
    delete_waybill_btn.setOnClickListener(object : SingleClick() {
        override fun onSingleClick(v: View?) {
            if (adapter.getAllData().isEmpty()) {
                showToast("请至少选择一个运单进行删除")
                return
            }
            TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要删除这些运单记录吗？") {

                mPresenter?.deleteWayBill("10030000234")
            }.show()
        }

    })
    invalid__waybill_btn.setOnClickListener(object : SingleClick() {
        override fun onSingleClick(v: View?) {
            if (adapter.getAllData().isEmpty()) {
                showToast("请至少选择一个运单进行作废")
                return
            }
            TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要作废这些运单记录吗？") {

            }.show()
        }

    })
    waybill_record_toolbar.setBackButtonOnClickListener {
        onBackPressed()
    }
    waybill_record_toolbar.setRightButtonOnClickListener {
        getDbWebId(object : WebDbInterface {
            override fun isNull() {

            }

            override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", "运单筛选", true, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnClickInterface {
                    */
/**
                     * s1 网点
                     * s2  start@end
                     *//*

                    */
/**
                     * s1 网点
                     * s2  start@end
                     *//*

                    override fun onResult(s1: String, s2: String) {
                        mShippingOutletsTag = s1
                        val timeList = s2.split("@")
                        if (timeList.isNotEmpty() && timeList.size == 2) {
                            mStartDateTag = timeList[0]
                            mEndDateTag = timeList[1]
                        }
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

override fun setAdapter(): BaseRecyclerAdapter<WaybillRecordBean> = WaybillRecordAdapter(mContext).also {
    check_all_box.setOnCheckedChangeListener { _, isChecked ->
        run {
            mIsSelectAllTag = isChecked
            if (isChecked) {
                it.selectAll()
            } else {
                it.clearAllSelect()

            }
        }

    }
}


override fun getPageDataS(list: List<WaybillRecordBean>, totalS: String) {
    if (mIsSelectAllTag) {
        for (item in list) {
            item.checked = true
        }
    }

    if (isCanRefreshTotalTitle) {
        waybill_record_toolbar.setCenterTitleText("运单记录($totalS)")
        isCanRefreshTotalTitle = false
    }
    appendDatas(list)
}

override fun deleteWayBillS() {

}

override fun deleteWayBillF(result: String) {

}*/
