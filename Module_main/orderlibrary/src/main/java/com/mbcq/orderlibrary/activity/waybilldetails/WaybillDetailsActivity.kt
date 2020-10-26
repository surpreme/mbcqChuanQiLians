package com.mbcq.orderlibrary.activity.waybilldetails


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.android.material.tabs.TabLayout
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.FragmentViewPagerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.baselibrary.view.TabBuilder
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.fragment.fixedwaybill.FixedWaybillFragment
import com.mbcq.orderlibrary.fragment.waybillinformation.WaybillInformationFragment
import com.mbcq.orderlibrary.fragment.waybillpictures.WaybillPictureFragment
import com.mbcq.orderlibrary.fragment.waybillroad.WaybillRoadBottomFragment
import com.mbcq.orderlibrary.fragment.waybillscan.WaybillScanFragment
import kotlinx.android.synthetic.main.activity_waybill_details.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-20 10:13:00 运单详情
 */

@Route(path = ARouterConstants.WaybillDetailsActivity)
class WaybillDetailsActivity : BaseMVPActivity<WaybillDetailsContract.View, WaybillDetailsPresenter>(), WaybillDetailsContract.View {
    @Autowired(name = "WaybillDetails")
    @JvmField
    var mLastDataNo: String = ""

    override fun getLayoutId(): Int = R.layout.activity_waybill_details
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getWaybillDetail(mLastDataNo)
    }

    override fun onClick() {
        super.onClick()
        waybill_details_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    private fun initOptionsIndex(billnoInfoJson: String) {
        options_index_tablayout.addTab(options_index_tablayout.newTab().setText("运单信息"))
        options_index_tablayout.addTab(options_index_tablayout.newTab().setText("运单轨迹"))
        options_index_tablayout.addTab(options_index_tablayout.newTab().setText("扫描轨迹"))
        options_index_tablayout.addTab(options_index_tablayout.newTab().setText("改单记录(x)"))
        options_index_tablayout.addTab(options_index_tablayout.newTab().setText("本单图片"))
        val fragments = ArrayList<Fragment>()
        fragments.add(WaybillInformationFragment())
        fragments.add(WaybillRoadBottomFragment())
        fragments.add(WaybillScanFragment())
        fragments.add(FixedWaybillFragment())
        fragments.add(WaybillPictureFragment())
        val mBundle = Bundle()
        mBundle.putString("WaybillDetails", billnoInfoJson)
        fragments[0].arguments = mBundle
        fragments[1].arguments = mBundle
        fragments[2].arguments = mBundle
        fragments[4].arguments = mBundle
        val mFragmentViewPagerAdapter = FragmentViewPagerAdapter(supportFragmentManager, fragments)
        waybill_details_viewpager.adapter = mFragmentViewPagerAdapter
        waybill_details_viewpager.offscreenPageLimit = options_index_tablayout.tabCount
        //滑动绑定
        waybill_details_viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(options_index_tablayout))
        options_index_tablayout.addOnTabSelectedListener(object : TabBuilder() {
            override fun onSelected(tab: TabLayout.Tab) {
                waybill_details_viewpager.currentItem = tab.position
            }
        })
    }

    override fun getWaybillDetailS(data: JSONObject) {
        initOptionsIndex(GsonUtils.toPrettyFormat(data))

    }

    override fun showError(msg: String) {
      TalkSureDialog(mContext,getScreenWidth(),"系统异常！请联系管理员或稍后再试"){
          onBackPressed()
      }.show()
    }
    override fun getWaybillDetailNull() {

    }

}