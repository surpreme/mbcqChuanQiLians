package com.mbcq.vehicleslibrary.fragment.shortfeederhouse.inventorylist


import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.interfaces.RxBus
import com.mbcq.baselibrary.ui.BaseListMVPFragment
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.fragment.shortfeederhouse.bean.ShortFeederHouseListBean
import com.mbcq.vehicleslibrary.fragment.shortfeederhouse.event.ShortFeederHouseInventoryListEvent
import kotlinx.android.synthetic.main.fragment_short_feeder_house_inventory_list.*

/**
 * @author: lzy
 * @time: 2020-09-15 11:01:40
 * 库存清单
 */

class ShortFeederHouseInventoryListFragment : BaseListMVPFragment<ShortFeederHouseInventoryListFragmentContract.View, ShortFeederHouseInventoryListFragmentPresenter, ShortFeederHouseListBean>(), ShortFeederHouseInventoryListFragmentContract.View {
    override fun getRecyclerViewId(): Int = R.id.inventory_list_recycler

    override fun setAdapter(): BaseRecyclerAdapter<ShortFeederHouseListBean> = ShortFeederHouseInventoryListAdapter(mContext).also {
        all_selected_checked.setOnCheckedChangeListener { _, isChecked ->
            it.checkedAll(isChecked)
        }

    }

    @SuppressLint("CheckResult")
    override fun initDatas() {
        super.initDatas()
        mPresenter?.getPage(1)
        RxBus.build().toObservableSticky(this, ShortFeederHouseInventoryListEvent::class.java).subscribe { msg ->
            if (msg.type == 0) {
                adapter.appendData(msg.list)
            }
        }
    }

    override fun onClick() {
        super.onClick()
        operating_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                val list = mutableListOf<ShortFeederHouseListBean>()
                val unCheckList = mutableListOf<ShortFeederHouseListBean>()
                for ((index, item) in (adapter.getAllData()).withIndex()) {
                    if (item.isChecked) {
                        item.isChecked=false
                        list.add(item)
                    } else {
                        unCheckList.add(item)
                    }
                }
                RxBus.build().postSticky(ShortFeederHouseInventoryListEvent(1, list))
                adapter.clearData()
                adapter.appendData(unCheckList)
                all_selected_checked.isChecked=false
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

    override fun getLayoutResId(): Int = R.layout.fragment_short_feeder_house_inventory_list


    override fun getPageS(list: List<ShortFeederHouseListBean>) {
        adapter.appendData(list)
    }
    override fun onDetach() {
        super.onDetach()
        RxBus.build().removeStickyEvent( ShortFeederHouseInventoryListEvent::class.java)

    }
}