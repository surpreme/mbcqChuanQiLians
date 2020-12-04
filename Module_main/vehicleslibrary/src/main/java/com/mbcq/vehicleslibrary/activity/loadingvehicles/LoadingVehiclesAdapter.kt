package com.mbcq.vehicleslibrary.activity.loadingvehicles

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.view.BaseRecyclerAdapter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.vehicleslibrary.R
import com.mbcq.vehicleslibrary.fragment.shortfeeder.ShortFeederAdapter

class LoadingVehiclesAdapter(context: Context) : BaseRecyclerAdapter<LoadingVehiclesBean>(context) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = ItemViewHolder(inflater.inflate(R.layout.item_loading_vehiclesshort_feeder, parent, false))

    var mDeleteClickInterface: OnClickInterface.OnRecyclerDeleteClickInterface? = null

    /**
     * 发车
     */
    var mDepartureClickInterface: OnClickInterface.OnRecyclerClickInterface? = null

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ItemViewHolder).departure_number_tv.text = mDatas[position].inoneVehicleFlag
        holder.loading_type_tv.text = if (mDatas[position].type == 0) "短驳" else "干线"
        holder.short_feeder_time_tv.text = mDatas[position].sendDate
        holder.shipper_outlets_tv.text = mDatas[position].webidCodeStr
        holder.receiver_outlets_tv.text = mDatas[position].ewebidCodeStr
        holder.feeder_state_tv.text = mDatas[position].vehicleStateStr
        holder.scan_rate_tv.text = "扫描率：${if (mDatas[position].scanPercentage.isNullOrBlank()) "0" else mDatas[position].scanPercentage}%"
        holder.operating_progressbar.progress = if (!mDatas[position].scanPercentage.isNullOrBlank() && mDatas[position].scanPercentage != "null") mDatas[position].scanPercentage.toInt() else 0
        holder.pre_installed_tv.visibility = if (mDatas[position].isScan == 2) View.VISIBLE else View.GONE
        holder.operating_ll.visibility = if (mDatas[position].isScan == 2) View.VISIBLE else View.GONE
        //***
//        holder.pre_installed_tv.visibility = if (mDatas[position].vehicleStateStr != "发货") View.VISIBLE else View.GONE
//        holder.operating_ll.visibility = if (mDatas[position].vehicleStateStr != "发货") View.VISIBLE else View.GONE
        //***
        holder.vehicler_info_tv.text = "${mDatas[position].vehicleNo} ${mDatas[position].chauffer} ${mDatas[position].chaufferMb}"
        holder.delete_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                mDeleteClickInterface?.onDelete(v, position, Gson().toJson(mDatas[position]))
            }

        })
        holder.post_vehicles_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                mDepartureClickInterface?.onItemClick(v, position, Gson().toJson(mDatas[position]))
            }

        })
        holder.itemView.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                mClickInterface?.onItemClick(v, position, Gson().toJson(mDatas[position]))
            }

        })
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var operating_progressbar: ProgressBar = itemView.findViewById(R.id.operating_progressbar)
        var operating_ll: LinearLayout = itemView.findViewById(R.id.operating_ll)
        var departure_number_tv: TextView = itemView.findViewById(R.id.departure_number_tv)
        var short_feeder_time_tv: TextView = itemView.findViewById(R.id.short_feeder_time_tv)
        var shipper_outlets_tv: TextView = itemView.findViewById(R.id.shipper_outlets_tv)
        var vehicler_info_tv: TextView = itemView.findViewById(R.id.vehicler_info_tv)
        var feeder_state_tv: TextView = itemView.findViewById(R.id.feeder_state_tv)
        var information_tv: TextView = itemView.findViewById(R.id.information_tv)
        var receiver_outlets_tv: TextView = itemView.findViewById(R.id.receiver_outlets_tv)
        var loading_type_tv: TextView = itemView.findViewById(R.id.loading_type_tv)
        var pre_installed_tv: TextView = itemView.findViewById(R.id.pre_installed_tv)
        var scan_rate_tv: TextView = itemView.findViewById(R.id.scan_rate_tv)
        var delete_tv: TextView = itemView.findViewById(R.id.delete_tv)
        var post_vehicles_tv: TextView = itemView.findViewById(R.id.post_vehicles_tv)
    }
}