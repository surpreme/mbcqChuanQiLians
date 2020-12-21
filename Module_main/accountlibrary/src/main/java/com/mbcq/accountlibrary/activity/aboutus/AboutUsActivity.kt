package com.mbcq.accountlibrary.activity.aboutus


import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.util.system.SystemUtil
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import kotlinx.android.synthetic.main.activity_about_us.*


/**
 * @author: lzy
 * @time: 2020-12-15 16:04:34 关于我们
 */

@Route(path = ARouterConstants.AboutUsActivity)
class AboutUsActivity : BaseMVPActivity<AboutUsContract.View, AboutUsPresenter>(), AboutUsContract.View {
    override fun getLayoutId(): Int = R.layout.activity_about_us

    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.white)
        version_tv.text = "${SystemUtil.getAppVersionName(mContext)}版本号"
    }

    override fun onClick() {
        super.onClick()
        phone_number_tv.apply {
            onSingleClicks {
                val intent = Intent(Intent.ACTION_DIAL)
                val data: Uri = Uri.parse("tel:${phone_number_tv.text}")
                intent.data = data
                startActivity(intent)
            }
        }
        net_address_tv.apply {
            onSingleClicks {
                val uri = Uri.parse("http://www.mbcq56.com/") //要跳转的网址
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(intent)

            }
        }

        about_us_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
}