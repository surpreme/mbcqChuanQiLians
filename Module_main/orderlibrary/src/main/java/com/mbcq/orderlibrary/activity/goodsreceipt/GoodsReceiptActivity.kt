package com.mbcq.orderlibrary.activity.goodsreceipt


import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_goods_receipt.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author: lzy
 * @time: 2020-09-27 11:18:00
 *货物签收
 */
@Route(path = ARouterConstants.GoodsReceiptActivity)
class GoodsReceiptActivity : BaseListMVPActivity<GoodsReceiptContract.View, GoodsReceiptPresenter, GoodsReceiptBean>(), GoodsReceiptContract.View {

    override fun getLayoutId(): Int = R.layout.activity_goods_receipt
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
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

    override fun initDatas() {
        super.initDatas()
        if (adapter.getAllData().isNotEmpty()) {
            adapter.clearData()
        }
        mPresenter?.getPage(1)
    }

    override fun onRestart() {
        super.onRestart()
        initDatas()
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
        goods_receipt_toolbar.setBackButtonOnClickListener(object : SingleClick() {
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

    override fun getRecyclerViewId(): Int = R.id.goods_receipt_recycler

    override fun setAdapter(): BaseRecyclerAdapter<GoodsReceiptBean> = GoodsReceiptAdapter(mContext)
    override fun getPageS(list: List<GoodsReceiptBean>) {
        adapter.appendData(list)
    }

}