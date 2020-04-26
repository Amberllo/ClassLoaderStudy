package com.amberllo.classloaderstudy;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.textView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PluginManager.checkAndCopyFile(v.getContext(), "app-release.apk");
                PluginManager.loadApk(v.getContext(), "com.amberllo.livedemo.business.activity.SplashActivity", "app-release.apk");
            }
        });


    }

}