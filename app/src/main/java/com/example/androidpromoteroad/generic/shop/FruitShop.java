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
}