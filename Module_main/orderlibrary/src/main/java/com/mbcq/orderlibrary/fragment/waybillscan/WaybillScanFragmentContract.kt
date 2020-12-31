package com.mbcq.orderlibrary.fragment.waybillscan

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-12-29 14:43:07 运单扫描轨迹
 */

class WaybillScanFragmentContract {

    interface View : BaseView {
        fun getWaybillScanS(list: List<WaybillScanFragmentListBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun getWaybillScan(billno: String)

    }
}
