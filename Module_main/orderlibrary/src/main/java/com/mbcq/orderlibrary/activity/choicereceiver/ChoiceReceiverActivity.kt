package com.mbcq.orderlibrary.activity.choicereceiver


import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.util.system.pinyin.PinYinUtil
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.activity.choiceshipper.ChoiceShipperBean
import kotlinx.android.synthetic.main.activity_choice_receiver.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-02 15:08:32 选择收货人
 */

@Route(path = ARouterConstants.ChoiceReceiverActivity)
class ChoiceReceiverActivity : BaseListMVPActivity<ChoiceReceiverContract.View, ChoiceReceiverPresenter, ChoiceReceiverBean>(), ChoiceReceiverContract.View {
    val RECEIVER_RESULT_DATA_CODE = 4439
    private val mMoreData = mutableListOf<ChoiceReceiverBean>()

    override fun getLayoutId(): Int = R.layout.activity_choice_receiver
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        filter_search_ed.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isBlank()) {
                    adapter.replaceData(mMoreData)
                    return
                }
                val mSearchDatas = mutableListOf<ChoiceReceiverBean>()
                for (item in adapter.getAllData()) {
                    if (item.contactMb.startsWith(s.toString()) or item.contactMan.contains(s.toString()) or item.cVipId.contains(s.toString()) or item.address.contains(s.toString())) {
                        mSearchDatas.add(item)
                    } else {
                        if (PinYinUtil.getFirstSpell(item.contactMan).contains(s.toString().toLowerCase()) or PinYinUtil.getFullSpell(item.address).contains(s.toString().toLowerCase()))
                            mSearchDatas.add(item)

                    }
                }
                if (mSearchDatas.isNotEmpty()) {
                    adapter.replaceData(mSearchDatas)
                }

            }

        })
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.getInfo()

    }

    override fun onClick() {
        super.onClick()
        choice_receiver_toolbar.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    override fun getRecyclerViewId(): Int = R.id.choice_receiver_recycler
    override fun setAdapter(): BaseRecyclerAdapter<ChoiceReceiverBean> = ChoiceReceiverAdapter(mContext).also {
        it.mOnDeleteInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val obj = JSONObject(mResult)
                TalkSureCancelDialog(mContext, getScreenWidth(), "您确认要删除收货客户${obj.optString("contactMan")}吗？") {
                    mPresenter?.deleteReceiver(obj.optString("id"), position)
                }.show()

            }

        }
        it.mOnFixedInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                ARouter.getInstance().build(ARouterConstants.FixReceiverActivity).withString("FixReceiverData", mResult).navigation()
            }

        }
        it.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {

                val resultObj = JSONObject(mResult)
                val obj = JSONObject()
                obj.put("name", resultObj.optString("contactMan"))
                obj.put("phone", resultObj.optString("shipperMb"))
                obj.put("address", resultObj.optString("address"))
                obj.put("shipperTel", resultObj.optString("shipperTel"))
                obj.put("product", resultObj.optString("product"))
                obj.put("package", resultObj.optString("package"))
                val json = GsonUtils.toPrettyFormat(obj.toString())
                setResult(RECEIVER_RESULT_DATA_CODE, Intent().putExtra("AddReceiveResultData", json))
                finish()
            }

        }
    }

    override fun getInfoS(list: List<ChoiceReceiverBean>) {
        adapter.replaceData(list)
    }

    override fun deleteReceiverS(position: Int) {
        adapter.removeItem(position)

    }
}