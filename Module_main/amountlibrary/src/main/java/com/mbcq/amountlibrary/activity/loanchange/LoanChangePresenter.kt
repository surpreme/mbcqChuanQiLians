package com.mbcq.amountlibrary.activity.loanchange

import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-16 10:16:03 贷款变更
 */

class LoanChangePresenter : BasePresenterImpl<LoanChangeContract.View>(), LoanChangeContract.Presenter {
    /**
     * {"code":0,"msg":"","count":1,"data":[
    {
    "id": 168,
    "companyId": 2001,
    "eCompanyId": 2001,
    "orderId": "",
    "billno": "10010000057",
    "oBillno": "",
    "billDate": "2020-10-09T17:09:52",
    "billState": 6,
    "billStateStr": "派送中",
    "billType": 0,
    "billTypeStr": "机打",
    "goodsNum": "00057-3",
    "okProcess": 2,
    "okProcessStr": "送货上门",
    "isUrgent": 0,
    "isUrgentStr": "否",
    "isTalkGoods": 0,
    "isTalkGoodsStr": "否",
    "webidCode": 1001,
    "webidCodeStr": "义乌后湖",
    "ewebidCode": 1003,
    "ewebidCodeStr": "汕头",
    "destination": "汕头",
    "transneed": 2,
    "transneedStr": "马帮快线",
    "vipId": "",
    "shipperId": "",
    "shipperMb": "15999999999",
    "shipperTel": "7886",
    "shipper": "彭小林",
    "shipperCid": "",
    "shipperAddr": "1111",
    "consigneeMb": "18614079738",
    "consigneeTel": "552",
    "consignee": "李紫洋",
    "consigneeAddr": "好廉价",
    "product": "玻璃1",
    "totalQty": 3,
    "qty": 3,
    "packages": "工工式",
    "weight": 66.00,
    "volumn": 58.00,
    "weightJs": 0.00,
    "safeMoney": 0.00,
    "accDaiShou": 0.00,
    "accHKChange": 0.00,
    "hkChangeReason": "",
    "sxf": 0.00,
    "wPrice": 0.00,
    "vPrice": 0.00,
    "qtyPrice": 0.00,
    "accNow": 0.00,
    "accArrived": 0.00,
    "accBack": 0.00,
    "accMonth": 0.00,
    "accHuoKuanKou": 0.00,
    "accTrans": 2885.00,
    "accFetch": 55.00,
    "accPackage": 88.00,
    "accSend": 0.00,
    "accGb": 0.00,
    "accSafe": 0.00,
    "accRyf": 0.00,
    "accHuiKou": 0.00,
    "accSms": 0.00,
    "accZz": 0.00,
    "accZx": 0.00,
    "accCb": 0.00,
    "accSl": 0.00,
    "accAz": 0.00,
    "accFj": 0.00,
    "accWz": 0.00,
    "accJc": 0.00,
    "accSum": 3028.00,
    "accType": 2,
    "accTypeStr": "提付",
    "backQty": "签信封",
    "backState": 0,
    "isWaitNotice": 0,
    "isWaitNoticeStr": "否",
    "bankCode": "",
    "bankName": "",
    "bankMan": "",
    "bankNumber": "",
    "createMan": "义乌后湖",
    "salesMan": "",
    "opeMan": "义乌后湖",
    "remark": "",
    "fromType": 3,
    "isTransferCount": 0,
    "preVehicleNo": "",
    "takeWay": "",
    "valueAddedService": "",
    "bilThState": 0,
    "bilThStateStr": ""
    }
    ]}
     */
    override fun getWaybillDetail(billNo: String) {
        val params= HttpParams()
        params.put("Billno",billNo)
        get<String>(ApiInterface.RECORD_SELECT_ORDER_INFO_GET,params,object:CallBacks{
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(0)){
                        mView?.getWaybillDetailS(it.getJSONObject(0))

                    }else{
                        mView?.getWaybillDetailNull()
                    }

                }
            }

        })
    }
}