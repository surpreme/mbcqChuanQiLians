package com.mbcq.vehicleslibrary.activity.alldeparturerecord.shipmentinventory

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-27 09:47:00
 */

class ShipmentInventoryPresenter : BasePresenterImpl<ShipmentInventoryContract.View>(), ShipmentInventoryContract.Presenter {
    /**
     * {"code":0,"msg":"","count":14,"data":[
    {
    "id": 16,
    "companyId": 2001,
    "eCompanyId": 0,
    "orderId": "",
    "billno": "10030000126",
    "oBillno": "",
    "billDate": "2020-06-19T13:08:47",
    "billState": 1,
    "billStateStr": "已入库",
    "billType": 0,
    "billTypeStr": "机打",
    "goodsNum": "00126-1",
    "okProcess": 1,
    "okProcessStr": "自提",
    "isUrgent": 0,
    "isUrgentStr": "否",
    "isTalkGoods": 0,
    "isTalkGoodsStr": "否",
    "webidCode": 1003,
    "webidCodeStr": "汕头",
    "ewebidCode": 1001,
    "ewebidCodeStr": "义乌后湖",
    "destination": "义乌后湖1",
    "transneed": 1,
    "transneedStr": "零担",
    "vipId": "",
    "shipperId": "",
    "shipperMb": "17530957256",
    "shipperTel": "0123-1234567",
    "shipper": "王哓我",
    "shipperCid": "410482199002265912",
    "shipperAddr": "发货人地址",
    "consigneeMb": "17530957256",
    "consigneeTel": "0248-5235544",
    "consignee": "1禾",
    "consigneeAddr": "蜚厘士别三日奔奔夺",
    "product": "玻璃",
    "totalQty": 1,
    "qty": 1,
    "packages": "aaas",
    "weight": 100.00,
    "volumn": 0.50,
    "weightJs": 130.00,
    "safeMoney": 10.00,
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
    "accTrans": 500.00,
    "accFetch": 0.00,
    "accPackage": 10.00,
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
    "accSum": 510.00,
    "accType": 1,
    "accTypeStr": "现付",
    "backQty": "签回单",
    "backState": 0,
    "isWaitNotice": 0,
    "isWaitNoticeStr": "否",
    "bankCode": "",
    "bankName": "",
    "bankMan": "",
    "bankNumber": "",
    "createMan": "",
    "salesMan": "",
    "opeMan": "汕头",
    "remark": "",
    "fromType": 0
    }
    ]}
     */
    override fun getPage(page: Int, selWebidCode: String, startDate: String, endDate: String) {
        val params = HttpParams()
        params.put("Page", page)
        params.put("Limit", 15)
        params.put("StartDate", startDate)
        params.put("EndDate", endDate)
        mView?.getContext()?.let {
            params.put("SelWebidCode", selWebidCode)

        }
        get<String>(ApiInterface.SHIPMENT_INVENTORY_SELECTED_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getPageS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<ShipmentInventoryBean>>() {}.type),page,obj.optString("count"))

                }


            }

        })
    }

}