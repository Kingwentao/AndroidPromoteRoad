package com.example.lib_reflection;

import android.app.Activity;
import java.lang.reflect.Field;

/**
 * author: created by wentaoKing
 * date: created in 2020/8/30
 * description: 绑定view控件的类
 */
public class Binding{

    //绑定activity的view控件
    public static void bind(Activity activity) {
        for (Field field : activity.getClass().getDeclaredFields()) {
            BindView bindView = field.getAnnotation(BindView.class);
            if (bindView == null) continue;
            try {
                field.setAccessible(true);
                field.set(activity, activity.findViewById(bindView.value()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
