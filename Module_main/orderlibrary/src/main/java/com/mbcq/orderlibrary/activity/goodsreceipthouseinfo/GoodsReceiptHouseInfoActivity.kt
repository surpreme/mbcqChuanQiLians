package com.mbcq.orderlibrary.activity.goodsreceipthouseinfo


import android.annotation.SuppressLint
import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_goods_receipt_house_info.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-01-08 16:35:12 货物签收详情
 */

@Route(path = ARouterConstants.GoodsReceiptHouseInfoActivity)
class GoodsReceiptHouseInfoActivity : BaseMVPActivity<GoodsReceiptHouseInfoContract.View, GoodsReceiptHouseInfoPresenter>(), GoodsReceiptHouseInfoContract.View {
    @Autowired(name = "GoodsReceiptHouseInfo")
    @JvmField
    var mLastData: String = ""


    override fun getLayoutId(): Int = R.layout.activity_goods_receipt_house_info

    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun initDatas() {
        super.initDatas()
        val obj = JSONObject(mLastData)
        mPresenter?.getWaybillDetail(obj.optString("billno"))

        delivery_date_tv.text = obj.optString("fetchDate")//签收日期
        picker_name_tv.text = obj.optString("fetchMan")//签收人
        picker_certificate_type_tv.text = obj.optString("fetManIdCarTypeStr")//签收人证件
        picker_card_number_tv.text = obj.optString("fetchIdCard")//签收人证件号
        agent_name_tv.text = obj.optString("fetchAgent")//代理人
        agent_certificate_type_tv.text = obj.optString("fetAgeIdCarTypeStr")//代理人证件类型
        agent_card_number_tv.text = obj.optString("fetAgeIdCard")//代理人证件号
        payment_method_tv.text = obj.optString("payTypeStr")//支付方式
        signing_situation_tv.text = obj.optString("fetchConStr")//签收情况

    }

    @SuppressLint("SetTextI18n")
    override fun getWaybillDetailS(data: JSONObject) {
        waybill_number_tv.text = data.optString("billno")
        waybill_time_tv.text = data.optString("billDate")
        shipper_outlets_tv.text = data.optString("webidCodeStr")
        receiver_outlets_tv.text = data.optString("ewebidCodeStr")
        goods_info_tv.text = "${data.optString("product")}  ${data.optString("totalQty")}  ${data.optString("packages")}"
        receiver_tv.text = "收货人 :${data.optString("consignee")}      手机号：${data.optString("consigneeMb")}"

        withdraw_tv.text = data.optString("accArrived").toString()
        reason_difference_tv.text = data.optString("hkChangeReason").toString()
        original_order_payment.text = data.optString("accDaiShou").toString()//
        change_payment_tv.text = data.optString("accHKChange").toString()
        actual_payment_tv.text = data.optString("accDaiShou").toString()//
        underpayment_tv.text = data.optString("accDaiShou").toString()
        increase_tv.text = "xxx"
        total_price_tv.text = data.optString("accSum").toString() + "元"
    }

    override fun getWaybillDetailNull() {
        TalkSureDialog(mContext, getScreenWidth(), "运单信息不存在或异常，请核实后重试").show()

    }
}