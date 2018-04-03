package com.yunkahui.datacubeper.base;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.yunkahui.datacubeper.R;

/**
 * 透明状态栏适配
 */
public abstract class CompatStatusBarActivity extends BaseActivity {

    private FrameLayout mFrameLayoutContent;
    private View mViewStatusBarPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        super.setContentView(R.layout.activity_compat_status_bar);
//
//        mViewStatusBarPlace = findViewById(R.id.view_status_bar_place);
//        mFrameLayoutContent = findViewById(R.id.frame_layout_content_place);
//        ViewGroup.LayoutParams params = mViewStatusBarPlace.getLayoutParams();
//        params.height = getStatusBarHeight();
//        mViewStatusBarPlace.setLayoutParams(params);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        View contentView = LayoutInflater.from(this).inflate(layoutResID, null);
        mFrameLayoutContent.addView(contentView);
    }

    //******** 动态设置状态栏高度 ********
    public int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    //******** 设置沉浸式状态栏 ********
    protected void setImmersiveStatusBar(int statusBarColor) {
        setTranslucentStatus();
        setStatusBarPlaceColor(statusBarColor);
    }

    private void setStatusBarPlaceColor(int statusColor) {
        if (mViewStatusBarPlace != null) {
            mViewStatusBarPlace.setBackgroundColor(statusColor);
        }
    }

    //******** 设置状态栏透明 ********
    private void setTranslucentStatus() {
        //******** 5.0以上系统状态栏透明 ********
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}
