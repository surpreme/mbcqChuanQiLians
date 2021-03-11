package com.mbcq.vehicleslibrary.activity.alldeparturerecord.shortfeederhouse

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.activity.alldeparturerecord.fixtrunkdeparturehouse.FixedTrunkDepartureHouseInfo
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-15 10:12:09
 */

class ShortFeederHousePresenter : BasePresenterImpl<ShortFeederHouseContract.View>(), ShortFeederHouseContract.Presenter {
    override fun saveInfo(ob: JSONObject) {
        post<String>(ApiInterface.COMPELETE_SHORT_TRANSFER_DEPARTURE_BATCH_NUMBER_POST, getRequestBody(ob), object : CallBacks {
            override fun onResult(result: String) {
                mView?.saveInfoS("")

            }

        })
    }

    /**
     * {"code":0,"msg":"","count":6,"data":[
    {
    "id": 2,
    "companyId": 2001,
    "eCompanyId": 2,
    "orderId": "",
    "billno": "10030000111",
    "oBillno": "",
    "billDate": "2020-06-19T13:04:54",
    "billState": 2,
    "billStateStr": "在库",
    "billType": 9,
    "billTypeStr": "string 10",
    "goodsNum": "string 13",
    "okProcess": 14,
    "okProcessStr": "string 15",
    "isUrgent": 16,
    "isUrgentStr": "string 17",
    "isTalkGoods": 18,
    "isTalkGoodsStr": "string 19",
    "webidCode": 20,
    "webidCodeStr": "string 21",
    "ewebidCode": 22,
    "ewebidCodeStr": "string 23",
    "destination": "string 24",
    "transneed": 25,
    "transneedStr": " string 26",
    "vipId": "string 27",
    "shipperId": "string 28",
    "shipperMb": "string 29",
    "shipperTel": "sample string 30",
    "shipper": "string 31",
    "shipperCid": "sample string 32",
    "shipperAddr": "sample string 33",
    "consigneeMb": "sample string 34",
    "consigneeTel": "sample string 35",
    "consignee": "string 36",
    "consigneeAddr": "sample string 37",
    "product": "string 38",
    "totalQty": 39,
    "qty": 40,
    "packages": "sample string 41",
    "weight": 42.00,
    "volumn": 43.00,
    "weightJs": 44.00,
    "safeMoney": 45.00,
    "accDaiShou": 46.00,
    "accHKChange": 47.00,
    "hkChangeReason": "sample string 48",
    "sxf": 49.00,
    "wPrice": 50.00,
    "vPrice": 51.00,
    "qtyPrice": 52.00,
    "accNow": 53.00,
    "accArrived": 54.00,
    "accBack": 55.00,
    "accMonth": 56.00,
    "accHuoKuanKou": 57.00,
    "accTrans": 58.00,
    "accFetch": 59.00,
    "accPackage": 60.00,
    "accSend": 61.00,
    "accGb": 62.00,
    "accSafe": 63.00,
    "accRyf": 64.00,
    "accHuiKou": 65.00,
    "accSms": 66.00,
    "accZz": 67.00,
    "accZx": 68.00,
    "accCb": 69.00,
    "accSl": 70.00,
    "accAz": 71.00,
    "accFj": 72.00,
    "accWz": 73.00,
    "accJc": 74.00,
    "accSum": 75.00,
    "accType": 76,
    "accTypeStr": "string 77",
    "backQty": "string 78",
    "backState": 79,
    "isWaitNotice": 80,
    "isWaitNoticeStr": " string 81",
    "bankCode": " string 82",
    "bankName": " string 83",
    "bankMan": " string 84",
    "bankNumber": "string 85",
    "createMan": "string 86",
    "salesMan": "string 87",
    "opeMan": "汕头",
    "remark": "string 88",
    "fromType": 89
    }
    ]}
     */

    override fun getInventory(page: Int, ewebidCode: String, ewebidCodeStr: String) {
        val params = HttpParams()
//        params.put("page", 1)
//        params.put("limit", 1000)
        params.put("webidCode", ewebidCode)
        params.put("WebidCodeStr", ewebidCodeStr)
//        mView?.getContext()?.let {
//            params.put("SelWebidCode", UserInformationUtil.getWebIdCode(it))
//        }

        get<String>(ApiInterface.WAYBILL_INVENTORY_SELECT_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val mData = Gson().fromJson<List<StockWaybillListBean>>(obj.optString("data"), object : TypeToken<List<StockWaybillListBean>>() {}.type)
                for (item in mData) {
                    item.isDevelopments = true
                    item.developmentsQty = item.qty.toInt()
                }
                mView?.getInventoryS(mData)

            }

        })
    }

    override fun overLocalCar(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_SELECT_LOCAL_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val mTotalData = obj.optJSONArray("data")
                mTotalData?.let { it1 ->
                    if (!it1.isNull(0)) {
                        val mFirstJson = it1.getJSONObject(0)
                        val mFirstData = mFirstJson.optJSONArray("data")
                        mFirstData?.let {
                            val mCarInfo = mFirstData.optString(0)
                            val postBody = JsonObject()
                            postBody.addProperty("id", Gson().fromJson(mCarInfo, FixedTrunkDepartureHouseInfo::class.java).id)
                            postBody.addProperty("InoneVehicleFlag", inoneVehicleFlag)
                            post<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_COMPLETE_LOCAL_INFO_POST, getRequestBody(postBody), object : CallBacks {
                                override fun onResult(mXResult: String) {
                                    mView?.overLocalCarS("")

                                }

                            })

                        }

                    }

                }
            }

        })
    }

}