package com.mbcq.accountlibrary.activity.login


import com.google.gson.Gson
import com.google.gson.JsonObject
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface


class LogInPresenter : BasePresenterImpl<LogInContract.View>(), LogInContract.Presenter {
    /**
     * 成功
     * {"code":0,"ljCode":"0","msg":"","token":"xxxxx","webidCode":"1003","webidCodeStr":"汕头"}
     * 失败
     * {"code":0,"ljCode":"1001","msg":"用户名或密码错误或用户已被锁定，请稍后尝试或联系管理员!","token":"","webidCode":"1003","webidCodeStr":"汕头"}
     */
    override fun logIn(userName: String, pw: String) {
        val jsonObj = JsonObject()
        jsonObj.addProperty("userName",userName)
        jsonObj.addProperty("pw",pw)
        post<LogInSuccessBean>(ApiInterface.LOG_IN_POST,getRequestBody(jsonObj),object :CallBacks{
            override fun onResult(result: String) {
                mView?.loInS(Gson().fromJson(result, LogInSuccessBean::class.java))
            }

        })



    }

}