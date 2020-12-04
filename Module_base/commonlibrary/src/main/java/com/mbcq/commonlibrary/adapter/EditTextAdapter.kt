package com.mbcq.commonlibrary.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.view.MoneyInputFilter
import com.mbcq.commonlibrary.R

class EditTextAdapter<T : BaseEditTextAdapterBean> : RecyclerView.Adapter<EditTextAdapter.ItemViewHolder> {
    private var context: Context?
    private val inflater: LayoutInflater
    private var mSonBean = ArrayList<T>()

    constructor(context: Context) {
        this.context = context
        this.inflater = LayoutInflater.from(context)
    }

    //合计
    interface OnToTalInterface {
        fun onItemFoused(v: View, position: Int, result: String, tag: String)
    }

    var mOnToTalInterface: OnToTalInterface? = null

    fun appendData(list: List<T>) {
        mSonBean.addAll(list)
        notifyDataSetChanged()
    }

     fun getData(): ArrayList<T> = mSonBean
    fun clearDatas() {
        mSonBean.clear()
        notifyDataSetChanged()
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title_tv: TextView = itemView.findViewById(R.id.title_tv)
        var inputStr_ed: EditText = itemView.findViewById(R.id.inputStr_ed)


    }

    var mClick: OnClickInterface.OnRecyclerClickInterface? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(inflater.inflate(R.layout.base_edittext, parent, false))
    }

    override fun getItemCount(): Int = mSonBean.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.title_tv.text = mSonBean[position].title
        holder.inputStr_ed.setText(mSonBean[position].inputStr)
//        InputFilter[] filters={new CashierInputFilter()};
        holder.inputStr_ed.filters = arrayOf<InputFilter>(MoneyInputFilter()) //设置金额输入的过滤器，保证只能输入金额类型
        holder.inputStr_ed.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                mSonBean[position].inputStr = s.toString()
                mOnToTalInterface?.onItemFoused(holder.inputStr_ed, position, holder.inputStr_ed.text.toString(), mSonBean[position].tag)

            }

        })

        holder.itemView.setOnClickListener {
            mClick?.onItemClick(it, position, mSonBean[position].tag)
        }


    }
}