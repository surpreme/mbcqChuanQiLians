package com.mbcq.amountlibrary.activity.receipttuberculosissalesinfo


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.amountlibrary.R
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import kotlinx.android.synthetic.main.activity_receipt_tuberculosis_sales_info.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2018.08.25 回单核销详情页
 */

@Route(path = ARouterConstants.ReceiptTuberculosisSalesInfoActivity)
class ReceiptTuberculosisSalesInfoActivity : BaseMVPActivity<ReceiptTuberculosisSalesInfoContract.View, ReceiptTuberculosisSalesInfoPresenter>(), ReceiptTuberculosisSalesInfoContract.View {
    @Autowired(name = "WriteoffInfo")
    @JvmField
    var mLastData: String = ""
    
    override fun getLayoutId(): Int = R.layout.activity_receipt_tuberculosis_sales_info
   
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        val mShowObj = JSONObject(mLastData)
        //TODO
        write_off_info_left_tv.text = "核销日期：${mShowObj.optString("receiptDate")}\n核 销  人：${mShowObj.optString("opeMan")}\n收 款  人：${mShowObj.optString("opeMan")}"
        write_off_info_right_tv.text = "核销状态：${mShowObj.optString("hxtype")}\n核销网点：${mShowObj.optString("accWebidCodeStr")}\n收款方式：${mShowObj.optString("receiptTypeStr")}"
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getWayBillInfo(JSONObject(mLastData).optString("billno"))
    }

    override fun onClick() {
        super.onClick()
        back_btn.apply {
            onSingleClicks {
                onBackPressed()
            }
        }
        receipt_tuberculosis_sales_info_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    @SuppressLint("SetTextI18n")
    override fun getWayBillInfoS(data: JSONObject) {
        waybill_info_left_tv.text = "运 单  号：${data.optString("billno")}\n开单日期:${data.optString("billDate")}\n发货网点：${data.optString("webidCodeStr")}\n发 货  人：${data.optString("shipper")}\n件      数：${data.optString("totalQty")}\n体      积：${data.optString("volumn")}m³\n业 务  员：${data.optString("salesMan")}\n现      付：${data.optString("accSum")} \n少收/赔款：xxx\n异地原因：xxx\n备      注：${data.optString("remark")}"
        waybill_info_right_tv.text="状      态：${data.optString("billStateStr")}\n货物名称：${data.optString("product")}\n到货网点：${data.optString("ewebidCodeStr")}\n收 货  人：${data.optString("consignee")}\n重      量：${data.optString("weight")}kg\n包     装：${data.optString("packages")}\n制 单 人：${data.optString("createMan")}\n增 加  款：xxx\n异地其他费：xxx"
    }

    override fun getWayBillInfoNull() {
        TalkSureDialog(mContext, getScreenWidth(), "运单信息异常或不存在，点击返回！") {
            onBackPressed()
        }.show()
    }
}