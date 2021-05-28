package com.mbcq.orderlibrary.fragment.waybillinformation


import android.annotation.SuppressLint
import android.graphics.Rect
import android.text.Html
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.ui.mvp.BaseMVPFragment
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.fragment_waybill_information.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-20 13:13:03 运单信息 121.473856,31.171852
 *
 */

class WaybillInformationFragment : BaseMVPFragment<WaybillInformationContract.View, WaybillInformationPresenter>(), WaybillInformationContract.View {
    var WaybillDetails = ""
    var mWaybillInformationTableAdapter: WaybillInformationTableAdapter? = null
    override fun getLayoutResId(): Int = R.layout.fragment_waybill_information
    override fun setIsShowNetLoading(): Boolean {
        return false
    }
    override fun initExtra() {
        super.initExtra()
        arguments?.let {
            WaybillDetails = it.getString("WaybillDetails", "{}")
        }
    }


    @SuppressLint("SetTextI18n")
    override fun initViews(view: View) {
        waybill_information_table_recycler.layoutManager = LinearLayoutManager(mContext)
        mWaybillInformationTableAdapter = WaybillInformationTableAdapter(mContext).also {
            waybill_information_table_recycler.adapter = it
        }
        waybill_information_table_recycler.addItemDecoration(object : BaseItemDecoration(mContext) {
            override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
                rect.top = ScreenSizeUtils.dp2px(mContext, 1f)
            }

            override fun doRule(position: Int, rect: Rect) {
                rect.bottom = rect.top
            }

        })
        val obj = JSONObject(WaybillDetails)
        waybill_state_tv.text = obj.optString("billStateStr")

