package com.mbcq.accountlibrary.fragment

import android.view.View
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.ui.BaseFragment
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.util.screen.StatusBarUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import kotlinx.android.synthetic.main.fragment_house.*

/***
 * 首页
 */
class HouseFragment : BaseFragment() {
    override fun getLayoutResId(): Int = R.layout.fragment_house
    override fun initViews(view: View) {
        indexTop(1)
        house_smartRefreshLayout.setEnableLoadMore(false)
    }

    fun indexTop(type: Int) {
        when (type) {
            1 -> {
                sender_title_line.background = ContextCompat.getDrawable(mContext, R.color.base_blue)
                received_title_line.background = ContextCompat.getDrawable(mContext, R.color.white)
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    sender_title_tv.setTextColor(mContext.getColor(R.color.base_blue))
                    received_title_tv.setTextColor(mContext.getColor(R.color.black))
                } else {
                    sender_title_tv.setTextColor(resources.getColor(R.color.base_blue))
                    received_title_tv.setTextColor(resources.getColor(R.color.black))

                }
            }
            2 -> {
                received_title_line.background = ContextCompat.getDrawable(mContext, R.color.base_blue)
                sender_title_line.background = ContextCompat.getDrawable(mContext, R.color.white)
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    received_title_tv.setTextColor(mContext.getColor(R.color.base_blue))
                    sender_title_tv.setTextColor(mContext.getColor(R.color.black))
                } else {
                    received_title_tv.setTextColor(resources.getColor(R.color.base_blue))
                    sender_title_tv.setTextColor(resources.getColor(R.color.black))

                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        toolbar_cl.setPadding(0, StatusBarUtils.getHeight(mContext), 0, 0)
    }

    override fun onClick() {
        super.onClick()
        sender_title_tv.apply {
            onSingleClicks {
                indexTop(1)
            }
        }
        received_title_tv.apply {
            onSingleClicks {
                indexTop(2)
            }
        }
        house_search_fragment_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.HouseSearchActivity).navigation()
            }

        })
        scan_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.ScanActivity).navigation()

            }

        })
    }

}