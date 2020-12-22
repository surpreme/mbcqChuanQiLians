package com.mbcq.vehicleslibrary.fragment.shortfeeder


import android.annotation.SuppressLint
import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.ui.BaseSmartMVPFragment
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.activity.alldeparturerecord.departurerecord.DepartureRecordEvent
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.alldeparturerecord.departurerecord.DepartureRecordRefreshEvent
import kotlinx.android.synthetic.main.fragment_short_feeder.*
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author: lzy
 * @time: 2020-09-12 13:08：55
 * 短驳发车记录
 */

class ShortFeederFragment : BaseSmartMVPFragment<ShortFeederContract.View, ShortFeederPresenter, ShortFeederBean>(), ShortFeederContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""//发货网点
    override fun getLayoutResId(): Int = R.layout.fragment_short_feeder
    override fun getSmartLayoutId(): Int = R.id.short_feeder_smart
    override fun getSmartEmptyId(): Int = R.id.short_feeder_smart_frame
    override fun getRecyclerViewId(): Int = R.id.short_feeder_recycler
    override fun setAdapter(): BaseRecyclerAdapter<ShortFeederBean> = ShortFeederAdapter(mContext).also {
        it.mOnShortFeederClickInterface=object :ShortFeederAdapter.OnShortFeederClickInterface{
            override fun onModify(v: View, position: Int, itemData: ShortFeederBean) {
                val job = JSONObject()
                job.put("InoneVehicleFlag", itemData.inoneVehicleFlag)
                job.put("Id", itemData.id)
                ARouter.getInstance().build(ARouterConstants.FixShortFeederHouseActivity).withString("FixedShortFeederHouse", GsonUtils.toPrettyFormat(job.toString())).navigation()
            }

            override fun onPint(v: View, position: Int, itemData: ShortFeederBean) {
            }

            override fun onClickItem(v: View, position: Int, itemData: ShortFeederBean) {
                val job = JSONObject()
                job.put("InoneVehicleFlag", itemData.inoneVehicleFlag)
                job.put("Id", itemData.id)
                ARouter.getInstance().build(ARouterConstants.FixShortFeederHouseActivity).withString("FixedShortFeederHouse", GsonUtils.toPrettyFormat(job.toString())).navigation()
            }

        }
    }
    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getShortFeeder(mCurrentPage, mShippingOutletsTag, mStartDateTag, mEndDateTag)

    }

    override fun onClick() {
        super.onClick()
        modify_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mContext.let {
                    var mItemdata: ShortFeederBean? = null
                    for (item in adapter.getAllData()) {
                        if (item.isChecked) {
                            mItemdata = item
                        }
                    }
                    if (mItemdata?.vehicleState==3){
                        showToast("发车状态的车的不可以修改")
                        return@let
                    }
                    if (mItemdata != null) {
                        val job = JSONObject()
                        job.put("InoneVehicleFlag", mItemdata.inoneVehicleFlag)
                        job.put("Id", mItemdata.id)
                        ARouter.getInstance().build(ARouterConstants.FixedShortFeederConfigurationActivity).withString("FixedShortFeederConfiguration", GsonUtils.toPrettyFormat(job.toString())).navigation()
                    } else {
                        showToast("请至少选择一辆车次进行操作修改")
                    }

                }
            }

        })
        invalid_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mContext.let {
                    var data: ShortFeederBean? = null
                    for (item in adapter.getAllData()) {
                        if (item.isChecked) {
                            data = item
                        }
                    }
                    if (data != null) {
                        TalkSureCancelDialog (it, getScreenWidth(), "您确定要作废车次${data.inoneVehicleFlag}吗?作废后不可恢复！") {
                            mPresenter?.invalidOrder(data.inoneVehicleFlag, data.id)

                        }.show()
                    } else {
                        showToast("请至少选择一辆车次进行操作作废")
                    }

                }

            }
        })
        add_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.AddShortFeederActivity).navigation()
            }

        })
    }

    @SuppressLint("SimpleDateFormat")
    override fun initExtra() {
        super.initExtra()
        mContext.let {
            val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
            val mDate = Date(System.currentTimeMillis())
            val format = mDateFormat.format(mDate)
            mStartDateTag = "$format 00:00:00"
            mEndDateTag = "$format 23:59:59"
            mShippingOutletsTag = UserInformationUtil.getWebIdCode(it) + ","

        }
    }

    @SuppressLint("CheckResult")
    override fun initDatas() {
        super.initDatas()
        RxBus.build().toObservable(this, DepartureRecordRefreshEvent::class.java).subscribe {
            refresh()
        }
        RxBus.build().toObservableSticky(this, DepartureRecordRefreshEvent::class.java).subscribe {
            refresh()
        }
        RxBus.build().toObservable(this, DepartureRecordEvent::class.java).subscribe { msg ->
            if (msg.type == 0) {
                mShippingOutletsTag = msg.webCode
                mStartDateTag = msg.startTime
                mEndDateTag = msg.endTime
                refresh()
            }

        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        RxBus.build().removeStickyEvent(DepartureRecordRefreshEvent::class.java)
    }

    @SuppressLint("SetTextI18n")
    override fun getShortFeederS(list: List<ShortFeederBean>, toltalData: ShortFeederTotalBean) {
        appendDatas(list)
        if (getCurrentPage() == 1)
            all_info_bottom_tv.text = "合计：${list.size}票，${toltalData.weight}kg，${toltalData.volumn}m³，运费${toltalData.accSum}"
    }

    override fun invalidOrderS() {
        refresh()

    }
}