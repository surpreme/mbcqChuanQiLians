package com.mbcq.vehicleslibrary.activity.shorthousechecklist


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.activity_short_house_checklist.*

/**
 * @author: lzy
 * @time: 2020-12-09 13:16:43 短驳在库清单
 */

@Route(path = ARouterConstants.ShortHouseChecklistActivity)
class ShortHouseChecklistActivity : BaseListMVPActivity<ShortHouseChecklistContract.View, ShortHouseChecklistPresenter, ShortHouseChecklistBean>(), ShortHouseChecklistContract.View {
    override fun getLayoutId(): Int = R.layout.activity_short_house_checklist
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        initCheck()
    }

    private fun initCheck() {
        allType_check.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                urgentType_check.isChecked = false
                abnormalType_check.isChecked = false
                overnightType_check.isChecked = false
            }
        }
        urgentType_check.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                allType_check.isChecked = false

            }
        }
        abnormalType_check.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                allType_check.isChecked = false

            }
        }
        overnightType_check.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                allType_check.isChecked = false

            }
        }
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getInventory(1)
    }


    override fun onClick() {
        super.onClick()
        search_btn.apply {
            onSingleClicks {
                if (billno_ed.text.toString().isNotBlank()) {
                    val mXList = mutableListOf<ShortHouseChecklistBean>()
                    for (item in mShowList) {
                        if (item.billno == billno_ed.text.toString()) {
                            mXList.add(item)
                        }
                        if (mXList.isNotEmpty()) {
                            adapter.clearData()
                            adapter.appendData(mXList)
                        } else {
                            showToast("未查询到此运单")
                        }
                    }

                } else {
                    adapter.clearData()
                    adapter.appendData(mShowList)
                }
            }
        }
        bottom_back_btn.apply {
            onSingleClicks {
                onBackPressed()
            }
        }
        short_house_checklist_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun getRecyclerViewId(): Int = R.id.short_house_checklist_recycler
    override fun setAdapter(): BaseRecyclerAdapter<ShortHouseChecklistBean> = ShortHouseChecklistAdapter(mContext)
    val mShowList = mutableListOf<ShortHouseChecklistBean>()
    override fun getInventoryS(list: List<ShortHouseChecklistBean>) {
        mShowList.addAll(list)
        adapter.appendData(list)
//        if (getCurrentPage() == 1)
//            all_info_bottom_tv.text = "合计：${list.size}票，${toltalData.rowCou}件，${toltalData.weight}kg，${toltalData.volumn}m³，运费${toltalData.accSum}"

    }
}