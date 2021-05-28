package com.mbcq.vehicleslibrary.activity.homedeliveryhouse


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouse.ShareDeliveryCostsBean
import kotlinx.android.synthetic.main.activity_home_delivery_house.*
import org.json.JSONArray
import org.json.JSONObject


/**
 * @author: lzy
 * @time: 2021-01-14 17:59:02 上门提货
 */

@Route(path = ARouterConstants.HomeDeliveryHouseActivity)
class HomeDeliveryHouseActivity : BaseHomeDeliveryHouseActivity<HomeDeliveryHouseContract.View, HomeDeliveryHousePresenter>(), HomeDeliveryHouseContract.View {
    @Autowired(name = "HomeDeliveryHouse")
    @JvmField
    var mLastData: String = ""

    override fun getLayoutId(): Int = R.layout.activity_home_delivery_house
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        dispatch_number_tv.text = "提货批次:${JSONObject(mLastData).optString("inOneVehicleFlag")}"
    }

    override fun initLoadingList() {
        super.initLoadingList()
        mLoadingListAdapter?.mOnFeeInterface = object : HomeDeliveryHouseLoadingAdapter.OnFeeInterface {
            override fun onFeeClick(position: Int, item: HomeDeliveryHouseBean) {
                HomeDeliveryHousePriceDialog(getScreenWidth(), Gson().toJson(item), object : OnClickInterface.OnClickInterface {
                    override fun onResult(s1: String, s2: String) {
                        val obj = JSONObject(s1)
                        item.accZxf = obj.optString("accZxf")
                        item.accCc = obj.optString("accCc")
                        mLoadingListAdapter?.notifyItemChangeds(position, item)
                        refreshTopInfo()


                    }

                }).show(supportFragmentManager, "HomeDeliveryHousePrice")
            }

        }
    }

    fun completeCar() {
        mLoadingListAdapter?.getAllData()?.let {
            if (it.isEmpty()) {
                TalkSureDialog(mContext, getScreenWidth(), "请配载您要发的运单").show()
                return
            }
            val mLastDataObj = JSONObject(mLastData)
            val jarray = JSONArray()
            val kk = StringBuilder()
            for ((index, item) in it.withIndex()) {
                val obj = JSONObject(Gson().toJson(item))
                kk.append(item.billno)
                if (index != it.size - 1)
                    kk.append(",")
                jarray.put(obj)
            }
            mLastDataObj.put("PickUpdetLst", jarray)
            mLastDataObj.put("CommonStr", kk.toString())
            mPresenter?.saveInfo(mLastDataObj)


        }
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getInventory()
    }

    /**
     * {"pickUpDate":"2021-3-26","inOneVehicleFlag":"TH1003-20210326-001","id":"1162","CommonStr":"10030005321","PickUpdetLst":[{"id":1162,"companyId":2001,"eCompanyId":2001,"orderId":"2","billno":"10030005321","oBillno":"","billDate":"2021-03-18T16:01:28","billState":1,"billStateStr":"已入库","billType":0,"billTypeStr":"机打","goodsNum":"05321-16","okProcess":1,"okProcessStr":"客户自提","isUrgent":0,"isUrgentStr":"","isTalkGoods":1,"isTalkGoodsStr":"生门提货","webidCode":1003,"webidCodeStr":"汕头","ewebidCode":1003,"ewebidCodeStr":"汕头","destination":"汕头","transneed":1,"transneedStr":"零担","vipId":"","shipperId":"","shipperMb":"13916742298","shipperTel":"66","shipper":"宋学宝","shipperCid":"gyy","shipperAddr":"上海市清234234234我的","consigneeMb":"13916742298","consigneeTel":"999","consignee":"非常慢","consigneeAddr":"北京市人民路北京西南路东面","product":"家电,,","totalQty":16,"qty":16,"packages":"纸箱,,","weight":6,"volumn":55,"weightJs":14300,"safeMoney":5,"accDaiShou":0,"accHKChange":0,"hkChangeReason":"","sxf":0,"wPrice":5,"vPrice":3,"qtyPrice":5,"accNow":0,"accArrived":129,"accBack":0,"accMonth":0,"accHuoKuanKou":0,"accTrans":129,"accFetch":0,"accPackage":0,"accSend":0,"accGb":5,"accSafe":0,"accRyf":0,"accHuiKou":0,"accSms":0,"accZz":0,"accZx":0,"accCb":0,"accSl":0,"accAz":0,"accFj":0,"accWz":0,"accJc":0,"accSum":134,"accType":2,"accTypeStr":"提付","backQty":"签回单","backState":1,"isWaitNotice":0,"isWaitNoticeStr":"否","bankCode":"","bankName":"","bankMan":"","bankNumber":"","createMan":"lzy","salesMan":"测试","opeMan":"lzy","remark":"","fromType":3,"isTransferCount":0,"preVehicleNo":"","valueAddedService":"2","bilThState":0,"bilThStateStr":"","accBackService":0,"Lightandheavy":"轻货","shipperCompany":"ff","consigneeCompany":"hhh","consigneeId":null,"shipperId1":""}]}
     */
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
                        val mNewData = mutableListOf<HomeDeliveryHouseBean>()
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
                        refreshTopInfo()

                    }

                }).show(supportFragmentManager, "ShareDeliveryCostsFilterDialog")
            }

        })
        complete_vehicle_btn.apply {
            onSingleClicks {
                completeCar()
            }
        }
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
        operating_btn.setOnClickListener(object : SingleClick() {
            @SuppressLint("SetTextI18n")
            override fun onSingleClick(v: View?) {
                if (operating_btn.text.toString() == "添加本车") {
                    addSomeThing()

                } else if (operating_btn.text.toString() == "移出本车") {
                    removeSomeThing()
                }
                inventory_list_tv.text = "库存清单(${mInventoryListAdapter?.getAllData()?.size})"
                loading_list_tv.text = "配载清单(${mLoadingListAdapter?.getAllData()?.size})"
            }

        })
        all_selected_checked.setOnCheckedChangeListener { _, isChecked ->
            if (mTypeIndex == 1)
                mInventoryListAdapter?.checkedAll(isChecked)
            else if (mTypeIndex == 2)
                mLoadingListAdapter?.checkedAll(isChecked)

        }
    }

    @SuppressLint("SetTextI18n")
    override fun getInventoryS(list: List<HomeDeliveryHouseBean>) {
        mInventoryListAdapter?.appendData(list)
        inventory_list_tv.text = "库存清单(${list.size})"
    }

    override fun saveInfoS(result: String) {
        TalkSureDialog(mContext, getScreenWidth(), "上门提货${JSONObject(mLastData).optString("inOneVehicleFlag")}已完成，点击返回查看详情！") {
            onBackPressed()

        }.show()
    }
}