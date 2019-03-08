package com.zjm.pattern.factory.practice.factoryPattern.AbstractFactory;

/**
 * Create by zjm on 2019/3/8
 */
public class XiaoMiFactory implements AbstractPhoneFactory{
    public Brand getBrand() {
        return new XiaoMi();
    }

    public Battery getBattery() {
        return new XiaoMiBattery();
    }
}
