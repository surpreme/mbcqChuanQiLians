package com.mbcq.vehicleslibrary.fragment.shortfeederhouse.loadinglist


import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.ui.BaseListFragment
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.activity.alldeparturerecord.departurerecord.ShortFeederHouseEvent
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean
import com.mbcq.vehicleslibrary.fragment.shortfeederhouse.event.ShortFeederHouseInventoryListEvent
import kotlinx.android.synthetic.main.fragment_short_feeder_house_loading_list.*


/**
 * @author: lzy
 * @time: 2020-09-15 11:01:40
 * 配载清单 弃用 已完成
 */

class ShortFeederHouseLoadingListFragment : BaseListFragment<StockWaybillListBean>() {
    override fun getRecyclerViewId(): Int = R.id.inventory_list_recycler

    override fun setAdapter(): BaseRecyclerAdapter<StockWaybillListBean> = ShortFeederHouseLoadingListAdapter(mContext).also {
        all_selected_checked.setOnCheckedChangeListener { _, isChecked ->
            it.checkedAll(isChecked)
        }
    }

    override fun onClick() {
        super.onClick()
        complete_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (adapter.getAllData().isEmpty()) {
                    showToast("请至少选中需要一个发车的库存")
                    return
                }
                RxBus.build().post(ShortFeederHouseEvent(adapter.getAllData()))
            }

        })
        operating_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                val list = mutableListOf<StockWaybillListBean>()
                val unCheckList = mutableListOf<StockWaybillListBean>()
                for ((index, item) in (adapter.getAllData()).withIndex()) {
                    if (item.isChecked) {
                        item.isChecked = false
                        list.add(item)
                    } else {
                        unCheckList.add(item)
                    }
                }
                if (list.isEmpty()) {
                    showToast("请至少选择一个库存进行操作")
                    unCheckList.clear()
                    return
                }
                RxBus.build().postSticky(ShortFeederHouseInventoryListEvent(0, list))
                adapter.clearData()
                adapter.appendData(unCheckList)
                all_selected_checked.isChecked = false
            }

        })
    }

    @SuppressLint("CheckResult")
    override fun initDatas() {
        super.initDatas()
        RxBus.build().toObservableSticky(this, ShortFeederHouseInventoryListEvent::class.java).subscribe { msg ->
            if (msg.type == 1) {
                adapter.appendData(msg.list)
            }
        }
    }

    override fun addItemDecoration(): RecyclerView.ItemDecoration = object : BaseItemDecoration(mContext) {
        override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
            rect.top = ScreenSizeUtils.dp2px(mContext, 10f)
        }

        override fun doRule(position: Int, rect: Rect) {
            rect.bottom = rect.top
        }
    }

    override fun getLayoutResId(): Int = R.layout.fragment_short_feeder_house_loading_list

    override fun onDetach() {
        super.onDetach()
        RxBus.build().removeStickyEvent(ShortFeederHouseInventoryListEvent::class.java)

    }

}