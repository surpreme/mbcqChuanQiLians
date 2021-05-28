package com.mbcq.orderlibrary.activity.goodsreceiptinfo


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
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.regular.IDNumberUtils
import com.mbcq.baselibrary.util.system.FileUtils
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.commonlibrary.Constant
import com.mbcq.commonlibrary.adapter.ImageViewAdapter
import com.mbcq.commonlibrary.adapter.ImageViewBean
import com.mbcq.commonlibrary.dialog.FilterDialog
import com.mbcq.commonlibrary.dialog.PaymentDialog
import com.mbcq.commonlibrary.dialog.UpdatePhotosFragment
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.fragment.goodsreceipt.GoodsReceiptBean
import com.tbruyelle.rxpermissions.RxPermissions
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MimeType
import com.zhihu.matisse.engine.impl.GlideEngine
import com.zhihu.matisse.internal.entity.CaptureStrategy
import kotlinx.android.synthetic.main.activity_goods_receipt_info.*
import org.json.JSONObject
import java.io.File

/**
 * @author: lzy
 * @time: 2020-09-27 14:40:05
 * 货物签收 详情页
 */
@Route(path = ARouterConstants.GoodsReceiptInfoActivity)
class GoodsReceiptInfoActivity : BaseMVPActivity<GoodsReceiptInfoContract.View, GoodsReceiptInfoPresenter>(), GoodsReceiptInfoContract.View {
    @Autowired(name = "GoodsReceiptBean")
    @JvmField
    var mLastDataJson: String = ""
    lateinit var rxPermissions: RxPermissions
    var mCertificateData = ""//证件类型制造的本地json
    var mSigningSituationData = ""//签收情况
    var pickerCertificateTypeTag = 1//提货人证件类型
    var agentCertificateTypeTag = 1//代理人证件类型
    var mSigningSituationTag = 1//签收情况
    var payMethodTag = 1
    var mImageViewAdapter: ImageViewAdapter? = null
    var mSelected: List<Uri> = ArrayList()
    val mShowImagesFile = mutableListOf<File>()
    val mShowImagesURL = mutableListOf<String>()

    override fun getLayoutId(): Int = R.layout.activity_goods_receipt_info
    override fun initExtra() {
        super.initExtra()
        ARouter.getInstance().inject(this)
        rxPermissions = RxPermissions(this)

    }

    fun getCanReceiptGoods(): Boolean {
        if (picker_name_ed.text.toString().isBlank()) {
            showToast("请输入提货人")
            return false
        }
        if (picker_card_number_ed.text.toString().isBlank()) {
            showToast("请输入提货人证件号")
            return false
        }
        if (picker_certificate_type_tv.text.toString() == "身份证" && !picker_card_number_ed.text.toString().isBlank()) {
            if (!IDNumberUtils.isIDNumber(picker_card_number_ed.text.toString())) {
                showToast("请检查提货人证件号")
                return false
            }

        }
        if (agent_name_checkbox.isChecked) {
            if (agent_name_ed.text.toString().isBlank()) {
                showToast("请输入代理人")
                return false
            }
            if (agent_card_number_ed.text.toString().isBlank()) {
                showToast("请输入代理人证件号")
                return false
            }
            if (agent_certificate_type_tv.text.toString() == "身份证" && !agent_card_number_ed.text.toString().isBlank()) {
                if (!IDNumberUtils.isIDNumber(agent_card_number_ed.text.toString())) {
                    showToast("请检查代理人证件号")
                    return false
                }

            }
        }

        return true
    }

