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
 * mScanType @1未装车 @2已扫描
 */
class ScanNumDialog(var mUnScanNum: Int = 0, var mScanType: Int = 0, var mClackInterface: OnClickInterface.OnClickInterface? = null) : BaseDialogFragment() {
    override fun initView(view: View, savedInstanceState: Bundle?) {
        scan_num_ed.hint = "还有$mUnScanNum 件${if (mScanType == 1) "未装车" else if (mScanType == 2) "已装车" else ""}"
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
//                mCancelClackInterface?.onResult("", "")
                dismiss()
            }

        })
    }

    override fun setContentView(): Int = R.layout.dialog_scan_num

}