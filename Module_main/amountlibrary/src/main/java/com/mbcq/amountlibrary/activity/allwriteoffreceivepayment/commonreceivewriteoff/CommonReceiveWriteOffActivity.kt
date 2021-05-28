package com.mbcq.amountlibrary.activity.allwriteoffreceivepayment.commonreceivewriteoff


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.amountlibrary.R
import com.mbcq.baselibrary.dialog.popup.XDialog
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
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.BottomOptionsDialog
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import kotlinx.android.synthetic.main.activity_common_receive_write_off.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-01-14 09:21:07 应收核销记录
 */

@Route(path = ARouterConstants.CommonReceiveWriteOffActivity)
class CommonReceiveWriteOffActivity : BaseSmartMVPActivity<CommonReceiveWriteOffContract.View, CommonReceiveWriteOffPresenter,CommonReceiveWriteOffBean>(), CommonReceiveWriteOffContract.View {
    @Autowired(name = "xTitle")
    @JvmField
    var xTitle: String = ""
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""
    var mSelectedType = 0
    
    override fun getLayoutId(): Int = R.layout.activity_common_receive_write_off


    @SuppressLint("SimpleDateFormat")
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
        mStartDateTag = FilterTimeUtils.getStartTime(7)
        mEndDateTag = FilterTimeUtils.getEndTime()
        mShippingOutletsTag = UserInformationUtil.getWebIdCode(mContext)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        common_receive_write_off_receive_toolbar.setCenterTitleText(xTitle)
    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getPaymented(mCurrentPage, mShippingOutletsTag, mStartDateTag, mEndDateTag, mSelectedType, xTitle)

    }

    override fun onClick() {
        super.onClick()
        type_tv.apply {
            onSingleClicks {
                val list = mutableListOf<BaseTextAdapterBean>()
                for (index in 0..2) {
                    val mBaseTextAdapterBean = BaseTextAdapterBean()
                    mBaseTextAdapterBean.title = if (index == 0) "全部" else if (index == 1) "已付款" else "未付款"
                    mBaseTextAdapterBean.tag = index.toString()
                    list.add(mBaseTextAdapterBean)
                }
                XDialog.Builder(mContext)
                        .setContentView(R.layout.dialog_bottom_options)
//                        .setWidth(type_tv.width)
                        .setIsDarkWindow(false)
                        .asCustom(BottomOptionsDialog(mContext, list).also {
                            it.mOnRecyclerClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                                override fun onItemClick(v: View, position: Int, mResult: String) {
                                    type_tv.text = if (mResult == "0") "全部" else if (mResult == "1") "已付款" else "未付款"
                                    mSelectedType = mResult.toInt()
                                    refresh()
                                }

                            }
                        }).showUp(type_tv)
            }
        }
        generate_payment_voucher_btn.apply {
            onSingleClicks {
                var isCheck = false
                for (item in adapter.getAllData()) {
                    if (item.isChecked) {
                        isCheck = true
                        break
                    }
                }
                if (!isCheck) {
                    showToast("请至少选择一个运单进行操作")
                    return@onSingleClicks
                }
                val selectDataObj = JSONObject()
                val selectDataJry = JSONArray()
//                var selectData = ""
                for ((_, item) in adapter.getAllData().withIndex()) {
                    if (item.isChecked) {
//                        item.setmCommonTitleStr(xTitle)
                        val selectData = Gson().toJson(item)
                        selectDataJry.put(JSONObject(selectData))
//                        selectData = Gson().toJson(item)
                    }
                }
                selectDataObj.put("selectData", selectDataJry)
                selectDataObj.put("mCommonTitleStr", xTitle)
                ARouter.getInstance().build(ARouterConstants.CommonWriteOffReceivePayCardActivity).withString("xSelectData", GsonUtils.toPrettyFormat(selectDataObj)).navigation()
            }
        }
        common_receive_write_off_receive_toolbar.setRightButtonOnClickListener(
                object : SingleClick() {
                    override fun onSingleClick(v: View?) {
                        WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                            override fun isNull() {

                            }
                            override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                                FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "提付核销记录筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {

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

                                }).show(supportFragmentManager, "PaymentingWriteoffFilterWithTimeDialog")
                            }

                        })
                    }

                })
        common_receive_write_off_receive_toolbar.setBackButtonOnClickListener(
                object : SingleClick() {
                    override fun onSingleClick(v: View?) {
                        onBackPressed()
                    }

                })
    }

    override fun getSmartLayoutId(): Int = R.id.common_receive_write_off_smart
    override fun getSmartEmptyId(): Int = R.id.common_receive_write_off_smart_frame
    override fun getRecyclerViewId(): Int = R.id.common_receive_write_off_recycler

    override fun setAdapter(): BaseRecyclerAdapter<CommonReceiveWriteOffBean> = CommonReceiveWriteOffAdapter(mContext).also {
        it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val obj = JSONObject(mResult)
                obj.put("mCommonTitleStr", xTitle)
                ARouter.getInstance().build(ARouterConstants.CommonReceiveWriteOffInfoActivity).withString("WriteoffInfo", GsonUtils.toPrettyFormat(obj)).navigation()
            }

        }
    }

    override fun getPaymentedS(list: List<CommonReceiveWriteOffBean>) {
        appendDatas(list)
    }
}