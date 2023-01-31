package com.hazukie.testakka.fileutils;

import android.content.Context;
import android.util.Log;

import com.hazukie.testakka.activities.LauncherActivity;
import com.hazukie.testakka.webutils.Gexhttp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtil {
    private Context context;
    public FileUtil(Context context){
        this.context=context;
    }
    public boolean writeF(String to_name, List<Object> lists) throws IOException {
        File f= context.getDir("datas",Context.MODE_PRIVATE);
        if(!f.exists()) f.mkdirs();
        File file=new File(f,to_name);
        if(!file.exists()) file.createNewFile();
        Log.i( "writeF: ",file.getAbsolutePath());

        FileWriter out=new FileWriter(file);
        BufferedWriter writer=new BufferedWriter(out);
        for(Object obj:lists){
            writer.write(obj.toString());
        }
        writer.close();
        return file.exists();
    }

    public boolean writeF(String to_name,String str) throws IOException {
        File f= context.getDir("datas",Context.MODE_PRIVATE);
        if(!f.exists()) f.mkdirs();
        File file=new File(f,to_name);
        if(!file.exists()) file.createNewFile();
        FileWriter out=new FileWriter(file);
        BufferedWriter writer=new BufferedWriter(out);
        writer.write(str);
        writer.close();
        return file.exists();
    }

    public List<Object> readF(String to_name) throws IOException {
        List<Object> lists=new ArrayList<>();
        File file=new File(context.getDir("datas",Context.MODE_PRIVATE),to_name);
        FileReader in=new FileReader(file);
        BufferedReader reader=new BufferedReader(in);
        String line="";
        while((line=reader.readLine())!=null){
            lists.add(line);
        }

        reader.close();
        return lists;
    }

    public  String readFs(String to_name) throws  IOException{
        StringBuilder builder=new StringBuilder();
        File file=new File(context.getDir("datas",Context.MODE_PRIVATE),to_name);
        if(!file.exists()) {
            Gexhttp.sendMsg(context,"https://blog.hazukieq.top/apis/hkarea.txt",0);
            file.createNewFile();
        }else{
            FileReader in=new FileReader(file);
            BufferedReader reader=new BufferedReader(in);
            String line;
            while((line=reader.readLine())!=null){
                builder.append(line);
            }
            reader.close();
        }

        return  builder.toString();
    }

    public void unzip(String file_path,String to_path){
        File file=new File(file_path);
        if(!file.exists()) throw new RuntimeException("文件不存在！");

        ZipFile zipFile=null;
        try{
            zipFile=new ZipFile(file);
            Enumeration<?> entries=zipFile.entries();
            while (entries.hasMoreElements()){
                ZipEntry entry=(ZipEntry) entries.nextElement();
                if(entry.isDirectory()){
                    String dirPath=to_path+"/"+entry.getName();
                    File dir=new File(dirPath);
                    dir.mkdirs();
                }else{
                    File targetF=new File(to_path+"/"+entry.getName());
                    if(!targetF.getParentFile().exists()){
                        targetF.getParentFile().mkdirs();
                    }
                    targetF.createNewFile();
                    InputStream is=zipFile.getInputStream(entry);
                    FileOutputStream fos=new FileOutputStream(targetF);
                    int len;
                    byte[] buf=new byte[1024];
                    while ((len=is.read(buf))!=-1){
                        fos.write(buf,0,len);
                    }
                    fos.close();
                    is.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(zipFile!=null){
                try{
                    zipFile.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }

    public boolean copyAsset2File(InputStream is,String target){
        try{
            File file=context.getDir("datas",Context.MODE_PRIVATE);
            File s_file=new File(file,"han.zip");
            if(!s_file.exists()) s_file.createNewFile();
            FileOutputStream fout=new FileOutputStream(s_file);
            byte[] bytes=new byte[1024];
            int len;
            while ((len=is.read(bytes))!=-1){
                fout.write(bytes,0,len);
            }
            fout.flush();
            is.close();
            fout.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
