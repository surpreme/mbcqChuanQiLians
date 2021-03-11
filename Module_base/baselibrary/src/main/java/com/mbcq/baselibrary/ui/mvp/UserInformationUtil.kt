package com.mbcq.baselibrary.ui.mvp

import android.content.Context
import com.mbcq.baselibrary.db.SharePreferencesHelper

object UserInformationUtil {

    /**
     * 总map的tag
     */
    private val USR_INFORMATION_ALL: String = "USR_INFORMATION_ALL"

    /**
     * map中key的tag
     */
    private val USR_INFORMATION_KEY: String = "USR_INFORMATION_KEY"

    /**
     * webidCode
     */
    private val USR_INFORMATION_WEBIDCODE: String = "USR_INFORMATION_WEBIDCODE"

    /**
     * webidCode String
     */
    private val USR_INFORMATION_WEBIDCODE_STR: String = "USR_INFORMATION_WEBIDCODE_STR"

    /**
     * 是否指纹登录
     */
    private val USER_IS_FINGER_LOGIN_TAG = "USER_IS_FINGER_LOGIN_TAG"

    /**
     * 是否显示指纹登录检验页面
     */
    private val USER_IS_AGAIN_ASK_FINGER_LOGIN_TAG = "USER_IS_AGAIN_ASK_FINGER_LOGIN_TAG"


    /**
     * map中记住密码 账号的tag
     */
    private val USR_INFORMATION_NAME: String = "USR_INFORMATION_NAME"

    /**
     * map中记住密码 密码的tag
     */
    private val USR_INFORMATION_PSW: String = "USR_INFORMATION_PSW"

    /**
     * 运单打印机 蓝牙地址
     */

    private val USR_INFORMATION_WAYBILL_PRINTER_BLUETOOTH_ADDRESS_TAG: String = "USR_INFORMATION_WAYBILL_PRINTER_BLUETOOTH_ADDRESS"
    private val USR_INFORMATION_WAYBILL_PRINTER_BLUETOOTH_NAME_TAG: String = "USR_INFORMATION_WAYBILL_PRINTER_BLUETOOTH_NAME_TAG"

    /**
     * 标签 打印机 蓝牙地址
     */

    private val USR_INFORMATION_LABEL_PRINTER_BLUETOOTH_ADDRESS_TAG: String = "USR_INFORMATION_LABEL_PRINTER_BLUETOOTH_ADDRESS_TAG"
    private val USR_INFORMATION_LABEL_PRINTER_BLUETOOTH_NAME_TAG: String = "USR_INFORMATION_LABEL_PRINTER_BLUETOOTH_NAME_TAG"

    /**
     * 两级保存运单打印机 蓝牙地址
     */

    fun setWayBillBlueToothPrinter(context: Context, address: String) {
        BaseConstant.USR_INFORMATION_WAYBILL_PRINTER_BLUETOOTH_ADDRESS = address
        if (mSharePreferencesHelper == null)
            mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
        mSharePreferencesHelper?.put(USR_INFORMATION_WAYBILL_PRINTER_BLUETOOTH_ADDRESS_TAG, address)

    }

    fun setWayBillBlueToothPrinterName(context: Context, name: String) {
        BaseConstant.USR_INFORMATION_WAYBILL_PRINTER_BLUETOOTH_NAME = name
        if (mSharePreferencesHelper == null)
            mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
        mSharePreferencesHelper?.put(USR_INFORMATION_WAYBILL_PRINTER_BLUETOOTH_NAME_TAG, name)

    }

    /**
     * 两级保存标签打印机 蓝牙地址
     */

    fun setLabelBlueToothPrinter(context: Context, address: String) {
        BaseConstant.USR_INFORMATION_LABEL_PRINTER_BLUETOOTH_ADDRESS = address
        if (mSharePreferencesHelper == null)
            mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
        mSharePreferencesHelper?.put(USR_INFORMATION_LABEL_PRINTER_BLUETOOTH_ADDRESS_TAG, address)

    }

