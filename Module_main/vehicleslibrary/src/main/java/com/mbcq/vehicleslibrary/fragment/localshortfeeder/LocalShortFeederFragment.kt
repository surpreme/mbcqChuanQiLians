package com.mbcq.vehicleslibrary.fragment.localshortfeeder


import android.view.View
import com.mbcq.baselibrary.ui.mvp.BaseMVPFragment
import com.mbcq.vehicleslibrary.R

/**
 * @author: lzy
 * @time: 2020-09-22 17:57:12
 */

class LocalShortFeederFragment : BaseMVPFragment<LocalShortFeederContract.View, LocalShortFeederPresenter>(), LocalShortFeederContract.View {
    override fun getLayoutResId() = R.layout.fragment_local_short_feeder

    override fun initViews(view: View) {

    }
}