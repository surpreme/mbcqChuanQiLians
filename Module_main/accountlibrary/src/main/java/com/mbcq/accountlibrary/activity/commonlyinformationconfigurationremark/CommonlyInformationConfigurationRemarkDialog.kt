package com.mbcq.accountlibrary.activity.commonlyinformationconfigurationremark

import android.os.Bundle
import android.view.View
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.util.system.ToastUtils
import com.mbcq.baselibrary.view.SingleClick
import kotlinx.android.synthetic.main.dialog_commonly_information_configuration_remark.*

class CommonlyInformationConfigurationRemarkDialog(var mClackInterface: OnClickInterface.OnClickInterface? = null) : BaseDialogFragment() {
    override fun initView(view: View, savedInstanceState: Bundle?) {
        sure_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (configuration_remark_ed.text.toString().isNotBlank()) {
                    mClackInterface?.onResult(configuration_remark_ed.text.toString(), "")
                    dismiss()

                }else{
                    ToastUtils.showToast(context,"请检查你输入的数据是否为空！")
                }
            }

        })
        cancel_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                dismiss()
            }

        })
    }

    override fun setContentView(): Int = R.layout.dialog_commonly_information_configuration_remark
}