package com.mbcq.orderlibrary.fragment.waybillscan

import android.view.View
import com.mbcq.baselibrary.ui.BaseFragment
import com.mbcq.baselibrary.ui.BaseListMVPFragment
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.orderlibrary.R
import org.json.JSONObject

/**
 * @information 扫描轨迹
 * @author lzy
 */
class WaybillScanFragment : BaseListMVPFragment<WaybillScanFragmentContract.View, WaybillScanFragmentPresenter, WaybillScanFragmentListBean>(), WaybillScanFragmentContract.View {
    override fun getLayoutResId(): Int = R.layout.fragment_waybill_scan
    override fun getRecyclerViewId(): Int = R.id.fragment_waybill_scan_recycler

    override fun setAdapter(): BaseRecyclerAdapter<WaybillScanFragmentListBean> = WaybillScanFragmentAdapter(mContext)
    override fun initDatas() {
        super.initDatas()
        arguments?.let {
            val mArgData = it.getString("WaybillDetails", "{}")
            mPresenter?.getWaybillScan(JSONObject(mArgData).optString("billno"))

        }
    }

    override fun getWaybillScanS(list: List<WaybillScanFragmentListBean>) {
        if (adapter.getAllData().isNotEmpty())
            adapter.clearData()
        adapter.appendData(list)
    }


}