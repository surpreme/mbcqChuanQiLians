package com.mbcq.orderlibrary.fragment.waybillinformation


import android.annotation.SuppressLint
import android.graphics.Rect
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
        obj.optString("hkIsOutStr").also {
            if (it.isBlank()) {
                payment_state_tv.text = "订单异常"
            } else {
                payment_state_tv.text = obj.optString("hkIsOutStr")

            }
        }
        basic_info_tv.text = "运  单 号：${obj.optString("billno")}\n开单时间：${obj.optString("billDate")}\n货      号：${obj.optString("goodsNum")}"
        //发货网点：汕头\n目  的 地：义乌市\n付货方式：送货
        webinfo_left_tv.text = "发货网点：${obj.optString("webidCodeStr")}\n目  的 地：${obj.optString("destination")}\n付货方式：${obj.optString("okProcessStr")}"
        //到货网点：汕头\n运输方式：义乌市
        webinfo_right_tv.text = "到货网点：${obj.optString("ewebidCodeStr")}\n运输方式：${obj.optString("transneedStr")}"
        shipper_info_tv.text = "客户编号：${obj.optString("shipperId")}\n发  货 人：${obj.optString("shipper")}\n手  机 号：${obj.optString("shipperMb")}\n固定电话：${obj.optString("shipperTel")}\n地      址：${obj.optString("shipperAddr")}\n公      司：${obj.optString("shipperCompany")}\n发货人证件：${obj.optString("shipperCid")}"
        receiver_info_tv.text = "客户编号：${obj.optString("consigneeId")}\n收  货 人：${obj.optString("consignee")}\n手  机 号：${obj.optString("consigneeMb")}\n固定电话：${obj.optString("consigneeTel")}\n地      址：${obj.optString("consigneeAddr")}"
        /**
         *
        保  价 费：${obj.optString("accSafe")}
        送  货 费：${obj.optString("accSend")}
        返      款：${obj.optString("accHuiKou")}
        付款方式：${obj.optString("accTypeStr")}
        实发货款：您看不到我
        备      注：${obj.optString("remark")}
         */
        goods_info_tv.text = "货物名称：${obj.optString("product")}\n包装方式：${obj.optString("packages")}\n体      积：${obj.optString("volumn")}m³"
        /**
         *
        提  货 费：${obj.optString("accFetch")}
        中  转 费：${obj.optString("outacc")}
        合计运费：${obj.optString("accSum")}
        代收货款：${obj.optString("accDaiShou")}
         */
        goods_info_second_tv.text = "件      数：${obj.optString("qty")}\n重      量：${obj.optString("weight")}kg\n保价金额：${obj.optString("safeMoney")}"
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
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getCostInformation(UserInformationUtil.getWebIdCode(mContext))
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
                mA += "${mShowCostStr[mIndex]}:${obj.optString(mShowCostFilNam[mIndex])}\n"
            } else {
                mB += "${mShowCostStr[mIndex]}:${obj.optString(mShowCostFilNam[mIndex])}\n"
            }

        }
        goods_info_bottom_tv.text = mA + "付款方式：${obj.optString("accTypeStr")}\n" + "实发货款：您看不到我\n" + "备      注：${obj.optString("remark")}"
        goods_info_bottom_second_tv.text = mB + "合计运费：${obj.optString("accSum")}\n" + "代收货款：${obj.optString("accDaiShou")}"
    }
}