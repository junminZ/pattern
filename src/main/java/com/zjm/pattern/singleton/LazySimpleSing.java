package com.zjm.pattern.singleton;

/**
 * Create by zjm on 2019/3/11
 * 单例模式-懒汉式
 */
public class LazySimpleSing {
    private LazySimpleSing() {}

    private static LazySimpleSing lazySing;

    public synchronized static LazySimpleSing getInstance() {
        if (lazySing == null) {
            lazySing = new LazySimpleSing();
        }
        return lazySing;
    }
}
