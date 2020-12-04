package com.mbcq.baselibrary.ui.mvp

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.OkGo
import com.lzy.okgo.model.HttpHeaders
import com.lzy.okgo.model.HttpParams
import com.lzy.okgo.request.base.Request
import com.mbcq.baselibrary.BaseApplication
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.util.log.LogUtils
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.annotations.NotNull
import org.json.JSONObject
import org.json.JSONTokener
import java.math.BigDecimal
import java.util.regex.Matcher
import java.util.regex.Pattern


open class BasePresenterImpl<V : BaseView> : BasePresenter<V>, LifecycleObserver {
    protected var mView: V? = null
    override fun attachView(view: V) {
        mView = view
    }

    protected interface CallBacks {
        fun onResult(result: String)
    }

    protected fun getRequestBody(jsonObject: JsonObject): RequestBody {
        val JSON = MediaType.parse("application/json; charset=utf-8")
        return RequestBody.create(JSON, Gson().toJson(jsonObject))
    }

    protected fun getRequestBody(jsonObject: JSONObject): RequestBody {
        val JSON = MediaType.parse("application/json; charset=utf-8")
        return RequestBody.create(JSON, GsonUtils.toPrettyFormat(jsonObject.toString()))
    }

    protected fun getRequestBody(json: String): RequestBody {
        val JSON = MediaType.parse("application/json; charset=utf-8")
        return RequestBody.create(JSON, json)
    }

    protected fun <T> post(url: String, body: RequestBody, callback: CallBacks) {
        post<T>(url, body, null, false, callback)
    }

    protected fun <T> post(url: String, params: HttpParams, callback: CallBacks) {
        post<T>(url, null, params, false, callback)
    }

    /**
     * 登录不需要传headers （token）
     */
    protected fun <T> post(url: String, body: RequestBody?, params: HttpParams?, isAddHeader: Boolean, callback: CallBacks) {
        val mHttpHeaders = HttpHeaders()
        if (!isAddHeader) {
            mView?.getContext()?.let {
                mHttpHeaders.put("Content-Type", "application/json")
                mHttpHeaders.put("charset", "utf-8")
                mHttpHeaders.put("token", UserInformationUtil.getUserToken(it))
            }
        }

        OkGo.post<T>(url).tag(mView?.getContext()).headers(if (isAddHeader) HttpHeaders() else mHttpHeaders).upRequestBody(body).params(params
                ?: HttpParams()).execute(object : ResultDataCallBack<T>() {
            override fun onResult(result: String) {
                if (result == "null") {
                    mView?.showError("服务器超时 请重试!")
                    return
                }
                val json = JSONTokener(result).nextValue()
                if (json is JSONObject) {
                    val obj = JSONObject(result)
                    val isSuccess = obj.optString("ljCode")
                    if (mView?.getContext() is Activity) {
                        (mView?.getContext() as Activity).runOnUiThread(Runnable {
                            if (isSuccess == "0")
                                callback.onResult(result)
                            else {
                                val errorLog = obj.optString("msg")
                                if (errorLog.contains("token值无效")) {
                                    mView?.UnToken(errorLog)
                                } else
                                    mView?.showError(errorLog)
                            }
                        })
                    }

                } else {
                    if (mView?.getContext() is Activity) {
                        (mView?.getContext() as Activity).runOnUiThread {
                            callback.onResult(result)
                        }
                    }

                }

            }

            override fun onStart(request: Request<T?, out Request<*, *>?>?) {
                super.onStart(request)
                mView?.showLoading()
            }

            override fun onFinish() {
                super.onFinish()
                mView?.closeLoading()
            }

        })
    }

    protected fun <X> getList(result: String): List<X> {
        val obj = JSONObject(result)
        return Gson().fromJson<List<X>>(obj.optString("data"), object : TypeToken<List<X>>() {}.type)

    }

    protected fun checkStrIsNum(str: String): Boolean {
        try {
            /** 先将str转成BigDecimal，然后在转成String  */
            BigDecimal(str).toString()
        } catch (e: java.lang.Exception) {
            /** 如果转换数字失败，说明该str并非全部为数字  */
            return false
        }
        val isNum: Matcher = Pattern.compile("-?[0-9]+(\\.[0-9]+)?").matcher(str)
        return isNum.matches()
    }

    protected fun <T> get(url: String, params: HttpParams?, callback: CallBacks) {
        val mHttpHeaders = HttpHeaders()
        mView?.getContext()?.let {
            mHttpHeaders.put("Content-Type", "application/json")
            mHttpHeaders.put("charset", "utf-8")
            mHttpHeaders.put("token", UserInformationUtil.getUserToken(it))

        }
        OkGo.get<T>(url).tag(mView?.getContext()).headers(mHttpHeaders).params(params
                ?: HttpParams()).execute(object : ResultDataCallBack<T>() {
            override fun onResult(result: String) {
                if (result == "null") { //配置 config
                    mView?.showError("服务器超时 请重试!")
                    return
                }
                val json = JSONTokener(result).nextValue()
                if (json is JSONObject) {
                    val obj = JSONObject(result)
                    val msg = obj.optString("msg")
                    if (msg.isNotEmpty()) {
                        if (msg.contains("token值无效")) {
                            mView?.UnToken(msg)
                            return
                        }
                        mView?.showError(msg)
                        return
                    }
                    val isSuccess = obj.optString("ljCode")
                    if (mView?.getContext() is Activity) {
                        (mView?.getContext() as Activity).runOnUiThread {
                            if (isSuccess == "0" || isSuccess == "")
                                callback.onResult(result)
                            else {
                                val errorLog = obj.optString("msg")
                                if (errorLog.contains("token值无效")) {
                                    mView?.UnToken(errorLog)
                                } else
                                    mView?.showError(errorLog)
                            }
                        }
                    }

                } else {
                    if (mView?.getContext() is Activity) {
                        (mView?.getContext() as Activity).runOnUiThread {
                            callback.onResult(result)
                        }
                    }

                }

            }

            override fun onStart(request: Request<T?, out Request<*, *>?>?) {
                super.onStart(request)
                mView?.showLoading()
            }

            override fun onFinish() {
                super.onFinish()
                mView?.closeLoading()
            }

        })
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    open fun onCreate(@NotNull owner: LifecycleOwner) {
        LogUtils.e("onCreate")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    open fun onDestroy(@NotNull owner: LifecycleOwner) {
        LogUtils.e("onDestroy")

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_ANY)
    open fun onLifecycleChanged(
            @NotNull owner: LifecycleOwner,
            @NotNull event: Lifecycle.Event
    ) {
        LogUtils.e("onLifecycleChanged")

    }

    override fun detachView() {
        mView = null
    }

    fun showError(s: String) {
        mView?.showError(s)
    }
}