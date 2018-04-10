package com.yunkahui.datacubeper.common.utils;

import android.app.Activity;
import android.widget.ImageView;

import com.lzy.imagepicker.loader.ImageLoader;
import com.yunkahui.datacubeper.GlideApp;

/**
 * Created by Administrator on 2018/4/10.
 */

public class ImagePickerGlideLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        GlideApp.with(activity).load(path).thumbnail(0.1f).into(imageView);
    }

    @Override
    public void displayImagePreview(Activity activity, String path, ImageView imageView, int width, int height) {
        GlideApp.with(activity).load(path).thumbnail(0.1f).into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
