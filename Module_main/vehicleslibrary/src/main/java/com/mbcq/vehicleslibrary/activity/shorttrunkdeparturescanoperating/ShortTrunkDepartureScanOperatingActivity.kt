package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating


import android.Manifest
import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.billy.android.swipe.SmartSwipe
import com.billy.android.swipe.SmartSwipeWrapper
import com.billy.android.swipe.SwipeConsumer
import com.billy.android.swipe.consumer.SpaceConsumer
import com.billy.android.swipe.consumer.TranslucentSlidingConsumer
import com.billy.android.swipe.listener.SimpleSwipeListener
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.dialog.popup.XDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.util.system.PhoneDeviceMsgUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.DialogFragmentUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean
import com.mbcq.commonlibrary.dialog.BottomOptionsDialog
import com.mbcq.commonlibrary.scan.scanlogin.ScanDialogFragment
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating.revoke.RevokeShortTrunkDepartureScanDataBean
import com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperatingmoreinfo.ShortTrunkDepartureScanOperatingMoreInfoIntentBean
import com.mbcq.vehicleslibrary.fragment.ScanNumDialog
import kotlinx.android.synthetic.main.activity_short_trunk_departure_scan_operating.*
import org.json.JSONArray
import org.json.JSONObject
import java.lang.StringBuilder


/**
 * @author: lzy
 * @time: 2020-11-04 16:05:23 短驳有计划发车
 * 扫描有两个方法 一个无计划和有计划
 */

@Route(path = ARouterConstants.ShortTrunkDepartureScanOperatingActivity)
class ShortTrunkDepartureScanOperatingActivity : BaseShortTrunkDepartureScanOperatingActivity<ShortTrunkDepartureScanOperatingContract.View, ShortTrunkDepartureScanOperatingPresenter, ShortTrunkDepartureScanOperatingBean>(), ShortTrunkDepartureScanOperatingContract.View {
    @Autowired(name = "ShortLoadingVehicles")
    @JvmField
    var mLastData: String = ""

    override fun getLayoutId(): Int = R.layout.activity_short_trunk_departure_scan_operating

    /**
     * 刷新
     */
    @SuppressLint("SetTextI18n")
    fun refreshScanInfo() {
        clearInfo()
        val obj = JSONObject(mLastData)
        mPresenter?.getCarInfo(obj.optString("inoneVehicleFlag"))
        unloading_batch_tv.text = "卸车批次:${obj.optString("inoneVehicleFlag")}"
        unScan_info_tv.text = "未扫：0票 0件 0kg  0m³             扫描人:${UserInformationUtil.getUserName(mContext)}"
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        refreshScanInfo()
    }

