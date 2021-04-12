package com.mbcq.vehicleslibrary.activity.alllocalagent.fixlocalgentshortfeederhouse

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouse.LocalGentShortFeederHouseBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-23 11:05:00
 */

class FixedLocalGentShortFeederHousePresenter : BasePresenterImpl<FixedLocalGentShortFeederHouseContract.View>(), FixedLocalGentShortFeederHouseContract.Presenter {
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
        get<String>(ApiInterface.LOCAL_AGENT_INVENTORY_GET + "?=", null, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getInventoryS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<LocalGentShortFeederHouseBean>>() {}.type))
                }

            }

        })
    }

    /**
     * {"code":0,"msg":"","count":2,"data":[
    {
    "orderId": "",
    "billno": "10010000019",
    "oBillno": "",
    "billDate": "2020-06-28T14:01:28",
    "billState": 12,
    "billStateStr": "本地签收",
    "billType": 0,
    "billTypeStr": "机打",
    "goodsNum": "00019-20",
    "okProcess": 1,
    "okProcessStr": "自提",
    "isUrgent": 0,
    "isUrgentStr": "否",
    "isTalkGoods": 0,
    "isTalkGoodsStr": "否",
    "webidCode": 1001,
    "webidCodeStr": "义乌后湖",
    "ewebidCode": 1001,
    "ewebidCodeStr": "义乌后湖",
    "destination": "北京1",
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
    "totalQty": 20,
    "qty": 20,
    "packages": "aaas",
    "weight": 350.00,
    "volumn": 0.00,
    "weightJs": 350.00,
    "safeMoney": 2345.00,
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
    "accTrans": 123.00,
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
    "accSum": 123.00,
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
    "opeMan": "义乌后湖",
    "remark": "",
    "fromType": 0
    }
    ]}
     */
    override fun getLoadingData(agentBillno: String) {
        val params = HttpParams()
        params.put("AgentBillno", agentBillno)
        params.put("agentType", 1)
        get<String>(ApiInterface.LOCAL_AGENT_AND_TERMINAL_AGENT_FIXED_SELECT_LOADING_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getLoadingDataS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<LocalGentShortFeederHouseBean>>() {}.type))
                }

            }

        })

    }

    override fun completeVehicle(s: JSONObject) {
        post<String>(ApiInterface.LOCAL_AGENT_COMPLETE_VEHICLE_POST, getRequestBody(s), object : CallBacks {
            override fun onResult(result: String) {
                mView?.completeVehicleS()

            }

        })

    }

    override fun removeOrder(removeOrderData: JSONObject) {
        /*  val obj = JSONObject()
          obj.put("commonStr", commonStr)
          obj.put("id", id)
          obj.put("agentBillno", agentBillno)*/
        post<String>(ApiInterface.LOCAL_AGENT_FIXED_REMOVE_LOADING_POST, getRequestBody(removeOrderData), object : CallBacks {
            override fun onResult(result: String) {
                mView?.removeOrderS()

            }

        })

    }

    override fun removeOrderItem(removeOrderData: JSONObject,position: Int, item: LocalGentShortFeederHouseBean) {
        post<String>(ApiInterface.LOCAL_AGENT_FIXED_REMOVE_LOADING_POST, getRequestBody(removeOrderData), object : CallBacks {
            override fun onResult(result: String) {
                mView?.removeOrderItemS(position,item)

            }

        })
    }

    override fun addOrder(removeOrderData: String) {
        post<String>(ApiInterface.LOCAL_AGENT_FIXED_ADD_LOADING_POST, getRequestBody(removeOrderData), object : CallBacks {
            override fun onResult(result: String) {
                mView?.addOrderS()
            }

        })
    }

    override fun addOrderItem(removeOrderData: String,position: Int, item: LocalGentShortFeederHouseBean) {
        post<String>(ApiInterface.LOCAL_AGENT_FIXED_ADD_LOADING_POST, getRequestBody(removeOrderData), object : CallBacks {
            override fun onResult(result: String) {
                mView?.addOrderItemS(position,item)
            }

        })
    }
    /**
     * {"code":0,"msg":"","count":3,"data":[
     * {"id":5,"companyid":2001,"webidcode":1003,"webid":"汕头","belwebcod":1001,"belwebstr":"义乌后湖","caruninum":"13698897362","caruniname":"马帮传奇","mainroute":"汕头，义乌，黄口，青口",
     * "resman":"汕头","mb":"12555999999","tel":"111","addr":"111","opehou":1111,"comlegper":"111","creditlevel":0,"opeman":"汕头","recorddate":"2019-03-06 00:00:00"},
     * {"id":11,"companyid":2001,"webidcode":1003,"webid":"汕头","belwebcod":1001,"belwebstr":"义乌后湖","caruninum":"1111","caruniname":"111......","mainroute":"11111","resman":"qqq......","mb":"15","tel":"","addr":"114","opehou":0,"comlegper":"111","creditlevel":0,"opeman":"wzj","recorddate":"2021-01-19 08:19:23"},{"id":12,"companyid":2001,"webidcode":1003,"webid":"汕头","belwebcod":1006,"belwebstr":"汕头峡山","caruninum":"1111","caruniname":"111","mainroute":"111","resman":"111","mb":"111","tel":"","addr":"111","opehou":0,"comlegper":"111","creditlevel":5,"opeman":"wzj","recorddate":"2021-01-19 08:20:50"}],"totalRow":
    {
    "rowCou": 3
    }
    }
     */
    override fun getTransitCompany(selWebidCode: String) {
        val params = HttpParams()
        params.put("selWebidCode", selWebidCode)
        get<String>(ApiInterface.LOCAL_AGENT_TRANSIT_COMPANY_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getTransitCompanyS(obj.optString("data"))
                }
            }

        })
    }
}