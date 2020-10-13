package com.mbcq.commonlibrary.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.mbcq.commonlibrary.R

/**
 * @Auther: valy
 * @datetime: 2020-02-28
 * @desc:
 */
class UpdatePhotosFragment : DialogFragment() {
    var mlistener: OnThingClickInterface? = null
    var take_photos_tv: TextView? = null
    var photo_card_tv: TextView? = null
    var cancel_tv: TextView? = null

    interface OnThingClickInterface {
        fun getThing(msg: Any)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog!!.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val mBaseView = inflater.inflate(R.layout.dialog_update_usericon_window, container, false)
        cancel_tv = mBaseView.findViewById(R.id.cancel_tv)
        photo_card_tv = mBaseView.findViewById(R.id.photo_card_tv)
        take_photos_tv = mBaseView.findViewById(R.id.take_photos_tv)
        initView()
        return mBaseView
    }

    private fun initView() {
        take_photos_tv?.setOnClickListener {
            if (mlistener != null) {
                mlistener?.getThing("1")
                dismiss()
            }
        }
        photo_card_tv?.setOnClickListener {
            if (mlistener != null) {
                mlistener?.getThing("2")
                dismiss()
            }
        }
        cancel_tv?.setOnClickListener {
            dismiss()
        }
    }

    /**
     * 设置位置在底部 而且设置背景为透明
     */
    override fun onStart() {
        super.onStart()
        dialog?.let {
            val window = it.window
            val params = window?.attributes
            params?.gravity = Gravity.BOTTOM
            params?.width = WindowManager.LayoutParams.MATCH_PARENT
//        params?.alpha=0.58f
            window?.attributes = params
            //        params.height = 700;
            //设置背景透明
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }




    }
}
