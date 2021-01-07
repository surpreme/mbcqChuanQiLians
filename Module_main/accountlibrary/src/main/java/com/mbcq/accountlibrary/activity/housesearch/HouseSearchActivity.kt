package com.mbcq.accountlibrary.activity.housesearch


import android.os.Bundle
import android.util.TypedValue
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.db.SharePreferencesHelper
import com.mbcq.baselibrary.dialog.common.TalkSureCancelDialog
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.commonlibrary.ARouterConstants
import kotlinx.android.synthetic.main.activity_house_search.*
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-012-08 16:04:45 主页搜索页面
 */

@Route(path = ARouterConstants.HouseSearchActivity)
class HouseSearchActivity : BaseMVPActivity<HouseSearchContract.View, HouseSearchPresenter>(), HouseSearchContract.View {
    val HOUSE_HOSTORY_SEARCH = "HOUSE_HOSTORY_SEARCH"
    val HOUSE_HOSTORY_SEARCH_KEY = "HOUSE_HOSTORY_SEARCH_KEY"
    var mSharePreferencesHelper: SharePreferencesHelper? = null

    override fun getLayoutId(): Int = R.layout.activity_house_search

    override fun initExtra() {
        super.initExtra()
        mSharePreferencesHelper = SharePreferencesHelper(mContext, HOUSE_HOSTORY_SEARCH)

    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.white)
        showHistory()

    }

    override fun onRestart() {
        super.onRestart()
        showHistory()
    }

    fun showHistory() {
        val jsonObj = JSONObject(mSharePreferencesHelper?.getSharePreference(HOUSE_HOSTORY_SEARCH_KEY, "{\n" +
                "   \n" +
                "      \"data\": [\n" +
                "       \n" +
                "      ]\n" +
                "    }") as String)
        jsonObj.optJSONArray("data")?.let {
            val mg = ScreenSizeUtils.dp2px(mContext, 6f)
            val pa = ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            pa.setMargins(0, mg, mg * 2, 0)
            if (history_FlowView.childCount >0)
                history_FlowView.removeAllViews()
            for (i in 0..it.length()) {
                if (!it.isNull(i)) {
                    val item = TextView(mContext)
                    item.text = it.getJSONObject(i).optString("content")
                    item.setBackgroundResource(R.drawable.hollow_out_gray)
                    item.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 14f)
                    item.setPadding(mg, mg, mg, mg)
                    item.setOnClickListener {
                        house_search_ed.setText(item.text)
                        searchOrder()
                    }
                    history_FlowView.addView(item, pa)
                }

            }
            if (it.isNull(0)) {
                history_title_tv.visibility = View.GONE
                history_delete_iv.visibility = View.GONE
            } else {
                history_title_tv.visibility = View.VISIBLE
                history_delete_iv.visibility = View.VISIBLE
            }
        }
    }

    fun searchOrder() {
        if (house_search_ed.text.toString().length > 3) {
            mPresenter?.getWaybillDetail(house_search_ed.text.toString())
        } else {
            showToast("您输入的运单号过短，请核实后重试！")
        }

    }

    override fun onClick() {
        super.onClick()
        history_delete_iv.apply {
            onSingleClicks {
                TalkSureCancelDialog(mContext, getScreenWidth(), "您确定要删除运单历史记录吗？") {
                    mSharePreferencesHelper?.put(HOUSE_HOSTORY_SEARCH_KEY, "{\n" +
                            "   \n" +
                            "      \"data\": [\n" +
                            "       \n" +
                            "      ]\n" +
                            "    }")
                    history_FlowView.removeAllViews()
                    history_title_tv.visibility = View.GONE
                    history_delete_iv.visibility = View.GONE
                }.show()

            }
        }
        house_search_ed.setOnEditorActionListener(object : TextView.OnEditorActionListener {
            override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    searchOrder()
                    return true
                }
                return false
            }

        })

        search_tv.apply {
            onSingleClicks {
                searchOrder()
            }
        }
        back_iv.apply {
            onSingleClicks {
                onBackPressed()
            }
        }
    }

    override fun getWaybillDetailS(data: JSONObject) {
        val olderData = mSharePreferencesHelper?.getSharePreference(HOUSE_HOSTORY_SEARCH_KEY, "{\n" +
                "   \n" +
                "      \"data\": [\n" +
                "       \n" +
                "      ]\n" +
                "    }") as String
        val jsonObj = JSONObject(olderData)
        val jsonAry = jsonObj.optJSONArray("data")
        jsonAry?.let {
            var isHas = false
            for (index in 0..jsonAry.length()) {
                if (!jsonAry.isNull(index)) {
                    if (jsonAry.getJSONObject(index).optString("content").replace(" ","") ==data.optString("billno")) {
                        isHas = true
                        break
                    }

                }
            }
            if (!isHas) {
                val itemJSONObj = JSONObject()
                itemJSONObj.put("content", data.optString("billno"))
                itemJSONObj.put("tag", data.optString("billno"))
                jsonAry.put(itemJSONObj)
                jsonObj.put("data", jsonAry)
                mSharePreferencesHelper?.put(HOUSE_HOSTORY_SEARCH_KEY, GsonUtils.toPrettyFormat(jsonObj))
            }

        }
        house_search_ed.setText("")
        ARouter.getInstance().build(ARouterConstants.WaybillDetailsActivity).withString("WaybillDetails", data.optString("billno")).navigation()
    }

    override fun getWaybillDetailNull() {
        TalkSureDialog(mContext, getScreenWidth(), "未查询到该运单，请核实后重试！").show()
    }
}