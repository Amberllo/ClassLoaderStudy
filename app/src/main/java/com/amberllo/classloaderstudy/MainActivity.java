package com.amberllo.classloaderstudy;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAndCopyFile("app-release.apk");
                loadApk("com.amberllo.livedemo.business.activity.SplashActivity", "app-release.apk");
            }
        });


    }

    public void checkAndCopyFile(String apkName) {
        try {
            File pluginFile = getExternalFilesDir("plugin");

            File apkFile = new File(pluginFile + "/" + apkName);


            if (apkFile.exists() && apkFile.length()!=0) {
                return;
            }
            apkFile.delete();
            if (pluginFile != null && !pluginFile.exists() || pluginFile.listFiles().length == 0) {
                pluginFile.mkdirs();
            }
            InputStream is = null;
            FileOutputStream fos = null;
            is = getAssets().open(apkName);
            fos = new FileOutputStream(apkFile);
            byte[] buffer = new byte[1024];
            int byteCount;
            while ((byteCount = is.read(buffer)) != -1) {
                fos.write(buffer, 0, byteCount);
            }
            fos.flush();
            is.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }


    }

    public void loadApk(String clazzName, String apkName) {

        // Step1. 获取到插件apk，通常都是从网络上下载，这里为了演示，直接将插件apk push到手机
        File pluginFile = getExternalFilesDir("plugin");
        if (pluginFile != null && !pluginFile.exists() || pluginFile.listFiles().length == 0) {
            Toast.makeText(this, "插件文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        pluginFile =new File(pluginFile + "/" + apkName);
        // Step2. 创建插件的DexClassLoader

        PathClassLoader dexClassLoader = new PathClassLoader(pluginFile.getAbsolutePath(),  getClassLoader());
        try {
            Class clazz = dexClassLoader.loadClass(clazzName);
            Toast.makeText(this, clazz.getSimpleName(), Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
