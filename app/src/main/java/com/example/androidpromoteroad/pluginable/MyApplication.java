package com.example.androidpromoteroad.pluginable;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.github.moduth.blockcanary.BlockCanary;
import com.github.moduth.blockcanary.BlockCanaryContext;
import com.wtk.nbutil.util.PermissionsUtil;

/**
 * author: created by wentaoKing
 * date: created in 2/21/21
 * description:
 */
public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //自动挂载热修复的apk
        //HotfixUtil.hotfixApk(base);
        //自动挂载热修复的dex文件
        HotfixUtil.hotfixDex(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //block canary use
        BlockCanary.install(this, new BlockCanaryContext()).start();
    }

}
