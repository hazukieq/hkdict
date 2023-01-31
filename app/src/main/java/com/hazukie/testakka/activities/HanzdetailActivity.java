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

import com.google.gson.Gson;
import com.hazukie.testakka.R;
import com.hazukie.testakka.base.ActcomWeb;
import com.hazukie.testakka.base.CnWebView;
import com.hazukie.testakka.database.DatabaseObserver;
import com.hazukie.testakka.database.Hkdatabase;
import com.hazukie.testakka.database.QueryHanzTask;
import com.hazukie.testakka.infoutils.BottomSheet;
import com.hazukie.testakka.models.Hkhan_model;
import com.hazukie.testakka.webutils.Keystatics;
import com.hazukie.testakka.webutils.SpvalueStorage;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;
import com.qmuiteam.qmui.widget.dialog.QMUIBottomSheet;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.qmuiteam.qmui.widget.dialog.QMUIDialogAction;

import org.w3c.dom.Text;

import java.util.List;

import kotlin.HashCodeKt;

public class HanzdetailActivity extends ActcomWeb {
    private static String local_url = "hkapp://";

    @SuppressLint("JavascriptInterface")
    @Override
    protected void configWebView(CnWebView webView) {
        super.configWebView(webView);
        mWebView.addJavascriptInterface(new HanzDetails(), "app");
    }


    @Override
    protected WebViewClient getWebViewClient() {
        return new ActcomwebClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (url.startsWith(local_url + "reload_/")) {
                        String reload_url = url.replace(local_url + "reload_/", "");
                        runOnUiThread(() -> mWebView.loadUrl("javascript:getUrl('" + reload_url + "')"));
                        return true;
                    }
                    return false;
                }
            };
        }



        @Override
        public void customStatus(QMUITopBarLayout topBarLayout) {

            setLightOrDarkStatusBar(1);
            topBarLayout
                    .addLeftBackImageButton()
                    .setOnClickListener(view -> {
                        if (mWebView.canGoBack()) mWebView.goBack();
                        else finish();
                    });

            topBarLayout
                    .addRightImageButton(R.drawable.ic_action_more, R.id.web_topbar)
                    .setOnClickListener(view -> {
                        //网页菜单
                        //controlRightmenus(view);
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
            public void offlineLoad(){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String str=String.format("javascript:getUrl('%s')",open_param);
                        mWebView.loadUrl(str);
                    }
                });
            }

            @JavascriptInterface
            public void AsyncJSON(){
                Hkdatabase hkdatabase=Hkdatabase.getInstance(HanzdetailActivity.this);
                new QueryHanzTask(hkdatabase, new DatabaseObserver() {
                    @Override
                    public void callbackOfMsg(List<Hkhan_model> datas) {
                        if(datas.size()>=1){
                            if(datas.get(0).hz=="..."){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mWebView.loadUrl("javascript:card_skeleton(2,'"+open_param+"')");

                                    }
                                });
                            }else{
                                Gson gson=new Gson();
                                String jsons=gson.toJson(datas.get(0));


                                if(!jsons.contains("va")){
                                    jsons=jsons.replace("}",",\"va\":\"\"}");
                                }

                                if(!jsons.contains("hd")){
                                    jsons=jsons.replace("}",",\"hd\":\"\"}");
                                }

                                if(!jsons.contains("jetd_gajin_hk")){
                                    jsons=jsons.replace("}",",\"jetd_gajin_hk\":\"\"}");
                                }

                                if(!jsons.contains("cmn_p")){
                                    jsons=jsons.replace("}",",\"cmn_p\":\"\"}");
                                }

                                String jsons_=jsons;
                                //Log.i("onHanzdetailActivity_AsyncJson>>",jsons);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        mWebView.loadUrl("javascript:mergeCardLayout("+jsons_+")");
                                    }
                                });
                            }
                        }
                    }
                }, open_param).execute();
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
                runOnUiThread(() -> showMessagePositiveDialog(ltr,HanzdetailActivity.this));

            }
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
