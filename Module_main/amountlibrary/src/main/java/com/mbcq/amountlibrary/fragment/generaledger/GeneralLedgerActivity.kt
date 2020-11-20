package com.mbcq.amountlibrary.fragment.generaledger


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.amountlibrary.R
import com.mbcq.baselibrary.ui.BaseSmartMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import kotlinx.android.synthetic.main.activity_general_ledger.*

/**
 * @author: lzy
 * @time: 2020-11-20 17:31:12 货款总账
 */

@Route(path = ARouterConstants.GeneralLedgerActivity)
class GeneralLedgerActivity : BaseSmartMVPActivity<GeneralLedgerContract.View, GeneralLedgerPresenter, GeneralLedgerBean>(), GeneralLedgerContract.View {
    override fun getLayoutId(): Int = R.layout.activity_general_ledger
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        genera_ledger_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }


    override fun getSmartLayoutId(): Int = R.id.genera_ledger_smart

    override fun getSmartEmptyId(): Int = R.id.genera_ledger_smart_frame

    override fun getRecyclerViewId(): Int = R.id.genera_ledger_recycler
    override fun setAdapter(): BaseRecyclerAdapter<GeneralLedgerBean> = GeneralLedgerAdapter(mContext).also {
        it.appendData(mutableListOf(GeneralLedgerBean()))
    }
}