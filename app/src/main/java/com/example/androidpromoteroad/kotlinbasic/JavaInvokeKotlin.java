package com.example.androidpromoteroad.kotlinbasic;

/**
 * author: created by wentaoKing
 * date: created in 2020-07-16
 * description: java代码调用kotlin代码
 */
public class JavaInvokeKotlin {

    private void useBean(){

        User user = new User();
        //user.setUserName("aaa");
        // @JvmField 注解能让该变量变成成员变量，不会生产get和set方法
        user.userName = "aaa";

        //调用kotlin的伴生对象
        BaseApplication.Companion.currentApplication();
        //用了@JvmStaic注解，将会将其变成静态的，然后就可以直接调用
        BaseApplication.currentApplication2();
    }
}
