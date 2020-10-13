package com.mbcq.commonlibrary

import android.hardware.Camera
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.BaseActivity
import com.mbcq.baselibrary.view.SingleClick
import kotlinx.android.synthetic.main.activity_take_photos.*
import java.io.IOException

@Route(path = ARouterConstants.TakePhotosActivity)
class TakePhotosActivity :BaseActivity(){

    override fun getLayoutId(): Int =R.layout.activity_take_photos

}