package com.zjm.pattern.factory.practice.factoryPattern.AbstractFactory;

/**
 * Create by zjm on 2019/3/8
 */
public class AppleBattery implements Battery {
    public void charging() {
        System.out.println("给Apple充电");
    }
}
