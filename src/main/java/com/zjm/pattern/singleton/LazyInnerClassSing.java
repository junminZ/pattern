package com.zjm.pattern.singleton;

/**
 * Create by zjm on 2019/3/11
 * 懒汉式单例
 * 这种形式兼顾饿汉式的内存浪费，也兼顾synchronized性能问题
 * 完美地屏蔽了这两个缺点
 * 史上最牛B的单例模式的实现方式
 */
public class LazyInnerClassSing {

    private LazyInnerClassSing(){
        if(LazyHolder.LAZY != null){
            throw new RuntimeException("不允许创建多个实例");
        }
    }

    public static final LazyInnerClassSing getInstance() {
        return LazyHolder.LAZY;
    }

    private static class LazyHolder {
        private static final LazyInnerClassSing LAZY = new LazyInnerClassSing();
    }
}
