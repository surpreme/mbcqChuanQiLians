package com.mbcq.accountlibrary.activity.commonlyinformation


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.db.SharePreferencesHelper
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.Constant
import com.mbcq.commonlibrary.NumberPlateBean
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean
import com.mbcq.commonlibrary.dialog.FilterDialog
import kotlinx.android.synthetic.main.activity_commonly_information.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-30 08:35:12 常用开单信息配置 Configuration
 */

@Route(path = ARouterConstants.CommonlyInformationActivity)
class CommonlyInformationActivity : BaseListMVPActivity<CommonlyInformationContract.View, CommonlyInformationPresenter, CommonlyInformationBean>(), CommonlyInformationContract.View {
    var mSharePreferencesHelper: SharePreferencesHelper? = null

    /**
     * 常用收货方式
     */
    val COMMONLY_USED_RECEIVING_METHODS = "COMMONLY_USED_RECEIVING_METHODS"

    /**
     * 常用付货方式
     */
    val COMMON_DELIVERY_METHODS = "COMMON_DELIVERY_METHODS"

    override fun getLayoutId(): Int = R.layout.activity_commonly_information

    override fun initExtra() {
        super.initExtra()
        if (mSharePreferencesHelper == null)
            mSharePreferencesHelper = SharePreferencesHelper(mContext, Constant.COMMON_CONFIGURATION_PREFERENCESMODE)
    }

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
                CommonlyInformationBean("常用收货网点", "设置经常使用的收货地址"),
                CommonlyInformationBean("常用目的地", "设置经常使用的目的地"),
                CommonlyInformationBean("常用收货方式",
                        if (mSharePreferencesHelper?.contain(COMMONLY_USED_RECEIVING_METHODS)!!)
                            mSharePreferencesHelper?.getSharePreference(COMMONLY_USED_RECEIVING_METHODS, "设置经常使用的收货方式") as String
                        else
                            "设置经常使用的收货方式"),
                CommonlyInformationBean("常用付货方式",
                        if (mSharePreferencesHelper?.contain(COMMON_DELIVERY_METHODS)!!)
                            mSharePreferencesHelper?.getSharePreference(COMMON_DELIVERY_METHODS, "设置经常使用的常用付货方式") as String
                        else
                            "设置经常使用的常用付货方式"),
                CommonlyInformationBean("常用货物名称", "设置经常使用的货物名称"),
                CommonlyInformationBean("常用包装方式", "设置经常使用的包装方式"),
                CommonlyInformationBean("常用开单备注", "设置经常使用的备注")
        ))
        it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                when (mResult) {
                    "常用收货方式" -> {
                        val mShowList = mutableListOf<BaseTextAdapterBean>()
                        for (index in 0..1) {
                            val mBaseTextAdapterBean = BaseTextAdapterBean()
                            if (index == 0)
                                mBaseTextAdapterBean.title = "客户自送"
                            else if (index == 1)
                                mBaseTextAdapterBean.title = "上门提货"
                            mShowList.add(mBaseTextAdapterBean)
                        }
                        FilterDialog(getScreenWidth(), Gson().toJson(mShowList), "title", "选择常用收货方式", true, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                            @SuppressLint("SetTextI18n")
                            override fun onItemClick(v: View, mPosition: Int, mResult: String) {
                                val mSelectData = Gson().fromJson<BaseTextAdapterBean>(mResult, BaseTextAdapterBean::class.java)
                                it.notifyItemChangeds(position, CommonlyInformationBean("常用收货方式", mSelectData.title))
                                mSharePreferencesHelper?.put(COMMONLY_USED_RECEIVING_METHODS, mSelectData.title)
                            }

                        }).show(supportFragmentManager, "getVehicleSFilterDialog")
                    }
                    "常用付货方式" -> {
                        val mShowList = mutableListOf<BaseTextAdapterBean>()
                        for (index in 0..2) {
                            val mBaseTextAdapterBean = BaseTextAdapterBean()
                            when (index) {
                                0 -> mBaseTextAdapterBean.title = "客户自提"
                                1 -> mBaseTextAdapterBean.title = "送货上门"
                                2 -> mBaseTextAdapterBean.title = "司机直送"
                            }
                            mShowList.add(mBaseTextAdapterBean)
                        }
                        FilterDialog(getScreenWidth(), Gson().toJson(mShowList), "title", "选择常用付货方式", true, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                            @SuppressLint("SetTextI18n")
                            override fun onItemClick(v: View, mPosition: Int, mResult: String) {
                                val mSelectData = Gson().fromJson<BaseTextAdapterBean>(mResult, BaseTextAdapterBean::class.java)
                                it.notifyItemChangeds(position, CommonlyInformationBean("常用付货方式", mSelectData.title))
                                mSharePreferencesHelper?.put(COMMON_DELIVERY_METHODS, mSelectData.title)

                            }

                        }).show(supportFragmentManager, "getVehicleSFilterDialog")
                    }
                    "常用开单备注" -> {
                        ARouter.getInstance().build(ARouterConstants.CommonlyInformationConfigurationRemarkActivity).navigation()
                    }
                    else -> {
                        ARouter.getInstance().build(ARouterConstants.CommonlyInformationConfigurationActivity).withString("CommonlyInformationConfiguration", mResult).navigation()
                    }
                }

            }

        }
    }
}