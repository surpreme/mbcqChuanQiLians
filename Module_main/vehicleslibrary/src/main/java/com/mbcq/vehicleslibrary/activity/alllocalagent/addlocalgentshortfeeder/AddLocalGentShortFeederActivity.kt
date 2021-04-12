package com.mbcq.vehicleslibrary.activity.alllocalagent.addlocalgentshortfeeder


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
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
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.vehicleslibrary.R
import com.mbcq.commonlibrary.NumberPlateBean
import kotlinx.android.synthetic.main.activity_add_local_gent_short_feeder.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-22 15:48 新增外转出库配置
 */

@Route(path = ARouterConstants.AddLocalGentShortFeederActivity)
class AddLocalGentShortFeederActivity : BaseMVPActivity<AddLocalGentShortFeederContract.View, AddLocalGentShortFeederPresenter>(), AddLocalGentShortFeederContract.View {
    @Autowired(name = "AddLocalGentShortFeeder")
    @JvmField
    var mRefreshTypeS: String = ""

    override fun getLayoutId(): Int = R.layout.activity_add_local_gent_short_feeder
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getDepartureBatchNumber()

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
        obj.put("WebidCode", UserInformationUtil.getWebIdCode(mContext))//出库网点编码
        obj.put("WebidCodeStr", UserInformationUtil.getWebIdCodeStr(mContext))// 出库网点
        obj.put("AgentBillno", dispatch_number_tv.text.toString())//发车批次号
        obj.put("AgentDate", TimeUtils.getCurrTime2())//发车日期
        obj.put("VehcileNo", number_plate_tv.text.toString())// 车牌号
        obj.put("VehicleNoTmp", temporary_vehicle_ed.text.toString())// 临时车辆
        obj.put("VehicleType", vehicle_type_ed.text.toString())// 车型
        obj.put("Chauffer", driver_name_ed.text.toString())// 司机
        obj.put("ChaufferTel", contact_number_ed.text.toString())// 司机电话
        obj.put("AgentAccSend", contact_number_ed.text.toString())// 中转送货费
        obj.put("Remark", remark_ed.text.toString())// 备注
        obj.put("AgentType", 1)// 代理类型编码
        obj.put("AgentTypeStr", "本地代理")// 代理类型
        obj.put("mTypeS", mRefreshTypeS)// 按车按票刷新的种类 提供添加成功刷新


        val json = GsonUtils.toPrettyFormat(obj.toString())
        ARouter.getInstance().build(ARouterConstants.LocalGentShortFeederHouseActivity).withString("LocalGentShortFeederHouse", json).navigation()
        this.finish()
    }

    override fun onClick() {
        super.onClick()

        next_step_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                saveVehicle()
            }

        })
        number_plate_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getVehicles()

            }

        })
        add_local_short_feeder_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }


    override fun getDepartureBatchNumberS(inOneVehicleFlag: String) {
        dispatch_number_tv.text = inOneVehicleFlag
    }

    override fun getVehicleS(result: String) {
        FilterDialog(getScreenWidth(), result, "vehicleno", "选择车牌号", true, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            @SuppressLint("SetTextI18n")
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<NumberPlateBean>(mResult, NumberPlateBean::class.java)
                number_plate_tv.text = mSelectData.vehicleno
                driver_name_ed.setText(mSelectData.chauffer)
                contact_number_ed.setText(mSelectData.chauffermb)
                vehicle_type_ed.setText(if (mSelectData.vehicletype == 1) "大车" else if (mSelectData.vehicletype == 2) "小车" else "未知车型")
            }

        }).show(supportFragmentManager, "AddLocalGentShortFeederActivitygetVehicleSFilterDialog")
    }
}