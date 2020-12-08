package com.mbcq.orderlibrary.fragment.waybillpictures


import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.ui.mvp.BaseMVPFragment
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.fragment_waybill_picture.*
import org.json.JSONObject


/**
 * @author: lzy
 * @time: 2020-10-26 09:32:02
 */

class WaybillPictureFragment : BaseMVPFragment<WaybillPictureContract.View, WaybillPicturePresenter>(), WaybillPictureContract.View {
    var WaybillPicture = ""
    var mWaybillPictureAdapter: WaybillPictureAdapter? = null
    override fun getLayoutResId(): Int = R.layout.fragment_waybill_picture
    override fun initViews(view: View) {
        waybill_picture_recycler.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(waybill_picture_recycler)
        mWaybillPictureAdapter = WaybillPictureAdapter(mContext).also {
            waybill_picture_recycler.adapter = it

        }
        waybill_picture_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            @SuppressLint("SetTextI18n")
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (recyclerView.childCount > 0) {
                    try {
                        val currentPosition = (recyclerView.getChildAt(0).layoutParams as RecyclerView.LayoutParams).viewAdapterPosition
                        mRecycler_scroll_index_tv.text = (currentPosition + 1).toString() + "/" + mWaybillPictureAdapter?.getAllData()?.size
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

            }
        })
    }

    override fun initExtra() {
        super.initExtra()
        arguments?.let {
            WaybillPicture = it.getString("WaybillDetails", "{}")
        }
    }

    override fun initDatas() {
        super.initDatas()
        val obj = JSONObject(WaybillPicture)
        mPresenter?.getPictures(obj.optString("billno"))
    }

    @SuppressLint("SetTextI18n")
    override fun getPictureS(list: List<WaybillPictureBean>) {
        mRecycler_scroll_index_tv.text = "1/" + list.size
        mWaybillPictureAdapter?.appendData(list)

    }
}