package com.amberllo.classloaderstudy;

import android.content.Context;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import dalvik.system.PathClassLoader;

public class PluginManager {

    public static void loadApk(Context context, String clazzName, String apkName) {

        // Step1. 获取到插件apk，通常都是从网络上下载，这里为了演示，直接将插件apk push到手机
        File pluginFile = context.getExternalFilesDir("plugin");
        if (pluginFile != null && !pluginFile.exists() || pluginFile.listFiles().length == 0) {
            Toast.makeText(context, "插件文件不存在", Toast.LENGTH_SHORT).show();
            return;
        }
        pluginFile =new File(pluginFile + "/" + apkName);
        // Step2. 创建插件的DexClassLoader

        PathClassLoader dexClassLoader = new PathClassLoader(pluginFile.getAbsolutePath(), context.getClassLoader());
        try {
            Class clazz = dexClassLoader.loadClass(clazzName);
            Toast.makeText(context, clazz.getSimpleName(), Toast.LENGTH_SHORT).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void checkAndCopyFile(Context context, String apkName) {
        try {
            File pluginFile = context.getExternalFilesDir("plugin");

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
            is = context.getAssets().open(apkName);
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
}
