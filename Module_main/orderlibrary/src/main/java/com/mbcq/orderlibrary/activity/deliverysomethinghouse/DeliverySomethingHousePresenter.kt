package com.mbcq.orderlibrary.activity.deliverysomethinghouse


import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time:
 */

class DeliverySomethingHousePresenter : BasePresenterImpl<DeliverySomethingHouseContract.View>(), DeliverySomethingHouseContract.Presenter {
    /**
     * {"code":0,"msg":"","count":1,"data":[
    {
    "id": 165,
    "companyId": 2001,
    "eCompanyId": 2001,
    "orderId": "",
    "billno": "10010000053",
    "oBillno": "",
    "billDate": "2020-09-29T15:57:41",
    "billState": 4,
    "billStateStr": "到达",
    "billType": 0,
    "billTypeStr": "机打",
    "goodsNum": "00053-3",
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
    "transneed": 1,
    "transneedStr": "零担",
    "vipId": "",
    "shipperId": "",
    "shipperMb": "15999999999",
    "shipperTel": "788",
    "shipper": "彭小林",
    "shipperCid": "",
    "shipperAddr": "上海",
    "consigneeMb": "18614079738",
    "consigneeTel": "888",
    "consignee": "李紫洋",
    "consigneeAddr": "北京朝阳",
    "product": "玻璃",
    "totalQty": 3,
    "qty": 3,
    "packages": "工工式",
    "weight": 6.00,
    "volumn": 25.00,
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
    "accTrans": 378.00,
    "accFetch": 3.00,
    "accPackage": 5.00,
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
    "accSum": 386.00,
    "accType": 2,
    "accTypeStr": "提付",
    "backQty": "签回单",
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
    "fromType": 3
    }
    ]}
     */
    override fun getInventory() {
        get<String>(ApiInterface.DELIVERY_SOMETHING_STOCK_GET + "?=", null, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getInventoryS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<DeliverySomethingHouseBean>>() {}.type))
                }

            }

        })
    }
    override fun saveInfo(ob: JSONObject) {
        post<String>(ApiInterface.DELIVERY_SOMETHING_NEW_COMPELETE_POST, getRequestBody(ob), object : CallBacks {
            override fun onResult(result: String) {
                mView?.saveInfoS("")

            }

        })
    }



}