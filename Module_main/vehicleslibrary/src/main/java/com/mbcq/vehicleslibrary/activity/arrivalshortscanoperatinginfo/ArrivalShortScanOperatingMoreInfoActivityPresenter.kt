package com.mbcq.vehicleslibrary.activity.arrivalshortscanoperatinginfo

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.activity.arrivaltrunkdeparturescanoperating.ArrivalTrunkDepartureScanOperatingBean
import org.json.JSONObject
import java.lang.StringBuilder

/**
 * @author: lzy
 * @time: 2021-03-19 13:43:12 短驳到车扫描详情
 */

class ArrivalShortScanOperatingMoreInfoActivityPresenter : BasePresenterImpl<ArrivalShortScanOperatingMoreInfoActivityContract.View>(), ArrivalShortScanOperatingMoreInfoActivityContract.Presenter {
    override fun getScanInfo(billno: String, inoneVehicleFlag: String) {
        val params = HttpParams()
        if (billno.isNotBlank())
            params.put("billno", billno)
        params.put("inoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_VEHICLE_SCANED_ORDER_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getScanInfoS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<ArrivalShortScanOperatingMoreInfoBean>>() {}.type))
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
                val mShowList = mutableListOf<ArrivalShortScanOperatingMoreInfoBean>()
                val mCarBiilno = StringBuilder()
                for ((mXBillnoItemIndexx, mXBillnoItem) in billno.split(",").withIndex()) {
                    for (mXTotalQtyItem in 1..totalQtyMore.split(",")[mXBillnoItemIndexx].toInt()) {
                        val mArrivalShortScanOperatingMoreInfoBean = ArrivalShortScanOperatingMoreInfoBean()
                        val endBillno = if (mXTotalQtyItem.toString().length == 1) "000$mXTotalQtyItem" else if (mXTotalQtyItem.toString().length == 2) "00$mXTotalQtyItem" else if (mXTotalQtyItem.toString().length == 3) "0$mXTotalQtyItem" else if (mXTotalQtyItem.toString().length == 4) "$mXTotalQtyItem" else ""
                        mArrivalShortScanOperatingMoreInfoBean.lableNo = mXBillnoItem + endBillno
                        listAry?.let {
                            for (itemIndex in 0 until it.length()) {
                                if (listAry.getJSONObject(itemIndex).optString("lableNo") == (mXBillnoItem + endBillno)) {
                                    mArrivalShortScanOperatingMoreInfoBean.isScan = true
                                    mArrivalShortScanOperatingMoreInfoBean.billno = listAry.getJSONObject(itemIndex).optString("billno")
                                    mArrivalShortScanOperatingMoreInfoBean.inOneVehicleFlag = listAry.getJSONObject(itemIndex).optString("inOneVehicleFlag")
                                    mArrivalShortScanOperatingMoreInfoBean.opeMan = listAry.getJSONObject(itemIndex).optString("opeMan")
                                    mArrivalShortScanOperatingMoreInfoBean.recordDate = listAry.getJSONObject(itemIndex).optString("recordDate")
                                    mArrivalShortScanOperatingMoreInfoBean.scanTypeStr = listAry.getJSONObject(itemIndex).optString("scanTypeStr")
                                    mArrivalShortScanOperatingMoreInfoBean.scanType = listAry.getJSONObject(itemIndex).optInt("scanType")
                                    mArrivalShortScanOperatingMoreInfoBean.setmDismantleInfo( if (!inoneVehicleFlag.contains(listAry.getJSONObject(itemIndex).optString("inOneVehicleFlag"))) "拆" else "")
                                    continue
                                }
                            }


                        }
                        mShowList.add(mArrivalShortScanOperatingMoreInfoBean)
                        mCarBiilno.append(mXBillnoItem + endBillno).append(",")
                    }
                }
//                TalkSureDialog(mView?.getContext()!!, 1200, mCarBiilno.toString()).show()
                if (mShowList.isNotEmpty())
                    mView?.getScanCarInfoS(mShowList)
            }

        })
    }

    override fun getScanCarInfo(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("inoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.SHORT_VEHICLE_ORDER_INFO_GET, params, object : CallBacks {
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