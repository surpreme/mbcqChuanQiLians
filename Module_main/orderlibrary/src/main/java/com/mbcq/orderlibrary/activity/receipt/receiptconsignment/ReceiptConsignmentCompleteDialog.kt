package com.mbcq.orderlibrary.activity.receipt.receiptconsignment

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.bigkoo.pickerview.listener.OnOptionsSelectListener
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.bigkoo.pickerview.view.OptionsPickerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.dialog.popup.XDialog
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.WebDbUtil
import com.mbcq.commonlibrary.WebsDbInterface
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import com.mbcq.commonlibrary.dialog.FilterWithTimeDialog
import com.mbcq.commonlibrary.dialog.TimeDialogUtil
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.dialog_receipt_consignment_compelete.*
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.text.DateFormat
import java.text.SimpleDateFormat


/**
 * 完整
 */
class ReceiptConsignmentCompleteDialog(val mScreenWidth: Int) : BaseDialogFragment() {
    private var options1Items: List<AreaDataBean> = ArrayList()
    private val options2Items: ArrayList<ArrayList<String>> = ArrayList()
    private val options3Items: ArrayList<ArrayList<ArrayList<String>>> = ArrayList()
    var mLock = false
    override fun setDialogWidth(): Int = mScreenWidth / 10 * 9

    override fun initView(view: View, savedInstanceState: Bundle?) {
        initAreaData()
        city_name_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (mLock) {
                    val pvOptions = OptionsPickerBuilder(mContext) { options1, options2, options3, _ -> //返回的分别是三个级别的选中位置
                        val opt1tx = if (options1Items.isNotEmpty()) options1Items[options1].pickerViewText else ""
                        val opt2tx = if (options2Items.size > 0
                                && options2Items[options1].size > 0) options2Items[options1][options2] else ""
                        val opt3tx = if (options2Items.size > 0 && options3Items[options1].size > 0 && options3Items[options1][options2].size > 0) options3Items[options1][options2][options3] else ""
//                        val tx = opt1tx + opt2tx + opt3tx
                        val tx = opt1tx + opt2tx
                        city_name_tv.text = tx
                    }
                            .setTitleText("城市选择")
                            .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                            .setContentTextSize(20)
                            .isDialog(true) //是否显示为对话框样式
                            .build<AreaDataBean>()

                    pvOptions.setPicker(options1Items, options2Items as List<MutableList<AreaDataBean>>)
//                    pvOptions.setPicker(options1Items, options2Items as List<MutableList<AreaDataBean>>, options3Items as List<MutableList<MutableList<AreaDataBean>>>) //三级选择器

                    pvOptions.show()
                }
            }

        })
        send_time_ll.setOnClickListener(object : SingleClick() {
            @SuppressLint("SimpleDateFormat")
            override fun onSingleClick(v: View?) {
                TimeDialogUtil.getChoiceTimer(mContext, OnTimeSelectListener { date, _ ->
                    val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                    val format: String = mDateFormat.format(date)
                    send_time_tv.text = format

                }, "选择寄出时间", isStartCurrentTime = false, isEndCurrentTime = false, isYear = true, isHM = false, isDialog = true).show(send_time_ll)
            }

        })
        send_branch_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                activity?.let {
                    WebDbUtil.getDbWebId(it.application, object : WebsDbInterface {
                        override fun isNull() {

                        }

                        override fun isSuccess(list: MutableList<WebAreaDbInfo>) {
                            val mDatassss = mutableListOf<BaseTextAdapterBean>()
                            for (item in list) {
                                mDatassss.add(BaseTextAdapterBean(item.webid, item.webid))
                            }
                            XDialog.Builder(mContext)
                                    .setContentView(R.layout.dialog_receipt_consignment_compelete_bottom)
                                    .setWidth(ScreenSizeUtils.dip2px(mContext, 200f))
                                    .setIsDarkWindow(false)
                                    .asCustom(ReceiptConsignmentCompleteBottomDialog(mContext, mDatassss).also {
                                        it.mCclick = object : OnClickInterface.OnRecyclerClickInterface {
                                            override fun onItemClick(v: View, position: Int, mResult: String) {
                                                send_branch_tv.text = mResult
                                            }

                                        }
                                    })
                                    .show(send_branch_ll)
                        }

                    })
                }


            }

        })
        company_name_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                XDialog.Builder(mContext)
                        .setContentView(R.layout.dialog_receipt_consignment_compelete_bottom)
                        .setWidth(ScreenSizeUtils.dip2px(mContext, 200f))
                        .setIsDarkWindow(false)
                        .asCustom(ReceiptConsignmentCompleteBottomDialog(mContext, mutableListOf(BaseTextAdapterBean("创运物流", "创运物流"), BaseTextAdapterBean("创运物流", "创运物流"), BaseTextAdapterBean("创运物流", "创运物流"))).also {
                            it.mCclick = object : OnClickInterface.OnRecyclerClickInterface {
                                override fun onItemClick(v: View, position: Int, mResult: String) {
                                    company_name_tv.text = mResult

                                }

                            }
                        })
                        .show(company_name_ll)
            }

        })
        delivery_status_receipt_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                XDialog.Builder(mContext)
                        .setContentView(R.layout.dialog_receipt_consignment_compelete_bottom)
                        .setWidth(ScreenSizeUtils.dip2px(mContext, 200f))
                        .setIsDarkWindow(false)
                        .asCustom(ReceiptConsignmentCompleteBottomDialog(mContext, mutableListOf(BaseTextAdapterBean("正常", "正常"), BaseTextAdapterBean("异常", "异常"))).also {
                            it.mCclick = object : OnClickInterface.OnRecyclerClickInterface {
                                override fun onItemClick(v: View, position: Int, mResult: String) {
                                    delivery_status_receipt_tv.text = mResult

                                }

                            }
                        })
                        .show(delivery_status_receipt_ll)
            }

        })
        dismiss_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                dismiss()
            }

        })
    }

    fun initAreaData() {
        val mJsonData: String = getJson(mContext, "province.json")

        val jsonBean = Gson().fromJson<List<AreaDataBean>>(mJsonData, object : TypeToken<List<AreaDataBean>>() {}.type)
        options1Items = jsonBean
        for (i in jsonBean.indices) { //遍历省份
            val cityList: ArrayList<String> = ArrayList() //该省的城市列表（第二级）
            val province_AreaList: ArrayList<ArrayList<String>> = ArrayList() //该省的所有地区列表（第三极）
            for (c in jsonBean[i].cityList.indices) { //遍历该省份的所有城市
                val cityName = jsonBean[i].cityList[c].name
                cityList.add(cityName) //添加城市
                val city_AreaList: ArrayList<String> = ArrayList() //该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                /*if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    city_AreaList.add("");
                } else {
                    city_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }*/
                city_AreaList.addAll(jsonBean[i].cityList[c].area)
                province_AreaList.add(city_AreaList) //添加该省所有地区数据
            }
            /**
             * 添加城市数据
             */
            options2Items.add(cityList)
            /**
             * 添加地区数据
             */
            options3Items.add(province_AreaList)
        }
        mLock = true
    }

    fun getJson(context: Context, fileName: String): String {
        val stringBuilder = StringBuilder()
        try {
            val assetManager = context.assets
            val bf = BufferedReader(InputStreamReader(assetManager.open(fileName)))
            var line: String? = null
            while (bf.readLine()?.also { line = it } != null) {
                line?.let {
                    stringBuilder.append(line)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return stringBuilder.toString()
    }

    override fun setContentView(): Int = R.layout.dialog_receipt_consignment_compelete

}