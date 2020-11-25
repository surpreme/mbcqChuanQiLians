package com.mbcq.amountlibrary.activity.generaledger

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-20 17:31:12 货款总账
 */

class GeneralLedgerPresenter : BasePresenterImpl<GeneralLedgerContract.View>(), GeneralLedgerContract.Presenter {
    override fun getPage(page: Int) {
        val params = HttpParams()
        params.put("Page", page)
        get<String>(ApiInterface.GENERAL_LEDGER_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getPageS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<GeneralLedgerBean>>() {}.type))
                }

            }

        })
    }

}