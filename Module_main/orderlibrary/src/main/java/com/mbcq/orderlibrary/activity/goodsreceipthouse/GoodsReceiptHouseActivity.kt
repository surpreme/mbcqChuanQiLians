package com.mbcq.orderlibrary.activity.goodsreceipthouse

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_goods_receipt_house.*

@Route(path = ARouterConstants.GoodsReceiptHouseActivity)
class GoodsReceiptHouseActivity : BaseGoodsReceiptHouseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_goods_receipt_house


    override fun onClick() {
        super.onClick()
        goods_receipt_house_toolbar.setRightButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {

                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "签收记录筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
                            /**
                             * s1 网点
                             * s2  start@end
                             */
                            override fun onResult(s1: String, s2: String) {
                                val mGoodsReceiptHouseEvent = GoodsReceiptHouseEvent()
                                mGoodsReceiptHouseEvent.type = mFragmentTag_index
                                mGoodsReceiptHouseEvent.webCode = s1
                                val timeList = s2.split("@")
                                if (timeList.isNotEmpty() && timeList.size == 2) {
                                    mGoodsReceiptHouseEvent.startTime = timeList[0]
                                    mGoodsReceiptHouseEvent.endTime = timeList[1]
                                }
                                RxBus.build().post(mGoodsReceiptHouseEvent)

                            }

                        }).show(supportFragmentManager, "GoodsReceiptHouseFilterWithTimeDialog")
                    }

                })
            }
        })
        goods_receipt_house_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

}