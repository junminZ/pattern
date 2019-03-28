package com.zjm.pattern.strategy.database;

/**
 * Create by zjm on 2019/3/28
 */
public class MysqlStrategy implements DatabasesStrategy{
    public void save() {
        System.out.println("使用Mysql存储数据");
    }
}
