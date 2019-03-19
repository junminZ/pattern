package com.zjm.pattern.proxy.dynamicproxy.jdkproxy;

import com.zjm.pattern.proxy.Car;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Create by zjm on 2019/3/18
 */
public class JDKMediation implements InvocationHandler {
    private Car target;

    public Object getInstance(Car car) {
        this.target = car;
        Class<?> clazz = target.getClass();
        return Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object invoke = method.invoke(this.target, args);
        after();
        return invoke;
    }

    private void before() {
        System.out.println("before：我是中介，我已经了解到你的需求");
        System.out.println("before：进行筛选");
    }

    private void after() {
        System.out.println("after：可以的话签约成交、送全车贴膜倒车影像");
    }
}
