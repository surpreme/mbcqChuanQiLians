package com.mbcq.commonlibrary.adapter;

import android.net.Uri;


public class ImageViewBean {
    private Uri imageUris;
    private String tag="";

    public Uri getImageUris() {
        return imageUris;
    }

    public void setImageUris(Uri imageUris) {
        this.imageUris = imageUris;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
