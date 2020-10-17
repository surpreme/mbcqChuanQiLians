package com.mbcq.orderlibrary.activity.deliveryadjustment


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_delivery_adjustment.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-16 14:27:44 放货调整
 */

@Route(path = ARouterConstants.DeliveryAdjustmentActivity)
class DeliveryAdjustmentActivity : BaseMVPActivity<DeliveryAdjustmentContract.View, DeliveryAdjustmentPresenter>(), DeliveryAdjustmentContract.View {
    @Autowired(name = "DeliveryAdjustment")
    @JvmField
    var mLastDataNo: String = ""
    override fun getLayoutId(): Int = R.layout.activity_delivery_adjustment
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    override fun initDatas() {
        super.initDatas()
        if (mLastDataNo.isNotBlank()) {
            essential_ed.setText(mLastDataNo)
            mPresenter?.getWillByInfo(mLastDataNo)
        }
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        new_info_ll.visibility = View.GONE
        request_control_btn.isClickable = false
        request_control_btn.alpha = 0.5f
        confirm_delivery_btn.alpha = 0.5f
        confirm_delivery_btn.isClickable = false
    }

    override fun onClick() {
        super.onClick()
        request_control_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                requestControl()
            }

        })
        confirm_delivery_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                requestControl()
            }

        })
        search_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (essential_ed.text.toString().isBlank()) {
                    TalkSureDialog(mContext, getScreenWidth(), "请输入运单号进行查询").show()
                    return
                }
                mPresenter?.getWillByInfo(essential_ed.text.toString())
            }

        })

        new_info_cb.setOnCheckedChangeListener { _, isChecked ->
            new_info_ll.visibility = if (isChecked) View.VISIBLE else View.GONE
        }
        delivery_adjustment_toolbar.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    private fun requestControl() {
        if (new_info_cb.isChecked) {
            if (new_receiver_ed.text.toString().isNotEmpty()) {
                updateDataObj?.remove("consignee")
                updateDataObj?.put("consignee", new_receiver_ed.text.toString())
            }
            if (new_receiver_tel_ed.text.toString().isNotEmpty()) {
                updateDataObj?.remove("consigneeTel")
                updateDataObj?.put("consigneeTel", new_receiver_tel_ed.text.toString())
            }
            if (new_receiver_phone_ed.text.toString().isNotEmpty()) {
                updateDataObj?.remove("consigneeMb")
                updateDataObj?.put("consigneeMb", new_receiver_phone_ed.text.toString())

            }
        }

        //////////////////////
        if (updateDataObj?.optString("isWaitNotice") == "1") {
            updateDataObj?.remove("isWaitNotice")
            updateDataObj?.put("isWaitNotice", 2)
        } else if (updateDataObj?.optString("isWaitNotice") == "2") {
            updateDataObj?.remove("isWaitNotice")
            updateDataObj?.put("isWaitNotice", 1)
        }
        updateDataObj?.let {
            mPresenter?.updateData(it)

        }

    }

    var updateDataObj: JSONObject? = null
    var mWebidCode = ""
    var mEWebidCode = ""
    override fun getWillByInfoS(data: JSONObject) {
        updateDataObj = data
        item_no_tv.text = data.optString("goodsNum")
        invoicing_date_tv.text = data.optString("billDate")
        delivery_network_tv.text = data.optString("webidCodeStr")
        mWebidCode = data.optString("webidCode")
        mEWebidCode = data.optString("ewebidCode")
        arrival_outlets_tv.text = data.optString("ewebidCodeStr")
        destination_tv.text = data.optString("destination")
        shipper_tv.text = data.optString("shipper")
        shipper_company_tv.text = data.optString("shipperAddr")
        receiver_tv.text = data.optString("consignee")
        receiver_company_tv.text = data.optString("consigneeAddr")
        receiver_tel_tv.text = data.optString("consigneeTel")
        receiver_mobile_tv.text = data.optString("consigneeMb")
        remarks_tv.text = data.optString("remark")
        /**
         * 0 默认
         * 1 等通知 已控货
         * 2 已放货
         */
        when (data.optString("isWaitNotice")) {
            "0" -> {
                request_control_btn.isClickable = false
                request_control_btn.alpha = 0.5f
                confirm_delivery_btn.alpha = 0.5f
                confirm_delivery_btn.isClickable = false

            }

            "1" -> {
                request_control_btn.isClickable = false
                request_control_btn.alpha = 0.5f
                confirm_delivery_btn.alpha = 1.0f
                confirm_delivery_btn.isClickable = true

            }
            "2" -> {
                request_control_btn.isClickable = true
                request_control_btn.alpha = 1.0f
                confirm_delivery_btn.alpha = 0.5f
                confirm_delivery_btn.isClickable = false

            }
        }

    }

    override fun getWillByInfoNull() {
        item_no_tv.text = ""
        invoicing_date_tv.text = ""
        delivery_network_tv.text = ""
        arrival_outlets_tv.text = ""
        destination_tv.text = ""
        shipper_tv.text = ""
        shipper_company_tv.text = ""
        receiver_tv.text = ""
        receiver_company_tv.text = ""
        receiver_tel_tv.text = ""
        receiver_mobile_tv.text = ""
        remarks_tv.text = ""
        mWebidCode = ""
        mEWebidCode = ""
        updateDataObj = null
        TalkSureDialog(mContext, getScreenWidth(), "未查询到运单信息，请检查后重新查询").show()
    }

    override fun updateDataS() {
        TalkSureDialog(mContext, getScreenWidth(), "运单号为：${essential_ed.text}已修改完毕,点击返回查看！") {
            onBackPressed()
        }.show()

    }
}