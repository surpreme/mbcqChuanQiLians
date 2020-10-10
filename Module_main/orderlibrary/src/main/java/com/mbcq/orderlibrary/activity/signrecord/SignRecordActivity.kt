package com.mbcq.orderlibrary.activity.signrecord


import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseSmartMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_sign_record.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: lzy
 * @time: 2020-10-10 09:33:12 签收记录
 */
@Route(path = ARouterConstants.SignRecordActivity)
class SignRecordActivity : BaseSmartMVPActivity<SignRecordContract.View, SignRecordPresenter, SignRecordBean>(), SignRecordContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""

    override fun getLayoutId(): Int = R.layout.activity_sign_record
    override fun getSmartLayoutId(): Int = R.id.sign_record_smart
    override fun getSmartEmptyId(): Int = R.id.sign_record_smart_frame
    override fun getRecyclerViewId(): Int = R.id.sign_record_recycler
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    @SuppressLint("SimpleDateFormat")
    override fun initExtra() {
        super.initExtra()
        val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
        val mDate = Date(System.currentTimeMillis())

        val format = mDateFormat.format(mDate)
        val startFormat = TimeUtils.getLastdayStr(7)

        mStartDateTag = "$startFormat 00:00:00"
        mEndDateTag = "$format 23:59:59"
        mShippingOutletsTag = UserInformationUtil.getWebIdCode(mContext)
    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getPage(mCurrentPage, mShippingOutletsTag, mStartDateTag, mEndDateTag)
    }

    override fun onClick() {
        super.onClick()
        sign_record_toolbar.setRightButtonOnClickListener {
            WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                override fun isNull() {

                }

                override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                    FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "签收记录筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
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

                    }).show(supportFragmentManager, "SignRecordActivityFilterWithTimeDialog")
                }

            })
        }
        sign_record_toolbar.setBackButtonOnClickListener(object : SingleClick() {
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

    override fun setAdapter(): BaseRecyclerAdapter<SignRecordBean> = SignRecordRecyclerAdapter(mContext).also {
        it.mOnCancelInterface = object : SignRecordRecyclerAdapter.OnCancelInterface {
            override fun result(v: View, position: Int, item: SignRecordBean) {
                item.commonStr = item.billno
                TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要取消签收订单为：${item.billno}吗？") {
                    mPresenter?.cancel(Gson().toJson(item), position)
                }.show()

            }

        }
    }

    override fun getPageS(list: List<SignRecordBean>) {
        appendDatas(list)
    }

    override fun cancelS(position: Int) {
        adapter.removeItem(position)
    }
}