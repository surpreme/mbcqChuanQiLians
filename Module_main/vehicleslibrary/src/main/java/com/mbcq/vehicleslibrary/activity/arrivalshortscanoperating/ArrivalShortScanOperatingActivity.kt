package com.mbcq.vehicleslibrary.activity.arrivalshortscanoperating


import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.billy.android.swipe.SmartSwipe
import com.billy.android.swipe.SmartSwipeWrapper
import com.billy.android.swipe.SwipeConsumer
import com.billy.android.swipe.consumer.SpaceConsumer
import com.billy.android.swipe.listener.SimpleSwipeListener
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.dialog.popup.XDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.DialogFragmentUtils
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean
import com.mbcq.commonlibrary.dialog.BottomOptionsDialog
import com.mbcq.commonlibrary.scan.scanlogin.ScanDialogFragment
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.fragment.ScanNumDialog
import kotlinx.android.synthetic.main.activity_arrival_short_scan_operating.*
import org.json.JSONObject
import java.lang.StringBuilder

/**
 * @author: lzy
 * @time: 2021-03-19 10:30:12 短驳到车扫描
 */

@Route(path = ARouterConstants.ArrivalShortScanOperatingActivity)
class ArrivalShortScanOperatingActivity : BaseArrivalShortScanOperatingActivity<ArrivalShortScanOperatingContract.View, ArrivalShortScanOperatingPresenter, ArrivalShortScanOperatingBean>(), ArrivalShortScanOperatingContract.View {
    @Autowired(name = "ArrivalVehicles")
    @JvmField
    var mLastData: String = ""

