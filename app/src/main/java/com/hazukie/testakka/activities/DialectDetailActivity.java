package com.hazukie.testakka.activities;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hazukie.testakka.R;
import com.hazukie.testakka.base.ActcomWeb;

import com.hazukie.testakka.infoutils.BottomSheet;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

public class DialectDetailActivity extends ActcomWeb {
    private static final String host_url = "https://www.hazukieq.top/";


    @Override
    protected WebViewClient getWebViewClient() {
        return new ActcomwebClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith(host_url)) {
                    WordetailActivity.startActivityWithLoadUrl(DialectDetailActivity.this, DialectDetailActivity.class, url, "", "");
                    return true;
                } else if (url.startsWith("hkpages")) {
                    super.shouldOverrideUrlLoading(view, url);
                    return true;
                }
                return false;
            }
        };
    }

    @Override
    public void customStatus(QMUITopBarLayout topBarLayout) {
        setLightOrDarkStatusBar(1);
        hiddenStatusBar(true);
       /* topBarLayout.addLeftBackImageButton().setOnClickListener(view -> {
            if (mWebView.canGoBack()) mWebView.goBack();
            else finish();
        });

        topBarLayout.addRightImageButton(R.drawable.ic_action_more, R.id.web_topbar).setOnClickListener(view -> {
            //网页菜单
            new BottomSheet(view.getContext(), new BottomSheet.Clicks() {
                @Override
                public void controlVerticalList(int position) {
                    if (position == 0) {
                        mWebView.reload();
                    }
                }
                @Override
                public void controlHorizontalList(int tag) {}
            }).setVerticalList(new String[]{"刷新页面"});
        });*/
    }
}
