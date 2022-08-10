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
import com.hazukie.testakka.infoutils.BottomSheet;
import com.hazukie.testakka.webutils.SpvalueStorage;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;

public class WordetailActivity extends ActcomWeb {
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
    public void customStatus(QMUITopBarLayout topBarLayout) {
        setLightOrDarkStatusBar(1);
        topBarLayout.addLeftBackImageButton().setOnClickListener(view -> {
            if (mWebView.canGoBack()) mWebView.goBack();
            else finish();
        });

        topBarLayout.addRightImageButton(R.drawable.ic_action_more, R.id.web_topbar).setOnClickListener(view -> {
            //网页菜单
            new BottomSheet(view.getContext(), new BottomSheet.Clicks() {
                @Override
                public void controlVerticalList(int position) {
                    switch (position){
                        case 0:
                            mWebView.reload();
                            break;
                        default:
                            break;
                    }
                }
                @Override
                public void controlHorizontalList(int tag) {}
            }).setVerticalList(new String[]{"刷新页面"});
        });
    }

}
