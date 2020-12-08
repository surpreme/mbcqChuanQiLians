package com.mbcq.commonlibrary.scan.pda

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.view.KeyEvent
import com.android.scanner.impl.ReaderManager
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.BaseActivity
import com.mbcq.baselibrary.ui.BaseListMVPActivity
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.baselibrary.ui.mvp.BaseView


/**
 * @author liziyang 2020-11-28
 * @information 扫描封装类
 */
abstract class CommonScanPDAMVPListActivity<V : BaseView, T : BasePresenterImpl<V>, X> : BaseListMVPActivity<V, T, X>(), BaseView {

    //扫描条码服务广播
    //Scanning barcode service broadcast.
    protected val SCN_CUST_ACTION_SCODE = "com.android.server.scannerservice.broadcast"

    //条码扫描数据广播
    //Barcode scanning data broadcast.
    protected val SCN_CUST_EX_SCODE = "scannerdata"
    abstract fun onPDAScanResult(result: String)

    /**
     * 扫描管理器
     */
    protected var mPDAScanManager: ReaderManager? = null

    /**
     * 可使用 (活跃的意思)
     */
    protected var isActive = false

    /**
     * 扫描锁 翻译的意思是设置扫描物理按键是否可使用
     */
    protected var enableScanKey = false

    /**
     * 获取当前数据输出模式
     * 0 means Copy and Paste,1 means Key Emulation，2 means API.
     * 如果该模式不是API，请将其配置为模式API，并在销毁活动时恢复为原始模式。
     */

    protected var outPutMode = 0

    /**
     * 扫描返回值添加后缀
     *  0 the end of code add "\n"
     *  1 the end of code add " "
     *  2 the end of code add "\t"
     *  3 NULL
     */
    protected var endCharMode = 0


    private val scanDataReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if (intent.action.equals(SCN_CUST_ACTION_SCODE)) {

                try {
                    var message: String? = ""
                    message = intent.getStringExtra(SCN_CUST_EX_SCODE)
                    mPDAScanManager?.stopScanAndDecode()
                    onPDAScanResult(message)
                } catch (e: Exception) {
                    e.printStackTrace()
//                    TalkSureDialog(mContext, getScreenWidth(), "pda扫描出错${e.toString()}").show()
                    showToast("pda扫描出错${e.toString()}")
                }
            }
        }

    }

    override fun onRestart() {
        super.onRestart()
        mPDAScanManager?.isEnableScankey = true

    }

    fun initReceiver() {
        val intentFilter = IntentFilter(SCN_CUST_ACTION_SCODE)
        registerReceiver(scanDataReceiver, intentFilter)
    }

    override fun initExtra() {
        super.initExtra()
        mPDAScanManager = ReaderManager.getInstance()
    }

    override fun onResume() {
        super.onResume()
        initializationScan()
        initReceiver()
    }

    protected fun initializationScan() {
        mPDAScanManager?.let {
            /**
             * Check whether turn on scan engine
             */
            isActive = it.GetActive()
            if (!isActive) {
                it.SetActive(true)
            }
            /**
             * The physical scanning key work or not
             */
            enableScanKey = it.isEnableScankey
            if (!enableScanKey) {
                it.isEnableScankey = true
            }
            /**
             * Get current data output mode:0 means Copy and Paste,1 means Key Emulation，2 means API.
             * If the mode is not API, please configure it as mode API，and restore to original mode when destroying activity.
             */
            outPutMode = it.outPutMode
            if (outPutMode != 2) {
                it.outPutMode = 2
            }
            /**
             *  Set up End character:
             */
            if (endCharMode != 3) {
                it.endCharMode = 3
            }

        }
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(scanDataReceiver)

    }

    override fun onDestroy() {
        mPDAScanManager?.Release()
        mPDAScanManager?.isEnableScankey = false
        mPDAScanManager = null
        super.onDestroy()

    }


    /*  override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
          if (keyCode == KeyEvent.KEYCODE_BACK && event.repeatCount == 0) {
              //Cancel register boardcast
  //            unregisterReceiver(scanDataReceiver)
              //
              mPDAScanManager?.outPutMode = outPutMode
              mPDAScanManager?.endCharMode = endCharMode//Set up the mode as system configured mode
              mPDAScanManager?.isEnableScankey = enableScanKey //Whether forbid physical scanning key.
              mPDAScanManager?.SetActive(isActive)  //Restore to system configured state of scan engine.

          }
          return super.onKeyDown(keyCode, event)

      }*/
}