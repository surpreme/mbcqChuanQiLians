package com.mbcq.amountlibrary.activity.loanrecycle

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-20 09:15:45 货款回收
 */

class LoanRecyclePresenter : BasePresenterImpl<LoanRecycleContract.View>(), LoanRecycleContract.Presenter {
    /**
     * Cc很酷:
    (waybill.SelType == 7)//根据开单时间查询

    Cc很酷:
    (waybill.SelType == 8)//根据签收时间查询

     */
    override fun getPage(page: Int) {
        val params = HttpParams()
        params.put("page", page)
        params.put("SelType", 7)
        get<String>(ApiInterface.LOAN_RECYCLE_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getPageS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<LoanRecycleBean>>() {}.type))

                }

            }

        })
    }

}