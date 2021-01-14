package com.mbcq.vehicleslibrary.activity.alldeparturerecord.trunkdeparturehouse


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.core.view.forEachIndexed
import androidx.core.view.get
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean
import kotlinx.android.synthetic.main.activity_add_trunk_departure_house.*
import kotlinx.android.synthetic.main.activity_add_trunk_departure_house.add_operating_interval_btn
import kotlinx.android.synthetic.main.activity_add_trunk_departure_house.all_selected_checked
import kotlinx.android.synthetic.main.activity_add_trunk_departure_house.complete_btn
import kotlinx.android.synthetic.main.activity_add_trunk_departure_house.departure_lot_tv
import kotlinx.android.synthetic.main.activity_add_trunk_departure_house.operating_btn
import kotlinx.android.synthetic.main.activity_add_trunk_departure_house.operating_interval_ll
import kotlinx.android.synthetic.main.activity_add_trunk_departure_house.operating_interval_tv
import kotlinx.android.synthetic.main.activity_add_trunk_departure_house.remove_operating_interval_btn
import kotlinx.android.synthetic.main.activity_fixed_trunk_departure_house.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2018.08.25
 * 干线发车详情操作页
 */
@Route(path = ARouterConstants.TrunkDepartureHouseActivity)
class TrunkDepartureHouseActivity : BaseTrunkDepartureHouseActivity<TrunkDepartureHouseContract.View, TrunkDepartureHousePresenter>(), TrunkDepartureHouseContract.View {
    @Autowired(name = "TrunkDepartureHouse")
    @JvmField
    var mLastDataJson: String = ""
    var mStartWebCode = ""
    var mEndWebCode = ""
    val mOutList = HashMap<String, String>()
    var mMaximumVehicleWeight = 0.0

