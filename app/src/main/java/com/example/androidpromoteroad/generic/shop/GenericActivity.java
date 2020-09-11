package com.example.androidpromoteroad.generic.shop;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.androidpromoteroad.R;
import com.example.androidpromoteroad.generic.fruits.Apple;
import com.example.androidpromoteroad.generic.fruits.Fruit;
import com.example.androidpromoteroad.generic.fruits.Orange;

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
//        ArrayList<? extends Fruit> fruits3 = new ArrayList<Apple>();
//        //3.1 不能添加
//        fruits3.add(new Orange());  //不可以添加橘子
//        fruits3.add(new Apple());  //为啥不可以添加苹果？？？
//        //3.2 只能获取
//        Apple apple = (Apple) fruits3.get(1);
//        Orange orange = (Orange) fruits3.get(0);
//
//        ArrayList<? super Apple> apples = new ArrayList<>();
//        Fruit fruit = new Fruit() {
//
//        };
//        apples.add(fruit);
//        Apple apple2 = apples.get(0);

        //4. 强制转换
        ArrayList<Fruit> fruits4 = (ArrayList) new ArrayList<Apple>();
        fruits4.add(new Orange());  //这样就可以添加橘子
        Orange apple1 = (Orange) fruits4.get(0);
        System.out.println("apple " + apple1);

        //泛型擦除后变成
        ArrayList<Object> fruits5 = new ArrayList<Object>();
    }
}