package com.mbcq.vehicleslibrary.activity.allarrivalrecord.arrivalrecord.trunk

import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.ui.BaseActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.allarrivalrecord.arrivalrecord.ArrivalRecordEvent
import kotlinx.android.synthetic.main.activity_short_barge_arrival_record.*

@Route(path = ARouterConstants.TrunkArrivalRecordActivity)
class TrunkArrivalRecordActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_short_barge_arrival_record
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        arrival_record_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
        arrival_record_toolbar.setRightButtonOnClickListener {
            WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                override fun isNull() {

                }

                override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                    FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "干线到车记录筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
                        /**
                         * s1 网点
                         * s2  start@end
                         */

                        override fun onResult(s1: String, s2: String) {
                            val mArrivalRecordEvent = ArrivalRecordEvent()
                            mArrivalRecordEvent.type = 1
                            mArrivalRecordEvent.webCode = s1
                            val timeList = s2.split("@")
                            if (timeList.isNotEmpty() && timeList.size == 2) {
                                mArrivalRecordEvent.startTime = timeList[0]
                                mArrivalRecordEvent.endTime = timeList[1]
                            }
                            RxBus.build().post(mArrivalRecordEvent)

                        }

                    }).show(supportFragmentManager, "mArrivalRecordFilterWithTimeDialog")
                }

            })
        }


    }
}