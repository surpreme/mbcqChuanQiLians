package com.mbcq.vehicleslibrary.activity.alldeparturerecord.fixshortfeederhouse

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

/**
 * @author: lzy
 * @time: 2020-09-18 14:23:00
 */

class FixShortFeederHousePresenter : BasePresenterImpl<FixShortFeederHouseContract.View>(), FixShortFeederHouseContract.Presenter {
    override fun modify(jsonObject: JSONObject) {
        post<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_MODIFY_LOCAL_INFO_POST, getRequestBody(jsonObject), object : CallBacks {
            override fun onResult(result: String) {
                mView?.modifyS()
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

    override fun getInventory(page: Int) {
        val params = HttpParams()
        params.put("page", 1)
        params.put("limit", 1000)
        get<String>(ApiInterface.WAYBILL_INVENTORY_SELECT_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val mXData =Gson().fromJson<List<StockWaybillListBean>>(obj.optString("data"), object : TypeToken<List<StockWaybillListBean>>() {}.type)
                mView?.getInventoryS(mXData)

            }

        })
    }

    override fun getCarInfo(id: Int, inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("id", id)
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_SELECT_LOCAL_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val mTotalData = obj.optJSONArray("data")
                var mFixShortFeederHouseCarInfo: FixShortFeederHouseCarInfo? = null
                mTotalData?.let { it1 ->
                    if (!it1.isNull(0)) {
                        val mFirstJson = it1.getJSONObject(0)
                        val mFirstData = mFirstJson.optJSONArray("data")
                        mFirstData?.let {
                            val mCarInfo = mFirstData.optString(0)
                            mFixShortFeederHouseCarInfo = Gson().fromJson(mCarInfo, FixShortFeederHouseCarInfo::class.java)
                        }

                    }
                    if (!it1.isNull(1)) {
                        val mSencondJson = it1.getJSONObject(1)
                        val mSecondData = mSencondJson.optString("data")
                        val mXData = Gson().fromJson<List<StockWaybillListBean>>(mSecondData, object : TypeToken<List<StockWaybillListBean>>() {}.type)
                        mFixShortFeederHouseCarInfo?.item = mXData

                    }
                    mFixShortFeederHouseCarInfo?.let {
                        mView?.getCarInfo(it)
                    }
                }
            }

        })

    }

    override fun removeOrder(commonStr: String, id: String, inoneVehicleFlag: String) {
        val postBody = JsonObject()
        postBody.addProperty("CommonStr", commonStr)
        postBody.addProperty("id", id)
        postBody.addProperty("InoneVehicleFlag", inoneVehicleFlag)
        post<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_REMOVE_LOCAL_INFO_POST, getRequestBody(postBody), object : CallBacks {
            override fun onResult(result: String) {
                mView?.removeOrderS()

            }

        })

    }

    override fun removeOrderItem(commonStr: String, id: String, inoneVehicleFlag: String, position: Int, item: StockWaybillListBean) {
        val postBody = JsonObject()
        postBody.addProperty("CommonStr", commonStr)
        postBody.addProperty("id", id)
        postBody.addProperty("InoneVehicleFlag", inoneVehicleFlag)
        post<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_REMOVE_LOCAL_INFO_POST, getRequestBody(postBody), object : CallBacks {
            override fun onResult(result: String) {
                mView?.removeOrderItemS(position, item)

            }

        })
    }

    override fun addOrderItem(commonStr: String, id: String, inoneVehicleFlag: String, position: Int, item: StockWaybillListBean) {
        val mAddOrderDataBean = AddShortOrderDataBean()
        mAddOrderDataBean.commonStr = commonStr
        mAddOrderDataBean.id = id
        mAddOrderDataBean.inoneVehicleFlag = inoneVehicleFlag
        val dbVehicleDetLst = mutableListOf<StockWaybillListBean>()
        dbVehicleDetLst.add(item)
        mAddOrderDataBean.dbVehicleDetLst = dbVehicleDetLst
        post<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_ADD_LOCAL_INFO_POST, getRequestBody(Gson().toJson(mAddOrderDataBean)), object : CallBacks {
            override fun onResult(result: String) {
                mView?.addOrderItemS(position, item)

            }

        })
    }

    override fun addOrder(commonStr: String, id: String, inoneVehicleFlag: String, dbVehicleDetLst: List<StockWaybillListBean>) {
        val mAddOrderDataBean = AddShortOrderDataBean()
        mAddOrderDataBean.commonStr = commonStr
        mAddOrderDataBean.id = id
        mAddOrderDataBean.inoneVehicleFlag = inoneVehicleFlag
        mAddOrderDataBean.dbVehicleDetLst = dbVehicleDetLst

        post<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_ADD_LOCAL_INFO_POST, getRequestBody(Gson().toJson(mAddOrderDataBean)), object : CallBacks {
            override fun onResult(result: String) {
                mView?.addOrderS()

            }

        })
    }

    override fun completeCar(id: Int, inoneVehicleFlag: String) {
        val postBody = JsonObject()
        postBody.addProperty("id", id)
        postBody.addProperty("InoneVehicleFlag", inoneVehicleFlag)
        post<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_COMPLETE_LOCAL_INFO_POST, getRequestBody(postBody), object : CallBacks {
            override fun onResult(result: String) {
                mView?.completeCarS()

            }

        })
    }

    /**
     * {"code":0,"msg":"","count":10,"data":[
    {
    "id": 43,
    "companyid": 2001,
    "vehicleno": "浙G12362",
    "vehicletype": 1,
    "vehicleshape": "0",
    "vlength": 12.00,
    "vwidth": 16.00,
    "vheight": 2001.00,
    "enginenum": "62382973763276",
    "ylpnum": "1234567890",
    "vjnum": "098765421",
    "addnum": "12345678",
    "openum": "9876543",
    "vcolor": "红色",
    "buydate": "2019-01-17T00:00:00",
    "supweight": 12.00,
    "volumn": 23.00,
    "yeachedate": "2019-01-17T00:00:00",
    "safenum": "23267812367",
    "safeunit": "中国人寿保险",
    "boxmoney": null,
    "vowner": "张飞云",
    "idcard": "433155198808082121",
    "ownertel": null,
    "ownermb": "15622266667",
    "owneradd": "北京市西城区夏城路北城街",
    "vbelongunit": "北京市供电局",
    "unitadd": "北京市西城区朝阳路1111号",
    "chauffer": "张凯",
    "chaidcard": "433125555666667777",
    "chauffertel": "",
    "chauffermb": "16276665366",
    "driverlicense": "25253367126545",
    "chaadd": "上海市黄浦区热敏广场",
    "belwebcode": 1004,
    "belweb": "汕头",
    "sharewebcode": "",
    "shareweb": "义乌后湖,义乌篁园,汕头,潮州彩塘,潮州庵埠,汕头峡山,杭州萧山,杭州余杭,广州丰和,永康中田,测试117,测试118,测试119,测试120",
    "vusetype": 0,
    "oilwear": 12.00,
    "Israliable": 0
    }]}
     */

    override fun getVehicles(vehicleNo: String) {
        val params = HttpParams()
        params.put("vehicleNo", vehicleNo)
        get<String>(ApiInterface.VEHICLE_SELECT_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val json = JSONTokener(obj.optString("data")).nextValue()
                if (json is JSONArray) {
                    obj.optJSONArray("data")?.let {
                        if (!it.isNull(0)) {
                            mView?.getVehicleS(obj.optString("data"))
                        }
                    }

                }


            }

        })
    }

    override fun invalidOrder(inoneVehicleFlag: String, id: Int) {
        val obj = JsonObject()
        obj.addProperty("inoneVehicleFlag", inoneVehicleFlag)
        obj.addProperty("id", id)
        post<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_INVALID_INFO_POST, getRequestBody(obj), object : CallBacks {
            override fun onResult(result: String) {
                mView?.invalidOrderS()

            }

        })
    }
}