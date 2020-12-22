package com.mbcq.commonlibrary.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.commonlibrary.R

class CheckBoxOutViewAdapter<T : BaseOutCheckedAdapterBean> : RecyclerView.Adapter<CheckBoxOutViewAdapter.ItemViewHolder> {
    private var context: Context?
    private val inflater: LayoutInflater
    private var mIsPintCheck = false
    private var mSonBean = ArrayList<T>()

    constructor(context: Context) {
        this.context = context
        this.inflater = LayoutInflater.from(context)
    }

    constructor(context: Context, isPintCheck: Boolean) {
        this.context = context
        this.inflater = LayoutInflater.from(context)
        this.mIsPintCheck = isPintCheck
    }


    fun appendData(list: List<T>) {
        mSonBean.addAll(list)
        notifyDataSetChanged()
    }

    fun clearDatas() {
        mSonBean.clear()
        notifyDataSetChanged()
    }

    fun selectAll() {
        for (item in mSonBean) {
            item.checked = true
        }
        notifyDataSetChanged()
    }

    fun unSelectAll() {
        for (item in mSonBean) {
            item.checked = false
        }
        notifyDataSetChanged()
    }

    fun getDatas(): ArrayList<T> {
        return mSonBean
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var base_out_check_box = itemView.findViewById<CheckBox>(R.id.base_out_check_box)
        var base_out_title_tv = itemView.findViewById<TextView>(R.id.base_out_title_tv)
        var base_out_content_ed = itemView.findViewById<EditText>(R.id.base_out_content_ed)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(inflater.inflate(R.layout.base_out_checkbox, parent, false))
    }

    override fun getItemCount(): Int = mSonBean.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.itemView.setBackgroundColor(Color.WHITE)
        holder.base_out_title_tv.text = mSonBean[position].title
//        holder.base_out_content_ed.setText(mSonBean[position].baseOutNum)
        holder.base_out_title_tv.textSize = 16f
        holder.base_out_check_box.isChecked = mSonBean[position].checked
        holder.base_out_content_ed.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                mSonBean[position].baseOutNum = s.toString()
             /*   if (s.toString().isNotBlank() && mIsPintCheck) {
                    if (!holder.base_out_check_box.isChecked) {
                        mSonBean[position].checked = true
                        notifyItemChanged(position)
                    }

                }*/
            }

            override fun afterTextChanged(s: Editable?) {
                mSonBean[position].outNum = "${mSonBean[position].title} ${s.toString()} "

            }

        })
        holder.base_out_check_box.setOnCheckedChangeListener { _, isChecked ->
            mSonBean[position].checked = isChecked
        }


    }
}