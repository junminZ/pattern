package com.zjm.pattern.factory.practice.factoryPattern.methodFactory;

/**
 * Create by zjm on 2019/3/8
 */
public class MethodFactoryTest {
    public static void main(String[] args) {
//        IPhoneFactory iPhoneFactory = new IPhoneFactory();
////        iPhoneFactory.create().call();
        MiPhoneFactory miPhoneFactory = new MiPhoneFactory();
        miPhoneFactory.create().call();
    }
}
