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
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.util.system.PhoneDeviceMsgUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.scan.scanlogin.ScanDialogFragment
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating.revoke.RevokeShortTrunkDepartureScanOperatingBean
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_revoke_departure_trunk_departure_scan_operating.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-19 08:31:06 撤销干线扫描
 */

@Route(path = ARouterConstants.RevokeDepartureTrunkDepartureScanOperatingActivity)
class RevokeDepartureTrunkDepartureScanOperatingActivity : BaseListMVPActivity<RevokeDepartureTrunkDepartureScanOperatingContract.View, RevokeDepartureTrunkDepartureScanOperatingPresenter, RevokeDepartureTrunkDepartureScanOperatingBean>(), RevokeDepartureTrunkDepartureScanOperatingContract.View {
    @Autowired(name = "RevokeDepartureLoadingVehicles")
    @JvmField
    var mLastData: String = ""

    lateinit var rxPermissions: RxPermissions
    var mSoundPool: SoundPool? = null
    private var soundPoolMap: HashMap<Int, Int>? = null
    val SCAN_SOUND_ERROR_TAG = 1

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
    }
    override fun initDatas() {
        super.initDatas()
        mPresenter?.getCarInfo(JSONObject(mLastData).optString("inoneVehicleFlag"))
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
                                    val obj = JSONObject(mLastData)
                                    mPresenter?.revokeOrder(s1.substring(0, s1.length - 4), s1, PhoneDeviceMsgUtils.getDeviceOnlyTag(mContext), obj.optString("inoneVehicleFlag"), "")

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
            soundPoolMap?.put(SCAN_SOUND_ERROR_TAG, it.load(this, com.mbcq.commonlibrary.R.raw.scan_success, 1))

        }
    }

    override fun revokeOrderS(result: String) {
        soundPoolMap?.get(SCAN_SOUND_ERROR_TAG)?.let { mSoundPool?.play(it, 1f, 1f, 0, 0, 1f) }
        mRemoveBillno.add(result)
        var isHas = false
        if (adapter.getAllData().isNotEmpty()) {
            for ((index, item) in adapter.getAllData().withIndex()) {
                if (item.billno == result) {
                    isHas = true
                    item.unLoadQty -= 1
                    adapter.notifyItemChangeds(index, item)
                }
            }
        }
        if (!isHas) {
            for (mItem in mCarList) {
                if (mItem.billno == result) {
                    mItem.unLoadQty -= 1
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