package com.mbcq.vehicleslibrary.activity.departuretrunkdepartureunplanscanoperating

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.activity.shorttrunkdepartureunplanscanoperating.ShortTrunkDepartureUnPlanScanOperatingBean
import com.mbcq.vehicleslibrary.activity.shorttrunkdepartureunplanscanoperating.ShortTrunkDepartureUnPlanScanOperatingIdBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-19 09:49:03 干线无计划扫描
 */

class DepartureTrunkDepartureUnPlanScanOperatingPresenter : BasePresenterImpl<DepartureTrunkDepartureUnPlanScanOperatingContract.View>(), DepartureTrunkDepartureUnPlanScanOperatingContract.Presenter {
    override fun getCarInfo(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_LOCAL_INFO_POST, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(1)) {
                        val dataObj = it.optJSONObject(1)
                        val mDepartureTrunkDepartureUnPlanScanOperatingIdBean = Gson().fromJson<DepartureTrunkDepartureUnPlanScanOperatingIdBean>(result, DepartureTrunkDepartureUnPlanScanOperatingIdBean::class.java)
                        mView?.getCarInfoS(Gson().fromJson(dataObj.optString("data"), object : TypeToken<List<DepartureTrunkDepartureUnPlanScanOperatingBean>>() {}.type), mDepartureTrunkDepartureUnPlanScanOperatingIdBean.data[0].data[0].id)
                    }
                }
            }

        })
    }

    override fun getWillByInfo(billno: String, resultBillno: String, mScanType: Int) {
        val params = HttpParams()
        params.put("Billno", billno)
        get<String>(ApiInterface.RECORD_SELECT_ORDER_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(0)) {
                        mView?.getWillByInfoS(it.getJSONObject(0), resultBillno,mScanType)

                    } else {
                        mView?.getWillByInfoNull()
                    }

                }
            }

        })
    }

    /**
     * {
      "CompanyId": 2001,
      "Billno": "10030002768",
      "LableNo": "100300027680006",
      "EwebidCode": 1004,
      "EwebidCodeStr": "潮州彩塘",
      "DeviceNo": "123",
      "InOneVehicleFlag": "DB1003-20201113-004",  
      "ScanType": 0,
      "ScanTypeStr": "PDA",
      "IsScan": 2,
      "ScanPercentage": 12 扫描率
    }
     */
    override fun scanOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, ewebidCodeStr: String, scanPercentage: String, mScanType: Int) {
        val jsonO = JSONObject()
        jsonO.put("CompanyId", "2001")
        jsonO.put("Billno", billno)
        jsonO.put("LableNo", lableNo)
        jsonO.put("DeviceNo", deviceNo)
        jsonO.put("ewebidCode", ewebidCode)
        jsonO.put("ewebidCodeStr", ewebidCodeStr)
        jsonO.put("InOneVehicleFlag", inOneVehicleFlag)
        jsonO.put("ScanPercentage", scanPercentage)//-扫描率
        jsonO.put("RecordDate", TimeUtils.getCurrTime2())//记录日期
        /**
         * @IsScan
         * 1有计划
         * 2无计划
         */
        jsonO.put("IsScan", 2)
        jsonO.put("isScanDet", 1)
        mView?.getContext()?.let {
            jsonO.put("opeMan", UserInformationUtil.getUserName(it))//操作人
        }
        /**
         * @mScanType 扫描类型
         * 0 PDA
         * 1 手动输入
         * 2 异常扫描
         */
//        val mScanType = 0
        jsonO.put("ScanType", mScanType)
        jsonO.put("ScanTypeStr", if (mScanType == 0) "PDA" else if (mScanType == 1) "手动输入" else if (mScanType == 2) "异常扫描" else "")
        /**
         * @scanOpeType 操作类型
         * 0 短驳装车
         * 1 干线装车
         * 2 短驳到车
         * 3 干线到车
         */
        val mScanOpeType = 1
        jsonO.put("ScanOpeType", mScanOpeType)
        jsonO.put("ScanOpeTypeStr", if (mScanOpeType == 0) "短驳装车" else if (mScanOpeType == 1) "干线装车" else if (mScanOpeType == 2) "短驳到车" else if (mScanOpeType == 3) "干线到车" else "")

        post<String>(ApiInterface.DEPARTURE_TRUNK_DEPARTURE_SCAN_INFO_POST, getRequestBody(jsonO), object : CallBacks {
            override fun onResult(result: String) {
                mView?.scanOrderS(billno, soundStr, lableNo)

            }

        })
    }
    override fun scanAbnormalOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, ewebidCodeStr: String, scanPercentage: String, mMoreScanBillno: String, mAbnormalReason: String) {
        val jsonO = JSONObject()
        jsonO.put("CompanyId", "2001")
        jsonO.put("Billno", billno)
        jsonO.put("LableNo", lableNo)
        jsonO.put("DeviceNo", deviceNo)
        jsonO.put("ewebidCode", ewebidCode)
        jsonO.put("ewebidCodeStr", ewebidCodeStr)
        jsonO.put("InOneVehicleFlag", inOneVehicleFlag)
        jsonO.put("ScanPercentage", scanPercentage)//-扫描率
        jsonO.put("RecordDate", TimeUtils.getCurrTime2())//记录日期
        jsonO.put("Content", mAbnormalReason)//异常原因-这里这个参数不允许app端写 后台写 这里给异常详情留出来
        /**
         * @IsScan
         * 1有计划
         * 2无计划
         */
        jsonO.put("IsScan", 2)
        mView?.getContext()?.let {
            jsonO.put("opeMan", UserInformationUtil.getUserName(it))//操作人
        }
        /**
         * @mScanType 扫描类型
         * 0 PDA
         * 1 手动输入
         * 2 异常扫描
         */
        val mScanType = 2
        jsonO.put("isScanDet", 1)
        jsonO.put("ScanType", mScanType)
        jsonO.put("ScanTypeStr", if (mScanType == 0) "PDA" else if (mScanType == 1) "手动输入" else if (mScanType == 2) "异常扫描" else "")
        /**
         * @scanOpeType 操作类型
         * 0 短驳装车
         * 1 干线装车
         * 2 短驳到车
         * 3 干线到车
         */
        val mScanOpeType = 1
        jsonO.put("ScanOpeType", mScanOpeType)
        jsonO.put("ScanOpeTypeStr", if (mScanType == 0) "短驳装车" else if (mScanType == 1) "干线装车" else if (mScanType == 2) "短驳到车" else if (mScanType == 3) "干线到车" else "")

        post<String>(ApiInterface.DEPARTURE_SHORT_FEEDER_DEPARTURE_SCAN_INFO_POST, getRequestBody(jsonO), object : CallBacks {
            override fun onResult(result: String) {


            }

        })
    }
    override fun saveScanPost(id: Int, inoneVehicleFlag: String) {
        val postBody = JsonObject()
        postBody.addProperty("id", id)
        postBody.addProperty("InoneVehicleFlag", inoneVehicleFlag)
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_COMPLETE_LOCAL_INFO_POST, getRequestBody(postBody), object : CallBacks {
            override fun onResult(result: String) {
                mView?.saveScanPostS("")

            }

        })
    }
}