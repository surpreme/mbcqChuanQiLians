package com.mbcq.orderlibrary.activity.acceptbilling.billingvolumecalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.util.log.LogUtils
import com.mbcq.baselibrary.util.system.EditFocusUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.dialog_billing_volume_calculator.*
import java.text.DecimalFormat

class BillingVolumeCalculatorDialog(val mScreenWidth: Int, var mOnResultInterface: OnResultInterface? = null) : BaseDialogFragment() {
    var mBillingVolumeCalculatorAdapter: BillingVolumeCalculatorAdapter? = null

    interface OnResultInterface {
        fun onResult(totalVolume: String)
    }

    override fun setDialogWidth(): Int {
        return mScreenWidth / 5 * 4
    }

    /**
     * 转换字符为小数后两位
     * 格式化，区小数后两位
     */
    fun haveTwoDouble(d: Double): String {
        val df = DecimalFormat("0.00")
        return try {
            df.format(d)
        } catch (e: Exception) {
            LogUtils.e(d)
            ""
        }
    }

    /**
     * 计算
     */
    fun calculationCalculation() {
        if (singleLong_ed.text.toString().isNotBlank() && singleWidth_ed.text.toString().isNotBlank() && singleHigh_ed.text.toString().isNotBlank() && number_ed.text.toString().isNotBlank())
            totalWeight_tv.text = haveTwoDouble(((singleLong_ed.text.toString()).toDouble() / 100) * ((singleWidth_ed.text.toString()).toDouble() / 100) * ((singleHigh_ed.text.toString()).toDouble() / 100) * (number_ed.text.toString()).toInt())

    }

    fun getIsCanSave(): Boolean {
        if (singleLong_ed.text.isBlank()) {
            return false
        }
        if (singleWidth_ed.text.isBlank()) {
            return false
        }
        if (singleHigh_ed.text.isBlank()) {
            return false
        }
        if (number_ed.text.isBlank()) {
            return false
        }
        return true
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        initAddGoodsRecycler(view)

        singleLong_ed.apply {
            afterTextChanged {
                calculationCalculation()
            }
        }
        singleWidth_ed.apply {
            afterTextChanged {
                calculationCalculation()
            }
        }
        singleHigh_ed.apply {
            afterTextChanged {
                calculationCalculation()
            }
        }
        number_ed.apply {
            afterTextChanged {
                calculationCalculation()
            }
        }
        save_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mOnResultInterface?.onResult(totalVolume_recycler_tv.text.toString())
                dismiss()
            }

        })
        join_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (getIsCanSave()) {
                    //宽长高
                    mBillingVolumeCalculatorAdapter?.appendData(mutableListOf(BillingVolumeCalculatorBean(singleWidth_ed.text.toString(), singleLong_ed.text.toString(), singleHigh_ed.text.toString(), number_ed.text.toString(), totalWeight_tv.text.toString())))
                    singleLong_ed.setText("")
                    singleWidth_ed.setText("")
                    singleHigh_ed.setText("")
                    number_ed.setText("")
                    totalWeight_tv.text = "0.00"
                    EditFocusUtils.showEditTextFocus(singleLong_ed)
                    planTotal()

                }
            }

        })
        cancel_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                dismiss()
            }

        })
    }

    private fun planTotal() {
        mBillingVolumeCalculatorAdapter?.getAllData()?.let {
            var singleWidthRecycler = 0
            var singleLongRecycler = 0
            var singleHightRecycler = 0
            var singleNumRecycler = 0
            var totalVolumeRecycler = 0.00
            for (item in it) {
                singleWidthRecycler += (item.singleWidth).toInt()
                singleLongRecycler += (item.singleLong).toInt()
                singleHightRecycler += (item.singleHeight).toInt()
                singleNumRecycler += (item.number).toInt()
                totalVolumeRecycler += (item.totalWeight).toDouble()

            }
            singleLong_recycler_tv.text = singleLongRecycler.toString()
            singleWidth_recycler_tv.text = singleWidthRecycler.toString()
            singleHigh_recycler_tv.text = singleHightRecycler.toString()
            totalNum_recycler_tv.text = singleNumRecycler.toString()
            totalVolume_recycler_tv.text = totalVolumeRecycler.toString()
        }

    }

    private fun initAddGoodsRecycler(view: View) {
        val calculator_recycler_view = view.findViewById<RecyclerView>(R.id.calculator_recycler_view)
        calculator_recycler_view.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        if (mBillingVolumeCalculatorAdapter == null) {
            mBillingVolumeCalculatorAdapter = BillingVolumeCalculatorAdapter(mContext).also {
                it.mOnRemoveInterface = object : BillingVolumeCalculatorAdapter.OnRemoveInterface {
                    override fun onRemove(position: Int, result: String) {
                        planTotal()
                    }
                }
            }
        }
        calculator_recycler_view.adapter = mBillingVolumeCalculatorAdapter
        planTotal()

    }

    override fun setContentView(): Int = R.layout.dialog_billing_volume_calculator
}

fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}