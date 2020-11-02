package com.mbcq.vehicleslibrary.fragment.arrivalshortfeederscan


import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseListMVPFragment
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.fragment_arrival_short_feeder_scan.*

/**
 * @author: lzy
 * @time: 2020-10-29 14:54:32 短驳到车扫描
 */

class ArrivalShortFeederScanFragment : BaseListMVPFragment<ArrivalShortFeederScanFragmentContract.View, ArrivalShortFeederScanFragmentPresenter, ArrivalShortFeederScanBean>(), ArrivalShortFeederScanFragmentContract.View {
    override fun getLayoutResId(): Int = R.layout.fragment_arrival_short_feeder_scan
    override fun getRecyclerViewId(): Int = R.id.arrival_short_scan_list_recycler
    override fun setIsShowNetLoading(): Boolean {
        return false
    }

    override fun initViews(view: View) {
        super.initViews(view)
        search_arrival_short_scan_smart.setEnableLoadMore(false)
        search_arrival_short_scan_smart.setOnRefreshListener {
            adapter.clearData()
            initDatas()
            it.finishRefresh()
        }
    }
    override fun initDatas() {
        super.initDatas()
        mPresenter?.getLoading("", "", "")
        mPresenter?.getUnLoading("", "", "")

    }

    override fun setAdapter(): BaseRecyclerAdapter<ArrivalShortFeederScanBean> = ArrivalShortFeederScanAdapter(mContext).also {
        it.mClickInterface=object :OnClickInterface.OnRecyclerClickInterface{
            override fun onItemClick(v: View, position: Int, mResult: String) {
                ARouter.getInstance().build(ARouterConstants.ArrivalTrunkDepartureScanOperatingActivity).navigation()

            }

        }
    }
    override fun getPageS(list: List<ArrivalShortFeederScanBean>) {
        adapter.appendData(list)
    }

}