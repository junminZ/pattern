package com.zjm.pattern.singTest;

import com.zjm.pattern.singleton.LazyInnerClassSing;

/**
 * Create by zjm on 2019/3/11
 */
public class ExectorThread implements Runnable{
    public void run() {
//        LazySimpleSing lazySimpleSing = LazySimpleSing.getInstance();
//        System.out.println(Thread.currentThread().getName()+":----"+lazySimpleSing);
        LazyInnerClassSing instance = LazyInnerClassSing.getInstance();
        System.out.println(Thread.currentThread().getName()+":----"+instance);
    }
}
