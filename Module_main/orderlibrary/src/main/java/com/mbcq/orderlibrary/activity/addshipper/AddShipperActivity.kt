package com.mbcq.orderlibrary.activity.addshipper


import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.adapter.BaseAdapterBean
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.activity.addreceiver.AddReceiverBean
import kotlinx.android.synthetic.main.activity_add_receiver.*
import kotlinx.android.synthetic.main.activity_add_shipper.*
import kotlinx.android.synthetic.main.activity_add_shipper.address_ed
import kotlinx.android.synthetic.main.activity_add_shipper.name_ed
import kotlinx.android.synthetic.main.activity_add_shipper.phone_ed
import kotlinx.android.synthetic.main.activity_add_shipper.sure_btn
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020.09.24
 * 添加发货人
 */
@Route(path = ARouterConstants.AddShipperActivity)
class AddShipperActivity : BaseMVPActivity<AddShipperContract.View, AddShipperPresenter>(), AddShipperContract.View {
    private val RESULT_DATA_CODE = 5848
    override fun getLayoutId(): Int = R.layout.activity_add_shipper
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        /**
         * 监听editttext焦点
         */
        phone_ed.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (phone_ed.text.toString().isNotEmpty()) {
                    mPresenter?.getShipperInfo(phone_ed.text.toString())
                }
            }
        }
    }


    override fun onClick() {
        super.onClick()
        add_shipper_toolbar.setBackButtonOnClickListener {
            onBackPressed()
        }
        sure_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (name_ed.text.toString().isNotEmpty() && phone_ed.text.toString().isNotEmpty() && address_ed.text.toString().isNotEmpty()) {
//                    val datas = "${name_ed.text}@${phone_ed.text}@${address_ed.text}"
                    val obj=JSONObject()
                    obj.put("name",name_ed.text.toString())
                    obj.put("phone",phone_ed.text.toString())
                    obj.put("address",address_ed.text.toString())
                    obj.put("shipperTel",mShipperTel_ed.text.toString())
                    obj.put("shipperCid",mShipperCid_ed.text.toString())
                    obj.put("shipperId",mShipperId_ed.text.toString())
                    val json=GsonUtils.toPrettyFormat(obj.toString())
                    setResult(RESULT_DATA_CODE, Intent().putExtra("AddShipperResultData", json))
                    finish()
                } else
                    showToast("请检查您输入的信息")

            }

        })
    }

    override fun getShipperInfoS(result: String) {
        val titleList = mutableListOf<String>()
        titleList.add("opeMan")
        titleList.add("contactMb")
        titleList.add("address")
        val startList = mutableListOf<String>()
        startList.add("姓名:")
        startList.add("电话:")
        startList.add("地址:")
        FilterDialog(getScreenWidth(), result, titleList, startList, "\n", "选择发货人", false, isShowOutSide = false, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mAddShipperBean = Gson().fromJson<AddShipperBean>(mResult, AddShipperBean::class.java)
                mAddShipperBean?.let {
                    address_ed.setText(it.address)
                    name_ed.setText(it.opeMan)
                }

            }

        }).show(supportFragmentManager, "AddShipperActivityFilterDialog")

    }
}