package com.mbcq.vehicleslibrary.activity.departurerecord


import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_departure_record.*

/**
 * @author: lzy
 * @time: 2020-09-12 10:50:33
 * 发车记录
 */
@Route(path = ARouterConstants.DepartureRecordActivity)
class DepartureRecordActivity : BaseDepartureRecordActivity<DepartureRecordContract.View, DepartureRecordPresenter>(), DepartureRecordContract.View {

    override fun getLayoutId(): Int = R.layout.activity_departure_record
    override fun onClick() {
        super.onClick()
        short_feeder_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                indexTop(1)
                setTabSelection(0)

            }

        })
        main_line_departure_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                indexTop(2)
                setTabSelection(1)

            }

        })
        departure_billing_toolbar.setBackButtonOnClickListener {
            onBackPressed()
        }
        departure_billing_toolbar.setRightButtonOnClickListener {
            getDbWebId(object : WebDbInterface {
                override fun isNull() {

                }

                override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                    FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "${if (mFragmentTag_index == 0) "短驳" else "干线"}发车记录筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
                        /**
                         * s1 网点
                         * s2  start@end
                         */
                        override fun onResult(s1: String, s2: String) {
                            val mDepartureRecordBus = DepartureRecordEvent()
                            mDepartureRecordBus.type = mFragmentTag_index
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

    override fun onRestart() {
        super.onRestart()
        if (intent.getBooleanExtra("IsRefresh", false)) {
            RxBus.build().post(DepartureRecordRefreshEvent::class.java)
        }
        if (intent.getBooleanExtra("TrunkDepartureIsRefresh", false)) {
            RxBus.build().post(TrunkDepartureIsRefreshEvent::class.java)
        }
    }
}