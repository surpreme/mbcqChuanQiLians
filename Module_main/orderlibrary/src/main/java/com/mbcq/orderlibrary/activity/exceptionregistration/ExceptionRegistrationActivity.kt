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
import cc.shinichi.library.ImagePreview
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.util.system.FileUtils
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.commonlibrary.Constant
import com.mbcq.commonlibrary.adapter.ImageViewBean
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.commonlibrary.dialog.UpdatePhotosFragment
import com.mbcq.orderlibrary.R
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
@Route(path = ARouterConstants.ExceptionRegistrationActivity)
class ExceptionRegistrationActivity : BaseExceptionRegistrationActivity<ExceptionRegistrationContract.View, ExceptionRegistrationPresenter>(), ExceptionRegistrationContract.View {
    override fun getLayoutId(): Int = R.layout.activity_exception_registration


    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        current_time_tv.text = TimeUtils.getCurrTime2()
        clearInfo()

        initImageShowGridRecycler()
    }

    override fun initImageShowGridRecycler() {
        super.initImageShowGridRecycler()
        mImageViewAdapter?.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                mImageViewAdapter?.getAllData()?.let {
                    ImagePreview
                            .getInstance()
                            .setContext(this@ExceptionRegistrationActivity)
                            .setShowDownButton(false)
                            .setIndex(position)
                            .setImageList(mShowImagesURL)
                            .start()

                }

            }

        }
    }
    override fun onClick() {
        super.onClick()
        take_photos_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (getIsCanTakePictures())
                    getCameraPermission()
            }

        })
        problem_son_type_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (problem_type_tv.text.isBlank()) {
                    showToast("请先选择差错类型后选择！")
                    return
                }
                val obj = JSONObject(mProblemTypeTag)
                mPresenter?.getWrongChildrenType(obj.optString("id"), obj.optString("companyid"), obj.optString("typecode"), mProblemTypeIndex.toString(), obj.optString("tdescribe"), obj.optString("opeman"), obj.optString("recorddate"))
            }

        })

        problem_type_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getWrongType()
            }

        })
        search_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (billno_ed.text.toString().isBlank()) {
                    TalkSureDialog(mContext, getScreenWidth(), "请输入运单号进行查询").show()
                    return
                }
                mPresenter?.getExceptionInfo(billno_ed.text.toString())
            }

        })
        exception_registration_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    fun updateOvering() {
        val obj = JSONObject()
        val Id = ""
        obj.put("Id", Id)
        val Billno = ""
        obj.put("Billno", Billno)
        val FindDate = ""
        obj.put("FindDate", FindDate)
        val FindMan = ""
        obj.put("FindMan", FindMan)
        val OpeMan = ""
        obj.put("OpeMan", OpeMan)
        val OpeWebidCode = ""
        obj.put("OpeWebidCode", OpeWebidCode)
        val OpeWebidCodeStr = ""
        obj.put("OpeWebidCodeStr", OpeWebidCodeStr)
        val OpeDate = ""
        obj.put("OpeDate", OpeDate)
        val BadType = ""
        obj.put("BadType", BadType)
        val BadTypeStr = ""
        obj.put("BadTypeStr", BadTypeStr)
        val BadChildType = ""
        obj.put("BadChildType", BadChildType)
        val BadChildTypeStr = ""
        obj.put("BadChildTypeStr", BadChildTypeStr)
        val BadInfo = ""
        obj.put("BadInfo", BadInfo)
        val LoseCount = ""
        obj.put("LoseCount", LoseCount)
        val BadCount = ""
        obj.put("BadCount", BadCount)

        val GoodSworth = ""
        obj.put("GoodSworth", GoodSworth)
        val ImageId = ""
        obj.put("ImageId", ImageId)
        val ConfirmMan = ""
        obj.put("ConfirmMan", ConfirmMan)
        val ConfirmWebCod = ""
        obj.put("ConfirmWebCod", ConfirmWebCod)
        val ConfirmWebcodStr = ""
        obj.put("ConfirmWebcodStr", ConfirmWebcodStr)
        val ConcomId = ""
        obj.put("ConcomId", ConcomId)
        val ConfirmDate = ""
        obj.put("ConfirmDate", ConfirmDate)
        val InOnevehicleflag = ""
        obj.put("InOnevehicleflag", InOnevehicleflag)
        val AllImagesLst = ""
        obj.put("AllImagesLst", AllImagesLst)

    }

    fun getCameraPermission() {
        rxPermissions.request(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { granted ->
                    if (granted) { // Always true pre-M
                        // I can control the camera now
                        takePhotoes()
                    } else {
                        // Oups permission denied
                        TalkSureDialog(mContext, getScreenWidth(), "权限未赋予！照相机无法启动！请联系在线客服或手动进入系统设置授予摄像头以及读取存储权限！").show()

                    }
                }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constant.CHOOSE_PHOTOS_REQUEST_CODE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data)
            Glide.with(mContext).load(mSelected[0]).into(result_image)
            //************
            val itemBean = ImageViewBean()
            itemBean.imageUris = mSelected[0]
            itemBean.tag = ""
            mImageViewAdapter?.appendData(mutableListOf(itemBean))
            if (!isDestroyed) {
                val params = HttpParams()
                params.put(System.currentTimeMillis().toString(), File(FileUtils.getPath(mContext, mSelected[0])))
                mPresenter?.postImg(params)
            }

        } else if (requestCode == Constant.TAKE_PHOTOS_REQUEST_CODE && resultCode == RESULT_OK) {
            data?.let {
                val imageBitmap = it.extras?.get("data") as Bitmap
                Glide.with(mContext).load(imageBitmap).into(result_image)
                val uri = Uri.parse(MediaStore.Images.Media.insertImage(contentResolver, imageBitmap, null, null))
                //************
                val itemBean = ImageViewBean()
                itemBean.imageUris = uri
                itemBean.tag = ""
                mImageViewAdapter?.appendData(mutableListOf(itemBean))
                if (!isDestroyed) {
                    val params = HttpParams()
                    params.put(System.currentTimeMillis().toString(), FileUtils.getFile(imageBitmap, "mbcq"))
                    mPresenter?.postImg(params)
                }

            }

        }
    }

    fun takePhotoes() {
        val mUpdatePhotosFragment = UpdatePhotosFragment()
        mUpdatePhotosFragment.mlistener = object : UpdatePhotosFragment.OnThingClickInterface {
            override fun getThing(msg: Any) {
                if (msg.toString() == "1") {
                    Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                        takePictureIntent.resolveActivity(packageManager)?.also {
                            startActivityForResult(takePictureIntent, Constant.TAKE_PHOTOS_REQUEST_CODE)
                        }
                    }
                } else if (msg.toString() == "2") {
//                    TODO()
                    Matisse.from(this@ExceptionRegistrationActivity)
                            .choose(MimeType.ofImage(), false) // 选择 mime 的类型
                            .countable(true)
                            .capture(true) //使用拍照功能
                            .captureStrategy(CaptureStrategy(true, Constant.TAKE_PHOTOS_FILE_PROVIDER))//是否拍照功能，并设置拍照后图片的保存路径
                            .maxSelectable(9) // 图片选择的最多数量
                            .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                            .thumbnailScale(0.85f) // 缩略图的比例
                            .imageEngine(GlideEngine()) // 使用的图片加载引擎
                            .theme(R.style.Matisse_Dracula)
                            .forResult(Constant.CHOOSE_PHOTOS_REQUEST_CODE) // 设置作为标记的请求码
                }
            }

        }
        mUpdatePhotosFragment.show(supportFragmentManager, "UpdatePhotosFragment")


    }





    @SuppressLint("SetTextI18n")
    override fun getExceptionInfoS(data: JSONObject) {
        order_info_cl.visibility = View.VISIBLE
        waybill_number_tv.text = data.optString("billno")
        serial_number_tv.text = data.optString("goodsNum")
        shipper_outlets_tv.text = data.optString("webidCodeStr")
        receiver_outlets_tv.text = data.optString("ewebidCodeStr")
        receiver_tv.text = "收货人:${data.optString("consignee")}        ${data.optString("consigneeMb")}"
        goods_info_tv.text = "${data.optString("product")} ${data.optString("qty")} ${data.optString("packages")}"
    }

    override fun getExceptionInfoNull() {
        clearInfo()
        TalkSureDialog(mContext, getScreenWidth(), "未查询到运单，请确认输入运单号是否有误！").show()


    }

    override fun postImgS(url: String) {
        mShowImagesURL.add(ApiInterface.BASE_URIS + url)
    }


    override fun getWrongTypeS(result: String) {
        FilterDialog(getScreenWidth(), result, "tdescribe", "选择差错类型", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<ExceptionRegistrationWrongTypeBean>(mResult, ExceptionRegistrationWrongTypeBean::class.java)
                problem_type_tv.text = mSelectData.tdescribe
                mProblemTypeTag = mResult
                mProblemTypeIndex = position + 1
                problem_son_type_tv.text = ""
            }

        }).show(supportFragmentManager, "getWrongTypeFilterDialog")
    }


    override fun getWrongChildrenTypeS(result: String) {
        FilterDialog(getScreenWidth(), result, "tdescribe", "选择子类差错类型", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val mSelectData = Gson().fromJson<ExceptionRegistrationWrongTypeBean>(mResult, ExceptionRegistrationWrongTypeBean::class.java)
                problem_son_type_tv.text = mSelectData.tdescribe
//                mProblemTypeTag = mResult
            }

        }).show(supportFragmentManager, "getWrongChildrenTypeSFilterDialog")
    }
}