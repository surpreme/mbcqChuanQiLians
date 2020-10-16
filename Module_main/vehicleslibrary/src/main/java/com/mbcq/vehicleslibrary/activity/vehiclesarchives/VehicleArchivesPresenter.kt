package com.mbcq.vehicleslibrary.activity.vehiclesarchives

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.activity.alldeparturerecord.shipmentinventory.ShipmentInventoryBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-14 14:43:16 车辆档案
 */

class VehicleArchivesPresenter : BasePresenterImpl<VehicleArchivesContract.View>(), VehicleArchivesContract.Presenter {
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
    }
    ]}
     */
    override fun getPage(page: Int, selWebidCode: String) {
        val params = HttpParams()
        params.put("Page", page)
        params.put("Limit", 15)
        params.put("SelWebidCode", selWebidCode)

        get<String>(ApiInterface.VEHICLE_ARCHIVES_SELECT_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getPageS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<VehicleArchivesBean>>() {}.type))

                }
            }

        })
    }

    override fun deleteItem(json: String, position: Int) {
        post<String>(ApiInterface.VEHICLE_ARCHIVES_DELETE_POST, getRequestBody(json), object : CallBacks {
            override fun onResult(result: String) {
                mView?.deleteItemS(position)
            }

        })
    }

    override fun searchItem(id: String, commonStr: String) {
        val params = HttpParams()
        params.put("id", id)
        params.put("commonStr", commonStr)
        get<String>(ApiInterface.VEHICLE_ARCHIVES_SELECT_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getPageS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<VehicleArchivesBean>>() {}.type))

                }
            }

        })
    }

}