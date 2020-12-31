package com.mbcq.orderlibrary.fragment.fixedwaybill

import android.view.View
import com.mbcq.baselibrary.ui.BaseFragment
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.BaseListMVPFragment
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.orderlibrary.R

/**
 * @information 改單記錄
 * @author lzy
 */
class FixedWaybillFragment : BaseListMVPFragment<FixedWaybillFragmentContract.View, FixedWaybillFragmentPresenter, FixedWaybillListBean>(), FixedWaybillFragmentContract.View {
    override fun getLayoutResId(): Int = R.layout.fragment_waybill_fixed


    override fun getRecyclerViewId(): Int = R.id.fixed_waybill_moreInfo_recycler

    override fun setAdapter(): BaseRecyclerAdapter<FixedWaybillListBean> = FixedWaybillFragmentAdapter(mContext).also {
        it.appendData(mutableListOf(FixedWaybillListBean()))
    }

}