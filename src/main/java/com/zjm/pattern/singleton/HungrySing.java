package com.zjm.pattern.singleton;

/**
 * Create by zjm on 2019/3/11
 * 单例模式-饿汉式
 */
public class HungrySing {
    private HungrySing() { }

    private static final HungrySing hungrySing ;

    static {
        hungrySing =  new HungrySing();
    }

    public static HungrySing getInstance() {
        return hungrySing;
    }
}
