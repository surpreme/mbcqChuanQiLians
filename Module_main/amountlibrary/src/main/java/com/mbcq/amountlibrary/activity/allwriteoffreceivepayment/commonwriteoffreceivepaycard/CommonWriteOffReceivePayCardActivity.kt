package com.mbcq.amountlibrary.activity.allwriteoffreceivepayment.commonwriteoffreceivepaycard


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mbcq.amountlibrary.R
import com.mbcq.amountlibrary.activity.allwriteoffpayment.commonwriteoffpaycard.WriteOffPayCardPriceDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.commonlibrary.dialog.TimeDialogUtil
import kotlinx.android.synthetic.main.activity_common_write_off_receive_pay_card.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * @author: lzy
 * @time: 2021-01-14 09:02:03 应收核销凭证
 *
 */

@Route(path = ARouterConstants.CommonWriteOffReceivePayCardActivity)
class CommonWriteOffReceivePayCardActivity : BaseListMVPActivity<CommonWriteOffReceivePayCardContract.View, CommonWriteOffReceivePayCardPresenter, CommonWriteOffReceivePayCardInfoBean>(), CommonWriteOffReceivePayCardContract.View {
    @Autowired(name = "xSelectData")
    @JvmField
    var xSelectData: String = ""

    override fun getLayoutId(): Int = R.layout.activity_common_write_off_receive_pay_card


    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        common_write_off_receive_paycard_toolbar.setCenterTitleText(JSONObject(xSelectData).optString("mCommonTitleStr") + "生成付款凭证")
        voucher_date_tv.text = TimeUtils.getCurrentYYMMDD()

    }

    override fun initDatas() {
        super.initDatas()
        val dataObj = JSONObject(xSelectData)
        mPresenter?.getSerialNumber()
        mPresenter?.getWriteOffType(dataObj.optString("mCommonTitleStr") + dataObj.optString("xmtype"))
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
                obj.put("receiptTypeStr", payment_method_tv.text.toString())
                obj.put("receiptNo", mReceiptNo)
                obj.put("billno", JSONObject(xSelectData).optString("billno"))
                obj.put("account", JSONObject(xSelectData).optString("accArrived"))
                val jay = JSONArray()
                for (item in adapter.getAllData()) {
                    val itemObj = JSONObject()
                    itemObj.put("inoneVehicleFlag", item.inoneVehicleFlag)
                    itemObj.put("account", item.accArrivedAfter)
                    itemObj.put("inOrOutType", "0")
                    itemObj.put("inOrOutTypeStr", "收入")
                    itemObj.put("content", item.summary)
                    jay.put(itemObj)
                }
               /* val itemObj = JSONObject()
                itemObj.put("billno", JSONObject(xSelectData).optString("billno"))
                itemObj.put("account", JSONObject(xSelectData).optString("accArrived"))
                itemObj.put("inOrOutType", "0")
                itemObj.put("inOrOutTypeStr", "收入")
                itemObj.put("content", "收${voucher_date_tv.text}日现付款")
                jay.put(itemObj)*/
                obj.put("InOrOutReceiptDetLst", jay)
                mPresenter?.savePayCardInfo(obj, JSONObject(xSelectData).optString("mCommonTitleStr"))

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
        common_write_off_receive_paycard_toolbar.setBackButtonOnClickListener(object : SingleClick() {
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
        val obj = JSONObject(xSelectData)
        obj.optJSONArray("selectData")?.let {
            val list = Gson().fromJson<List<CommonWriteOffReceivePayCardInfoBean>>(obj.optString("selectData"), object : TypeToken<List<CommonWriteOffReceivePayCardInfoBean>>() {}.type)
            for (item in list) {
                item.accArrivedAfter = item.yue
                item.documentNo = result
                item.summary = "收${TimeUtils.getCurrentYYMMDD()}提付款"
            }
            adapter.appendData(list)
            planTotal()
        }


    }

    override fun savePayCardInfoS(result: String) {
        TalkSureDialog(mContext, getScreenWidth(), "核销成功，点击返回！") {
            onBackPressed()
        }.show()
    }
    fun planTotal() {
        object : CountDownTimer(500, 500) {
            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                if (isDestroyed) return
                val list = adapter.getAllData()
                var mTotalPrice = 0.00
                for (item in list) {
                    if (item.accArrivedAfter.isBlank())
                        continue
                    mTotalPrice += item.accArrivedAfter.toDouble()
                }
                total_info_tv.text = "合计：${list.size}票  金额$mTotalPrice"
            }

            override fun onTick(millisUntilFinished: Long) {
            }

        }.start()

    }
    override fun getRecyclerViewId(): Int = R.id.waybill_order_recycler

    override fun setAdapter(): BaseRecyclerAdapter<CommonWriteOffReceivePayCardInfoBean> = CommonWriteOffReceivePayCardAdapter(mContext).also {
       it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val bean = Gson().fromJson<CommonWriteOffReceivePayCardInfoBean>(mResult, CommonWriteOffReceivePayCardInfoBean::class.java)
                WriteOffPayCardPriceDialog(getScreenWidth(), bean.yue, bean.accArrivedAfter, bean.summary, object : OnClickInterface.OnClickInterface {
                    override fun onResult(s1: String, s2: String) {
                        val obj = JSONObject(s1)
                        bean.accArrivedAfter = obj.optString("accArrivedAfter")
                        bean.summary = obj.optString("summary")
                        adapter.notifyItemChangeds(position, bean)
                        planTotal()
                    }

                }).show(supportFragmentManager, "WriteOffPayCardPriceDialog")
            }

        }
    }

}