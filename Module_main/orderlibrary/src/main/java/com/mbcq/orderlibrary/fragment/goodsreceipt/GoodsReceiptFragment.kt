package com.mbcq.orderlibrary.fragment.goodsreceipt


import android.graphics.Rect
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseListMVPFragment
import com.mbcq.baselibrary.ui.BaseSmartMVPFragment
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.frgment_goods_receipt.*

/**
 * @author: lzy
 * @time: 2020-09-27 11:18:00
 *货物未签收记录
 */
class GoodsReceiptFragment : BaseSmartMVPFragment<GoodsReceiptContract.View, GoodsReceiptPresenter, GoodsReceiptBean>(), GoodsReceiptContract.View {

    override fun getLayoutResId(): Int = R.layout.frgment_goods_receipt
    override fun getSmartLayoutId(): Int = R.id.goods_receipt_smart
    override fun getSmartEmptyId(): Int = R.id.goods_receipt_smart_frame
    override fun getRecyclerViewId(): Int = R.id.goods_receipt_recycler
    override fun getEnableLoadMore(): Boolean = false

    override fun initViews(view: View) {
        super.initViews(view)
        billno_ed.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.toString().isBlank())
                    initDatas()
            }

        })
    }

    fun getBillNoData() {
        if (adapter.getAllData().isNotEmpty()) {
            adapter.clearData()
        }
        mPresenter?.getBillNoData(billno_ed.text.toString())
    }

    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        if (adapter.getAllData().isNotEmpty()) {
            adapter.clearData()
        }
        mPresenter?.getPage(1)
    }


    override fun onClick() {
        super.onClick()
        search_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (billno_ed.text.toString().isNotBlank()) {
                    getBillNoData()
                }

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


    override fun setAdapter(): BaseRecyclerAdapter<GoodsReceiptBean> = GoodsReceiptAdapter(mContext).also {
        it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                ARouter.getInstance().build(ARouterConstants.GoodsReceiptInfoActivity).withString("GoodsReceiptBean", mResult).navigation()

            }

        }
    }

    override fun getPageS(list: List<GoodsReceiptBean>) {
        appendDatas(list)
    }

}