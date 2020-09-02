package com.mbcq.commonlibrary.dialog

import android.os.Bundle
import android.view.View
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.commonlibrary.R

class FilterDialog :BaseDialogFragment(){
    override fun initView(view: View, savedInstanceState: Bundle?) {

    }

    override fun setContentView(): Int = R.layout.dialog_filter_recyclerview

}