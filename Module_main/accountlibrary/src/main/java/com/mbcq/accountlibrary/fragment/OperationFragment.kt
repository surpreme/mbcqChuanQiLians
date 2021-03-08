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
import kotlinx.android.synthetic.main.fragment_operation.*

/**
 * 运营
 */
class OperationFragment : BaseListFragment<IconViewBean>() {
    override fun getLayoutResId(): Int = R.layout.fragment_operation

    override fun initViews(view: View) {
        super.initViews(view)
        operation_toolbar.setPadding(0, getStatusBarHeight(), 0, 0)
        operation_toolbar.setCenterTitleText(UserInformationUtil.getWebIdCodeStr(mContext))

    }

    override fun initDatas() {
        super.initDatas()
        val list = arrayListOf<IconViewBean>()
        for (index in 0..9) {
            val mOperationViewBean = IconViewBean()
            when (index) {
                0 -> {
                    mOperationViewBean.tag = 1
                    mOperationViewBean.title = "发货管理"
                }
                1 -> {
                    mOperationViewBean.tag = 2
                    val listItem = arrayListOf<IconViewBean.ItemBean>()

                    for (mItemIndex in 0..18) {
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
                                item.itemText = "外转"
                            }
                            13 -> {
                                item.itemText = "短驳无计划扫描"
                            }
                            14 -> {
                                item.itemText = "干线无计划扫描"
                            }
                            15 -> {
                                item.itemText = "短驳有计划扫描"
                            }
                            16 -> {
                                item.itemText = "干线有计划扫描"
                            }
                            17 -> {
                                item.itemText = "短驳发车"
                            }
                            18 -> {
                                item.itemText = "干线发车"
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

                    for (mItemIndex in 0..12) {
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
                            11 -> {
                                item.itemText = "短驳到车"
                            }
                            12 -> {
                                item.itemText = "干线到车"
                            }

                        }
                        listItem.add(item)
                    }
                    mOperationViewBean.item = listItem
                }
                4 -> {
                    mOperationViewBean.tag = 1
                    mOperationViewBean.title = "回单管理"
                }
                5 -> {
                    mOperationViewBean.tag = 2
                    val listItem = arrayListOf<IconViewBean.ItemBean>()

                    for (mItemIndex in 0..5) {
                        val item = IconViewBean.ItemBean()
                        when (mItemIndex) {
                            0 -> {
                                item.itemText = "回单签收"
                            }
                            1 -> {
                                item.itemText = "回单寄出"
                            }
                            2 -> {
                                item.itemText = "回单接受"
                            }
                            3 -> {
                                item.itemText = "回单返厂"
                            }
                            4 -> {
                                item.itemText = "回单总账"
                            }
                            5 -> {
                                item.itemText = "取消操作"
                            }

                        }
                        listItem.add(item)
                    }
                    mOperationViewBean.item = listItem
                }
                6 -> {
                    mOperationViewBean.tag = 1
                    mOperationViewBean.title = "其他管理"
                }
                7 -> {
                    mOperationViewBean.tag = 2
                    val listItem = arrayListOf<IconViewBean.ItemBean>()

                    for (mItemIndex in 0..10) {
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

                            9 -> {
                                item.itemText = "异常登记"
                            }
                            10 -> {
                                item.itemText = "控货管理"
                            }

                        }
                        listItem.add(item)
                    }
                    mOperationViewBean.item = listItem
                }
                8 -> {
                    mOperationViewBean.tag = 1
                    mOperationViewBean.title = "货款管理"
                }
                9 -> {
                    mOperationViewBean.tag = 2
                    val listItem = arrayListOf<IconViewBean.ItemBean>()

                    for (mItemIndex in 0..3) {
                        val item = IconViewBean.ItemBean()
                        when (mItemIndex) {
                            0 -> {
                                item.itemText = "货款变更"
                            }
                            1 -> {
                                item.itemText = "货款回收"
                            }
                            2 -> {
                                item.itemText = "货款发放"
                            }
                            3 -> {
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

    override fun getRecyclerViewId(): Int = R.id.operation_recycler_view

    override fun setAdapter(): BaseRecyclerAdapter<IconViewBean> {
        return IconViewRecyclerAdapter(mContext).also {
            it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                override fun onItemClick(v: View, position: Int, mResult: String) {
                    LogUtils.d("result" + mResult + "position" + position)
                    when (mResult) {
                        "1" -> {
                            when (position) {
                                0 -> {
                                    ARouter.getInstance().build(ARouterConstants.AcceptBillingActivity).navigation()
                                }
                                1 -> {
                                    ARouter.getInstance().build(ARouterConstants.WaybillRecordActivity).navigation()

                                }
                                2 -> {
                                    ARouter.getInstance().build(ARouterConstants.HomeDeliveryActivity).navigation()

                                }
                                3 -> {
                                    ARouter.getInstance().build(ARouterConstants.AcceptBillingRecordingActivity).navigation()

                                }
                                4 -> {
                                    ARouter.getInstance().build(ARouterConstants.LoadingVehiclesActivity).navigation()

                                }
                                7 -> {
                                    ARouter.getInstance().build(ARouterConstants.PrintAcceptBillingActivity).navigation()

                                }
                                8 -> {
                                    //TODO 当日运单删除
                                }

                                9 -> {
                                    ARouter.getInstance().build(ARouterConstants.ShipmentInventoryActivity).navigation()

                                }
                                10 -> {
                                    ARouter.getInstance().build(ARouterConstants.StowageAlongWayActivity).navigation()

                                }
                                11 -> {
                                    ARouter.getInstance().build(ARouterConstants.DepartureRecordActivity).navigation()

                                }
                                12 -> {
                                    ARouter.getInstance().build(ARouterConstants.LocalAgentActivity).navigation()

                                }
                                13 -> {
                                    ARouter.getInstance().build(ARouterConstants.ShortBargeUnLoadingVehiclesActivity).navigation()

                                }
                                14 -> {
                                    ARouter.getInstance().build(ARouterConstants.TrunkUnLoadingVehiclesActivity).navigation()

                                }
                                15 -> {
                                    ARouter.getInstance().build(ARouterConstants.ShortBargeLoadingVehiclesActivity).navigation()

                                }
                                16 -> {
                                    ARouter.getInstance().build(ARouterConstants.TrunkLoadingVehiclesActivity).navigation()

                                }
                                17 -> {
                                    ARouter.getInstance().build(ARouterConstants.ShortBargeDepartureRecordActivity).navigation()

                                }
                                18 -> {
                                    ARouter.getInstance().build(ARouterConstants.TrunkDepartureRecordActivity).navigation()

                                }
                            }
                        }
                        "3" -> {
                            when (position) {
                                0 -> {
                                    ARouter.getInstance().build(ARouterConstants.ArrivalVehiclesScanActivity).navigation()

                                }
                                2 -> {
                                    ARouter.getInstance().build(ARouterConstants.ArrivalRecordActivity).navigation()

                                }
                                7 -> {
                                    ARouter.getInstance().build(ARouterConstants.DeliverySomeThingActivity).navigation()

                                }
                                4 -> {
                                    ARouter.getInstance().build(ARouterConstants.ArrivalInventoryActivity).navigation()

                                }
                                8 -> {
                                    ARouter.getInstance().build(ARouterConstants.GoodsReceiptHouseActivity).navigation()

                                }
                                9 -> {
                                    ARouter.getInstance().build(ARouterConstants.GoodsReceiptHouseActivity).navigation()

                                }
                                10 -> {
                                    ARouter.getInstance().build(ARouterConstants.TerminalAgentActivity).navigation()

                                }
                                11 -> {
                                    ARouter.getInstance().build(ARouterConstants.ShortBargeArrivalRecordActivity).navigation()

                                }
                                12 -> {
                                    ARouter.getInstance().build(ARouterConstants.TrunkArrivalRecordActivity).navigation()

                                }
                            }
                        }
                        "5" -> {
                            when (position) {
                                0 -> {
                                    ARouter.getInstance().build(ARouterConstants.ReceiptSignActivity).navigation()

                                }
                                1 -> {
                                    ARouter.getInstance().build(ARouterConstants.ReceiptConsignmentActivity).navigation()

                                }
                                2 -> {
                                    ARouter.getInstance().build(ARouterConstants.ReceiptReceiveActivity).navigation()

                                }
                                3 -> {
                                    ARouter.getInstance().build(ARouterConstants.ReceiptReturnFactoryActivity).navigation()

                                }
                                4 -> {
                                    ARouter.getInstance().build(ARouterConstants.ReceiptGeneralLedgerActivity).navigation()

                                }
                                5 -> {
                                    ARouter.getInstance().build(ARouterConstants.ReceiptCancelActivity).navigation()

                                }
                            }
                        }
                        "7" -> {
                            when (position) {
                                5 -> {
                                    ARouter.getInstance().build(ARouterConstants.ClaimSettlementActivity).navigation()
                                }
                                8 -> {
                                    ARouter.getInstance().build(ARouterConstants.VehicleArchivesActivity).navigation()
                                }
                                9 -> {
                                    ARouter.getInstance().build(ARouterConstants.ExceptionRegistrationActivity).navigation()
                                }
                                10 -> {
                                    ARouter.getInstance().build(ARouterConstants.ControlManagementActivity).navigation()
                                }
                            }
                        }
                        "9" -> {
                            when (position) {
                                0 -> {
                                    ARouter.getInstance().build(ARouterConstants.LoanChangeActivity).navigation()

                                }
                                1 -> {
                                    ARouter.getInstance().build(ARouterConstants.LoanRecycleActivity).navigation()

                                }
                                2 -> {
                                    ARouter.getInstance().build(ARouterConstants.LoanIssuanceActivity).navigation()

                                }
                                3 -> {
                                    ARouter.getInstance().build(ARouterConstants.GeneralLedgerActivity).navigation()

                                }
                            }
                        }
                    }
                }
            }
        }
    }
}