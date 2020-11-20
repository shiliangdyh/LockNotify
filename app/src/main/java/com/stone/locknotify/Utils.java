package com.stone.locknotify;

import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.os.Handler;
import android.os.PowerManager;

public final class Utils {
    public static void acquire(Context context){
        PowerManager pm = (PowerManager) context.getApplicationContext().getSystemService(Context.POWER_SERVICE);
        final PowerManager.WakeLock wakeLock = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "TAG");
        wakeLock.acquire(10*60*1000L /*10 minutes*/);//亮屏
    }

    public static void lock(Context context){
        DevicePolicyManager dpm = (DevicePolicyManager) context.getApplicationContext().getSystemService(Context.DEVICE_POLICY_SERVICE);

        dpm.lockNow();

    }
}
