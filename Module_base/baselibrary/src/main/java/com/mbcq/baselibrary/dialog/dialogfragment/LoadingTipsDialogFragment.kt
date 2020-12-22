package com.mbcq.baselibrary.dialog.dialogfragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.mbcq.baselibrary.R
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import kotlinx.android.synthetic.main.dialog_loading_withtips.*

/**
 * @Auther: valy
 * @datetime: 2020-02-28
 * @desc:
 */
class LoadingTipsDialogFragment(var loadingTipsTv: String = "") : BaseDialogFragment() {
    @SuppressLint("SetTextI18n")
    override fun initView(view: View, savedInstanceState: Bundle?) {
        if (loadingTipsTv.isBlank())
            loading_tips_tv.visibility = View.GONE
        else
            loading_tips_tv.text = loadingTipsTv
    }

    override fun setContentView(): Int = R.layout.dialog_loading_withtips


    override fun setIsShowBackDark(): Boolean = true
}