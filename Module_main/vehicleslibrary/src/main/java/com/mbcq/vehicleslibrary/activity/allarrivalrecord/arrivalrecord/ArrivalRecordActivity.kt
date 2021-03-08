package com.mbcq.vehicleslibrary.activity.allarrivalrecord.arrivalrecord


import android.annotation.SuppressLint
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
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.alllocalagent.event.LocalGentShortFeederHouseEvent
import kotlinx.android.synthetic.main.activity_arrival_record.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * @author: lzy
 * @time: 2020-09-09 17:22:09
 * 到车记录
 */
@Route(path = ARouterConstants.ArrivalRecordActivity)
class ArrivalRecordActivity : BaseArrivalRecordActivity<ArrivalRecordContract.View, ArrivalRecordPresenter>(), ArrivalRecordContract.View {

    override fun getLayoutId(): Int = R.layout.activity_arrival_record

    override fun isOpenEventBus(): Boolean = true

    @SuppressLint("SetTextI18n")
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = false)
    fun onRefreshArrivalRecordNewDataEvent(event: ArrivalRecordRefreshEvent) {
        if (event.type == 1)
            short_feeder_tv.text = "短驳到车(${event.num})"
        else if (event.type == 2)
            main_line_departure_tv.text = "干线到车(${event.num})"
    }

    override fun onClick() {
        super.onClick()
        arrival_record_toolbar.setRightButtonOnClickListener {
            WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                override fun isNull() {

                }

                override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                    FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "${if (mFragmentTag_index == 0) "短驳" else "干线"}到车记录筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
                        /**
                         * s1 网点
                         * s2  start@end
                         */
                        override fun onResult(s1: String, s2: String) {
                            val mArrivalRecordEvent = ArrivalRecordEvent()
                            mArrivalRecordEvent.type = mFragmentTag_index
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

    }
}