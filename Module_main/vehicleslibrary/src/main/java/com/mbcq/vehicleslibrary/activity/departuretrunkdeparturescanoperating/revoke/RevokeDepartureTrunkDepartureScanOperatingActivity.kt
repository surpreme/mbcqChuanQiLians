package com.mbcq.vehicleslibrary.activity.departuretrunkdeparturescanoperating.revoke


import android.Manifest
import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.util.system.PhoneDeviceMsgUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.scan.scanlogin.ScanDialogFragment
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.fragment.ScanNumDialog
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_revoke_departure_trunk_departure_scan_operating.*
import org.json.JSONObject
import java.lang.StringBuilder

/**
 * @author: lzy
 * @time: 2020-11-19 08:31:06 撤销干线扫描
 */

@Route(path = ARouterConstants.RevokeDepartureTrunkDepartureScanOperatingActivity)
class RevokeDepartureTrunkDepartureScanOperatingActivity : BaseListMVPActivity<RevokeDepartureTrunkDepartureScanOperatingContract.View, RevokeDepartureTrunkDepartureScanOperatingPresenter, RevokeDepartureTrunkDepartureScanOperatingBean>(), RevokeDepartureTrunkDepartureScanOperatingContract.View {
    @Autowired(name = "RevokeDepartureLoadingVehicles")
    @JvmField
    var mLastData: RevokeDepartureTrunkDepartureScanDataBean? = null


    /**
     * 已扫描数量
     */
    var mLoadingOrderNum = 0


    lateinit var rxPermissions: RxPermissions
    var mSoundPool: SoundPool? = null
    private var soundPoolMap: HashMap<Int, Int>? = null
    val SCAN_SOUND_SUCCES_TAG = 1

    override fun getLayoutId(): Int = R.layout.activity_revoke_departure_trunk_departure_scan_operating

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

    override fun initDatas() {
        super.initDatas()
        mLastData?.inoneVehicleFlag?.let {
            mPresenter?.getCarInfo(it)
        }
    }

    override fun onClick() {
        super.onClick()
        scan_number_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getCameraPermission()
            }

        })
        departure_trunk_departure_scan_operating_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
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
                                    var isAdpterHase = false
                                    var mRevokeDepartureTrunkDepartureScanOperatingBean: RevokeDepartureTrunkDepartureScanOperatingBean = RevokeDepartureTrunkDepartureScanOperatingBean()
                                    for (adpterItem in adapter.getAllData()) {
                                        if (adpterItem.billno == s1.substring(0, s1.length - 4)) {
                                            isAdpterHase = true
                                            mRevokeDepartureTrunkDepartureScanOperatingBean = adpterItem
                                        }

                                    }
                                    if (!isAdpterHase) {
                                        for (mCaritem in mCarList) {
                                            mRevokeDepartureTrunkDepartureScanOperatingBean = mCaritem
                                        }
                                    }
                                    if (mRevokeDepartureTrunkDepartureScanOperatingBean.totalQty > 20) {
                                        ScanNumDialog(object : OnClickInterface.OnClickInterface {
                                            override fun onResult(x1: String, x2: String) {
                                                if (isInteger(x1)) {
                                                    val mScanSun = mRevokeDepartureTrunkDepartureScanOperatingBean.totalQty - mRevokeDepartureTrunkDepartureScanOperatingBean.unLoadQty
                                                    if (x1.toInt() > mRevokeDepartureTrunkDepartureScanOperatingBean.unLoadQty) {
                                                        showToast("您输入的数量已经超过已扫描货物的数量")
                                                        return
                                                    }
                                                    val scanBuilder = StringBuilder()
                                                    for (index in (mScanSun + 1)..(mScanSun + x1.toInt())) {
                                                        val endBillno = if (index.toString().length == 1) "000$index" else if (index.toString().length == 2) "00$index" else if (index.toString().length == 3) "0$index" else if (index.toString().length == 4) "$index" else ""
                                                        scanBuilder.append(s1.substring(0, s1.length - 4) + endBillno)
                                                        if (index != (mScanSun + x1.toInt()))
                                                            scanBuilder.append(",")
                                                    }
//                                                    val obj = JSONObject(mLastData)
                                                    mLastData?.let {
                                                        mPresenter?.revokeOrder(s1.substring(0, s1.length - 4), scanBuilder.toString(), PhoneDeviceMsgUtils.getDeviceOnlyTag(mContext), it.inoneVehicleFlag, "", (((((mLoadingOrderNum + x1.toInt()) * 100) / it.mTotalLoadingOrderNum).toDouble()).toString()))

                                                    }
                                                }
                                            }

                                        }).show(supportFragmentManager, "ScanDialogFragment")
                                    } else {
                                        mLastData?.let {
                                            mPresenter?.revokeOrder(s1.substring(0, s1.length - 4), s1, PhoneDeviceMsgUtils.getDeviceOnlyTag(mContext), it.inoneVehicleFlag, "", ((((mLoadingOrderNum - 1) * 100) / it.mTotalLoadingOrderNum).toString()))

                                        }
                                    }

                                }
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
            soundPoolMap?.put(SCAN_SOUND_SUCCES_TAG, it.load(this, com.mbcq.commonlibrary.R.raw.scan_success, 1))

        }
    }

    override fun revokeOrderS(result: String, mMoreScanBillno: String) {
        soundPoolMap?.get(SCAN_SOUND_SUCCES_TAG)?.let { mSoundPool?.play(it, 1f, 1f, 0, 0, 1f) }
        mRemoveBillno.add(result)
        var isHas = false
        val mScanO: Int = if (mMoreScanBillno.split(",").isNullOrEmpty()) 1 else mMoreScanBillno.split(",").lastIndex + 1
        mLoadingOrderNum -= mScanO
        if (adapter.getAllData().isNotEmpty()) {
            for ((index, item) in adapter.getAllData().withIndex()) {
                if (item.billno == result) {
                    isHas = true
                    item.unLoadQty -= mScanO
                    adapter.notifyItemChangeds(index, item)
                }
            }
        }
        if (!isHas) {
            for (mItem in mCarList) {
                if (mItem.billno == result) {
                    mItem.unLoadQty -= mScanO
                    adapter.appendData(mutableListOf(mItem))
                }
            }
        }
    }

    val mCarList = mutableListOf<RevokeDepartureTrunkDepartureScanOperatingBean>()
    val mRemoveBillno = mutableListOf<String>()
    override fun getCarInfoS(list: List<RevokeDepartureTrunkDepartureScanOperatingBean>) {
        if (mCarList.isNotEmpty()) {
            mCarList.clear()
        }
        mCarList.addAll(list)
    }

    override fun getRecyclerViewId(): Int = R.id.departure_trunk_departure_scan_operating_recycler


    override fun setAdapter(): BaseRecyclerAdapter<RevokeDepartureTrunkDepartureScanOperatingBean> = RevokeDepartureTrunkDepartureScanOperatingAdapter(mContext)
}