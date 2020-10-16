package com.mbcq.vehicleslibrary.activity.vehiclesarchives


import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseSmartMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterMoreWebDialog
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_vehicle_archives.*

/**
 * @author: lzy
 * @time: 2020-10-14 14:43:16 车辆档案
 */
@Route(path = ARouterConstants.VehicleArchivesActivity)
class VehicleArchivesActivity : BaseSmartMVPActivity<VehicleArchivesContract.View, VehicleArchivesPresenter, VehicleArchivesBean>(), VehicleArchivesContract.View {
    var mShippingOutletsTag=""
    override fun getLayoutId(): Int = R.layout.activity_vehicle_archives
    override fun initExtra() {
        super.initExtra()
        mShippingOutletsTag = UserInformationUtil.getWebIdCode(mContext)

    }
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getPage(mCurrentPage,mShippingOutletsTag)

    }

    override fun onClick() {
        super.onClick()
        search_btn.setOnClickListener(object:SingleClick(2000){
            override fun onSingleClick(v: View?) {
                mPresenter?.searchItem(essential_ed.text.toString(),essential_ed.text.toString())
            }

        })
        delete_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                for ((index,item) in adapter.getAllData().withIndex()) {
                    if (item.isChecked) {
                        TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要删除车牌号为 ${item.vehicleno} 的车辆档案信息吗？删除后不可恢复！") {
                            mPresenter?.deleteItem(Gson().toJson(item), index)

                        }.show()
                        break
                    }

                }

            }

        })
        more_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.AddVehicleArchivesActivity).navigation()
            }


        })
        update_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                for (item in adapter.getAllData()) {
                    if (item.isChecked) {
                        ARouter.getInstance().build(ARouterConstants.FixedVehicleArchivesActivity).withString("FixedVehicleArchives", Gson().toJson(item)).navigation()
                        break
                    }
                }
            }


        })
        vehicle_archives_toolbar.setRightButtonOnClickListener(object:SingleClick(){
            override fun onSingleClick(v: View?) {
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {

                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        FilterMoreWebDialog(getScreenWidth(), Gson().toJson(list), "webid", "webidCode", true, isShowTime = false, isOnlyLast = true, tips = "筛选车辆档案网点", isGridLayoutManager = true, mClickInterface = object : FilterMoreWebDialog.OnClickInterface {
                            override fun onResult(s1: String, s2: String, s3: String) {
                                mShippingOutletsTag = s1
                                refresh()
                            }

                        }).show(supportFragmentManager, "VehicleArchivesActivityFilterWithTimeDialog")
                    }

                })
            }

        })
        vehicle_archives_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun addItemDecoration(): RecyclerView.ItemDecoration = object : BaseItemDecoration(mContext) {
        override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
            rect.top = ScreenSizeUtils.dp2px(mContext, 10f)
        }

        override fun doRule(position: Int, rect: Rect) {
            rect.bottom = rect.top
        }
    }

    override fun getSmartLayoutId() = R.id.vehicle_archives_smart
    override fun getSmartEmptyId() = R.id.vehicle_archives_smart_frame
    override fun getRecyclerViewId(): Int = R.id.vehicle_archives_recycler

    override fun setAdapter(): BaseRecyclerAdapter<VehicleArchivesBean> = VehicleArchivesRecyclerAdapter(mContext)
    override fun getPageS(list: List<VehicleArchivesBean>) {
        appendDatas(list)
    }

    override fun deleteItemS(position: Int) {
        adapter.removeItem(position)
    }
}