package com.mbcq.vehicleslibrary.activity.alldeparturerecord.departurerecord.trunk

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
import com.mbcq.vehicleslibrary.activity.alldeparturerecord.departurerecord.DepartureRecordEvent
import kotlinx.android.synthetic.main.activity_trunk_departure_record.*

@Route(path = ARouterConstants.TrunkDepartureRecordActivity)
class TrunkDepartureRecordActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_trunk_departure_record
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)

    }
    override fun onClick() {
        super.onClick()

        departure_billing_toolbar.setBackButtonOnClickListener {
            onBackPressed()
        }
        departure_billing_toolbar.setRightButtonOnClickListener {
            WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                override fun isNull() {

                }

                override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                    FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "干线发车记录筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
                        /**
                         * s1 网点
                         * s2  start@end
                         */
                        override fun onResult(s1: String, s2: String) {
                            val mDepartureRecordBus = DepartureRecordEvent()
                            mDepartureRecordBus.type = 1
                            mDepartureRecordBus.webCode = s1
                            val timeList = s2.split("@")
                            if (timeList.isNotEmpty() && timeList.size == 2) {
                                mDepartureRecordBus.startTime = timeList[0]
                                mDepartureRecordBus.endTime = timeList[1]
                            }
                            RxBus.build().post(mDepartureRecordBus)

                        }

                    }).show(supportFragmentManager, "DepartureRecordFilterWithTimeDialog")
                }

            })
        }
    }


}