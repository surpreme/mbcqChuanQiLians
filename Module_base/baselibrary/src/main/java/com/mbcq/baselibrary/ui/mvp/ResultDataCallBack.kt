package com.mbcq.baselibrary.ui.mvp

import com.lzy.okgo.callback.AbsCallback
import com.lzy.okgo.model.Progress
import com.lzy.okgo.model.Response
import com.lzy.okgo.request.base.Request

/**
 *
 */
abstract class ResultDataCallBack<T> : AbsCallback<T?>() {
    protected abstract fun onResult(result: String)
    override fun onStart(request: Request<T?, out Request<*, *>?>?) {

    }
    override fun onSuccess(response: Response<T?>) {


    }
    override fun onCacheSuccess(response: Response<T?>) {}
    override fun onError(response: Response<T?>) {
        onResult(response.body().toString())
    }
    override fun onFinish() {}
    override fun uploadProgress(progress: Progress) {}
    override fun downloadProgress(progress: Progress) {}

    @Throws(Throwable::class)
    override fun convertResponse(response: okhttp3.Response): T? {
        response.body()?.string()?.let {
            onResult(it) }
        return null
    }
    /**
     * 返回的数据类型判断
     * Object json = new JSONTokener(result).nextValue();
     * if (json instanceof JSONArray)
     */
}