package com.mbcq.orderlibrary.activity.acceptbillingoperationreview


import android.annotation.SuppressLint
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.BaseItemDecoration
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.fragment.waybillinformation.WaybillInformationTableAdapter
import com.mbcq.orderlibrary.fragment.waybillinformation.WaybillInformationTableBean
import kotlinx.android.synthetic.main.activity_accept_billing_fixed_review.*
import org.json.JSONObject


/**
 * @author: lzy
 * @time: 2020-12-28 10:25:17 改单申请财务和运营审核和详情
 * 这里共用不接受反驳 没几个功能 而且是新开的页面 也没放到改单的页面
 */

@Route(path = ARouterConstants.AcceptBillingFixedReviewActivity)
class AcceptBillingFixedReviewActivity : BaseMVPActivity<AcceptBillingFixedReviewContract.View, AcceptBillingFixedReviewPresenter>(), AcceptBillingFixedReviewContract.View {
    @Autowired(name = "AcceptBillingFixedReview")
    @JvmField
    var mAcceptBillingFixedReview: String = ""
    var mWaybillInformationTableAdapter: WaybillInformationTableAdapter? = null

    override fun getLayoutId(): Int = R.layout.activity_accept_billing_fixed_review
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        val mToolbarShowType = JSONObject(mAcceptBillingFixedReview).optInt("fixType", 0)
        accept_billing_operation_review_toolbar.setCenterTitleText(if (mToolbarShowType == 1) "运营审核" else if (mToolbarShowType == 2) "财务审核" else "改单申请详情")
        if (mToolbarShowType == 3) {
            review_btn.visibility = View.GONE
        }
        waybill_information_table_recycler.layoutManager = LinearLayoutManager(mContext)
        mWaybillInformationTableAdapter = WaybillInformationTableAdapter(mContext).also {
            waybill_information_table_recycler.adapter = it
        }
        waybill_information_table_recycler.addItemDecoration(object : BaseItemDecoration(mContext) {
            override fun configExtraSpace(position: Int, count: Int, rect: Rect) {
                rect.top = ScreenSizeUtils.dp2px(mContext, 1f)
            }

            override fun doRule(position: Int, rect: Rect) {
                rect.bottom = rect.top
            }

        })
    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getReviewData(JSONObject(mAcceptBillingFixedReview).optString("billno"))
        mPresenter?.getOrderData(JSONObject(mAcceptBillingFixedReview).optString("billno"))
    }

    override fun onClick() {
        super.onClick()
        review_btn.apply {
            onSingleClicks {
                val mToolbarShowType = JSONObject(mAcceptBillingFixedReview).optInt("fixType", 0)
                TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要${if (mToolbarShowType == 1) "运营审核" else if (mToolbarShowType == 2) "财务审核" else ""}${JSONObject(mAcceptBillingFixedReview).optString("billno")}吗？") {
                    mPresenter?.postReviewData(JSONObject(mAcceptBillingFixedReview).optString("billno"), JSONObject(mAcceptBillingFixedReview).optString("id"), mToolbarShowType)
                }.show()
            }
        }
        accept_billing_operation_review_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }


    @SuppressLint("SetTextI18n")
    override fun getReviewDataS(modifyContent: String, modifyReason: String) {
        fixed_order_info_tv.text = "修改内容：${modifyContent}"
        fixed_reason_order_info_tv.text = "修改原因：${modifyReason}"

    }

    override fun getOrderDataNull(billno: String) {

    }

    @SuppressLint("SetTextI18n")
    override fun getOrderDataS(data: JSONObject) {

        basic_info_tv.text = "运  单 号：${data.optString("billno")}\n开单时间：${data.optString("billDate")}\n货      号：${data.optString("goodsNum")}"
        //发货网点：汕头\n目  的 地：义乌市\n付货方式：送货
        webinfo_left_tv.text = "发货网点：${data.optString("webidCodeStr")}\n目  的 地：${data.optString("destination")}\n付货方式：${data.optString("okProcessStr")}"
        //到货网点：汕头\n运输方式：义乌市
        webinfo_right_tv.text = "到货网点：${data.optString("ewebidCodeStr")}\n运输方式：${data.optString("transneedStr")}"
        receiver_info_tv.text = "客户编号：${data.optString("consigneeId")}\n收  货 人：${data.optString("consignee")}\n手  机 号：${data.optString("consigneeMb")}\n固定电话：${data.optString("consigneeTel")}\n地      址：${data.optString("consigneeAddr")}"
        shipper_info_tv.text = "客户编号：${data.optString("shipperId")}\n发  货 人：${data.optString("shipper")}\n手  机 号：${data.optString("shipperMb")}\n固定电话：${data.optString("shipperTel")}\n地      址：${data.optString("shipperAddr")}\n公      司：xxxx\n发货人证件：${data.optString("shipperCid")}"

        goods_info_tv.text = "货物名称：${data.optString("product")}\n包装方式：${data.optString("packages")}\n体      积：${data.optString("volumn")}m³\n保  价 费：${data.optString("accSafe")}\n送  货 费：${data.optString("accSend")}\n返      款：${data.optString("accHuiKou")}\n付款方式：${data.optString("accTypeStr")}\n实发货款：您看不到我\n备      注：${data.optString("remark")}"
        goods_info_second_tv.text = "件      数：${data.optString("qty")}\n重      量：${data.optString("weight")}kg\n保价金额：${data.optString("safeMoney")}\n提  货 费：${data.optString("accFetch")}\n中  转 费：${data.optString("outacc")}\n合计运费：${data.optString("accSum")}\n代收货款：${data.optString("accDaiShou")}"
        data.optJSONArray("WayGoosLst")?.let {
            val mAdapterList = mutableListOf<WaybillInformationTableBean>()
            val mTopTitleBean = WaybillInformationTableBean()
            mTopTitleBean.product = "货物名称"
            mTopTitleBean.qty = "件数"
            mTopTitleBean.packages = "包装方式"
            mTopTitleBean.weight = "重量"
            mTopTitleBean.volumn = "体积"
            mTopTitleBean.isTitles = true
            mAdapterList.add(mTopTitleBean)
            for (index in 0 until it.length()) {
                val mWaybillInformationTableBean = WaybillInformationTableBean()
                mWaybillInformationTableBean.product = it.getJSONObject(index).optString("product")
                mWaybillInformationTableBean.qty = it.getJSONObject(index).optString("qty")
                mWaybillInformationTableBean.packages = it.getJSONObject(index).optString("packages")
                mWaybillInformationTableBean.weight = it.getJSONObject(index).optString("weight")
                mWaybillInformationTableBean.volumn = it.getJSONObject(index).optString("volumn")
                mAdapterList.add(mWaybillInformationTableBean)
            }
            if (mAdapterList.isNotEmpty())
                mWaybillInformationTableAdapter?.appendData(mAdapterList)

        }
    }

    override fun postReviewDataS(result: String) {
        TalkSureDialog(mContext, getScreenWidth(), "审核完成，点击返回！") {
            onBackPressed()
        }.show()

    }
}