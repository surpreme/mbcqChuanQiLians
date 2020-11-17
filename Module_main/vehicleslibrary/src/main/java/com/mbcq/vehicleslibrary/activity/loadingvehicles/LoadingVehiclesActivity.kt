package com.mbcq.vehicleslibrary.activity.loadingvehicles


import android.Manifest
import android.graphics.Rect
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.scan.scanlogin.ScanDialogFragment
import com.mbcq.vehicleslibrary.R
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_loading_vehicles.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-04 09:49:32 装车
 */

@Route(path = ARouterConstants.LoadingVehiclesActivity)
class LoadingVehiclesActivity : BaseListMVPActivity<LoadingVehiclesContract.View, LoadingVehiclesPresenter, LoadingVehiclesBean>(), LoadingVehiclesContract.View {
    lateinit var rxPermissions: RxPermissions

    override fun getLayoutId(): Int = R.layout.activity_loading_vehicles

    override fun initExtra() {
        super.initExtra()
        rxPermissions = RxPermissions(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        loading_vehicles_smart.setEnableLoadMore(false)
        loading_vehicles_smart.setRefreshHeader(ClassicsHeader(mContext))
        loading_vehicles_smart.setOnRefreshListener {
            adapter.clearData()
            initDatas()
            it.finishRefresh()
        }
    }

    override fun onClick() {
        super.onClick()
        scan_vehicles_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getCameraPermission()
            }

        })
        search_inventory_ed.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (v.text.toString().isNotBlank())
                        mPresenter?.searchShortFeeder(v.text.toString())
                    else
                        initDatas()
                    return true
                }
                return false
            }

        })
        loading_vehicles_toolbar.setRightButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ScanDialogFragment(getScreenWidth(), null, object : OnClickInterface.OnClickInterface {
                    override fun onResult(s1: String, s2: String) {
                        mPresenter?.searchShortFeeder(s1)
                    }

                }).show(supportFragmentManager, "ScanDialogFragment")
            }

        })
        short_vehicles_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.AddScanShortFeederActivity).navigation()
            }

        })

        loading_vehicles_toolbar.setBackButtonOnClickListener(object : SingleClick() {
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
                        ScanDialogFragment(getScreenWidth()).show(supportFragmentManager, "ScanDialogFragment")
                    } else {
                        // Oups permission denied
                        ScanDialogFragment(getScreenWidth(), null, object : OnClickInterface.OnClickInterface {
                            override fun onResult(s1: String, s2: String) {
                                mPresenter?.searchShortFeeder(s1)
                            }

                        }).show(supportFragmentManager, "ScanDialogFragment")
                    }
                }

    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getShortFeeder()
    }

    override fun addItemDecoration(): RecyclerView.ItemDecoration = object : BaseItemDecoration(mContext) {
        override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
            rect.top = ScreenSizeUtils.dp2px(mContext, 10f)
        }

        override fun doRule(position: Int, rect: Rect) {
            rect.bottom = rect.top
        }
    }

    override fun getRecyclerViewId(): Int = R.id.loading_vehicles_recycler

    override fun setAdapter(): BaseRecyclerAdapter<LoadingVehiclesBean> = LoadingVehiclesAdapter(mContext).also {
        it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val obj = JSONObject(mResult)
                /**
                 * @type 0短驳 1干线
                 * @isScan 1有计划 2无计划
                 */
                if (obj.optInt("type") == 1)
                    ARouter.getInstance().build(ARouterConstants.DepartureTrunkDepartureScanOperatingActivity).withString("LoadingVehicles", mResult).navigation()
                else if (obj.optInt("type") == 0) {
                    if (obj.optInt("isScan") == 1)
                        ARouter.getInstance().build(ARouterConstants.ShortTrunkDepartureScanOperatingActivity).withString("ShortLoadingVehicles", mResult).navigation()
                    else if (obj.optInt("isScan") == 2)
                        ARouter.getInstance().build(ARouterConstants.RevokeShortTrunkDepartureUnPlanScanOperatingActivity).withString("ShortLoadingVehicles", mResult).navigation()


                }
            }

        }
    }

    override fun getShortFeederS(list: List<LoadingVehiclesBean>, isScan: Boolean) {
        adapter.appendData(list)
        if (!isScan)
            mPresenter?.getTrunkDeparture()
    }

    override fun getTrunkDepartureS(list: List<LoadingVehiclesBean>, isScan: Boolean) {
        adapter.appendData(list)
    }
}