package com.mbcq.vehicleslibrary.fragment

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.dialog_scan_num.*
import java.math.BigDecimal
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 *拆票
 */
class SplitTicketNumDialog(var mAllNum: Int = 0, var mClackInterface: OnClickInterface.OnClickInterface? = null) : BaseDialogFragment() {
    @SuppressLint("SetTextI18n")
    override fun initView(view: View, savedInstanceState: Bundle?) {
        scan_num_ed.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (!checkStrIsNum(scan_num_ed.text.toString()))
                    return
                if (scan_num_ed.text.toString().toLong() > mAllNum) {
                    scan_num_ed.setTextColor(Color.RED)
                } else {
                    if (scan_num_ed.currentTextColor != Color.BLACK) {
                        scan_num_ed.setTextColor(Color.BLACK)
                    }
                }

            }

        })
        sure_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (scan_num_ed.text.toString().isBlank())
                    return
                if (scan_num_ed.text.toString().toInt() > mAllNum)
                    return
                if (scan_num_ed.text.toString().toInt() == 0)
                    return
                mClackInterface?.onResult(scan_num_ed.text.toString().toInt().toString(), "")
                dismiss()
            }

        })
        title_tv.text = "请输入拆票件数"
        scan_num_tips_tv.text = "全部拆票件数${mAllNum}"
        cancel_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
//                mCancelClackInterface?.onResult("", "")
                dismiss()
            }

        })
    }

    private fun checkStrIsNum(str: String): Boolean {
        try {
            /** 先将str转成BigDecimal，然后在转成String  */
            BigDecimal(str).toString()
        } catch (e: java.lang.Exception) {
            /** 如果转换数字失败，说明该str并非全部为数字  */
            return false
        }
        val isNum: Matcher = Pattern.compile("-?[0-9]+(\\.[0-9]+)?").matcher(str)
        return isNum.matches()
    }

    override fun setContentView(): Int = R.layout.dialog_scan_num

}