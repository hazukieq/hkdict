package com.hazukie.testakka.activities;

import android.annotation.SuppressLint;
import android.view.KeyEvent;
import android.webkit.JavascriptInterface;

import com.hazukie.testakka.base.ActcomWeb;
import com.hazukie.testakka.base.CnWebView;
import com.hazukie.testakka.infoutils.Dialogsheet;
import com.hazukie.testakka.webutils.Keystatics;
import com.hazukie.testakka.webutils.SpvalueStorage;
import com.qmuiteam.qmui.widget.QMUITopBarLayout;

public class SettingActivity extends ActcomWeb {

    @Override
    public void customStatus(QMUITopBarLayout topBarLayout) {
        setLightOrDarkStatusBar(1);
        hiddenStatusBar(true);
    }


    @SuppressLint("JavascriptInterface")
    @Override
    protected void configWebView(CnWebView webView) {
        super.configWebView(webView);
        webView.addJavascriptInterface(new Settings(), "app");

    }


    public class Settings {
        //private String retr = "";

        @JavascriptInterface
        public void showaloh(int key, String name, String appendStr) {
            runOnUiThread(()->
                    new Dialogsheet(SettingActivity.this,
                            (position, tag, selectedValue) -> {
                        SpvalueStorage.getInstance(SettingActivity.this);
                        SpvalueStorage.setIntValue(tag, position);
                        loadJSfunction(tag, selectedValue);
                    })
                            .showBottom(key, "", Keystatics.getStatics(name), false, true, appendStr)
            );
        }

        @JavascriptInterface
        public void setSwitch(int id,int s){
            SpvalueStorage.getInstance(SettingActivity.this);
            SpvalueStorage.setIntValue(Keystatics.keys[id],s);
            }

            @JavascriptInterface
            public void setParam(int ty){
            SpvalueStorage.getInstance(SettingActivity.this);
            SpvalueStorage.setIntValue("cmntone",ty);
            }

        @JavascriptInterface
        public void callSwics(){
            String values=autoloadSwics();
             runOnUiThread(() -> mWebView.loadUrl("javascript:loadSwitchs("+values+")"));
        }

        @JavascriptInterface
        public void loadSingleprams(String name, int type) {
            runOnUiThread(() -> {
                String  mUrl=returnSpValues(name,type);
                mWebView.loadUrl(mUrl);
            });
        }

        @JavascriptInterface
        public void loadBigValues(String nam){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                   String y=returnListValues(nam);
                   mWebView.loadUrl(y);
                }
            });
        }

        @JavascriptInterface
        public void finic() {
            finish();
        }
    }

    public void loadJSfunction(String tag, String selectedValue) {
        runOnUiThread(() -> mWebView.loadUrl("javascript:updateSingleValue('" + tag + "','" + selectedValue + "')"));
    }

    public String returnSpValues(String name,int type) {
        SpvalueStorage.getInstance(SettingActivity.this);
        String restr = "javascript:generateLists('" + name + "','";
        String tag="";
        String _visi="";
        String _tone ="";
        switch (name){
            case "item_hk":
                tag="hk";
                _visi = Keystatics.hk_visibles[SpvalueStorage.getInt(tag + Keystatics.hkcmn[0], 1)];
                _tone = Keystatics.tones[SpvalueStorage.getInt(tag+Keystatics.hkcmn[1], 0)];
                break;
            case "item_cmn":
                tag="cmn";
                _visi = Keystatics.cmn_visibles[SpvalueStorage.getInt(tag + Keystatics.hkcmn[0], 1)];
                  if(_visi.equals("汉语拼音")) _tone = Keystatics.special_tones[SpvalueStorage.getInt(tag+Keystatics.hkcmn[1], 0)];
                else{
                    int _t=SpvalueStorage.getInt(tag+Keystatics.hkcmn[1], 0);
                    if(_t==3) _t=2;
                    _tone = Keystatics.tones[_t];
                }
                break;
        }
        restr +=_visi + "','" + _tone + "'," + type + ")";
        return restr;
    }

    public String returnListValues(String name){
            SpvalueStorage.getInstance(SettingActivity.this);
            String restr = "javascript:generateBigLists('" + name + "',";
            String tag="";
            switch (name){
                case "item_hk":
                    break;
                case "item_cmn":
                    break;
                case "item_sw":
                    tag="sw";
                    break;
                case "item_kx":
                    tag="kx";
                    break;
                case "item_hd":
                    tag="hd";
                    break;
                case "item_ltc":
                    tag="ltc";
                    break;
            }

            int _drt=SpvalueStorage.getInt(tag+Keystatics.item_listkeys[0],0);
            int _ft=SpvalueStorage.getInt(tag+Keystatics.item_listkeys[1],1);
            int _lt=SpvalueStorage.getInt(tag+Keystatics.item_listkeys[2],1);
            int _lh=SpvalueStorage.getInt(tag+Keystatics.item_listkeys[3],1);
            //int _pl=SpvalueStorage.getInt(tag+Keystatics.item_listkeys[4],1);
            int _bg=SpvalueStorage.getInt(tag+Keystatics.item_listkeys[4],1);

            restr+="'"+tag+"',"+_drt+","+_ft+","+_lt+","+_lh+","+_bg+")";
            return restr;
        }

    public  String  autoloadSwics(){
        SpvalueStorage.getInstance(SettingActivity.this);
        int isOffline=SpvalueStorage.getInt(Keystatics.keys[0],0);
        int isTabs=SpvalueStorage.getInt(Keystatics.keys[1], 1);
        return isOffline+","+isTabs+",";
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
