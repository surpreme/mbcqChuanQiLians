package com.mbcq.amountlibrary.activity.receipttuberculosissalespaycard


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.mbcq.amountlibrary.R
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean
import com.mbcq.commonlibrary.adapter.TextViewAdapter
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.commonlibrary.dialog.TimeDialogUtil
import kotlinx.android.synthetic.main.activity_receipt_tuberculosis_sales_paycard.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * @author: lzy
 * @time: 2021-01-13 10:10:00 回单核销详情
 */

@Route(path = ARouterConstants.ReceiptTuberculosisSalesPayCardActivity)
class ReceiptTuberculosisSalesPayCardActivity : BaseMVPActivity<ReceiptTuberculosisSalesPayCardContract.View, ReceiptTuberculosisSalesPayCardPresenter>(), ReceiptTuberculosisSalesPayCardContract.View {
    @Autowired(name = "xSelectData")
    @JvmField
    var xSelectData: String = ""
    
    override fun getLayoutId(): Int = R.layout.activity_receipt_tuberculosis_sales_paycard

    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        voucher_date_tv.text = TimeUtils.getCurrentYYMMDD()
        waybill_order_recycler.layoutManager = LinearLayoutManager(mContext)
        mTextViewAdapter = TextViewAdapter<BaseTextAdapterBean>(mContext, Gravity.CENTER_VERTICAL).also {
            it.setIsShowOutSide(false)
            waybill_order_recycler.adapter = it

        }
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getSerialNumber()
        mPresenter?.getWriteOffType()
        mPresenter?.getDocumentNo()

    }


    @SuppressLint("SimpleDateFormat")
    override fun onClick() {
        super.onClick()
        commit_btn.apply {
            onSingleClicks {
                val obj = JSONObject()
                obj.put("inOrOutType", "0")
                obj.put("inOrOutTypeStr", "收入")
                obj.put("accWebidCode", UserInformationUtil.getWebIdCodeStr(mContext))
                obj.put("receiptDate", voucher_date_tv.text.toString())
                obj.put("serialNo", mSerialNo)
                obj.put("oneClassItem", mOneClassItem)
                obj.put("oneClassItemStr", mOneClassItemStr)
                obj.put("twoClassItem", mTwoClassItem)
                obj.put("twoClassItemStr", mTwoClassItemStr)
                obj.put("receiptType", payMethodTag)
                obj.put("opeMan", payee_ed.text.toString())
                obj.put("receiptTypeStr", payment_method_tv.text.toString())
                obj.put("receiptNo", mReceiptNo)
                obj.put("billno", JSONObject(xSelectData).optString("billno"))
                obj.put("account", JSONObject(xSelectData).optString("accArrived"))
                val jay = JSONArray()
                val itemObj = JSONObject()
                itemObj.put("billno", JSONObject(xSelectData).optString("billno"))
                itemObj.put("account", JSONObject(xSelectData).optString("accArrived"))
                itemObj.put("inOrOutType", "0")
                itemObj.put("inOrOutTypeStr", "收入")
                itemObj.put("content", "收${voucher_date_tv.text}日现付款")
                jay.put(itemObj)
                obj.put("InOrOutReceiptDetLst", jay)
                mPresenter?.savePayCardInfo(obj)

            }
        }
        voucher_date_ll.apply {
            onSingleClicks {
                TimeDialogUtil.getChoiceTimer(mContext, OnTimeSelectListener { date, _ ->
                    val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                    val format: String = mDateFormat.format(date)
                    voucher_date_tv.text = format

                }, "选择寄出时间", isStartCurrentTime = false, isEndCurrentTime = false, isYear = true, isHM = false, isDialog = false).show(voucher_date_ll)
            }
        }
        payment_method_ll.apply {
            onSingleClicks {
                mPresenter?.getPaymentAway()
            }
        }
        receipt_tuberculosis_sales_paycard_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    var mSerialNo = ""
    var mReceiptNo = ""
    var mOneClassItem = ""
    var mOneClassItemStr = ""
    var mTwoClassItem = ""
    var mTwoClassItemStr = ""

    @SuppressLint("SetTextI18n")
    override fun getSerialNumberS(result: String) {
        mSerialNo = result
        info_left_tv.text = "记账公司：${UserInformationUtil.getWebIdCodeStr(mContext)}\n流水号：$result "
        info_right_tv.text = "记账网点：${UserInformationUtil.getWebIdCodeStr(mContext)} "
    }

    var mTextViewAdapter: TextViewAdapter<BaseTextAdapterBean>? = null

    @SuppressLint("SetTextI18n")
    override fun getWriteOffTypeS(data: JSONObject) {
        level_subjects_tv.text = "一级科目：${data.optString("oneClassItemStr")}"
        detailed_subjects_tv.text = "明细科目：${data.optString("twoClassItemStr")}"
        mOneClassItem = data.optString("oneClassItem")
        mOneClassItemStr = data.optString("oneClassItemStr")
        mTwoClassItem = data.optString("twoClassItem")
        mTwoClassItemStr = data.optString("twoClassItemStr")
    }

    var payMethodTag = 1
    override fun getPaymentAwayS(result: String) {
        FilterDialog(getScreenWidth(), result, "tdescribe", "现付核销收款方式", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val obj = JSONObject(mResult)
                payment_method_tv.text = obj.optString("tdescribe")
                payMethodTag = obj.optInt("typecode")
            }

        }).show(supportFragmentManager, "getPaymentWaySFilterDialog")
    }

    override fun getDocumentNoS(result: String) {
        mReceiptNo = result
        val list = mutableListOf<BaseTextAdapterBean>(BaseTextAdapterBean("源  单 号：${JSONObject(xSelectData).optString("billno")}       金      额：${JSONObject(xSelectData).optString("accArrived")}\n摘      要：\n凭证编号：$result", ""))
        mTextViewAdapter?.appendData(list)
    }

    override fun savePayCardInfoS(result: String) {
        TalkSureDialog(mContext, getScreenWidth(), "核销成功，点击返回！") {
            onBackPressed()
        }.show()
    }


}