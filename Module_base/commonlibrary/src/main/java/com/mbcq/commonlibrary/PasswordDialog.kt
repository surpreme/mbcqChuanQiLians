package com.mbcq.commonlibrary

import android.os.Bundle
import android.view.View
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.util.system.ToastUtils
import com.mbcq.baselibrary.view.SingleClick
import kotlinx.android.synthetic.main.dialog_password_sure.*

class PasswordDialog(val mPassword: String, var mOnResult: OnClickInterface.OnClickInterface? = null) : BaseDialogFragment() {
    override fun initView(view: View, savedInstanceState: Bundle?) {
        sure_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (information_ed.text.toString() == mPassword) {
                    mOnResult?.onResult("1", "1")
                    dismiss()
                } else {
                    information_ed.setText("")
                    ToastUtils.showToast(mContext, "请检查您的密码后重试")
                }
            }

        })
        cancel_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                dismiss()
            }

        })
    }

    override fun setContentView(): Int = R.layout.dialog_password_sure
}