package com.mbcq.vehicleslibrary.activity.departuretrunkdepartureunplanscanoperating


import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.system.PhoneDeviceMsgUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.scan.scanlogin.ScanDialogFragment
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.shorttrunkdepartureunplanscanoperating.ShortTrunkDepartureUnPlanScanOperatingBean
import com.mbcq.vehicleslibrary.fragment.ScanNumDialog
import kotlinx.android.synthetic.main.activity_departure_trunk_departure_un_plan_scan_operating.*
import org.json.JSONObject
import java.lang.StringBuilder

/**
 * @author: lzy
 * @time: 2020-11-19 09:49:03 干线无计划扫描
 */

@Route(path = ARouterConstants.DepartureTrunkDepartureUnPlanScanOperatingActivity)
class DepartureTrunkDepartureUnPlanScanOperatingActivity : BaseDepartureTrunkDepartureUnPlanScanOperatingActivity<DepartureTrunkDepartureUnPlanScanOperatingContract.View, DepartureTrunkDepartureUnPlanScanOperatingPresenter, DepartureTrunkDepartureUnPlanScanOperatingBean>(), DepartureTrunkDepartureUnPlanScanOperatingContract.View {
    @Autowired(name = "DepartureLoadingVehicles")
    @JvmField
    var mLastData: String = ""
    override fun getLayoutId(): Int = R.layout.activity_departure_trunk_departure_un_plan_scan_operating

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        if (mLastData.isBlank())
            return
        val obj = JSONObject(mLastData)
        unScan_info_tv.text = "未扫： xxx件 xxxxkg  xxm³             扫描人:${UserInformationUtil.getUserName(mContext)}"
        unloading_batch_tv.text = "卸车批次:${obj.optString("inoneVehicleFlag")}"

    }
    override fun onResume() {
        super.onResume()
        val obj = JSONObject(mLastData)
        mPresenter?.getCarInfo(obj.optString("inoneVehicleFlag"))
    }

    override fun onClick() {
        super.onClick()
        scan_number_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getCameraPermission()
            }

        })
        departure_vehicles_un_plan_scan_operating_toolbar.setRightTitleOnClickListener(object :SingleClick(){
            override fun onSingleClick(v: View?) {
                //TODO 撤销共用
                ARouter.getInstance().build(ARouterConstants.RevokeDepartureTrunkDepartureScanOperatingActivity).withString("RevokeDepartureLoadingVehicles", mLastData).navigation()
            }

        })
        save_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (mTotalUnLoadingNum != 0) {
                    showToast("请扫描完毕再发车")
                    return
                }
                TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要完成本车吗") {
                    val modifyData = JSONObject(mLastData)
                    mPresenter?.saveScanPost(modifyData.optInt("id"), modifyData.optString("inoneVehicleFlag"))
                }.show()

            }

        })
    }

    fun getCameraPermission() {
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe { granted ->
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        ScanDialogFragment(getScreenWidth(), null, object : OnClickInterface.OnClickInterface {
                            override fun onResult(s1: String, s2: String) {
                                if (s1.length > 5) {
                                    val mAdapterData = adapter.getAllData()
                                    if (!mAdapterData.isNullOrEmpty()) {
                                        var isHasOrder = false
                                        for (item in mAdapterData) {
                                            if (item.billno == s1.substring(0, s1.length - 4)) {
                                                isHasOrder = true
                                                /**
                                                 * 已经扫描过此货物
                                                 */
                                                /**
                                                 *  多件扫描start------------------------------------------------------
                                                 */
                                                if (item.qty > 20) {
                                                    ScanNumDialog(object : OnClickInterface.OnClickInterface {
                                                        override fun onResult(x1: String, x2: String) {
                                                            if (isInteger(x1)) {
                                                                val mScanSun = item.totalQty - item.unLoadQty
                                                                if (x1.toInt() > mScanSun) {
                                                                    showToast("您输入的数量已经超过货物剩余的数量")
                                                                    return
                                                                }
                                                                val scanBuilder = StringBuilder()
                                                                for (index in ((mScanSun - x1.toInt()) + 1)..mScanSun) {
                                                                    val endBillno = if (index.toString().length == 1) "000$index" else if (index.toString().length == 2) "00$index" else if (index.toString().length == 3) "0$index" else if (index.toString().length == 4) "$index" else ""

                                                                    scanBuilder.append(s1.substring(0, s1.length - 4) + endBillno)
                                                                    if (index != mScanSun)
                                                                        scanBuilder.append(",")
                                                                }
                                                                mPresenter?.scanOrder(
                                                                        s1.substring(0, s1.length - 4),
                                                                        scanBuilder.toString(),
                                                                        PhoneDeviceMsgUtils.getDeviceOnlyTag(mContext),
                                                                        JSONObject(mLastData).optString("inoneVehicleFlag"),
                                                                        item.ewebidCodeStr,
                                                                        item.ewebidCode.toString(),
                                                                        item.ewebidCodeStr,
                                                                        //不可以使用进度条的进度 传给后台的是需要达到的进度
                                                                        (((totalLoadingNum - (mTotalUnLoadingNum - (scanBuilder.toString().split(",").lastIndex + 1))) * 100) / totalLoadingNum).toString())
                                                            }
                                                        }

                                                    }).show(supportFragmentManager, "ScanNumDialogNext")

                                                    /**
                                                     * 多件扫描end------------------------------------------------------
                                                     */
                                                }else{
                                                    mPresenter?.scanOrder(
                                                            s1.substring(0, s1.length - 4),
                                                            s1,
                                                            PhoneDeviceMsgUtils.getDeviceOnlyTag(mContext),
                                                            JSONObject(mLastData).optString("inoneVehicleFlag"),
                                                            item.ewebidCodeStr,
                                                            item.ewebidCode.toString(),
                                                            item.ewebidCodeStr,
                                                            //不可以使用进度条的进度 传给后台的是需要达到的进度
                                                            (((totalLoadingNum - (mTotalUnLoadingNum - 1)) * 100) / totalLoadingNum).toString()
                                                    )
                                                }

                                            }
                                        }
                                        /**
                                         * 第一次扫描货物
                                         */


                                        if (!isHasOrder) {
                                            mPresenter?.getWillByInfo(s1.substring(0, s1.length - 4), s1)
                                        }
                                        /**
                                         * 本车为空 什么货物都没有
                                         */

                                    } else
                                        mPresenter?.getWillByInfo(s1.substring(0, s1.length - 4), s1)

                                }
                            }

                        }).show(supportFragmentManager, "ScanDialogFragment")
                    } else {
                        // Oups permission denied
                        TalkSureDialog(mContext, getScreenWidth(), "权限未赋予！照相机无法启动！请联系在线客服或手动进入系统设置授予摄像头权限！").show()

                    }
                }

    }

    override fun getRecyclerViewId(): Int = R.id.departure_vehicles_unplan_scan_operating_recycler

    override fun setAdapter(): BaseRecyclerAdapter<DepartureTrunkDepartureUnPlanScanOperatingBean> = DepartureTrunkDepartureUnPlanScanOperatingAdapter(mContext)

    override fun getWillByInfoS(data: JSONObject, resultBillno: String) {
        //3在途
        if (data.optInt("billState") != 3) {
            //(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, ewebidCodeStr: String, scanPercentage: String) {
            val obj = JSONObject(mLastData)
            val inoneV = obj.optString("inoneVehicleFlag")
            //ScanWebidType 扫描网点类型  默认0 1代表只装所选到货网点 2不限到货网点
            if (obj.optString("ScanWebidType") == "1" || obj.optString("scanWebidType") == "1") {
                if (data.optString("ewebidCodeStr") != obj.optString("ewebidCodeStr")) {
                    soundPoolMap?.get(SCAN_SOUND_ERROR_TAG)?.let { mSoundPool?.play(it, 1f, 1f, 0, 0, 1f) }
                    return
                }
            }
            val mScanSun = data.optInt("qty", 0)

            if (mScanSun > 20) {
                ScanNumDialog(object : OnClickInterface.OnClickInterface {
                    override fun onResult(s1: String, s2: String) {
                        if (isInteger(s1)) {
                            if (s1.toInt() > data.optInt("qty")) {
                                showToast("您输入的数量已经超过货物的数量")
                                return
                            }
                            val scanBuilder = StringBuilder()
                            for (index in ((mScanSun - s1.toInt()) + 1)..mScanSun) {
                                val endBillno = if (index.toString().length == 1) "000$index" else if (index.toString().length == 2) "00$index" else if (index.toString().length == 3) "0$index" else if (index.toString().length == 4) "$index" else ""

                                scanBuilder.append(resultBillno.substring(0, resultBillno.length - 4) + endBillno)
                                if (index != mScanSun)
                                    scanBuilder.append(",")
                            }
                            onFirstScanOrder(data, resultBillno, inoneV, scanBuilder.toString())
                        }
                    }

                }).show(supportFragmentManager, "ScanNumDialog")
                return
            }
            onFirstScanOrder(data, resultBillno, inoneV)

        } else {
            soundPoolMap?.get(SCAN_SOUND_ERROR_TAG)?.let { mSoundPool?.play(it, 1f, 1f, 0, 0, 1f) }
            TalkSureDialog(mContext, getScreenWidth(), "该运单已经在途，请核实后重试").show()
        }
    }

    override fun getWillByInfoNull() {
        soundPoolMap?.get(SCAN_SOUND_ERROR_TAG)?.let { mSoundPool?.play(it, 1f, 1f, 0, 0, 1f) }

    }
    fun onFirstScanOrder(data: JSONObject, resultBillno: String, inoneV: String) {
        onFirstScanOrder(data, resultBillno, inoneV, "")

    }

    fun onFirstScanOrder(data: JSONObject, resultBillno: String, inoneV: String, moreScanStr: String) {
        val iodjjk = Gson().fromJson(GsonUtils.toPrettyFormat(data), DepartureTrunkDepartureUnPlanScanOperatingBean::class.java)
        iodjjk.ewebidCodeStrGx = iodjjk.ewebidCodeStr
        iodjjk.webidCodeStrGx = iodjjk.webidCodeStr
        adapter.appendData(mutableListOf(iodjjk))
        val allnum = (totalLoadingNum + data.optInt("totalQty"))
        val edNum = if (moreScanStr.split(",").isNullOrEmpty()) (allnum - mTotalUnLoadingNum - data.optInt("totalQty")) + 1 else (allnum - mTotalUnLoadingNum - data.optInt("totalQty")) + moreScanStr.split(",").lastIndex + 1
        //(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, ewebidCodeStr: String, scanPercentage: String) {
        mPresenter?.scanOrder(
                resultBillno.substring(0, resultBillno.length - 4),
                moreScanStr,
                PhoneDeviceMsgUtils.getDeviceOnlyTag(mContext),
                inoneV,
                data.optString("ewebidCodeStr"),
                data.optString("ewebidCode"),
                data.optString("ewebidCodeStr"),
                (((edNum * 100) / allnum).toInt()).toString())
        mTts?.startSpeaking(data.optString("ewebidCodeStr"), null)

    }
    override fun scanOrderS(billno: String, soundStr: String, mMoreScanBillno: String) {
        for ((index, item) in adapter.getAllData().withIndex()) {
            if (item.billno == billno) {
                val ii = item
                if (mMoreScanBillno.split(",").isNullOrEmpty())
                    ii.unLoadQty = item.unLoadQty + 1
                else
                    ii.unLoadQty = item.unLoadQty + mMoreScanBillno.split(",").lastIndex + 1
                adapter.notifyItemChangeds(index, ii)
            }
        }
        notifyMathChange()
    }

    @SuppressLint("SetTextI18n")
    fun notifyMathChange() {
        clearInfo()
        if (adapter.getAllData().isEmpty()) return
        for (item in adapter.getAllData()) {
            totalLoadingNum = (totalLoadingNum + item.totalQty)//本车全部货物数量+
            mTotalUnLoadingNum = (mTotalUnLoadingNum + (item.totalQty - item.unLoadQty))//全部未扫描数量
            mTotalUnLoadingVolume = (mTotalUnLoadingVolume + (item.volumn / item.totalQty) * (item.totalQty - item.unLoadQty))//全部未扫描体积
            mTotalLoadingVolume = (mTotalLoadingVolume + (item.volumn / item.totalQty) * item.unLoadQty)//全部扫描体积
            mTotalUnLoadingWeight = (mTotalUnLoadingWeight + (item.weight / item.totalQty) * (item.totalQty - item.unLoadQty))//全部未扫描重量
            mTotalLoadingWeight = (mTotalLoadingWeight + (item.weight / item.totalQty) * item.unLoadQty)//全部扫描重量
            if (item.unLoadQty != item.totalQty) {
                mTotalUnLoadingOrderNum = (mTotalUnLoadingOrderNum + 1)//全部未扫描单子
            } else {
                mTotalLoadingOrderNum = (mTotalLoadingOrderNum + 1)//全部扫描单子

            }
        }
        scan_progressBar.progress = (((totalLoadingNum - mTotalUnLoadingNum) * 100) / totalLoadingNum)
        scan_number_total_tv.text = "${totalLoadingNum - mTotalUnLoadingNum} / $totalLoadingNum"
        unScan_info_tv.text = "未扫： ${mTotalUnLoadingNum}件 ${haveTwoDouble(mTotalUnLoadingWeight)}kg  ${haveTwoDouble(mTotalUnLoadingVolume)}m³             扫描人:${UserInformationUtil.getUserName(mContext)}"
        scaned_info__tv.text = "已扫：${mTotalLoadingOrderNum}票 ${totalLoadingNum - mTotalUnLoadingNum}件 ${haveTwoDouble(mTotalLoadingWeight)}kg  ${haveTwoDouble(mTotalLoadingVolume)}m³             金额:xxxx"


    }
    override fun saveScanPostS(result: String) {
        val obj = JSONObject(mLastData)
        TalkSureDialog(mContext, getScreenWidth(), "车次为${obj.optString("inoneVehicleFlag")}的无计划车辆已经扫描发车，点击此处返回！") {
            onBackPressed()
        }.show()
    }

    override fun getCarInfoS(list: List<DepartureTrunkDepartureUnPlanScanOperatingBean>) {
        if (!adapter.getAllData().isNullOrEmpty()) {
            adapter.clearData()
        }
        adapter.appendData(list)
        notifyMathChange()

    }
}