    fun setLabelBlueToothPrinterName(context: Context, name: String) {
        BaseConstant.USR_INFORMATION_LABEL_PRINTER_BLUETOOTH_NAME = name
        if (mSharePreferencesHelper == null)
            mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
        mSharePreferencesHelper?.put(USR_INFORMATION_LABEL_PRINTER_BLUETOOTH_NAME_TAG, name)

    }

    /**
     * 两级保存用户key
     */
    fun setUserKey(context: Context, key: String) {
        BaseConstant.USER_KEY = key
        if (mSharePreferencesHelper == null)
            mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
        mSharePreferencesHelper?.put(USR_INFORMATION_KEY, key)

    }

    fun setUserIsFingerLogIn(context: Context, isFingerLogin: Boolean) {
        if (mSharePreferencesHelper == null)
            mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
        mSharePreferencesHelper?.put(USER_IS_FINGER_LOGIN_TAG, isFingerLogin)

    }

    fun setWebIdCode(context: Context, localWebcode: String) {
        BaseConstant.WEBID_CODE = localWebcode
        if (mSharePreferencesHelper == null)
            mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
        mSharePreferencesHelper?.put(USR_INFORMATION_WEBIDCODE, localWebcode)

    }

    fun setWebIdCodeStr(context: Context, localWebcodeStr: String) {
        BaseConstant.WEBID_CODE_STR = localWebcodeStr
        if (mSharePreferencesHelper == null)
            mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
        mSharePreferencesHelper?.put(USR_INFORMATION_WEBIDCODE_STR, localWebcodeStr)

    }

    fun setUserIsAskFingerLogIn(context: Context, isAskFingerLogin: Boolean) {
        if (mSharePreferencesHelper == null)
            mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
        mSharePreferencesHelper?.put(USER_IS_AGAIN_ASK_FINGER_LOGIN_TAG, isAskFingerLogin)

    }

    var mSharePreferencesHelper: SharePreferencesHelper? = null
    fun setUserAccount(context: Context, name: String, psw: String) {
        if (mSharePreferencesHelper == null)
            mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
        mSharePreferencesHelper?.put(USR_INFORMATION_NAME, name)
        mSharePreferencesHelper?.put(USR_INFORMATION_PSW, psw)
    }

    fun getUserIsFingerLogIn(context: Context): Boolean {
        if (mSharePreferencesHelper == null)
            mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
        return if (mSharePreferencesHelper?.contain(USER_IS_FINGER_LOGIN_TAG)!!) {
            (mSharePreferencesHelper?.getSharePreference(USER_IS_FINGER_LOGIN_TAG, false)) as Boolean

        } else {
            false
        }
    }

    fun getUserIsAskFingerLogIn(context: Context): Boolean {
        if (mSharePreferencesHelper == null)
            mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
        return if (mSharePreferencesHelper?.contain(USER_IS_AGAIN_ASK_FINGER_LOGIN_TAG)!!) {
            (mSharePreferencesHelper?.getSharePreference(USER_IS_AGAIN_ASK_FINGER_LOGIN_TAG, false)) as Boolean

        } else {
            true
        }
    }

    fun getUserName(context: Context): String {
        if (mSharePreferencesHelper == null)
            mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
        return if (mSharePreferencesHelper?.contain(USR_INFORMATION_NAME)!!) {
            (mSharePreferencesHelper?.getSharePreference(USR_INFORMATION_NAME, "")) as String

        } else {
            ""
        }
    }

    fun getWayBillBlueToothPrinter(context: Context): String {
        return if (BaseConstant.USR_INFORMATION_WAYBILL_PRINTER_BLUETOOTH_ADDRESS.isNullOrEmpty()) {
            if (mSharePreferencesHelper == null)
                mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
            if (mSharePreferencesHelper?.contain(USR_INFORMATION_WAYBILL_PRINTER_BLUETOOTH_ADDRESS_TAG)!!) {
                mSharePreferencesHelper?.getSharePreference(USR_INFORMATION_WAYBILL_PRINTER_BLUETOOTH_ADDRESS_TAG, "") as String
            } else {
                ""
            }

        } else {
            BaseConstant.USR_INFORMATION_WAYBILL_PRINTER_BLUETOOTH_ADDRESS
        }
    }