        basic_info_tv.text = Html.fromHtml(
                "<font color='#A0A1A4'>运&nbsp;&nbsp;&nbsp;单&nbsp;&nbsp;&nbsp;号：</font><font color='#000000'>${obj.optString("billno")}</font><br />" +
                        "<font color='#A0A1A4'>开单&nbsp;&nbsp;时间：</font><font color='#000000'>${obj.optString("billDate")}</font><br />" +
                        "<font color='#A0A1A4'>货&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号&nbsp;&nbsp;：</font><font color='#000000'>${obj.optString("goodsNum")}</font>")
        //发货网点：汕头<br />目  的 地：义乌市<br />付货方式：送货
        webinfo_left_tv.text = Html.fromHtml(
                "<font color='#A0A1A4'>发货&nbsp;&nbsp;网点：</font><font color='#000000'>${obj.optString("webidCodeStr")}</font><br />" +
                "<font color='#A0A1A4'>目&nbsp;&nbsp;&nbsp;的&nbsp;&nbsp;&nbsp;地：</font><font color='#000000'>${obj.optString("destination")}</font><br />" +
                "<font color='#A0A1A4'>付货&nbsp;&nbsp;方式：</font><font color='#000000'>${obj.optString("okProcessStr")}</font><br />" +
                "<font color='#A0A1A4'>回单&nbsp;&nbsp;要求：</font><font color='#000000'>${obj.optString("backQty")}</font>")
        //到货网点：汕头<br />运输方式：义乌市
        webinfo_right_tv.text =  Html.fromHtml(
                "<font color='#A0A1A4'>到货&nbsp;&nbsp;网点：</font><font color='#000000'>${obj.optString("ewebidCodeStr")}</font><br />" +
                        "<font color='#A0A1A4'>运输&nbsp;&nbsp;方式：</font><font color='#000000'>${obj.optString("transneedStr")}</font><br />" +
                        "<font color='#A0A1A4'>预装&nbsp;&nbsp;车号：</font><font color='#000000'>${obj.optString("preVehicleNo")}</font><br />" +
                        "<font color='#A0A1A4'>业&nbsp;&nbsp;&nbsp;务&nbsp;&nbsp;&nbsp;员：</font><font color='#000000'>${obj.optString("salesMan")}</font>")
        shipper_info_tv.text =  Html.fromHtml(
                "<font color='#A0A1A4'>客户&nbsp;&nbsp;编号：</font><font color='#000000'>${obj.optString("shipperId")}</font><br />" +
                        "<font color='#A0A1A4'>发&nbsp;&nbsp;&nbsp;货&nbsp;&nbsp;&nbsp;人：</font><font color='#000000'>${obj.optString("shipper")}</font><br />" +
                        "<font color='#A0A1A4'>手&nbsp;&nbsp;&nbsp;机&nbsp;&nbsp;&nbsp;号：</font><font color='#000000'>${obj.optString("shipperMb")}</font><br />" +
                        "<font color='#A0A1A4'>固定&nbsp;&nbsp;电话：</font><font color='#000000'>${obj.optString("shipperTel")}</font><br />" +
                        "<font color='#A0A1A4'>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</font><font color='#000000'>${obj.optString("shipperAddr")}</font><br />" +
                        "<font color='#A0A1A4'>公&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;司：</font><font color='#000000'>${obj.optString("shipperCompany")}</font><br />" +
                        "<font color='#A0A1A4'>发货人证件：</font><font color='#000000'>${obj.optString("shipperCid")}</font><br />" +
                        "<font color='#A0A1A4'>银行&nbsp;&nbsp;卡号：</font><font color='#000000'>${obj.optString("bankCode")}</font><br />" +
                        "<font color='#A0A1A4'>开&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;行：</font><font color='#000000'>${obj.optString("bankName")}</font><br />" +
                        "<font color='#A0A1A4'>开&nbsp;&nbsp;&nbsp;户&nbsp;&nbsp;&nbsp;名：</font><font color='#000000'>${obj.optString("bankMan")}</font>")
        receiver_info_tv.text =  Html.fromHtml(
                "<font color='#A0A1A4'>客户&nbsp;&nbsp;编号：</font><font color='#000000'>${obj.optString("consigneeId")}</font><br />" +
                        "<font color='#A0A1A4'>收&nbsp;&nbsp;&nbsp;货&nbsp;&nbsp;&nbsp;人：</font><font color='#000000'>${obj.optString("consignee")}</font><br />" +
                        "<font color='#A0A1A4'>手&nbsp;&nbsp;&nbsp;机&nbsp;&nbsp;&nbsp;号：</font><font color='#000000'>${obj.optString("consigneeMb")}</font><br />" +
                        "<font color='#A0A1A4'>固定&nbsp;&nbsp;电话：</font><font color='#000000'>${obj.optString("consigneeTel")}</font><br />" +
                        "<font color='#A0A1A4'>地&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;址：</font><font color='#000000'>${obj.optString("consigneeAddr")}</font>")
        /**
         *
        保  价 费：${obj.optString("accSafe")}
        送  货 费：${obj.optString("accSend")}
        返      款：${obj.optString("accHuiKou")}
        付款方式：${obj.optString("accTypeStr")}
        实发货款：您看不到我
        备      注：${obj.optString("remark")}
         */
        goods_info_tv.text = Html.fromHtml(
                "<font color='#A0A1A4'>货物&nbsp;&nbsp;名称：</font><font color='#000000'>${obj.optString("product")}</font><br />" +
                        "<font color='#A0A1A4'>包装&nbsp;&nbsp;方式：</font><font color='#000000'>${obj.optString("packages")}</font><br />" +
                        "<font color='#A0A1A4'>体&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;积：</font><font color='#000000'>${obj.optString("volumn")}m³</font>")
        /**
         *
        提  货 费：${obj.optString("accFetch")}
        中  转 费：${obj.optString("outacc")}
        合计运费：${obj.optString("accSum")}
        代收货款：${obj.optString("accDaiShou")}
         */
        goods_info_second_tv.text =Html.fromHtml(
                "<font color='#A0A1A4'>件&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;数：</font><font color='#000000'>${obj.optString("qty")}</font><br />" +
                        "<font color='#A0A1A4'>重&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;量：</font><font color='#000000'>${obj.optString("weight")}kg</font><br />" +
                        "<font color='#A0A1A4'>保价&nbsp;&nbsp;金额：</font><font color='#000000'>${obj.optString("safeMoney")}</font>")
        obj.optJSONArray("WayGoosLst")?.let {
            val mAdapterList = mutableListOf<WaybillInformationTableBean>()
            val mTopTitleBean = WaybillInformationTableBean()
            mTopTitleBean.product = "货物名称"
            mTopTitleBean.qty = "件数"
            mTopTitleBean.packages = "包装方式"
            mTopTitleBean.weight = "重量"
            mTopTitleBean.volumn = "体积"
            mTopTitleBean.isTitles = true
            mAdapterList.add(mTopTitleBean)
            for (index in 0 until it.length()) {
                val mWaybillInformationTableBean = WaybillInformationTableBean()
                mWaybillInformationTableBean.product = it.getJSONObject(index).optString("product")
                mWaybillInformationTableBean.qty = it.getJSONObject(index).optString("qty")
                mWaybillInformationTableBean.packages = it.getJSONObject(index).optString("packages")
                mWaybillInformationTableBean.weight = it.getJSONObject(index).optString("weight")
                mWaybillInformationTableBean.volumn = it.getJSONObject(index).optString("volumn")
                mAdapterList.add(mWaybillInformationTableBean)
            }
            if (mAdapterList.isNotEmpty())
                mWaybillInformationTableAdapter?.appendData(mAdapterList)

        }
        planMoreGoods()
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getCostInformation(UserInformationUtil.getWebIdCode(mContext))
        mPresenter?.getOrderBigInfo(JSONObject(WaybillDetails).optString("billno"))
    }

    @SuppressLint("SetTextI18n")
    override fun getCostInformationS(result: String) {
        val data = JSONObject(result)
        val mShowCostFilNam = data.optString("showCostFilNam").split(",")
        val mShowCostStr = data.optString("showCostStr").split(",")
        var mA = ""
        var mB = ""
        val obj = JSONObject(WaybillDetails)
        for (mIndex in mShowCostStr.indices) {
            if ((mIndex + 1) % 2 != 0) {
                mA += "<font color='#A0A1A4'>${mShowCostStr[mIndex]}:</font><font color='#000000'>${obj.optString(mShowCostFilNam[mIndex])}</font><br />"
            } else {
                mB += "<font color='#A0A1A4'>${mShowCostStr[mIndex]}:</font><font color='#000000'>${obj.optString(mShowCostFilNam[mIndex])}</font><br />"
            }

        }
        goods_info_bottom_tv.text = Html.fromHtml(
                mA +
                "<font color='#A0A1A4'>付款&nbsp;&nbsp;方式：</font><font color='#000000'>${obj.optString("accTypeStr")}</font><br />" +
                "<font color='#A0A1A4'>实发&nbsp;&nbsp;货款：</font><font color='#000000'>您看不到我</font><br />" +
                "<font color='#A0A1A4'>备&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;注：</font><font color='#000000'>${obj.optString("remark")}</font>")
        goods_info_bottom_second_tv.text =  Html.fromHtml(
                mB +
                "<font color='#A0A1A4'>合计&nbsp;&nbsp;运费：</font><font color='#000000'>${obj.optString("accSum")}</font><br />" +
                "<font color='#A0A1A4'>代收&nbsp;&nbsp;货款：</font><font color='#000000'>${obj.optString("accDaiShou")}</font>")
    }
    fun planMoreGoods() {
        if (mWaybillInformationTableAdapter?.getAllData()?.isNotEmpty()!!) {
            total_info_ll.visibility = View.VISIBLE
            val totalProduct = StringBuilder()
            var totalQty = 0
            val totalPackages = StringBuilder()
            var totalWeight = 0.00
            var totalVolume = 0.00
            for (item in mWaybillInformationTableAdapter?.getAllData()!!) {
                if(checkStrIsNum(item.weight) && checkStrIsNum(item.qty)){
                    totalProduct.append(item.product).append(",")
                    totalQty += item.qty.toInt()
                    totalPackages.append(item.packages).append(" ")
                    totalWeight += item.weight.toDouble()
                    totalVolume += item.volumn.toDouble()
                }

            }
            product_tv.text = totalProduct.toString()
            qty_tv.text = totalQty.toString()
            packages_tv.text = totalPackages.toString()
            weight_tv.text = totalWeight.toString()
            volumn_tv.text = totalVolume.toString()
        } else {
            total_info_ll.visibility = View.GONE
        }
    }

    override fun getOrderBigInfoS(result: String) {
        val obj = JSONObject(result)
        /**
         * 货款
         */
        obj.optString("hkIsOutStr").also {
            if (it.isBlank()) {
                payment_state_tv.text = "后台异常"
            } else {
                payment_state_tv.text = it

            }
        }
        /**
         * 运费
         */
        obj.optString("accTransStateStr").also {
            if (it.isBlank()) {
                freight_state_tv.text = "后台异常"
            } else {
                freight_state_tv.text = it

            }
        }
        /**
         * 提包费
         */
        obj.optString("isPayAccTbPrice").also {
            if (it.isBlank()) {
                bagfee_state_tv.text = "后台异常"
            } else {
                bagfee_state_tv.text = if (it == "1") "提包费已付" else "提包费未付"

            }
        }
    }
}