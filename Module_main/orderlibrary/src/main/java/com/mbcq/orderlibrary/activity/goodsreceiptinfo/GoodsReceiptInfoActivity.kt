package com.mbcq.orderlibrary.activity.goodsreceiptinfo


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.Constant
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.commonlibrary.dialog.PaymentDialog
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.activity.goodsreceipt.GoodsReceiptBean
import kotlinx.android.synthetic.main.activity_goods_receipt_info.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-27 14:40:05
 * 货物签收 详情页
 */
@Route(path = ARouterConstants.GoodsReceiptInfoActivity)
class GoodsReceiptInfoActivity : BaseMVPActivity<GoodsReceiptInfoContract.View, GoodsReceiptInfoPresenter>(), GoodsReceiptInfoContract.View {
    @Autowired(name = "GoodsReceiptBean")
    @JvmField
    var mLastDataJson: String = ""
    var mCertificateData = ""//证件类型制造的本地json
    var mSigningSituationData = ""//签收情况
    var pickerCertificateTypeTag = 1//提货人证件类型
    var agentCertificateTypeTag = 1//代理人证件类型
    var mSigningSituationTag = 1//签收情况
    var payMethodTag = 1

    override fun getLayoutId(): Int = R.layout.activity_goods_receipt_info
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    fun getCanReceiptGoods(): Boolean {
        if (picker_name_ed.text.toString().isBlank()) {
            showToast("请输入提货人")
            return false
        }
        if (picker_card_number_ed.text.toString().isBlank()) {
            showToast("请输入提货人证件号")
            return false
        }
        if (agent_name_ed.text.toString().isBlank()) {
            showToast("请输入代理人")
            return false
        }
        if (agent_card_number_ed.text.toString().isBlank()) {
            showToast("请输入代理人证件号")
            return false
        }
        return true
    }

    fun receiptGoods() {
        val mGoodsReceiptBean = Gson().fromJson<GoodsReceiptBean>(mLastDataJson, GoodsReceiptBean::class.java)

        val obj = JSONObject()
        val CommonStr = mGoodsReceiptBean.billno//运单号
        obj.put("CommonStr", CommonStr)
        val Billno = mGoodsReceiptBean.billno//运单号
        obj.put("Billno", Billno)

        val FetComId = 0//签收公司
        obj.put("FetComId", FetComId)

        val FetchWebidCode = UserInformationUtil.getWebIdCode(mContext)//签收网点编码
        obj.put("FetchWebidCode", FetchWebidCode)

        val FetchWebidCodeStr = UserInformationUtil.getWebIdCodeStr(mContext)//签收网点
        obj.put("FetchWebidCodeStr", FetchWebidCodeStr)

        val FetchDate = delivery_date_tv.text.toString()//签收日期
        obj.put("FetchDate", FetchDate)

        val FetchMan = picker_name_ed.text.toString()///签收人
        obj.put("FetchMan", FetchMan)

        val FetManIdCarType = pickerCertificateTypeTag//签收人证件类型编码
        obj.put("FetManIdCarType", FetManIdCarType)

        val FetManIdCarTypeStr = picker_certificate_type_tv.text.toString()//签收人证件
        obj.put("FetManIdCarTypeStr", FetManIdCarTypeStr)

        val FetchIdCard = picker_card_number_ed.text.toString()//签收人证件号
        obj.put("FetchIdCard", FetchIdCard)

        val FetchAgent = agent_name_ed.text.toString()//代理人
        obj.put("FetchAgent", FetchAgent)

        val FetAgeIdCarType = agentCertificateTypeTag//代理人证件类型编码
        obj.put("FetAgeIdCarType", FetAgeIdCarType)

        val FetAgeIdCarTypeStr = agent_certificate_type_tv.text.toString()//代理人证件类型
        obj.put("FetAgeIdCarTypeStr", FetAgeIdCarTypeStr)

        val FetAgeIdCard = agent_card_number_ed.text.toString()//代理人证件号
        obj.put("FetAgeIdCard", FetAgeIdCard)
        /**
         * 1代理 2提货 3送货 4外转
         */
        val FetchType = 2//签收类型编码
        obj.put("FetchType", FetchType)

        val FetchTypeStr = "提货"//签收类型
        obj.put("FetchTypeStr", FetchTypeStr)

        val PayType = payMethodTag//支付方式编码
        obj.put("PayType", PayType)

        val PayTypeStr = payment_method_tv.text.toString()//支付方式
        obj.put("PayTypeStr", PayTypeStr)

        val FetchCon = mSigningSituationTag//签收情况编码
        obj.put("FetchCon", FetchCon)

        val FetchConStr = signing_situation_ed.text.toString()//签收情况
        obj.put("FetchConStr", FetchConStr)

        val FetchRemark = ""//签收备注
        obj.put("FetchRemark", FetchRemark)

        val RecordDate = TimeUtils.getCurrTime2()//记录日期
        obj.put("RecordDate", RecordDate)

        val FromType = Constant.ANDROID////签收位置编码
        obj.put("FromType", FromType)

        val FromTypeStr = Constant.ANDROID_STR////签收位置
        obj.put("FromTypeStr", FromTypeStr)
        mPresenter?.receiptGoods(obj)


    }

