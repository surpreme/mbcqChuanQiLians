package com.mbcq.accountlibrary.fragment.finance

/*
import com.mbcq.accountlibrary.fragment.iconadapter.IconViewBean

if (true)return
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
                        item.itemText = "回单付核销"
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
if (true)return

when (mResult) {
    "1" -> {
        when (position) {
            0 -> {
                ARouter.getInstance().build(ARouterConstants.CommonWriteOffActivity).withString("xTitle", "现付核销").navigation()
            }
            1 -> {
                ARouter.getInstance().build(ARouterConstants.CommonWriteOffActivity).withString("xTitle", "提付核销").navigation()

            }
            2 -> {
                ARouter.getInstance().build(ARouterConstants.CommonWriteOffActivity).withString("xTitle", "回单付核销").navigation()

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
}*/
