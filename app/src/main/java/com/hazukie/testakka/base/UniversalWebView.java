package com.hazukie.testakka.base;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ZoomButtonsController;

import com.hazukie.testakka.R;
import com.qmuiteam.qmui.widget.QMUIEmptyView;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UniversalWebView#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UniversalWebView extends Fragment {

    private FrameLayout web_frame;
    protected CnWebView mWebView;
    public SharedPreferences sp;
    public SharedPreferences.Editor editor;


    private String mUrl;

    private boolean mIsPageFinished=false;
    public boolean NEED_CLEAR=false;
    private boolean isAdd=false;

    public String open_url="https://www.baidu.com";


    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    public UniversalWebView() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UniversalWebView.
     */

    public static UniversalWebView newInstance(String param1, String param2) {
        UniversalWebView fragment = new UniversalWebView();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root=inflater.inflate(R.layout.universal_web_view, container, false);
        web_frame=root.findViewById(R.id.web_frame);
        sp= PreferenceManager.getDefaultSharedPreferences(getContext());
        editor=sp.edit();

        if(open_url!=null&&open_url.length()>0) handleUrl(open_url);

        initWebView();
        return root;
    }




    protected void initWebView(){
        mWebView=new CnWebView(getActivity());
        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        web_frame.addView(mWebView,params);

        mWebView.setWebChromeClient(getWebViewChromeClient());
        mWebView.setWebViewClient(getWebViewClient());
        //隐藏滚动条
        mWebView.setVerticalScrollBarEnabled(false);
        mWebView.requestFocus(View.FOCUS_DOWN);
        //setZoomControlGone(mWebView);
        configWebView(mWebView);
        mWebView.loadUrl(mUrl);

        onKeyDown();
    }


    private void onKeyDown(){
        mWebView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==KeyEvent.KEYCODE_BACK&&mWebView.canGoBack()){
                    if(keyEvent.getAction()==KeyEvent.ACTION_DOWN){
                        mWebView.goBack();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    //可以通过改方法配置网页设置
    protected void configWebView(CnWebView webView){

    }


    private void handleUrl(String url){
        String decodeUrl;
        try{
            decodeUrl= URLDecoder.decode(url,"utf-8");
        }catch (UnsupportedEncodingException e){
            decodeUrl=url;
        }
        mUrl=decodeUrl;
    }

    protected WebChromeClient getWebViewChromeClient(){
        return new UniwebChromeClient(this);
    }

    protected WebViewClient getWebViewClient(){
        return new UniwebClient();
    }

    @Override
    public void onStop() {
        if(mWebView!=null) mWebView.getSettings().setJavaScriptEnabled(false);
        super.onStop();
    }

    @Override
    public void onResume() {
        if(mWebView!=null) mWebView.getSettings().setJavaScriptEnabled(true);
        super.onResume();
    }

    /*@Override
    public void onDestroy() {
        //销毁WebView组件，防止内存泄露
        if(mWebView!=null){
            mWebView.loadDataWithBaseURL(null,"","text/html","utf-8",null);
            web_frame.removeView(mWebView);
            mWebView.destroy();
            mWebView=null;
        }
        super.onDestroy();
    }*/



    public static class UniwebChromeClient extends WebChromeClient{
        private UniversalWebView mActivity;

        public UniwebChromeClient(UniversalWebView webFrag){
           mActivity=webFrag;
        }
    }

    protected class UniwebClient extends  WebViewClient{

        public UniwebClient(){ }
        @Override
        public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
            super.doUpdateVisitedHistory(view, url, isReload);
            //清空错误代码加载成功后之前保留的后台栈历史
             view.clearHistory();
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if(!view.getSettings().getLoadsImagesAutomatically()){
                view.getSettings().setLoadsImagesAutomatically(true);
            }
        }

    }
}
