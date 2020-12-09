package com.mbcq.vehicleslibrary.activity.shorthousechecklist

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-12-09 13:16:43 短驳在库清单
 */

class ShortHouseChecklistPresenter : BasePresenterImpl<ShortHouseChecklistContract.View>(), ShortHouseChecklistContract.Presenter {
    override fun getInventory(page: Int) {
        val params = HttpParams()
        params.put("page", 1)
        params.put("limit", 1000)
        get<String>(ApiInterface.WAYBILL_INVENTORY_SELECT_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {

                val obj = JSONObject(result)
                mView?.getInventoryS(Gson().fromJson<List<ShortHouseChecklistBean>>(obj.optString("data"), object : TypeToken<List<ShortHouseChecklistBean>>() {}.type))

            }

        })
    }
}