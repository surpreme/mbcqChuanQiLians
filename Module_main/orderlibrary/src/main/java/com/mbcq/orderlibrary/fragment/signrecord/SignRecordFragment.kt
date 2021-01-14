package com.mbcq.orderlibrary.fragment.signrecord


import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.ui.BaseSmartMVPFragment
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.activity.goodsreceipthouse.GoodsReceiptHouseEvent
import kotlinx.android.synthetic.main.frgment_sign_record.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: lzy
 * @time: 2020-10-10 09:33:12 货物已签收记录
 */
class SignRecordFragment : BaseSmartMVPFragment<SignRecordContract.View, SignRecordPresenter, SignRecordBean>(), SignRecordContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""

    override fun getLayoutResId(): Int = R.layout.frgment_sign_record
    override fun getSmartLayoutId(): Int = R.id.sign_record_smart
    override fun getSmartEmptyId(): Int = R.id.sign_record_smart_frame
    override fun getRecyclerViewId(): Int = R.id.sign_record_recycler


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

    @SuppressLint("CheckResult")
    override fun initDatas() {
        super.initDatas()
        RxBus.build().toObservable(this, GoodsReceiptHouseEvent::class.java).subscribe { msg ->
            if (msg.type == 0) {
                mShippingOutletsTag = msg.webCode
                mStartDateTag = msg.startTime
                mEndDateTag = msg.endTime
                refresh()
            }

        }
    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getPage(mCurrentPage, mShippingOutletsTag, mStartDateTag, mEndDateTag)
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
        it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                ARouter.getInstance().build(ARouterConstants.GoodsReceiptHouseInfoActivity).withString("GoodsReceiptHouseInfo", mResult).navigation()
            }

        }
    }

    @SuppressLint("SetTextI18n")
    override fun getPageS(list: List<SignRecordBean>, totalData: SignRecordToTalBean) {
        appendDatas(list)
        all_info_bottom_tv.text = "合计：${totalData.rowCou}票，${totalData.qty}件，运费¥${totalData.accSum}"
    }

    override fun cancelS(position: Int) {
        adapter.removeItem(position)
    }


}