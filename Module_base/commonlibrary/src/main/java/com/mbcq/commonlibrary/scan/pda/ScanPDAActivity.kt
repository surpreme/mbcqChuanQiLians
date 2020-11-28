package com.mbcq.commonlibrary.scan.pda

import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.R

@Route(path = ARouterConstants.ScanPDAActivity)
class ScanPDAActivity : CommonScanPDAActivity() {
    override fun onPDAScanResult(result: String) {
        TalkSureDialog(mContext, getScreenWidth(), result).show()

    }

    override fun getLayoutId(): Int = R.layout.textview

}