    override fun getLayoutId(): Int = R.layout.activity_add_trunk_departure_house

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        val mLastData = JSONObject(mLastDataJson)
        mDepartureLot = mLastData.optString("InoneVehicleFlag")
        departure_lot_tv.text = "发车批次: $mDepartureLot"
        mStartWebCode = mLastData.optString("WebidCodeStr")
        mEndWebCode = mLastData.optString("EwebidCodeStr")
        operating_interval_tv.text = "$mStartWebCode-$mEndWebCode"
        mMaximumVehicleWeight = mLastData.optDouble("MaximumVehicleWeight", 0.0)

    }

    /**
     * 完成本车保存
     */
    fun completeCar() {
        mLoadingListAdapter?.getAllData()?.let {
            if (it.isEmpty()) {
                TalkSureDialog(mContext, getScreenWidth(), "请配载您要发的运单").show()
                return
            }
            val mLastData = JSONObject(mLastDataJson)
            val jarray = JSONArray()
            val kk = StringBuilder()

            for ((index, item) in it.withIndex()) {
                val obj = JSONObject()
                obj.put("billno", item.billno)
                obj.put("qty", item.developmentsQty)
                val xV = ((item.volumn.toDouble()) / (item.totalQty.toInt()))
                obj.put("sfVolumn", haveTwoDouble(xV * item.developmentsQty))
                val xW = ((item.weight.toDouble()) / (item.totalQty.toInt()))
                obj.put("sfWeight", haveTwoDouble(xW * item.developmentsQty))
                kk.append(item.billno)
                if (index != it.size - 1)
                    kk.append(",")
                jarray.put(obj)
            }
            mLastData.put("GxVehicleDetLst", jarray)
            mLastData.put("CommonStr", kk.toString())
            mIsCanCloseLoading = false

            mPresenter?.saveInfo(mLastData)
        }

    }

    override fun onClick() {
        super.onClick()
        remove_operating_interval_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                for (index in 0 until operating_interval_ll.childCount) {
                    val itemCheckBox = operating_interval_ll[index] as CheckBox
                    if (itemCheckBox.isChecked) {
                        mOutList.remove(itemCheckBox.text.toString())
                    }
                }
                operating_interval_ll.removeAllViews()
                for (item in mOutList) {
                    val checkBox = CheckBox(mContext)
                    checkBox.text = item.key
                    operating_interval_ll.addView(checkBox)
                }

                refreshShowOut()

            }

        })
        complete_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                TalkSureCancelDialog(mContext, getScreenWidth(), "${if (mMaximumVehicleWeight < mXWeight) "本车已超载，" else ""}您确定要完成本车吗？") {
                    completeCar()
                }.show()
            }

        })
        add_operating_interval_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {

                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        geDeliveryPointLocal(list)
                    }

                })
            }

        })
        operating_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (operating_btn.text.toString() == "添加本车") {
                    addSomeThing()

                } else if (operating_btn.text.toString() == "移出本车") {
                    removeSomeThing()
                }
                short_feeder_house_tabLayout.getTabAt(0)?.text = "库存清单(${mInventoryListAdapter?.getAllData()?.size})"
                short_feeder_house_tabLayout.getTabAt(1)?.text = "配载清单(${mLoadingListAdapter?.getAllData()?.size})"
            }

        })

        all_selected_checked.setOnCheckedChangeListener { _, isChecked ->
            if (mTypeIndex == 1)
                mInventoryListAdapter?.checkedAll(isChecked)
            else if (mTypeIndex == 2)
                mLoadingListAdapter?.checkedAll(isChecked)

        }
    }

    override fun addSomeThing() {
        super.addSomeThing()
        refreshTopInfo()
    }

    override fun removeSomeThing() {
        super.removeSomeThing()
        refreshTopInfo()
    }

    fun refreshShowOut() {
        var showStr = mStartWebCode
        for (item in mOutList) {
            showStr = "$showStr-${item.key}"
        }
        showStr = "$showStr-$mEndWebCode"

        operating_interval_tv.text = showStr
    }

    fun geDeliveryPointLocal(list: MutableList<WebAreaDbInfo>) {
        FilterDialog(getScreenWidth(), Gson().toJson(list), "webid", "选择沿途网点", true, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            @SuppressLint("SetTextI18n")
            override fun onItemClick(v: View, position: Int, mResult: String) {
                var mHas = false
                for (index in 0 until operating_interval_ll.childCount) {
                    val itemCheckBox = operating_interval_ll[index] as CheckBox
                    when {
                        itemCheckBox.text.toString() == list[position].webid -> {
                            mHas = true
                        }
                        itemCheckBox.text.toString() == mStartWebCode -> {
                            mHas = true
                        }
                        itemCheckBox.text.toString() == mEndWebCode -> {
                            mHas = true
                        }
                    }


                }
                when (list[position].webid) {
                    mStartWebCode -> {
                        mHas = true
                    }
                    mEndWebCode -> {
                        mHas = true
                    }
                }
                if (!mHas) {
                    val checkBox = CheckBox(mContext)
                    checkBox.text = list[position].webid
                    mOutList.put(list[position].webid, list[position].webidCode)
                    operating_interval_ll.addView(checkBox)
                    refreshShowOut()


                } else {
                    showToast("您已经选过${list[position].webid}了")
                }

            }

        }).show(supportFragmentManager, "BaseTrunkDepartureHouseActivitygeDeliveryPointLocalDialogFilterDialog")
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getInventory(1)
    }

    override fun getInventoryS(list: List<StockWaybillListBean>) {
        short_feeder_house_tabLayout.getTabAt(0)?.text = "库存清单(${list.size})"
        mInventoryListAdapter?.appendData(list)
    }

    override fun saveInfoS(s: String) {
        operating_interval_ll.visibility = View.GONE
        if (operating_interval_ll.childCount == 0) {
            TalkSureCancelDialog(mContext, getScreenWidth(), "干线计划装车${mDepartureLot}配载成功，您确定要完成本车吗?") {
                mPresenter?.overLocalCar(mDepartureLot)
            }.show()
            return
        }

        val itemCheckBox = operating_interval_ll[0] as CheckBox
        mOutList[itemCheckBox.text.toString()]?.let { mPresenter?.addStowageAlongWay(mDepartureLot, it, itemCheckBox.text.toString(), mOutList, false) }


    }

    override fun overLocalCarS(s: String) {
        TalkSureDialog(mContext, getScreenWidth(), "干线计划装车${mDepartureLot}已完成，点击查看详情！") {
            onBackPressed()
        }.show()
    }

    override fun addStowageAlongWayS(inoneVehicleFlag: String, webidCode: String, webidCodeStr: String, result: String, datalist: HashMap<String, String>, isOver: Boolean) {
        if (operating_interval_ll.getChildAt(0) == null) return
        operating_interval_ll.removeViewAt(0)
        if (operating_interval_ll.getChildAt(0) == null) {
            closeLoading()
            TalkSureCancelDialog(mContext, getScreenWidth(), "干线计划装车${mDepartureLot}配载成功，您确定要完成本车吗？") {
                mPresenter?.overLocalCar(inoneVehicleFlag)
            }.show()
            return
        }
        val itemCheckBox = operating_interval_ll[0] as CheckBox
        mOutList.get(itemCheckBox.text.toString())?.let { mPresenter?.addStowageAlongWay(mDepartureLot, it, itemCheckBox.text.toString(), mOutList, false) }


    }


}