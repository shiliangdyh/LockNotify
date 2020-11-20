package com.stone.locknotify;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class DetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Window win = getWindow();

        WindowManager.LayoutParams params =win.getAttributes();

        params.flags |=
//                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED

                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD

                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON

                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;

        win.setAttributes(params);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    }
}
