package com.yunkahui.datacubeper.share.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.just.agentweb.AgentWeb;
import com.just.agentweb.DefaultWebClient;
import com.yunkahui.datacubeper.R;
import com.yunkahui.datacubeper.base.IActivityStatusBar;
import com.yunkahui.datacubeper.common.utils.LogUtils;

/**
 * Created by YD1 on 2018/4/11
 */
public class WebViewActivity extends AppCompatActivity implements IActivityStatusBar {

    private AgentWeb mAgentWeb;
    private LinearLayout mLinearLayoutWebView;
    private WebView mWebView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_web_view);
        super.onCreate(savedInstanceState);

        String title = getIntent().getStringExtra("title");
        setTitle(title);
    }

    @Override
    public void initData() {
        String url = getIntent().getStringExtra("url");
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent(mLinearLayoutWebView, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
                .useDefaultIndicator(-1, 3)
                .setWebViewClient(mWebViewClient)
                .setWebChromeClient(mWebChromeClient)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.DISALLOW)
                .interceptUnkownUrl()
                .createAgentWeb()
                .ready()
                .go(url);
        mWebView = mAgentWeb.getWebCreator().getWebView();

        if(url.contains("<html>")){
            mWebView.loadDataWithBaseURL(null, url.toString(), "text/html" , "utf-8", null);
        }

        mAgentWeb.getWebCreator().getWebView().setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            LogUtils.e("当前网页-->" + url);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }
    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
        }
    };

    @Override
    public void onBackPressed() {
        if (!mAgentWeb.back()) {
            finish();
        }
    }

    @Override
    public void initView() {
        mLinearLayoutWebView = findViewById(R.id.linear_layout_webview);
    }

    @Override
    public int getStatusBarColor() {
        return getResources().getColor(R.color.colorPrimary);
    }

    @Override
    protected void onResume() {
        mAgentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mAgentWeb.getWebLifeCycle().onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mAgentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }
}

