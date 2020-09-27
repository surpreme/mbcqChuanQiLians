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
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.activity.acceptbilling.AcceptReceiptRequirementBean
import com.mbcq.orderlibrary.activity.goodsreceipt.GoodsReceiptBean
import kotlinx.android.synthetic.main.activity_goods_receipt_info.*
import org.json.JSONArray
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
    var mCertificateData = ""

    override fun getLayoutId(): Int = R.layout.activity_goods_receipt_info
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
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
    }

    override fun onClick() {
        super.onClick()
        payment_method_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getPaymentWay()
            }

        })
        picker_certificate_type_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                FilterDialog(getScreenWidth(), mCertificateData, "title", "提货人证件类型", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                    override fun onItemClick(v: View, position: Int, mResult: String) {

                        picker_certificate_type_tv.text = JSONObject(mResult).optString("title")
                    }

                }).show(supportFragmentManager, "picker_certificate_type_tvSFilterDialog")
            }

        })
        agent_certificate_type_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                FilterDialog(getScreenWidth(), mCertificateData, "title", "代理人证件类型", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                    override fun onItemClick(v: View, position: Int, mResult: String) {

                        agent_certificate_type_tv.text = JSONObject(mResult).optString("title")
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

                payment_method_tv.text = JSONObject(mResult).optString("tdescribe")
            }

        }).show(supportFragmentManager, "getPaymentWaySSFilterDialog")
    }
}