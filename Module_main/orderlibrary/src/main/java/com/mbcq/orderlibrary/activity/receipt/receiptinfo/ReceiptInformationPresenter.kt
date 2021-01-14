package com.mbcq.orderlibrary.activity.receipt.receiptinfo

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.orderlibrary.activity.receipt.receiptcancel.ReceiptCancelBean
import com.mbcq.orderlibrary.activity.receipt.receiptgeneralledger.ReceiptGeneralLedgerBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-10 15:17:58 回单详情
 */

class ReceiptInformationPresenter : BasePresenterImpl<ReceiptInformationContract.View>(), ReceiptInformationContract.Presenter {
    override fun getWayBillInfo(billno: String) {
        val params = HttpParams()
        params.put("Billno", billno)
        get<String>(ApiInterface.RECORD_SELECT_ORDER_MORE_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(0)) {
                        mView?.getWaybillDetailS(it.getJSONObject(0))

                    } else {
                        mView?.getWaybillDetailNull()
                    }

                }
            }

        })
    }

    override fun getReceiptInfo(billno: String) {
        val params = HttpParams()
        params.put("billno", billno)
        get<String>(ApiInterface.RECEIPT_CANCEL_RECEIVE_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {

                    val mResultList = Gson().fromJson<List<ReceiptCancelBean>>(obj.optString("data"), object : TypeToken<List<ReceiptCancelBean>>() {}.type)
                    val mDList = mutableListOf<ReceiptInformationBean>()
                    val mDStateList = mutableListOf<Boolean>(false, false, false, false)
                    for (item in mResultList) {
                        when (item.cztype) {
                            "签收" -> {
                                mDList.add(ReceiptInformationBean("签收时间：${item.czdate}\n签 收  人：${item.czman}\n签收网点：${item.czman}", "签收", ""))
                                mDStateList[0] = true
                            }
                            "寄出" -> {
                                mDList.add(ReceiptInformationBean("寄出时间：${item.czdate}\n寄 出  人：${item.czman}\n寄出网点：xx\n接收网点：xx\n寄出快递公司：xx\n快递单号：xxx", "寄出", ""))
                                mDStateList[1] = true

                            }
                            "返厂" -> {
                                mDList.add(ReceiptInformationBean("返厂时间：${item.czdate}\n返 厂  人：${item.czman}\n返厂网点：xxx", "返厂", ""))
                                mDStateList[2] = true

                            }
                            "接收" -> {
                                mDList.add(ReceiptInformationBean("接收时间：${item.czdate}\n接 收  人：${item.czman}\n接收网点：xx\n寄出快递公司：xx\n 快递单号：xxx", "接收", ""))
                                mDStateList[3] = true

                            }
                        }
                    }

                    mView?.getReceiptInfoS(mDList,mDStateList)
                }
            }

        })
    }

}