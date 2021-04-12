package com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouse


import android.annotation.SuppressLint
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.view.MoneyInputFilter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.alllocalagent.event.LocalGentShortFeederHouseEvent
import kotlinx.android.synthetic.main.activity_local_gent_short_feeder_house.*
import org.greenrobot.eventbus.EventBus
import org.json.JSONArray
import org.json.JSONObject


/**
 * @author: lzy
 * @time: 2020-09-22 17:13:27
 * 中转出库 中转公司Transit company
 */
@Route(path = ARouterConstants.LocalGentShortFeederHouseActivity)
class LocalGentShortFeederHouseActivity : BaseLocalGentShortFeederHouseActivity<LocalGentShortFeederHouseContract.View, LocalGentShortFeederHousePresenter>(), LocalGentShortFeederHouseContract.View {
    @Autowired(name = "LocalGentShortFeederHouse")
    @JvmField
    var mLastDataJson: String = ""


    override fun getLayoutId(): Int = R.layout.activity_local_gent_short_feeder_house

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        val mLastData = JSONObject(mLastDataJson)
        mDepartureLot = mLastData.optString("AgentBillno")
        dispatch_number_tv.text = "派车单号: $mDepartureLot"
        share_delivery_costs_ed.filters = arrayOf<InputFilter>(MoneyInputFilter())
    }

    fun saveV() {
        mLoadingListAdapter?.getAllData()?.let {
            if (it.isEmpty())
                return
            val mLastData = JSONObject(mLastDataJson)
            val jarray = JSONArray()
            val kk = StringBuilder()

            for ((index, item) in it.withIndex()) {
                val obj = JSONObject(Gson().toJson(item))
//                obj.put("billno", item.billno)
                kk.append(item.billno)
                if (index != it.size - 1)
                    kk.append(",")
                jarray.put(obj)
            }
            mLastData.put("WaybillAgentDetLst", jarray)
            mLastData.put("CommonStr", kk.toString())
            mPresenter?.completeVehicle(mLastData)
        }
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getInventory()
        mPresenter?.getTransitCompany(UserInformationUtil.getWebIdCode(mContext))
    }


    override fun initLoadingList() {
        super.initLoadingList()
        mLoadingListAdapter?.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                if (mtTransitCompanyJsonStr.isBlank()) {
                    showToast("初始化失败")
                    return
                }
                val jay = JSONArray(mtTransitCompanyJsonStr)
                val mShowCList = mutableListOf<BaseTextAdapterBean>()
                for (index in 0 until jay.length()) {
                    val mBaseTextAdapterBean = BaseTextAdapterBean(jay.getJSONObject(index).optString("caruniname") + "  " + jay.getJSONObject(index).optString("mb") + "  " + jay.getJSONObject(index).optString("mainroute"), GsonUtils.toPrettyFormat(jay.getJSONObject(index)))
                    mShowCList.add(mBaseTextAdapterBean)
                }
                val mLocalGentShortFeederHouseTransitCompanyDialog = LocalGentShortFeederHouseTransitCompanyConfigurationDialog(mShowCList,mResult).also {
                    it.mOnClickInterface = object : OnClickInterface.OnClickInterface {
                        override fun onResult(s1: String, s2: String) {
                            val obj = JSONObject(mResult)
                            val mDataObj = JSONObject(s1)
                            obj.put("outcygs", mDataObj.optString("outcygs"))
                            obj.put("outacc", mDataObj.optString("outacc"))
                            obj.put("outbillno", mDataObj.optString("outbillno"))
                            obj.put("contactmb", mDataObj.optString("contactmb"))
                            mLoadingListAdapter?.notifyItemChangeds(position, Gson().fromJson(GsonUtils.toPrettyFormat(obj), LocalGentShortFeederHouseBean::class.java))
                        }

                    }
                }
                mLocalGentShortFeederHouseTransitCompanyDialog.show(supportFragmentManager, "LocalGentShortFeederHouseTransitCompanyDialog$position")
            }

        }
    }


    override fun onClick() {
        super.onClick()
        share_delivery_costs_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (share_delivery_costs_ed.text.toString().isBlank()) {
                    showToast("请输入送货费")
                    return
                }
                mLoadingListAdapter?.getAllData()?.let {
                    if (it.isEmpty()) {
                        showToast("请选择需要中转的运单")
                        return
                    }
                }

                val showDataList = mutableListOf<ShareDeliveryCostsBean>()
                for (index in 0..3) {
                    val mShareDeliveryCostsBean = ShareDeliveryCostsBean()
                    when (index) {
                        0 -> {
                            mShareDeliveryCostsBean.showStr = "按所单所占件数比例分摊"
                        }
                        1 -> {
                            mShareDeliveryCostsBean.showStr = "按票平均分摊"

                        }
                        2 -> {
                            mShareDeliveryCostsBean.showStr = "按该单所占重量比例分担"

                        }
                        3 -> {
                            mShareDeliveryCostsBean.showStr = "按该单所占体积比例分担"

                        }
                    }
                    mShareDeliveryCostsBean.index = index
                    showDataList.add(mShareDeliveryCostsBean)
                }
                FilterDialog(getScreenWidth(), Gson().toJson(showDataList), "showStr", "选择分摊送货费方式", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                    @SuppressLint("SetTextI18n")
                    override fun onItemClick(v: View, position: Int, mResult: String) {
                        val resultObj = JSONObject(mResult)
                        val mCalculationMoney: Double = share_delivery_costs_ed.text.toString().toDouble()
                        val mOldData = mLoadingListAdapter?.getAllData()
                        val mNewData = mutableListOf<LocalGentShortFeederHouseBean>()
                        mOldData?.let {
                            when (resultObj.optString("showStr")) {
                                "按所单所占件数比例分摊" -> {
                                    val toltal = mCalculationMoney / mTotalQty
                                    for (item in it) {
                                        item.outacc = haveTwoDouble(toltal * item.qty.toInt())
                                        mNewData.add(item) //accsendout
                                    }

                                }
                                "按票平均分摊" -> {
                                    val toltal = mCalculationMoney / mOldData.size
                                    for (item in it) {
                                        item.outacc = haveTwoDouble(toltal)
                                        mNewData.add(item) //accsendout
                                    }

                                }
                                "按该单所占重量比例分担" -> {
                                    val toltal = mCalculationMoney / mToTalWeight
                                    for (item in it) {
                                        item.outacc =haveTwoDouble (toltal * item.weight.toDouble())
                                        mNewData.add(item) //accsendout
                                    }

                                }

                                "按该单所占体积比例分担" -> {
                                    val toltal = mCalculationMoney / mToTalVolume
                                    for (item in it) {
                                        item.outacc =haveTwoDouble (toltal * item.volumn.toDouble())
                                        mNewData.add(item) //accsendout
                                    }

                                }


                            }
                        }

                        mLoadingListAdapter?.replaceData(mNewData)

                    }

                }).show(supportFragmentManager, "ShareDeliveryCostsFilterDialog")
            }

        })
        complete_vehicle_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要完成${dispatch_number_tv.text}吗") {
                    saveV()
                }.show()
            }

        })
        all_selected_checked.setOnCheckedChangeListener { _, isChecked ->
            if (mTypeIndex == 1)
                mInventoryListAdapter?.checkedAll(isChecked)
            else if (mTypeIndex == 2)
                mLoadingListAdapter?.checkedAll(isChecked)

        }
        operating_btn.setOnClickListener(object : SingleClick() {
            @SuppressLint("SetTextI18n")
            override fun onSingleClick(v: View?) {
                if (operating_btn.text.toString() == "添加本车") {
                    addSomeThing()

                } else if (operating_btn.text.toString() == "移出本车") {
                    removeSomeThing()
                }
                showTopTotal()
            }

        })
        inventory_list_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                selectIndex(1)
            }

        })
        loading_list_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                selectIndex(2)
            }

        })
    }

    @SuppressLint("SetTextI18n")
    override fun getInventoryS(list: List<LocalGentShortFeederHouseBean>) {
        mInventoryListAdapter?.appendData(list)
        inventory_list_tv.text = "库存清单(${list.size})"

    }

    override fun completeVehicleS() {
        TalkSureDialog(mContext, getScreenWidth(), "${dispatch_number_tv.text}已完成出库") {
            EventBus.getDefault().postSticky(LocalGentShortFeederHouseEvent(JSONObject(mLastDataJson).optInt("mTypeS")))
            onBackPressed()
        }.show()
    }

    var mtTransitCompanyJsonStr = ""
    override fun getTransitCompanyS(result: String) {
        mtTransitCompanyJsonStr = result
    }

}