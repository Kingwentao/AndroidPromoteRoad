package com.example.androidpromoteroad.pluginable;

import android.app.Application;
import android.content.Context;

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
}
