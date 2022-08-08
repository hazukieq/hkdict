package com.hazukie.testakka.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.hazukie.testakka.base.ActcomWeb;
import com.hazukie.testakka.base.CnWebView;
import com.hazukie.testakka.infoutils.Dialogsheet;
import com.hazukie.testakka.infoutils.EventMsg;
import com.hazukie.testakka.webutils.Keystatics;
import com.hazukie.testakka.webutils.SpvalueStorage;
import com.qmuiteam.qmui.skin.QMUISkinManager;
import com.qmuiteam.qmui.util.QMUIStatusBarHelper;

import org.greenrobot.eventbus.EventBus;

public class SettingActivity extends ActcomWeb {

    public SettingActivity() {
        menus = new String[]{"刷新页面"};
        isList = false;
        Bottom_dialog_mode = false;
    }

    @Override
    public void geParams() {
        super.geParams();

    }

    @Override
    public void customStatus() {
        super.customStatus();
        hiddenST(true);
       // int y=SpvalueStorage.getInt("currentTheme", 0);
        //loadNightOrNot(y);
        QMUIStatusBarHelper.setStatusBarLightMode(this);
        QMUIStatusBarHelper.translucent(this);
    }


    @SuppressLint("JavascriptInterface")
    @Override
    protected void configWebView(CnWebView webView) {
        super.configWebView(webView);
        webView.addJavascriptInterface(new Settings(), "app");

    }

   /* public void loadNightOrNot(int type){
        if(type==0){
            QMUIStatusBarHelper.setStatusBarLightMode(this);
        }else{
            QMUIStatusBarHelper.setStatusBarDarkMode(this);
        }

    }*/

    public class Showdialoh extends Dialogsheet {

        public Showdialoh(Context getContext) {
            super(getContext);
        }

        @Override
        public void controlClick(int position, String tag, String selectedValue) {

            SpvalueStorage.getInstance(SettingActivity.this);
            SpvalueStorage.setIntValue(tag, position);
            //Toast.makeText(SettingActivity.this, "tag:"+tag+"-->"+selectedValue+",-->"+position, Toast.LENGTH_SHORT).show();
            loadJSfunction(tag, selectedValue);
            //super.controlClick(position);
        }
    }

    public class Settings {
        private String retr = "";

        @JavascriptInterface
        public void showaloh(int key, String name, String appendStr) {
            new Showdialoh(SettingActivity.this).showBottom(key, "", Keystatics.getStatics(name), false, true, appendStr);
        }

        @JavascriptInterface
        public void setSwitch(int id,int s){
            SpvalueStorage.getInstance(SettingActivity.this);
            SpvalueStorage.setIntValue(Keystatics.keys[id],s);
/*            if(id==2){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        switch (s){
                            case 0:
                                mWebView.loadUrl("file:///android_asset/setting.html");
                                break;
                            case 1:
                                mWebView.loadUrl("file:///android_asset/nights/setting.html");
                                break;
                        }
                        loadNightOrNot(s);
                    }
                });
            }*/
            }

            @JavascriptInterface
            public void setParam(int ty){
            SpvalueStorage.getInstance(SettingActivity.this);
            SpvalueStorage.setIntValue("cmntone",ty);
            }

        @JavascriptInterface
        public void callSwics(){
            String values=autoloadSwics();
             runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                     mWebView.loadUrl("javascript:loadSwitchs("+values+")");
                 }
             });
        }

        @JavascriptInterface
        public void loadSingleprams(String name, int type) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String  mUrl=returnSpValues(name,type);
                    mWebView.loadUrl(mUrl);//"javascript:generateLists('item_hk','" + Keystatics.hk_visibles[SpvalueStorage.getInt("hk" + Keystatics.hkcmn[0], 1)] + "','"+Keystatics.tones[SpvalueStorage.getInt("hk"+Keystatics.hkcmn[1], 0)]+"',0)");
                }
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

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mWebView.loadUrl("javascript:updateSingleValue('" + tag + "','" + selectedValue + "')");
            }
        });
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
                if(_visi=="汉语拼音") _tone = Keystatics.special_tones[SpvalueStorage.getInt(tag+Keystatics.hkcmn[1], 0)];
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
            //int night=SpvalueStorage.getInt(Keystatics.keys[2],0 );
            return isOffline+","+isTabs+",";//+night;
        }





    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onPause() {
        super.onPause();
        /*SpvalueStorage.getInstance(this);
        int nightMode=SpvalueStorage.getInt(Keystatics.keys[2], 0);
        int currentTheme=SpvalueStorage.getInt("currentTheme",1 );
        if(currentTheme!=nightMode){
            SpvalueStorage.getInstance(SettingActivity.this);
            SpvalueStorage.setIntValue("currentTheme",nightMode);
            EventMsg msg=new EventMsg(nightMode);
            EventBus.getDefault().post(msg);
        }*/
    }
}
