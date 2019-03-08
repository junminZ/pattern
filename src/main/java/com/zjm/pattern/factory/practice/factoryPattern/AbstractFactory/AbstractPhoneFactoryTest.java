package com.zjm.pattern.factory.practice.factoryPattern.AbstractFactory;

/**
 * Create by zjm on 2019/3/8
 */
public class AbstractPhoneFactoryTest {
    public static void main(String[] args) {
//        AbstractPhoneFactory phoneFactory = new AppleFactory();
//        Brand apple = phoneFactory.getBrand();
//        apple.play();
//        AbstractPhoneFactory xiaoMiFactory = new XiaoMiFactory();
//        Brand xiaomi = xiaoMiFactory.getBrand();
//        xiaomi.play();
        AbstractPhoneFactory phoneFactory = new AppleFactory();
        phoneFactory.getBattery().charging();
    }
}
