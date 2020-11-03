package com.mbcq.baselibrary.util.system

import android.widget.EditText

/**
 * 输入框工具
 */
object EditFocusUtils {
    fun showEditTextFocus(editText: EditText) {
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
    }
}