    fun getWayBillBlueToothPrinterName(context: Context): String {
        return if (BaseConstant.USR_INFORMATION_WAYBILL_PRINTER_BLUETOOTH_NAME.isNullOrEmpty()) {
            if (mSharePreferencesHelper == null)
                mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
            if (mSharePreferencesHelper?.contain(USR_INFORMATION_WAYBILL_PRINTER_BLUETOOTH_NAME_TAG)!!) {
                mSharePreferencesHelper?.getSharePreference(USR_INFORMATION_WAYBILL_PRINTER_BLUETOOTH_NAME_TAG, "") as String
            } else {
                ""
            }

        } else {
            BaseConstant.USR_INFORMATION_WAYBILL_PRINTER_BLUETOOTH_NAME
        }
    }

    fun getLabelBlueToothPrinter(context: Context): String {
        return if (BaseConstant.USR_INFORMATION_LABEL_PRINTER_BLUETOOTH_ADDRESS.isNullOrEmpty()) {
            if (mSharePreferencesHelper == null)
                mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
            if (mSharePreferencesHelper?.contain(USR_INFORMATION_LABEL_PRINTER_BLUETOOTH_ADDRESS_TAG)!!) {
                mSharePreferencesHelper?.getSharePreference(USR_INFORMATION_LABEL_PRINTER_BLUETOOTH_ADDRESS_TAG, "") as String
            } else {
                ""
            }

        } else {
            BaseConstant.USR_INFORMATION_LABEL_PRINTER_BLUETOOTH_ADDRESS
        }
    }


    fun getLabelBlueToothPrinterName(context: Context): String {
        return if (BaseConstant.USR_INFORMATION_LABEL_PRINTER_BLUETOOTH_NAME.isNullOrEmpty()) {
            if (mSharePreferencesHelper == null)
                mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
            if (mSharePreferencesHelper?.contain(USR_INFORMATION_LABEL_PRINTER_BLUETOOTH_NAME_TAG)!!) {
                mSharePreferencesHelper?.getSharePreference(USR_INFORMATION_LABEL_PRINTER_BLUETOOTH_NAME_TAG, "") as String
            } else {
                ""
            }

        } else {
            BaseConstant.USR_INFORMATION_LABEL_PRINTER_BLUETOOTH_NAME
        }
    }

    fun getUserPsw(context: Context): String {
        if (mSharePreferencesHelper == null)
            mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
        return if (mSharePreferencesHelper?.contain(USR_INFORMATION_PSW)!!) {
            (mSharePreferencesHelper?.getSharePreference(USR_INFORMATION_PSW, "")) as String

        } else {
            ""
        }
    }

    fun getUserToken(context: Context): String {
        return if (BaseConstant.USER_KEY.isNullOrEmpty()) {
            if (mSharePreferencesHelper == null)
                mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
            if (mSharePreferencesHelper?.contain(USR_INFORMATION_KEY)!!) {
                mSharePreferencesHelper?.getSharePreference(USR_INFORMATION_KEY, "") as String
            } else {
                ""
            }

        } else {
            BaseConstant.USER_KEY
        }
    }

    fun getWebIdCode(context: Context): String {
        return if (BaseConstant.WEBID_CODE.isNullOrEmpty()) {
            if (mSharePreferencesHelper == null)
                mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
            if (mSharePreferencesHelper?.contain(USR_INFORMATION_WEBIDCODE)!!) {
                mSharePreferencesHelper?.getSharePreference(USR_INFORMATION_WEBIDCODE, "") as String
            } else {
                ""
            }

        } else {
            BaseConstant.WEBID_CODE
        }
    }

    fun getWebIdCodeStr(context: Context): String {
        return if (BaseConstant.WEBID_CODE_STR.isNullOrEmpty()) {
            if (mSharePreferencesHelper == null)
                mSharePreferencesHelper = SharePreferencesHelper(context, USR_INFORMATION_ALL)
            if (mSharePreferencesHelper?.contain(USR_INFORMATION_WEBIDCODE_STR)!!) {
                mSharePreferencesHelper?.getSharePreference(USR_INFORMATION_WEBIDCODE_STR, "") as String
            } else {
                ""
            }

        } else {
            BaseConstant.WEBID_CODE_STR
        }
    }


}