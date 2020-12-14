package com.mbcq.vehicleslibrary.activity.departuretrunkdeparturescanoperatingmoreinfo

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-12-14 13:31:14 干线扫描详情
 */

class DepartureTrunkDepartureScanOperatingScanInfoPresenter : BasePresenterImpl<DepartureTrunkDepartureScanOperatingScanInfoContract.View>(), DepartureTrunkDepartureScanOperatingScanInfoContract.Presenter {
    /**
    {"code":0,"msg":"","count":1,"data":[
    {
    "id": 10708,
    "billno": "10030004857",
    "lableNo": "100300048570001",
    "webidCode": 1003,
    "webidCodeStr": "汕头",
    "scanOpeType": 0,
    "scanOpeTypeStr": "短驳装车",
    "deviceNo": "92:FD:7D:F0:1A:6D",
    "content": "PDA货物从汕头出发，下一站是，操作员是lzy",
    "inOneVehicleFlag": "DB1003-20201211-003",
    "scanType": 0,
    "scanTypeStr": "PDA",
    "opeMan": "lzy",
    "recordDate": "2020-12-11T11:12:03"
    }
    ]}
     */
    override fun getPageData(billno: String, inOneVehicleFlag: String, scanOpeType: Int) {
        val params = HttpParams()
        params.put("billno", billno)
        params.put("inOneVehicleFlag", inOneVehicleFlag)
        /**
         * @scanOpeType 操作类型
         * 0 短驳装车
         * 1 干线装车
         * 2 短驳到车
         * 3 干线到车
         */
        params.put("scanOpeType", scanOpeType)
        get<String>(ApiInterface.SHORT_TRUNK_DEPARTURE_SCAN_OPERATING_MORE_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val listAry = obj.optJSONArray("data")
                listAry?.let {
                    val mShowList = mutableListOf<DepartureTrunkDepartureScanOperatingScanInfoBean>()
                    for (itemIndex in 0 until it.length()) {
                        val mDepartureTrunkDepartureScanOperatingScanInfoBean= DepartureTrunkDepartureScanOperatingScanInfoBean()
                        mDepartureTrunkDepartureScanOperatingScanInfoBean.lableNo = listAry.getJSONObject(itemIndex).optString("lableNo")
                        mDepartureTrunkDepartureScanOperatingScanInfoBean.mResultTag = GsonUtils.toPrettyFormat(listAry.getJSONObject(itemIndex))
                        mShowList.add(mDepartureTrunkDepartureScanOperatingScanInfoBean)
                    }
                    if (mShowList.isNotEmpty())
                        mView?.getPageDataS(mShowList)
                }

            }

        })
    }
    override fun getCarScanData(inOneVehicleFlag: String, scanOpeType: Int) {
        val params = HttpParams()
        params.put("inOneVehicleFlag", inOneVehicleFlag)
        /**
         * @scanOpeType 操作类型
         * 0 短驳装车
         * 1 干线装车
         * 2 短驳到车
         * 3 干线到车
         */
        params.put("scanOpeType", scanOpeType)
        get<String>(ApiInterface.SHORT_TRUNK_DEPARTURE_SCAN_OPERATING_MORE_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val listAry = obj.optJSONArray("data")
                listAry?.let {
                    val mShowList = mutableListOf<DepartureTrunkDepartureScanOperatingScanInfoBean>()
                    for (itemIndex in 0 until it.length()) {
                        val mDepartureTrunkDepartureScanOperatingScanInfoBean= DepartureTrunkDepartureScanOperatingScanInfoBean()
                        mDepartureTrunkDepartureScanOperatingScanInfoBean.lableNo = listAry.getJSONObject(itemIndex).optString("lableNo")
                        mDepartureTrunkDepartureScanOperatingScanInfoBean.mResultTag = GsonUtils.toPrettyFormat(listAry.getJSONObject(itemIndex))
                        mShowList.add(mDepartureTrunkDepartureScanOperatingScanInfoBean)
                    }
                    if (mShowList.isNotEmpty())
                        mView?.getCarScanDataS(mShowList)
                }

            }

        })
    }
    override fun getCarInfo(inoneVehicleFlag: String,list: List<DepartureTrunkDepartureScanOperatingScanInfoBean>) {
        val params = HttpParams()
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_LOCAL_INFO_POST, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(1)) {
                        val dataObj = it.optJSONObject(1)
                        mView?.getCarInfoS(Gson().fromJson(dataObj.optString("data"), object : TypeToken<List<DepartureTrunkDepartureScanOperatingMoreCarInfoBean>>() {}.type),list)

                    }
                }
            }

        })
    }
}