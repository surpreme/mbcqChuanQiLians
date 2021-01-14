package com.mbcq.accountlibrary.fragment

import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.accountlibrary.R
import com.mbcq.accountlibrary.fragment.iconadapter.IconViewBean
import com.mbcq.accountlibrary.fragment.iconadapter.IconViewRecyclerAdapter
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseListFragment
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.commonlibrary.ARouterConstants
import kotlinx.android.synthetic.main.fragment_finance.*

/**
 * 财务
 */
class FinanceFragment : BaseListFragment<IconViewBean>() {
    override fun getLayoutResId(): Int = R.layout.fragment_finance
    override fun initViews(view: View) {
        super.initViews(view)
        finance_toolbar.setPadding(0, getStatusBarHeight(), 0, 0)
        finance_toolbar.setCenterTitleText(UserInformationUtil.getWebIdCodeStr(mContext))
    }

    override fun initDatas() {
        super.initDatas()
        val list = arrayListOf<IconViewBean>()
        for (index in 0..5) {
            val mOperationViewBean = IconViewBean()
            when (index) {
                0 -> {
                    mOperationViewBean.tag = 1
                    mOperationViewBean.title = "应收核销"
                }
                1 -> {
                    mOperationViewBean.tag = 2
                    val listItem = arrayListOf<IconViewBean.ItemBean>()
                    for (mItemIndex in 0..4) {
                        val item = IconViewBean.ItemBean()
                        when (mItemIndex) {
                            0 -> {
                                item.itemText = "现付核销"
                            }
                            1 -> {
                                item.itemText = "提付核销"
                            }
                            2 -> {
                                item.itemText = "回单核销"
                            }
                            3 -> {
                                item.itemText = "月结核销"
                            }
                            4 -> {
                                item.itemText = "提包费收入核销"
                            }

                        }
                        listItem.add(item)
                    }


                    mOperationViewBean.item = listItem
                }
                2 -> {
                    mOperationViewBean.tag = 1
                    mOperationViewBean.title = "应付核销"
                }
                3 -> {
                    mOperationViewBean.tag = 2
                    val listItem = arrayListOf<IconViewBean.ItemBean>()
                    for (mItemIndex in 0..7) {
                        val item = IconViewBean.ItemBean()
                        when (mItemIndex) {
                            0 -> {
                                item.itemText = "返款核销"
                            }
                            1 -> {
                                item.itemText = "提货费核销"
                            }
                            2 -> {
                                item.itemText = "中转费核销"
                            }
                            3 -> {
                                item.itemText = "短驳车费核销"
                            }
                            4 -> {
                                item.itemText = "干线车费核销"
                            }
                            5 -> {
                                item.itemText = "送货费支出核销"
                            }
                            6 -> {
                                item.itemText = "提包费支出核销"
                            }
                            7 -> {
                                item.itemText = "异常理赔支出核销"
                            }

                        }
                        listItem.add(item)
                    }
                    mOperationViewBean.item = listItem

                }
                4 -> {
                    mOperationViewBean.tag = 1
                    mOperationViewBean.title = "其他"
                }
                5 -> {
                    mOperationViewBean.tag = 2
                    val listItem = arrayListOf<IconViewBean.ItemBean>()
                    for (mItemIndex in 0..5) {
                        val item = IconViewBean.ItemBean()
                        when (mItemIndex) {
                            0 -> {
                                item.itemText = "运费异动核销"
                            }
                            1 -> {
                                item.itemText = "网点收款报账单"
                            }
                            2 -> {
                                item.itemText = "网点付款报账单"
                            }
                            3 -> {
                                item.itemText = "反核销"
                            }
                            4 -> {
                                item.itemText = "应收应付汇总"
                            }
                            5 -> {
                                item.itemText = "代理对账"
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
                    when (result) {
                        "1" -> {
                            when (position) {
                                0 -> {
                                    ARouter.getInstance().build(ARouterConstants.CommonWriteOffActivity).withString("xTitle", "现付核销").navigation()
                                }
                                1 -> {
                                    ARouter.getInstance().build(ARouterConstants.CommonWriteOffActivity).withString("xTitle", "提付核销").navigation()

                                }
                                2 -> {
                                    ARouter.getInstance().build(ARouterConstants.CommonWriteOffActivity).withString("xTitle", "回单核销").navigation()

                                }
                                3 -> {
                                    ARouter.getInstance().build(ARouterConstants.CommonWriteOffActivity).withString("xTitle", "月结核销").navigation()
                                }
                            }
                        }
                        "3" -> {
                            when (position) {
                                0 -> {
                                    ARouter.getInstance().build(ARouterConstants.CommonReceiveWriteOffActivity).withString("xTitle", "返款核销").navigation()
                                }
                                1 -> {
//                                    ARouter.getInstance().build(ARouterConstants.CommonWriteOffActivity).withString("xTitle", "提货费核销").navigation()
                                }
                                2 -> {
                                    ARouter.getInstance().build(ARouterConstants.CommonReceiveWriteOffActivity).withString("xTitle", "中转费核销").navigation()
                                }
                                3 -> {
                                    ARouter.getInstance().build(ARouterConstants.CommonReceiveWriteOffActivity).withString("xTitle", "短驳车费核销").navigation()
                                }
                                4 -> {
                                    ARouter.getInstance().build(ARouterConstants.CommonReceiveWriteOffActivity).withString("xTitle", "干线车费核销").navigation()
                                }
                            }
                        }
                    }

                }
            }
        }
    }

}