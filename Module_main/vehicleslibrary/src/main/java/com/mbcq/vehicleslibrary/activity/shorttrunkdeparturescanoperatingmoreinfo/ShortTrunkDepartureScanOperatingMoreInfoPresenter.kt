package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperatingmoreinfo

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.activity.shorttrunkdepartureunplanscanoperating.ShortTrunkDepartureUnPlanScanOperatingBean
import com.mbcq.vehicleslibrary.activity.shorttrunkdepartureunplanscanoperating.ShortTrunkDepartureUnPlanScanOperatingIdBean
import org.json.JSONObject
import java.lang.StringBuilder

/**
 * @author: lzy
 * @time: 2020-12-14 08:38:10  短驳扫描详情
 */

class ShortTrunkDepartureScanOperatingMoreInfoPresenter : BasePresenterImpl<ShortTrunkDepartureScanOperatingMoreInfoContract.View>(), ShortTrunkDepartureScanOperatingMoreInfoContract.Presenter {
    /**
    {"code":0,"msg":"","count":1,"data":[
    {
    "id": 10708,
    "billno": "10030004857",
    "lableNo": "100300048570001",
    "webidCode": 1003,
    "webidCodeStr": "汕头",
    "scanOpeType": 0,
    "scanOpeTypeStr": "短驳装车",
    "deviceNo": "92:FD:7D:F0:1A:6D",
    "content": "PDA货物从汕头出发，下一站是，操作员是lzy",
    "inOneVehicleFlag": "DB1003-20201211-003",
    "scanType": 0,
    "scanTypeStr": "PDA",
    "opeMan": "lzy",
    "recordDate": "2020-12-11T11:12:03"
    }
    ]}
     */
    override fun getPageData(billno: String, inOneVehicleFlag: String, scanOpeType: Int) {
        val params = HttpParams()
        if (billno.isNotBlank())
            params.put("billno", billno)
//        params.put("inOneVehicleFlag", inOneVehicleFlag)
        params.put("limit", 9999)
        /**
         * @scanOpeType 操作类型
         * 0 短驳装车
         * 1 干线装车
         * 2 短驳到车
         * 3 干线到车
         */
        params.put("scanOpeType", scanOpeType)
        get<String>(ApiInterface.SHORT_TRUNK_DEPARTURE_SCAN_OPERATING_MORE_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val listAry = obj.optJSONArray("data")
                listAry?.let {
                    val mShowList = mutableListOf<ShortTrunkDepartureScanOperatingMoreInfoBean>()
                    for (itemIndex in 0 until it.length()) {
                        if (listAry.getJSONObject(itemIndex).optInt("scanType") != 2) {
                            val mShortTrunkDepartureScanOperatingMoreInfoBean = ShortTrunkDepartureScanOperatingMoreInfoBean()
                            mShortTrunkDepartureScanOperatingMoreInfoBean.time = listAry.getJSONObject(itemIndex).optString("recordDate")
                            mShortTrunkDepartureScanOperatingMoreInfoBean.scanName = listAry.getJSONObject(itemIndex).optString("opeMan")
                            mShortTrunkDepartureScanOperatingMoreInfoBean.scanTypeStr = listAry.getJSONObject(itemIndex).optString("scanTypeStr")
                            mShortTrunkDepartureScanOperatingMoreInfoBean.mScanInOneVehicleFlag = if (!inOneVehicleFlag.contains(listAry.getJSONObject(itemIndex).optString("inOneVehicleFlag"))) listAry.getJSONObject(itemIndex).optString("inOneVehicleFlag") else ""
                            mShortTrunkDepartureScanOperatingMoreInfoBean.isScaned = true
                            mShortTrunkDepartureScanOperatingMoreInfoBean.lableNo = listAry.getJSONObject(itemIndex).optString("lableNo")
                            mShortTrunkDepartureScanOperatingMoreInfoBean.mDismantleInfo = if (!inOneVehicleFlag.contains(listAry.getJSONObject(itemIndex).optString("inOneVehicleFlag"))) "拆" else ""
                            mShowList.add(mShortTrunkDepartureScanOperatingMoreInfoBean)
                        }

                    }
                    if (mShowList.isNotEmpty())
                        mView?.getPageDataS(mShowList)
                }

            }

        })
    }

    fun getCarScanData(billno: String, totalQtyMore: String, inoneVehicleFlag: String) {
        val params = HttpParams()
        if (billno.isNotBlank())
            params.put("billno", billno)
        params.put("limit", 99999)
        /**
         * @scanOpeType 操作类型
         * 0 短驳装车
         * 1 干线装车
         * 2 短驳到车
         * 3 干线到车
         */
        params.put("scanOpeType", "-1")
        get<String>(ApiInterface.SHORT_TRUNK_DEPARTURE_SCAN_OPERATING_MORE_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val listAry = obj.optJSONArray("data")
                //********--------------------------------------------
                val mShowList = mutableListOf<ShortTrunkDepartureScanOperatingMoreInfoBean>()
                val mCarBiilno = StringBuilder()
                for ((mXBillnoItemIndexx, mXBillnoItem) in billno.split(",").withIndex()) {
                    for (mXTotalQtyItem in 1..totalQtyMore.split(",")[mXBillnoItemIndexx].toInt()) {
                        val mShortTrunkDepartureScanOperatingMoreInfoBean = ShortTrunkDepartureScanOperatingMoreInfoBean()
                        val endBillno = if (mXTotalQtyItem.toString().length == 1) "000$mXTotalQtyItem" else if (mXTotalQtyItem.toString().length == 2) "00$mXTotalQtyItem" else if (mXTotalQtyItem.toString().length == 3) "0$mXTotalQtyItem" else if (mXTotalQtyItem.toString().length == 4) "$mXTotalQtyItem" else ""
                        mShortTrunkDepartureScanOperatingMoreInfoBean.lableNo = mXBillnoItem + endBillno
                        listAry?.let {
                            for (itemIndex in 0 until it.length()) {
                                if (listAry.getJSONObject(itemIndex).optInt("scanType") != 2) {
                                    if (listAry.getJSONObject(itemIndex).optString("lableNo") == (mXBillnoItem + endBillno)) {
                                        mShortTrunkDepartureScanOperatingMoreInfoBean.time = listAry.getJSONObject(itemIndex).optString("recordDate")
                                        mShortTrunkDepartureScanOperatingMoreInfoBean.scanName = listAry.getJSONObject(itemIndex).optString("opeMan")
                                        mShortTrunkDepartureScanOperatingMoreInfoBean.scanTypeStr = listAry.getJSONObject(itemIndex).optString("scanTypeStr")
                                        mShortTrunkDepartureScanOperatingMoreInfoBean.mScanInOneVehicleFlag = if (!inoneVehicleFlag.contains(listAry.getJSONObject(itemIndex).optString("inOneVehicleFlag"))) listAry.getJSONObject(itemIndex).optString("inOneVehicleFlag") else ""
                                        mShortTrunkDepartureScanOperatingMoreInfoBean.isScaned = true
                                        mShortTrunkDepartureScanOperatingMoreInfoBean.mDismantleInfo = if (!inoneVehicleFlag.contains(listAry.getJSONObject(itemIndex).optString("inOneVehicleFlag"))) "拆" else ""
                                        continue
                                    }
                                }

                            }
                        }
                        mShowList.add(mShortTrunkDepartureScanOperatingMoreInfoBean)
                        mCarBiilno.append(mXBillnoItem + endBillno).append(",")
                    }
                }
//                TalkSureDialog(mView?.getContext()!!, 1200, mCarBiilno.toString()).show()
                if (mShowList.isNotEmpty())
                    mView?.getPageDataS(mShowList)
            }

        })
    }


    override fun getCarInfo(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_SELECT_LOCAL_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(1)) {
                        val dataObj = it.optJSONObject(1)
                        val mCarBiilno = StringBuilder()
                        val mCarQty = StringBuilder()
                        val mXDataList = Gson().fromJson<List<ShortTrunkDepartureScanOperatingMoreCarInfoBean>>(dataObj.optString("data"), object : TypeToken<List<ShortTrunkDepartureScanOperatingMoreCarInfoBean>>() {}.type)
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