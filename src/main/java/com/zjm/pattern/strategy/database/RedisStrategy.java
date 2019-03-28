package com.zjm.pattern.strategy.database;

/**
 * Create by zjm on 2019/3/28
 */
public class RedisStrategy implements DatabasesStrategy{
    public void save() {
        System.out.println("使用redis存储数据");
    }
}
