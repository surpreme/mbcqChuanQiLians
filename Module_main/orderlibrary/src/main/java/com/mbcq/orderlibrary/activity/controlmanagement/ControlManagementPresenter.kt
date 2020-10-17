package com.mbcq.orderlibrary.activity.controlmanagement

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.orderlibrary.activity.waybillrecord.WaybillRecordBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-16 13:10:06 控货管理
 */

class ControlManagementPresenter : BasePresenterImpl<ControlManagementContract.View>(), ControlManagementContract.Presenter {
    /**
     * {
    "code": 0,
    "msg": "",
    "count": 64,
    "data": [
    {
    "id": 125,
    "companyId": 2001,
    "eCompanyId": 2001,
    "orderId": "",
    "billno": "10030001897",
    "oBillno": "",
    "billDate": "2020-09-09T18:17:59",
    "billState": 1,
    "billStateStr": "已入库",
    "billType": 0,
    "billTypeStr": "机打",
    "goodsNum": "01897-1",
    "okProcess": 0,
    "okProcessStr": "客户自提",
    "isUrgent": 0,
    "isUrgentStr": "否",
    "isTalkGoods": 0,
    "isTalkGoodsStr": "否",
    "webidCode": 1003,
    "webidCodeStr": "汕头",
    "ewebidCode": 1001,
    "ewebidCodeStr": "义乌后湖",
    "destination": "义乌后湖",
    "transneed": 1,
    "transneedStr": "零担",
    "vipId": "17530957256",
    "shipperId": "",
    "shipperMb": "15999999999",
    "shipperTel": "5555",
    "shipper": "彭小林",
    "shipperCid": "",
    "shipperAddr": "1111",
    "consigneeMb": "18614079730",
    "consigneeTel": "",
    "consignee": "李紫洋",
    "consigneeAddr": "还是",
    "product": "玻璃1",
    "totalQty": 0,
    "qty": 1,
    "packages": "工工式",
    "weight": 1,
    "volumn": 1,
    "weightJs": 0,
    "safeMoney": 0,
    "accDaiShou": 0,
    "accHKChange": 0,
    "hkChangeReason": "",
    "sxf": 0,
    "wPrice": 0,
    "vPrice": 0,
    "qtyPrice": 0,
    "accNow": 0,
    "accArrived": 0,
    "accBack": 0,
    "accMonth": 0,
    "accHuoKuanKou": 0,
    "accTrans": 100,
    "accFetch": 1,
    "accPackage": 40,
    "accSend": 1,
    "accGb": 1,
    "accSafe": 1,
    "accRyf": 1,
    "accHuiKou": 2,
    "accSms": 0,
    "accZz": 0,
    "accZx": 0,
    "accCb": 0,
    "accSl": 0,
    "accAz": 0,
    "accFj": 0,
    "accWz": 0,
    "accJc": 0,
    "accSum": 200,
    "accType": 1,
    "accTypeStr": "现付",
    "backQty": "签回单",
    "backState": 0,
    "isWaitNotice": 1,
    "isWaitNoticeStr": "是",
    "bankCode": "301290000007",
    "bankName": "交通银行",
    "bankMan": "wxl",
    "bankNumber": "",
    "createMan": "lzy",
    "salesMan": "",
    "opeMan": "lzy",
    "remark": "12588",
    "fromType": 3,
    "isTransferCount": 0,
    "preVehicleNo": "",
    "takeWay": "",
    "valueAddedService": "",
    "bilThState": 0,
    "bilThStateStr": ""
    }
    ]
    }
     */

    override fun getPageData(page: Int, selWebidCode: String, startDate: String, endDate: String) {
        val mHttpParams = HttpParams()
        mHttpParams.put("page", page)
        mHttpParams.put("limit", 15)
        /* mView?.getContext()?.let {
             mHttpParams.put("webidCode", UserInformationUtil.getWebIdCode(it))

         }*/
        mHttpParams.put("SelWebidCode", selWebidCode)
        mHttpParams.put("startDate", startDate)
        mHttpParams.put("endDate", endDate)

        get<String>(ApiInterface.WAYBILL_RECORD_SELECT_ALLINFO_GET, mHttpParams, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val listD = Gson().fromJson<List<ControlManagementBean>>(obj.optString("data"), object : TypeToken<List<ControlManagementBean>>() {}.type)
                mView?.getPageDataS(listD,obj.optString("count"))
            }

        })
    }

}