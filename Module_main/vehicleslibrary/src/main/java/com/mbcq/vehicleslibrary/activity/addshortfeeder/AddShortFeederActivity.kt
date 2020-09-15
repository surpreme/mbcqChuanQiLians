package com.mbcq.vehicleslibrary.activity.addshortfeeder


import android.annotation.SuppressLint
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_add_short_feeder.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-14 13:42:27
 * 添加短驳发车 运输方式 update
 */
@Route(path = ARouterConstants.AddShortFeederActivity)
class AddShortFeederActivity : BaseAddShortFeederActivity<AddShortFeederContract.View, AddShortFeederPresenter>(), AddShortFeederContract.View {
    override fun getLayoutId(): Int = R.layout.activity_add_short_feeder


    override fun initDatas() {
        super.initDatas()
        mPresenter?.getDepartureBatchNumber()
    }


    override fun onClick() {
        super.onClick()
        next_step_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                val obj = JSONObject()
                obj.put("inOneVehicleFlag", contract_No_tv.text.toString())
                val json = GsonUtils.toPrettyFormat(obj.toString())
                ARouter.getInstance().build(ARouterConstants.ShortFeederHouseActivity).withString("ShortFeederHouse",json).navigation()
            }

        })

        cash_freight_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                showHideCashFreight()
            }

        })
        freight_onarrival_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                showHideFreightOnArrival()

            }

        })
        number_plate_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getVehicles()
            }

        })
        oil_card_first_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getDbWebId(object : WebDbInterface {
                    override fun isNull() {
                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        geDeliveryPointLocal(list, 1)
                    }

                })

            }

        })
        oil_card_second_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getDbWebId(object : WebDbInterface {
                    override fun isNull() {
                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        geDeliveryPointLocal(list, 2)
                    }

                })
            }

        })
        oil_card_third_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getDbWebId(object : WebDbInterface {
                    override fun isNull() {
                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        geDeliveryPointLocal(list, 3)
                    }

                })
            }

        })
        destination_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getDbWebId(object : WebDbInterface {
                    override fun isNull() {
                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        geDeliveryPointLocal(list, 0)
                    }

                })
            }

        })
        add_short_feeder_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }


    override fun getDepartureBatchNumberS(inOneVehicleFlag: String) {
        if (inOneVehicleFlag.isBlank()) {
            TalkSureDialog(mContext, getScreenWidth(), "获取合同编号失败 点击确认退出") {
                onBackPressed()
            }.show()
            return
        }
        contract_No_tv.text = inOneVehicleFlag
    }

    override fun getVehicleS(result: String) {
        FilterDialog(getScreenWidth(), result, "vehicleno", "选择车牌号", true, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            @SuppressLint("SetTextI18n")
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<NumberPlateBean>(mResult, NumberPlateBean::class.java)
                number_plate_tv.text = mSelectData.vehicleno
                driver_name_ed.setText(mSelectData.chauffer)
                contact_number_ed.setText(mSelectData.chauffermb)
                vehicle_type_tv.text = if (mSelectData.vehicletype == 1) "大车" else if (mSelectData.vehicletype == 2) "小车" else "未知车型"
                on_board_weight_tv.text = "${mSelectData.supweight}吨"
            }

        }).show(supportFragmentManager, "getVehicleSFilterDialog")
    }


}