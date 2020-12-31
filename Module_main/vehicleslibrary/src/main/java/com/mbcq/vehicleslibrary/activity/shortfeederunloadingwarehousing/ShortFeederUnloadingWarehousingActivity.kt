package com.mbcq.vehicleslibrary.activity.shortfeederunloadingwarehousing


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.flyco.tablayout.listener.CustomTabEntity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.LocalEntity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_short_feeder_unloading_warehousing.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-12-31 16:28:43 短驳卸车入库
 */

@Route(path = ARouterConstants.ShortFeederUnloadingWarehousingActivity)
class ShortFeederUnloadingWarehousingActivity : BaseMVPActivity<ShortFeederUnloadingWarehousingContract.View, ShortFeederUnloadingWarehousingPresenter>(), ShortFeederUnloadingWarehousingContract.View {
    @Autowired(name = "ShortFeederUnloadingWarehousing")
    @JvmField
    var mShortFeederUnloadingWarehousing: String = ""
    val mTabEntities: ArrayList<CustomTabEntity> = ArrayList()

    override fun getLayoutId(): Int = R.layout.activity_short_feeder_unloading_warehousing

    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        mTabEntities.add(LocalEntity("本车清单"))
        mTabEntities.add(LocalEntity("回单清单"))
        short_feeder_unloading_warehousing_tabLayout.setTabData(mTabEntities)
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getVehicleInfo(JSONObject(mShortFeederUnloadingWarehousing).optString("inoneVehicleFlag"))
    }

    override fun onClick() {
        super.onClick()
        short_feeder_unloading_warehousing_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
}