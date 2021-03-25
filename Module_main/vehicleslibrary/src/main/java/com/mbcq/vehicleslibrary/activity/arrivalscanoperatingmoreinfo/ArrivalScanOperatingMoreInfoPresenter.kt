package com.mbcq.vehicleslibrary.activity.arrivalscanoperatingmoreinfo

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.activity.arrivaltrunkdeparturescanoperating.ArrivalTrunkDepartureScanOperatingBean
import com.mbcq.vehicleslibrary.activity.arrivaltrunkdeparturescanoperating.ArrivalTrunkDepartureScanOperatingByIdBean
import com.mbcq.vehicleslibrary.activity.departuretrunkdeparturescanoperating.DepartureTrunkDepartureScanOperatingBean
import com.mbcq.vehicleslibrary.activity.departuretrunkdeparturescanoperatingmoreinfo.DepartureTrunkDepartureScanOperatingScanInfoBean
import org.json.JSONObject
import java.lang.StringBuilder

/**
 * @author: lzy
 * @time: 2021-03-16 14:22:43 到车扫描详情
 */

class ArrivalScanOperatingMoreInfoPresenter : BasePresenterImpl<ArrivalScanOperatingMoreInfoContract.View>(), ArrivalScanOperatingMoreInfoContract.Presenter {
    override fun getScanInfo(billno: String, inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("billno", billno)
//        params.put("inoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_VEHICLE_SCANED_ORDER_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    val list = Gson().fromJson<List<ArrivalScanOperatingMoreInfoBean>>(obj.optString("data"), object : TypeToken<List<ArrivalScanOperatingMoreInfoBean>>() {}.type)

                    val xParams = HttpParams()
                    xParams.put("billno", billno)
                    xParams.put("limit", 99999)
                    /**
                     * @scanOpeType 操作类型
                     * 0 短驳装车
                     * 1 干线装车
                     * 2 短驳到车
                     * 3 干线到车
                     */
                    xParams.put("scanOpeType", "-1")
                    get<String>(ApiInterface.SHORT_TRUNK_DEPARTURE_SCAN_OPERATING_MORE_INFO_GET, xParams, object : CallBacks {
                        override fun onResult(xResult: String) {
                            val xObj = JSONObject(xResult)

                            mView?.getScanInfoS(list, xObj.optString("data"))
                        }
                    })
                }
            }

        })
    }

    fun getCarScanData(billno: String, totalQtyMore: String, inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("inoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_VEHICLE_SCANED_ORDER_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val listAry = obj.optJSONArray("data")
                //********--------------------------------------------
                val mShowList = mutableListOf<ArrivalScanOperatingMoreInfoBean>()
                val mCarBiilno = StringBuilder()
                for ((mXBillnoItemIndexx, mXBillnoItem) in billno.split(",").withIndex()) {
                    for (mXTotalQtyItem in 1..totalQtyMore.split(",")[mXBillnoItemIndexx].toInt()) {
                        val mArrivalScanOperatingMoreInfoBean = ArrivalScanOperatingMoreInfoBean()
                        val endBillno = if (mXTotalQtyItem.toString().length == 1) "000$mXTotalQtyItem" else if (mXTotalQtyItem.toString().length == 2) "00$mXTotalQtyItem" else if (mXTotalQtyItem.toString().length == 3) "0$mXTotalQtyItem" else if (mXTotalQtyItem.toString().length == 4) "$mXTotalQtyItem" else ""
                        mArrivalScanOperatingMoreInfoBean.lableNo = mXBillnoItem + endBillno
//                        mArrivalScanOperatingMoreInfoBean.setmDismantleInfo("拆")
                        listAry?.let {
                            for (itemIndex in 0 until it.length()) {
                                if (listAry.getJSONObject(itemIndex).optString("lableNo") == (mXBillnoItem + endBillno)) {
                                    mArrivalScanOperatingMoreInfoBean.isScan = true
                                    mArrivalScanOperatingMoreInfoBean.billno = listAry.getJSONObject(itemIndex).optString("billno")
                                    mArrivalScanOperatingMoreInfoBean.inOneVehicleFlag = listAry.getJSONObject(itemIndex).optString("inOneVehicleFlag")
                                    mArrivalScanOperatingMoreInfoBean.opeMan = listAry.getJSONObject(itemIndex).optString("opeMan")
                                    mArrivalScanOperatingMoreInfoBean.recordDate = listAry.getJSONObject(itemIndex).optString("recordDate")
                                    mArrivalScanOperatingMoreInfoBean.scanTypeStr = listAry.getJSONObject(itemIndex).optString("scanTypeStr")
                                    mArrivalScanOperatingMoreInfoBean.scanType = listAry.getJSONObject(itemIndex).optInt("scanType")
                                    if (listAry.getJSONObject(itemIndex).optString("inOneVehicleFlag").isNotBlank())
                                        mArrivalScanOperatingMoreInfoBean.setmDismantleInfo(if (!inoneVehicleFlag.contains(listAry.getJSONObject(itemIndex).optString("inOneVehicleFlag"))) "拆" else "")
                                    continue
                                }
                            }


                        }
                        mShowList.add(mArrivalScanOperatingMoreInfoBean)
                        mCarBiilno.append(mXBillnoItem + endBillno).append(",")
                    }
                }
//                TalkSureDialog(mView?.getContext()!!, 1200, mCarBiilno.toString()).show()
                if (mShowList.isNotEmpty())
                    mView?.getScanCarInfoS(mShowList)
            }

        })
    }

    override fun getScanCarInfo(inoneVehicleFlag: String, selEwebidCode: String) {
        val params = HttpParams()
        params.put("inoneVehicleFlag", inoneVehicleFlag)
        params.put("SelEwebidCode", selEwebidCode)
        get<String>(ApiInterface.DEPARTURE_VEHICLE_ORDER_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(1)) {
                        val dataObj = it.optJSONObject(1)
                        val mCarBiilno = StringBuilder()
                        val mCarQty = StringBuilder()
//                        if (true)return
                        val mXDataList = Gson().fromJson<List<ArrivalTrunkDepartureScanOperatingBean>>(dataObj.optString("data"), object : TypeToken<List<ArrivalTrunkDepartureScanOperatingBean>>() {}.type)
                        for ((index, item) in mXDataList.withIndex()) {
                            mCarBiilno.append(item.billno)
                            mCarQty.append(item.totalQty)
                            if (index != mXDataList.lastIndex) {
                                mCarBiilno.append(",")
                                mCarQty.append(",")
                            }
                        }
                        if (mCarBiilno.toString().isNotBlank())
                            getCarScanData(mCarBiilno.toString(), mCarQty.toString(), inoneVehicleFlag)

                    }
                }
            }

        })
    }

}