package com.mbcq.orderlibrary.fragment.fixedwaybill

import android.view.View
import com.mbcq.baselibrary.ui.BaseFragment
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.BaseListMVPFragment
import com.mbcq.baselibrary.ui.BaseSmartMVPFragment
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.fragment_waybill_fixed.*

/**
 * @information 改單記錄
 * @author lzy
 */
class FixedWaybillFragment : BaseSmartMVPFragment<FixedWaybillFragmentContract.View, FixedWaybillFragmentPresenter, FixedWaybillListBean>(), FixedWaybillFragmentContract.View {
    override fun getLayoutResId(): Int = R.layout.fragment_waybill_fixed

    override fun getSmartLayoutId(): Int = R.id.fixed_waybill_moreInfo_smart
    override fun getSmartEmptyId(): Int = R.id.fixed_waybill_moreInfo_smart_frame
    override fun getRecyclerViewId(): Int = R.id.fixed_waybill_moreInfo_recycler
    override fun initViews(view: View) {
        super.initViews(view)
        fixed_waybill_moreInfo_smart.setEnableLoadMore(false)
    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        adapter.appendData(mutableListOf(FixedWaybillListBean()))
    }
    override fun setAdapter(): BaseRecyclerAdapter<FixedWaybillListBean> = FixedWaybillFragmentAdapter(mContext).also {

    }

}