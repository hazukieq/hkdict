package com.hazukie.testakka.activities;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.hazukie.testakka.Fileutils.DownloadBean;
import com.hazukie.testakka.Fileutils.DownloadManager;
import com.hazukie.testakka.Fileutils.DownloadObserver;
import com.hazukie.testakka.Fileutils.FileUtil;
import com.hazukie.testakka.R;
import com.hazukie.testakka.base.BaseActivity;
import com.hazukie.testakka.webutils.Gexhttp;
import com.hazukie.testakka.webutils.SpvalueStorage;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

public class LauncherActivity extends BaseActivity{

    private Intent intent;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SpvalueStorage.getInstance(this);
        /*int s = SpvalueStorage.getInt("currentTheme", 2);
        if (s == 2) {
            SpvalueStorage.setIntValue("currentTheme", 0);
        }*/
        checkPreload();
        checkUpdateCategories();
    }



    /*换算文件的大小*/
    public String FormetFileSize(long fileSize) {// 转换文件大小
        if (fileSize <= 0) {
            return "0";
        }
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileSize < 1024) {
            fileSizeString = df.format((double) fileSize) + "B";
        } else if (fileSize < 1048576) {
            fileSizeString = df.format((double) fileSize / 1024) + "K";
        } else if (fileSize < 1073741824) {
            fileSizeString = df.format((double) fileSize / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileSize / 1073741824) + "G";
        }
        return fileSizeString;
    }

    public void  openDialog(DownloadBean model) throws IOException {
        QMUIDialog.CustomDialogBuilder dialogBuilder = new QMUIDialog.CustomDialogBuilder(LauncherActivity.this);
        dialogBuilder.setLayout(R.layout.progressbar_ialog);
        final QMUIDialog dialog = dialogBuilder.setTitle("正在同步服务器数据").create();

        ProgressBar progressBar=(ProgressBar) dialog.findViewById(R.id.ialog_progress);
        TextView txt_title=(TextView)dialog.findViewById(R.id.ialog_title);
        TextView txt_state=(TextView) dialog.findViewById(R.id.ialog_text);

        txt_title.setText(model.name);
        progressBar.setProgress(0);

        DownloadManager.getInstance().download(model);
        progressBar.setProgress((int) ((float) model.progress
                / (float) model.appSize * 100f));

        DownloadManager.getInstance().registerObserver(model.name,
                new DownloadObserver() {
                    @Override
                    public void onPrepare(DownloadBean bean) {
                        txt_state.setText("排队等待中");
                    }

                    @Override
                    public void onStart(DownloadBean bean) {
                       txt_state.setText("开始下载");
                        dialog.show();
                    }

                    @Override
                    public void onProgress(DownloadBean bean) {
                        txt_state
                                .setText(FormetFileSize(bean.progress)
                                        + " / "
                                        + FormetFileSize(bean.appSize));

                        progressBar
                                .setProgress((int) ((float) bean.progress
                                        / (float) bean.appSize * 100f));
                    }
                    @Override
                    public void onStop(DownloadBean bean) {
                        txt_state.setText("暂停中");
                    }
                    @Override
                    public void onFinish(DownloadBean bean) throws IOException {

                        txt_state.setText("下载完成");
                        progressBar.setProgress(100);
                        dialog.dismiss();
                        SpvalueStorage.setIntValue("isPreload",1);
                        initMainAct();
                    }
                    @Override
                    public void onError(DownloadBean bean) {
                        txt_state.setText("下载失败");
                        DownloadManager.getInstance().download(bean);
                    }

                    @Override
                    public void onDelete(DownloadBean bean) {
                        txt_state.setText(FormetFileSize(0)
                                + " / " + FormetFileSize(bean.appSize));
                        progressBar.setProgress(0);
                    }
                });

        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
                    return true;
                }
                return false;
            }
        });
        dialog.setCanceledOnTouchOutside(false);

    }

    private void checkPreload(){
        int isPreload=SpvalueStorage.getInt("isPreload",0);
        if(isPreload==0) {
            File f = this.getDir("datas", MODE_PRIVATE);
            String path = f.getAbsolutePath();
            DownloadBean model = new DownloadBean("精简数据库", -1, "https://www.hazukieq.top/database/simple_db.db", path + "/simple_db.db", 0, 0);
            try {
                //boolean isCopy = new FileUtil(this).copyAsset2File(getAssets().open("font/han.zip"), "han.zip");
                //Log.i( "checkPreload: ",f.getAbsolutePath());
                //if (isCopy)
                  //  new FileUtil(this).unzip(new File(f, "han.zip").getAbsolutePath(), f.getAbsolutePath());
                openDialog(model);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            initMainAct();
        }
    }

    private void checkUpdateCategories(){
        Calendar cal=Calendar.getInstance();
        Date date=new Date();
        cal.setTime(date);
        Integer day=cal.get(Calendar.DAY_OF_YEAR);

        int check_=SpvalueStorage.getInt("hkcategories_date",0);
        Log.i("curret-day:",String.valueOf(check_));
        if(check_==0){
            SpvalueStorage.setIntValue("hkcategories_date",day);
            Gexhttp.sendMsg(LauncherActivity.this,"https://blog.hazukieq.top/apis/hkarea.txt",day);
        }else if(check_+3==day){
                Log.i("Launcher:","Now updating Hkcategories!");
                Gexhttp.sendMsg(LauncherActivity.this,"https://blog.hazukieq.top/apis/hkarea.txt",day);
        }
    }

    private void initMainAct(){
        if (intent == null) {
            intent = new Intent(this, MainActivity.class);
        }
        this.startActivity(intent);
        finish();
    }

    @Override
    public void overridePendingTransition(int enterAnim, int exitAnim) {
        super.overridePendingTransition(enterAnim, exitAnim);
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getAction()==KeyEvent.ACTION_DOWN){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DownloadManager.getInstance().Destory();
    }
}