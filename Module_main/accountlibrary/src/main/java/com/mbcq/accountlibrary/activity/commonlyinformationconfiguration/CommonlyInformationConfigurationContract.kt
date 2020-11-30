package com.mbcq.accountlibrary.activity.commonlyinformationconfiguration

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-11-30 11:11:46 常用网点配置详情页面
 */

class CommonlyInformationConfigurationContract {

    interface View : BaseView {
        fun getDestinationS(result: String)
        fun getPackageS(result: String)
        fun getCargoAppellationS(result: String)

    }

    interface Presenter : BasePresenter<View> {
        fun getDestination()
        /**
         * 包装
         */
        fun getPackage()

        /**
         * 货物 名称
         */
        fun getCargoAppellation()

    }
}