    fun receiptGoods() {
        val mGoodsReceiptBean = Gson().fromJson<GoodsReceiptBean>(mLastDataJson, GoodsReceiptBean::class.java)

        val obj = JSONObject()
        val CommonStr = mGoodsReceiptBean.billno//运单号
        obj.put("CommonStr", CommonStr)
        val Billno = mGoodsReceiptBean.billno//运单号
        obj.put("Billno", Billno)

        val FetComId = 0//签收公司
        obj.put("FetComId", FetComId)

        val FetchWebidCode = UserInformationUtil.getWebIdCode(mContext)//签收网点编码
        obj.put("FetchWebidCode", FetchWebidCode)

        val FetchWebidCodeStr = UserInformationUtil.getWebIdCodeStr(mContext)//签收网点
        obj.put("FetchWebidCodeStr", FetchWebidCodeStr)

        val FetchDate = delivery_date_tv.text.toString()//签收日期
        obj.put("FetchDate", FetchDate)

        val FetchMan = picker_name_ed.text.toString()///签收人
        obj.put("FetchMan", FetchMan)

        val FetManIdCarType = pickerCertificateTypeTag//签收人证件类型编码
        obj.put("FetManIdCarType", FetManIdCarType)

        val FetManIdCarTypeStr = picker_certificate_type_tv.text.toString()//签收人证件
        obj.put("FetManIdCarTypeStr", FetManIdCarTypeStr)

        val FetchIdCard = picker_card_number_ed.text.toString()//签收人证件号
        obj.put("FetchIdCard", FetchIdCard)

        val FetchAgent = agent_name_ed.text.toString()//代理人
        obj.put("FetchAgent", FetchAgent)

        val FetAgeIdCarType = agentCertificateTypeTag//代理人证件类型编码
        obj.put("FetAgeIdCarType", FetAgeIdCarType)

        val FetAgeIdCarTypeStr = agent_certificate_type_tv.text.toString()//代理人证件类型
        obj.put("FetAgeIdCarTypeStr", FetAgeIdCarTypeStr)

        val FetAgeIdCard = agent_card_number_ed.text.toString()//代理人证件号
        obj.put("FetAgeIdCard", FetAgeIdCard)

        val ReceiptSigned = if (signed_receipt_checked.isChecked) "1" else "0"//回单已签收
        obj.put("ReceiptSigned", ReceiptSigned)

        val MoneyReceived = "1"//代收货款已收
        obj.put("MoneyReceived", MoneyReceived)
        /**
         * 1代理 2提货 3送货 4外转
         */
        val FetchType = 2//签收类型编码
        obj.put("FetchType", FetchType)

        val FetchTypeStr = "提货"//签收类型
        obj.put("FetchTypeStr", FetchTypeStr)

        val PayType = payMethodTag//支付方式编码
        obj.put("PayType", PayType)

        val PayTypeStr = payment_method_tv.text.toString()//支付方式
        obj.put("PayTypeStr", PayTypeStr)

        val FetchCon = mSigningSituationTag//签收情况编码
        obj.put("FetchCon", FetchCon)

        val FetchConStr = signing_situation_ed.text.toString()//签收情况
        obj.put("FetchConStr", FetchConStr)

        val FetchRemark = ""//签收备注
        obj.put("FetchRemark", FetchRemark)

        val RecordDate = TimeUtils.getCurrTime2()//记录日期
        obj.put("RecordDate", RecordDate)

        val FromType = Constant.ANDROID////签收位置编码
        obj.put("FromType", FromType)

        val FromTypeStr = Constant.ANDROID_STR////签收位置
        obj.put("FromTypeStr", FromTypeStr)
        mPresenter?.receiptGoods(obj)


    }

    /**
     * TODO
     * {"accArrived":0.0,"accAz":0.0,"accBack":0.0,"accCb":0.0,"accDaiShou":0.0,"accFetch":0.0,"accFj":0.0,"accGb":0.0,"accHKChange":0.0,"accHuiKou":0.0,"accHuoKuanKou":0.0,"accJc":0.0,"accMonth":0.0,"accNow":0.0,"accPackage":0.0,"accRyf":0.0,"accSafe":0.0,"accSend":0.0,"accSl":0.0,"accSms":0.0,"accSum":567.0,"accTrans":567.0,"accType":1,"accTypeStr":"现付","accWz":0.0,"accZx":0.0,"accZz":0.0,"backQty":"签回单","backState":0,"bankCode":"","bankMan":"","bankName":"","bankNumber":"","billDate":"2020-06-28T17:12:12","billState":4,"billStateStr":"到达","billType":0,"billTypeStr":"机打","billno":"10010000046","companyId":2001,"consignee":"1禾","consigneeAddr":"蜚厘士别三日奔奔夺","consigneeMb":"17530957256","consigneeTel":"0248-5235544","createMan":"","destination":"汕头","eCompanyId":0,"ewebidCode":1003,"ewebidCodeStr":"汕头","fromType":0,"goodsNum":"00046-12","hkChangeReason":"","id":81,"isTalkGoods":0,"isTalkGoodsStr":"否","isUrgent":0,"isUrgentStr":"否","isWaitNotice":0,"isWaitNoticeStr":"否","oBillno":"","okProcess":1,"okProcessStr":"自提","opeMan":"义乌后湖","orderId":"","packages":"aaas","product":"玻璃","qty":12,"qtyPrice":0.0,"remark":"","safeMoney":0.0,"salesMan":"","shipper":"王哓我","shipperAddr":"发货人地址","shipperCid":"410482199002265912","shipperId":"","shipperMb":"17530957256","shipperTel":"0123-1234567","sxf":0.0,"totalQty":12,"transneed":1,"transneedStr":"零担","vPrice":0.0,"vipId":"","volumn":0.0,"wPrice":0.0,"webidCode":1001,"webidCodeStr":"义乌后湖","weight":123.0,"weightJs":123.0}
     */
    @SuppressLint("SetTextI18n")
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        val mGoodsReceiptBean = Gson().fromJson<GoodsReceiptBean>(mLastDataJson, GoodsReceiptBean::class.java)
        waybill_number_tv.text = mGoodsReceiptBean.billno
        waybill_time_tv.text = mGoodsReceiptBean.billDate
        shipper_outlets_tv.text = mGoodsReceiptBean.webidCodeStr
        receiver_outlets_tv.text = mGoodsReceiptBean.ewebidCodeStr
        goods_info_tv.text = "${mGoodsReceiptBean.product}  ${mGoodsReceiptBean.totalQty}  ${mGoodsReceiptBean.packages}"
        receiver_tv.text = "收货人 :${mGoodsReceiptBean.consignee}      手机号：${mGoodsReceiptBean.consigneeMb}"

