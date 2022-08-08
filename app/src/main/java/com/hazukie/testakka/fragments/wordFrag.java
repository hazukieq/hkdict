package com.hazukie.testakka.fragments;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hazukie.testakka.activities.WordetailActivity;
import com.hazukie.testakka.base.CnWebView;
import com.hazukie.testakka.base.UniversalWebView;
import com.hazukie.testakka.webutils.Keystatics;

public class wordFrag extends UniversalWebView {
    private static String host_url="http://dict.hazukieq.top/";
    public wordFrag(String url){
        open_url=url;
    }

    @Override
    protected void configWebView(CnWebView webView) {
        super.configWebView(webView);
    }

    @Override
    protected WebViewClient getWebViewClient() {
        return new UniwebClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.startsWith(host_url+"words/lwordse.html?secaid")){
                    String sid=url.replace(host_url+"words/lwordse.html?secaid=","");
                    WordetailActivity.startActivityWithLoadUrl(getActivity(),WordetailActivity.class,url,"词汇分类","");
                    return true;
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        };

    }
}
