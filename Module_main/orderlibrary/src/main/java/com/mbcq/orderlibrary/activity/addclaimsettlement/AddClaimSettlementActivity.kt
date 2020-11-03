package com.mbcq.orderlibrary.activity.addclaimsettlement


import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.Constant
import com.mbcq.commonlibrary.dialog.UpdatePhotosFragment
import com.mbcq.orderlibrary.R
import com.tbruyelle.rxpermissions.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.activity_add_claim_settlement.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-02 17:56:13 添加 理赔
 */

@Route(path = ARouterConstants.AddClaimSettlementActivity)
class AddClaimSettlementActivity : BaseMVPActivity<AddClaimSettlementContract.View, AddClaimSettlementPresenter>(), AddClaimSettlementContract.View {
    var mSafeMoney = ""
    lateinit var rxPermissions: RxPermissions

    override fun getLayoutId(): Int = R.layout.activity_add_claim_settlement

    override fun initExtra() {
        super.initExtra()
        rxPermissions = RxPermissions(this)

    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        clearInfo()

    }

    override fun onClick() {
        super.onClick()
        update_img_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getCameraPermission()
            }

        })

        search_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (billno_ed.text.toString().isBlank()) {
                    showToast("请检查运单号后重试")
                    return
                }
                mPresenter?.getOrderInfo(billno_ed.text.toString())
            }

        })
        add_claim_settlement_toolbar.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
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

    @SuppressLint("SetTextI18n")
    override fun getOrderInfoS(data: JSONObject) {
        order_info_cl.visibility = View.VISIBLE
        mSafeMoney = data.optString("safeMoney")// 保价金额
        waybill_number_tv.text = data.optString("billno")
        serial_number_tv.text = data.optString("goodsNum")
        shipper_outlets_tv.text = data.optString("webidCodeStr")
        receiver_outlets_tv.text = data.optString("ewebidCodeStr")
        receiver_tv.text = "收货人:${data.optString("consignee")}        ${data.optString("consigneeMb")}"
        goods_info_tv.text = "${data.optString("product")} ${data.optString("qty")} ${data.optString("packages")}"
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
                    Matisse.from(this@AddClaimSettlementActivity)
                            .choose(MimeType.ofImage(), false) // 选择 mime 的类型
                            .countable(true)
                            .capture(true) //使用拍照功能
                            .captureStrategy(CaptureStrategy(true, Constant.TAKE_PHOTOS_FILE_PROVIDER))//是否拍照功能，并设置拍照后图片的保存路径
                            .maxSelectable(5) // 图片选择的最多数量
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

    override fun getOrderInfoNull() {
        clearInfo()
        TalkSureDialog(mContext, getScreenWidth(), "未查询到运单，请确认输入运单号是否有误！").show()

    }

}