package com.mbcq.orderlibrary.activity.exceptionregistration

import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-28 17:40:00 异常登记
 */

class ExceptionRegistrationContract {

    interface View : BaseView {

        fun getExceptionInfoS(data: JSONObject)
        fun getExceptionInfoNull()
        fun postImgS(url: String)
        fun getWrongTypeS(result: String)
        fun getWrongChildrenTypeS(result: String)
        fun getShortCarNumberS(result: ExceptionRegistrationShortCarNumberBean)
        fun getDepartureLotS(result: ExceptionRegistrationDepartureCarNumberBean)
    }

    interface Presenter : BasePresenter<View> {
        fun getExceptionInfo(billno: String)
        fun postImg(params: HttpParams)
        fun getWrongType()
        fun updateAllInfo(jsonObject: JSONObject)

        /**
         *    * id : 7
         * companyid : 2001
         * typecode : 7
         * partypcod : 0
         * tdescribe : 破损
         * opeman :
         * recorddate : 2018-12-29T13:52:03
         */
        fun getWrongChildrenType(id: String, companyid: String, typecode: String, partypcod: String, tdescribe: String, opeman: String, recorddate: String)

        /**
         * 短驳和干线都需要查询发车批次
         */
        fun getDepartureLot(billno: String)
        fun getShortCarNumber(billno: String)

    }
}