    fun onFilterRecyclerData() {
        val list = mutableListOf<BaseTextAdapterBean>()
        /**
        按扫描先后
        按扫描率
        按计划外
        按计划
         */
        for (index in 0..3) {
            val mBaseTextAdapterBean = BaseTextAdapterBean()
            mBaseTextAdapterBean.title = if (index == 0) "默认" else if (index == 1) "按扫描率" else if (index == 2) "按计划外" else "按计划"
            mBaseTextAdapterBean.tag = index.toString()
            list.add(mBaseTextAdapterBean)
        }
        XDialog.Builder(mContext)
                .setContentView(R.layout.dialog_bottom_options)
//                        .setWidth(type_tv.width)
                .setIsDarkWindow(false)
                .asCustom(BottomOptionsDialog(mContext, list).also {
                    it.mOnRecyclerClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                        override fun onItemClick(v: View, position: Int, mResult: String) {
                            type_tv.text = if (mResult == "0") "默认" else if (mResult == "1") "按扫描率" else if (mResult == "2") "按计划外" else "按计划"
                            when (mResult) {
                                "0" -> {
                                    refreshScanInfo()
                                }
                                "1" -> {
                                    showLoading()
                                    //-1 前 1后
                                    adapter.sortWith(Comparator { o1, o2 ->
                                        val mO1Progress = if (o1.unLoadQty == 0) 0 else if (o1.unLoadQty == (o1.unLoadQty + o1.waybillFcdQty)) 100 else ((o1.unLoadQty * 100) / (o1.unLoadQty + o1.waybillFcdQty))
                                        val mO2Progress = if (o2.unLoadQty == 0) 0 else if (o2.unLoadQty == (o2.unLoadQty + o2.waybillFcdQty)) 100 else ((o2.unLoadQty * 100) / (o2.unLoadQty + o2.waybillFcdQty))

                                        if (mO1Progress >= mO2Progress) 1 else -1
                                    })
                                    closeLoading()
                                }
                                "2" -> {
                                    showLoading()
                                    //-1 前 1后
                                    adapter.sortWith(Comparator { o1, o2 ->
                                        if (o1.isScanDet == "2" && o2.isScanDet != "2") -1 else 1
                                    })
                                    closeLoading()
                                }
                                "3" -> {
                                    showLoading()
                                    //-1 前 1后
                                    adapter.sortWith(Comparator { o1, o2 ->
                                        if (o1.isScanDet == "2" && o2.isScanDet != "2") 1 else -1
                                    })
                                    closeLoading()
                                }
                            }
                        }

                    }
                })
                .showUp(type_tv)
    }

    override fun onClick() {
        super.onClick()
        //侧滑动画
        SmartSwipe.wrap(this)
                .addConsumer(SpaceConsumer())
                .enableLeft() //启用左右两侧侧滑
                .addListener(object : SimpleSwipeListener() {
                    override fun onSwipeOpened(wrapper: SmartSwipeWrapper?, consumer: SwipeConsumer, direction: Int) {
                        super.onSwipeOpened(wrapper, consumer, direction)
                        if (direction == SwipeConsumer.DIRECTION_LEFT) {
                            val mBean = ShortTrunkDepartureScanOperatingMoreInfoIntentBean()
                            mBean.inOneVehicleFlag = JSONObject(mLastData).optString("inoneVehicleFlag")
                            mBean.setmType(1)
                            ARouter.getInstance().build(ARouterConstants.ShortTrunkDepartureScanOperatingMoreInfoActivity).withSerializable("ShortScanInfo", mBean).navigation()
                        }
                    }
                })
        look_local_car_info_tv.apply {
            onSingleClicks {
                val mBean = ShortTrunkDepartureScanOperatingMoreInfoIntentBean()
                mBean.inOneVehicleFlag = JSONObject(mLastData).optString("inoneVehicleFlag")
                mBean.setmType(1)
                ARouter.getInstance().build(ARouterConstants.ShortTrunkDepartureScanOperatingMoreInfoActivity).withSerializable("ShortScanInfo", mBean).navigation()
            }
        }
        type_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onFilterRecyclerData()

            }

        })
        inventory_btn.apply {
            onSingleClicks {
                ARouter.getInstance().build(ARouterConstants.ShortHouseChecklistActivity).navigation()
            }
        }
        search_btn.apply {
            onSingleClicks {
                if (billno_ed.text.toString().isBlank() || billno_ed.text.toString().length < 5) {
                    showToast("请检查扫描编码后重试")
                    return@onSingleClicks
                }
                scanSuccess(billno_ed.text.toString(), true)
            }
        }
        save_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                val mWithoutScanBillno = mutableListOf<String>()
                for (item in adapter.getAllData()) {
                    if (item.unLoadQty == 0) {
                        mWithoutScanBillno.add(item.billno)
                    }
                }
                if (mWithoutScanBillno.isEmpty()) {
                    commitScanInfo()

                } else {
                    mPresenter?.deleteUnScanOrder(mWithoutScanBillno[0], JSONObject(mLastData).optString("inoneVehicleFlag"), "0", mWithoutScanBillno)
                }

            }

        })
        short_vehicles_scan_operating_toolbar.setRightTitleOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                val mRevokeShortTrunkDepartureScanDataBean = RevokeShortTrunkDepartureScanDataBean()
                mRevokeShortTrunkDepartureScanDataBean.inoneVehicleFlag = JSONObject(mLastData).optString("inoneVehicleFlag")
                mRevokeShortTrunkDepartureScanDataBean.mTotalUnLoadingOrderNum = mTotalUnLoadingNum
                mRevokeShortTrunkDepartureScanDataBean.mTotalLoadingOrderNum = totalLoadingNum
                ARouter.getInstance().build(ARouterConstants.RevokeShortTrunkDepartureScanOperatingActivity).withSerializable("RevokeShortLoadingVehicles", mRevokeShortTrunkDepartureScanDataBean).navigation()
            }

        })
        scan_number_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getCameraPermission()
            }

        })

    }

    fun commitScanInfo() {
        val unScanOverBillno = StringBuilder()
        for (item in adapter.getAllData()) {
            if (item.unLoadQty != item.totalQty && item.unLoadQty != 0) {
                unScanOverBillno.append(item.billno).append("  ")
            }
        }
        val mXXXWeightTips = if (mTotalLoadingWeight > (mMaximumVehicleWeight * 1000)) "本车已超载，" else ""
        val mXXXMoreCarTips = if (unScanOverBillno.isBlank()) "您确认要完成发车批次为${JSONObject(mLastData).optString("inoneVehicleFlag")}的发车吗？" else "运单号为${unScanOverBillno.toString()}为拆票运单，请确认无误后发车！"
        TalkSureCancelDialog(mContext, getScreenWidth(), mXXXWeightTips + mXXXMoreCarTips) {
            val modifyData = JSONObject(mLastData)
            mPresenter?.saveScanPost(modifyData.optInt("id"), modifyData.optString("inoneVehicleFlag"))
        }.show()
    }

    fun scanSuccess(s1: String, isHeaderPint: Boolean) {
        if (DialogFragmentUtils.getIsShowDialogFragment(this))
            return
        if (s1.length > 5) {
            val obj = JSONObject(mLastData)
            var soundString = "无计划地址"
            for (item in adapter.getAllData()) {
                if (item.billno == s1.substring(0, s1.length - 4)) {
                    soundString = item.ewebidCodeStr
                    if (item.totalQty > 20) {
                        if (item.waybillFcdQty <= 0) {
                            showToast("该票${item.billno}已经扫描完毕")
                            return
                        }
                        ScanNumDialog(item.waybillFcdQty, 1, object : OnClickInterface.OnClickInterface {
                            override fun onResult(x1: String, x2: String) {
                                if (isInteger(x1)) {
                                    val mScanSun = item.waybillFcdQty
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
                                    val mOutPintO = (scanBuilder.toString().split(",").lastIndex + 1)
                                    mPresenter?.scanOrder(
                                            s1.substring(0, s1.length - 4),
                                            scanBuilder.toString(),
                                            PhoneDeviceMsgUtils.getDeviceOnlyTag(mContext),
                                            obj.optString("inoneVehicleFlag"),
                                            soundString,
                                            item.ewebidCode.toString(),
                                            (((totalLoadingNum - (mTotalUnLoadingNum - mOutPintO)) * 100) / totalLoadingNum).toString(),
                                            item.totalQty,
                                            if (isHeaderPint) 1 else 0

                                    )

                                }
                            }

                        }).show(supportFragmentManager, "ScanDialogFragment")
                    } else{
                        if (s1.substring(s1.length - 4, s1.length).toInt() > item.totalQty) {
                            showError("标签号$s1 异常!请核对件数后重试！")
                            return
                        }
                        mPresenter?.scanOrder(
                                s1.substring(0, s1.length - 4),
                                s1,
                                PhoneDeviceMsgUtils.getDeviceOnlyTag(mContext),
                                obj.optString("inoneVehicleFlag"),
                                soundString,
                                item.ewebidCode.toString(),
                                (((totalLoadingNum - (mTotalUnLoadingNum - 1)) * 100) / totalLoadingNum).toString(),
                                item.totalQty,
                                if (isHeaderPint) 1 else 0

                        )
                    }

                }
            }
            /**
             * 有计划可以添加计划外的
             */
            if (soundString == "无计划地址") {
                mPresenter?.getWillByInfo(s1.substring(0, s1.length - 4), s1, if (isHeaderPint) 1 else 0)


            }
        }
    }

    fun getCameraPermission() {
        rxPermissions.request(Manifest.permission.CAMERA)
                .subscribe { granted ->
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        ScanDialogFragment(getScreenWidth(), null, object : OnClickInterface.OnClickInterface {
                            override fun onResult(s1: String, s2: String) {
                                object : CountDownTimer(1000, 1000) {
                                    override fun onTick(millisUntilFinished: Long) {

                                    }

                                    override fun onFinish() {
                                        if (!isDestroyed)
                                            scanSuccess(s1, false)
                                    }

                                }.start()
                            }

                        }).show(supportFragmentManager, "ScanDialogFragment")
                    } else {
                        // Oups permission denied
                        TalkSureDialog(mContext, getScreenWidth(), "权限未赋予！照相机无法启动！请联系在线客服或手动进入系统设置授予摄像头权限！").show()

                    }
                }

    }


    override fun getRecyclerViewId(): Int = R.id.short_vehicles_scan_operating_recycler

    override fun setAdapter(): BaseRecyclerAdapter<ShortTrunkDepartureScanOperatingBean> = ShortTrunkDepartureScanOperatingAdapter(mContext).also {
        it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            @SuppressLint("SetTextI18n")
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectBean = Gson().fromJson<ShortTrunkDepartureScanOperatingBean>(mResult, ShortTrunkDepartureScanOperatingBean::class.java)
                if (mSelectBean.totalQty in 1..20) {
                    mPresenter?.getScanBillNoInfo(mSelectBean.billno, mSelectBean.totalQty)

                } else if (mSelectBean.totalQty in 21..9999) {
                    billno_ed.setText("${mSelectBean.billno}0001")
                }
            }

        }
        it.mOnLookInformationInterface = object : ShortTrunkDepartureScanOperatingAdapter.OnLookInformationInterface {
            override fun lookInfo(v: View, position: Int, data: ShortTrunkDepartureScanOperatingBean) {
                val testDataStr = Gson().toJson(data)
                val mBean = Gson().fromJson<ShortTrunkDepartureScanOperatingMoreInfoIntentBean>(testDataStr, ShortTrunkDepartureScanOperatingMoreInfoIntentBean::class.java)
                mBean.inOneVehicleFlag = JSONObject(mLastData).optString("inoneVehicleFlag")
                mBean.goodsTotalNum = data.totalQty
                ARouter.getInstance().build(ARouterConstants.ShortTrunkDepartureScanOperatingMoreInfoActivity).withSerializable("ShortScanInfo", mBean).navigation()
            }

            override fun lookAllInfo(v: View, position: Int, data: ShortTrunkDepartureScanOperatingBean) {
                val mBean = ShortTrunkDepartureScanOperatingMoreInfoIntentBean()
                mBean.inOneVehicleFlag = JSONObject(mLastData).optString("inoneVehicleFlag")
                mBean.setmType(1)
                ARouter.getInstance().build(ARouterConstants.ShortTrunkDepartureScanOperatingMoreInfoActivity).withSerializable("ShortScanInfo", mBean).navigation()
            }

        }
    }


    /**
     * unLoadQty 已扫数量
     */
    @SuppressLint("SetTextI18n")
    override fun getCarInfoS(list: List<ShortTrunkDepartureScanOperatingBean>) {
        if (!adapter.getAllData().isNullOrEmpty()) {
            adapter.clearData()
        }
        adapter.appendData(list)
        notifyMathChange()
    }

    override fun scanOrderS(billno: String, soundStr: String, mMoreScanBillno: String) {
        for ((index, item) in adapter.getAllData().withIndex()) {
            if (item.billno == billno) {
                val ii = item
                val mScanO: Int = if (mMoreScanBillno.split(",").isNullOrEmpty()) 1 else mMoreScanBillno.split(",").lastIndex + 1
                ii.unLoadQty = item.unLoadQty + mScanO
                ii.waybillFcdQty = item.waybillFcdQty - mScanO
                adapter.notifyItemChangeds(index, ii)
                notifyMathChange()
            }
        }
        mTts?.startSpeaking(soundStr, null)

    }

    /**
     * 这里是计划中车辆添加无计划运单的处理中心
     * 异常信息也是从非本车货物运单检测
     */
    override fun getWillByInfoS(data: JSONObject, resultBillno: String, mScanType: Int) {
        if (data.optString("webidCode") != UserInformationUtil.getWebIdCode(mContext)) {
            errorStep("该运单开单网点和扫描网点不匹配，请核实后重试")
            //(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, ewebidCodeStr: String, scanPercentage: String, mMoreScanBillno: String, mAbnormalReason: String) {
            mPresenter?.scanAbnormalOrder(
                    data.optString("billno"),
                    resultBillno,
                    PhoneDeviceMsgUtils.getDeviceOnlyTag(mContext),
                    JSONObject(mLastData).optString("inoneVehicleFlag"),
                    data.optString("ewebidCodeStr"),
                    data.optString("ewebidCode"),
                    data.optString("ewebidCodeStr"),
                    scan_progressBar.progress.toString(),
                    resultBillno,
                    "该运单开单网点和扫描网点不匹配"

            )
            return
        }
        //3在途data.optInt("billState") != 3
        if (true) {
            val obj = JSONObject(mLastData)
            val inoneV = obj.optString("inoneVehicleFlag")
            //ScanWebidType 扫描网点类型  默认0 1代表只装所选到货网点 2不限到货网点
            if (obj.optString("ScanWebidType") == "1" || obj.optString("scanWebidType") == "1") {
                if (data.optString("ewebidCodeStr") != obj.optString("ewebidCodeStr")) {
                    errorStep("该运单到货网点配置只送到货网点，请核实后重试")
                    //(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, ewebidCodeStr: String, scanPercentage: String, mMoreScanBillno: String, mAbnormalReason: String) {
                    mPresenter?.scanAbnormalOrder(
                            data.optString("billno"),
                            resultBillno,
                            PhoneDeviceMsgUtils.getDeviceOnlyTag(mContext),
                            JSONObject(mLastData).optString("inoneVehicleFlag"),
                            data.optString("ewebidCodeStr"),
                            data.optString("ewebidCode"),
                            data.optString("ewebidCodeStr"),
                            scan_progressBar.progress.toString(),
                            resultBillno,
                            "该运单到货网点配置只送到货网点"

                    )
                    return
                }
            }
            /**
             * 大件不管剩多少都是大件 跟剩余多少无关
             */
            val mScanSun = data.optInt("waybillFcdQty", 0)
            if (data.optInt("totalQty", 0) > 20) {
                ScanNumDialog(mScanSun, 1, object : OnClickInterface.OnClickInterface {
                    override fun onResult(s1: String, s2: String) {
                        if (isInteger(s1)) {
                            if (s1.toInt() > data.optInt("waybillFcdQty")) {
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
                            onFirstScanOrder(data, resultBillno, inoneV, scanBuilder.toString(), mScanType)
                        }
                    }

                }).show(supportFragmentManager, "ScanNumDialog")
                return
            }
            onFirstScanOrder(data, resultBillno, inoneV, mScanType)

        }
    }

    fun onFirstScanOrder(data: JSONObject, resultBillno: String, inoneV: String, mXScanType: Int) {
        onFirstScanOrder(data, resultBillno, inoneV, resultBillno, mXScanType)

    }

    fun onFirstScanOrder(data: JSONObject, resultBillno: String, inoneV: String, moreScanStr: String, mXScanType: Int) {
        val iodjjk = Gson().fromJson(GsonUtils.toPrettyFormat(data), ShortTrunkDepartureScanOperatingBean::class.java)
        iodjjk.ewebidCodeStrDb = iodjjk.ewebidCodeStr
        iodjjk.webidCodeStrDb = iodjjk.webidCodeStr
        adapter.appendData(mutableListOf(iodjjk))
        val allnum = (totalLoadingNum + data.optInt("waybillFcdQty"))
        val edNum = if (moreScanStr.split(",").isNullOrEmpty()) (allnum - mTotalUnLoadingNum - data.optInt("waybillFcdQty")) + 1 else (allnum - mTotalUnLoadingNum - data.optInt("waybillFcdQty")) + moreScanStr.split(",").lastIndex + 1
        //(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, ewebidCodeStr: String, scanPercentage: String) {
        mPresenter?.scanOrderUNPlan(
                data.optString("billno"),
                resultBillno,
                PhoneDeviceMsgUtils.getDeviceOnlyTag(mContext),
                inoneV,
                data.optString("ewebidCodeStr"),
                data.optString("ewebidCode"),
                (((edNum * 100) / allnum)).toString(),
                data.optInt("totalQty"),
                mXScanType

        )
    }

    @SuppressLint("SetTextI18n")
    fun notifyMathChange() {
        clearInfo()
        if (adapter.getAllData().isEmpty()) return
        for (item in adapter.getAllData()) {
            totalLoadingNum = (totalLoadingNum + (item.unLoadQty + item.waybillFcdQty))//本车全部货物数量+
            mTotalUnLoadingNum = (mTotalUnLoadingNum + item.waybillFcdQty)//全部未扫描数量
            mTotalUnLoadingVolume = (mTotalUnLoadingVolume + (item.volumn / item.totalQty) * item.waybillFcdQty)//全部未扫描体积
            mTotalLoadingVolume = (mTotalLoadingVolume + (item.volumn / item.totalQty) * item.unLoadQty)//全部扫描体积
            mTotalUnLoadingWeight = (mTotalUnLoadingWeight + (item.weight / item.totalQty) * item.waybillFcdQty)//全部未扫描重量
            mTotalLoadingWeight = (mTotalLoadingWeight + (item.weight / item.totalQty) * item.unLoadQty)//全部扫描重量
            if (item.waybillFcdQty != 0) {
                mTotalUnLoadingOrderNum = (mTotalUnLoadingOrderNum + 1)//全部未扫描单子
            } else {
                mTotalLoadingOrderNum = (mTotalLoadingOrderNum + 1)//全部扫描单子

            }
        }
        unScan_info_tv.text = "未扫：${mTotalUnLoadingOrderNum}票 ${mTotalUnLoadingNum}件 ${haveTwoDouble(mTotalUnLoadingWeight)}kg  ${haveTwoDouble(mTotalUnLoadingVolume)}m³             扫描人:${UserInformationUtil.getUserName(mContext)}"
        scaned_info__tv.text = "已扫：${mTotalLoadingOrderNum}票 ${totalLoadingNum - mTotalUnLoadingNum}件 ${haveTwoDouble(mTotalLoadingWeight)}kg  ${haveTwoDouble(mTotalLoadingVolume)}m³             金额:xxxx"
        scan_progressBar.progress = (((totalLoadingNum - mTotalUnLoadingNum) * 100) / totalLoadingNum)
        scan_number_total_tv.text = "${totalLoadingNum - mTotalUnLoadingNum} / $totalLoadingNum"
    }

    override fun getWillByInfoNull() {
        errorStep("该运单信息不存在，请核实后重试")

    }

    override fun isNotAtStock(billno: String) {
        errorStep("该运单$billno 不在库存，请核实后重试")
    }

    /**
     * 协程写法 不太喜欢堵塞 个人看法
    GlobalScope.launch { // 在后台启动一个新的协程并继续
    delay(500L) // 非阻塞的等待 0.5 秒钟（默认时间单位是毫秒）
    if (!isDestroyed) {
    xxxx
    }
    }
     */
    override fun againScanException(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, ewebidCodeStr: String, scanPercentage: String, mScanType: Int, errorStr: String) {
        errorStep(errorStr)
        refreshScanInfo()
        object : CountDownTimer(500, 500) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                if (!isDestroyed) {
                    mPresenter?.scanAbnormalOrder(
                            billno,
                            lableNo,
                            deviceNo,
                            inOneVehicleFlag,
                            ewebidCodeStr,
                            ewebidCode,
                            ewebidCodeStr,
                            scan_progressBar.progress.toString(),
                            lableNo,
                            errorStr)
                }
            }

        }.start()
    }

    override fun deleteUnScanOrderS(billno: String, inOneVehicleFlag: String, scanOpeType: String, withoutScanBillno: MutableList<String>, isOver: Boolean) {
        if (!isOver) {
            mPresenter?.deleteUnScanOrder(billno, inOneVehicleFlag, scanOpeType, withoutScanBillno)
        } else {
            commitScanInfo()

        }
    }

    override fun getScanBillNoInfoS(billno: String, result: String, totalQty: Int) {
        var mShowBillnoLable = ""
        val obj = JSONObject(result)
        val listAry = obj.optJSONArray("data")
        for (mCCCIndex in totalQty downTo 1) {
            val endBillno = billno + if (mCCCIndex.toString().length == 1) "000$mCCCIndex" else if (mCCCIndex.toString().length == 2) "00$mCCCIndex" else if (mCCCIndex.toString().length == 3) "0$mCCCIndex" else if (mCCCIndex.toString().length == 4) "$mCCCIndex" else ""
            listAry?.let {
                var isHas = false
                for (index in 0..it.length()) {
                    if (!it.isNull(index)) {
                        if (endBillno == it.getJSONObject(index).optString("lableNo")) {
                            isHas = true
                            continue
                        }
                    }
                }
                if (!isHas) {
                    mShowBillnoLable = endBillno
                }
            }
        }
        billno_ed.setText(mShowBillnoLable)
    }

    override fun saveScanPostS(result: String) {
        val obj = JSONObject(mLastData)
        TalkSureDialog(mContext, getScreenWidth(), "车次为${obj.optString("inoneVehicleFlag")}的车辆已经扫描发车，点击此处返回！") {
            onBackPressed()
        }.show()
    }

    override fun onPDAScanResult(result: String) {
        scanSuccess(result, false)
    }

    /**
     * 车载重 x/吨
     */
    var mMaximumVehicleWeight = 0.0
    override fun getVehicleS(result: String) {
        val jay = JSONArray(result)
        if (!jay.isNull(0)) {
            val obj = jay.getJSONObject(0)
            mMaximumVehicleWeight = obj.optDouble("supweight", 0.0)
        }
    }

}