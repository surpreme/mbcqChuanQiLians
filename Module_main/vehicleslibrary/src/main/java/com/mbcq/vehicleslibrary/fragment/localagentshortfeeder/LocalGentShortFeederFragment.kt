package com.mbcq.vehicleslibrary.fragment.localagentshortfeeder


import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.ui.BaseSmartMVPFragment
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.fragment_locala_gent_short_feeder.*

/**
 * @author: lzy
 * @time: 2020-09-22 13:06
 */

class LocalGentShortFeederFragment : BaseSmartMVPFragment<LocalGentShortFeederContract.View, LocalGentShortFeederPresenter, LocalGentShortFeederBean>(), LocalGentShortFeederContract.View {
    override fun getLayoutResId(): Int = R.layout.fragment_locala_gent_short_feeder


    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getPage(mCurrentPage)
    }

    override fun onClick() {
        super.onClick()
        out_stock_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.AddLocalGentShortFeederActivity).navigation()
            }

        })


    }

    override fun getSmartLayoutId(): Int = R.id.local_short_feeder_smart
    override fun getSmartEmptyId(): Int = R.id.local_short_feeder_smart_frame
    override fun getRecyclerViewId(): Int = R.id.local_short_feeder_recycler

    override fun setAdapter(): BaseRecyclerAdapter<LocalGentShortFeederBean> = LocalGentShortFeederAdapter(mContext)
    override fun getPageS(list: List<LocalGentShortFeederBean>) {
        appendDatas(list)

    }
}