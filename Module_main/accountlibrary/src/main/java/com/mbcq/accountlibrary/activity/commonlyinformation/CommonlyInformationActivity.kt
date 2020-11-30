package com.mbcq.accountlibrary.activity.commonlyinformation


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import kotlinx.android.synthetic.main.activity_commonly_information.*

/**
 * @author: lzy
 * @time: 2020-11-30 08:35:12 常用开单信息配置 Configuration
 */

@Route(path = ARouterConstants.CommonlyInformationActivity)
class CommonlyInformationActivity : BaseListMVPActivity<CommonlyInformationContract.View, CommonlyInformationPresenter, CommonlyInformationBean>(), CommonlyInformationContract.View {
    override fun getLayoutId(): Int = R.layout.activity_commonly_information
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.white)
    }

    override fun onClick() {
        super.onClick()
        commonly_information_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun getRecyclerViewId(): Int = R.id.commonly_information_recycler
    override fun setAdapter(): BaseRecyclerAdapter<CommonlyInformationBean> = CommonlyInformationAdapter(mContext).also {
        it.appendData(mutableListOf(
                CommonlyInformationBean("常用收货网点","设置经常使用的收货地址"),
                CommonlyInformationBean("常用目的地","设置经常使用的目的地"),
                CommonlyInformationBean("常用收货方式","设置经常使用的收货方式"),
                CommonlyInformationBean("常用付货方式","设置经常使用的常用付货方式"),
                CommonlyInformationBean("常用货物名称","设置经常使用的货物名称"),
                CommonlyInformationBean("常用包装方式","设置经常使用的包装方式"),
                CommonlyInformationBean("常用开单备注","设置经常使用的备注")
        ))
        it.mClickInterface=object : OnClickInterface.OnRecyclerClickInterface{
            override fun onItemClick(v: View, position: Int, mResult: String) {
                ARouter.getInstance().build(ARouterConstants.CommonlyInformationConfigurationActivity).withString("CommonlyInformationConfiguration",mResult).navigation()
            }

        }
    }
}