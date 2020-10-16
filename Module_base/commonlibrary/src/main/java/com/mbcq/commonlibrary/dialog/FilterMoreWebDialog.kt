package com.mbcq.commonlibrary.dialog

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.mvp.BaseConstant
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.system.TimeUtils
import com.mbcq.baselibrary.util.system.ToastUtils
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.R
import com.mbcq.commonlibrary.adapter.BaseCheckedAdapterBean
import com.mbcq.commonlibrary.adapter.CheckBoxViewAdapter
import com.mbcq.commonlibrary.db.WebAreaDbInfo
import kotlinx.android.synthetic.main.dialog_filter_withtime_recyclerview.*
import org.json.JSONArray
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * 仅需要把list的string 传过来 减少那边代码
 * 选中则会把当前的string传回去
 * 统一弹窗 请慎重修改
 */

class FilterMoreWebDialog : BaseDialogFragment {
    var mScreenWidth: Int = 0
    var mDatas: List<BaseCheckedAdapterBean>
    var tips: String
    var isGridLayoutManager: Boolean = false
    var isShowOutSide: Boolean = false
    var isShowTimes: Boolean = true
    var isOnlyLast: Boolean = true
    var mClickInterface: OnClickInterface
    var mStartTime = ""
    var mEndTime = ""
   /* lateinit var filter_recycler_view: RecyclerView
    lateinit var top_title_tv: TextView
    lateinit var today_tv: TextView
    lateinit var yesterday_tv: TextView
    lateinit var last_three_day: TextView
    lateinit var this_monday_tv: TextView
    lateinit var end_time_ll: LinearLayout
    lateinit var start_time_ll: LinearLayout
    lateinit var start_time_tv: TextView
    lateinit var end_time_tv: TextView
    lateinit var cancel_btn: Button
    lateinit var sure_btn: Button
    lateinit var all_check_box: CheckBox*/
    var mTimeTag = "今天"
    override fun setDialogWidth(): Int {
        return mScreenWidth / 5 * 4
    }

    interface OnClickInterface {
        fun onResult(s1: String, s2: String, s3: String)
    }

    /**
     * mScreenWidth 为了适配屏幕宽度
     * mDatas 需要展示的数据list<Bean> 的json
     * showTag 需要展示给用户看的tag
     * tips 顶部显示的title
     * isGridLayoutManager 是否为表格布局 3
     * isShowOutSide 是否显示背景为边框线
     * mClickInterface 点击回调事件
     */
    /*  constructor(mScreenWidth: Int, mDatas: String, showTag: String, tips: String, isGridLayoutManager: Boolean, isShowOutSide: Boolean, mClickInterface: OnClickInterface.OnClickInterface) {
          init(mScreenWidth, mDatas, showTag, "", tips, isGridLayoutManager, isShowOutSide, mClickInterface)
      }*/

    constructor(mScreenWidth: Int, mDatas: String, showTag: String, resultTag: String, isWebCodeDialog: Boolean, isShowTime: Boolean,isOnlyLast: Boolean, tips: String, isGridLayoutManager: Boolean, mClickInterface: OnClickInterface) {
        val dataslist = JSONArray(mDatas)
        val showDatas = mutableListOf<BaseCheckedAdapterBean>()
        for (index in 0 until dataslist.length()) {
            val mBaseCheckedAdapterBean = BaseCheckedAdapterBean()
            val obj = dataslist.getJSONObject(index)
            mBaseCheckedAdapterBean.title = obj.optString(showTag)
            mBaseCheckedAdapterBean.tag = if (resultTag.isNotEmpty()) obj.optString(resultTag) else GsonUtils.toPrettyFormat(obj.toString())
            if (isWebCodeDialog) {
                if (obj.optString("webidCode") == BaseConstant.WEBID_CODE) {
                    mBaseCheckedAdapterBean.checked = true
                }
            }

            showDatas.add(mBaseCheckedAdapterBean)
        }
        this.isOnlyLast = isOnlyLast
        this.isShowTimes = isShowTime
        this.mDatas = showDatas
        this.mScreenWidth = mScreenWidth
        this.tips = tips
        this.isGridLayoutManager = isGridLayoutManager
        this.mClickInterface = mClickInterface
    }

