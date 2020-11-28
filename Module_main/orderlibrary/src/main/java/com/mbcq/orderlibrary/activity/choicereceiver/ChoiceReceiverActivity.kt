package com.mbcq.orderlibrary.activity.choicereceiver


import android.content.Intent
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_choice_receiver.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-02 15:08:32 选择收货人
 */

@Route(path = ARouterConstants.ChoiceReceiverActivity)
class ChoiceReceiverActivity : BaseListMVPActivity<ChoiceReceiverContract.View, ChoiceReceiverPresenter, ChoiceReceiverBean>(), ChoiceReceiverContract.View {
     val RECEIVER_RESULT_DATA_CODE = 4439

    override fun getLayoutId(): Int = R.layout.activity_choice_receiver
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun initDatas() {
        super.initDatas()
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
        adapter.appendData(list)
    }
}