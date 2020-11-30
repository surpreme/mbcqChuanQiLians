package com.mbcq.accountlibrary.activity.commonlyinformationconfiguration


import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.baselibrary.view.recyclermove.ItemDragHelperCallback
import com.mbcq.baselibrary.view.recyclermove.OnRecyclerItemClickListener
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import kotlinx.android.synthetic.main.activity_commonly_information_configuration.*
import org.json.JSONArray


/**
 * @author: lzy
 * @time: 2020-11-30 11:11:46 常用网点配置详情页面
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
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.white)
        commonly_information_configuration_toolbar.setLeftTitleText(mShowStr)
        father_configuration_recycler.layoutManager = GridLayoutManager(mContext, 4)
        selected_recycler.layoutManager = GridLayoutManager(mContext, 4)
        mCommonlyInformationConfigurationAdapter = CommonlyInformationConfigurationAdapter(mContext).also {
            it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                override fun onItemClick(v: View, position: Int, mResult: String) {
                    var hasItem = false
                    for (item in mShowList) {
                        if (item == mResult) {
                            hasItem = true
                        }
                    }
                    if (!hasItem) {
                        mCommonlyInformationConfigurationSelectedAdapter?.appendData(mutableListOf(BaseTextAdapterBean(mResult, mResult)))
                        mShowList.add(mResult)

                    } else
                        showToast("您已经选过${mResult}了")

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
        when (mShowStr) {
            "常用收货网点" -> {
                WebDbUtil.getDbWebId(application, object : WebsDbInterface {
                    override fun isNull() {

                    }

                    override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                        val showDatas = mutableListOf<BaseTextAdapterBean>()
                        for (item in list) {
                            val mBaseAdapterBean = BaseTextAdapterBean()
                            mBaseAdapterBean.title = item.webid
                            mBaseAdapterBean.tag = item.webid
                            showDatas.add(mBaseAdapterBean)
                        }
                        mCommonlyInformationConfigurationAdapter?.appendData(showDatas)
                    }

                })
            }
            "常用目的地" -> {
                mPresenter?.getDestination()

            }
            "常用收货方式" -> {

            }
            "常用付货方式" -> {

            }
            "常用货物名称" -> {
                mPresenter?.getCargoAppellation()

            }
            "常用包装方式" -> {
                mPresenter?.getPackage()

            }
            "常用开单备注" -> {

            }
        }

    }

    override fun onClick() {
        super.onClick()
        commonly_information_configuration_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun onFinishDrag() {

    }

    fun showData(result: String, showTag: String) {
        val jry = JSONArray(result)
        val showDatas = mutableListOf<BaseTextAdapterBean>()
        for (index in 0 until jry.length()) {
            val mBaseAdapterBean = BaseTextAdapterBean()
            mBaseAdapterBean.title = jry.optJSONObject(index).optString(showTag)
            mBaseAdapterBean.tag = jry.optJSONObject(index).optString(showTag)
            showDatas.add(mBaseAdapterBean)
        }
        mCommonlyInformationConfigurationAdapter?.appendData(showDatas)
    }

    override fun getDestinationS(result: String) {
        showData(result, "mapDes")

    }

    override fun getPackageS(result: String) {
        showData(result, "packages")
    }

    override fun getCargoAppellationS(result: String) {
        showData(result, "product")

    }
}