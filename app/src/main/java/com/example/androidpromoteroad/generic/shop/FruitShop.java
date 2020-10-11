package com.example.androidpromoteroad.generic.shop;

import com.example.androidpromoteroad.generic.fruits.Fruit;

/**
 * author: created by wentaoKing
 * date: created in 2020/9/8
 * description:
 */
public class FruitShop<F extends Fruit> implements Shop<F>{
    @Override
    public void sale(F item) {

    }

    @Override
    public <T> void limitType(T a, T b) {

    }

    Object changeItem(Object item){
        Object newItem = new Object();
        return newItem;
    }

    //泛型方法声明
//    <T,E> T change(E item) {
//        //省略具体实现
//    }

}
