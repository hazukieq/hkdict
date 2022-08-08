package com.hazukie.testakka.activities;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.hazukie.testakka.R;
import com.hazukie.testakka.base.ActcomWeb;
import com.hazukie.testakka.base.CnWebView;
import com.hazukie.testakka.webutils.SpvalueStorage;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

public class WordetailActivity extends ActcomWeb {
    private static int[] dRes = new int[]{R.drawable.ic_action_webview_copy};
    private static String[] menus = new String[]{
            "页面设置"
    };
    private static int[] tags = new int[]{
            4
    };

    @SuppressLint("JavascriptInterface")
    @Override
    protected void configWebView(CnWebView webView) {
        super.configWebView(webView);
    }

    private static String host_url = "http://dict.hazukieq.top/words/lwordetail/";
    private static String local_url = "hkapp://";

    @Override
    protected WebViewClient getWebViewClient() {
        return new ActcomwebClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(host_url)) {
                    WordetailActivity.startActivityWithLoadUrl(WordetailActivity.this,WordetailActivity.class, url, "词汇详情", "");
                    return true;
                }

                return false;
            }
        };
    }
    @Override
    public void customStatus() {
        super.customStatus();
        //int y= SpvalueStorage.getInt("currentTheme", 0);
        //loadNightOrNot(y);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        QMUIStatusBarHelper.translucent(this);
    }

    public void loadNightOrNot(int type){
        if(type==0){
            QMUIStatusBarHelper.setStatusBarLightMode(this);
        }else{
            QMUIStatusBarHelper.setStatusBarDarkMode(this);

        }

    }

}
