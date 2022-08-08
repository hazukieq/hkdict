package com.hazukie.testakka.activities;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.hazukie.testakka.R;
import com.hazukie.testakka.base.ActcomWeb;
import com.hazukie.testakka.base.CnWebView;
import com.hazukie.testakka.webutils.Keystatics;
import com.hazukie.testakka.webutils.SpvalueStorage;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import org.w3c.dom.Text;

public class HanzdetailActivity extends ActcomWeb {
    private boolean isCanBack=true;

    public HanzdetailActivity(){
        menus=new String[]{"刷新页面"};
        //imgRes=new int[]{R.drawable.}
        isList=true;
        Bottom_dialog_mode=true;
    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void configWebView(CnWebView webView) {
        super.configWebView(webView);
        mWebView.addJavascriptInterface(new HanzDetails(), "app");
    }

    private static String local_url = "hkapp://";

    @Override
    protected WebViewClient getWebViewClient() {
        return new ActcomwebClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.startsWith(local_url + "reload_/")) {
                        String reload_url = url.replace(local_url + "reload_/", "");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mWebView.loadUrl("javascript:getUrl('" + reload_url + "')");
                            }
                        });
                        return true;
                    }else if(url.equals("hkapp://detail_/")){
                        DialectDetailActivity.startActivityWithLoadUrl(HanzdetailActivity.this  ,DialectDetailActivity.class,"file:///android_asset/dialects/introduce/unihk_introduce.html","通用客家话拼音方案","file:///android_asset/dialect.html");
                        return true;
                    }
                    return false;
                }
            };
        }



        @Override
        public void customStatus() {
            super.customStatus();
            int y=SpvalueStorage.getInt("currentTheme", 0);
            loadNightOrNot(y);
            QMUIStatusBarHelper.translucent(this);
        }

        public void loadNightOrNot(int type){
        if(type==0){
            QMUIStatusBarHelper.setStatusBarLightMode(this);
        }else{
            QMUIStatusBarHelper.setStatusBarDarkMode(this);
        }
    }


    public class HanzDetails {
            @JavascriptInterface
            public void autoLoad() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SpvalueStorage.getInstance(HanzdetailActivity.this);
                        int  cmntype=SpvalueStorage.getInt("cmn_visibles",0);
                        int toney=SpvalueStorage.getInt("cmntone",0);
                        String str=String.format("javascript:getUrl('%s&cmntype=%s&toney=%s')",open_param,cmntype,toney);

                        mWebView.loadUrl(str);
                    }
                });
            }

            @JavascriptInterface
            public String returnAlls(){
                return  returnAllParams();
            }

            @JavascriptInterface
            public int returnHk_type(){
                SpvalueStorage.getInstance(HanzdetailActivity.this);
                int type_=SpvalueStorage.getInt("hk_visibles",1);
                return type_;
            }

            @JavascriptInterface
            public int returnHk_toney(){
                SpvalueStorage.getInstance(HanzdetailActivity.this);
                int toney=SpvalueStorage.getInt("hktone",0);
                return toney;
            }

            @JavascriptInterface
            public void showsource(String ltr){
                showMessagePositiveDialog(ltr,HanzdetailActivity.this);
            }
    }



    @Override
    public RightMenu controlRightmenus(View view) {
        return new RightMenu(view.getContext()){
            @Override
            public void controlList(int tags) {
                switch (tags){
                    case 0:
                        mWebView.reload();
                        break;
                    default:
                        break;
                }
            }
        };
    }

    public String returnAllParams(){
        SpvalueStorage.getInstance(HanzdetailActivity.this);
        String alls;
        String sv = "",kv="",hv="",lv="";

        for(int g=0;g<Keystatics.item_listkeys.length;g++){
            int vars=1;
            if(g==0) vars=0;
            sv+=SpvalueStorage.getInt("sw"+ Keystatics.item_listkeys[g],vars)+",";
            kv+=SpvalueStorage.getInt("kx"+ Keystatics.item_listkeys[g],vars)+",";
            hv+=SpvalueStorage.getInt("hd"+ Keystatics.item_listkeys[g],vars)+",";
            lv+=SpvalueStorage.getInt("ltc"+ Keystatics.item_listkeys[g],vars)+",";
        }
        alls=sv+kv+hv+lv;
        return alls;
    }

    private void showMessagePositiveDialog(String str,Context getActivity) {

        new QMUIDialog.MessageDialogBuilder(getActivity)
                .setTitle("数据来源")
                .setMessage(Html.fromHtml(str))
                .addAction(0, "关闭", QMUIDialogAction.ACTION_PROP_POSITIVE, new QMUIDialogAction.ActionListener() {
                    @Override
                    public void onClick(QMUIDialog dialog, int index) {
                        dialog.dismiss();
                    }
                })
                .show();
    }
}
