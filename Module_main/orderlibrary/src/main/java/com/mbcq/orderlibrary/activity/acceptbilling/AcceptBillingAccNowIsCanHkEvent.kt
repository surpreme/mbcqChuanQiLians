package com.mbcq.orderlibrary.activity.acceptbilling

/**
 * 开启返款后选择付款方式后的
 * 选择配置的付款方式后会让返款可以输入
 * 1 可输入 2 不可输入
 */
data class AcceptBillingAccNowIsCanHkEvent(var mAccNowTag: String = "", var isCanInput: Int = 0)