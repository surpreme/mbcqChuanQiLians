package com.mbcq.orderlibrary.activity.goodsreceiptinfo

import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-27 14:40:05
 */

class GoodsReceiptInfoPresenter : BasePresenterImpl<GoodsReceiptInfoContract.View>(), GoodsReceiptInfoContract.Presenter {
    /**
     * {"code":0,"msg":"","count":3,"data":[
    {
    "id": 140,
    "type": 26,
    "typestr": "收付款类型",
    "companyid": 2001,
    "typecode": 1,
    "tdescribe": "现金",
    "recorddate": "2019-02-23T13:53:05",
    "typedes": " 对应凭证主表(b_account)里面的billtype"
    },
    {
    "id": 141,
    "type": 26,
    "typestr": "收付款类型",
    "companyid": 2001,
    "typecode": 2,
    "tdescribe": "微信",
    "recorddate": "2019-02-23T13:53:05",
    "typedes": " 对应凭证主表(b_account)里面的billtype"
    },
    {
    "id": 142,
    "type": 26,
    "typestr": "收付款类型",
    "companyid": 2001,
    "typecode": 3,
    "tdescribe": "支付宝",
    "recorddate": "2019-02-23T13:53:05",
    "typedes": " 对应凭证主表(b_account)里面的billtype"
    }
    ]}
     */
    override fun getPaymentWay() {
        val httpParmas = HttpParams()
        httpParmas.put("type", 26)
        get<String>(ApiInterface.PAYMENT_METHOD_SELECTED_INFO_GET, httpParmas, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getPaymentWayS(obj.optString("data"))

                }

            }

        })
    }

}