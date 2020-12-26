package com.mbcq.vehicleslibrary.activity.alldeparturerecord.fixtrunkdeparturehouse

import android.telecom.Call
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
 * @time: 2020-09-18 11:33
 */

class FixedTrunkDepartureHousePresenter : BasePresenterImpl<FixedTrunkDepartureHouseContract.View>(), FixedTrunkDepartureHouseContract.Presenter {
    override fun getCarInfo(id: Int, inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("id", id)
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_LOCAL_INFO_POST, params, object : CallBacks {
            override fun onResult(result: String) {

                val obj = JSONObject(result)
                val mTotalData = obj.optJSONArray("data")
                var mFixedTrunkDepartureHouseInfo: FixedTrunkDepartureHouseInfo? = null
                mTotalData?.let { it1 ->
                    if (!it1.isNull(0)) {
                        val mFirstJson = it1.getJSONObject(0)
                        val mFirstData = mFirstJson.optJSONArray("data")
                        mFirstData?.let {
                            val mCarInfo = mFirstData.optString(0)
                            mFixedTrunkDepartureHouseInfo = Gson().fromJson(mCarInfo, FixedTrunkDepartureHouseInfo::class.java)
                        }

                    }
                    if (!it1.isNull(1)) {
                        val mSencondJson = it1.getJSONObject(1)
                        val mSecondData = mSencondJson.optString("data")
                        mFixedTrunkDepartureHouseInfo?.item = Gson().fromJson<List<StockWaybillListBean>>(mSecondData, object : TypeToken<List<StockWaybillListBean>>() {}.type)

                    }
                    mFixedTrunkDepartureHouseInfo?.let {
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
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_REMOVE_LOCAL_INFO_POST, getRequestBody(postBody), object : CallBacks {
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
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_REMOVE_LOCAL_INFO_POST, getRequestBody(postBody), object : CallBacks {
            override fun onResult(result: String) {
                mView?.removeOrderItemS(position, item)

            }

        })
    }

    override fun addOrderItem(commonStr: String, id: String, inoneVehicleFlag: String, position: Int, item: StockWaybillListBean) {
        val mAddTrunkOrderDataBean = AddTrunkOrderDataBean()
        mAddTrunkOrderDataBean.commonStr = commonStr
        mAddTrunkOrderDataBean.id = id
        mAddTrunkOrderDataBean.inoneVehicleFlag = inoneVehicleFlag
        val gxVehicleDetLst = mutableListOf<StockWaybillListBean>()
        gxVehicleDetLst.add(item)
        mAddTrunkOrderDataBean.gxVehicleDetLst = gxVehicleDetLst
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_ADD_LOCAL_INFO_POST, getRequestBody(Gson().toJson(mAddTrunkOrderDataBean)), object : CallBacks {
            override fun onResult(result: String) {
                mView?.addOrderItemS(position, item)

            }

        })
    }

    override fun addOrder(commonStr: String, id: String, inoneVehicleFlag: String, gxVehicleDetLst: List<StockWaybillListBean>) {
        val mAddTrunkOrderDataBean = AddTrunkOrderDataBean()
        mAddTrunkOrderDataBean.commonStr = commonStr
        mAddTrunkOrderDataBean.id = id
        mAddTrunkOrderDataBean.inoneVehicleFlag = inoneVehicleFlag
        mAddTrunkOrderDataBean.gxVehicleDetLst = gxVehicleDetLst

        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_ADD_LOCAL_INFO_POST, getRequestBody(Gson().toJson(mAddTrunkOrderDataBean)), object : CallBacks {
            override fun onResult(result: String) {
                mView?.addOrderS()

            }

        })
    }

    override fun modify(jsonObject: JSONObject) {
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_MODIFY_LOCAL_INFO_POST, getRequestBody(jsonObject), object : CallBacks {
            override fun onResult(result: String) {
                mView?.modifyS()
            }

        })
    }

    override fun getInventory(page: Int) {
        val params = HttpParams()
        params.put("page", 1)
        params.put("limit", 1000)
        get<String>(ApiInterface.WAYBILL_INVENTORY_SELECT_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {

                val obj = JSONObject(result)
                mView?.getInventoryS(Gson().fromJson<List<StockWaybillListBean>>(obj.optString("data"), object : TypeToken<List<StockWaybillListBean>>() {}.type))

            }

        })
    }

    override fun invalidOrder(inoneVehicleFlag: String, id: Int) {
        val obj = JsonObject()
        obj.addProperty("inoneVehicleFlag", inoneVehicleFlag)
        obj.addProperty("id", id)
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_INVALID_INFO_POST, getRequestBody(obj), object : CallBacks {
            override fun onResult(result: String) {
                mView?.invalidOrderS()

            }

        })
    }

    override fun completeCar(id: Int, inoneVehicleFlag: String) {
        val postBody = JsonObject()
        postBody.addProperty("id", id)
        postBody.addProperty("InoneVehicleFlag", inoneVehicleFlag)
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_COMPLETE_LOCAL_INFO_POST, getRequestBody(postBody), object : CallBacks {
            override fun onResult(result: String) {
                mView?.completeCarS()

            }

        })
    }
    override fun addStowageAlongWay(inoneVehicleFlag: String, webidCode: String, webidCodeStr: String) {
        val jsonObj = JSONObject()
        jsonObj.put("inoneVehicleFlag", inoneVehicleFlag)
        jsonObj.put("webidCode", webidCode)
        jsonObj.put("webidCodeStr", webidCodeStr)
        post<String>(ApiInterface.STOWAGE_ALONG_WAY_RECORD_MAIN_LINE_ADD_LOCAL_INFO_POST, getRequestBody(jsonObj), object : CallBacks {
            override fun onResult(result: String) {
                mView?.addStowageAlongWayS(inoneVehicleFlag, webidCode, webidCodeStr, result)
            }

        })
    }

    override fun getStowageAlongWay(inoneVehicleFlag: String, id: Int) {
        val params=HttpParams()
        params.put("inoneVehicleFlag",inoneVehicleFlag)
        params.put("id",id)
        get<String>(ApiInterface.STOWAGE_ALONG_WAY_RECORD_MAIN_LINE_SELECT_LOCAL_INFO_POST,params,object :CallBacks{
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                mView?.getStowageAlongWayS(Gson().fromJson<List<FixedStowageAlongWayBean>>(obj.optString("data"), object : TypeToken<List<FixedStowageAlongWayBean>>() {}.type))

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
    override fun deleteStowageAlongWay(inoneVehicleFlag: String) {
        val jsonObj=JSONObject()
        jsonObj.put("inoneVehicleFlag",inoneVehicleFlag)
        post<String>(ApiInterface.STOWAGE_ALONG_WAY_RECORD_MAIN_LINE_DELETE_LOCAL_INFO_POST,getRequestBody(jsonObj),object :CallBacks{
            override fun onResult(result: String) {

            }

        })
    }
}