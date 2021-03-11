package com.mbcq.accountlibrary.fragment.report

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.accountlibrary.activity.house.AuthorityMenuBean
import com.mbcq.accountlibrary.fragment.iconadapter.IconViewBean
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

class ReportPresenter : BasePresenterImpl<ReportContract.View>(), ReportContract.Presenter {

    /**
     * title : APP_运营
     * authCode : 140000
     * spread : false
     * icon :
     * list : [{"title":"发货管理","url":"/order","authCode":"140001","list":[{"authCode":"141001","title":"受理开单","url":"/order/AcceptBillingActivity","list":[]},{"authCode":"141002","title":"运单记录","url":"/order/WaybillRecordActivity","list":[]},{"authCode":"141003","title":"上门提货","url":"/order","list":[]},{"authCode":"141004","title":"改单申请","url":"/order","list":[]},{"authCode":"141005","title":"运单标签补打","url":"/order","list":[]},{"authCode":"141006","title":"发货库存","url":"/order","list":[]},{"authCode":"141007","title":"沿途配载","url":"/order","list":[]},{"authCode":"141008","title":"外转","url":"/order","list":[]}]},{"title":"到货管理","url":"/order","authCode":"140002","list":[]}]
     */
    override fun getMenuAuthority() {
        val params = HttpParams()
        params.put("menutype", "app")
        get<String>(ApiInterface.NAVIGATION_ITEM_MENU_AUTHORITY_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    val mAuthorityMenuBeanList = Gson().fromJson<List<AuthorityMenuBean>>(obj.optString("data"), object : TypeToken<List<AuthorityMenuBean>>() {}.type)
                    mAuthorityMenuBeanList?.let {
                        for (item in it) {
                            if (item.title.contains("报表")) {
                                val iconList = mutableListOf<IconViewBean>()
                                for (itemX in item.list) {
                                    val mIconViewBean = IconViewBean()
                                    mIconViewBean.title = itemX.title
                                    mIconViewBean.authCode = itemX.authCode
                                    mIconViewBean.tag = 1
                                    iconList.add(mIconViewBean)
                                    if (!itemX.list.isNullOrEmpty()) {
                                        val mItemIconViewBean = IconViewBean()
                                        mItemIconViewBean.tag=2
                                        val listItem = arrayListOf<IconViewBean.ItemBean>()
                                        for (itemZ in itemX.list) {
                                            val mXIconItem = IconViewBean.ItemBean()
                                            mXIconItem.itemText = itemZ.title
                                            mXIconItem.arouterAddress = itemZ.url
                                            mXIconItem.authCode = itemZ.authCode
                                            listItem.add(mXIconItem)
                                        }
                                        mItemIconViewBean.item=listItem
                                        iconList.add(mItemIconViewBean)
                                    }


                                }
                                mView?.getMenuAuthorityS(iconList)
                                break
                            }
                        }
                    }
                }
            }

        })
    }

}