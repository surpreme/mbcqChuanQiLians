package com.mbcq.vehicleslibrary.activity.addvehiclesarchives

import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

/**
 * @author: lzy
 * @time: 2020-10-14 17:20:45 添加车辆档案
 */

class AddVehicleArchivesPresenter : BasePresenterImpl<AddVehicleArchivesContract.View>(), AddVehicleArchivesContract.Presenter {
    override fun saveInfo(jsonObject: JSONObject) {
        post<String>(ApiInterface.VEHICLE_ARCHIVES_ADD_POST,getRequestBody(jsonObject),object:CallBacks{
            override fun onResult(result: String) {
                mView?.saveInfoS("车辆档案新建立完成，点击返回查看详情！")
            }

        })
    }
    override fun getTransportMode() {
        val params = HttpParams()
        params.put("type", "5")
        get<String>(ApiInterface.ALLTYPE_SELECT_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val data = obj.opt("data")
                val json = JSONTokener(data?.toString()).nextValue()
                if (json is JSONArray) {
                    obj.optJSONArray("data").let {
                        it?.toString()?.let { it1 ->
                            mView?.getTransportModeS(it1)
                        }


                    }
                } else {
                    mView?.showError("服务器返回异常信息")
                }

            }

        })
    }

}