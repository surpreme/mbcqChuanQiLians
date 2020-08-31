package com.mbcq.accountlibrary.fragment.setting

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.accountlibrary.R
import com.mbcq.accountlibrary.fragment.iconadapter.IconViewBean
import com.mbcq.accountlibrary.fragment.settingadapter.SettingIconBean
import com.mbcq.accountlibrary.fragment.settingadapter.SettingViewRecyclerAdapter
import com.mbcq.baselibrary.ui.BaseFragment
import com.mbcq.baselibrary.ui.BaseListFragment
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter

/**
 * 我的
 */
class SettingFragment : BaseListFragment<SettingIconBean>() {
    override fun getLayoutResId(): Int = R.layout.fragment_setting

    override fun initViews(view: View) {
        super.initViews(view)
    }

    override fun addItemDecoration(): RecyclerView.ItemDecoration = object : BaseItemDecoration(mContext) {
        override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
            if (position == 0||position==7||position == 14||position==18)
                rect.bottom = ScreenSizeUtils.dp2px(mContext, 10f)
            else if(position==17){
                rect.bottom = ScreenSizeUtils.dp2px(mContext, 1f)

            }
        }

        override fun doRule(position: Int, rect: Rect) {
            rect.bottom = rect.top
        }
    }

    override fun initDatas() {
        super.initDatas()
        val list = arrayListOf<SettingIconBean>()
        for (index in 0..18) {
            val mSettingIconBean = SettingIconBean()
            when (index) {
                0 -> {
                    mSettingIconBean.tag = 2
                }
                1 -> {
                    mSettingIconBean.tag = 1
                }
                2 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "打印模式"
                }
                3 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "运单打印机"
                }
                4 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "标签打印机"

                }
                5 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "指环王设置"

                }
                6 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "网间托运单"

                }
                7 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "网内托运单"

                }
                /**
                 *
                 */
                8 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "常用收货网点"

                }
                9 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "常用目的地"

                }
                10 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "常用收货方式"

                }
                11 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "常用付货方式"

                }
                12 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "常用货物名称"

                }
                13 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "常用包装方式"

                }
                14 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "常用开单备注"

                }
                /**
                 *
                 */
                15 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "扫描备注"

                }
                16 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "语音播报开关"

                }
                17 -> {
                    mSettingIconBean.tag = 3
                    mSettingIconBean.title = "语音播报人"

                }
                18 -> {
                    mSettingIconBean.tag = 4
                }
            }
            list.add(mSettingIconBean)

        }
        adapter.appendData(list)

    }

    override fun getRecyclerViewId(): Int = R.id.setting_recycler_view

    override fun setAdapter(): BaseRecyclerAdapter<SettingIconBean> = SettingViewRecyclerAdapter(mContext)

}