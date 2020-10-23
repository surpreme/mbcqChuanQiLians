package com.mbcq.orderlibrary.activity.acceptbilling


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import android.text.TextPaint
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.commonlibrary.*
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.greendao.DaoSession
import com.mbcq.commonlibrary.greendao.WebAreaDbInfoDao
import com.mbcq.orderlibrary.R
import com.tbruyelle.rxpermissions.RxPermissions
import kotlinx.android.synthetic.main.activity_accept_billing.*
import zpSDK.zpSDK.zpBluetoothPrinter
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*


/**
 * @author: lzy
 * @time: 2018.08.25
 */

abstract class BaseAcceptBillingActivity<V : BaseView, T : BasePresenterImpl<V>> : CommonPrintMVPActivity<V, T>(), BaseView {

    /**
     * 到达网点
     */
    var endWebIdCode = ""
    var endWebIdCodeStr = ""

    /**
     * 到达公司的id
     * 这里从网点列表中获取
     */
    var eCompanyId = ""

    /**
     * 目的地
     */
    var destinationt = ""

    var mAccType = ""//付款方式编码

    var mAccTypeStr = ""//付款方式


    var mTransneed = ""//运输类型编码
    var mTransneedStr = ""//运输类型


    /**
     * 发货人信息
     */
    var mShipperId = ""//发货客户编号
    var mShipperMb = ""//发货人手机号
    var mShipperTel = ""//发货人固定电话
    var mShipper = ""//发货人
    var mShipperCid = ""//发货人身份证号
    var mShipperAddr = ""//发货人地址

    /**
     * 收货人信息
     */
    var mConsigneeMb = ""//收货人手机号
    var mConsigneeTel = ""//收货人固定电话
    var mConsignee = ""//收货人
    var mConsigneeAddr = ""//收货人地址

    lateinit var rxPermissions: RxPermissions
    protected val RESULT_DATA_CODE = 5848
    protected val RECEIVER_RESULT_DATA_CODE = 4439

    //运单号生成方式
    protected lateinit var waybillNumberTag: String

    //付货方式
    protected lateinit var okProcessStrTag: String
    var okProcessStrTagIndex = 1
    protected var isTalkGoodsStrTag: Boolean = false
    override fun isShowErrorDialog() = true

