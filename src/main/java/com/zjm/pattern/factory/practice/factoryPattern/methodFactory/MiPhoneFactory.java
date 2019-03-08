package com.zjm.pattern.factory.practice.factoryPattern.methodFactory;

import com.zjm.pattern.factory.practice.factoryPattern.MiPhone;
import com.zjm.pattern.factory.practice.factoryPattern.Phone;

/**
 * Create by zjm on 2019/3/8
 */
public class MiPhoneFactory implements PhoneFactory{
    public Phone create() {
        return new MiPhone();
    }
}
