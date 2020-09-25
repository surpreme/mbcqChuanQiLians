package com.mbcq.accountlibrary.fragment

import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.accountlibrary.R
import com.mbcq.accountlibrary.fragment.iconadapter.IconViewBean
import com.mbcq.accountlibrary.fragment.iconadapter.IconViewRecyclerAdapter
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseListFragment
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.commonlibrary.ARouterConstants
import kotlinx.android.synthetic.main.fragment_operation.*

/**
 * 运营
 */
class OperationFragment : BaseListFragment<IconViewBean>() {
    override fun getLayoutResId(): Int = R.layout.fragment_operation

    override fun initViews(view: View) {
        super.initViews(view)
        operation_toolbar.setPadding(0, getStatusBarHeight(), 0, 0)
    }

    override fun initDatas() {
        super.initDatas()
        val list = arrayListOf<IconViewBean>()
        for (index in 0..5) {
            val mOperationViewBean = IconViewBean()
            when (index) {
                0 -> {
                    mOperationViewBean.tag = 1
                    mOperationViewBean.title = "发货管理"
                }
                1 -> {
                    mOperationViewBean.tag = 2
                    val listItem = arrayListOf<IconViewBean.ItemBean>()

                    for (mItemIndex in 0..12) {
                        val item = IconViewBean.ItemBean()
                        when (mItemIndex) {
                            0 -> {
                                item.itemText = "受理开单"
                            }
                            1 -> {
                                item.itemText = "运单记录"
                            }
                            2 -> {
                                item.itemText = "上门提货"
                            }
                            3 -> {
                                item.itemText = "改单申请"
                            }
                            4 -> {
                                item.itemText = "装车"
                            }
                            5 -> {
                                item.itemText = "发车扫描"
                            }
                            6 -> {
                                item.itemText = "异常登记"
                            }
                            7 -> {
                                item.itemText = "运单标签补打"
                            }
                            8 -> {
                                item.itemText = "当日运单"
                            }
                            9 -> {
                                item.itemText = "发货库存"
                            }
                            10 -> {
                                item.itemText = "沿途配载"
                            }
                            11 -> {
                                item.itemText = "发车记录"
                            }
                            12 -> {
                                item.itemText = "本地代理"
                            }

                        }
                        listItem.add(item)
                    }
                    mOperationViewBean.item = listItem
                }
                2 -> {
                    mOperationViewBean.tag = 1
                    mOperationViewBean.title = "到货管理"
                }
                3 -> {
                    mOperationViewBean.tag = 2
                    val listItem = arrayListOf<IconViewBean.ItemBean>()

                    for (mItemIndex in 0..10) {
                        val item = IconViewBean.ItemBean()
                        when (mItemIndex) {
                            0 -> {
                                item.itemText = "到车扫描"
                            }
                            1 -> {
                                item.itemText = "卸车"
                            }
                            2 -> {
                                item.itemText = "到车记录"
                            }
                            3 -> {
                                item.itemText = "卸货入库"
                            }
                            4 -> {
                                item.itemText = "到货库存"
                            }
                            5 -> {
                                item.itemText = "送货上门"
                            }
                            6 -> {
                                item.itemText = "送货计划中"
                            }
                            7 -> {
                                item.itemText = "送货记录"
                            }
                            8 -> {
                                item.itemText = "货物签收"
                            }
                            9 -> {
                                item.itemText = "签收记录"
                            }
                            10 -> {
                                item.itemText = "终端代理"
                            }
                        }
                        listItem.add(item)
                    }
                    mOperationViewBean.item = listItem
                }
                4 -> {
                    mOperationViewBean.tag = 1
                    mOperationViewBean.title = "其他管理"
                }
                5 -> {
                    mOperationViewBean.tag = 2
                    val listItem = arrayListOf<IconViewBean.ItemBean>()

                    for (mItemIndex in 0..8) {
                        val item = IconViewBean.ItemBean()
                        when (mItemIndex) {
                            0 -> {
                                item.itemText = "盘库"
                            }
                            1 -> {
                                item.itemText = "付款单审核"
                            }
                            2 -> {
                                item.itemText = "货物追踪"
                            }
                            3 -> {
                                item.itemText = "控货管理"
                            }
                            4 -> {
                                item.itemText = "回单管理"
                            }
                            5 -> {
                                item.itemText = "理赔记录"
                            }
                            6 -> {
                                item.itemText = "客户档案"
                            }
                            7 -> {
                                item.itemText = "资金账户"
                            }
                            8 -> {
                                item.itemText = "车辆档案"
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

    override fun getRecyclerViewId(): Int = R.id.operation_recycler_view

    override fun setAdapter(): BaseRecyclerAdapter<IconViewBean> {
        return IconViewRecyclerAdapter(mContext).also {
            it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                override fun onItemClick(v: View, position: Int, mResult: String) {
                    LogUtils.d("result" + mResult + "position" + position)
                    if (mResult == "1") {
                        when (position) {
                            0 -> {
                                ARouter.getInstance().build(ARouterConstants.AcceptBillingActivity).navigation()
                            }
                            1 -> {
                                ARouter.getInstance().build(ARouterConstants.WaybillRecordActivity).navigation()

                            }
                            11 -> {
                                ARouter.getInstance().build(ARouterConstants.DepartureRecordActivity).navigation()

                            }
                            12 -> {
                                ARouter.getInstance().build(ARouterConstants.LocalAgentActivity).navigation()

                            }
                        }
                    } else if (mResult == "3") {
                        when (position) {
                            2 -> {
                                ARouter.getInstance().build(ARouterConstants.ArrivalRecordActivity).navigation()

                            }
                            10 -> {
                                ARouter.getInstance().build(ARouterConstants.TerminalAgentActivity).navigation()

                            }
                        }
                    }
                }
            }
        }
    }
}