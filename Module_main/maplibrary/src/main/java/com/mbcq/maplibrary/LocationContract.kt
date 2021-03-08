package com.mbcq.maplibrary

import com.amap.api.location.AMapLocation
import com.amap.api.maps.model.LatLng
import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.maplibrary.view.WebCodeLocationBean

/**
 * @author: lzy
 * @time: 2018.08.25
 */

class LocationContract {

    interface View : BaseView {
        fun getAllWebCodeInfoS(list: List<WebCodeLocationBean>,mLatLng: LatLng)

    }

    interface Presenter : BasePresenter<View> {
        fun getAllWebCodeInfo(mLatLng: LatLng)

    }
}
