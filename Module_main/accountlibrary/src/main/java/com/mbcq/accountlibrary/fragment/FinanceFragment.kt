package com.mbcq.accountlibrary.fragment

import android.view.View
import com.mbcq.accountlibrary.R
import com.mbcq.accountlibrary.fragment.iconadapter.IconViewBean
import com.mbcq.accountlibrary.fragment.iconadapter.IconViewRecyclerAdapter
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseListFragment
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_finance.*

/**
 * 财务
 */
class FinanceFragment : BaseListFragment<IconViewBean>() {
    override fun getLayoutResId(): Int = R.layout.fragment_finance
    override fun initViews(view: View) {
        super.initViews(view)
        finance_toolbar.setPadding(0, getStatusBarHeight(), 0, 0)

    }

    override fun initDatas() {
        super.initDatas()
        val list = arrayListOf<IconViewBean>()
        for (index in 0..1) {
            val mOperationViewBean = IconViewBean()
            when (index) {
                0 -> {
                    mOperationViewBean.tag = 1
                    mOperationViewBean.title = "财务管理"
                }
                1 -> {
                    mOperationViewBean.tag = 2
                    val listItem = arrayListOf<IconViewBean.ItemBean>()

                    for (mItemIndex in 0..19) {
                        val item = IconViewBean.ItemBean()
                        when (mItemIndex) {
                            0 -> {
                                item.itemText = "现付核销"
                            }
                            1 -> {
                                item.itemText = "提付核销"
                            }
                            2 -> {
                                item.itemText = "回单月结核销"
                            }
                            3 -> {
                                item.itemText = "提包费收入核销"
                            }
                            4 -> {
                                item.itemText = "返款核销"
                            }
                            5 -> {
                                item.itemText = "提货费核销"
                            }
                            6 -> {
                                item.itemText = "上转车费核销"
                            }
                            7 -> {
                                item.itemText = "运费异动核销"
                            }
                            8 -> {
                                item.itemText = "中转费核销"
                            }
                            9 -> {
                                item.itemText = "干线大车费核销"
                            }
                            10 -> {
                                item.itemText = "送货费支出核销"
                            }
                            11 -> {
                                item.itemText = "下转车费核销"
                            }

                            12 -> {
                                item.itemText = "提包费支出核销"
                            }

                            13 -> {
                                item.itemText = "异常理赔支出核销"
                            }

                            14 -> {
                                item.itemText = "网点收款报账单"
                            }
                            15 -> {
                                item.itemText = "网点付款报账单"
                            }

                            16 -> {
                                item.itemText = "返核销"
                            }

                            17 -> {
                                item.itemText = "货款变更"
                            }
                            18 -> {
                                item.itemText = "货款回收"
                            }
                            19 -> {
                                item.itemText = "货款总账"
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


    override fun getRecyclerViewId(): Int = R.id.finance_recycler_view

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