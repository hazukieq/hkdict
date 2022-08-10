package com.hazukie.testakka.base;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import com.hazukie.testakka.R;
import com.hazukie.testakka.webutils.SpvalueStorage;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class ActcomWeb extends BaseActivity {


    private FrameLayout web_frame;
    private QMUITopBarLayout topBarLayout;
    private ProgressBar mProgressBar;
    protected CnWebView mWebView;

    private final static int PRORESS_PROGRESS = 0;
    private final static int PROGRESS_GONE = 1;

    private ProgressHandler mProgressHandler;
    public boolean NEED_CLEAR = false;

    //默认链接
    public String open_url = "https://www.baidu.com";
    //传递参数
    public String open_param="";
    private String mUrl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_act_comweb);
        web_frame = findViewById(R.id.web_frame);
        topBarLayout = findViewById(R.id.web_topbar);
        initTopbar();

        getArguments();
        if (open_url != null && open_url.length() > 0) handleUrl(open_url);

        mProgressBar = findViewById(R.id.web_progress);
        mProgressHandler = new ProgressHandler(mProgressBar);
        initWebView();
    }

    /**
     * @param TYPE 0代表白色文字状态栏，1代表黑色文字状态栏
     */
    public void setLightOrDarkStatusBar(int TYPE){
        QMUIStatusBarHelper.translucent(this);
        if(TYPE==1){
            QMUIStatusBarHelper.setStatusBarLightMode(this);
        }
        else{
            QMUIStatusBarHelper.setStatusBarDarkMode(this);
        }
    }

    //控制活动界面状态栏颜色
    public void customStatus(QMUITopBarLayout mTopBarLayout) { }


    //读取网页链接
    private void getArguments() {
        Intent in_=getIntent();
        open_url = in_.getStringExtra("url");
        String title = in_.getStringExtra("title");
        open_param=in_.getStringExtra("appendParam");
        topBarLayout.setTitle(title);
    }

    /**
     * @param context 当前活动 Context
     * @param secondeClass 目标活动名字
     * @param url 加载网页链接
     * @param title 网页标题
     * @param appendParam 附加参数
     */
    public static void startActivityWithLoadUrl(Context context,Class secondeClass, String url,String title,String appendParam) {
        Intent intent = new Intent(context, secondeClass);
        intent.putExtra("url", url);
        intent.putExtra("title",title);
        intent.putExtra("appendParam",appendParam);
        context.startActivity(intent);
    }


    //初始化状态栏
    private void initTopbar() {
        customStatus(topBarLayout);
    }


    //是否隐藏状态栏
    public  void hiddenStatusBar(boolean isLoad){
        if(isLoad)topBarLayout.setVisibility(View.GONE);
        else topBarLayout.setVisibility(View.VISIBLE);
    }


    //处理网页链接编码
    private void handleUrl(String url) {
        String decodeUrl;
        try {
            decodeUrl = URLDecoder.decode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            decodeUrl = url;
        }
        mUrl = decodeUrl;
    }

    protected void initWebView() {
        mWebView = new CnWebView(getApplicationContext());
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        web_frame.addView(mWebView, params);

        mWebView.setWebChromeClient(getWebViewChromeClient());
        mWebView.setWebViewClient(getWebViewClient());
        //隐藏滚动条
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.setHorizontalScrollBarEnabled(false);
        mWebView.requestFocus(View.FOCUS_DOWN);
        configWebView(mWebView);

        //设置网页初始化背景颜色
        SpvalueStorage.getInstance(this);
        int s=SpvalueStorage.getInt("currentTheme",0);
        switch (s){
            case 0:
                mWebView.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
            case 1:
                mWebView.setBackgroundColor(Color.parseColor("#0f1421"));
                break;
        }
        mWebView.loadUrl(mUrl);


    }


    //可以通过改方法配置网页设置
    protected void configWebView(CnWebView webView) {}

    protected WebChromeClient getWebViewChromeClient() {
        return new ActcomwebChromeClient(this);
    }

    protected WebViewClient getWebViewClient() {
        return new ActcomwebClient();
    }

    private void sendProgressMessage(int progressType, int progress, int duration) {
        Message msg = new Message();
        msg.what = progressType;
        msg.arg1 = progress;
        msg.arg2 = duration;
        mProgressHandler.sendMessage(msg);

    }

    public static class ActcomwebChromeClient extends WebChromeClient {
        private ActcomWeb mActivity;

        public ActcomwebChromeClient(ActcomWeb activity) {
            mActivity = activity;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            // 修改进度条
            if (newProgress > mActivity.mProgressHandler.mDstProgressIndex) {
                mActivity.sendProgressMessage(PRORESS_PROGRESS, newProgress, 100);
            }
        }
    }

    protected class ActcomwebClient extends WebViewClient {
        public ActcomwebClient() {}

        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
            //清空错误代码加载成功后之前保留的后台栈历史
            if (NEED_CLEAR) view.clearHistory();
        }

        //网页开始加载时
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (mProgressHandler.mDstProgressIndex == 0) {
                sendProgressMessage(PRORESS_PROGRESS, 30, 500);
            }
        }

        //网页加载结束时
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(!view.getSettings().getLoadsImagesAutomatically()){
                view.getSettings().setLoadsImagesAutomatically(true);
            }
            sendProgressMessage(PROGRESS_GONE, 100, 0);
        }


    }


    @Override
    protected void onStop() {
        mWebView.getSettings().setJavaScriptEnabled(false);
        super.onStop();
    }


    @Override
    protected void onResume() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //销毁WebView组件，防止内存泄露
        if (mWebView != null) {
            mWebView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            web_frame.removeView(mWebView);
            mWebView.destroy();
            mWebView = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
