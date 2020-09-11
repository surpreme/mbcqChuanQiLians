package com.mbcq.orderlibrary.activity.addreceiver


import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_add_receiver.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2018.08.25
 * 添加收货人
 */

@Route(path = ARouterConstants.AddReceiverActivity)
class AddReceiverActivity : BaseMVPActivity<AddReceiverContract.View, AddReceiverPresenter>(), AddReceiverContract.View {
    private val RECEIVER_RESULT_DATA_CODE = 4439

    override fun getLayoutId(): Int = R.layout.activity_add_receiver
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        /**
         * 监听editttext焦点
         */
        phone_ed.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (phone_ed.text.toString().isNotEmpty()) {
                    mPresenter?.getReceiverInfo(phone_ed.text.toString())
                }
            }
        }
    }


    override fun onClick() {
        super.onClick()
        add_receiver_toolbar.setBackButtonOnClickListener {
            onBackPressed()
        }
        sure_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (name_ed.text.toString().isNotEmpty() && phone_ed.text.toString().isNotEmpty() && address_ed.text.toString().isNotEmpty()) {
                    val obj = JSONObject()
                    obj.put("name", name_ed.text.toString())
                    obj.put("phone", phone_ed.text.toString())
                    obj.put("address", address_ed.text.toString())
                    obj.put("consigneeTel", mConsigneeTel_ed.text.toString())
                    val json = GsonUtils.toPrettyFormat(obj.toString())
                    setResult(RECEIVER_RESULT_DATA_CODE, Intent().putExtra("AddReceiveResultData", json))
                    finish()
                } else
                    showToast("请检查您输入的信息")

            }

        })
    }

    override fun getReceiverInfoS(result: String) {
        val titleList = mutableListOf<String>()
        titleList.add("opeMan")
        titleList.add("contactMb")
        titleList.add("address")
        val startList = mutableListOf<String>()
        startList.add("姓名:")
        startList.add("电话:")
        startList.add("地址:")
        FilterDialog(getScreenWidth(), result, titleList, startList, "\n", "选择收货人", false, isShowOutSide = false, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mAddReceiverBean = Gson().fromJson<AddReceiverBean>(mResult, AddReceiverBean::class.java)
                mAddReceiverBean?.let {
                    address_ed.setText(it.address)
                    name_ed.setText(it.opeMan)
                }

            }

        }).show(supportFragmentManager, "AddReceiverActivityFilterDialog")
    }
}