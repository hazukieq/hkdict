package com.hazukie.testakka.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.hazukie.testakka.R;
import com.qmuiteam.qmui.util.QMUIDisplayHelper;
import com.qmuiteam.qmui.util.QMUIPackageHelper;

import java.io.File;

public class CnWebView extends WebView {

    public CnWebView(Context context) {
        this(context, null);
        init(context);
    }

    public CnWebView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.webViewStyle);
        init(context);
    }

    public CnWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void init(Context context) {
        setBackgroundColor(Color.TRANSPARENT);
        setBackgroundResource(R.color.white);

        //隐藏滚动条
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        setOverScrollMode(View.OVER_SCROLL_NEVER);//取消WebView中混动或拖动到顶部、底部时的阴影
        setNestedScrollingEnabled(false);//

        WebSettings webSettings = getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.setSupportMultipleWindows(false);

        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls(false);
        webSettings.setDisplayZoomControls(false);
        webSettings.setUseWideViewPort(false);


        webSettings.setAllowFileAccess(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);

        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setBlockNetworkLoads(false);
        webSettings.setDomStorageEnabled(true);

        webSettings.setDefaultTextEncodingName("utf-8");
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);


        webSettings.setTextZoom(100);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);

        //set web cache strategy
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);


        //启动硬件加速
        setLayerType(View.LAYER_TYPE_HARDWARE,null);
    }
}
