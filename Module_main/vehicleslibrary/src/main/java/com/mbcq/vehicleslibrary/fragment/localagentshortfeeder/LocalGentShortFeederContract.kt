package com.mbcq.vehicleslibrary.fragment.localagentshortfeeder

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-09-22 13:06
 */

class LocalGentShortFeederContract {

    interface View : BaseView {
        fun getPageS(list:List<LocalGentShortFeederBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun getPage(page: Int)

    }
}
