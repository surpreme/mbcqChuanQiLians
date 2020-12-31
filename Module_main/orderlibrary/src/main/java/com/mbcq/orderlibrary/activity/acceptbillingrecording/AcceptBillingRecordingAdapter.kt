package com.mbcq.orderlibrary.activity.acceptbillingrecording

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.orderlibrary.R

class AcceptBillingRecordingAdapter(context: Context?) : BaseRecyclerAdapter<AcceptBillingRecordingBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_accept_billing_record, parent, false))
    interface OnFixedAcceptBillingRecordingInterface {
        //驳回申请
        fun onReject(v: View, position: Int, data: AcceptBillingRecordingBean)

        //审核
        fun onReview(v: View, position: Int, data: AcceptBillingRecordingBean)

        //运营审查
        fun onOperationReview(v: View, position: Int, data: AcceptBillingRecordingBean)

        //财务审计
        fun onFinancialAudit(v: View, position: Int, data: AcceptBillingRecordingBean)

        //查看详情
        fun onLookMoreInfo(v: View, position: Int, data: AcceptBillingRecordingBean)
    }

    var mOnFixedAcceptBillingRecordingInterface: OnFixedAcceptBillingRecordingInterface? = null

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        (holder as ItemViewHolder).waybill_number_tv.text = mDatas[position].billno
        holder.waybill_time_tv.text = mDatas[position].yyCheckDate
        holder.applicant_tv.text = "申请人：${mDatas[position].opeMan}"
        holder.apply_outlets_tv.text = "申请网点：${mDatas[position].opeWebidCodeStr}"
        holder.modify_reason_tv.text = "修改原因：${mDatas[position].updateRemark}"
        //修改内容
        holder.modify_content_tv.text = mDatas[position].updateContent
        holder.reject_application_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                mOnFixedAcceptBillingRecordingInterface?.onReject(v, position, mDatas[position])
            }

        })
        holder.father_cl.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                mOnFixedAcceptBillingRecordingInterface?.onLookMoreInfo(v, position, mDatas[position])

            }

        })
        context?.let {
            holder.operation_review_iv.setColorFilter(it.resources.getColor(if (mDatas[position].yyCheckMan.isNotBlank()) R.color.base_blue else R.color.base_grayDark))
            holder.financial_audit_iv.setColorFilter(it.resources.getColor(if (mDatas[position].cwCheckMan.isNotBlank()) R.color.base_blue else R.color.base_grayDark))
            holder.operation_review_tv.setTextColor(it.resources.getColor(if (mDatas[position].yyCheckMan.isNotBlank()) R.color.base_blue else R.color.base_grayDark))
            holder.financial_audit_tv.setTextColor(it.resources.getColor(if (mDatas[position].cwCheckMan.isNotBlank()) R.color.base_blue else R.color.base_grayDark))
        }
        /**
         * 内容 isUpdCon
         * 费用 isUpdMon
         */
        holder.financial_audit_tv.visibility = if (mDatas[position].isUpdMon == 1) View.VISIBLE else View.GONE
        holder.financial_audit_iv.visibility = if (mDatas[position].isUpdMon == 1) View.VISIBLE else View.GONE

        /**
         * @1 如果需要运营审核和财务审核
         * @2 当不需要财务审核和已经运营审核 不显示
         */
        if (mDatas[position].isUpdCon == 1 && mDatas[position].isUpdMon == 1) {
            holder.review_btn.visibility = if (mDatas[position].yyCheckMan.isNotBlank() && mDatas[position].cwCheckMan.isNotBlank()) View.GONE else View.VISIBLE
        } else if (mDatas[position].isUpdMon == 0) {
            holder.review_btn.visibility = if (mDatas[position].yyCheckMan.isNotBlank()) View.GONE else View.VISIBLE
        } else {
            holder.review_btn.visibility = View.VISIBLE
        }
        holder.review_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                mOnFixedAcceptBillingRecordingInterface?.onReview(v, position, mDatas[position])


            }

        })
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var waybill_number_tv = itemView.findViewById<TextView>(R.id.waybill_number_tv)
        var waybill_time_tv = itemView.findViewById<TextView>(R.id.waybill_time_tv)
        var applicant_tv = itemView.findViewById<TextView>(R.id.applicant_tv)
        var modify_content_tv = itemView.findViewById<TextView>(R.id.modify_content_tv)
        var modify_reason_tv = itemView.findViewById<TextView>(R.id.modify_reason_tv)
        var apply_outlets_tv = itemView.findViewById<TextView>(R.id.apply_outlets_tv)
        var operation_review_tv = itemView.findViewById<TextView>(R.id.operation_review_tv)
        var financial_audit_tv = itemView.findViewById<TextView>(R.id.financial_audit_tv)
        var financial_audit_iv = itemView.findViewById<ImageView>(R.id.financial_audit_iv)
        var operation_review_iv = itemView.findViewById<ImageView>(R.id.operation_review_iv)
        var review_btn = itemView.findViewById<Button>(R.id.review_btn)
        var reject_application_ll = itemView.findViewById<LinearLayout>(R.id.reject_application_ll)
        var father_cl = itemView.findViewById<ConstraintLayout>(R.id.father_cl)
    }
}