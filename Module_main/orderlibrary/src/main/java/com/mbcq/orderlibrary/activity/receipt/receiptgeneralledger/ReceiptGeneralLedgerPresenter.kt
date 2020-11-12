package com.mbcq.orderlibrary.activity.receipt.receiptgeneralledger

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.orderlibrary.activity.receipt.receiptconsignment.ReceiptConsignmentBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-12 09:23:05 回单总账
 */

class ReceiptGeneralLedgerPresenter : BasePresenterImpl<ReceiptGeneralLedgerContract.View>(), ReceiptGeneralLedgerContract.Presenter {
    /**
    /// selType:1代表开单日期，2代表回单寄出，3代表回单返回，4代表回单返厂
    /// HdDirection:1代表所有，2代表发货，3代表到货
    /// HdIsBackState:1代表所有，2代表未签收，3代表已返厂，4代表已返回，5代表已寄出，6代表已签收
    /// GoodsState：1代表所有，2代表已入库，3代表在库，4代表短驳在途，5代表到达，6代表提货签收，7代表派送中，8代表终端外转，9代表送货签收，10代表本地签收，11代表终端签收，12代表异常
     */
    override fun getPage(page: Int) {
        val params = HttpParams()
        params.put("selType", 1)
        params.put("HdDirection", 1)
        params.put("HdIsBackState", 1)
        params.put("GoodsState", 1)
        params.put("Page", page)
        params.put("Limit", 15)
        get<String>(ApiInterface.RECEIPT_GENERAL_LEDGER_RETURN_FACTORY_OVER_POST, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getPageS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<ReceiptGeneralLedgerBean>>() {}.type))
                }
            }

        })
    }

}