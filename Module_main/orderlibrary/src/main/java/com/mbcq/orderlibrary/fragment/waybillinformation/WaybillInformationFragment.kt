package com.mbcq.orderlibrary.fragment.waybillinformation


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPFragment
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.fragment_waybill_information.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-20 13:13:03 运单信息 121.473856,31.171852
 */

class WaybillInformationFragment : BaseMVPFragment<WaybillInformationContract.View, WaybillInformationPresenter>(), WaybillInformationContract.View {
    var WaybillDetails=""
    override fun getLayoutResId(): Int= R.layout.fragment_waybill_information
    override fun initExtra() {
        super.initExtra()
        arguments?.let {
            WaybillDetails=it.getString("WaybillDetails","{}")
        }
    }


    @SuppressLint("SetTextI18n")
    override fun initViews(view: View) {
        val obj=JSONObject(WaybillDetails)
        waybill_state_tv.text= obj.optString("billStateStr")
        basic_info_tv.text="运  单 号：${obj.optString("billno")}\n开单时间：${obj.optString("billDate")}\n货      号：${obj.optString("goodsNum")}\n发货网点：${obj.optString("webidCodeStr")}                        到货网点：${obj.optString("ewebidCodeStr")}\n目  的 地：${obj.optString("destination")}                     运输方式：${obj.optString("transneedStr")}\n付货方式：${obj.optString("okProcessStr")}"
        shipper_info_tv.text="发  货 人：${obj.optString("shipper")}\n手  机 号：${obj.optString("shipperMb")}\n地      址：${obj.optString("shipperAddr")}\n发货人证件：${obj.optString("shipperCid")}"
        receiver_info_tv.text="收  货 人：${obj.optString("consignee")}\n手  机 号：${obj.optString("consigneeMb")}\n地      址：${obj.optString("consigneeAddr")}"
        goods_info_tv.text="货物名称：${obj.optString("product")}\n包装方式：${obj.optString("packages")}\n体      积：${obj.optString("volumn")}m³\n保  价 费：${obj.optString("accSafe")}\n送  货 费：${obj.optString("accSend")}\n返      款：${obj.optString("accHuiKou")}\n付款方式：${obj.optString("accTypeStr")}\n实发货款：您看不到我\n备      注：${obj.optString("remark")}"
        goods_info_second_tv.text="件      数：${obj.optString("qty")}\n重      量：${obj.optString("weight")}kg\n保价金额：${obj.optString("safeMoney")}\n提  货 费：${obj.optString("accFetch")}\n中  转 费：${obj.optString("outacc")}\n合计运费：${obj.optString("accSum")}\n代收货款：${obj.optString("accDaiShou")}"
    }

}