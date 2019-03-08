package com.zjm.pattern.factory.practice.factoryPattern.simpleFactory;

import com.zjm.pattern.factory.practice.factoryPattern.Phone;

/**
 * Create by zjm on 2019/3/8
 */
public class PhoneFactory {
    Phone create(Class<? extends Phone> clazz) {
        if (null != clazz) {
            try {
                return clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
