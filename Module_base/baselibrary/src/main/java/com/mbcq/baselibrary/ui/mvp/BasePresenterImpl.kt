package com.mbcq.baselibrary.ui.mvp

import android.app.Activity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.lzy.okgo.OkGo
import com.lzy.okgo.request.base.Request
import com.mbcq.baselibrary.BaseApplication
import com.mbcq.baselibrary.util.log.LogUtils
import okhttp3.MediaType
import okhttp3.RequestBody
import org.jetbrains.annotations.NotNull
import org.json.JSONObject
import org.json.JSONTokener


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

    protected fun <T> post(url: String, body: RequestBody, callback: CallBacks) {
        OkGo.post<T>(url).tag(BaseApplication.getContext()).upRequestBody(body).execute(object : ResultDataCallBack<T>() {
            override fun onResult(result: String) {
                LogUtils.d(result)
                if (result=="null"){
                    mView?.showError("服务器超时 请重试!")
                    return
                }
                val json = JSONTokener(result).nextValue()
                if (json is JSONObject){
                    val obj=JSONObject(result)
                    val isSuccess=obj.optString("ljCode")
                    if(mView?.getContext() is Activity){
                        (mView?.getContext() as Activity).runOnUiThread(Runnable {
                            if (isSuccess=="0")
                                callback.onResult(result)
                            else{
                                val errorLog=obj.optString("msg")
                                mView?.showError(errorLog)
                            }
                        })
                    }

                }else{
                    if(mView?.getContext() is Activity){
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