    /**
     * TODO
     * {"accArrived":0.0,"accAz":0.0,"accBack":0.0,"accCb":0.0,"accDaiShou":0.0,"accFetch":0.0,"accFj":0.0,"accGb":0.0,"accHKChange":0.0,"accHuiKou":0.0,"accHuoKuanKou":0.0,"accJc":0.0,"accMonth":0.0,"accNow":0.0,"accPackage":0.0,"accRyf":0.0,"accSafe":0.0,"accSend":0.0,"accSl":0.0,"accSms":0.0,"accSum":567.0,"accTrans":567.0,"accType":1,"accTypeStr":"现付","accWz":0.0,"accZx":0.0,"accZz":0.0,"backQty":"签回单","backState":0,"bankCode":"","bankMan":"","bankName":"","bankNumber":"","billDate":"2020-06-28T17:12:12","billState":4,"billStateStr":"到达","billType":0,"billTypeStr":"机打","billno":"10010000046","companyId":2001,"consignee":"1禾","consigneeAddr":"蜚厘士别三日奔奔夺","consigneeMb":"17530957256","consigneeTel":"0248-5235544","createMan":"","destination":"汕头","eCompanyId":0,"ewebidCode":1003,"ewebidCodeStr":"汕头","fromType":0,"goodsNum":"00046-12","hkChangeReason":"","id":81,"isTalkGoods":0,"isTalkGoodsStr":"否","isUrgent":0,"isUrgentStr":"否","isWaitNotice":0,"isWaitNoticeStr":"否","oBillno":"","okProcess":1,"okProcessStr":"自提","opeMan":"义乌后湖","orderId":"","packages":"aaas","product":"玻璃","qty":12,"qtyPrice":0.0,"remark":"","safeMoney":0.0,"salesMan":"","shipper":"王哓我","shipperAddr":"发货人地址","shipperCid":"410482199002265912","shipperId":"","shipperMb":"17530957256","shipperTel":"0123-1234567","sxf":0.0,"totalQty":12,"transneed":1,"transneedStr":"零担","vPrice":0.0,"vipId":"","volumn":0.0,"wPrice":0.0,"webidCode":1001,"webidCodeStr":"义乌后湖","weight":123.0,"weightJs":123.0}
     */
    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        val mGoodsReceiptBean = Gson().fromJson<GoodsReceiptBean>(mLastDataJson, GoodsReceiptBean::class.java)
        waybill_number_tv.text = mGoodsReceiptBean.billno
        waybill_time_tv.text = mGoodsReceiptBean.billDate
        shipper_outlets_tv.text = mGoodsReceiptBean.webidCodeStr
        receiver_outlets_tv.text = mGoodsReceiptBean.ewebidCodeStr
        goods_info_tv.text = "${mGoodsReceiptBean.product}  ${mGoodsReceiptBean.totalQty}  ${mGoodsReceiptBean.packages}"
        receiver_tv.text = "收货人 :${mGoodsReceiptBean.consignee}      手机号：${mGoodsReceiptBean.consigneeMb}"

