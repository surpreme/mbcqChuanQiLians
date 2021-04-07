package com.mbcq.orderlibrary.activity.fixreceiver


import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.ui.onToolbarBackClicks
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_fix_receiver.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-04-07 17:46:43 修改收货客户
 */

@Route(path = ARouterConstants.FixReceiverActivity)
class FixReceiverActivity : BaseMVPActivity<FixReceiverContract.View, FixReceiverPresenter>(), FixReceiverContract.View {
    @Autowired(name = "FixReceiverData")
    @JvmField
    var mLastData: String = ""

    override fun getLayoutId(): Int = R.layout.activity_fix_receiver

    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        showShipperInfo()

    }

    private fun showShipperInfo() {
        val obj = JSONObject(mLastData)
        mReceiverId_ed.setText(obj.optString("vipId"))
        name_ed.setText(obj.optString("contactMan"))
        phone_ed.setText(obj.optString("contactMb"))
        company_ed.setText(obj.optString("companyName"))
        mConsigneeTel_ed.setText(obj.optString("contactTel"))
        mReceiverCid_ed.setText(obj.optString("idCard"))
        address_ed.setText(obj.optString("address"))

    }

    override fun onClick() {
        super.onClick()
        save_btn.onSingleClicks {
            TalkSureCancelDialog(mContext, getScreenWidth(), "您确认要修改此收货客户吗？") {
                val obj = JSONObject(mLastData)
                obj.put("vipId", mReceiverId_ed.text.toString())
                obj.put("contactMan", name_ed.text.toString())
                obj.put("contactMb", phone_ed.text.toString())
                obj.put("companyName", company_ed.text.toString())
                obj.put("idCard", mReceiverCid_ed.text.toString())
                obj.put("address", address_ed.text.toString())
                mPresenter?.fixData(obj)
            }.show()
        }
        fix_receiver_toolbar.onToolbarBackClicks {
            onBackPressed()
        }
    }

    override fun fixDataS(result: String) {
        TalkSureDialog(mContext, getScreenWidth(), "修改成功，点击返回") {
            onBackPressed()
        }.show()
    }
}