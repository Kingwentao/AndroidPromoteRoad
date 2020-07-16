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
    }
}
