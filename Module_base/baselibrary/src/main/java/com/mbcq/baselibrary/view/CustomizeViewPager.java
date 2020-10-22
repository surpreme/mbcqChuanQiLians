package com.mbcq.baselibrary.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class CustomizeViewPager extends ViewPager {

    public CustomizeViewPager(@NonNull Context context) {
        super(context);
    }

    public CustomizeViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean canScroll(View v, boolean checkV, int dx, int x, int y) {
        if (v.getClass().getName().equals("com.amap.api.maps.MapView"))
            return true;
        return super.canScroll(v, checkV, dx, x, y);
    }
}
