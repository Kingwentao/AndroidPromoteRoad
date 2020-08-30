package com.example.lib_bind;

import android.app.Activity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * author: created by wentaoKing
 * date: created in 2020/8/30
 * description: 通过bind processor生成的类实现view绑定
 */
public class Binding {
    public static void bind(Activity activity) {
        try {
            //约定：获取绑定view所在的类+Binding -> 注解处理器最终生成的类
            Class bindClass = Class.forName(activity.getClass().getCanonicalName() + "Binding");
            Constructor constructor = bindClass.getDeclaredConstructor(activity.getClass());
            constructor.newInstance(activity);
        } catch (ClassNotFoundException | NoSuchMethodException |
                InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
