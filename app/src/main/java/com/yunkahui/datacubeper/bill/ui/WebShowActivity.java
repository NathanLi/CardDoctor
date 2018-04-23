package com.yunkahui.datacubeper.bill.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;

/**
 * @author WYF on 2018/4/23/023.
 */
public class WebShowActivity extends AppCompatActivity implements IActivityStatusBar {

    private WebView mWebView;

    @Override protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_web_show);
        super.onCreate(savedInstanceState);
        setTitle("协议");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ((ViewGroup) mWebView.getParent()).removeView(mWebView);
        mWebView.destroy();
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    public void initData() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.loadUrl("file:///android_asset/" + getIntent().getStringExtra("url"));
    }

    @Override
    public void initView() {
        mWebView = findViewById(R.id.show_web);
    }
}

