package com.amberllo.classloaderstudy;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class ProxyActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String className = getIntent().getStringExtra("pageName");
    }
}
