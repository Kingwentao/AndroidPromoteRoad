package com.example.androidpromoteroad.pluginable;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.androidpromoteroad.kotlinupgrade.View;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

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
        // 判断当前进程是否为LeakCanary进程，该进程运行一个HeapAnalyzerService服务
        // 如果不是，则初始化LeakCanary进程
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }
    }

}
