package com.mbcq.vehicleslibrary.fragment.shortfeeder


import android.graphics.Rect
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.ui.BaseSmartMVPFragment
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.vehicleslibrary.R

/**
 * @author: lzy
 * @time: 2020-09-12 13:08：55
 * 短驳发车记录
 */

class ShortFeederFragment : BaseSmartMVPFragment<ShortFeederContract.View, ShortFeederPresenter, ShortFeederBean>(), ShortFeederContract.View {
    override fun getLayoutResId(): Int = R.layout.fragment_short_feeder
    override fun getSmartLayoutId(): Int = R.id.short_feeder_smart
    override fun getSmartEmptyId(): Int = R.id.short_feeder_smart_frame
    override fun getRecyclerViewId(): Int = R.id.short_feeder_recycler
    override fun setAdapter(): BaseRecyclerAdapter<ShortFeederBean> = ShortFeederAdapter(mContext)
    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getShortFeeder(mCurrentPage)

    }
    override fun addItemDecoration(): RecyclerView.ItemDecoration = object : BaseItemDecoration(mContext) {
        override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
            rect.top = ScreenSizeUtils.dp2px(mContext, 10f)
        }

        override fun doRule(position: Int, rect: Rect) {
            rect.bottom = rect.top
        }
    }
    override fun getShortFeederS(list: List<ShortFeederBean>) {
        appendDatas(list)
    }
}