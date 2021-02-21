package com.example.androidpromoteroad.pluginable;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.androidpromoteroad.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * 插件化学习：最重要的功能就是动态部署
 * 模拟粗暴方式：直接把apk放入，通过DexClassLoader加载需要访问的类
 */
public class PluginableActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLoadPlugin;
    Button btnBug;
    Button btnHotfix;
    Button btnRemoveHotfix;
    Button btnKillSelf;
    TextView tvShowText;
    String mDexTargetCachePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pluginable);
        btnLoadPlugin = findViewById(R.id.btnLoadPlugin);
        btnHotfix = findViewById(R.id.btnHotfix);
        tvShowText = findViewById(R.id.tvShowText);
        btnRemoveHotfix = findViewById(R.id.btnRemoveHotfix);
        btnKillSelf = findViewById(R.id.btnKillSelf);
        btnBug = findViewById(R.id.btnBug);
        btnLoadPlugin.setOnClickListener(this);
        btnHotfix.setOnClickListener(this);
        btnBug.setOnClickListener(this);
        btnRemoveHotfix.setOnClickListener(this);
        btnKillSelf.setOnClickListener(this);
        //reflectTest();
        mDexTargetCachePath =
                getCacheDir().getPath() + File.separator + "hotfix.dex";
    }


    //访问插件的内容
    private void loadPlugin() {
        String targetPath = getCacheDir().getPath() + File.separator + "plugin.apk";
        //把apk拷贝到cache目录下
        copyAssetFile("plugin.apk", targetPath);
        String optimizedDirectory = getCacheDir().getPath();
        //获取插件apk所在目录的类加载器
        DexClassLoader dexClassLoader = new DexClassLoader(targetPath,
                optimizedDirectory, null, null);
        try {
            //获取插件apk的所要访问的插件类
            Class pluginClass =
                    dexClassLoader.loadClass("com.example.plugin.PluginClass");
            //解除构造方法的访问限制
            Constructor constructor = pluginClass.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            Object object = constructor.newInstance();
            Method method = pluginClass.getDeclaredMethod("getName");
            //解除内部方法的访问限制
            method.setAccessible(true);
            Object res = method.invoke(object);
            System.out.println(res);
        } catch (ClassNotFoundException
                | IllegalAccessException
                | InstantiationException
                | InvocationTargetException
                | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    /**
     * 热修复整个apk
     */
    private void hotfixApk() {
        String targetPath = getCacheDir().getPath() + File.separator + "hotfix.apk";
        //把apk拷贝到cache目录下
        copyAssetFile("hotfix.apk", targetPath);
        HotfixUtil.hotfixApk(this);
    }

    /**
     * 热修复dex文件
     */
    private void hotfixDex() {
        //把修复的dex文件拷贝到cache目录下，dex文件可通过d8工具对class文件操作来生成
        copyAssetFile("hotfix.dex", mDexTargetCachePath);
        HotfixUtil.hotfixDex(this);
    }

    private void copyAssetFile(String assetFileName, String targetPath) {
        try (Source source = Okio.source(getAssets().open(assetFileName));
             BufferedSink bufferedSink = Okio.buffer(Okio.sink(new File(targetPath)))) {
            bufferedSink.writeAll(source);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reflectTest() {
        try {
            Class reflectionTest = Class.forName("com.example." +
                    "androidpromoteroad.pluginable.reflection.ReflectionTest");
            //解除构造方法的访问限制
            Constructor constructor = reflectionTest.getDeclaredConstructors()[0];
            constructor.setAccessible(true);
            Object object = constructor.newInstance();
            Method method = reflectionTest.getDeclaredMethod("getName");
            //解除内部方法的访问限制
            method.setAccessible(true);
            Object res = method.invoke(object);
            System.out.println(res);
        } catch (ClassNotFoundException
                | IllegalAccessException
                | InstantiationException
                | InvocationTargetException
                | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private void showBugText() {
        BugClass bugClass = new BugClass();
        tvShowText.setText(bugClass.getBug());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnHotfix: {
                //hotfixApk();
                hotfixDex();
                break;
            }
            case R.id.btnLoadPlugin: {
                loadPlugin();
                break;
            }
            case R.id.btnBug: {
                showBugText();
                break;
            }
            case R.id.btnKillSelf: {
                Process.killProcess(Process.myPid());
                break;
            }
            case R.id.btnRemoveHotfix: {
                File dexFile = new File(mDexTargetCachePath);
                if (dexFile.exists()) {
                    dexFile.delete();
                }
                break;
            }
        }
    }
}