package com.stone.locknotify;

import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements Runnable, View.OnClickListener {
    private static final String TAG = "MainActivity";
    private boolean isAdminActive;
    private DevicePolicyManager policyManager;
    private ComponentName componentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.text).setOnClickListener(this);


    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: ");
        super.onPause();

    }

    @Override
    public void run() {
        Log.d(TAG, "run: ");
        Intent intent = new Intent(this, LockActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        lockScreen(v);
    }

    public void lockScreen(View v){
        policyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(this, MyAdmin.class);
        if (policyManager.isAdminActive(componentName)) {//判断是否有权限(激活了设备管理器)
            lockNow();
        }else{
            activeManager();//激活设备管理器获取权限
        }
    }

    // 解除绑定
    public void Bind(View v){
        if(componentName!=null){
            policyManager.removeActiveAdmin(componentName);
            activeManager();
        }
    }
    @Override
    protected void onResume() {//重写此方法用来在第一次激活设备管理器之后锁定屏幕
        if (policyManager!=null && policyManager.isAdminActive(componentName)) {
//            lockNow();
        }
        super.onResume();
    }

    private void lockNow(){
        Log.d(TAG, "lockNow: ");
        policyManager.lockNow();
//        android.os.Process.killProcess(android.os.Process.myPid());
        new Handler().postDelayed(this, 3000);
    }

    private void activeManager() {
        //使用隐式意图调用系统方法来激活指定的设备管理器
        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
        intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "一键锁屏");
        startActivity(intent);
    }

}