package com.mbcq.vehicleslibrary.activity.alldeparturerecord.fixtrunkdeparturehouse


import android.annotation.SuppressLint
import android.graphics.Color
import android.view.View
import android.widget.CheckBox
import androidx.core.view.get
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean
import com.mbcq.vehicleslibrary.fragment.shortfeederhouse.inventorylist.ShortFeederHouseInventoryListAdapter
import com.mbcq.vehicleslibrary.fragment.shortfeederhouse.loadinglist.ShortFeederHouseLoadingListAdapter
import kotlinx.android.synthetic.main.activity_fixed_trunk_departure_house.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-18 11:33
 * 修改 干线发车
 */
@Route(path = ARouterConstants.FixedTrunkDepartureHouseActivity)
class FixedTrunkDepartureHouseActivity : BaseFixedTrunkDepartureHouseActivity<FixedTrunkDepartureHouseContract.View, FixedTrunkDepartureHousePresenter>(), FixedTrunkDepartureHouseContract.View {
    @Autowired(name = "FixedTrunkDepartureHouse")
    @JvmField
    var mFixDataJson: String = ""
    var mStartWebCode = ""
    var mEndWebCode = ""
    val mOutList = HashMap<String, String>()

    override fun getLayoutId(): Int = R.layout.activity_fixed_trunk_departure_house

    @SuppressLint("SetTextI18n")
    override fun initDatas() {
        super.initDatas()
        val mLastData = JSONObject(mFixDataJson)
        mInoneVehicleFlag = mLastData.optString("InoneVehicleFlag")
        departure_lot_tv.text = "发车批次: $mInoneVehicleFlag"
        mIsCanCloseLoading = false
        mPresenter?.getCarInfo(mLastData.optInt("Id"), mLastData.optString("InoneVehicleFlag"))
        mPresenter?.getInventory(1)
        mPresenter?.getStowageAlongWay(mLastData.optString("InoneVehicleFlag"), mLastData.optInt("Id"))

    }

    override fun invalidOrderS() {
        TalkSureDialog(mContext, getScreenWidth(), "已完成作废${departure_lot_tv.text}，给您带来不便敬请原谅！") {
            onBackPressed()
        }.show()

    }

    override fun removeOrderItemS(position: Int, item: StockWaybillListBean) {
        mLoadingListAdapter?.removeItem(position)
        mInventoryListAdapter?.appendData(mutableListOf(item))
        refreshTopNumber()
        invalidateCar()
    }

    override fun addOrderItemS(position: Int, item: StockWaybillListBean) {
        mInventoryListAdapter?.removeItem(position)
        mLoadingListAdapter?.appendData(mutableListOf(item))
        refreshTopNumber()
    }

    override fun completeCarS() {
        TalkSureDialog(mContext, getScreenWidth(), "完成${departure_lot_tv.text}干线发车成功") {
            onBackPressed()
        }.show()
    }

    override fun addStowageAlongWayS(inoneVehicleFlag: String, webidCode: String, webidCodeStr: String, result: String) {
        val checkBox = CheckBox(mContext)
        checkBox.text = webidCodeStr
        mOutList.put(webidCodeStr, webidCode)
        operating_interval_ll.addView(checkBox)
        refreshShowOut()
    }

    override fun getStowageAlongWayS(list: List<FixedStowageAlongWayBean>) {
        for (item in list) {
            val checkBox = CheckBox(mContext)
            checkBox.text = item.webidCodeStr
            operating_interval_ll.addView(checkBox)
            mOutList.put(item.webidCodeStr, item.webidCode.toString())

        }
        mIsCanCloseLoading = true
        closeLoading()
    }

    override fun deleteStowageAlongWay(result: String) {

    }


    override fun initLoadingList() {
        super.initLoadingList()
        mLoadingListAdapter?.mOnRemoveInterface = object : ShortFeederHouseLoadingListAdapter.OnRemoveInterface {
            override fun onClick(position: Int, item: StockWaybillListBean) {
                val mLastData = JSONObject(mFixDataJson)
                mPresenter?.removeOrderItem(item.billno, mLastData.optString("Id"), mLastData.optString("InoneVehicleFlag"), position, item)
            }

        }
    }

