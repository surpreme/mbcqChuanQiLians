package com.mbcq.orderlibrary.fragment.fixedwaybill

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-12-29 14:03:13 改单记录
 */

class FixedWaybillFragmentContract {

    interface View : BaseView {
        fun getReviewDataS(list: List<FixedWaybillListBean>)
    }

    interface Presenter : BasePresenter<View> {
        fun getReviewData(billno: String)
    }
}
