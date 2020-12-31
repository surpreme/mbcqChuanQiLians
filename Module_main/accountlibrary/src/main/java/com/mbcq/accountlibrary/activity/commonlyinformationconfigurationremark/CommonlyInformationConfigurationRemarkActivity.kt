package com.mbcq.accountlibrary.activity.commonlyinformationconfigurationremark


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.accountlibrary.R
import com.mbcq.accountlibrary.activity.commonlyinformationconfiguration.CommonlyInformationConfigurationAdapter
import com.mbcq.accountlibrary.activity.commonlyinformationconfiguration.CommonlyInformationConfigurationSaveBean
import com.mbcq.accountlibrary.activity.commonlyinformationconfiguration.CommonlyInformationConfigurationSelectedAdapter
import com.mbcq.baselibrary.db.SharePreferencesHelper
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.baselibrary.view.recyclermove.ItemDragHelperCallback
import com.mbcq.baselibrary.view.recyclermove.OnRecyclerItemClickListener
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.Constant
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean
import kotlinx.android.synthetic.main.activity_commonly_information_configuration_remark.*

/**
 * @author: lzy
 * @time: 2020-12-30 10:22:43 常见备注偏好设置配置
 */

@Route(path = ARouterConstants.CommonlyInformationConfigurationRemarkActivity)
class CommonlyInformationConfigurationRemarkActivity : BaseMVPActivity<CommonlyInformationConfigurationRemarkContract.View, CommonlyInformationConfigurationRemarkPresenter>(), CommonlyInformationConfigurationRemarkContract.View, ItemDragHelperCallback.OnDragListener {
    val mShowList = mutableListOf<String>()
    var mSharePreferencesHelper: SharePreferencesHelper? = null
    val COMMON_USERS_REMARK = "COMMON_USERS_REMARK"
    var mCommonlyInformationConfigurationAdapter: CommonlyInformationConfigurationAdapter? = null
    var mCommonlyInformationConfigurationSelectedAdapter: CommonlyInformationConfigurationSelectedAdapter? = null

    override fun getLayoutId(): Int = R.layout.activity_commonly_information_configuration_remark
    
    override fun initExtra() {
        super.initExtra()
        if (mSharePreferencesHelper == null)
            mSharePreferencesHelper = SharePreferencesHelper(mContext, Constant.COMMON_CONFIGURATION_PREFERENCESMODE)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.white)
        mCommonlyInformationConfigurationAdapter = CommonlyInformationConfigurationAdapter(mContext).also {
            it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                override fun onItemClick(v: View, position: Int, mResult: String) {
                    val selectData = Gson().fromJson<BaseTextAdapterBean>(mResult, BaseTextAdapterBean::class.java)
                    var hasItem = false
                    for (item in mShowList) {
                        if (item == selectData.title) {
                            hasItem = true
                        }
                    }
                    if (!hasItem) {
                        mCommonlyInformationConfigurationSelectedAdapter?.appendData(mutableListOf(BaseTextAdapterBean(selectData.title, selectData.tag)))
                        mShowList.add(selectData.title)

                    } else
                        showToast("您已经选过${selectData.title}了")

                }

            }
            father_configuration_recycler.layoutManager = GridLayoutManager(mContext, 4)
            father_configuration_recycler.adapter = it


        }
        mCommonlyInformationConfigurationSelectedAdapter = CommonlyInformationConfigurationSelectedAdapter(mContext).also {
            selected_recycler.layoutManager = GridLayoutManager(mContext, 4)
            selected_recycler.adapter = it
            it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                override fun onItemClick(v: View, position: Int, mResult: String) {
                    it.removeItem(position)
                    mShowList.removeAt(position)
                }

            }

        }
        val touchHelper = ItemTouchHelper(ItemDragHelperCallback(mCommonlyInformationConfigurationSelectedAdapter).setOnDragListener(this))
        //attach到RecyclerView中
        touchHelper.attachToRecyclerView(selected_recycler)
        selected_recycler.addOnItemTouchListener(object : OnRecyclerItemClickListener(selected_recycler) {
            override fun onLongClick(vh: RecyclerView.ViewHolder) {
                mCommonlyInformationConfigurationSelectedAdapter?.getAllData()?.let {
                    touchHelper.startDrag(vh)
                }


            }

        })
    }

    override fun initDatas() {
        super.initDatas()
        (mSharePreferencesHelper?.contain(COMMON_USERS_REMARK))?.let {
            if (it) {
                val mSaveInfo = Gson().fromJson<CommonlyInformationConfigurationSaveBean>((mSharePreferencesHelper?.getSharePreference(COMMON_USERS_REMARK, "{list:[]}") as String), CommonlyInformationConfigurationSaveBean::class.java)
                if (!mSaveInfo.list.isNullOrEmpty()) {
                    val mWaitShowList = mutableListOf<BaseTextAdapterBean>()
                    for (mItem in mSaveInfo.list) {
                        mWaitShowList.add(BaseTextAdapterBean(mItem.getmTitle(), mItem.getmContentStr()))
                        mShowList.add(mItem.getmTitle())
                    }
                    mCommonlyInformationConfigurationSelectedAdapter?.appendData(mWaitShowList)
                }
            }
        }
    }

    override fun onClick() {
        super.onClick()
        commit_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mCommonlyInformationConfigurationSelectedAdapter?.getAllData()?.let {
                    if (mSharePreferencesHelper == null)
                        mSharePreferencesHelper = SharePreferencesHelper(mContext, Constant.COMMON_CONFIGURATION_PREFERENCESMODE)
                    val mSelectListBean = mutableListOf<CommonlyInformationConfigurationSaveBean.CommonlyInformationConfigurationSaveItemBean>()
                    for (item in it) {
                        val mCommonlyInformationConfigurationSaveItemBean = CommonlyInformationConfigurationSaveBean.CommonlyInformationConfigurationSaveItemBean()
                        mCommonlyInformationConfigurationSaveItemBean.setmTitle(item.title)
                        mCommonlyInformationConfigurationSaveItemBean.setmContentStr(item.tag)
                        mSelectListBean.add(mCommonlyInformationConfigurationSaveItemBean)
                    }
                    val mCommonlyInformationConfigurationSaveBean = CommonlyInformationConfigurationSaveBean(mSelectListBean)
                    TalkSureDialog(mContext, getScreenWidth(), if (mSharePreferencesHelper?.put(COMMON_USERS_REMARK, Gson().toJson(mCommonlyInformationConfigurationSaveBean))!!) "保存成功！" else "保存失败！") {
                        onBackPressed()
                    }.show()
                }
            }

        })

        father_configuration_add_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                CommonlyInformationConfigurationRemarkDialog(object : OnClickInterface.OnClickInterface {
                    override fun onResult(s1: String, s2: String) {
                        mCommonlyInformationConfigurationAdapter?.appendData(mutableListOf(BaseTextAdapterBean(s1, s1)))
                    }

                }).show(supportFragmentManager, "CommonlyInformationConfigurationRemarkDialog")

            }

        })
        commonly_information_configuration_remark_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun onFinishDrag() {

    }
}