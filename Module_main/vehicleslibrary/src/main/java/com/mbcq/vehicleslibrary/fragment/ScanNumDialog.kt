package com.mbcq.vehicleslibrary.fragment

import android.os.Bundle
import android.view.View
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.dialog_scan_num.*

/**
 * 扫描输入扫描件数
 */
class ScanNumDialog(var mClackInterface: OnClickInterface.OnClickInterface? = null,var mCancelClackInterface: OnClickInterface.OnClickInterface? = null) : BaseDialogFragment() {
    override fun initView(view: View, savedInstanceState: Bundle?) {
        sure_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (scan_num_ed.text.toString().isBlank())
                    return
                mClackInterface?.onResult(scan_num_ed.text.toString(), "")
                dismiss()
            }

        })
        cancel_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mCancelClackInterface?.onResult("","")
                dismiss()
            }

        })
    }

    override fun setContentView(): Int = R.layout.dialog_scan_num

}