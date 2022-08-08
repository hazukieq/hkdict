package com.hazukie.testakka.base;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.hazukie.testakka.R;
import com.hazukie.testakka.activities.HanzdetailActivity;
import com.hazukie.testakka.activities.WordetailActivity;
import com.hazukie.testakka.webutils.SpvalueStorage;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;

public class ActcomWeb extends BaseActivity {


    private FrameLayout web_frame;
    //private QMUIEmptyView web_error;
    private QMUITopBarLayout topBarLayout;
    private ProgressBar mProgressBar;
    protected CnWebView mWebView;


    public static final String EXTRA_URL = "EXTRA_URL";

    private final static int PRORESS_PROGRESS = 0;
    private final static int PROGRESS_GONE = 1;

    private String mUrl;
    // private String mTitle;

    private ProgressHandler mProgressHandler;
    private boolean mIsPageFinished = false;
    public boolean NEED_CLEAR = false;

    public String open_url = "https://www.baidu.com";
    public String open_param="";
    public String[] menus=new String[]{"浏览器打开","复制链接","刷新页面"};
    public int[] imgRes=new int[]{R.drawable.ic_action_webview_icon,R.drawable.ic_action_webview_copy,R.drawable.ic_action_webview_refresh};
    public int[] tags=new int[]{0,1,2};
    public boolean isList=false;
    public boolean Bottom_dialog_mode=true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_act_comweb);
        web_frame = (FrameLayout) findViewById(R.id.web_frame);
        topBarLayout = (QMUITopBarLayout) findViewById(R.id.web_topbar);
        customStatus();
        geParams();
        Bundle returns = setArguments();
        if (returns != null) {
            String url = returns.getString(EXTRA_URL);
            if (url != null && url.length() > 0) {
                handleUrl(url);
            }
        }

        initTopbar();
        mProgressBar = (ProgressBar) findViewById(R.id.web_progress);
        mProgressHandler = new ProgressHandler();
        initWebView();

    }

    //控制活动界面状态栏颜色
    public void customStatus() {

    }

    public void geParams() {
        open_url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        open_param=getIntent().getStringExtra("appendParam");
        topBarLayout.setTitle(title);
    }

    public static void startActivityWithLoadUrl(Context context,Class secodeClass, String url,String title,String appendParam) {
        Intent intent = new Intent(context, secodeClass);
        intent.putExtra("url", url);
        intent.putExtra("title",title);
        intent.putExtra("appendParam",appendParam);
        context.startActivity(intent);
    }

    //继承方法后，可自由加载网页链接
    public Bundle setArguments() {
        Bundle bun = new Bundle();
        bun.putString(EXTRA_URL, open_url);
        return bun;
    }

    private void initTopbar() {
        topBarLayout.addLeftBackImageButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mWebView.canGoBack()) {
                    mWebView.goBack();
                } else finish();
            }
        });

        topBarLayout.addRightImageButton(R.drawable.ic_action_more, R.id.web_topbar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Bottom_dialog_mode) controlRightmenus(view);
                else{
                    mWebView.loadUrl("javascript:openModal()");
                }
            }
        });


    }


    public  void hiddenST(boolean isLoad){
        if(isLoad)topBarLayout.setVisibility(View.GONE);
        else topBarLayout.setVisibility(View.VISIBLE);
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
    protected void configWebView(CnWebView webView) {

    }


    private void handleUrl(String url) {
        String decodeUrl;
        try {
            decodeUrl = URLDecoder.decode(url, "utf-8");
        } catch (UnsupportedEncodingException e) {
            decodeUrl = url;
        }
        mUrl = decodeUrl;
    }

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


        //接受网页标题
        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }
    }

    protected class ActcomwebClient extends WebViewClient {
        public ActcomwebClient mWebViewClient;

        public ActcomwebClient() {
        }

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
            if(!view.getSettings().getLoadsImagesAutomatically()){
                view.getSettings().setLoadsImagesAutomatically(true);
            }
            if (mProgressHandler.mDstProgressIndex == 0) {
                sendProgressMessage(PRORESS_PROGRESS, 30, 500);
            }
        }

        //网页加载结束时
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            sendProgressMessage(PROGRESS_GONE, 100, 0);
        }


    }


    @SuppressLint("HandlerLeak")
    private class ProgressHandler extends Handler {

        private int mDstProgressIndex;
        private int mDuration;
        private ObjectAnimator mAnimator;


        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PRORESS_PROGRESS:
                    mIsPageFinished = false;
                    mDstProgressIndex = msg.arg1;
                    mDuration = msg.arg2;
                    mProgressBar.setVisibility(View.VISIBLE);
                    if (mAnimator != null && mAnimator.isRunning()) {
                        mAnimator.cancel();
                    }
                    mAnimator = ObjectAnimator.ofInt(mProgressBar, "progress", mDstProgressIndex);
                    mAnimator.setDuration(mDuration);
                    mAnimator.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            if (mProgressBar.getProgress() == 100) {
                                sendEmptyMessageDelayed(PROGRESS_GONE, 500);
                            }
                        }
                    });
                    mAnimator.start();
                    break;
                case PROGRESS_GONE:
                    mDstProgressIndex = 0;
                    mDuration = 0;
                    mProgressBar.setProgress(0);
                    mProgressBar.setVisibility(View.GONE);
                    if (mAnimator != null && mAnimator.isRunning()) {
                        mAnimator.cancel();
                    }
                    mAnimator = ObjectAnimator.ofInt(mProgressBar, "progress", 0);
                    mAnimator.setDuration(0);
                    mAnimator.removeAllListeners();
                    mIsPageFinished = true;
                    break;
                default:
                    break;
            }
        }
    }


    public RightMenu controlRightmenus(View view){
      return  new RightMenu(view.getContext());
    }

    public class RightMenu{
        private Context context;
        public RightMenu(Context context){
            this.context=context;
           if (isList) setRightList();
           else setRightClicks();
        }


        private void setRightClicks() {
            QMUIBottomSheet.BottomGridSheetBuilder builder = new QMUIBottomSheet.BottomGridSheetBuilder(context);
            for(int i=0;i<menus.length;i++){
                builder.addItem(imgRes[i], menus[i], tags[i], QMUIBottomSheet.BottomGridSheetBuilder.FIRST_LINE);
            }
                    builder.setAddCancelBtn(true)
                    .setSkinManager(QMUISkinManager.defaultInstance(context))
                    .setOnSheetItemClickListener(new QMUIBottomSheet.BottomGridSheetBuilder.OnSheetItemClickListener() {
                        @Override
                        public void onClick(QMUIBottomSheet dialog, View itemView) {
                            dialog.dismiss();
                            int tag = (int) itemView.getTag();
                            controlMenus(tag);
                        }
                    }).build().show();
        }

        private void setRightList() {
           QMUISkinManager QSkinman=QMUISkinManager.defaultInstance(context);

            QMUIBottomSheet.BottomListSheetBuilder builder = new QMUIBottomSheet.BottomListSheetBuilder(context);
            for(int i=0;i<menus.length;i++){
                builder.addItem(menus[i]);
            }
            builder.setAddCancelBtn(true)
                    .setGravityCenter(true)
                    .setAllowDrag(true)
                    .setFitNav(true)
                    .setOnSheetItemClickListener(new QMUIBottomSheet.BottomListSheetBuilder.OnSheetItemClickListener() {
                        @Override
                        public void onClick(QMUIBottomSheet dialog, View itemView, int position, String tag) {
                              controlList(position);
                              dialog.dismiss();
                        }
                    }).build().show();
        }

    public void controlList(int position){

    }

        public void controlMenus(int tags){
            switch (tags) {
                case 0:
                    //Intent intent = new Intent(Intent.ACTION_VIEW);
                    //intent.setData(Uri.parse(open_param));
                    //startActivity(intent);
                    Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData mClipDta = ClipData.newPlainText("Pinhanzs", open_url);
                    cmb.setPrimaryClip(mClipDta);
                    Toast.makeText(context, "已复制网页链接", Toast.LENGTH_SHORT).show();
                    break;
                case 2:
                    mWebView.reload();
                    break;
            }

        }
    }

}
