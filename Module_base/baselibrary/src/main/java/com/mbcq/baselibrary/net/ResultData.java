package com.mbcq.baselibrary.net;

/**
 * TODO
 */
public abstract class ResultData {


    protected abstract void onSuccess(String result);

    protected abstract void error(String error);
    /**
     * 返回的数据类型判断
     *  Object json = new JSONTokener(result).nextValue();
     *  if (json instanceof JSONArray)
     */
}
