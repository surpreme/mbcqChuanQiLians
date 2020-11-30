package com.mbcq.accountlibrary.activity.commonlyinformationconfiguration

import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

/**
 * @author: lzy
 * @time: 2020-11-30 11:11:46 常用网点配置详情页面
 */

class CommonlyInformationConfigurationPresenter : BasePresenterImpl<CommonlyInformationConfigurationContract.View>(), CommonlyInformationConfigurationContract.Presenter {
    override fun getDestination() {
        get<String>(ApiInterface.ACCEPT_DESTINATION_GET + "?=", null, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val data = obj.opt("data")
                val json = JSONTokener(data?.toString()).nextValue()
                if (json is JSONArray) {
                    data?.let {
                        mView?.getDestinationS(it.toString())
                    }
                } else {
                    mView?.showError("服务器返回异常信息")
                }
            }

        })

    }
    override fun getCargoAppellation() {
        get<String>(ApiInterface.ACCEPT_SELECT_CARGO_APPELLATION_GET + "?=", null, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val data = obj.opt("data")
                val json = JSONTokener(data?.toString()).nextValue()
                if (json is JSONArray) {
                    data?.let {
                        mView?.getCargoAppellationS(it.toString())
                    }
                } else {
                    mView?.showError("服务器返回异常信息")
                }
            }

        })
    }
    override fun getPackage() {
        get<String>(ApiInterface.ACCEPT_SELECT_PACKAGE_GET + "?=", null, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val data = obj.opt("data")
                val json = JSONTokener(data?.toString()).nextValue()
                if (json is JSONArray) {
                    data?.let {
                        mView?.getPackageS(it.toString())
                    }
                } else {
                    mView?.showError("服务器返回异常信息")
                }
            }

        })
    }
}