package com.mbcq.baselibrary.dialog.dialogfragment

import android.os.Bundle
import android.view.View
import com.mbcq.baselibrary.R
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment

/**
 * @Auther: valy
 * @datetime: 2020-02-28
 * @desc:
 */
class LoadingDialogFragment : BaseDialogFragment() {
    override fun initView(view: View, savedInstanceState: Bundle?) {

    }

    override fun isCancelable(): Boolean {
        return false
    }
      override fun setCanceledOnTouchOutside(): Boolean {
        return false
    }

    override fun setContentView(): Int=R.layout.dialog_loading


    override fun setIsShowBackDark(): Boolean =true
}