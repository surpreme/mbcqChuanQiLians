package com.mbcq.orderlibrary.activity.printacceptbilling

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextPaint
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.commonlibrary.CommonPrintMVPActivity
import com.mbcq.commonlibrary.MoneyChineseUtil
import com.mbcq.commonlibrary.PrintBlueToothBean
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_print_accept_billing.*
import org.json.JSONObject
import zpSDK.zpSDK.zpBluetoothPrinter
import java.text.SimpleDateFormat
import java.util.*

abstract class BaseBlueToothPrintAcceptBillingActivity<V : BaseView, T : BasePresenterImpl<V>> : CommonPrintMVPActivity<V, T>(), BaseView {
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        label_checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                enableBlueTooth()
            }
        }
        consignment_checkbox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                enableBlueTooth()
            }
        }
    }
    override fun setBlueToothConnectInterface(): BlueToothConnectInterface = object : BlueToothConnectInterface {
        override fun isUnUsed(reason: String) {
            label_checkbox.isChecked = false
            consignment_checkbox.isChecked = false

        }

        override fun overring() {

        }

    }
    /**
     * 获取条码号
     *
     * @param billno
     * @param index
     * @return
     */
    fun GetBarcode(billno: String, index: Int): String {
        return String.format(Locale.getDefault(), "%s%04d", billno, index)
    }

    /**
     * 远航物流托运单模板
     */
    protected fun print_YH_TYD_NEW1(kz: PrintAcceptBillingBean, isZhengzou: Boolean, showwebAddress: String, priceObj: JSONObject, zpSDK1: zpBluetoothPrinter) {
        val mSimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
        val font_bold = Typeface.create(Typeface.SERIF, Typeface.BOLD)
        val font = Typeface.SERIF
        zpSDK1.pageSetup(580, 1240)
        zpSDK1.drawLine(2, 10, 245, 576, 245, false)
        zpSDK1.drawLine(2, 10, 368, 574, 368, false)
        zpSDK1.drawLine(2, 10, 544, 574, 544, false)
        zpSDK1.drawLine(2, 10, 730, 574, 730, false)

        //---------------绘制:Text-----------
        var date: String = kz.billDate
        try {
            date = mSimpleDateFormat.format(mSimpleDateFormat.parse(date.replace('/', '-')))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        zpSDK1.drawText(152, 10, "创运物流", 5, 0, 0, false, false)
        zpSDK1.drawText(10, 150, "发货日期：$date", 2, 0, 0, false, false)
        zpSDK1.drawText(10, 183, "发货单号：" + kz.billno, 2, 0, 0, false, false)
        zpSDK1.drawText(302, 183, "货物编号：" + kz.goodsNum, 2, 0, 0, false, false)
        zpSDK1.drawText(10, 215, "卡号：" + kz.vipId, 2, 0, 0, false, false)
        zpSDK1.drawText(10, 252, "起    站：" + kz.webidCodeStr, 2, 0, 0, false, false)
        zpSDK1.drawText(302, 252, "到   站：" + kz.ewebidCodeStr, 2, 0, 0, false, false)
        zpSDK1.drawText(10, 282, "目 的 地：" + kz.destination, 2, 0, 0, false, false)
        zpSDK1.drawText(302, 282, "运输方式：" + kz.transneedStr, 2, 0, 0, false, false)
        zpSDK1.drawText(30, 310, "发 货 人：" + kz.shipper, 2, 0, 0, false, false)
        zpSDK1.drawText(302, 310, "电话：" + kz.shipperMb + "--"+ kz.shipperTel, 2, 0, 0, false, false)
        zpSDK1.drawText(10, 342, "收 货 人：" + kz.consignee, 2, 0, 0, false, false)
        zpSDK1.drawText(302, 342, "电话：" + kz.consigneeMb + "--"+kz.consigneeMb, 2, 0, 0, false, false)
        zpSDK1.drawText(10, 375, "货物名称：" + kz.product, 2, 0, 0, false, false)
        zpSDK1.drawText(302, 375, "包 装：" + kz.packages, 2, 0, 0, false, false)
        zpSDK1.drawText(10, 407, "数 量：" + kz.qty, 2, 0, 0, false, false)
        zpSDK1.drawText(302, 407, "送 货 费：" + priceObj.optString("accSend"), 2, 0, 0, false, false)
        zpSDK1.drawText(10, 438, "保 费：" + priceObj.optString("accSafe"), 2, 0, 0, false, false)

        //TODO
        zpSDK1.drawText(302, 470, "外转费：" +priceObj.optString("accWz") , 2, 0, 0, false, false)


        zpSDK1.drawText(10, 470, "工本费：" +priceObj.optString("accGb"), 2, 0, 0, false, false)
        zpSDK1.drawText(302, 438, "运 费：" + priceObj.optString("accTrans") + "元", 2, 0, 0, false, false)
        zpSDK1.drawText(10, 500, "备    注：" + kz.remark, 2, 0, 0, false, false)
        zpSDK1.drawText(302, 554, "提货方式：" + kz.okProcessStr, 2, 0, 0, false, false)
        zpSDK1.drawText(10, 554, "付款方式：" + kz.accTypeStr, 2, 0, 0, false, false)
        zpSDK1.drawText(10, 586, "运费合计：￥" + kz.accSum.toString() + " " + MoneyChineseUtil.toChinese(kz.accSum), 2, 0, 0, false, false)
        zpSDK1.drawText(10, 620, "代收货款：￥" + priceObj.optString("accDaiShou") + " " + MoneyChineseUtil.toChinese( priceObj.optString("accDaiShou") ), 2, 0, 0, false, false)
        zpSDK1.drawText(10, 648, "开 票 人：" + kz.createMan, 2, 0, 0, false, false)
        zpSDK1.drawText(10, 678, "开 户 名：" + kz.bankMan, 2, 0, 0, false, false)
        var strbankcode = ""
        if (kz.bankCode.length > 9) {
            strbankcode = kz.bankCode.substring(0, 5) + "****" + kz.bankCode.substring(kz.bankCode.length - 4)
        }
        zpSDK1.drawText(10, 708, "银行卡号：$strbankcode", 2, 0, 0, false, false)
        zpSDK1.drawText(10, 740, "查货电话：" + if (isZhengzou) "0371-56156112" else "0351-6980998/6986588", 2, 0, 0, false, false)
        if (isZhengzou) zpSDK1.drawText(295, 740, "理赔电话：0371-56156114", 2, 0, 0, false, false)
        zpSDK1.drawText(320, 780, kz.backQty, 5, 0, 0, false, false)
        zpSDK1.drawText(10, 770, "查款电话：" + if (isZhengzou) "0371-56156116" else "0351-6986622(自动)/5685834(人工)", 2, 0, 0, false, false)
        zpSDK1.drawText(10, 800, "收货点电话：" + "测试", 2, 0, 0, false, false)
        zpSDK1.drawText(10, 830, "到站电话：" + "测试", 2, 0, 0, false, false)
        zpSDK1.drawText(10, 862, "查货微信：yuanhang56", 2, 0, 0, false, false)
        zpSDK1.drawText(10, 893, "查货网址：http://www.mbcq56.com", 2, 0, 0, false, false)
        zpSDK1.drawText(10, 930, 540, 100, (if (isZhengzou) "郑州总部地址：" else "太原总部地址：") + showwebAddress, 2, 0, 0, false, false)
        zpSDK1.drawText(47, 1050, "开票员盖章方可生效", 3, 0, 0, false, false)
        zpSDK1.drawText(90, 1085, "微信扫码查询", 3, 0, 0, false, false)
        val content: String = "https://www.baidu.com/${kz.billno}"
        zpSDK1.drawBarCode(90, 62, kz.billno, 128, false, 3, 70)
        zpSDK1.drawQrCode(380, 1000, content, 0, 1, 0)
        zpSDK1.print(0, 0)
    }

    /**
     * 创运物流XT423标签打印
     *
     * @param context 上下文结构对象
     * @param kz      快找
     * @param num     当前打印的第几份
     * @param zpSDK1  sdk
     */
    protected fun print_LabelTemplated_XT423(kz: PrintAcceptBillingBean, num: Int, zpSDK1: zpBluetoothPrinter) {
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
        zpSDK1.drawText(5, 150, kz.consignee, 4, 0, 1, false, false)
        p.typeface = font
        p.textSize = 30f
        zpSDK1.drawText(400, 160, "件数:" + num + "/" + kz.qty, 3, 0, 0, false, false)
        zpSDK1.drawText(220, 160, "包装:" + kz.packages, 3, 0, 0, false, false)
        zpSDK1.drawText(6, 220, kz.transneedStr, 4, 0, 1, false, false)
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
            /* zpSDK1.printerStatus()
             showPrintEx(zpSDK1.GetStatus())
             zpSDK1.disconnect()*/
            closePrint(zpSDK1)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}