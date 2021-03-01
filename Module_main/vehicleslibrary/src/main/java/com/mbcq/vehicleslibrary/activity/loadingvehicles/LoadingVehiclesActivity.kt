package com.mbcq.vehicleslibrary.activity.loadingvehicles


import android.Manifest
import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.os.CountDownTimer
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.FilterTimeUtils
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.commonlibrary.scan.pda.CommonScanPDAMVPSmartActivity
import com.mbcq.commonlibrary.scan.scanlogin.ScanDialogFragment
import com.mbcq.vehicleslibrary.R
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_loading_vehicles.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-04 09:49:32 装车
 */

@Route(path = ARouterConstants.LoadingVehiclesActivity)
class LoadingVehiclesActivity : CommonScanPDAMVPSmartActivity<LoadingVehiclesContract.View, LoadingVehiclesPresenter, LoadingVehiclesBean>(), LoadingVehiclesContract.View {
    lateinit var rxPermissions: RxPermissions
    var mStartDateTag = ""
    var mEndDateTag = ""
    var mShippingOutletsTag = ""//发货网点

    override fun getLayoutId(): Int = R.layout.activity_loading_vehicles


    override fun getIsOnCreateGetData(): Boolean = false

    @SuppressLint("SimpleDateFormat")
    override fun initExtra() {
        mIsCanCloseLoading = false
        super.initExtra()
        rxPermissions = RxPermissions(this)
        mStartDateTag = FilterTimeUtils.getStartTime(7)
        mEndDateTag = FilterTimeUtils.getEndTime()
        mShippingOutletsTag = UserInformationUtil.getWebIdCode(mContext)
    }

    override fun getEnableLoadMore(): Boolean = false

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
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
                    if (v.text.toString().isNotBlank()) {
                        mPresenter?.searchScanInfo(v.text.toString())
                    } else
                        initDatas()
                    return true
                }
                return false
            }

        })

        short_vehicles_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.AddScanShortFeederActivity).navigation()
            }

        })
        trunk_vehicles_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.AddDepartureTrunkActivity).navigation()
            }

        })
        loading_vehicles_toolbar.setRightButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {

                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        FilterWithTimeDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, "运单筛选", true, mClickInterface = object : OnClickInterface.OnClickInterface {
                            /**
                             * s1 网点
                             * s2  start@end
                             */
                            override fun onResult(s1: String, s2: String) {
                                mShippingOutletsTag = s1
                                val timeList = s2.split("@")
                                if (timeList.isNotEmpty() && timeList.size == 2) {
                                    mStartDateTag = timeList[0]
                                    mEndDateTag = timeList[1]
                                }
                                refresh()
                                /* adapter.clearData()
                                 initDatas()*/
                            }

                        }).show(supportFragmentManager, "WaybillRecordActivityFilterWithTimeDialog")
                    }

                })
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
                        ScanDialogFragment(getScreenWidth(), null, object : OnClickInterface.OnClickInterface {
                            override fun onResult(s1: String, s2: String) {
                                mPresenter?.searchScanInfo(if (checkStrIsNum(s1)) s1.substring(0, s1.length - 4) else s1)
                            }

                        }).show(supportFragmentManager, "ScanDialogFragment")
                    } else {
                        // Oups permission denied
                        TalkSureDialog(mContext, getScreenWidth(), "权限未赋予！照相机无法启动！请联系在线客服或手动进入系统设置授予摄像头权限！").show()

                    }
                }

    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getScanVehicleList(mStartDateTag, mEndDateTag)
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
                if (obj.optString("vehicleStateStr").contains("发货")) {
                    TalkSureDialog(mContext, getScreenWidth(), "已经发货，请到发车记录去查看").show()
                    return
                }
                /**
                 * @type 0短驳 1干线
                 * @isScan 1有计划 2无计划
                 */
                if (obj.optInt("type") == 1) {
                    if (obj.optInt("isScan") == 1)
                        ARouter.getInstance().build(ARouterConstants.DepartureTrunkDepartureScanOperatingActivity).withString("LoadingVehicles", mResult).navigation()
                    else if (obj.optInt("isScan") == 2)
                        ARouter.getInstance().build(ARouterConstants.DepartureTrunkDepartureUnPlanScanOperatingActivity).withString("DepartureLoadingVehicles", mResult).navigation()

                } else if (obj.optInt("type") == 0) {
                    if (obj.optInt("isScan") == 1)
                        ARouter.getInstance().build(ARouterConstants.ShortTrunkDepartureScanOperatingActivity).withString("ShortLoadingVehicles", mResult).navigation()
                    else if (obj.optInt("isScan") == 2)
                        ARouter.getInstance().build(ARouterConstants.ShortTrunkDepartureUnPlanScanOperatingActivity).withString("ShortLoadingVehicles", mResult).navigation()


                }
            }
        }
        /**
         * type @0短驳 @1 干线
         * mType  @1短驳 @2干线
         */
        it.mDeleteClickInterface = object : OnClickInterface.OnRecyclerDeleteClickInterface {
            override fun onDelete(v: View, position: Int, mResult: String) {
                val mObj = JSONObject(mResult)
                TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要删除发车批次为${mObj.optString("inoneVehicleFlag")}的装车记录吗？") {
                    mPresenter?.invalidOrder(mObj.optString("inoneVehicleFlag"), mObj.optInt("id"), if (mObj.optInt("type") == 0) 1 else 2, position)
                }.show()

            }

        }
        /**
         * type @0短驳 @1 干线
         * mType  @1短驳 @2干线
         */
        it.mDepartureClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mObj = JSONObject(mResult)
                TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要完成发车批次为${mObj.optString("inoneVehicleFlag")}的装车吗？") {
                    mPresenter?.saveScanPost(mObj.optInt("id"), mObj.optString("inoneVehicleFlag"), if (mObj.optInt("type") == 0) 1 else 2, position)
                }.show()
            }

        }
        /**
         * type @0短驳 @1 干线
         */
        it.mChangeClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mObj = JSONObject(mResult)
                if (mObj.optInt("type") == 0)
                    ARouter.getInstance().build(ARouterConstants.FixedScanShortFeederConfigurationActivity).withString("FixedScanShortFeederConfiguration", mObj.optString("inoneVehicleFlag")).navigation()
                else
                    ARouter.getInstance().build(ARouterConstants.FixedScanDepartureTrunkConfigurationActivity).withString("FixedScanDepartureTrunkConfiguration", mObj.optString("inoneVehicleFlag")).navigation()
            }

        }
    }


    override fun searchScanInfoS(list: List<LoadingVehiclesBean>) {
        adapter.clearData()
        appendDatas(list)
    }

    override fun invalidOrderS(position: Int) {
        adapter.removeItem(position)

    }

    override fun saveScanPostS(position: Int) {
        showToast("完成本车成功！")

    }

    override fun getScanVehicleListS(list: List<LoadingVehiclesBean>) {
        adapter.clearData()
        object : CountDownTimer(500, 500) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                if (!isDestroyed)
                    appendDatas(list)
            }

        }.start()
    }

    override fun onPDAScanResult(result: String) {
        mPresenter?.searchScanInfo(if (checkStrIsNum(result)) result.substring(0, result.length - 4) else result)
    }

    override fun getSmartLayoutId(): Int = R.id.loading_vehicles_smart

    override fun getSmartEmptyId(): Int = R.id.loading_vehicles_smart_frame
}