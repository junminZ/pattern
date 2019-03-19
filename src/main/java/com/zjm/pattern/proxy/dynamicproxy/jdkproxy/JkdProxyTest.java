package com.zjm.pattern.proxy.dynamicproxy.jdkproxy;

import com.zjm.pattern.proxy.Car;

/**
 * Create by zjm on 2019/3/18
 */
public class JkdProxyTest {
    public static void main(String[] args) {
        Car c = (Car) new JDKMediation().getInstance(new Audi());
        c.buyCar();
    }
}
