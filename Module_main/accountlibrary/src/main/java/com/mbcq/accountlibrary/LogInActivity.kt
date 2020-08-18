package com.mbcq.accountlibrary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.accountlibrary.login.LogInContract
import com.mbcq.accountlibrary.login.LogInPresenter
import com.mbcq.accountlibrary.login.LogInSuccessBean
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import kotlinx.android.synthetic.main.activity_log_in.*
import java.util.*

@Route(path = ARouterConstants.LogInActivity)
class LogInActivity : BaseMVPActivity<LogInContract.View,LogInPresenter>(),LogInContract.View {

    override fun getLayoutId(): Int =R.layout.activity_log_in
    override fun onClick() {
        super.onClick()
        login_btn.setOnClickListener (object:SingleClick(){
            override fun onSingleClick(v: View?) {
                mPresenter?.logIn(name_edit.text.toString(),psw_edit.text.toString())
            }

        })
    }

    override fun isShowErrorDialog(): Boolean=true

    override fun loInS(result: LogInSuccessBean) {
        TalkSureDialog(mContext,getScreenWidth(),"登录成功").show()

    }


}
