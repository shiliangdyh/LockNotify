package com.stone.locknotify;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyAdmin extends DeviceAdminReceiver {
    private static final String TAG = "MyAdmin";
    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        System.out.println("onreceiver");
        Log.d(TAG, "onReceive: ");
    }

    @Override
    public void onEnabled(Context context, Intent intent) {
        System.out.println("激活使用");
        Log.d(TAG, "onEnabled: ");
        super.onEnabled(context, intent);
    }

    @Override
    public void onDisabled(Context context, Intent intent) {
        System.out.println("取消激活");
        Log.d(TAG, "onDisabled: ");
        super.onDisabled(context, intent);
    }
}
