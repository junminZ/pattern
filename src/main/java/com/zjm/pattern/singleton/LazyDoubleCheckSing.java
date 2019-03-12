package com.zjm.pattern.singleton;

/**
 * Create by zjm on 2019/3/11
 * 单例模式-双重检验锁
 */
public class LazyDoubleCheckSing {
    private LazyDoubleCheckSing() {}

    private volatile static LazyDoubleCheckSing lazySing;

    public static LazyDoubleCheckSing getInstance() {
        if (lazySing == null) {
            synchronized (LazyDoubleCheckSing.class) {
                if (lazySing == null) {
                    lazySing = new LazyDoubleCheckSing();
                }
            }
        }
        return lazySing;
    }
}
