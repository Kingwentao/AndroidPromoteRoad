package com.example.androidpromoteroad.pluginable;

import android.content.Context;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;
import dalvik.system.DexClassLoader;

/**
 * author: created by wentaoKing
 * date: created in 2/21/21
 * description: 热修复工具类
 */
class HotfixUtil {

    static void hotfixApk(Context context) {
        String targetPath = context.getCacheDir().getPath() + File.separator + "hotfix.apk";
        if (!new File(targetPath).exists()) {
            return;
        }

        String optimizedDirectory = context.getCacheDir().getPath();
        //获取热修复apk所在目录的类加载器
        DexClassLoader dexClassLoader = new DexClassLoader(targetPath,
                optimizedDirectory, null, null);
        ClassLoader originClassLoader = context.getClassLoader();
        Class loaderClass = BaseDexClassLoader.class;
        try {
            //目的：通过反射实现->
            // originClassLoader.pathList.dexElements = dexClassLoader.pathList.dexElements
            //获取pathList数据
            Field pathListField = loaderClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);
            Object pathListObj = pathListField.get(dexClassLoader);

            //获取DexPathList的dexElements
            Class pathListClass = pathListObj.getClass();
            Field dexElementField = pathListClass.getDeclaredField("dexElements");
            dexElementField.setAccessible(true);
            Object dexElementsObj = dexElementField.get(pathListObj);

            //拿hotfix的结果去修改原先的dexElements
            Object originPathListObj = pathListField.get(originClassLoader);
            dexElementField.set(originPathListObj, dexElementsObj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    static void hotfixDex(Context context) {
        String targetPath = context.getCacheDir().getPath() + File.separator + "hotfix.dex";
        if (!new File(targetPath).exists()) {
            return;
        }
        String optimizedDirectory = context.getCacheDir().getPath();
        //获取热修复apk所在目录的类加载器
        DexClassLoader dexClassLoader = new DexClassLoader(targetPath,
                optimizedDirectory, null, null);
        ClassLoader originClassLoader = context.getClassLoader();
        Class loaderClass = BaseDexClassLoader.class;
        try {
            //目的：通过反射实现，把新的dex添加到旧的dex前面
            // originClassLoader.pathList.dexElements[0] = dexClassLoader.pathList.dexElement
            //获取pathList数据
            Field pathListField = loaderClass.getDeclaredField("pathList");
            pathListField.setAccessible(true);
            Object pathListObj = pathListField.get(dexClassLoader);

            //获取DexPathList的dexElements（新的）
            Class pathListClass = pathListObj.getClass();
            Field dexElementField = pathListClass.getDeclaredField("dexElements");
            dexElementField.setAccessible(true);
            Object dexElementsObj = dexElementField.get(pathListObj);

            //获取原先的dexElements
            Object originPathListObj = pathListField.get(originClassLoader);
            Object originElementObj = dexElementField.get(originPathListObj);

            //创建新的dexElements数组
            int oldLength = Array.getLength(originElementObj);
            int newLength = Array.getLength(dexElementsObj);
            Object contactElementsObj = Array.newInstance(dexElementsObj.getClass().getComponentType(),
                    oldLength + newLength);

            //把修复的dex文件加入到新数组的最前面
            for (int i = 0; i < newLength; i++) {
                Array.set(contactElementsObj, i, Array.get(dexElementsObj, i));
            }
            for (int i = 0; i < oldLength; i++){
                Array.set(contactElementsObj, newLength + i, Array.get(originElementObj, i));
            }
            dexElementField.set(originPathListObj, contactElementsObj);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
