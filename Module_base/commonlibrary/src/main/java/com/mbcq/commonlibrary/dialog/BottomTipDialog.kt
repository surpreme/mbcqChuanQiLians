package com.mbcq.commonlibrary.dialog

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.R
import kotlinx.android.synthetic.main.dialog_bottom_tips.*

/**
 * @-1 关闭
 * @1  正常返回
 */
class BottomTipDialog(var mTips: String = "", var mClickInterface: OnClickInterface.OnClickInterface? = null) : BaseDialogFragment() {
    override fun setDialogWidth(): Int {
        return -1
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        bottom_information_tips_tv.text = mTips
        bottom_information_tips_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mClickInterface?.onResult("1", "")
                dismiss()
            }

        })
        cancel_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mClickInterface?.onResult("-1", "")
                dismiss()
            }

        })
    }

    override fun setContentView(): Int = R.layout.dialog_bottom_tips
    override fun setDialogGravity(): Int {
        return Gravity.BOTTOM
    }
}