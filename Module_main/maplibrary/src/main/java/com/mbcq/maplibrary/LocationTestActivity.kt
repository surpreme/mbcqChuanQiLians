package com.mbcq.maplibrary

import android.content.Intent
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.util.system.ToastUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.Constant
import com.mbcq.commonlibrary.LocationResultEvent
import com.mbcq.maplibrary.search.SearchPoiActivity
import kotlinx.android.synthetic.main.selection_map_fragment.*
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject

@Route(path = ARouterConstants.LocationTestActivity)
class LocationTestActivity : BaseAmapSelectionsActivity() {
    @Autowired(name = "LocationMapType")
    @JvmField
    var mLocationMapType: String = ""

    companion object {
        const val RESULT_CODE = 5543

    }

    override fun selectData(result: String) {
        EventBus.getDefault().postSticky(LocationResultEvent(result, mLocationMapType.toInt(), getmLatitude(), getmLongitude()))
        onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_CODE) {
            (data?.getStringExtra("poi"))?.let {
                LogUtils.e(it)
                val obj=JSONObject(it)
                moveAddress(obj.optDouble("latitude"),obj.optDouble("longitude"),obj.optString("name"))
//                ToastUtils.showToast(this, it)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        ARouter.getInstance().inject(this)
        house_search_fragment_tv.onSingleClicks {
            startActivityForResult(Intent(this, SearchPoiActivity::class.java), RESULT_CODE)

        }
        sure_location_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (getmSelectAddress().isNotBlank()) {
                    EventBus.getDefault().postSticky(LocationResultEvent(getmSelectAddress(), mLocationMapType.toInt(), getmLatitude(), getmLongitude()))
                    onBackPressed()
                } else
                    ToastUtils.showToast(this@LocationTestActivity, "请选择一个地址进行操作")

            }

        })
    }

}