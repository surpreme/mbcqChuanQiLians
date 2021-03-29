package com.mbcq.vehicleslibrary.activity.homedelivery


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseSmartMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.FilterTimeUtils
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_home_delivery.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-01-14 15:44:03 上门提货
 */

@Route(path = ARouterConstants.HomeDeliveryActivity)
class HomeDeliveryActivity : BaseSmartMVPActivity<HomeDeliveryContract.View, HomeDeliveryPresenter, HomeDeliveryBean>(), HomeDeliveryContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""

    override fun getLayoutId(): Int = R.layout.activity_home_delivery

    @SuppressLint("SimpleDateFormat")
    override fun initExtra() {
        super.initExtra()
        mStartDateTag = FilterTimeUtils.getStartTime(7)
        mEndDateTag = FilterTimeUtils.getEndTime()
        mShippingOutletsTag = UserInformationUtil.getWebIdCode(mContext)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getPage(mCurrentPage, mShippingOutletsTag, mStartDateTag, mEndDateTag)

    }

    override fun onClick() {
        super.onClick()
        add_btn.apply {
            onSingleClicks {
                ARouter.getInstance().build(ARouterConstants.AddHomeDeliveryActivity).navigation()
            }
        }
        home_delivery_toolbar.setRightButtonOnClickListener(object :SingleClick(){
            override fun onSingleClick(v: View?) {
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {

                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "上门提货记录筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
                            /**
                             * s1 网点
                             * s2  start@end
                             */
                            override fun onResult(s1: String, s2: String) {
                                mShippingOutletsTag = s1
                                val timeList = s2.split("@")
                                if (timeList.isNotEmpty() && timeList.size == 2) {
                                    mStartDateTag = timeList[0]
                                    mEndDateTag = timeList[1]
                                }
                                refresh()

                            }

                        }).show(supportFragmentManager, "HomeDeliveryFilterWithTimeDialog")
                    }

                })
            }

        })
        home_delivery_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun getSmartLayoutId(): Int = R.id.home_delivery_recording_smart
    override fun getSmartEmptyId(): Int = R.id.home_delivery_recording_smart_frame
    override fun getRecyclerViewId(): Int = R.id.home_delivery_recording_recycler

    override fun setAdapter(): BaseRecyclerAdapter<HomeDeliveryBean> = HomeDeliveryAdapter(mContext).also {
        it.mOnDoingInterface = object : HomeDeliveryAdapter.OnDoingInterface {
            override fun onFixed(v: View, position: Int, result: String) {
                ARouter.getInstance().build(ARouterConstants.FixHomeDeliveryConfigurationActivity).withString("FixHomeDeliveryConfiguration", result).navigation()

            }

            override fun onDelete(v: View, position: Int, result: String) {
                val mRecyclerObj = JSONObject(result)
                TalkSureCancelDialog(mContext,getScreenWidth(),"您确认要作废上门提货${mRecyclerObj.optString("inOneVehicleFlag")}吗？"){
                    val obj = JSONObject()
                    obj.put("id", mRecyclerObj.optString("id"))
                    obj.put("inOneVehicleFlag", mRecyclerObj.optString("inOneVehicleFlag"))
                    obj.put("pickUpdetLst", JSONArray())
                    mPresenter?.onDelete(GsonUtils.toPrettyFormat(obj),position)
                }.show()


            }

        }
        it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                ARouter.getInstance().build(ARouterConstants.FixHomeDeliveryHouseActivity).withString("FixHomeDeliveryHouse", mResult).navigation()
            }

        }
    }

    override fun getPageS(list: List<HomeDeliveryBean>) {
        appendDatas(list)
    }

    override fun onDeleteS(position: Int) {
        adapter.removeItem(position)
    }
}