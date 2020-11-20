package com.stone.locknotify;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class App extends Application implements Runnable {
    private static final String TAG = "App";
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void run() {
        Log.d(TAG, "run: ");
        Intent intent = new Intent(this, LockActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void startActivity(Intent intent, Bundle options) {
        try {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            super.startActivity(intent, options);
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "startActivity: " + e.getMessage());
        }
    }
}
