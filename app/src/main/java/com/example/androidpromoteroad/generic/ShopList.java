package com.example.androidpromoteroad.generic;

import com.example.androidpromoteroad.generic.fruits.Apple;
import com.example.androidpromoteroad.generic.shop.FruitShop;

import java.util.ArrayList;
import java.util.List;

/**
 * author: created by wentaoKing
 * date: created in 2020/9/20
 * description:
 */
class ShopList<T extends List<FruitShop<Apple>>> extends ArrayList<T> {

    void sale(T item){}

}
