package com.stone.locknotify;

import android.app.KeyguardManager;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LockActivity extends AppCompatActivity {
    private PowerManager pm;
    private PowerManager.WakeLock wl;
    private KeyguardManager km;
    private KeyguardManager.KeyguardLock kl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        final Window win = getWindow();
        win.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
        );

        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_notify_big2);

        findViewById(R.id.title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wakeAndUnlock(true);
                startActivity(new Intent(LockActivity.this, DetailActivity.class));

                finish();
            }
        });
        ((ImageView) findViewById(R.id.custom_song_icon)).setImageResource(R.mipmap.ic_launcher);

    }

    private static final String TAG = "LockActivity";

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: ");
        super.onResume();
        new Handler().postDelayed(new Runnable() {
            private DevicePolicyManager policyManager;

            @Override
            public void run() {
                policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
                policyManager.lockNow();
            }
        }, 10000);
    }

    private void wakeAndUnlock(boolean b) {
        //获取电源管理器对象
        pm = (PowerManager) getSystemService(Context.POWER_SERVICE);

        //获取PowerManager.WakeLock对象，后面的参数|表示同时传入两个值，最后的是调试用的Tag
        wl = pm.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_BRIGHT_WAKE_LOCK, "bright");

        //点亮屏幕
        wl.acquire(1000);
        wl.release();

        // 屏幕解锁
        KeyguardManager keyguardManager = (KeyguardManager) this.getSystemService(KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock keyguardLock = keyguardManager.newKeyguardLock("unLock");
        // 屏幕锁定
//        keyguardLock.reenableKeyguard();
        keyguardLock.disableKeyguard(); // 解锁
        final Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);

        win.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        setImmersiveStatusBar(view);
    }

    @Override
    public void setContentView(int layoutResID) {
        View view = LayoutInflater.from(this).inflate(layoutResID, null);
        super.setContentView(view);
        setImmersiveStatusBar(view);
    }

    /**
     * 设置沉浸式状态栏
     *
     * @param view
     */
    protected void setImmersiveStatusBar(View view) {
        if (!StatusBarUtils.setLightMode(this)) {
            StatusBarUtils.setColor(this, Color.BLACK);
        } else {
            StatusBarUtils.setColorNoTranslucent(this, Color.BLACK);
        }
        StatusBarUtils.setFitsSystemWindows(view, true);
    }
}
