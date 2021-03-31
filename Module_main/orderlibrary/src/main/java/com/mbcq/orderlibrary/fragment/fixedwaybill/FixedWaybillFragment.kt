package com.mbcq.orderlibrary.fragment.fixedwaybill

import android.view.View
import com.mbcq.baselibrary.ui.BaseSmartMVPFragment
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.activity.waybilldetails.FixedWaybillRefreshEvent
import kotlinx.android.synthetic.main.fragment_waybill_fixed.*
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

/**
 * @information 改單記錄
 * @author lzy
 */
class FixedWaybillFragment : BaseSmartMVPFragment<FixedWaybillFragmentContract.View, FixedWaybillFragmentPresenter, FixedWaybillListBean>(), FixedWaybillFragmentContract.View {
    var mSrgumentsData = ""

    override fun getLayoutResId(): Int = R.layout.fragment_waybill_fixed

    override fun getSmartLayoutId(): Int = R.id.fixed_waybill_moreInfo_smart
    override fun getSmartEmptyId(): Int = R.id.fixed_waybill_moreInfo_smart_frame
    override fun getRecyclerViewId(): Int = R.id.fixed_waybill_moreInfo_recycler
    override fun initViews(view: View) {
        super.initViews(view)
        fixed_waybill_moreInfo_smart.setEnableLoadMore(false)
    }

    override fun initExtra() {
        super.initExtra()
        arguments?.let {
            mSrgumentsData = it.getString("WaybillDetails", "{}")
        }

    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getReviewData(billno = JSONObject(mSrgumentsData).optString("billno"))
    }

    override fun setAdapter(): BaseRecyclerAdapter<FixedWaybillListBean> = FixedWaybillFragmentAdapter(mContext).also {

    }

    override fun getReviewDataS(list: List<FixedWaybillListBean>) {
        appendDatas(list)
        EventBus.getDefault().post(FixedWaybillRefreshEvent(type = 1,number = list.size,billno = ""))
    }

}