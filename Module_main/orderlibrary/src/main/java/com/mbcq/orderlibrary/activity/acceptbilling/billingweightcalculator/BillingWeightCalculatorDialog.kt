package com.mbcq.orderlibrary.activity.acceptbilling.billingweightcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.util.system.EditFocusUtils
import com.mbcq.baselibrary.util.system.ToastUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.activity.acceptbilling.AddGoodsAcceptBillingAdapter
import kotlinx.android.synthetic.main.dialog_billing_weight_calculator.*

/**
 * @time 2020-11-04
 * @author lzy
 * 受理开单 重量计算器
 */
class BillingWeightCalculatorDialog(val mScreenWidth: Int, var mOnResultInterface: OnResultInterface? = null) : BaseDialogFragment() {
    interface OnResultInterface {
        fun onResult(totalWeight: String)
    }

    var mBillingWeightCalculatorAdapter: BillingWeightCalculatorAdapter? = null
    override fun setDialogWidth(): Int {
        return mScreenWidth / 5 * 4
    }

    private fun planTotal() {
        mBillingWeightCalculatorAdapter?.getAllData()?.let {
            var singleWeightRecycler = 0.00
            var totalWeightRecycler = 0.00
            var numberRecycler = 0
            for (item in it) {
                singleWeightRecycler += (item.singleWeight).toDouble()
                totalWeightRecycler += (item.totalWeight).toDouble()
                numberRecycler += (item.number).toInt()

            }
            singleWeight_recycler_tv.text = singleWeightRecycler.toString()
            number_recycler_tv.text = numberRecycler.toString()
            totalWeight_recycler_tv.text = totalWeightRecycler.toString()
        }

    }

    private fun initAddGoodsRecycler(view: View) {
        val calculator_recycler_view = view.findViewById<RecyclerView>(R.id.calculator_recycler_view)
        calculator_recycler_view.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)
        if (mBillingWeightCalculatorAdapter == null) {
            mBillingWeightCalculatorAdapter = BillingWeightCalculatorAdapter(mContext).also {
                it.mOnRemoveInterface = object : BillingWeightCalculatorAdapter.OnRemoveInterface {
                    override fun onRemove(position: Int, result: String) {
                        planTotal()
                    }
                }
            }
        }
        calculator_recycler_view.adapter = mBillingWeightCalculatorAdapter
        planTotal()

    }

    fun getIsCanSave(): Boolean {
        if (singleWeight_ed.text.isBlank()) {
            return false
        }
        if (number_ed.text.isBlank()) {
            return false
        }
        return true
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        initAddGoodsRecycler(view)
        save_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                mOnResultInterface?.onResult(totalWeight_recycler_tv.text.toString())
                dismiss()
            }
        })

        singleWeight_ed.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (singleWeight_ed.text.toString().isNotBlank() && number_ed.text.toString().isNotBlank())
                    totalWeight_tv.text = ((singleWeight_ed.text.toString()).toDouble() * (number_ed.text.toString()).toInt()).toString()
            }

        })
        number_ed.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                if (singleWeight_ed.text.toString().isNotBlank() && number_ed.text.toString().isNotBlank())
                    totalWeight_tv.text = ((singleWeight_ed.text.toString()).toDouble() * (number_ed.text.toString()).toInt()).toString()
            }

        })
        join_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (getIsCanSave()) {
                    mBillingWeightCalculatorAdapter?.appendData(mutableListOf(BillingWeightCalculatorBean(singleWeight_ed.text.toString(), number_ed.text.toString(), totalWeight_tv.text.toString())))
                    singleWeight_ed.setText("")
                    number_ed.setText("")
                    totalWeight_tv.text = "0.00"
                    EditFocusUtils.showEditTextFocus(singleWeight_ed)
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

    override fun setContentView(): Int = R.layout.dialog_billing_weight_calculator

}