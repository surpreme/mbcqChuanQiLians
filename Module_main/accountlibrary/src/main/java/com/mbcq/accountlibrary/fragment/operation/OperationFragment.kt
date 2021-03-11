package com.mbcq.accountlibrary.fragment.operation

import android.view.View
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.accountlibrary.R
import com.mbcq.accountlibrary.fragment.iconadapter.IconViewBean
import com.mbcq.accountlibrary.fragment.iconadapter.IconViewRecyclerAdapter
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseListMVPFragment
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import kotlinx.android.synthetic.main.fragment_operation.*

/**
 * 运营
 */
class OperationFragment : BaseListMVPFragment<OperationContract.View, OperationPresenter, IconViewBean>(), OperationContract.View {
    override fun getLayoutResId(): Int = R.layout.fragment_operation

    override fun initViews(view: View) {
        super.initViews(view)
        operation_toolbar.setPadding(0, getStatusBarHeight(), 0, 0)
        operation_toolbar.setCenterTitleText(UserInformationUtil.getWebIdCodeStr(mContext))

    }

    override fun initDatas() {
        super.initDatas()
        mPresenter?.getMenuAuthority()

    }

    override fun getRecyclerViewId(): Int = R.id.operation_recycler_view

    override fun setAdapter(): BaseRecyclerAdapter<IconViewBean> {
        return IconViewRecyclerAdapter(mContext).also {
            it.mIconClickInterface = object : OnClickInterface.OnClickInterface {
                override fun onResult(s1: String, s2: String) {
                    LogUtils.d("s1" + s1 + "s2" + s2)
                    try {
                        ARouter.getInstance().build(s1).navigation()
                    } catch (e: Exception) {
                        e.printStackTrace()
                        LogUtils.e("cause by：mysql未配置arouter路径 或者路径配置名字不匹配 class:OperationFragment" + e.printStackTrace())
                    }
                }
            }

        }
    }

    override fun getMenuAuthorityS(list: List<IconViewBean>) {
        adapter.replaceData(list)
    }
}