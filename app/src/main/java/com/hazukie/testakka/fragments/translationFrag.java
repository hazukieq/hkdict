package com.hazukie.testakka.fragments;

import com.hazukie.testakka.base.CnWebView;
import com.hazukie.testakka.base.UniversalWebView;
import com.hazukie.testakka.webutils.Keystatics;

public class translationFrag extends UniversalWebView {
    public translationFrag(String url){
        open_url=url;

    }

    @Override
    protected void configWebView(CnWebView webView) {
        super.configWebView(webView);

    }
}
