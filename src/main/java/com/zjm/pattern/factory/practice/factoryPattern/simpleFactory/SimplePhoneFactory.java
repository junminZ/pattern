package com.zjm.pattern.factory.practice.factoryPattern.simpleFactory;

import com.zjm.pattern.factory.practice.factoryPattern.IPhone;
import com.zjm.pattern.factory.practice.factoryPattern.Phone;

/**
 * Create by zjm on 2019/3/8
 */
public class SimplePhoneFactory {
    public static void main(String[] args) {
        PhoneFactory phoneFactory = new PhoneFactory();
        Phone phone = phoneFactory.create(IPhone.class);
        phone.call();
    }
}
