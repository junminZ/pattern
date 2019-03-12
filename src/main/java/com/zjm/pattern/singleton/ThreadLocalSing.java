package com.zjm.pattern.singleton;

/**
 * Create by zjm on 2019/3/12
 */
public class ThreadLocalSing {
    private static final ThreadLocal<ThreadLocalSing> threadLocalInstance = new ThreadLocal<ThreadLocalSing>(){
        @Override
        protected ThreadLocalSing initialValue() {
            return new ThreadLocalSing();
        }
    };
    private ThreadLocalSing(){}

    public static ThreadLocalSing getInstance() {
        return threadLocalInstance.get();
    }
}
