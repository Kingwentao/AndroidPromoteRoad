package com.example.lib_reflection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

/**
 * author: WentaoKing
 * created on: 2020/8/29
 * description: bind view的注解
 */

@Retention(RetentionPolicy.RUNTIME)
public @interface BindView {
    int value();
}
