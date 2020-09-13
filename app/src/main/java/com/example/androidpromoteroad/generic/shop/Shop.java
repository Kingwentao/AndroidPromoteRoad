package com.example.androidpromoteroad.generic.shop;

/**
 * author: created by wentaoKing
 * date: created in 2020/9/8
 * description:
 */
public interface Shop<T> {
    void sale(T item);


    //泛型的类型约束
    <T> void limitType(T a, T b);

}
