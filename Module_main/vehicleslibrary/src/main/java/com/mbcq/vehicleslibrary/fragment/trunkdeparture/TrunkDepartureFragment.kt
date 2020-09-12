package com.mbcq.vehicleslibrary.fragment.trunkdeparture


import com.mbcq.baselibrary.ui.BaseSmartMVPFragment
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.vehicleslibrary.R

/**
 * @author: lzy
 * @time: 2020-09-12 15:44:58
 * 短驳发车记录
 */

class TrunkDepartureFragment : BaseSmartMVPFragment<TrunkDepartureContract.View, TrunkDeparturePresenter, TrunkDepartureBean>(), TrunkDepartureContract.View {
    override fun getSmartLayoutId(): Int = R.id.trunk_departure_smart

    override fun getSmartEmptyId(): Int = R.id.trunk_departure_smart_frame

    override fun getRecyclerViewId(): Int = R.id.trunk_departure_recycler

    override fun setAdapter(): BaseRecyclerAdapter<TrunkDepartureBean> = TrunkDepartureAdapter(mContext)

    override fun getLayoutResId(): Int = R.layout.fragment_trunk_departure
    override fun getTrunkDepartureS(list: List<TrunkDepartureBean>) {
        appendDatas(list)
    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getTrunkDeparture(mCurrentPage)
    }
}