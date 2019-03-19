package com.zjm.pattern.proxy.dynamicproxy.jdkproxy;

import com.zjm.pattern.proxy.Car;

/**
 * Create by zjm on 2019/3/18
 */
public class Audi implements Car {
    public void buyCar() {
        System.out.println("座椅加热");
        System.out.println("LED大灯");
        System.out.println("全时四驱");
    }
}
