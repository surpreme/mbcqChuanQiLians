package com.mbcq.vehicleslibrary.activity.allterminalagent.terminalagentbycarhouse

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouse.LocalGentShortFeederHouseBean
import com.mbcq.vehicleslibrary.activity.allterminalagent.TerminalAgentByCarHouseBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-25 13:33:00
 */

class TerminalAgentByCarHousePresenter : BasePresenterImpl<TerminalAgentByCarHouseContract.View>(), TerminalAgentByCarHouseContract.Presenter {
    /**
     * {"code":0,"msg":"","count":3,"data":[
    {
    "id": 39,
    "companyId": 2001,
    "eCompanyId": 0,
    "orderId": "",
    "billno": "10030000217",
    "oBillno": "",
    "billDate": "2020-06-23T17:01:59",
    "billState": 1,
    "billStateStr": "已入库",
    "billType": 0,
    "billTypeStr": "机打",
    "goodsNum": "00217-50",
    "okProcess": 1,
    "okProcessStr": "自提",
    "isUrgent": 0,
    "isUrgentStr": "否",
    "isTalkGoods": 0,
    "isTalkGoodsStr": "否",
    "webidCode": 1003,
    "webidCodeStr": "汕头",
    "ewebidCode": 1003,
    "ewebidCodeStr": "汕头",
    "destination": "彩塘",
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
    "product": "玻璃1",
    "totalQty": 50,
    "qty": 50,
    "packages": "aaas",
    "weight": 10.00,
    "volumn": 0.60,
    "weightJs": 156.00,
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
    "accTrans": 30.00,
    "accFetch": 0.00,
    "accPackage": 0.00,
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
    "accSum": 30.00,
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
    override fun getInventory() {
        get<String>(ApiInterface.TERMINAL_AGENT_INVENTORY_GET + "?=", null, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getInventoryS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<TerminalAgentByCarHouseBean>>() {}.type))
                }

            }

        })
    }
    override fun completeVehicle(s: JSONObject) {
        post<String>(ApiInterface.TERMINAL_AGENT_COMPLETE_VEHICLE_GET, getRequestBody(s), object : CallBacks {
            override fun onResult(result: String) {
                mView?.completeVehicleS()

            }

        })

    }

}