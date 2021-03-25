package com.mbcq.vehicleslibrary.activity.arrivalshortscanoperating

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.activity.arrivaltrunkdeparturescanoperating.ArrivalTrunkDepartureScanOperatingBean
import com.mbcq.vehicleslibrary.activity.arrivaltrunkdeparturescanoperating.ArrivalTrunkDepartureScanOperatingByIdBean
import org.json.JSONObject
import java.lang.StringBuilder

/**
 * @author: lzy
 * @time: 2021-03-19 10:30:12 短驳到车扫描
 */

class ArrivalShortScanOperatingPresenter : BasePresenterImpl<ArrivalShortScanOperatingContract.View>(), ArrivalShortScanOperatingContract.Presenter {
    override fun getCarInfo(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("inoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.SHORT_VEHICLE_ORDER_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(1)) {
                        val dataObj = it.optJSONObject(1)
                        val mArrivalShortScanOperatingByIdBean = Gson().fromJson<ArrivalShortScanOperatingByIdBean>(result, ArrivalShortScanOperatingByIdBean::class.java)
                        var mXid = -1
                        if (mArrivalShortScanOperatingByIdBean.data.isNotEmpty()) {
                            if (mArrivalShortScanOperatingByIdBean.data[0].data.isNotEmpty())
                                mXid = mArrivalShortScanOperatingByIdBean.data[0].data[0].id

                        }
                        if (mXid == -1) {
                            /**
                             * 服务器必须返回id
                             */
                            mView?.showError("无法发车,请联系服务器工作人员！")
                        }
                        mView?.getCarInfoS(Gson().fromJson(dataObj.optString("data"), object : TypeToken<List<ArrivalShortScanOperatingBean>>() {}.type), mXid)
                    }
                }
            }
        })
    }

    override fun scanOrder(billno: String, lableNo: String, inoneVehicleFlag: String, ewebidCodeStr: String, scanType: Int, totalQty: Int, xcScanPercentage: String) {
        val testParams = HttpParams()
        testParams.put("billno", billno)
        /**
         * @scanOpeType 操作类型
         * 0 短驳装车
         * 1 干线装车
         * -1 查询扫描信息
         * SHORT_TRUNK_DEPARTURE_SCAN_OPERATING_MORE_INFO_GET 装车的扫描详情
         */

        testParams.put("scanOpeType", "0")
        testParams.put("limit", "9999")
        get<String>(ApiInterface.SHORT_TRUNK_DEPARTURE_SCAN_OPERATING_MORE_INFO_GET, testParams, object : CallBacks {
            override fun onResult(mXresult: String) {
                getTopLableNo(mXresult, billno, inoneVehicleFlag, lableNo, totalQty, object : OnResultInteface {
                    override fun onResult(xlableNo: String) {
                        val scanObj = JSONObject()
                        scanObj.put("xcScanPercentage", xcScanPercentage)//扫描率
                        scanObj.put("billno", billno)
                        scanObj.put("lableNo", xlableNo)
                        scanObj.put("inoneVehicleFlag", inoneVehicleFlag)
                        scanObj.put("scanType", scanType)
                        scanObj.put("scanTypeStr", if (scanType == 0) "PDA" else "手动输入")
//                        Log.e("scanObj", "onResult: " + GsonUtils.toPrettyFormat(scanObj))
//                        if (true) return
                        post<String>(ApiInterface.SHORT_SCAN_ARRIVAL_DATA_POST, getRequestBody(scanObj), object : CallBacks {
                            override fun onResult(result: String) {
                                mView?.scanOrderS(billno, ewebidCodeStr, lableNo)

                            }
                        })
                    }

                })
            }
        })

    }

    override fun getScanData(billno: String, lableNo: String, inoneVehicleFlag: String) {
        val testParams = HttpParams()
        testParams.put("billno", billno)
        /**
         * @scanOpeType 操作类型
         * 0 短驳装车
         * 1 干线装车
         * -1 查询扫描信息
         * SHORT_TRUNK_DEPARTURE_SCAN_OPERATING_MORE_INFO_GET 装车的扫描详情
         */

        testParams.put("scanOpeType", "0")
        testParams.put("limit", "9999")
        get<String>(ApiInterface.SHORT_TRUNK_DEPARTURE_SCAN_OPERATING_MORE_INFO_GET, testParams, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(0)) {
                        var isCanScan = false
                        for (index in 0 until it.length()) {
                            val itemObj = it.getJSONObject(index)
                            if (itemObj.optString("lableNo") == lableNo) {
                                if (itemObj.optString("inOneVehicleFlag") == inoneVehicleFlag) {
                                    isCanScan = true
                                    break
                                }


                            }
                        }
                        if (!isCanScan)
                            mView?.showError("该标签号${lableNo}不存在于本车，请核实后重试！")
                        else
                            mView?.getScanDataS(lableNo)
                    } else {
                        mView?.getScanDataS(lableNo)

                    }

                }
            }
        })

    }

    /**
     * type 1 获取点击数据
     */
    override fun getClickLable(billno: String, inoneVehicleFlag: String, totalQty: Int, type: Int) {
        val testParams = HttpParams()
        testParams.put("billno", billno)
        /**
         * @scanOpeType 操作类型
         * 0 短驳装车
         * 1 干线装车
         * -1 查询扫描信息
         * SHORT_TRUNK_DEPARTURE_SCAN_OPERATING_MORE_INFO_GET 装车的扫描详情
         */

        testParams.put("scanOpeType", "0")
        testParams.put("limit", "9999")
        get<String>(ApiInterface.SHORT_TRUNK_DEPARTURE_SCAN_OPERATING_MORE_INFO_GET, testParams, object : CallBacks {
            override fun onResult(mXresult: String) {
                getTopLableNo(mXresult, billno, inoneVehicleFlag, billno + "0001,", totalQty, object : OnResultInteface {
                    override fun onResult(xlableNo: String) {
                        mView?.getClickLableS(if (type == 1) xlableNo.split(",")[0] else xlableNo)
                    }
                })
            }

        })
    }

    fun getTopLableNo2(mXlableNo: List<Long>, billno: String, inoneVehicleFlag: String, lableNo: String, type: Int, mOnResultInteface: OnResultInteface?) {
        val Vparams = HttpParams()
        Vparams.put("billno", billno)
        Vparams.put("inoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.SHORT_VEHICLE_SCANED_ORDER_INFO_GET, Vparams, object : CallBacks {
            override fun onResult(vResult: String) {
                val vObj = JSONObject(vResult)
                vObj.optJSONArray("data")?.let { xIt ->
                    val mVlableNo = arrayListOf<Long>()//全部本车未扫标签号
                    for (item in mXlableNo) {
//                        Log.e("vvvvvvv", "onResult: item" + item.toString())
                        var isVHas = false
                        for (vItem in 0 until xIt.length()) {
//                            Log.e("vvvvvvv", "onResult:lableNo " + xIt.getJSONObject(vItem).optString("lableNo"))
                            if (xIt.getJSONObject(vItem).optString("lableNo") == item.toString()) {
                                isVHas = true
                                continue
                            }
                        }
                        if (!isVHas) {
                            mVlableNo.add(item)
                        }
                    }
                    //-1 前 1后 正序
                    mVlableNo.sortWith(Comparator { o1, o2 ->
                        if (o1 > o2) 1 else -1
                    })
                    val mXPostScaningDataStr = StringBuilder()
//                    val shortNum=if ()
                    for (index in (mVlableNo.size - (lableNo.split(",").size)) until mVlableNo.size) {
                        if (index >= 0) {
                            mXPostScaningDataStr.append(mVlableNo[index])
                            if (index != (mVlableNo.size - 1))
                                mXPostScaningDataStr.append(",")
                        }

                    }

//                    Log.e("vvvvvvv", "onResult: " + mXPostScaningDataStr.toString())
                    if (type == 1)
                        mOnResultInteface?.onResult(mXPostScaningDataStr.toString())
                }

            }

        })
    }

    fun getTopLableNo3(billno: String, inoneVehicleFlag: String, lableNo: String, totalQty: Int, mOnResultInteface: OnResultInteface) {
        if (lableNo.contains(",")) {
            val mAlableNo = arrayListOf<Long>()
            for (mCCCIndex in 1..totalQty) {
                val endBillno = billno + if (mCCCIndex.toString().length == 1) "000$mCCCIndex" else if (mCCCIndex.toString().length == 2) "00$mCCCIndex" else if (mCCCIndex.toString().length == 3) "0$mCCCIndex" else if (mCCCIndex.toString().length == 4) "$mCCCIndex" else ""
                mAlableNo.add(endBillno.toLong())
            }
            //-1 前 1后 正序
            mAlableNo.sortWith(Comparator { o1, o2 ->
                if (o1 > o2) 1 else -1
            })
            getTopLableNo2(mAlableNo, billno, inoneVehicleFlag, lableNo, 1, mOnResultInteface)
        } else
            mOnResultInteface.onResult(lableNo)
    }

    fun getTopLableNo(mXresult: String, billno: String, inoneVehicleFlag: String, lableNo: String, totalQty: Int, mOnResultInteface: OnResultInteface) {
        /**
         * @mXresult 发车全部扫描数据记录
         */

        val mXObj = JSONObject(mXresult)
        mXObj.optJSONArray("data")?.let {
            /**
             * 两种情况 拆票一定会走发车扫描
             */
            if (!it.isNull(0)) {
                val mXlableNo = arrayListOf<Long>()//全部本车发车标签号
                if (lableNo.contains(",")) {
                    for (mXitemIndex in 0 until it.length()) {
                        // "inOneVehicleFlag": "GX1001-20210316-011"
                        if (it.getJSONObject(mXitemIndex).optString("inOneVehicleFlag").replace(" ", "").contains(inoneVehicleFlag.replace(" ", "")))
                            mXlableNo.add(it.getJSONObject(mXitemIndex).optLong("lableNo"))
                    }
                    //-1 前 1后 正序
                    mXlableNo.sortWith(Comparator { o1, o2 ->
                        if (o1 > o2) 1 else -1
                    })
                    getTopLableNo2(mXlableNo, billno, inoneVehicleFlag, lableNo, 1, mOnResultInteface)


                } else {
                    /**
                     *
                     */
                    getTopLableNo3(billno, inoneVehicleFlag, lableNo, totalQty, mOnResultInteface)

                }

            } else {
                getTopLableNo3(billno, inoneVehicleFlag, lableNo, totalQty, mOnResultInteface)

            }

        }


    }

    interface OnResultInteface {
        fun onResult(xlableNo: String)
    }
}