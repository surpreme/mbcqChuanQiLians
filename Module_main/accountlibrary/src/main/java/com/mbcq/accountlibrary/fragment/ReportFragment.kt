package com.mbcq.accountlibrary.fragment

import android.view.View
import com.mbcq.accountlibrary.R
import com.mbcq.accountlibrary.fragment.iconadapter.IconViewBean
import com.mbcq.accountlibrary.fragment.iconadapter.IconViewRecyclerAdapter
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseListFragment
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_report.*

/**
 * 报表
 */
class ReportFragment : BaseListFragment<IconViewBean>(){
    override fun getLayoutResId(): Int = R.layout.fragment_report

    override fun initViews(view: View) {
        super.initViews(view)
        report_toolbar.setPadding(0, getStatusBarHeight(), 0, 0)
        report_toolbar.setCenterTitleText(UserInformationUtil.getWebIdCodeStr(mContext))

    }

    override fun initDatas() {
        super.initDatas()
        val list = arrayListOf<IconViewBean>()
        for (index in 0..1) {
            val mOperationViewBean = IconViewBean()
            when (index) {
                0 -> {
                    mOperationViewBean.tag = 1
                    mOperationViewBean.title = "财务类报表"
                }
                1 -> {
                    mOperationViewBean.tag = 2
                    val listItem = arrayListOf<IconViewBean.ItemBean>()

                    for (mItemIndex in 0..12) {
                        val item = IconViewBean.ItemBean()
                        when (mItemIndex) {
                            0 -> {
                                item.itemText = "营业额汇总"
                            }
                            1 -> {
                                item.itemText = "网点出库汇总表"
                            }
                            2 -> {
                                item.itemText = "网点发货汇总表"
                            }
                            3 -> {
                                item.itemText = "网点到货汇总表"
                            }
                            4 -> {
                                item.itemText = "单车毛利"
                            }
                            5 -> {
                                item.itemText = "上转发车汇总表"
                            }
                            6 -> {
                                item.itemText = "干线发车汇总表"
                            }
                            7 -> {
                                item.itemText = "下转发车汇总表"
                            }
                            8 -> {
                                item.itemText = "网点未报账统计表"
                            }
                            9 -> {
                                item.itemText = "科目余额表"
                            }
                            10 -> {
                                item.itemText = "大车费分摊表"
                            }
                            11 -> {
                                item.itemText = "运单时效跟踪表"
                            }

                            12 -> {
                                item.itemText = "修改运单统计表"
                            }

                        }
                        listItem.add(item)
                    }


                    mOperationViewBean.item = listItem
                }
            }
            list.add(mOperationViewBean)

        }
        adapter.appendData(list)
    }

    override fun getRecyclerViewId(): Int = R.id.report_recycler_view

    override fun setAdapter(): BaseRecyclerAdapter<IconViewBean> {
        return IconViewRecyclerAdapter(mContext).also {
            it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                override fun onItemClick(v: View, position: Int, result: String) {
                    LogUtils.d("result" + result + "position" + position)
                    if (result == "1" && position == 0) {
//                        ARouter.getInstance().build(ARouterConstants.AcceptBillingActivity).navigation()
                    }
                }
            }
        }
    }

}