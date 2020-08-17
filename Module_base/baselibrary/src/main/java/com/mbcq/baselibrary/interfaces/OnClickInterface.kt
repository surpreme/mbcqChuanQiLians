package com.mbcq.baselibrary.interfaces

import android.view.View

class OnClickInterface {
    interface OnRecyclerClickInterface {
        fun onItemClick(v: View, position: Int,result:String)
    }

    //toolbar返回键
    interface OnToolBarClickInterface {
        fun back()
        fun rightClick(v: View)
    }
}