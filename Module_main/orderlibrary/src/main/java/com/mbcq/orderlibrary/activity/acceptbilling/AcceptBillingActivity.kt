package com.mbcq.orderlibrary.activity.acceptbilling


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.RadioGroupUtil
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_accept_billing.*
import org.json.JSONArray
import org.json.JSONObject


/**
 * @author: lzy
 * @time: 2018.08.25login
 * 受理开单
 */

@Route(path = ARouterConstants.AcceptBillingActivity)
class AcceptBillingActivity : BaseAcceptBillingActivity<AcceptBillingContract.View, AcceptBillingPresenter>(), AcceptBillingContract.View {

    /**
     * 到达网点
     */
    var endWebIdCode = ""

    /**
     * 目的地
     */
    var destinationt = ""

    override fun getLayoutId(): Int = R.layout.activity_accept_billing
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getWaybillNumber()
        mPresenter?.getPaymentMode()
        mPresenter?.getTransportMode()

        bank_number_ed.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                if (bank_number_ed.text.toString().isNotEmpty()) {
                    mPresenter?.getCardNumberCondition(bank_number_ed.text.toString())
                }
            }
        }
    }


    /**
     * ARouter 度娘
     * {"nameValuePairs":{"name":"xxxx","phone":"15999999999","address":"1111"}}
     */
    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RESULT_DATA_CODE) {
            (data?.getStringExtra("AddShipperResultData"))?.let {
                val mDatas = JSONObject(it)
                val timi = mDatas.optJSONObject("nameValuePairs")
                timi?.let {
                    add_shipper_tv.text = "${timi.optString("name")} ${timi.optString("phone")} \n${timi.optString("address")} "
                }


            }
        } else if (requestCode == RECEIVER_RESULT_DATA_CODE) {
            (data?.getStringExtra("AddReceiveResultData"))?.let {
                val mDatas = JSONObject(it)
                val timi = mDatas.optJSONObject("nameValuePairs")
                timi?.let {
                    add_receiver_tv.text = "${timi.optString("name")} ${timi.optString("phone")} \n${timi.optString("address")} "
                }


            }
        }
    }

    override fun onClick() {
        super.onClick()
        receipt_requirements_name_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getReceiptRequirement()
            }

        })
        receipt_requirements_name_down_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getReceiptRequirement()
            }

        })
        package_name_down_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getPackage()

            }

        })
        cargo_name_down_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getCargoAppellation()

            }

        })
        add_shipper_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.AddShipperActivity).navigation(this@AcceptBillingActivity, RESULT_DATA_CODE)

            }

        })
        add_receiver_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.AddReceiverActivity).navigation(this@AcceptBillingActivity, RECEIVER_RESULT_DATA_CODE)

            }

        })
        destinationt_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (endWebIdCode.isEmpty()) {
                    showToast("请先选择到达网点")
                    return
                }
                mPresenter?.getDestination(UserInformationUtil.getWebIdCode(mContext), endWebIdCode)
            }

        })
        arrive_outlet_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getDbWebId(object : WebDbInterface {
                    override fun isNull() {
                        mPresenter?.getWebAreaId()
                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        showWebIdDialog(list)
                    }

                })
            }

        })
        get_delivery_mention_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                initDeliveryMethod(1)
            }

        })
        get_delivery_home_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                initDeliveryMethod(2)
            }

        })
        get_driver_direct_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                initDeliveryMethod(3)
            }

        })
        customer_mention_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                initReceivingMethod(1)
            }

        })
        home_delivery_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                initReceivingMethod(2)
            }

        })
        location_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getLocation()
            }

        })

        waybill_number_handwriting_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                waybillNumber(true)
            }

        })
        waybill_number_machine_printed_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                waybillNumber(false)
            }

        })
    }


    override fun getWaybillNumberS(result: String) {
        waybill_number_ed.setText(result)

    }

    override fun getDestinationS(result: String) {
        FilterDialog(getScreenWidth(), result, "mapDes", "选择目的地", true, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<DestinationtBean>(mResult, DestinationtBean::class.java)
                destinationt_tv.text = mSelectData.mapDes
                destinationt = result
            }

        }).show(supportFragmentManager, "getDestinationSFilterDialog")
    }

    override fun getCargoAppellationS(result: String) {
        FilterDialog(getScreenWidth(), result, "product", "货物名称", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<CargoAppellationBean>(mResult, CargoAppellationBean::class.java)
                cargo_name_ed.setText(mSelectData.product)
            }

        }).show(supportFragmentManager, "getCargoAppellationSFilterDialog")
    }

    override fun getPackageS(result: String) {
        FilterDialog(getScreenWidth(), result, "packages", "包装", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<AcceptPackageBean>(mResult, AcceptPackageBean::class.java)
                package_name_ed.setText(mSelectData.packages)
            }

        }).show(supportFragmentManager, "getPackagesFilterDialog")

    }

    override fun getCardNumberConditionS(result: String) {
        val mAcceptMemberInfoBean = Gson().fromJson<AcceptMemberInfoBean>(result, AcceptMemberInfoBean::class.java)
        if (mAcceptMemberInfoBean != null) {
            account_names_tv.text = mAcceptMemberInfoBean.shipper
            mPresenter?.getBankCardInfo(mAcceptMemberInfoBean.vipBankId)
        }
    }

    /**
     * 清空输入会员卡号的信息
     */
    override fun getCardNumberConditionNull() {
        account_names_tv.text = ""
        account_bank_tv.text = ""
        bank_number_tv.text = ""
    }

    override fun getBankCardInfoS(result: String) {
        val dataslist = Gson().fromJson<List<AcceptBankInfoBean>>(result, object : TypeToken<List<AcceptBankInfoBean>>() {}.type)
        if (dataslist.isNullOrEmpty()) return
        account_bank_tv.text = dataslist[0].bankName
        bank_number_tv.text = dataslist[0].bankNumber

    }

    override fun getReceiptRequirementS(result: String) {
        FilterDialog(getScreenWidth(), result, "tdescribe", "回单要求", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<AcceptReceiptRequirementBean>(mResult, AcceptReceiptRequirementBean::class.java)
                receipt_requirements_name_tv.text = mSelectData.tdescribe
            }

        }).show(supportFragmentManager, "getReceiptRequirementSFilterDialog")
    }

    override fun getTransportModeS(result: String) {
        val mTransportModeArray = JSONArray(result)
        for (mIndex in 0 until mTransportModeArray.length()) {
            val obj = mTransportModeArray.optJSONObject(mIndex)
            obj?.let {
                transport_method_rg.addView(RadioGroupUtil.addSelectItem(mContext, it.optString("tdescribe"), mIndex))
            }
        }
        transport_method_rg.check(0)


    }


    override fun getPaymentModeS(result: String) {
        val mPaymentModeArray = JSONArray(result)
        for (mIndex in 0 until mPaymentModeArray.length()) {
            val obj = mPaymentModeArray.optJSONObject(mIndex)
            obj?.let {
                pay_way_title_rg.addView(RadioGroupUtil.addSelectItem(mContext, it.optString("tdescribe"), mIndex))
            }
        }
        pay_way_title_rg.check(0)
    }


    private fun showWebIdDialog(list: MutableList<WebAreaDbInfo>) {
        FilterDialog(getScreenWidth(), Gson().toJson(list), "webid", "选择到货网点", true, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                arrive_outlet_tv.text = list[position].webid
                destinationt_tv.text = ""
                destinationt = ""
                endWebIdCode = list[position].webidCode
            }

        }).show(supportFragmentManager, "showWebIdDialogFilterDialog")
    }

}