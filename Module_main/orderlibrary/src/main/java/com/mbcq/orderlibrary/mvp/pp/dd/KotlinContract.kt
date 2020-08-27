package com.mbcq.orderlibrary.mvp.pp.dd

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2018.08.25
 */

class KotlinContract {

    interface View : BaseView {

    }

    interface Presenter : BasePresenter<View> {

    }
}