    constructor(mScreenWidth: Int, mDatas: String, showTag: String, resultTag: String, mWebCodeList: List<String>, isShowTime: Boolean,isOnlyLast: Boolean,tips: String, isGridLayoutManager: Boolean, mClickInterface: OnClickInterface) {
        val dataslist = JSONArray(mDatas)
        val showDatas = mutableListOf<BaseCheckedAdapterBean>()
        for (index in 0 until dataslist.length()) {
            val mBaseCheckedAdapterBean = BaseCheckedAdapterBean()
            val obj = dataslist.getJSONObject(index)
            mBaseCheckedAdapterBean.title = obj.optString(showTag)
            mBaseCheckedAdapterBean.tag = if (resultTag.isNotEmpty()) obj.optString(resultTag) else GsonUtils.toPrettyFormat(obj.toString())
            for (item in mWebCodeList){
                if ( item == obj.optString("webid")){
                    mBaseCheckedAdapterBean.checked=true
                    break
                }
            }

            showDatas.add(mBaseCheckedAdapterBean)
        }
        this.isOnlyLast = isOnlyLast
        this.isShowTimes = isShowTime
        this.mDatas = showDatas
        this.mScreenWidth = mScreenWidth
        this.tips = tips
        this.isGridLayoutManager = isGridLayoutManager
        this.mClickInterface = mClickInterface
    }


    @SuppressLint("SimpleDateFormat")
    override fun initView(view: View, savedInstanceState: Bundle?) {
     /*   all_check_box = view.findViewById(R.id.all_check_box)
        filter_recycler_view = view.findViewById(R.id.filter_recycler_view)
        this_monday_tv = view.findViewById(R.id.this_monday_tv)
        last_three_day = view.findViewById(R.id.last_three_day)
        this_monday_tv = view.findViewById(R.id.this_monday_tv)
        yesterday_tv = view.findViewById(R.id.yesterday_tv)
        today_tv = view.findViewById(R.id.today_tv)
        sure_btn = view.findViewById(R.id.sure_btn)
        cancel_btn = view.findViewById(R.id.cancel_btn)
        top_title_tv = view.findViewById(R.id.top_title_tv)
        end_time_ll = view.findViewById(R.id.end_time_ll)
        start_time_ll = view.findViewById(R.id.start_time_ll)
        start_time_tv = view.findViewById(R.id.start_time_tv)
        end_time_tv = view.findViewById(R.id.end_time_tv)*/
        top_title_tv.text = tips
        cancel_btn.setOnClickListener {
            dismiss()
        }
        time_ll.visibility = if (isShowTimes) View.VISIBLE else View.GONE


        if (isGridLayoutManager)
            filter_recycler_view.layoutManager = GridLayoutManager(mContext, 3)
        else
            filter_recycler_view.layoutManager = LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false)

        val mCheckBoxViewAdapter = CheckBoxViewAdapter<BaseCheckedAdapterBean>(mContext)

