package com.mbcq.orderlibrary.activity.stowagealongway


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseSmartMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_stowage_along_way.*

/**
 * @author: lzy
 * @time: 2020-11-21 15:48:12 沿途配载记录
 */

@Route(path = ARouterConstants.StowageAlongWayActivity)
class StowageAlongWayActivity : BaseSmartMVPActivity<StowageAlongWayContract.View, StowageAlongWayPresenter, StowageAlongWayBean>(), StowageAlongWayContract.View {
    override fun getLayoutId(): Int = R.layout.activity_stowage_along_way
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        stowage_along_way_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getPage(mCurrentPage)
    }

    override fun getSmartLayoutId(): Int = R.id.stowage_along_way_smart
    override fun getSmartEmptyId(): Int = R.id.stowage_along_way_smart_frame
    override fun getRecyclerViewId(): Int = R.id.stowage_along_way_recycler
    override fun setAdapter(): BaseRecyclerAdapter<StowageAlongWayBean> = StowageAlongWayAdapter(mContext).also {
        it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                ARouter.getInstance().build(ARouterConstants.StowageAlongWayHouseActivity).withString("StowageAlongWayHouse",mResult).navigation()
            }

        }
    }

    override fun getPageS(list: List<StowageAlongWayBean>) {
        appendDatas(list)
    }
}