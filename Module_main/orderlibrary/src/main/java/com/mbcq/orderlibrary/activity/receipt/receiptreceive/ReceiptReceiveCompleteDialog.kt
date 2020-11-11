package com.mbcq.orderlibrary.activity.receipt.receiptreceive

import android.os.Bundle
import android.view.View
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.orderlibrary.R

class ReceiptReceiveCompleteDialog (val mScreenWidth: Int):BaseDialogFragment(){
    override fun setDialogWidth(): Int = mScreenWidth / 10 * 9

    override fun initView(view: View, savedInstanceState: Bundle?) {

    }

    override fun setContentView(): Int = R.layout.dialog_receipt_receive_compelete

}