package com.mbcq.vehicleslibrary.activity.departuretrunkdepartureunplanscanoperating

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.activity.departuretrunkdeparturescanoperatingmoreinfo.DepartureTrunkDepartureScanOperatingScanInfoBean
import com.mbcq.vehicleslibrary.activity.shorttrunkdepartureunplanscanoperating.ShortTrunkDepartureUnPlanScanOperatingBean
import com.mbcq.vehicleslibrary.activity.shorttrunkdepartureunplanscanoperating.ShortTrunkDepartureUnPlanScanOperatingIdBean
import com.mbcq.vehicleslibrary.activity.shorttrunkdepartureunplanscanoperating.ShortTrunkDepartureUnPlanScanOperatingPresenter
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.lang.StringBuilder

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
                        var mXid = -1
                        if (mDepartureTrunkDepartureUnPlanScanOperatingIdBean.data.isNotEmpty()) {
                            if (mDepartureTrunkDepartureUnPlanScanOperatingIdBean.data[0].data.isNotEmpty())
                                mXid = mDepartureTrunkDepartureUnPlanScanOperatingIdBean.data[0].data[0].id

                        }
                        if (mXid == -1) {
                            mView?.showError("请联系服务器工作人员！")
                        }
                        mView?.getCarInfoS(Gson().fromJson(dataObj.optString("data"), object : TypeToken<List<DepartureTrunkDepartureUnPlanScanOperatingBean>>() {}.type), mXid)
                    }
                }
            }

        })
    }

    override fun getWillByInfo(billno: String, resultBillno: String, mScanType: Int) {
        val xParams = HttpParams()
        xParams.put("billno", billno)
        mView?.getContext()?.let {
            xParams.put("SelWebidCode", UserInformationUtil.getWebIdCode(it))
        }
        get<String>(ApiInterface.SHIPMENT_INVENTORY_SELECTED_INFO_GET, xParams, object : CallBacks {
            override fun onResult(result: String) {
                val xObj = JSONObject(result)
                xObj.optJSONArray("data")?.let { xJay ->
                    if (!xJay.isNull(0)) {
                        val stockObj = xJay.getJSONObject(0)
                        val stockQty = stockObj.optString("waybillFcdQty").replace(".0", "")


                        val params = HttpParams()
                        params.put("Billno", billno)
                        get<String>(ApiInterface.RECORD_SELECT_ORDER_INFO_GET, params, object : CallBacks {
                            override fun onResult(mVResult: String) {
                                val obj = JSONObject(mVResult)
                                obj.optJSONArray("data")?.let { willbyJay ->
                                    if (!willbyJay.isNull(0)) {
                                        val sendObj = willbyJay.optJSONObject(0)
                                        sendObj.remove("waybillFcdQty")
                                        sendObj.put("waybillFcdQty", stockQty.toInt())
                                        mView?.getWillByInfoS(sendObj, resultBillno, mScanType)

                                    } else {
                                        mView?.getWillByInfoNull(billno)
                                    }
                                }
                            }

                        })
                    } else
                        mView?.isNotAtStock(billno)
                }
            }
        })

    }

    /**
    {
    "CompanyId": "2001",
    "Billno": "10030004897",
    "LableNo": "100300048970001",
    "DeviceNo": "92:FD:7D:F0:1A:6D",
    "InOneVehicleFlag": "GX1003-20201217-004",
    "ScanPercentage": "80",
    "RecordDate": "2020-12-18 08:51:20",
    "IsScan": 2,
    "isScanDet": 2,
    "opeMan": "lzy",
    "ScanType": 0,
    "ScanTypeStr": "PDA",
    "ScanOpeType": 1,
    "ScanOpeTypeStr": "干线装车"
    }
    扫描前查询扫描信息 防止扫描第二辆车重复 防止漏货
    这里本辆车再次扫描不为异常 让后台去判断
     */
    override fun scanOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, ewebidCodeStr: String, scanPercentage: String, totalQty: Int, mScanType: Int) {
        getScanBillNoInfo(billno, 2, totalQty, object : OnScanBillNoInfoResultInterface {
            override fun onResult(result: String) {
                var mTopLableNo = lableNo
                val obj = JSONObject(result)
                val listAry = obj.optJSONArray("data")
                listAry?.let {
                    val mXlableNo = arrayListOf<Long>()//全部未扫描大票运单号
                    if (lableNo.contains(",")) {
                        for (mCCCIndex in 1..totalQty) {
                            val endBillno = billno + if (mCCCIndex.toString().length == 1) "000$mCCCIndex" else if (mCCCIndex.toString().length == 2) "00$mCCCIndex" else if (mCCCIndex.toString().length == 3) "0$mCCCIndex" else if (mCCCIndex.toString().length == 4) "$mCCCIndex" else ""
                            var isHasIt = false
                            for (itemIndex in 0 until it.length()) {
                                if (listAry.getJSONObject(itemIndex).optString("lableNo") == endBillno && listAry.getJSONObject(itemIndex).optInt("scanType") != 2) {
                                    isHasIt = true
                                    continue
                                }
                            }
                            if (!isHasIt)
                                mXlableNo.add(endBillno.toLong())
                        }
                        //-1 前 1后 正序
                        mXlableNo.sortWith(Comparator { o1, o2 ->
                            if (o1 > o2) 1 else -1
                        })
                        val mXPostScaningDataStr = StringBuilder()
                        for (index in (mXlableNo.size - lableNo.split(",").size) until mXlableNo.size) {
                            mXPostScaningDataStr.append(mXlableNo[index])
                            if (index != (mXlableNo.size - 1))
                                mXPostScaningDataStr.append(",")
                        }
                        mTopLableNo = mXPostScaningDataStr.toString()
                    } else {
                        for (itemIndex in 0 until it.length()) {
                            if (lableNo==listAry.getJSONObject(itemIndex).optString("lableNo")){
                                mView?.againScanException(billno, lableNo, deviceNo, inOneVehicleFlag, soundStr, ewebidCode, ewebidCodeStr, scanPercentage, 2,"该车已经在车次${listAry.getJSONObject(itemIndex).optString("inOneVehicleFlag")}扫描,请核实后重试！")
                                return@let
                            }
                        }
                    }

                    val jsonO = JSONObject()
                    jsonO.put("CompanyId", "2001")
                    jsonO.put("Billno", billno)
                    jsonO.put("LableNo", mTopLableNo)
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

    interface OnScanBillNoInfoResultInterface {
        fun onResult(result: String)
    }

    /**
     * @type 1 走正常回调 2 走interface返回
     */
    fun getScanBillNoInfo(billno: String, type: Int, totalQty: Int, mOnScanBillNoInfoResultInterface: OnScanBillNoInfoResultInterface?) {
        val params = HttpParams()
        params.put("billno", billno)
        /**
         * @scanOpeType 操作类型
         * 0 短驳装车
         * 1 干线装车
         * 2 短驳到车
         * 3 干线到车
         * -1 查询扫描信息
         */
        params.put("scanOpeType", "-1")
        get<String>(ApiInterface.SHORT_TRUNK_DEPARTURE_SCAN_OPERATING_MORE_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                if (type == 2)
                    mOnScanBillNoInfoResultInterface?.onResult(result)
                else if (type == 1)
                    mView?.getScanBillNoInfoS(billno, result, totalQty)
            }
        })
    }

    override fun getScanBillNoInfo(billno: String, totalQty: Int) {
        getScanBillNoInfo(billno, 1, totalQty, null)

    }

    /**
     * {"code":0,"msg":"","count":10,"data":[
    {
    "id": 43,
    "companyid": 2001,
    "vehicleno": "浙G12362",
    "vehicletype": 1,
    "vehicleshape": "0",
    "vlength": 12.00,
    "vwidth": 16.00,
    "vheight": 2001.00,
    "enginenum": "62382973763276",
    "ylpnum": "1234567890",
    "vjnum": "098765421",
    "addnum": "12345678",
    "openum": "9876543",
    "vcolor": "红色",
    "buydate": "2019-01-17T00:00:00",
    "supweight": 12.00,
    "volumn": 23.00,
    "yeachedate": "2019-01-17T00:00:00",
    "safenum": "23267812367",
    "safeunit": "中国人寿保险",
    "boxmoney": null,
    "vowner": "张飞云",
    "idcard": "433155198808082121",
    "ownertel": null,
    "ownermb": "15622266667",
    "owneradd": "北京市西城区夏城路北城街",
    "vbelongunit": "北京市供电局",
    "unitadd": "北京市西城区朝阳路1111号",
    "chauffer": "张凯",
    "chaidcard": "433125555666667777",
    "chauffertel": "",
    "chauffermb": "16276665366",
    "driverlicense": "25253367126545",
    "chaadd": "上海市黄浦区热敏广场",
    "belwebcode": 1004,
    "belweb": "汕头",
    "sharewebcode": "",
    "shareweb": "义乌后湖,义乌篁园,汕头,潮州彩塘,潮州庵埠,汕头峡山,杭州萧山,杭州余杭,广州丰和,永康中田,测试117,测试118,测试119,测试120",
    "vusetype": 0,
    "oilwear": 12.00,
    "Israliable": 0
    }]}
     */

    override fun getVehicles(vehicleNo: String) {
        val params = HttpParams()
        params.put("vehicleNo", vehicleNo)
        get<String>(ApiInterface.VEHICLE_SELECT_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val json = JSONTokener(obj.optString("data")).nextValue()
                if (json is JSONArray) {
                    obj.optJSONArray("data")?.let {
                        if (!it.isNull(0)) {
                            mView?.getVehicleS(obj.optString("data"))
                        }
                    }

                }


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