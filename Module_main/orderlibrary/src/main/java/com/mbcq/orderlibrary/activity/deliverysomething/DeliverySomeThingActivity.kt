package com.mbcq.orderlibrary.activity.deliverysomething


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.baselibrary.ui.BaseSmartMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_delivery_some_thing.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-29 08:35:06  送货
 */
@Route(path = ARouterConstants.DeliverySomeThingActivity)
class DeliverySomeThingActivity : BaseSmartMVPActivity<DeliverySomeThingContract.View, DeliverySomeThingPresenter, DeliverySomeThingBean>(), DeliverySomeThingContract.View {


    override fun getLayoutId(): Int = R.layout.activity_delivery_some_thing
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)

    }


    override fun getPageDatas(mCurrentPage: Int) {
        super.getPageDatas(mCurrentPage)
        mPresenter?.getDeliveryS(mCurrentPage)

    }

    override fun onClick() {
        super.onClick()
        plan_delivery_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.AddDeliverySomeThingActivity).navigation()
            }

        })
        fixed_delivery_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                for (item in adapter.getAllData()) {
                    if (item.isChecked) {
                        ARouter.getInstance().build(ARouterConstants.FixedDeliverySomethingHouseActivity).withString("FixedDeliverySomeThing", Gson().toJson(item)).navigation()
                        break

                    }
                }
            }

        })

        delivery_something_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }
        })
    }

    override fun getSmartLayoutId(): Int = R.id.delivery_something_smart

    override fun getSmartEmptyId(): Int = R.id.delivery_something_smart_frame

    override fun getRecyclerViewId(): Int = R.id.delivery_something_recycler

    override fun setAdapter(): BaseRecyclerAdapter<DeliverySomeThingBean> = DeliverySomeThingAdapter(mContext)
    override fun getDeliverySS(list: List<DeliverySomeThingBean>) {
        appendDatas(list)
    }
}