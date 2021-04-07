package com.mbcq.maplibrary

import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.util.system.ToastUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.LocationResultEvent
import kotlinx.android.synthetic.main.selection_map_fragment.*
import org.greenrobot.eventbus.EventBus

@Route(path = ARouterConstants.LocationTestActivity)
class LocationTestActivity : BaseAmapSelectionsActivity() {
    @Autowired(name = "LocationMapType")
    @JvmField
    var mLocationMapType: String = ""
    override fun selectData(result: String) {
        EventBus.getDefault().postSticky(LocationResultEvent(result, mLocationMapType.toInt(),getmLatitude(),getmLongitude()))
        onBackPressed()
    }

    override fun onStart() {
        super.onStart()
        ARouter.getInstance().inject(this)
        sure_location_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (getmSelectAddress().isNotBlank()) {
                    EventBus.getDefault().postSticky(LocationResultEvent(getmSelectAddress(), mLocationMapType.toInt(),getmLatitude(),getmLongitude()))
                    onBackPressed()
                } else
                    ToastUtils.showToast(this@LocationTestActivity, "请选择一个地址进行操作")

            }

        })
    }

}