package com.mbcq.accountlibrary.activity.commonlyinformationconfiguration


import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.db.SharePreferencesHelper
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseConstant
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.baselibrary.view.recyclermove.ItemDragHelperCallback
import com.mbcq.baselibrary.view.recyclermove.OnRecyclerItemClickListener
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.Constant
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import kotlinx.android.synthetic.main.activity_commonly_information_configuration.*
import org.json.JSONArray


/**
 * @author: lzy
 * @time: 2020-11-30 11:11:46 常用配置详情页面
 * @CommonlyInformationConfigurationRemarkActivity 常见备注偏好设置配置
 */

@Route(path = ARouterConstants.CommonlyInformationConfigurationActivity)
class CommonlyInformationConfigurationActivity : BaseMVPActivity<CommonlyInformationConfigurationContract.View, CommonlyInformationConfigurationPresenter>(), CommonlyInformationConfigurationContract.View, ItemDragHelperCallback.OnDragListener {
    @Autowired(name = "CommonlyInformationConfiguration")
    @JvmField
    var mShowStr: String = ""
    var mCommonlyInformationConfigurationAdapter: CommonlyInformationConfigurationAdapter? = null
    var mCommonlyInformationConfigurationSelectedAdapter: CommonlyInformationConfigurationSelectedAdapter? = null
    val mShowList = mutableListOf<String>()