        withdraw_tv.text = mGoodsReceiptBean.accArrived.toString()
        reason_difference_tv.text = mGoodsReceiptBean.hkChangeReason.toString()
        original_order_payment.text = mGoodsReceiptBean.accDaiShou.toString()//
        change_payment_tv.text = getBeanString(mGoodsReceiptBean.accHKChange)
        actual_payment_tv.text = mGoodsReceiptBean.accDaiShou.toString()//
        underpayment_tv.text = mGoodsReceiptBean.accDaiShou.toString()
        increase_tv.text = "xxx"
        total_price_tv.text = getBeanString(mGoodsReceiptBean.accSum) + "元"
        destination_tv.text = "目的地：${getBeanString(mGoodsReceiptBean.destination)}"
        delivery_date_tv.text = TimeUtils.getCurrTime2()
        initImageShowGridRecycler()
    }

    override fun initDatas() {
        super.initDatas()
        val jsonArray = JsonArray()
        for (index in 0 until 4) {
            val obj = JsonObject()
            obj.addProperty("tag", index)
            when (index) {
                0 -> {
                    obj.addProperty("title", "身份证")
                }
                1 -> {
                    obj.addProperty("title", "港澳通行证")
                }
                2 -> {
                    obj.addProperty("title", "军官证")
                }
                3 -> {
                    obj.addProperty("title", "其他证件")
                }

            }
            jsonArray.add(obj)

        }
        mCertificateData = Gson().toJson(jsonArray)


        val mSigningSituationJsonArray = JsonArray()
        val mSigningSituationObj = JsonObject()
        mSigningSituationObj.addProperty("tag", 1)
        mSigningSituationObj.addProperty("title", "正常")
        mSigningSituationJsonArray.add(mSigningSituationObj)
        mSigningSituationData = Gson().toJson(mSigningSituationJsonArray)

    }

    override fun onClick() {
        super.onClick()
        signature_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.SignatureActivity).navigation()
            }

        })
        take_photos_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                getCameraPermission()
            }

        })
        confirm_receipt_btn.setOnClickListener(object : SingleClick(2000) {
            override fun onSingleClick(v: View?) {
                if (getCanReceiptGoods()) {
                    PaymentDialog(object : PaymentDialog.OnSelectPaymentMethodInterface {
                        override fun onResult(v: View, result: Int) {
                            if (result == 1) {
                                ARouter.getInstance().build(ARouterConstants.PayBarActivity).navigation()
                            } else if (result == 3) {
                                receiptGoods()
                            }
                        }

                    }).show(supportFragmentManager, "confirm_receiptGoodsReceiptInfoActivityPaymentDialog")
                }

            }

        })

        payment_method_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mPresenter?.getPaymentWay()
            }

        })
        picker_certificate_type_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                FilterDialog(getScreenWidth(), mCertificateData, "title", "提货人证件类型", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                    override fun onItemClick(v: View, position: Int, mResult: String) {
                        val obj = JSONObject(mResult)
                        picker_certificate_type_tv.text = obj.optString("title")
                        pickerCertificateTypeTag = obj.optInt("tag") + 1
                    }

                }).show(supportFragmentManager, "picker_certificate_type_tvSFilterDialog")
            }

        })
        signing_situation_down_iv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                FilterDialog(getScreenWidth(), mSigningSituationData, "title", "签收情况类型", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                    override fun onItemClick(v: View, position: Int, mResult: String) {
                        val obj = JSONObject(mResult)
                        signing_situation_ed.setText(obj.optString("title"))
                        mSigningSituationTag = obj.optInt("tag")
                    }

                }).show(supportFragmentManager, "signing_situation_down_ivSFilterDialog")
            }

        })
        agent_certificate_type_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                FilterDialog(getScreenWidth(), mCertificateData, "title", "代理人证件类型", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
                    override fun onItemClick(v: View, position: Int, mResult: String) {
                        val obj = JSONObject(mResult)
                        agent_certificate_type_tv.text = obj.optString("title")
                        agentCertificateTypeTag = obj.optInt("tag") + 1
                    }

                }).show(supportFragmentManager, "agent_certificate_type_llSFilterDialog")
            }

        })
        goods_receipt_info_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    fun takePhotoes() {
        if (mShowImagesURL.size > 9) {
            showToast("最多上传9张图片")
            return
        }
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
                    Matisse.from(this@GoodsReceiptInfoActivity)
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
        mUpdatePhotosFragment.show(supportFragmentManager, "GoodsReceiptInfoActivityUpdatePhotosFragment")


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        var resultFile: File
        if (requestCode == Constant.CHOOSE_PHOTOS_REQUEST_CODE && resultCode == RESULT_OK) {
            mSelected = Matisse.obtainResult(data)
//            Glide.with(mContext).load(mSelected[0]).into(result_image)
            //************
            val itemBean = ImageViewBean()
            itemBean.imageUris = mSelected[0]
            itemBean.tag = ""
            mImageViewAdapter?.appendData(mutableListOf(itemBean))
            if (!isDestroyed) {
                val params = HttpParams()
                resultFile = File(FileUtils.getRealFilePath(mContext, mSelected[0]))
                mShowImagesFile.add(resultFile)
                params.put(System.currentTimeMillis().toString(), resultFile)
                mPresenter?.postImg(params)
            }

        } else if (requestCode == Constant.TAKE_PHOTOS_REQUEST_CODE && resultCode == RESULT_OK) {
            data?.let {
                val imageBitmap = it.extras?.get("data") as Bitmap
//                Glide.with(mContext).load(imageBitmap).into(result_image)
                val uri = Uri.parse(MediaStore.Images.Media.insertImage(contentResolver, imageBitmap, null, null))
                //************
                val itemBean = ImageViewBean()
                itemBean.imageUris = uri
                itemBean.tag = ""
                mImageViewAdapter?.appendData(mutableListOf(itemBean))
                if (!isDestroyed) {
                    val params = HttpParams()
                    resultFile = FileUtils.getFile(imageBitmap, "mbcq")
                    mShowImagesFile.add(resultFile)
                    params.put(System.currentTimeMillis().toString(), resultFile)
                    mPresenter?.postImg(params)
                }

            }

        }
    }

    fun initImageShowGridRecycler() {
        update_goods_recycler.layoutManager = GridLayoutManager(mContext, 3)
        mImageViewAdapter = ImageViewAdapter(mContext)
        update_goods_recycler.adapter = mImageViewAdapter
        mImageViewAdapter?.mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                mImageViewAdapter?.getAllData()?.let {
                    ImagePreview
                            .getInstance()
                            .setContext(this@GoodsReceiptInfoActivity)
                            .setShowDownButton(false)
                            .setIndex(position)
                            .setImageList(mShowImagesURL)
                            .start()

                }

            }

        }
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

    override fun getPaymentWayS(result: String) {
        FilterDialog(getScreenWidth(), result, "tdescribe", "提货收款方式", false, isShowOutSide = true, mClickInterface = object : OnClickInterface.OnRecyclerClickInterface {
            override fun onItemClick(v: View, position: Int, mResult: String) {
                val obj = JSONObject(mResult)
                payment_method_tv.text = obj.optString("tdescribe")
                payMethodTag = obj.optInt("typecode")
            }

        }).show(supportFragmentManager, "getPaymentWaySFilterDialog")
    }

    override fun receiptGoodsS() {
        TalkSureDialog(mContext, getScreenWidth(), "货物签收成功，点击返回查看详情！") {
            onBackPressed()
        }.show()
    }

    override fun postImgS(url: String) {
        mShowImagesURL.add(ApiInterface.BASE_URIS + url)

    }
}