    override fun initExtra() {
        super.initExtra()
        rxPermissions = RxPermissions(this)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        initToolbar()
//        initTransportMethodLayout()
//        initPayWayLayout()
        waybillNumber(false)
        initReceivingMethod(1)
        initDeliveryMethod(1)
        labelcheck.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                enableBlueTooth()
            }
        }
        waybillcheck.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                enableBlueTooth()
            }
        }
    }

    /**
     * 获取条码号
     *
     * @param billno
     * @param index
     * @return
     */
     fun GetBarcode(billno: String?, index: Int): String {
        return String.format(Locale.getDefault(), "%s%04d", billno, index)
    }

    /**
     * 创运物流XT423标签打印
     *
     * @param context 上下文结构对象
     * @param kz      快找
     * @param num     当前打印的第几份
     * @param zpSDK1  sdk
     */
    fun print_LabelTemplated_XT423( kz: PrintBlueToothBean, num: Int, zpSDK1: zpBluetoothPrinter) {
        val mSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val font_bold = Typeface.create(Typeface.SERIF, Typeface.BOLD)
        val font = Typeface.SERIF
        val date: Date?
        date = try {
            mSimpleDateFormat.parse(kz.billDate)
        } catch (e: Exception) {
            Date()
        }
        zpSDK1.pageSetup(578, 350)
        val p = TextPaint()
        if (kz.transneed == "马帮快线") {
            zpSDK1.drawText(443, 10, "M", 6, 0, 1, true, false)
        }
        if (kz.transneed == "叮当小票") {
            zpSDK1.drawText(443, 10, "D", 6, 0, 1, true, false)
        }
        zpSDK1.drawText(20, 280, SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date), 3, 0, 0, false, false)
        zpSDK1.drawText(55, 305, SimpleDateFormat("HH:mm", Locale.getDefault()).format(date), 3, 0, 0, false, false)
        zpSDK1.drawText(150, 125, "到", 2, 0, 0, false, false)
        zpSDK1.drawText(5, 110, kz.webidCodeStr, 3, 0, 0, false, false)

        p.typeface = font_bold
        p.textSize = 40f
        // 计算居中显示的位置
        var w = p.measureText(kz.ewebidCodeStr).toInt() // 193 374
        w = (560 - 180 - w) / 2
        if (w > 0) zpSDK1.drawText(180 + w, 100, kz.ewebidCodeStr, 4, 0, 1, false, false) else zpSDK1.drawText(180, 100, kz.ewebidCodeStr, 4, 0, 1, false, false)

        zpSDK1.drawText(150, 10, "单号:" + kz.billno, 3, 0, 0, false, false)
        zpSDK1.drawText(150, 60, "货号:" + kz.goodsNum, 3, 0, 0, false, false)
        p.textSize = 35f
        zpSDK1.drawText(5, 150, kz.getConsignee(), 4, 0, 1, false, false)
        p.typeface = font
        p.textSize = 30f
        zpSDK1.drawText(400, 160, "件数:" + num + "/" + kz.qty, 3, 0, 0, false, false)
        zpSDK1.drawText(220, 160, "包装:" + kz.packages, 3, 0, 0, false, false)
        zpSDK1.drawText(6, 220, kz.transneed, 4, 0, 1, false, false)
        p.typeface = font
        p.textSize = 28f
        val unitstr: String = GetBarcode(kz.billno, num)
        try {
            // logo
            val bmp = BitmapFactory.decodeResource(resources, R.drawable.print_chuangyun_logo)
            val matrix = Matrix()
            // 缩放原图
            val inSampleSize = 100.toFloat() / bmp.width.toFloat()
            matrix.postScale(inSampleSize, inSampleSize)
            val bitmap2 = Bitmap.createBitmap(bmp, 0, 0, bmp.width, bmp.height, matrix, true)
            // login
            zpSDK1.drawGraphic(19, 4, bitmap2.width, bitmap2.height, bitmap2)
            zpSDK1.drawBarCode(200, 211, unitstr, 1, false, 2, 80) // 绘制条码
            // 底部数据条码内容
            zpSDK1.drawText(230, 300, unitstr, 3, 0, 0, false, false)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        try {
            zpSDK1.print(0, 1)
            /**
             *
             */
            zpSDK1.printerStatus()
            showPrintEx(zpSDK1.GetStatus())
            zpSDK1.disconnect()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @SuppressLint("ResourceType")
    protected fun printOrder(result: String) {
        /**
         *
         */
        if (!isCanPrint())
            return


        val zpSDK = getZpBluetoothPrinter()

        /**
         *
         */
        val res: Resources = resources
        val `is`: InputStream = res.openRawResource(R.drawable.app_logo)
        val bmpDraw = BitmapDrawable(`is`)
        val bmp: Bitmap = bmpDraw.bitmap


        zpSDK.pageSetup(568, 568)
        zpSDK.drawBarCode(8, 540, "12345678901234567", 128, true, 3, 60)
        zpSDK.drawGraphic(90, 48, 0, 0, bmp)
        zpSDK.drawQrCode(350, 48, "111111111", 0, 3, 0)
        zpSDK.drawText(90, 48 + 100, "400-8800-", 3, 0, 0, false, false)
        zpSDK.drawText(100, 48 + 100 + 56, "株洲      张贺", 4, 0, 0, false, false)
        zpSDK.drawText(250, 48 + 100 + 56 + 56, "经由  株洲", 2, 0, 0, false, false)

        zpSDK.drawText(100, 48 + 100 + 56 + 56 + 80, "2015110101079-01-01   广州", 3, 0, 0, false, false)
        zpSDK.drawText(100, 48 + 100 + 56 + 56 + 80 + 80, "2015-11-01  23:00    卡班", 3, 0, 0, false, false)

        zpSDK.drawBarCode(124, 48 + 100 + 56 + 56 + 80 + 80 + 80, "12345678901234567", 128, false, 3, 60)
        zpSDK.print(0, 0)

        /**
         *
         */
        zpSDK.printerStatus()
        showPrintEx(zpSDK.GetStatus())
        zpSDK.disconnect()
    }


    interface WebDbInterface {
        fun isNull()
        fun isSuccess(list: MutableList<WebAreaDbInfo>)

    }

    /**
     * 得到greenDao数据库中的网点
     * 可视化 stetho 度娘
     */
    protected fun getDbWebId(mWebDbInterface: WebDbInterface) {
        val daoSession: DaoSession = (application as CommonApplication).daoSession
        val userInfoDao: WebAreaDbInfoDao = daoSession.webAreaDbInfoDao
        val dbDatas = userInfoDao.queryBuilder().list()
        if (dbDatas.isNullOrEmpty()) {
            mWebDbInterface.isNull()
        } else {
            mWebDbInterface.isSuccess(dbDatas)
        }
    }

    private fun initToolbar() {
        accept_billing_toolbar.setBackButtonOnClickListener {
            onBackPressed()
        }
    }

    protected fun isCanSaveAcctBilling(): Boolean {
        if (endWebIdCode.isEmpty()) {
            showToast("请选择到达网点")
            return false
        }
        if (destinationt.isEmpty()) {
            showToast("请选择目的地")
            return false
        }
        if (mShipperMb.isEmpty()) {
            showToast("请选择发货人")
            return false
        }
        if (mConsigneeMb.isEmpty()) {
            showToast("请选择收货人")
            return false
        }
        if (cargo_name_ed.text.toString().isEmpty()) {
            showToast("请选择货物名称")
            return false
        }
        if (package_name_ed.text.toString().isEmpty()) {
            showToast("请选择包装")
            return false
        }
        if (weight_name_ed.text.toString().isEmpty()) {
            showToast("请输入重量")
            return false
        }
        if (volume_name_tv.text.toString().isEmpty()) {
            showToast("请输入体积")
            return false
        }
        if (receipt_requirements_name_tv.text.toString().isEmpty()) {
            showToast("请选择回单要求")
            return false
        }
        return true
    }

    fun waybillNumber(isHandWriting: Boolean) {
        if (isHandWriting) {
            waybill_number_ed.isFocusableInTouchMode = true
            waybill_number_ed.isFocusable = true
            waybill_number_ed.requestFocus()
            waybillNumberTag = "手写"
            waybill_number_handwriting_tv.setBackgroundResource(R.drawable.round_blue_lefttop_leftbottom)
            waybill_number_machine_printed_tv.setBackgroundResource(R.drawable.round_white_righttop_rightbottom)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                waybill_number_handwriting_tv.setTextColor(getColor(R.color.white))
                waybill_number_machine_printed_tv.setTextColor(getColor(R.color.black))
            } else {
                waybill_number_handwriting_tv.setTextColor(resources.getColor(R.color.white))
                waybill_number_machine_printed_tv.setTextColor(resources.getColor(R.color.black))

            }
        } else {
            waybill_number_ed.isFocusable = false
            waybill_number_ed.isFocusableInTouchMode = false

            hideKeyboard(waybill_number_ed)
            waybillNumberTag = "机打"

            waybill_number_handwriting_tv.setBackgroundResource(R.drawable.round_white_lefttop_leftbottom)
            waybill_number_machine_printed_tv.setBackgroundResource(R.drawable.round_blue_righttop_rightbottom)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                waybill_number_handwriting_tv.setTextColor(getColor(R.color.black))
                waybill_number_machine_printed_tv.setTextColor(resources.getColor(R.color.white))

            } else {
                waybill_number_handwriting_tv.setTextColor(resources.getColor(R.color.black))
                waybill_number_machine_printed_tv.setTextColor(resources.getColor(R.color.white))

            }

        }

    }

    /**
     * 收货方式
     * @1 客户自提
     * @2 上门提货
     */
    fun initReceivingMethod(type: Int) {
        when (type) {
            1 -> {
                customer_mention_tv.setBackgroundResource(R.drawable.round_blue)
                customer_mention_tv.setTextColor(Color.WHITE)

                home_delivery_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                home_delivery_tv.setTextColor(Color.BLACK)
                isTalkGoodsStrTag = false
            }
            2 -> {
                home_delivery_tv.setBackgroundResource(R.drawable.round_blue)
                home_delivery_tv.setTextColor(Color.WHITE)

                customer_mention_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                customer_mention_tv.setTextColor(Color.BLACK)
                isTalkGoodsStrTag = true

            }
        }
    }

    /**
     * 付货方式
     *
     */
    fun initDeliveryMethod(type: Int) {
        okProcessStrTagIndex = type
        when (type) {
            1 -> {
                get_delivery_mention_tv.setBackgroundResource(R.drawable.round_blue)
                get_delivery_mention_tv.setTextColor(Color.WHITE)

                get_delivery_home_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                get_delivery_home_tv.setTextColor(Color.BLACK)

                get_driver_direct_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                get_driver_direct_tv.setTextColor(Color.BLACK)
                okProcessStrTag = get_delivery_mention_tv.text.toString()

            }
            2 -> {
                get_delivery_home_tv.setBackgroundResource(R.drawable.round_blue)
                get_delivery_home_tv.setTextColor(Color.WHITE)

                get_delivery_mention_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                get_delivery_mention_tv.setTextColor(Color.BLACK)

                get_driver_direct_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                get_driver_direct_tv.setTextColor(Color.BLACK)
                okProcessStrTag = get_delivery_home_tv.text.toString()

            }
            3 -> {
                get_driver_direct_tv.setBackgroundResource(R.drawable.round_blue)
                get_driver_direct_tv.setTextColor(Color.WHITE)

                get_delivery_mention_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                get_delivery_mention_tv.setTextColor(Color.BLACK)

                get_delivery_home_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                get_delivery_home_tv.setTextColor(Color.BLACK)
                okProcessStrTag = get_driver_direct_tv.text.toString()

            }
        }

    }

    /**
     * 静态数据 因为需要动态 仅供参考
     * 运输方式
     * @1
     */
    private fun initTransportMethodLayout() {
        for (index in 0..3) {
            when (index) {
                0 -> {
                    transport_method_rg.addView(RadioGroupUtil.addSelectItem(mContext, "零担", index))
                }
                1 -> {
                    transport_method_rg.addView(RadioGroupUtil.addSelectItem(mContext, "马帮快线", index))
                }
                2 -> {
                    transport_method_rg.addView(RadioGroupUtil.addSelectItem(mContext, "整车", index))

                }
                3 -> {
                    transport_method_rg.addView(RadioGroupUtil.addSelectItem(mContext, "叮当小票", index))

                }

            }

            transport_method_rg.check(0)
        }


    }


    /**
     * 静态数据 因为需要动态 仅供参考
     * 付款方式 @2
     */
    private fun initPayWayLayout() {
        for (index in 0..5) {
            when (index) {
                0 -> {
                    pay_way_title_rg.addView(RadioGroupUtil.addSelectItem(mContext, "现付", index))
                }
                1 -> {
                    pay_way_title_rg.addView(RadioGroupUtil.addSelectItem(mContext, "提付", index))
                }
                2 -> {
                    pay_way_title_rg.addView(RadioGroupUtil.addSelectItem(mContext, "回单付", index))
                }
                3 -> {
                    pay_way_title_rg.addView(RadioGroupUtil.addSelectItem(mContext, "月结", index))
                }
                4 -> {
                    pay_way_title_rg.addView(RadioGroupUtil.addSelectItem(mContext, "货款扣", index))

                }
                5 -> {
                    pay_way_title_rg.addView(RadioGroupUtil.addSelectItem(mContext, "两笔付", index))

                }
            }


        }
        pay_way_title_rg.check(0)
    }

    fun getLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                    .subscribe { granted ->
                        if (granted) { // Always true pre-M
                            // I can control the camera now
                            ARouter.getInstance().build(ARouterConstants.LocationActivity).navigation()
                        } else {
                            // Oups permission denied
                        }
                    }
        } else {
            ARouter.getInstance().build(ARouterConstants.LocationActivity).navigation()

        }
    }
}