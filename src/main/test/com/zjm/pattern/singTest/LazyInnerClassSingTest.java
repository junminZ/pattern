package com.zjm.pattern.singTest;

/**
 * Create by zjm on 2019/3/11
 */
public class LazyInnerClassSingTest {
    public static void main(String[] args) {
        Thread t1 = new Thread(new ExectorThread());
        Thread t2 = new Thread(new ExectorThread());

        t1.start();
        t2.start();
    }
}
