package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating.revoke


import android.Manifest
import android.graphics.Color
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Gravity
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.util.system.PhoneDeviceMsgUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.CustomizeToastUtil
import com.mbcq.baselibrary.view.DialogFragmentUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.scan.pda.CommonScanPDAMVPListActivity
import com.mbcq.commonlibrary.scan.scanlogin.ScanDialogFragment
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.fragment.ScanNumDialog
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_revoke_short_trunk_departure_scan_operating.*
import java.lang.StringBuilder

/**
 * @author: lzy
 * @time: 2020-11-10 10:02:03 撤销短驳装车扫描
 */

@Route(path = ARouterConstants.RevokeShortTrunkDepartureScanOperatingActivity)
class RevokeShortTrunkDepartureScanOperatingActivity : CommonScanPDAMVPListActivity<RevokeShortTrunkDepartureScanOperatingContract.View, RevokeShortTrunkDepartureScanOperatingPresenter, RevokeShortTrunkDepartureScanOperatingBean>(), RevokeShortTrunkDepartureScanOperatingContract.View {
    @Autowired(name = "RevokeShortLoadingVehicles")
    @JvmField
    var mLastData: RevokeShortTrunkDepartureScanDataBean? = null

    /**
     * 已扫描数量
     */
    var mLoadingOrderNum = 0
    lateinit var rxPermissions: RxPermissions
    var mSoundPool: SoundPool? = null
    private var soundPoolMap: HashMap<Int, Int>? = null
    val SCAN_SOUND_ERROR_TAG = 1
//    override fun isShowErrorDialog(): Boolean = true

    override fun getLayoutId(): Int = R.layout.activity_revoke_short_trunk_departure_scan_operating

    override fun initExtra() {
        super.initExtra()
        rxPermissions = RxPermissions(this)
        ARouter.getInstance().inject(this)
        initSoundPool()

    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        mLastData?.let {
            mLoadingOrderNum = it.mTotalLoadingOrderNum - it.mTotalUnLoadingOrderNum

        }
    }

    override fun showError(msg: String) {
        LogUtils.e(msg)
        CustomizeToastUtil().Short(mContext, msg).setGravity(Gravity.CENTER).setToastBackground(Color.WHITE, R.drawable.toast_radius).show()

    }

    override fun initDatas() {
        super.initDatas()
        mLastData?.inoneVehicleFlag?.let {
            mPresenter?.getCarInfo(it)
        }
    }

