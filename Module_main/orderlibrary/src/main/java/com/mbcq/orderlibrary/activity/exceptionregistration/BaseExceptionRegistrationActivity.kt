package com.mbcq.orderlibrary.activity.exceptionregistration


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import cc.shinichi.library.ImagePreview
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.baselibrary.util.system.FileUtils
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.commonlibrary.Constant
import com.mbcq.commonlibrary.adapter.ImageViewAdapter
import com.mbcq.commonlibrary.adapter.ImageViewBean
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.commonlibrary.dialog.UpdatePhotosFragment
import com.mbcq.orderlibrary.R
import com.tbruyelle.rxpermissions.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.activity_exception_registration.*
import org.json.JSONObject
import java.io.File


/**
 * @author: lzy
 * @time: 2020-09-28 17:40:00 异常登记
 */
abstract class BaseExceptionRegistrationActivity<V : BaseView, T : BasePresenterImpl<V>> : BaseMVPActivity<V, T>(), BaseView {
    lateinit var rxPermissions: RxPermissions
    protected var mSelected: List<Uri> = ArrayList()
    val mShowImagesURL = mutableListOf<String>()
    var mImageViewAdapter: ImageViewAdapter? = null
    var mProblemTypeTag = ""
    var mProblemTypeIndex = 0
    override fun initExtra() {
        super.initExtra()
        rxPermissions = RxPermissions(this)

    }


    protected open fun initImageShowGridRecycler() {
        result_image_recycler.layoutManager = GridLayoutManager(mContext, 3)
        mImageViewAdapter = ImageViewAdapter(mContext)
        result_image_recycler.adapter = mImageViewAdapter

    }

    fun clearInfo() {
        waybill_number_tv.text = ""
        serial_number_tv.text = ""
        shipper_outlets_tv.text = ""
        receiver_outlets_tv.text = ""
        receiver_tv.text = ""
        goods_info_tv.text = ""
        order_info_cl.visibility = View.GONE
    }


    fun getIsCanTakePictures(): Boolean {
        if (order_info_cl.visibility == View.GONE) {
            showToast("请输入运单号")
            return false

        }
        if (problem_man_ed.text.isBlank()) {
            showToast("请输入您的大名")
            return false

        }
        if (problem_type_tv.text.isBlank()) {
            showToast("请选择差错类型")
            return false

        }
        if (problem_son_type_tv.text.isBlank()) {
            showToast("请选择差错子类")
            return false

        }
        if (problem_info_tv.text.isBlank()) {
            showToast("请输入货损情况")
            return false

        }
        if (problem_less_ed.text.isBlank()) {
            showToast("请输入货差")
            return false

        }
        if (problem_bad_ed.text.isBlank()) {
            showToast("请输入货损")
            return false

        }

        return true
    }

}