        filter_recycler_view.adapter = mCheckBoxViewAdapter
        mCheckBoxViewAdapter.appendData(mDatas)
        all_check_box.setOnCheckedChangeListener { _, isChecked ->
            run {
                if (isChecked) {
                    mCheckBoxViewAdapter.selectAll()
                } else {
                    mCheckBoxViewAdapter.unSelectAll()

                }
            }
        }
        sure_btn.setOnClickListener {
            val mSelect = StringBuilder()
            val mSelectTitle = StringBuilder()
            for ((index, item) in (mCheckBoxViewAdapter.getDatas()).withIndex()) {
                if (item.checked) {
                    mSelect.append(item.tag)
                    mSelectTitle.append(item.title)
                    if (index != mCheckBoxViewAdapter.getDatas().size) {
                        mSelect.append(",")
                        mSelectTitle.append(",")
                    }


                }

            }
            if (isOnlyLast){
                if (mSelect.toString().isEmpty()) {
                    ToastUtils.showToast(mContext, "请至少选择一个网点")
                    return@setOnClickListener
                }
            }

            mClickInterface.onResult(mSelect.toString(), mSelectTitle.toString(), "$mStartTime@$mEndTime")
            dismiss()
        }
        initClickTime()
        initCurrentTime(1, 0)

    }

    /**
     * type
     * @1今天
     * @2昨天
     * @3前三天
     * @4本月
     */
    @SuppressLint("SimpleDateFormat")
    private fun initCurrentTime(type: Int, lastNumber: Int) {
        when (type) {
            1 -> {
                val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val mDate = Date(System.currentTimeMillis())
                val format = mDateFormat.format(mDate)
                mStartTime = "$format 00:00:00"
                mEndTime = "$format 23:59:59"
                start_time_tv.text = format
                end_time_tv.text = format
            }
            2 -> {
                val format = TimeUtils.getLastdayStr(lastNumber)
                mStartTime = "$format 00:00:00"
                mEndTime = "$format 23:59:59"
                start_time_tv.text = format
                end_time_tv.text = format
            }
            3 -> {
                //结束时间 今天
                val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val mDate = Date(System.currentTimeMillis())
                val endFormat = mDateFormat.format(mDate)
                //开始时间
                val format = TimeUtils.getLastdayStr(lastNumber)

                mStartTime = "$format 00:00:00"
                mEndTime = "$endFormat 23:59:59"
                start_time_tv.text = format
                end_time_tv.text = endFormat
            }
            4 -> {
                //结束时间 今天
                val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                val mDate = Date(System.currentTimeMillis())
                val endFormat = mDateFormat.format(mDate)
                //开始时间
                val mStartDate = TimeUtils.getFirstDayOfMonth(mDate)
                val startFormat = mDateFormat.format(mStartDate)

                mStartTime = "$startFormat 00:00:00"
                mEndTime = "$endFormat 23:59:59"
                start_time_tv.text = startFormat
                end_time_tv.text = endFormat
            }
        }
    }

    private fun clearSelectBtnTime() {
        yesterday_tv.setBackgroundResource(R.drawable.hollow_out_gray)
        yesterday_tv.setTextColor(Color.BLACK)
        mTimeTag = yesterday_tv.text.toString()

        today_tv.setBackgroundResource(R.drawable.hollow_out_gray)
        today_tv.setTextColor(Color.BLACK)

        last_three_day.setBackgroundResource(R.drawable.hollow_out_gray)
        last_three_day.setTextColor(Color.BLACK)

        this_monday_tv.setBackgroundResource(R.drawable.hollow_out_gray)
        this_monday_tv.setTextColor(Color.BLACK)
    }

    private fun initClickTime() {
        start_time_ll.setOnClickListener(object : SingleClick() {
            @SuppressLint("SimpleDateFormat")
            override fun onSingleClick(v: View?) {
                TimeDialogUtil.getChoiceTimer(mContext, OnTimeSelectListener { date, _ ->
                    val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                    val format: String = mDateFormat.format(date)
                    start_time_tv.text = format
                    mStartTime = "$format 00:00:00"
                    clearSelectBtnTime()


                }, "选择开始时间", isStartCurrentTime = false, isEndCurrentTime = true, isYear = true, isHM = false).show(start_time_ll)
            }

        })
        end_time_ll.setOnClickListener(object : SingleClick() {
            @SuppressLint("SimpleDateFormat")
            override fun onSingleClick(v: View?) {
                TimeDialogUtil.getChoiceTimer(mContext, OnTimeSelectListener { date, _ ->
                    val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                    val format: String = mDateFormat.format(date)
                    end_time_tv.text = format

                    mEndTime = "$format 23:59:59"
                    clearSelectBtnTime()

                }, "选择结束时间", isStartCurrentTime = false, isEndCurrentTime = true, isYear = true, isHM = false).show(end_time_ll)
            }

        })
        today_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                today_tv.setBackgroundResource(R.drawable.round_blue)
                today_tv.setTextColor(Color.WHITE)
                mTimeTag = today_tv.text.toString()

                yesterday_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                yesterday_tv.setTextColor(Color.BLACK)

                last_three_day.setBackgroundResource(R.drawable.hollow_out_gray)
                last_three_day.setTextColor(Color.BLACK)

                this_monday_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                this_monday_tv.setTextColor(Color.BLACK)
                initCurrentTime(1, 0)


            }
        })
        yesterday_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                yesterday_tv.setBackgroundResource(R.drawable.round_blue)
                yesterday_tv.setTextColor(Color.WHITE)
                mTimeTag = yesterday_tv.text.toString()

                today_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                today_tv.setTextColor(Color.BLACK)

                last_three_day.setBackgroundResource(R.drawable.hollow_out_gray)
                last_three_day.setTextColor(Color.BLACK)

                this_monday_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                this_monday_tv.setTextColor(Color.BLACK)
                initCurrentTime(2, 1)
            }
        })
        last_three_day.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                last_three_day.setBackgroundResource(R.drawable.round_blue)
                last_three_day.setTextColor(Color.WHITE)
                mTimeTag = last_three_day.text.toString()

                yesterday_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                yesterday_tv.setTextColor(Color.BLACK)

                today_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                today_tv.setTextColor(Color.BLACK)

                this_monday_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                this_monday_tv.setTextColor(Color.BLACK)
                initCurrentTime(3, 3)
            }
        })
        this_monday_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                this_monday_tv.setBackgroundResource(R.drawable.round_blue)
                this_monday_tv.setTextColor(Color.WHITE)
                mTimeTag = this_monday_tv.text.toString()

                yesterday_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                yesterday_tv.setTextColor(Color.BLACK)

                last_three_day.setBackgroundResource(R.drawable.hollow_out_gray)
                last_three_day.setTextColor(Color.BLACK)

                today_tv.setBackgroundResource(R.drawable.hollow_out_gray)
                today_tv.setTextColor(Color.BLACK)
                initCurrentTime(4, 0)

            }
        })
    }

    override fun setContentView(): Int = R.layout.dialog_filter_withtime_recyclerview

}