package com.mbcq.vehicleslibrary.fragment.shortfeederhouse.event

import com.mbcq.vehicleslibrary.fragment.shortfeederhouse.bean.ShortFeederHouseListBean

/**
 * 1 添加
 * 2 移出
 */
data class ShortFeederHouseInventoryListEvent(var type: Int, var list: List<ShortFeederHouseListBean>)