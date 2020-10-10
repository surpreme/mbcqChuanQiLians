package com.mbcq.orderlibrary.activity.adddeliverysomething


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.NumberPlateBean
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_add_delivery_some_thing_activity.*
import org.json.JSONObject


/**
 * @author: lzy
 * @time: 2020-09-09 10:44:13 添加送货
 */
@Route(path = ARouterConstants.AddDeliverySomeThingActivity)
class AddDeliverySomeThingActivity : BaseMVPActivity<AddDeliverySomeThingContract.View, AddDeliverySomeThingPresenter>(), AddDeliverySomeThingContract.View {
    override fun getLayoutId(): Int = R.layout.activity_add_delivery_some_thing_activity
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getDeparture()
    }

    fun saveVehicle() {
        if (number_plate_tv.text.toString().isEmpty()) {
            showToast("请选择车牌号")
            return
        }
        if (driver_name_ed.text.toString().isEmpty()) {
            showToast("请输入司机姓名")
            return
        }
        if (contact_number_ed.text.toString().isEmpty()) {
            showToast("请输入司机联系电话")
            return
        }

        val obj = JSONObject()


        obj.put("SendInOneFlag", departure_number_tv.text.toString())//批次号
        obj.put("SendMan", driver_name_ed.text.toString())//送货人
        obj.put("SendManTel", contact_number_ed.text.toString())//电话
        obj.put("SendManMb", contact_number_ed.text.toString())//手机号
        obj.put("SendRemark", remarks_tv.text.toString())//备注
        obj.put("SendVehicleNo", number_plate_tv.text.toString())// 送货车牌号
        obj.put("SendDate", TimeUtils.getCurrTime2())// 送货时间
        obj.put("SenWebCod", UserInformationUtil.getWebIdCode(mContext))// 送货网点
        obj.put("SenWebCodStr", UserInformationUtil.getWebIdCodeStr(mContext))// 送货网点

        val json = GsonUtils.toPrettyFormat(obj.toString())
        ARouter.getInstance().build(ARouterConstants.DeliverySomethingHouseActivity).withString("AddDeliverySomeThing", json).navigation()
        this.finish()
    }

    override fun onClick() {
        super.onClick()
        number_plate_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getVehicles()
            }

        })
        next_step_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                saveVehicle()
            }

        })
        add_delivery_something_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun getDepartureS(s: String) {
        departure_number_tv.text = s

    }

    override fun getVehicleS(result: String) {
        FilterDialog(getScreenWidth(), result, "vehicleno", "选择车牌号", true, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            @SuppressLint("SetTextI18n")
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<NumberPlateBean>(mResult, NumberPlateBean::class.java)
                number_plate_tv.text = mSelectData.vehicleno
                driver_name_ed.setText(mSelectData.chauffer)
                contact_number_ed.setText(mSelectData.chauffermb)
            }

        }).show(supportFragmentManager, "AddDeliverySomeThingActivityGetVehicleSFilterDialog")
    }
}