        withdraw_tv.text = mGoodsReceiptBean.accArrived.toString()
        reason_difference_tv.text = mGoodsReceiptBean.hkChangeReason.toString()
        original_order_payment.text = mGoodsReceiptBean.accDaiShou.toString()//
        change_payment_tv.text = mGoodsReceiptBean.accHKChange.toString()
        actual_payment_tv.text = mGoodsReceiptBean.accDaiShou.toString()//
        underpayment_tv.text = mGoodsReceiptBean.accDaiShou.toString()
        increase_tv.text = "xxx"
        total_price_tv.text = mGoodsReceiptBean.accSum.toString() + "元"




        delivery_date_tv.text = TimeUtils.getCurrTime2()
    }

    override fun initDatas() {
        super.initDatas()
        val jsonArray = JsonArray()
        for (index in 0 until 4) {
            val obj = JsonObject()
            obj.addProperty("tag", index)
            when (index) {
                0 -> {
                    obj.addProperty("title", "身份证")
                }
                1 -> {
                    obj.addProperty("title", "港澳通行证")
                }
                2 -> {
                    obj.addProperty("title", "军官证")
                }
                3 -> {
                    obj.addProperty("title", "其他证件")
                }

            }
            jsonArray.add(obj)

        }
        mCertificateData = Gson().toJson(jsonArray)


        val mSigningSituationJsonArray = JsonArray()
        val mSigningSituationObj = JsonObject()
        mSigningSituationObj.addProperty("tag", 1)
        mSigningSituationObj.addProperty("title", "正常")
        mSigningSituationJsonArray.add(mSigningSituationObj)
        mSigningSituationData = Gson().toJson(mSigningSituationJsonArray)

    }

    override fun onClick() {
        super.onClick()
        confirm_receipt_btn.setOnClickListener(object : SingleClick(2000) {
            override fun onSingleClick(v: View?) {
                if (getCanReceiptGoods()) {
                    PaymentDialog(object : PaymentDialog.OnSelectPaymentMethodInterface {
                        override fun onResult(v: View, result: Int) {
                            if (result == 1) {
                                ARouter.getInstance().build(ARouterConstants.PayBarActivity).navigation()
                            } else if (result == 3) {
                                receiptGoods()
                            }
                        }

                    }).show(supportFragmentManager, "confirm_receiptGoodsReceiptInfoActivityPaymentDialog")
                }

            }

        })

        payment_method_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getPaymentWay()
            }

        })
        picker_certificate_type_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                FilterDialog(getScreenWidth(), mCertificateData, "title", "提货人证件类型", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                    override fun onItemClick(v: View, position: Int, mResult: String) {
                        val obj = JSONObject(mResult)
                        picker_certificate_type_tv.text = obj.optString("title")
                        pickerCertificateTypeTag = obj.optInt("tag") + 1
                    }

                }).show(supportFragmentManager, "picker_certificate_type_tvSFilterDialog")
            }

        })
        signing_situation_down_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                FilterDialog(getScreenWidth(), mSigningSituationData, "title", "签收情况类型", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                    override fun onItemClick(v: View, position: Int, mResult: String) {
                        val obj = JSONObject(mResult)
                        signing_situation_ed.setText(obj.optString("title"))
                        mSigningSituationTag = obj.optInt("tag")
                    }

                }).show(supportFragmentManager, "signing_situation_down_ivSFilterDialog")
            }

        })
        agent_certificate_type_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                FilterDialog(getScreenWidth(), mCertificateData, "title", "代理人证件类型", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                    override fun onItemClick(v: View, position: Int, mResult: String) {
                        val obj = JSONObject(mResult)
                        agent_certificate_type_tv.text = obj.optString("title")
                        agentCertificateTypeTag = obj.optInt("tag") + 1
                    }

                }).show(supportFragmentManager, "agent_certificate_type_llSFilterDialog")
            }

        })
        goods_receipt_info_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun getPaymentWayS(result: String) {
        FilterDialog(getScreenWidth(), result, "tdescribe", "提货收款方式", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val obj = JSONObject(mResult)
                payment_method_tv.text = obj.optString("tdescribe")
                payMethodTag = obj.optInt("typecode")
            }

        }).show(supportFragmentManager, "getPaymentWaySFilterDialog")
    }

    override fun receiptGoodsS() {
        TalkSureDialog(mContext, getScreenWidth(), "货物签收成功，点击返回查看详情！") {
            onBackPressed()
        }.show()
    }
}