    override fun initInventoryList() {
        super.initInventoryList()
        mInventoryListAdapter?.mOnRemoveInterface = object : ShortFeederHouseInventoryListAdapter.OnRemoveInterface {
            override fun onClick(position: Int, item: StockWaybillListBean) {
                val mLastData = JSONObject(mFixDataJson)
                mPresenter?.addOrderItem(item.billno, mLastData.optString("Id"), mLastData.optString("InoneVehicleFlag"), position, item)
            }
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
                val mLastData = JSONObject(mFixDataJson)
                if (operating_btn.text.toString() == "添加本车") {
                    getSelectInventoryList()?.let {
                        if (it.isEmpty()) {
                            showToast("请至少选择一票进行添加！")
                            return
                        }
                        mPresenter?.addOrder(getSelectInventoryOrder(), mLastData.optString("Id"), mLastData.optString("InoneVehicleFlag"), it)
                    }
                } else if (operating_btn.text.toString() == "移出本车") {
                    if (getSelectLoadingOrderItem() == 0) {
                        showToast("请至少选择一票进行移出！")
                        return
                    }
                    mPresenter?.removeOrder(getSelectLoadingOrder(), mLastData.optString("Id"), mLastData.optString("InoneVehicleFlag"))
                }

            }

        })

        complete_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                val modifyData = JSONObject(mFixDataJson)
                if (complete_btn.text == "取消完成本车") {
                    TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要取消完成本车${departure_lot_tv.text}吗？") {
                        mPresenter?.modify(modifyData)
                    }.show()
                } else if (complete_btn.text == "完成本车") {
                    if (!invalidateCar())
                        return
//                    mPresenter?.deleteStowageAlongWay(mInoneVehicleFlag)

                    mPresenter?.completeCar(modifyData.optInt("Id"), modifyData.optString("InoneVehicleFlag"))
                }
            }

        })

    }

    private fun geDeliveryPointLocal(list: MutableList<WebAreaDbInfo>) {
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

                    mPresenter?.addStowageAlongWay(mInoneVehicleFlag, list[position].webidCode, list[position].webid)

                } else {
                    showToast("您已经选过${list[position].webid}了")
                }

            }

        }).show(supportFragmentManager, "FixedTrunkDepartureHouseDialogFilterDialog")
    }

    fun refreshShowOut() {
        var showStr = mStartWebCode
        /*   for (item in mOutList) {
               showStr = "$showStr-${item.key}"
           }
           showStr = "$showStr-$mEndWebCode"*/
        for (index in 0 until operating_interval_ll.childCount) {
            val itemCheckBox = operating_interval_ll[index] as CheckBox
            showStr = "$showStr-${itemCheckBox.text}"
        }
        showStr = "$showStr-$mEndWebCode"
        operating_interval_tv.text = showStr
    }

    override fun addOrderS() {
        addSomeThing()
        refreshTopNumber()
    }

    fun invalidateCar(): Boolean {
        mLoadingListAdapter?.getAllData()?.let {
            if (it.isEmpty()) {
                val modifyData = JSONObject(mFixDataJson)
                TalkSureCancelDialog(mContext, getScreenWidth(), "您${departure_lot_tv.text}的配载为0，您是否需要作废本车？") {
                    mPresenter?.invalidOrder(modifyData.optString("InoneVehicleFlag"), modifyData.optInt("Id"))
                }.show()
                return false
            }
            return true
        }
        return true
    }

    override fun removeOrderS() {
        removeSomeThing()
        refreshTopNumber()
        invalidateCar()

    }

    override fun getCarInfo(result: FixedTrunkDepartureHouseInfo) {
        mLoadingListAdapter?.appendData(result.item)
        fix_trunk_departure_house_tabLayout.getTabAt(1)?.text = "配载清单(${result.item.size})"
        mStartWebCode = result.webidCodeStr
        mEndWebCode = result.ewebidCodeStr
        operating_interval_tv.text = result.vehicleInterval
        if (result.vehicleState == "1") {
            complete_btn.text = "取消完成本车"
            /**
             * 发货中不可以修改沿途网点
             */
            add_operating_interval_btn.isClickable = false
            remove_operating_interval_btn.isClickable = false
            add_operating_interval_btn.setBackgroundColor(Color.GRAY)
            remove_operating_interval_btn.setBackgroundColor(Color.GRAY)

            operating_btn.setBackgroundColor(Color.GRAY)
            operating_btn.isClickable = false
            all_selected_checked.isEnabled = false
            mInventoryListAdapter?.mOnRemoveInterface = null
            mLoadingListAdapter?.mOnRemoveInterface = null
        }
    }

    override fun modifyS() {
        TalkSureDialog(mContext, getScreenWidth(), "成功取消完成本车") {
            onBackPressed()
            this.finish()
        }.show()
    }

    override fun getInventoryS(list: List<StockWaybillListBean>) {
        mInventoryListAdapter?.appendData(list)
        fix_trunk_departure_house_tabLayout.getTabAt(0)?.text = "库存清单(${list.size})"

    }
}