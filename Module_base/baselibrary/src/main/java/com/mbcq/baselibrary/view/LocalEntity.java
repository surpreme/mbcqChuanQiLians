package com.mbcq.baselibrary.view;

import com.flyco.tablayout.listener.CustomTabEntity;

public class LocalEntity implements CustomTabEntity {
    public LocalEntity(String title) {
        this.title = title;
    }

    public String title;

    @Override
    public String getTabTitle() {
        return title;
    }

    @Override
    public int getTabSelectedIcon() {
        return 0;
    }

    @Override
    public int getTabUnselectedIcon() {
        return 0;
    }
}
