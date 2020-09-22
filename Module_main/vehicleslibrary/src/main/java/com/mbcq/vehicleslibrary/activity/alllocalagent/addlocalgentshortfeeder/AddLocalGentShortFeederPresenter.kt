package com.mbcq.vehicleslibrary.activity.alllocalagent.addlocalgentshortfeeder

import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

/**
 * @author: lzy
 * @time: 2020-09-22 15:48
 */

class AddLocalGentShortFeederPresenter : BasePresenterImpl<AddLocalGentShortFeederContract.View>(), AddLocalGentShortFeederContract.Presenter {
    /**
     *
    {"code":0,"msg":"","count":1,"data":[
    {
    "proCode": 0,
    "inOneVehicleFlag": "BDDL1003-20200922-001"
    }
    ]}
     */
    override fun getDepartureBatchNumber() {
        get<String>(ApiInterface.LOCAL_AGENT_BATCH_NUMBER_GET + "?=", null, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(0)){
                        val itemObj=it.getJSONObject(0)
                        mView?.getDepartureBatchNumberS(itemObj.optString("inOneVehicleFlag"))
                    }

                }


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

    override fun getVehicles() {
        get<String>(ApiInterface.VEHICLE_SELECT_INFO_GET + "?=", null, object : CallBacks {
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

}