    override fun getLayoutId(): Int = R.layout.activity_arrival_short_scan_operating
    override fun getRecyclerViewId(): Int = R.id.arrival_vehicles_scan_operating_recycler

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        //侧滑动画
        SmartSwipe.wrap(this)
                .addConsumer(SpaceConsumer())
                .enableLeft() //启用左右两侧侧滑
                .addListener(object : SimpleSwipeListener() {
                    override fun onSwipeOpened(wrapper: SmartSwipeWrapper?, consumer: SwipeConsumer, direction: Int) {
                        super.onSwipeOpened(wrapper, consumer, direction)
                        if (direction == SwipeConsumer.DIRECTION_LEFT) {
                            lookLocalCarScanMoreInfo()
                        }
                    }
                })
    }

    fun lookLocalCarScanMoreInfo() {
        val obj = JSONObject()
        obj.put("lookType", 2)
        obj.put("inoneVehicleFlag", JSONObject(mLastData).optString("inoneVehicleFlag"))
        ARouter.getInstance().build(ARouterConstants.ArrivalShortScanOperatingMoreInfoActivityActivity).withString("ArrivalShortScanOperatingMoreInfo", GsonUtils.toPrettyFormat(obj)).navigation()

    }

    override fun onResume() {
        super.onResume()
        refreshScanInfo()
    }

    @SuppressLint("SetTextI18n")
    fun refreshScanInfo() {
        val obj = JSONObject(mLastData)
        mPresenter?.getCarInfo(obj.optString("inoneVehicleFlag"))
        unloading_batch_tv.text = "卸车批次：${obj.optString("inoneVehicleFlag")}"
        unScan_info__tv.text = "未扫：0票 0件 0kg  0m³             扫描人:${UserInformationUtil.getUserName(mContext)}"
    }

    override fun onClick() {
        super.onClick()
        look_more_info_btn.apply {
            onSingleClicks {
                lookLocalCarScanMoreInfo()
            }
        }
        type_tv.apply {
            onSingleClicks {
                onFilterRecyclerData()
            }
        }
        scan_number_iv.apply {
            onSingleClicks {
                getCameraPermission()
            }
        }
        search_btn.apply {
            onSingleClicks {
                if (billno_ed.text.toString().length < 5) {
                    showToast("请检查扫描编码后重试")
                    return@onSingleClicks
                }
                scanSuccess(billno_ed.text.toString(), true)

            }
        }
    }

    override fun setAdapter(): BaseRecyclerAdapter<ArrivalShortScanOperatingBean> = ArrivalShortScanOperatingAdapter(mContext).also {
        it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            @SuppressLint("SetTextI18n")
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectBean = Gson().fromJson<ArrivalShortScanOperatingBean>(mResult, ArrivalShortScanOperatingBean::class.java)
                if (mSelectBean.totalQty in 1..20) {
                    mPresenter?.getClickLable(mSelectBean.billno, mSelectBean.inoneVehicleFlag, mSelectBean.totalQty)

                } else if (mSelectBean.totalQty in 21..9999) {
                    billno_ed.setText("${mSelectBean.billno}0001")
                }
            }

        }
        it.mOnLookInformationInterface = object : ArrivalShortScanOperatingAdapter.OnLookInformationInterface {
            override fun lookInfo(v: View, position: Int, data: ArrivalShortScanOperatingBean) {
                val obj = JSONObject()
                obj.put("lookType", 1)
                obj.put("billno", data.billno)
                obj.put("inoneVehicleFlag", data.inoneVehicleFlag)
                obj.put("totalQty", data.totalQty)
                ARouter.getInstance().build(ARouterConstants.ArrivalShortScanOperatingMoreInfoActivityActivity).withString("ArrivalShortScanOperatingMoreInfo", GsonUtils.toPrettyFormat(obj)).navigation()

            }

            override fun lookAllInfo(v: View, position: Int, data: ArrivalShortScanOperatingBean) {

            }

        }

    }

    fun onFilterRecyclerData() {
        val list = mutableListOf<BaseTextAdapterBean>()
        /**
        按扫描先后
        按扫描率
        按计划外
        按计划
         */
        for (index in 0..1) {
            val mBaseTextAdapterBean = BaseTextAdapterBean()
            mBaseTextAdapterBean.title = if (index == 0) "默认" else "按扫描率"
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
                            type_tv.text = if (mResult == "0") "默认" else "按扫描率"
                            when (mResult) {
                                "0" -> {
                                    refreshScanInfo()
                                }
                                "1" -> {
                                    showLoading()
                                    /**
                                     * 扫描率算法要跟recyclerview adapter统一
                                     *
                                     */
                                    //-1 前 1后
                                    adapter.sortWith(Comparator { o1, o2 ->
                                        val mO1Progress = if (o1.loadQty == 0)
                                            0
                                        else if (o1.loadQty == o1.qty)
                                            100
                                        else
                                            ((o1.loadQty * 100) / (o1.qty))
                                        val mO2Progress = if (o2.loadQty == 0)
                                            0
                                        else if (o2.loadQty == o2.qty)
                                            100
                                        else
                                            ((o2.loadQty * 100) / (o2.qty))

                                        if (mO1Progress >= mO2Progress) 1 else -1
                                    })
                                    closeLoading()
                                }
                            }
                        }

                    }
                })
                .showUp(type_tv)
    }

    fun scanSuccess(s1: String, isHeaderPint: Boolean) {
        if (DialogFragmentUtils.getIsShowDialogFragment(this))
            return
        if (s1.length > 5) {
            val obj = JSONObject(mLastData)

            var mIsOrderNumber = false//是否为运单号 假如你拿自己的微信名片就不可以
            for (item in adapter.getAllData()) {
                if (item.billno == s1.substring(0, s1.length - 4)) {
                    mIsOrderNumber = true
                    if (item.totalQty > 20) {
                        val mUnLoadNum = (item.qty - item.loadQty)
                        ScanNumDialog(mUnLoadNum, 1, object : OnClickInterface.OnClickInterface {
                            override fun onResult(x1: String, x2: String) {
                                if (isInteger(x1)) {
                                    val mScanSun = mUnLoadNum
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
//                                    Log.e("ggggggg", "onResult: " + scanBuilder.toString())
//                                    if (true)return
                                    mPresenter?.scanOrder(
                                            s1.substring(0, s1.length - 4),
                                            if (scanBuilder.toString().contains(",")) scanBuilder.toString() else "$scanBuilder,",
                                            obj.optString("inoneVehicleFlag"),
                                            item.ewebidCodeStr,
                                            if (isHeaderPint) 1 else 0,
                                            item.totalQty,
                                            (((totalLoadingNum - (mTotalUnLoadingNum - (scanBuilder.toString().split(",").lastIndex + 1))) * 100) / totalLoadingNum).toString()

                                    )

                                }
                            }

                        }).show(supportFragmentManager, "ScanDialogFragment")

                    } else {
                        mPresenter?.scanOrder(
                                s1.substring(0, s1.length - 4),
                                s1,
                                obj.optString("inoneVehicleFlag"),
                                item.ewebidCodeStr,
                                if (isHeaderPint) 1 else 0,
                                item.totalQty,
                                haveTwoDouble(((((totalLoadingNum - (mTotalUnLoadingNum - 1)) * 100) / totalLoadingNum).toDouble()))
                        )
                    }
                    break
                }
            }
            if (!mIsOrderNumber) {
                showError("标签号非本车货物,请检查您的标签号稍后重试")

            }

        } else {
            showError("标签号过短,请检查您的标签号稍后重试")
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
                                    override fun onFinish() {
                                        if (!isDestroyed)
                                            scanSuccess(s1, false)

                                    }

                                    override fun onTick(millisUntilFinished: Long) {

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

    override fun onPDAScanResult(result: String) {
        scanSuccess(result, false)

    }

    override fun getCarInfoS(list: List<ArrivalShortScanOperatingBean>, id: Int) {
        if (!adapter.getAllData().isNullOrEmpty()) {
            adapter.clearData()
        }
        adapter.appendData(list)
        notifyMathChange()
    }

    override fun scanOrderS(billno: String, soundStr: String, mMoreScanBillno: String) {
        var mXScannLable = mMoreScanBillno
        if (mMoreScanBillno.endsWith(","))
            mXScannLable = mMoreScanBillno.substring(0, mMoreScanBillno.length - 1)
        for ((index, item) in adapter.getAllData().withIndex()) {
            if (item.billno == billno) {
                val ii = item
                val mScanO: Int = if (mXScannLable.split(",").isNullOrEmpty()) 1 else mXScannLable.split(",").lastIndex + 1
                ii.loadQty += mScanO
                adapter.notifyItemChangeds(index, ii)
                notifyMathChange()
            }
        }
        mTts?.startSpeaking(soundStr, null)
    }

    override fun getClickLableS(result: String) {
        billno_ed.setText(result.replace(",", ""))

    }

    @SuppressLint("SetTextI18n")
    fun notifyMathChange() {
        clearInfo()
        if (adapter.getAllData().isEmpty()) return
        for (item in adapter.getAllData()) {
            val mUnLoadNum = (item.qty - item.loadQty)
            totalLoadingNum = (totalLoadingNum + item.qty) //本车全部货物数量+
            mTotalUnLoadingNum = (mTotalUnLoadingNum + mUnLoadNum)//全部未扫描数量
            mTotalUnLoadingVolume = (mTotalUnLoadingVolume + (item.volumn / item.totalQty) * mUnLoadNum)//全部未扫描体积
            mTotalLoadingVolume = (mTotalLoadingVolume + (item.volumn / item.totalQty) * item.loadQty)//全部扫描体积
            mTotalUnLoadingWeight = (mTotalUnLoadingWeight + (item.weight / item.totalQty) * mUnLoadNum)//全部未扫描重量
            mTotalLoadingWeight = (mTotalLoadingWeight + (item.weight / item.totalQty) * item.loadQty)//全部扫描重量
            if (mUnLoadNum != 0) {
                mTotalUnLoadingOrderNum = (mTotalUnLoadingOrderNum + 1)//全部未扫描单子
            } else {
                mTotalLoadingOrderNum = (mTotalLoadingOrderNum + 1)//全部扫描单子

            }
        }
        unScan_info__tv.text = "未扫：${mTotalUnLoadingOrderNum}票 ${mTotalUnLoadingNum}件 ${haveTwoDouble(mTotalUnLoadingWeight)}kg  ${haveTwoDouble(mTotalUnLoadingVolume)}m³             扫描人:${UserInformationUtil.getUserName(mContext)}"
        scaned_info__tv.text = "已扫：${mTotalLoadingOrderNum}票 ${totalLoadingNum - mTotalUnLoadingNum}件 ${haveTwoDouble(mTotalLoadingWeight)}kg  ${haveTwoDouble(mTotalLoadingVolume)}m³             金额:xxxx"
        scan_progressBar.progress = (((totalLoadingNum - mTotalUnLoadingNum) * 100) / totalLoadingNum)
        scan_number_total_tv.text = "${totalLoadingNum - mTotalUnLoadingNum} / $totalLoadingNum"
    }
}