    override fun onClick() {
        super.onClick()
        search_btn.apply {
            onSingleClicks {
                if (billno_ed.text.toString().length<5){
                    showToast("请检查您输入的标签号！")
                    return@onSingleClicks
                }
                scanSuccess(billno_ed.text.toString())
            }
        }
        scan_number_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getCameraPermission()
            }

        })
        short_trunk_departure_scan_operating_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    fun scanSuccess(s1: String) {
        if (DialogFragmentUtils.getIsShowDialogFragment(this))
            return
        if (s1.length > 5) {
            var isAdpterHase = false
            var mRevokeShortTrunkDepartureScanOperatingBean: RevokeShortTrunkDepartureScanOperatingBean? = null
            for (adpterItem in adapter.getAllData()) {
                if (adpterItem.billno == s1.substring(0, s1.length - 4)) {
                    isAdpterHase = true
                    mRevokeShortTrunkDepartureScanOperatingBean = adpterItem
                }

            }
            if (!isAdpterHase) {
                for (mCaritem in mCarList) {
                    if (mCaritem.billno == s1.substring(0, s1.length - 4))
                        mRevokeShortTrunkDepartureScanOperatingBean = mCaritem
                }
            }
            mLastData?.let { lastBean ->
                mRevokeShortTrunkDepartureScanOperatingBean?.let { mRmRevokeShortTrunkDepartureScanOperatingBean ->
                    mPresenter?.getScanData(s1.substring(0, s1.length - 4), s1, lastBean.inoneVehicleFlag, 0, mXRevokeShortTrunkDepartureScanOperatingBean = mRmRevokeShortTrunkDepartureScanOperatingBean)

                }

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
                                            scanSuccess(s1)
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

    fun initSoundPool() {
        mSoundPool = SoundPool(1, AudioManager.STREAM_ALARM, 0)
//        mSoundPool?.setOnLoadCompleteListener { soundPool, sampleId, status -> }
        soundPoolMap = HashMap<Int, Int>()
        mSoundPool?.let {
            soundPoolMap?.put(SCAN_SOUND_ERROR_TAG, it.load(this, com.mbcq.commonlibrary.R.raw.scan_success, 1))

        }
    }

    override fun revokeOrderS(result: String, mMoreScanBillno: String) {
        soundPoolMap?.get(SCAN_SOUND_ERROR_TAG)?.let { mSoundPool?.play(it, 1f, 1f, 0, 0, 1f) }
        mRemoveBillno.add(result)
        var isHas = false
        val mScanO: Int = if (mMoreScanBillno.split(",").isNullOrEmpty()) 1 else mMoreScanBillno.split(",").lastIndex + 1
        mLoadingOrderNum -= mScanO

        if (adapter.getAllData().isNotEmpty()) {
            for ((index, item) in adapter.getAllData().withIndex()) {
                if (item.billno == result) {
                    isHas = true
                    item.unLoadQty -= mScanO
                    item.waybillFcdQty += mScanO
                    adapter.notifyItemChangeds(index, item)
                }
            }
        }
        if (!isHas) {
            for (mItem in mCarList) {
                if (mItem.billno == result) {
                    mItem.unLoadQty -= mScanO
                    mItem.waybillFcdQty += mScanO
                    adapter.appendData(mutableListOf(mItem))
                }
            }
        }

    }


    override fun getRecyclerViewId(): Int = R.id.short_trunk_departure_scan_operating_recycler

    override fun setAdapter(): BaseRecyclerAdapter<RevokeShortTrunkDepartureScanOperatingBean> = RevokeShortTrunkDepartureScanOperatingAdapter(mContext)
    val mCarList = mutableListOf<RevokeShortTrunkDepartureScanOperatingBean>()
    val mRemoveBillno = mutableListOf<String>()
    override fun getCarInfoS(list: List<RevokeShortTrunkDepartureScanOperatingBean>) {
        if (mCarList.isNotEmpty()) {
            mCarList.clear()
        }
        mCarList.addAll(list)
    }

    override fun getScanDataS(list: ArrayList<Long>, lableNo: String, mRevokeShortTrunkDepartureScanOperatingBean: RevokeShortTrunkDepartureScanOperatingBean) {
        /////////////////////////////////////////////////////////////////////////////////
        val mXScanedLabelNoList = list //倒序已扫描的标签号
        val mXScanedNum = mXScanedLabelNoList.size //已扫描的数量
        //-1 前 1后 倒序
        mXScanedLabelNoList.sortWith(Comparator { o1, o2 ->
            if (o1 > o2) -1 else 1
        })
        /////////////////////////////////////////////////////////////////////////////////
        if (mRevokeShortTrunkDepartureScanOperatingBean.totalQty > 20) {
            ScanNumDialog(mXScanedNum, 2, object : OnClickInterface.OnClickInterface {
                override fun onResult(x1: String, x2: String) {
                    if (isInteger(x1)) {
                        if (x1.toInt() > mXScanedNum) {
                            showToast("您输入的数量已经超过已扫描货物的数量")
                            return
                        }
                        val mXPostScaningDataStr = StringBuilder()
                        /**
                         * "100300049060040,100300049060041"
                         */
                        for (index in (mXScanedNum - x1.toInt()) until mXScanedNum) {
                            mXPostScaningDataStr.append(mXScanedLabelNoList[index])
                            if (index != (mXScanedNum - 1))
                                mXPostScaningDataStr.append(",")
                        }
                        mLastData?.let {
                            mPresenter?.revokeOrder(lableNo.substring(0, lableNo.length - 4), mXPostScaningDataStr.toString(), PhoneDeviceMsgUtils.getDeviceOnlyTag(mContext), it.inoneVehicleFlag, "", (((((mLoadingOrderNum - x1.toInt()) * 100) / it.mTotalLoadingOrderNum).toDouble()).toString()))

                        }
                    }
                }

            }).show(supportFragmentManager, "ScanDialogFragment")
        } else {
            /**
             * 防止数据错误
             * @1 已扫件数不能大于总件数
             * @2 已扫件数必须大于0
             */
            if (mRevokeShortTrunkDepartureScanOperatingBean.unLoadQty <= mRevokeShortTrunkDepartureScanOperatingBean.totalQty && mRevokeShortTrunkDepartureScanOperatingBean.unLoadQty > 0)
                mLastData?.let {
                    mPresenter?.revokeOrder(lableNo.substring(0, lableNo.length - 4), lableNo, PhoneDeviceMsgUtils.getDeviceOnlyTag(mContext), it.inoneVehicleFlag, "", ((((mLoadingOrderNum - 1) * 100) / it.mTotalLoadingOrderNum).toString()))
                }
            else
                showError("货物已经全部撤销，请核实后重试")
        }
    }


    override fun onPDAScanResult(result: String) {
        scanSuccess(result)
    }
}