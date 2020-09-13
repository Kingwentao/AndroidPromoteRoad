package com.example.androidpromoteroad.generic.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.androidpromoteroad.R;
import com.example.androidpromoteroad.generic.fruits.Apple;
import com.example.androidpromoteroad.generic.fruits.Fruit;
import com.example.androidpromoteroad.generic.fruits.GreenApple;
import com.example.androidpromoteroad.generic.fruits.Orange;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class GenericActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generic);

        //1. 错误写法
//        ArrayList<Fruit> fruits = new ArrayList<Apple>();
//        fruits.add(new Orange()); //添加橘子到苹果集合，为啥可以不报错？

        //2. 正确写法1
        ArrayList<Fruit> fruits2 = new ArrayList<Fruit>();
        fruits2.add(new Orange());
        fruits2.add(new Apple());

        //3. 或者这么写
        ArrayList<? extends Fruit> fruits3 = new ArrayList<Apple>();
        ArrayList<? extends Fruit> fruitsOr = new ArrayList<Orange>();
        //3.1 不能添加
        fruits3.add(new Orange());  //不可以添加橘子
        fruits3.add(new Apple());  //为啥不可以添加苹果？？？
        fruits3.set(0, new Orange()); //也不能使用
        //3.2 只能获取
        Fruit getFruit = fruits3.get(0);
        Apple getApple = (Apple) fruits3.get(0);

        ArrayList<? super Apple> apples = new ArrayList<>();
        Fruit fruit = new Fruit() {
        };
        apples.add(fruit);
        Apple apple2 = apples.get(0);

        //4. 强制转换
        ArrayList<Fruit> fruits4 = (ArrayList) new ArrayList<Apple>();
        fruits4.add(new Orange());  //这样就可以添加橘子
        fruits4.add(new Apple());
        Orange orange = (Orange) fruits4.get(0);
        Apple apple1 = (Apple) fruits4.get(1);
        System.out.println("orange " + orange);
        System.out.println("apple " + apple1);

        //5. ? extends 使用
        ArrayList<Apple> appList = new ArrayList<Apple>();
        getFruitName(appList);
        ArrayList<Fruit> fruitList = new ArrayList<Fruit>();
        getFruitName2(fruitList);

        superType(fruitList);
        superType(appList);

        //泛型擦除后变成
        ArrayList fruits5 = new ArrayList();
        //调用泛型方法
        FruitShop<Fruit> changeShop = new FruitShop();
        // Orange orange2 = (Orange) changeShop.changeItem(new Apple());

        Apple apple = new Apple();
        //Orange orange1 = changeShop.<Orange,Apple>change(apple); //省略类型
        Orange orange1 = changeShop.change(apple);
    }

    //这种场景使用<? extends Fruit>更合理
    void getFruitName(ArrayList<? extends Fruit> fruits) {
        for (Fruit fruit : fruits) {
            System.out.println("name is " + fruit.getName());
        }
    }

    void getFruitName2(ArrayList<Fruit> fruits) {
        for (Fruit fruit : fruits) {
            System.out.println("name is " + fruit.getName());
        }
    }

    void superType(ArrayList<? super Apple> apples){

        ArrayList<? super GreenApple> appleList = new ArrayList<Fruit>();
        apples.add(new GreenApple());
        apples.add(new Apple());

        GreenApple greenApple = appleList.get(0);
        Fruit fruit = appleList.get(0);
        Object object = appleList.get(0);


    }

}