    override fun getLayoutId(): Int = R.layout.activity_commonly_information_configuration
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
        if (mSharePreferencesHelper == null)
            mSharePreferencesHelper = SharePreferencesHelper(mContext, Constant.COMMON_CONFIGURATION_PREFERENCESMODE)
    }

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.white)
        if (mShowStr.length > 2)
            tips_title_tv.text = "所有${mShowStr.substring(2, mShowStr.length)}（长按拖拽改变位置顺序）"
        commonly_information_configuration_toolbar.setLeftTitleText(mShowStr)
        father_configuration_recycler.layoutManager = GridLayoutManager(mContext, 4)
        selected_recycler.layoutManager = GridLayoutManager(mContext, 4)
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
            father_configuration_recycler.adapter = it


        }

        mCommonlyInformationConfigurationSelectedAdapter = CommonlyInformationConfigurationSelectedAdapter(mContext).also {
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
//                    if (vh.layoutPosition !=it.size-1) {
                    touchHelper.startDrag(vh)
//                    }
                }


            }

        })

    }

    override fun initDatas() {
        super.initDatas()
//        TalkSureDialog(mContext,getScreenWidth() , mSharePreferencesHelper?.getSharePreference(RECEIVING_OUTLETS, "") as String).show()
        when (mShowStr) {
            "常用收货网点" -> {//RECEIVING_OUTLETS
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {

                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        val showDatas = mutableListOf<BaseTextAdapterBean>()
                        for (item in list) {
                            val mBaseAdapterBean = BaseTextAdapterBean()
                            mBaseAdapterBean.title = item.webid
                            mBaseAdapterBean.tag = Gson().toJson(item)
                            showDatas.add(mBaseAdapterBean)
                        }
                        mCommonlyInformationConfigurationAdapter?.appendData(showDatas)
                    }

                })
            }
            "常用目的地" -> {//FREQUENTLY_USED_DESTINATIONS
                mPresenter?.getDestination()

            }
            "常用收货方式" -> {

            }
            "常用付货方式" -> {

            }
            "常用货物名称" -> {//COMMON_GOODS_NAME
                mPresenter?.getCargoAppellation()

            }
            "常用包装方式" -> {//COMMON_PACKAGING_METHODS
                mPresenter?.getPackage()

            }
            "常用开单备注" -> {

            }
        }
        when (mShowStr) {
            "常用收货网点" -> {
                (mSharePreferencesHelper?.contain(RECEIVING_OUTLETS))?.let {
                    if (it) {
                        val mSaveInfo = Gson().fromJson<CommonlyInformationConfigurationSaveBean>((mSharePreferencesHelper?.getSharePreference(RECEIVING_OUTLETS, "{list:[]}") as String), CommonlyInformationConfigurationSaveBean::class.java)
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
            "常用目的地" -> {
                (mSharePreferencesHelper?.contain(FREQUENTLY_USED_DESTINATIONS))?.let {
                    if (it) {
                        val mSaveInfo = Gson().fromJson<CommonlyInformationConfigurationSaveBean>((mSharePreferencesHelper?.getSharePreference(FREQUENTLY_USED_DESTINATIONS, "{list:[]}") as String), CommonlyInformationConfigurationSaveBean::class.java)
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
            "常用货物名称" -> {
                (mSharePreferencesHelper?.contain(COMMON_GOODS_NAME))?.let {
                    if (it) {
                        val mSaveInfo = Gson().fromJson<CommonlyInformationConfigurationSaveBean>((mSharePreferencesHelper?.getSharePreference(COMMON_GOODS_NAME, "{list:[]}") as String), CommonlyInformationConfigurationSaveBean::class.java)
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
            "常用包装方式" -> {
                (mSharePreferencesHelper?.contain(COMMON_PACKAGING_METHODS))?.let {
                    if (it) {
                        val mSaveInfo = Gson().fromJson<CommonlyInformationConfigurationSaveBean>((mSharePreferencesHelper?.getSharePreference(COMMON_PACKAGING_METHODS, "{list:[]}") as String), CommonlyInformationConfigurationSaveBean::class.java)
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
        }


    }

    /**
     * 常用网点
     */
    val RECEIVING_OUTLETS = "RECEIVING_OUTLETS"

    /**
     * 常用目的地
     */
    val FREQUENTLY_USED_DESTINATIONS = "FREQUENTLY_USED_DESTINATIONS"

    /**
     * 常用货物名称
     */
    val COMMON_GOODS_NAME = "COMMON_GOODS_NAME"

    /**
     * 常用包装方式
     */
    val COMMON_PACKAGING_METHODS = "COMMON_PACKAGING_METHODS"


    override fun onClick() {
        super.onClick()
        commit_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mCommonlyInformationConfigurationSelectedAdapter?.getAllData()?.let {
                    /*if (it.isNullOrEmpty())
                        return*/
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
                    var isSaveSuccess = false
                    when (mShowStr) { //常用网点
                        "常用收货网点" -> {
                            isSaveSuccess = mSharePreferencesHelper?.put(RECEIVING_OUTLETS, Gson().toJson(mCommonlyInformationConfigurationSaveBean))!!
                        }
                        "常用目的地" -> {
                            isSaveSuccess = mSharePreferencesHelper?.put(FREQUENTLY_USED_DESTINATIONS, Gson().toJson(mCommonlyInformationConfigurationSaveBean))!!

                        }
                        "常用货物名称" -> {
                            isSaveSuccess = mSharePreferencesHelper?.put(COMMON_GOODS_NAME, Gson().toJson(mCommonlyInformationConfigurationSaveBean))!!

                        }
                        "常用包装方式" -> {
                            isSaveSuccess = mSharePreferencesHelper?.put(COMMON_PACKAGING_METHODS, Gson().toJson(mCommonlyInformationConfigurationSaveBean))!!

                        }
                    }



                    TalkSureDialog(mContext, getScreenWidth(), if (isSaveSuccess) "保存成功！" else "保存失败！") {
                        onBackPressed()
                    }.show()
                }
            }

        })
        commonly_information_configuration_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    var mSharePreferencesHelper: SharePreferencesHelper? = null
    override fun onFinishDrag() {


    }

    fun showData(result: String, showTag: String, resultTag: String) {
        val jry = JSONArray(result)
        val showDatas = mutableListOf<BaseTextAdapterBean>()
        for (index in 0 until jry.length()) {
            val mBaseAdapterBean = BaseTextAdapterBean()
            mBaseAdapterBean.title = jry.optJSONObject(index).optString(showTag)
            mBaseAdapterBean.tag = if (resultTag != "ALL") jry.optJSONObject(index).optString(resultTag) else GsonUtils.toPrettyFormat(jry.optJSONObject(index))
            showDatas.add(mBaseAdapterBean)
        }
        mCommonlyInformationConfigurationAdapter?.appendData(showDatas)
    }

    override fun getDestinationS(result: String) {
        showData(result, "mapDes", "ALL")

    }

    override fun getPackageS(result: String) {
        showData(result, "packages", "packages")
    }

    override fun getCargoAppellationS(result: String) {
        showData(result, "product", "ALL")

    }
}