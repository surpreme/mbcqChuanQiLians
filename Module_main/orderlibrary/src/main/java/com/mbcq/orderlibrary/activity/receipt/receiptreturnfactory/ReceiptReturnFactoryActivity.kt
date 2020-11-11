package com.mbcq.orderlibrary.activity.receipt.receiptreturnfactory


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseSmartMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_receipt_return_factory.*

/**
 * @author: lzy
 * @time: 2020-11-11 09:02:45 回单返厂
 */

@Route(path = ARouterConstants.ReceiptReturnFactoryActivity)
class ReceiptReturnFactoryActivity : BaseSmartMVPActivity<ReceiptReturnFactoryContract.View, ReceiptReturnFactoryPresenter, ReceiptReturnFactoryBean>(), ReceiptReturnFactoryContract.View {
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""

    override fun getLayoutId(): Int = R.layout.activity_receipt_return_factory

    @SuppressLint("SimpleDateFormat")
    override fun initExtra() {
        super.initExtra()
        mStartDateTag = "${TimeUtils.getLastdayStr(7)} 00:00:00"
        mEndDateTag = "${TimeUtils.getCurrentYYMMDD()} 23:59:59"
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
        receipt_return_factory_toolbar.setRightButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {

                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "回单签收筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
                            override fun onResult(s1: String, s2: String) {
                                mShippingOutletsTag = s1
                                val timeList = s2.split("@")
                                if (timeList.isNotEmpty() && timeList.size == 2) {
                                    mStartDateTag = timeList[0]
                                    mEndDateTag = timeList[1]
                                }
                                refresh()
                            }

                        }).show(supportFragmentManager, "ReceiptReturnFactoryActivityFilterWithTimeDialog")
                    }

                })

            }

        })
        receipt_return_factory_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun getSmartLayoutId(): Int = R.id.receipt_return_factory_smart
    override fun getSmartEmptyId(): Int = R.id.receipt_return_factory_smart_frame
    override fun getRecyclerViewId(): Int = R.id.receipt_return_factory_recycler
    override fun setAdapter(): BaseRecyclerAdapter<ReceiptReturnFactoryBean> = ReceiptReturnFactoryAdapter(mContext).also {
        receipt_return_factory_checkbox.setOnCheckedChangeListener { _, isChecked ->
            it.checkedAll(isChecked)
        }
    }

    override fun getPageS(list: List<ReceiptReturnFactoryBean>) {
        appendDatas(list)
    }

}