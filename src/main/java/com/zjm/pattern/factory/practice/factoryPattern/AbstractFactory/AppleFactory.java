package com.zjm.pattern.factory.practice.factoryPattern.AbstractFactory;

/**
 * Create by zjm on 2019/3/8
 */
public class AppleFactory implements AbstractPhoneFactory{
    public Brand getBrand() {
        return new Apple();
    }

    public Battery getBattery() {
        return new AppleBattery();
    }
}
