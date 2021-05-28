package com.mbcq.orderlibrary.activity.deliverysomethingmaphouse


import android.os.Bundle
import android.os.PersistableBundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.maps.AMap
import com.amap.api.maps.MapView
import com.amap.api.maps.model.LatLng
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_delivery_something_map_house.*

/**
 * @author: lzy
 * @time: 2021-04-24 11:27:43 送货地图选择
 */

@Route(path = ARouterConstants.DeliverySomethingMapHouseActivity)
class DeliverySomethingMapHouseActivity : BaseDeliverySomethingMapHouseActivity<DeliverySomethingMapHouseActivityContract.View, DeliverySomethingMapHouseActivityPresenter, DeliverySomethingMapHouseBean>(), DeliverySomethingMapHouseActivityContract.View {
    override fun getLayoutId(): Int = R.layout.activity_delivery_something_map_house
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.white)
        initGaoDeAmapView(savedInstanceState)


    }

    override fun onClick() {
        super.onClick()
        back_iv.onSingleClicks {
            onBackPressed()
        }
    }

    override fun locationSuccessResult(mLatLng: LatLng) {
    }

    override fun getRecyclerViewId(): Int = R.id.delivery_location_recycler

    override fun setAdapter(): BaseRecyclerAdapter<DeliverySomethingMapHouseBean> =DeliverySomethingMapHouseAdapter(mContext).also {

    }

}